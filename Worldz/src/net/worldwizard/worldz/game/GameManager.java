/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.creatures.PartyMember;
import net.worldwizard.worldz.creatures.StatGUI;
import net.worldwizard.worldz.effects.EffectConstants;
import net.worldwizard.worldz.effects.EffectManager;
import net.worldwizard.worldz.generic.ArrowTypeConstants;
import net.worldwizard.worldz.generic.GenericMovableObject;
import net.worldwizard.worldz.generic.TypeConstants;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.generic.WorldObjectList;
import net.worldwizard.worldz.objects.AnnihilationWand;
import net.worldwizard.worldz.objects.DisarmTrapWand;
import net.worldwizard.worldz.objects.Empty;
import net.worldwizard.worldz.objects.EmptyVoid;
import net.worldwizard.worldz.objects.Player;
import net.worldwizard.worldz.objects.TeleportWand;
import net.worldwizard.worldz.objects.Wall;
import net.worldwizard.worldz.objects.WallBreakingWand;
import net.worldwizard.worldz.objects.WallMakingWand;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;
import net.worldwizard.worldz.resourcemanagers.MusicManager;
import net.worldwizard.worldz.resourcemanagers.SoundManager;
import net.worldwizard.worldz.scripts.Script;
import net.worldwizard.worldz.world.World;
import net.worldwizard.worldz.world.WorldConstants;
import net.worldwizard.worldz.world.WorldManager;

public class GameManager implements EffectConstants {
    // Fields
    private JFrame outputFrame;
    private Container outputPane, borderPane;
    private JLabel messageLabel;
    private WorldObject savedWorldObject, objectBeingUsed;
    private EventHandler handler;
    private ObjectInventory objectInv, savedObjectInv;
    private boolean pullInProgress;
    private boolean using;
    private int lastUsedObjectIndex;
    private boolean knm;
    private boolean savedGameFlag;
    private int activeArrowType;
    private int poisonCounter;
    private final PlayerLocationManager plMgr;
    private final GameViewingWindowManager vwMgr;
    private final StatGUI sg;
    private final EffectManager em;
    private JLabel[][] drawGrid;
    private boolean delayedDecayActive;
    private WorldObject delayedDecayObject;
    private boolean actingRemotely;
    boolean arrowActive;
    boolean teleporting;
    private int[] remoteCoords;
    private boolean stateChanged;

    // Constructors
    public GameManager() {
        this.plMgr = new PlayerLocationManager();
        this.vwMgr = new GameViewingWindowManager();
        this.em = new EffectManager();
        this.sg = new StatGUI();
        this.setUpGUI();
        this.setPullInProgress(false);
        this.setUsingAnItem(false);
        this.savedWorldObject = new Empty();
        this.lastUsedObjectIndex = 0;
        this.knm = false;
        this.savedGameFlag = false;
        this.delayedDecayActive = false;
        this.delayedDecayObject = null;
        this.actingRemotely = false;
        this.remoteCoords = new int[3];
        this.arrowActive = false;
        this.teleporting = false;
        this.activeArrowType = ArrowTypeConstants.ARROW_TYPE_PLAIN;
        this.stateChanged = true;
        this.poisonCounter = 0;
    }

    // Methods
    public boolean newGame() {
        if (this.savedGameFlag) {
            return true;
        } else {
            return PartyManager.createParty();
        }
    }

    public void invalidateStatImageCaches() {
        this.sg.updateGUI();
    }

    public void stateChanged() {
        this.stateChanged = true;
    }

    public World getTemporaryBattleCopy() {
        return Worldz.getApplication().getWorldManager().getWorld()
                .getTemporaryBattleCopy(this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ());
    }

    public PlayerLocationManager getPlayerManager() {
        return this.plMgr;
    }

    public GameViewingWindowManager getViewManager() {
        return this.vwMgr;
    }

    public JLabel getMessageLabel() {
        return this.messageLabel;
    }

    public void setArrowType(final int type) {
        this.activeArrowType = type;
    }

    void arrowDone() {
        this.arrowActive = false;
        this.activeArrowType = ArrowTypeConstants.ARROW_TYPE_PLAIN;
        this.sg.updateStats();
        if (!PartyManager.getParty().isAlive()) {
            this.gameOver();
        }
    }

    public WorldObject getSavedWorldObject() {
        return this.savedWorldObject;
    }

    public void setSavedWorldObject(final WorldObject newSavedObject) {
        this.savedWorldObject = newSavedObject;
    }

    public boolean isFloorBelow() {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            m.getCell(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ() - 1,
                    WorldConstants.LAYER_OBJECT);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        } catch (final NullPointerException np) {
            return false;
        }
    }

    public boolean isFloorAbove() {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            m.getCell(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ() + 1,
                    WorldConstants.LAYER_OBJECT);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        } catch (final NullPointerException np) {
            return false;
        }
    }

    public boolean isLevelBelow() {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        return m.doesLevelExistOffset(-1);
    }

    public boolean isLevelAbove() {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        return m.doesLevelExistOffset(1);
    }

    public boolean doesFloorExist(final int floor) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            m.getCell(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(), floor,
                    WorldConstants.LAYER_OBJECT);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        } catch (final NullPointerException np) {
            return false;
        }
    }

    public void resetObjectInventory() {
        this.objectInv = new ObjectInventory();
    }

    public void setSavedGameFlag(final boolean value) {
        this.savedGameFlag = value;
    }

    public boolean usingAnItem() {
        return this.using;
    }

    public boolean isTeleporting() {
        return this.teleporting;
    }

    public void setUsingAnItem(final boolean isUsing) {
        this.using = isUsing;
    }

    public void setPullInProgress(final boolean pulling) {
        this.pullInProgress = pulling;
    }

    public boolean isPullInProgress() {
        return this.pullInProgress;
    }

    public void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
    }

    private void decayEffects() {
        this.em.decayEffects();
    }

    public void activateEffect(final int effectID, final int duration) {
        this.em.activateEffect(effectID, duration);
    }

    private void deactivateAllEffects() {
        this.em.deactivateAllEffects();
    }

    int[] doEffects(final int x, final int y) {
        return this.em.doEffects(x, y);
    }

    public void setRemoteAction(final int x, final int y, final int z) {
        this.remoteCoords = new int[] { x, y, z };
        this.actingRemotely = true;
    }

    public void doRemoteAction(final int x, final int y, final int z) {
        this.setRemoteAction(x, y, z);
        final WorldObject acted = Worldz.getApplication().getWorldManager()
                .getWorldObject(x, y, z, WorldConstants.LAYER_OBJECT);
        acted.preMoveAction(false, x, y, this.objectInv);
        acted.postMoveAction(false, x, y, this.objectInv);
        if (acted.doesChainReact()) {
            acted.chainReactionAction(x, y, z);
        }
    }

    public void doClockwiseRotate(final int r) {
        final World m = Worldz.getApplication().getWorldManager().getWorld();
        boolean b = false;
        if (this.actingRemotely) {
            b = m.rotateRadiusClockwise(this.remoteCoords[0],
                    this.remoteCoords[1], this.remoteCoords[2], r);
        } else {
            b = m.rotateRadiusClockwise(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(), r);
        }
        if (b) {
            this.findPlayerAndAdjust();
        } else {
            this.keepNextMessage();
            Messager.showMessage("Rotation failed!");
        }
    }

    public void findPlayerAndAdjust() {
        // Find the player, adjust player location
        final World m = Worldz.getApplication().getWorldManager().getWorld();
        final int w = this.plMgr.getPlayerLocationW();
        m.findPlayer();
        this.plMgr.setPlayerLocation(m.getFindResultColumn(),
                m.getFindResultRow(), m.getFindResultFloor(), w);
        this.resetViewingWindow();
        this.redrawWorld();
    }

    private void fireStepActions() {
        final World m = Worldz.getApplication().getWorldManager().getWorld();
        if (m.getPoisonPower() > 0) {
            this.poisonCounter++;
            if (this.poisonCounter >= m.getPoisonPower()) {
                // Poison
                this.poisonCounter = 0;
                m.doPoisonDamage();
            }
        }
        this.objectInv.fireStepActions();
        PartyManager.getParty().fireStepActions();
    }

    public void doCounterclockwiseRotate(final int r) {
        final World m = Worldz.getApplication().getWorldManager().getWorld();
        boolean b = false;
        if (this.actingRemotely) {
            b = m.rotateRadiusCounterclockwise(this.remoteCoords[0],
                    this.remoteCoords[1], this.remoteCoords[2], r);
        } else {
            b = m.rotateRadiusCounterclockwise(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(), r);
        }
        if (b) {
            this.findPlayerAndAdjust();
        } else {
            this.keepNextMessage();
            Messager.showMessage("Rotation failed!");
        }
    }

    public void fireArrow(final int x, final int y) {
        final ArrowTask at = new ArrowTask(x, y, this.activeArrowType);
        this.arrowActive = true;
        at.start();
    }

    public void updatePositionRelative(final int ox, final int oy) {
        this.actingRemotely = false;
        int px = this.plMgr.getPlayerLocationX();
        int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final int[] mod = this.doEffects(ox, oy);
        final int x = mod[0];
        final int y = mod[1];
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        m.tickTimers(pz);
        boolean proceed = false;
        WorldObject o = new Empty();
        WorldObject acted = new Empty();
        WorldObject groundInto = new Empty();
        WorldObject below = null;
        WorldObject previousBelow = null;
        WorldObject nextBelow = null;
        WorldObject nextAbove = null;
        WorldObject nextNextBelow = null;
        WorldObject nextNextAbove = null;
        final boolean isXNonZero = x != 0;
        final boolean isYNonZero = y != 0;
        int pullX = 0, pullY = 0, pushX = 0, pushY = 0;
        if (isXNonZero) {
            final int signX = (int) Math.signum(x);
            pushX = (Math.abs(x) + 1) * signX;
            pullX = (Math.abs(x) - 1) * signX;
        }
        if (isYNonZero) {
            final int signY = (int) Math.signum(y);
            pushY = (Math.abs(y) + 1) * signY;
            pullY = (Math.abs(y) - 1) * signY;
        }
        do {
            try {
                try {
                    o = m.getCell(px + x, py + y, pz,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    o = new Empty();
                }
                try {
                    below = m.getCell(px, py, pz, WorldConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    below = new Empty();
                }
                try {
                    nextBelow = m.getCell(px + x, py + y, pz,
                            WorldConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextBelow = new Empty();
                }
                try {
                    nextAbove = m.getCell(px + x, py + y, pz,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextAbove = new Wall();
                }
                try {
                    previousBelow = m.getCell(px - x, py - y, pz,
                            WorldConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    previousBelow = new Empty();
                }
                try {
                    nextNextBelow = m.getCell(px + 2 * x, py + 2 * y, pz,
                            WorldConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextNextBelow = new Empty();
                }
                try {
                    nextNextAbove = m.getCell(px + 2 * x, py + 2 * y, pz,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextNextAbove = new Wall();
                }
                try {
                    proceed = o.preMoveAction(true, px + x, py + y,
                            this.objectInv);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    proceed = true;
                } catch (final InfiniteRecursionException ir) {
                    proceed = false;
                }
            } catch (final NullPointerException np) {
                proceed = false;
                o = new Empty();
            }
            if (proceed) {
                this.plMgr.savePlayerLocation();
                this.vwMgr.saveViewingWindow();
                try {
                    if (this.checkSolid(x + px, y + py, this.savedWorldObject,
                            below, nextBelow, nextAbove)) {
                        if (this.delayedDecayActive) {
                            this.doDelayedDecay();
                        }
                        m.setCell(this.savedWorldObject, px, py, pz,
                                WorldConstants.LAYER_OBJECT);
                        acted = new Empty();
                        try {
                            acted = m.getCell(px - x, py - y, pz,
                                    WorldConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException ae) {
                            // Do nothing
                        }
                        if (acted.isPullable() && this.isPullInProgress()) {
                            if (!this.checkPull(x, y, pullX, pullY, acted,
                                    previousBelow, below,
                                    this.savedWorldObject)) {
                                // Pull failed - object can't move that way
                                acted.pullFailedAction(this.objectInv, x, y,
                                        pullX, pullY);
                                this.decayEffects();
                            }
                        } else if (!acted.isPullable()
                                && this.isPullInProgress()) {
                            // Pull failed - object not pullable
                            acted.pullFailedAction(this.objectInv, x, y, pullX,
                                    pullY);
                            this.decayEffects();
                        }
                        this.plMgr.offsetPlayerLocationX(x);
                        this.plMgr.offsetPlayerLocationY(y);
                        px += x;
                        py += y;
                        this.vwMgr.offsetViewingWindowLocationX(y);
                        this.vwMgr.offsetViewingWindowLocationY(x);
                        this.savedWorldObject = m.getCell(px, py, pz,
                                WorldConstants.LAYER_OBJECT);
                        groundInto = m.getCell(px, py, pz,
                                WorldConstants.LAYER_GROUND);
                        m.setCell(new Player(), px, py, pz,
                                WorldConstants.LAYER_OBJECT);
                        this.decayEffects();
                        this.redrawWorld();
                        app.getWorldManager().setDirty(true);
                        if (groundInto.overridesDefaultPostMove()) {
                            groundInto.postMoveAction(false, px, py,
                                    this.objectInv);
                            if (!this.savedWorldObject.isOfType(
                                    TypeConstants.TYPE_PASS_THROUGH)) {
                                this.savedWorldObject.postMoveAction(false, px,
                                        py, this.objectInv);
                            }
                        } else {
                            this.savedWorldObject.postMoveAction(false, px, py,
                                    this.objectInv);
                        }
                        this.fireStepActions();
                    } else {
                        acted = m.getCell(px + x, py + y, pz,
                                WorldConstants.LAYER_OBJECT);
                        if (acted.isPushable()) {
                            if (this.checkPush(x, y, pushX, pushY, acted,
                                    nextBelow, nextNextBelow, nextNextAbove)) {
                                if (this.delayedDecayActive) {
                                    this.doDelayedDecay();
                                }
                                m.setCell(this.savedWorldObject, px, py, pz,
                                        WorldConstants.LAYER_OBJECT);
                                this.plMgr.offsetPlayerLocationX(x);
                                this.plMgr.offsetPlayerLocationY(y);
                                px += x;
                                py += y;
                                this.vwMgr.offsetViewingWindowLocationX(y);
                                this.vwMgr.offsetViewingWindowLocationY(x);
                                this.savedWorldObject = m.getCell(px, py, pz,
                                        WorldConstants.LAYER_OBJECT);
                                groundInto = m.getCell(px, py, pz,
                                        WorldConstants.LAYER_GROUND);
                                m.setCell(new Player(), px, py, pz,
                                        WorldConstants.LAYER_OBJECT);
                                this.decayEffects();
                                this.redrawWorld();
                                app.getWorldManager().setDirty(true);
                                if (groundInto.overridesDefaultPostMove()) {
                                    groundInto.postMoveAction(false, px, py,
                                            this.objectInv);
                                    if (!this.savedWorldObject.isOfType(
                                            TypeConstants.TYPE_PASS_THROUGH)) {
                                        this.savedWorldObject.postMoveAction(
                                                false, px, py, this.objectInv);
                                    }
                                } else {
                                    this.savedWorldObject.postMoveAction(false,
                                            px, py, this.objectInv);
                                }
                                this.fireStepActions();
                            } else {
                                // Push failed - object can't move that way
                                acted.pushFailedAction(this.objectInv, x, y,
                                        pushX, pushY);
                                this.decayEffects();
                            }
                        } else if (acted.doesChainReact()) {
                            acted.chainReactionAction(px + x, py + y, pz);
                        } else {
                            // Move failed - object is solid in that direction
                            this.fireMoveFailedActions(px + x, py + y,
                                    this.savedWorldObject, below, nextBelow,
                                    nextAbove);
                            this.decayEffects();
                        }
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.vwMgr.restoreViewingWindow();
                    this.plMgr.restorePlayerLocation();
                    m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(),
                            this.plMgr.getPlayerLocationZ(),
                            WorldConstants.LAYER_OBJECT);
                    // Move failed - attempted to go outside the world
                    o.moveFailedAction(false, this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(), this.objectInv);
                    this.decayEffects();
                    Messager.showMessage("Can't go outside the world");
                    o = new Empty();
                }
            } else {
                // Move failed - pre-move check failed
                o.moveFailedAction(false, px + x, py + y, this.objectInv);
                this.decayEffects();
            }
            // Run custom script, if one exists
            if (o.hasCustomScript()) {
                final Script scpt = o.getCustomScript();
                ScriptRunner.runScript(scpt);
            }
        } while (proceed
                && !groundInto.hasFrictionConditionally(this.objectInv, false)
                && this.checkSolid(x, y, this.savedWorldObject, below,
                        nextBelow, nextAbove));
        this.sg.updateStats();
        if (!PartyManager.getParty().isAlive()) {
            this.gameOver();
        }
    }

    public void updatePositionRelativeNoEvents(final int x, final int y) {
        this.actingRemotely = false;
        int px = this.plMgr.getPlayerLocationX();
        int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        boolean proceed = true;
        WorldObject groundInto = new Empty();
        WorldObject below = null;
        WorldObject nextBelow = null;
        WorldObject nextAbove = null;
        do {
            try {
                try {
                    below = m.getCell(px, py, pz, WorldConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    below = new Empty();
                }
                try {
                    nextBelow = m.getCell(px + x, py + y, pz,
                            WorldConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextBelow = new Empty();
                }
                try {
                    nextAbove = m.getCell(px + x, py + y, pz,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextAbove = new Wall();
                }
            } catch (final NullPointerException np) {
                proceed = false;
            }
            if (proceed) {
                this.plMgr.savePlayerLocation();
                this.vwMgr.saveViewingWindow();
                try {
                    if (this.checkSolid(x + px, y + py, this.savedWorldObject,
                            below, nextBelow, nextAbove)) {
                        m.setCell(this.savedWorldObject, px, py, pz,
                                WorldConstants.LAYER_OBJECT);
                        this.plMgr.offsetPlayerLocationX(x);
                        this.plMgr.offsetPlayerLocationY(y);
                        px += x;
                        py += y;
                        this.vwMgr.offsetViewingWindowLocationX(y);
                        this.vwMgr.offsetViewingWindowLocationY(x);
                        this.savedWorldObject = m.getCell(px, py, pz,
                                WorldConstants.LAYER_OBJECT);
                        groundInto = m.getCell(px, py, pz,
                                WorldConstants.LAYER_GROUND);
                        m.setCell(new Player(), px, py, pz,
                                WorldConstants.LAYER_OBJECT);
                        this.redrawWorld();
                        app.getWorldManager().setDirty(true);
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.vwMgr.restoreViewingWindow();
                    this.plMgr.restorePlayerLocation();
                    m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(),
                            this.plMgr.getPlayerLocationZ(),
                            WorldConstants.LAYER_OBJECT);
                    // Move failed - attempted to go outside the world
                    Messager.showMessage("Can't go outside the world");
                }
            }
        } while (proceed
                && !groundInto.hasFrictionConditionally(this.objectInv, false)
                && this.checkSolid(x, y, this.savedWorldObject, below,
                        nextBelow, nextAbove));
        this.sg.updateStats();
        if (!PartyManager.getParty().isAlive()) {
            this.gameOver();
        }
    }

    public void backUpPlayer() {
        final World m = Worldz.getApplication().getWorldManager().getWorld();
        final int pz = this.plMgr.getPlayerLocationZ();
        this.plMgr.restorePlayerLocation();
        this.vwMgr.restoreViewingWindow();
        final int opx = this.plMgr.getPlayerLocationX();
        final int opy = this.plMgr.getPlayerLocationY();
        this.savedWorldObject = m.getCell(opx, opy, pz,
                WorldConstants.LAYER_OBJECT);
        m.setCell(new Player(), opx, opy, pz, WorldConstants.LAYER_OBJECT);
        this.redrawWorld();
    }

    private boolean checkSolid(final int x, final int y,
            final WorldObject inside, final WorldObject below,
            final WorldObject nextBelow, final WorldObject nextAbove) {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final boolean insideSolid = inside.isConditionallyDirectionallySolid(
                false, x - px, y - py, this.objectInv);
        final boolean belowSolid = below.isConditionallyDirectionallySolid(
                false, x - px, y - py, this.objectInv);
        final boolean nextBelowSolid = nextBelow
                .isConditionallyDirectionallySolid(true, x - px, y - py,
                        this.objectInv);
        final boolean nextAboveSolid = nextAbove
                .isConditionallyDirectionallySolid(true, x - px, y - py,
                        this.objectInv);
        if (insideSolid || belowSolid || nextBelowSolid || nextAboveSolid) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkSolidAbsolute(final WorldObject inside,
            final WorldObject below, final WorldObject nextBelow,
            final WorldObject nextAbove) {
        final boolean insideSolid = inside.isConditionallySolid(this.objectInv);
        final boolean belowSolid = below.isConditionallySolid(this.objectInv);
        final boolean nextBelowSolid = nextBelow
                .isConditionallySolid(this.objectInv);
        final boolean nextAboveSolid = nextAbove
                .isConditionallySolid(this.objectInv);
        if (insideSolid || belowSolid || nextBelowSolid || nextAboveSolid) {
            return false;
        } else {
            return true;
        }
    }

    private void fireMoveFailedActions(final int x, final int y,
            final WorldObject inside, final WorldObject below,
            final WorldObject nextBelow, final WorldObject nextAbove) {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final boolean insideSolid = inside.isConditionallyDirectionallySolid(
                false, x - px, y - py, this.objectInv);
        final boolean belowSolid = below.isConditionallyDirectionallySolid(
                false, x - px, y - py, this.objectInv);
        final boolean nextBelowSolid = nextBelow
                .isConditionallyDirectionallySolid(true, x - px, y - py,
                        this.objectInv);
        final boolean nextAboveSolid = nextAbove
                .isConditionallyDirectionallySolid(true, x - px, y - py,
                        this.objectInv);
        if (insideSolid) {
            inside.moveFailedAction(false, x, y, this.objectInv);
        }
        if (belowSolid) {
            below.moveFailedAction(false, x, y, this.objectInv);
        }
        if (nextBelowSolid) {
            nextBelow.moveFailedAction(false, x, y, this.objectInv);
        }
        if (nextAboveSolid) {
            nextAbove.moveFailedAction(false, x, y, this.objectInv);
        }
    }

    private boolean checkPush(final int x, final int y, final int pushX,
            final int pushY, final WorldObject acted,
            final WorldObject nextBelow, final WorldObject nextNextBelow,
            final WorldObject nextNextAbove) {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final boolean nextBelowAccept = nextBelow.isPushableOut();
        final boolean nextNextBelowAccept = nextNextBelow.isPushableInto();
        final boolean nextNextAboveAccept = nextNextAbove.isPushableInto();
        if (nextBelowAccept && nextNextBelowAccept && nextNextAboveAccept) {
            nextBelow.pushOutAction(this.objectInv, acted, px + pushX,
                    py + pushY, pz);
            acted.pushAction(this.objectInv, nextNextAbove, x, y, pushX, pushY);
            nextNextAbove.pushIntoAction(this.objectInv, acted, px + pushX,
                    py + pushY, pz);
            nextNextBelow.pushIntoAction(this.objectInv, acted, px + pushX,
                    py + pushY, pz);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPull(final int x, final int y, final int pullX,
            final int pullY, final WorldObject acted,
            final WorldObject previousBelow, final WorldObject below,
            final WorldObject above) {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final boolean previousBelowAccept = previousBelow.isPullableOut();
        final boolean belowAccept = below.isPullableInto();
        final boolean aboveAccept = above.isPullableInto();
        if (previousBelowAccept && belowAccept && aboveAccept) {
            previousBelow.pullOutAction(this.objectInv, acted, px - pullX,
                    py - pullY, pz);
            acted.pullAction(this.objectInv, above, x, y, pullX, pullY);
            above.pullIntoAction(this.objectInv, acted, px - pullX, py - pullY,
                    pz);
            below.pullIntoAction(this.objectInv, acted, px - pullX, py - pullY,
                    pz);
            return true;
        } else {
            return false;
        }
    }

    public void updatePushedPosition(final int x, final int y, final int pushX,
            final int pushY, final WorldObject o) {
        final int xInc = (int) Math.signum(x), yInc = (int) Math.signum(y);
        int cumPushX = pushX, cumPushY = pushY, cumX = x, cumY = y;
        final Application app = Worldz.getApplication();
        final WorldManager mm = app.getWorldManager();
        WorldObject there = mm.getWorldObject(
                this.plMgr.getPlayerLocationX() + cumX,
                this.plMgr.getPlayerLocationY() + cumY,
                this.plMgr.getPlayerLocationZ(), WorldConstants.LAYER_GROUND);
        if (there != null) {
            do {
                this.movePushedObjectPosition(cumX, cumY, cumPushX, cumPushY, o,
                        there);
                cumX += xInc;
                cumY += yInc;
                cumPushX += xInc;
                cumPushY += yInc;
                there = mm.getWorldObject(
                        this.plMgr.getPlayerLocationX() + cumX,
                        this.plMgr.getPlayerLocationY() + cumY,
                        this.plMgr.getPlayerLocationZ(),
                        WorldConstants.LAYER_GROUND);
                if (there == null) {
                    break;
                }
            } while (!there.hasFrictionConditionally(this.objectInv, true));
        }
    }

    private void movePushedObjectPosition(final int x, final int y,
            final int pushX, final int pushY, final WorldObject o,
            final WorldObject g) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            m.setCell(new Empty(), this.plMgr.getPlayerLocationX() + x,
                    this.plMgr.getPlayerLocationY() + y,
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_OBJECT);
            m.setCell(o, this.plMgr.getPlayerLocationX() + pushX,
                    this.plMgr.getPlayerLocationY() + pushY,
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_OBJECT);
            if (g.overridesDefaultPostMove()) {
                g.postMoveAction(false, this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(), this.objectInv);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePulledPosition(final int x, final int y, final int pullX,
            final int pullY, final WorldObject o) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            m.setCell(new Empty(), this.plMgr.getPlayerLocationX() - x,
                    this.plMgr.getPlayerLocationY() - y,
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_OBJECT);
            m.setCell(o, this.plMgr.getPlayerLocationX() - pullX,
                    this.plMgr.getPlayerLocationY() - pullY,
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePushedIntoPositionAbsolute(final int x, final int y,
            final int z, final int x2, final int y2, final int z2,
            final GenericMovableObject pushedInto, final WorldObject source) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            if (!m.getCell(x, y, z, WorldConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                final WorldObject saved = m.getCell(x, y, z,
                        WorldConstants.LAYER_OBJECT);
                m.setCell(pushedInto, x, y, z, WorldConstants.LAYER_OBJECT);
                m.setCell(source, x2, y2, z2, WorldConstants.LAYER_OBJECT);
                saved.pushIntoAction(this.objectInv, pushedInto, x2, y2,
                        z2 - 1);
                this.redrawWorld();
                app.getWorldManager().setDirty(true);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.setCell(new Empty(), x2, y2, z2, WorldConstants.LAYER_OBJECT);
        }
    }

    public boolean tryUpdatePositionRelative(final int x, final int y) {
        try {
            final Application app = Worldz.getApplication();
            final World m = app.getWorldManager().getWorld();
            final WorldObject below = m.getCell(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_GROUND);
            final WorldObject nextBelow = m.getCell(
                    this.plMgr.getPlayerLocationX() + x,
                    this.plMgr.getPlayerLocationY() + y,
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_GROUND);
            final WorldObject nextAbove = m.getCell(
                    this.plMgr.getPlayerLocationX() + x,
                    this.plMgr.getPlayerLocationY() + y,
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_OBJECT);
            return this.checkSolid(x, y, this.savedWorldObject, below,
                    nextBelow, nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public boolean tryUpdatePositionAbsolute(final int x, final int y,
            final int z) {
        try {
            final Application app = Worldz.getApplication();
            final World m = app.getWorldManager().getWorld();
            final WorldObject below = m.getCell(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_GROUND);
            final WorldObject nextBelow = m.getCell(x, y, z,
                    WorldConstants.LAYER_GROUND);
            final WorldObject nextAbove = m.getCell(x, y, z,
                    WorldConstants.LAYER_OBJECT);
            return this.checkSolidAbsolute(this.savedWorldObject, below,
                    nextBelow, nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public void updatePositionAbsolute(final int x, final int y, final int z) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            m.getCell(x, y, z, WorldConstants.LAYER_OBJECT).preMoveAction(true,
                    x, y, this.objectInv);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
        this.plMgr.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, WorldConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                m.setCell(this.savedWorldObject,
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        WorldConstants.LAYER_OBJECT);
                this.plMgr.setPlayerLocation(x, y, z,
                        this.plMgr.getPlayerLocationW());
                this.vwMgr.setViewingWindowLocationX(
                        this.plMgr.getPlayerLocationY()
                                - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(
                        this.plMgr.getPlayerLocationX()
                                - this.vwMgr.getOffsetFactorY());
                this.savedWorldObject = m.getCell(
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        WorldConstants.LAYER_OBJECT);
                m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        WorldConstants.LAYER_OBJECT);
                this.redrawWorld();
                app.getWorldManager().setDirty(true);
                this.savedWorldObject.postMoveAction(false, x, y,
                        this.objectInv);
                // Run custom script, if one exists
                if (this.savedWorldObject.hasCustomScript()) {
                    final Script scpt = this.savedWorldObject.getCustomScript();
                    ScriptRunner.runScript(scpt);
                }
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_OBJECT);
            Messager.showMessage("Can't go outside the world");
        } catch (final NullPointerException np) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_OBJECT);
            Messager.showMessage("Can't go outside the world");
        }
    }

    public void updatePositionAbsoluteNoEvents(final int x, final int y,
            final int z, final int w) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        this.plMgr.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, WorldConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                m.setCell(this.savedWorldObject,
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        WorldConstants.LAYER_OBJECT);
                this.plMgr.setPlayerLocation(x, y, z, w);
                this.vwMgr.setViewingWindowLocationX(
                        this.plMgr.getPlayerLocationY()
                                - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(
                        this.plMgr.getPlayerLocationX()
                                - this.vwMgr.getOffsetFactorY());
                this.savedWorldObject = m.getCell(
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        WorldConstants.LAYER_OBJECT);
                m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        WorldConstants.LAYER_OBJECT);
                this.redrawWorld();
                app.getWorldManager().setDirty(true);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_OBJECT);
            Messager.showMessage("Can't go outside the world");
        } catch (final NullPointerException np) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    WorldConstants.LAYER_OBJECT);
            Messager.showMessage("Can't go outside the world");
        }
    }

    public void redrawWorld() {
        // Draw the world, if it is visible
        if (this.outputFrame.isVisible()) {
            // Rebuild draw grid
            final EmptyBorder eb = new EmptyBorder(0, 0, 0, 0);
            this.outputPane.removeAll();
            for (int x = 0; x < this.vwMgr.getViewingWindowSizeX(); x++) {
                for (int y = 0; y < this.vwMgr.getViewingWindowSizeY(); y++) {
                    this.drawGrid[x][y] = new JLabel();
                    // Fix to make draw grid line up properly
                    this.drawGrid[x][y].setBorder(eb);
                    this.outputPane.add(this.drawGrid[x][y]);
                }
            }
            this.redrawWorldNoRebuild();
        }
    }

    public void redrawWorldNoRebuild() {
        // Draw the world, if it is visible
        if (this.outputFrame.isVisible()) {
            final Application app = Worldz.getApplication();
            int x, y, u, v;
            int xFix, yFix;
            boolean visible;
            u = this.plMgr.getPlayerLocationX();
            v = this.plMgr.getPlayerLocationY();
            for (x = this.vwMgr.getViewingWindowLocationX(); x <= this.vwMgr
                    .getLowerRightViewingWindowLocationX(); x++) {
                for (y = this.vwMgr.getViewingWindowLocationY(); y <= this.vwMgr
                        .getLowerRightViewingWindowLocationY(); y++) {
                    xFix = x - this.vwMgr.getViewingWindowLocationX();
                    yFix = y - this.vwMgr.getViewingWindowLocationY();
                    visible = app.getWorldManager().getWorld()
                            .isSquareVisible(u, v, y, x);
                    try {
                        if (visible) {
                            final String name1 = app.getWorldManager()
                                    .getWorld()
                                    .getCell(y, x,
                                            this.plMgr.getPlayerLocationZ(),
                                            WorldConstants.LAYER_GROUND)
                                    .gameRenderHook(y, x,
                                            this.plMgr.getPlayerLocationZ());
                            final String name2 = app.getWorldManager()
                                    .getWorld()
                                    .getCell(y, x,
                                            this.plMgr.getPlayerLocationZ(),
                                            WorldConstants.LAYER_OBJECT)
                                    .gameRenderHook(y, x,
                                            this.plMgr.getPlayerLocationZ());
                            this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                    .getCompositeImage(name1, name2));
                        } else {
                            this.drawGrid[xFix][yFix].setIcon(
                                    GraphicsManager.getImage("Darkness"));
                        }
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                .getImage(new EmptyVoid().gameRenderHook(y, x,
                                        this.plMgr.getPlayerLocationZ())));
                    } catch (final NullPointerException np) {
                        this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                .getImage(new EmptyVoid().gameRenderHook(y, x,
                                        this.plMgr.getPlayerLocationZ())));
                    }
                }
            }
            if (this.knm) {
                this.knm = false;
            } else {
                this.setStatusMessage(" ");
            }
            this.outputFrame.pack();
        }
    }

    void redrawOneSquare(final int x, final int y, final boolean useDelay,
            final String name3) {
        // Draw the square, if the world is visible
        final Application app = Worldz.getApplication();
        if (this.outputFrame.isVisible()) {
            int xFix, yFix;
            boolean visible;
            xFix = y - this.vwMgr.getViewingWindowLocationX();
            yFix = x - this.vwMgr.getViewingWindowLocationY();
            visible = app.getWorldManager().getWorld().isSquareVisible(
                    this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(), x, y);
            try {
                if (visible) {
                    final String name1 = app.getWorldManager().getWorld()
                            .getCell(x, y, this.plMgr.getPlayerLocationZ(),
                                    WorldConstants.LAYER_GROUND)
                            .gameRenderHook(x, y,
                                    this.plMgr.getPlayerLocationZ());
                    final String name2 = app.getWorldManager().getWorld()
                            .getCell(x, y, this.plMgr.getPlayerLocationZ(),
                                    WorldConstants.LAYER_OBJECT)
                            .gameRenderHook(x, y,
                                    this.plMgr.getPlayerLocationZ());
                    this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                            .getVirtualCompositeImage(name1, name2, name3));
                } else {
                    this.drawGrid[xFix][yFix]
                            .setIcon(GraphicsManager.getImage("Darkness"));
                }
                this.drawGrid[xFix][yFix].repaint();
            } catch (final ArrayIndexOutOfBoundsException ae) {
                // Do nothing
            } catch (final NullPointerException np) {
                // Do nothing
            }
            this.outputFrame.pack();
            if (useDelay) {
                // Delay, for animation purposes
                try {
                    Thread.sleep(60);
                } catch (final InterruptedException ie) {
                    // Ignore
                }
            }
        }
    }

    public void updateStats() {
        // Update stats
        this.sg.updateStats();
        // Check for game over
        if (!PartyManager.getParty().isAlive()) {
            this.gameOver();
        }
    }

    public void resetViewingWindowAndPlayerLocation() {
        this.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        this.vwMgr.setViewingWindowLocationX(this.plMgr.getPlayerLocationY()
                - this.vwMgr.getOffsetFactorX());
        this.vwMgr.setViewingWindowLocationY(this.plMgr.getPlayerLocationX()
                - this.vwMgr.getOffsetFactorY());
    }

    public void resetPlayerLocation() {
        this.resetPlayerLocation(0);
    }

    public void resetPlayerLocation(final int level) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        m.switchLevel(level);
        this.plMgr.setPlayerLocation(m.getStartColumn(), m.getStartRow(),
                m.getStartFloor(), level);
    }

    public void resetGameState() {
        this.deactivateAllEffects();
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        app.getWorldManager().setDirty(false);
        m.restore();
        this.setSavedGameFlag(false);
        this.decay();
        this.objectInv = new ObjectInventory();
        this.savedObjectInv = new ObjectInventory();
        final int startW = m.getStartLevel();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            m.switchLevel(startW);
            this.plMgr.setPlayerLocation(m.getStartColumn(), m.getStartRow(),
                    m.getStartFloor(), startW);
            m.save();
        }
    }

    public void solvedWorld() {
        final World m = Worldz.getApplication().getWorldManager().getWorld();
        Messager.showTitledDialog(m.getWorldEndMessage(), m.getWorldTitle());
        this.exitGame();
    }

    void exitGame() {
        this.stateChanged = true;
        this.deactivateAllEffects();
        final Application app = Worldz.getApplication();
        app.setInGame(false);
        final World m = app.getWorldManager().getWorld();
        // Restore the world
        m.restoreLevel();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            PartyManager.getParty().healPartyFully();
            this.resetViewingWindowAndPlayerLocation();
        } else {
            app.getWorldManager().setLoaded(false);
        }
        // Wipe the inventory
        this.objectInv = new ObjectInventory();
        // Reset saved game flag
        this.savedGameFlag = false;
        app.getWorldManager().setDirty(false);
        // Exit game
        this.hideOutput();
        app.getGUIManager().showGUI();
    }

    private void gameOver() {
        SoundManager.playSound("gameover");
        Messager.showDialog("You have died - Game Over!");
        this.exitGame();
    }

    public JFrame getOutputFrame() {
        return this.outputFrame;
    }

    public void decay() {
        if (this.actingRemotely) {
            Worldz.getApplication().getWorldManager().getWorld().setCell(
                    new Empty(), this.remoteCoords[0], this.remoteCoords[1],
                    this.remoteCoords[2], WorldConstants.LAYER_OBJECT);
        } else {
            this.savedWorldObject = new Empty();
        }
    }

    public void decayTo(final WorldObject decay) {
        if (this.actingRemotely) {
            Worldz.getApplication().getWorldManager().getWorld().setCell(decay,
                    this.remoteCoords[0], this.remoteCoords[1],
                    this.remoteCoords[2], WorldConstants.LAYER_OBJECT);
        } else {
            this.savedWorldObject = decay;
        }
    }

    private void doDelayedDecay() {
        if (this.actingRemotely) {
            Worldz.getApplication().getWorldManager().getWorld().setCell(
                    this.delayedDecayObject, this.remoteCoords[0],
                    this.remoteCoords[1], this.remoteCoords[2],
                    WorldConstants.LAYER_OBJECT);
        } else {
            this.savedWorldObject = this.delayedDecayObject;
        }
        this.delayedDecayActive = false;
    }

    public void delayedDecayTo(final WorldObject obj) {
        this.delayedDecayActive = true;
        this.delayedDecayObject = obj;
    }

    public void morph(final WorldObject morphInto, final int x, final int y,
            final int z) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            m.setCell(morphInto, x, y, z, morphInto.getLayer());
            this.redrawWorldNoRebuild();
            app.getWorldManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morph(final WorldObject morphInto, final int x, final int y,
            final int z, final String msg) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            m.setCell(morphInto, x, y, z, morphInto.getLayer());
            Messager.showMessage(msg);
            this.keepNextMessage();
            this.redrawWorldNoRebuild();
            app.getWorldManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morph(final WorldObject morphInto, final int x, final int y,
            final int z, final int e) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            m.setCell(morphInto, x, y, z, e);
            this.redrawWorldNoRebuild();
            app.getWorldManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morphOther(final WorldObject morphInto, final int x,
            final int y, final int e) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        try {
            m.setCell(morphInto, this.plMgr.getPlayerLocationX() + x,
                    this.plMgr.getPlayerLocationY() + y,
                    this.plMgr.getPlayerLocationZ(), e);
            this.redrawWorldNoRebuild();
            app.getWorldManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void keepNextMessage() {
        this.knm = true;
    }

    public void showEquipmentDialog() {
        final PartyMember member = PartyManager.getParty().pickOnePartyMember();
        final String[] equipString = member.getItems()
                .generateEquipmentStringArray();
        Messager.showInputDialog("Equipment", "Equipment", equipString,
                equipString[0]);
    }

    public void showItemInventoryDialog() {
        final String[] invString = PartyManager.getParty().getLeader()
                .getItems().generateInventoryStringArray();
        Messager.showInputDialog("Item Inventory", "Item Inventory", invString,
                invString[0]);
    }

    public void showObjectInventoryDialog() {
        final String[] invString = this.objectInv
                .generateInventoryStringArray();
        Messager.showInputDialog("Object Inventory", "Object Inventory",
                invString, invString[0]);
    }

    public void showUseDialog() {
        int x;
        final WorldObjectList list = Worldz.getApplication().getObjects();
        final WorldObject[] choices = list.getAllUsableObjects();
        final String[] userChoices = this.objectInv.generateUseStringArray();
        final String result = Messager.showInputDialog("Use which item?",
                "Worldz", userChoices, userChoices[this.lastUsedObjectIndex]);
        try {
            for (x = 0; x < choices.length; x++) {
                if (result.equals(userChoices[x])) {
                    this.lastUsedObjectIndex = x;
                    this.objectBeingUsed = choices[x];
                    if (this.objectInv.getUses(this.objectBeingUsed) == 0) {
                        Messager.showMessage(
                                "That item has no more uses left.");
                        this.setUsingAnItem(false);
                    } else if (!this.objectBeingUsed
                            .isOfType(TypeConstants.TYPE_BOW)) {
                        Messager.showMessage("Click to set target");
                    } else {
                        this.useItemHandler(0, 0);
                        this.setUsingAnItem(false);
                    }
                    return;
                }
            }
        } catch (final NullPointerException np) {
            this.setUsingAnItem(false);
        }
    }

    public ObjectInventory getObjectInventory() {
        return this.objectInv;
    }

    public void setObjectInventory(final ObjectInventory newObjectInventory) {
        this.objectInv = newObjectInventory;
    }

    public void useItemHandler(final int x, final int y) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        final int xOffset = this.vwMgr.getViewingWindowLocationX()
                - this.vwMgr.getOffsetFactorX();
        final int yOffset = this.vwMgr.getViewingWindowLocationY()
                - this.vwMgr.getOffsetFactorY();
        final int destX = x / GraphicsManager.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / GraphicsManager.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = this.plMgr.getPlayerLocationZ();
        if (this.usingAnItem() && app.getMode() == Application.STATUS_GAME) {
            if (!this.objectBeingUsed.isOfType(TypeConstants.TYPE_BOW)) {
                final boolean visible = app.getWorldManager().getWorld()
                        .isSquareVisible(this.plMgr.getPlayerLocationX(),
                                this.plMgr.getPlayerLocationY(), destX, destY);
                try {
                    final WorldObject target = m.getCell(destX, destY, destZ,
                            WorldConstants.LAYER_OBJECT);
                    final String name = this.objectBeingUsed.getName();
                    if ((target.isSolid() || !visible)
                            && name.equals(new TeleportWand().getName())) {
                        this.setUsingAnItem(false);
                        Messager.showMessage("Can't teleport there");
                    }
                    if (target.getName().equals(new Player().getName())) {
                        this.setUsingAnItem(false);
                        Messager.showMessage("Don't aim at yourself!");
                    }
                    if (!target.isDestroyable()
                            && name.equals(new AnnihilationWand().getName())) {
                        this.setUsingAnItem(false);
                        Messager.showMessage("Can't destroy that");
                    }
                    if (!target.isDestroyable()
                            && name.equals(new WallMakingWand().getName())) {
                        this.setUsingAnItem(false);
                        Messager.showMessage("Can't create a wall there");
                    }
                    if ((!target.isDestroyable()
                            || !target.isOfType(TypeConstants.TYPE_WALL))
                            && name.equals(new WallBreakingWand().getName())) {
                        this.setUsingAnItem(false);
                        Messager.showMessage("Aim at a wall");
                    }
                    if ((!target.isDestroyable()
                            || !target.isOfType(TypeConstants.TYPE_TRAP))
                            && name.equals(new DisarmTrapWand().getName())) {
                        this.setUsingAnItem(false);
                        Messager.showMessage("Aim at a trap");
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.setUsingAnItem(false);
                    Messager.showMessage("Aim within the world");
                } catch (final NullPointerException np) {
                    this.setUsingAnItem(false);
                }
            }
            if (this.usingAnItem()) {
                this.objectInv.use(this.objectBeingUsed, destX, destY, destZ);
                this.redrawWorld();
            }
        }
    }

    public void controllableTeleport() {
        this.teleporting = true;
        Messager.showMessage("Click to set destination");
    }

    void controllableTeleportHandler(final int x, final int y) {
        if (this.teleporting) {
            final int xOffset = this.vwMgr.getViewingWindowLocationX()
                    - this.vwMgr.getOffsetFactorX();
            final int yOffset = this.vwMgr.getViewingWindowLocationY()
                    - this.vwMgr.getOffsetFactorY();
            final int destX = x / GraphicsManager.getGraphicSize()
                    + this.vwMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            final int destY = y / GraphicsManager.getGraphicSize()
                    + this.vwMgr.getViewingWindowLocationY() + xOffset
                    - yOffset;
            final int destZ = this.plMgr.getPlayerLocationZ();
            this.updatePositionAbsolute(destX, destY, destZ);
            if (Worldz.getApplication().getPrefsManager()
                    .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
                SoundManager.playSound("teleport");
            }
            this.teleporting = false;
        }
    }

    public void identifyObject(final int x, final int y) {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        final int xOffset = this.vwMgr.getViewingWindowLocationX()
                - this.vwMgr.getOffsetFactorX();
        final int yOffset = this.vwMgr.getViewingWindowLocationY()
                - this.vwMgr.getOffsetFactorY();
        final int destX = x / GraphicsManager.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / GraphicsManager.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = this.plMgr.getPlayerLocationZ();
        try {
            final WorldObject target1 = m.getCell(destX, destY, destZ,
                    WorldConstants.LAYER_GROUND);
            final WorldObject target2 = m.getCell(destX, destY, destZ,
                    WorldConstants.LAYER_OBJECT);
            target1.determineCurrentAppearance(destX, destY, destZ);
            target2.determineCurrentAppearance(destX, destY, destZ);
            final String gameName1 = target1.getGameName();
            final String gameName2 = target2.getGameName();
            Messager.showMessage(gameName2 + " on " + gameName1);
            if (Worldz.getApplication().getPrefsManager()
                    .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
                WorldObject.playIdentifySound();
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            final EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(destX, destY, destZ);
            Messager.showMessage(ev.getGameName());
            if (Worldz.getApplication().getPrefsManager()
                    .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
                WorldObject.playIdentifySound();
            }
        }
    }

    public void loadGameHook(final DataReader worldFile,
            final int formatVersion) throws IOException {
        this.objectInv = ObjectInventory.readInventory(worldFile,
                formatVersion);
        this.savedObjectInv = ObjectInventory.readInventory(worldFile,
                formatVersion);
    }

    public void saveGameHook(final DataWriter worldFile) throws IOException {
        this.objectInv.writeInventory(worldFile);
        this.savedObjectInv.writeInventory(worldFile);
    }

    public void playWorld() {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        if (app.getWorldManager().getLoaded()) {
            app.getGUIManager().hideGUI();
            app.setInGame(true);
            if (this.stateChanged) {
                // Initialize only if the world state has changed
                this.poisonCounter = 0;
                app.getWorldManager().getWorld().switchLevel(
                        app.getWorldManager().getWorld().getStartLevel());
                this.savedWorldObject = new Empty();
                this.stateChanged = false;
            }
            // Make sure message area is attached to the border pane
            this.borderPane.removeAll();
            this.borderPane.add(this.outputPane, BorderLayout.CENTER);
            this.borderPane.add(this.messageLabel, BorderLayout.SOUTH);
            this.borderPane.add(this.sg.getStatsPane(), BorderLayout.EAST);
            Messager.showTitledDialog(m.getWorldStartMessage(),
                    m.getWorldTitle());
            this.showOutput();
            this.sg.updateStats();
            if (!PartyManager.getParty().isAlive()) {
                this.gameOver();
            }
            this.redrawWorld();
        } else {
            Messager.showDialog("No World Opened");
        }
    }

    public void showOutput() {
        final Application app = Worldz.getApplication();
        app.getMenuManager().setGameMenus();
        if (app.getPrefsManager()
                .getMusicEnabled(PreferencesManager.MUSIC_EXPLORING)) {
            if (!MusicManager.isMusicPlaying()) {
                MusicManager.playMusic("exploring");
            }
        }
        this.outputFrame.setVisible(true);
        this.outputFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
    }

    public void hideOutput() {
        if (Worldz.getApplication().getPrefsManager()
                .getMusicEnabled(PreferencesManager.MUSIC_EXPLORING)) {
            if (MusicManager.isMusicPlaying()) {
                MusicManager.stopMusic();
            }
        }
        if (this.outputFrame != null) {
            this.outputFrame.setVisible(false);
        }
    }

    private void setUpGUI() {
        this.objectInv = new ObjectInventory();
        this.handler = new EventHandler();
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        this.outputFrame = new JFrame("Worldz");
        final Image iconlogo = Worldz.getApplication().getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.outputPane = new Container();
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.outputPane
                .setLayout(new GridLayout(this.vwMgr.getViewingWindowSizeX(),
                        this.vwMgr.getViewingWindowSizeY()));
        this.outputFrame.setResizable(false);
        this.outputFrame.addKeyListener(this.handler);
        this.outputFrame.addWindowListener(this.handler);
        this.outputPane.addMouseListener(this.handler);
        this.drawGrid = new JLabel[this.vwMgr
                .getViewingWindowSizeX()][this.vwMgr.getViewingWindowSizeY()];
        for (int x = 0; x < this.vwMgr.getViewingWindowSizeX(); x++) {
            for (int y = 0; y < this.vwMgr.getViewingWindowSizeY(); y++) {
                this.drawGrid[x][y] = new JLabel();
                // Mac OS X-specific fix to make draw grid line up properly
                if (System.getProperty("os.name").startsWith("Mac OS X")) {
                    this.drawGrid[x][y].setBorder(new EmptyBorder(0, 0, 0, 0));
                }
                this.outputPane.add(this.drawGrid[x][y]);
            }
        }
        this.sg.updateGUI();
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.SOUTH);
        this.borderPane.add(this.sg.getStatsPane(), BorderLayout.EAST);
    }

    private class EventHandler
            implements KeyListener, WindowListener, MouseListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            if (!GameManager.this.arrowActive) {
                if (!Worldz.getApplication().getPrefsManager().oneMove()) {
                    if (e.isAltDown()) {
                        this.handleArrows(e);
                    } else {
                        this.handleMovement(e);
                    }
                }
            }
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            if (!GameManager.this.arrowActive) {
                if (Worldz.getApplication().getPrefsManager().oneMove()) {
                    if (e.isAltDown()) {
                        this.handleArrows(e);
                    } else {
                        this.handleMovement(e);
                    }
                }
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            // Do nothing
        }

        public void handleMovement(final KeyEvent e) {
            try {
                final GameManager gm = GameManager.this;
                final int keyCode = e.getKeyCode();
                if (e.isShiftDown()) {
                    gm.setPullInProgress(true);
                }
                switch (keyCode) {
                case KeyEvent.VK_NUMPAD4:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(-1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD2:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_X:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(0, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD6:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD8:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(0, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD7:
                case KeyEvent.VK_Q:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(-1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD9:
                case KeyEvent.VK_E:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD3:
                case KeyEvent.VK_C:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(1, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD1:
                case KeyEvent.VK_Z:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(-1, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD5:
                case KeyEvent.VK_S:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.updatePositionRelative(0, 0);
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    if (gm.usingAnItem()) {
                        gm.setUsingAnItem(false);
                        Messager.showMessage(" ");
                    } else if (gm.isTeleporting()) {
                        gm.teleporting = false;
                        Messager.showMessage(" ");
                    }
                    break;
                default:
                    break;
                }
                if (gm.isPullInProgress()) {
                    gm.setPullInProgress(false);
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }

        public void handleArrows(final KeyEvent e) {
            try {
                final GameManager gm = GameManager.this;
                final int keyCode = e.getKeyCode();
                if (e.isShiftDown()) {
                    gm.setPullInProgress(true);
                }
                switch (keyCode) {
                case KeyEvent.VK_NUMPAD4:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(-1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD2:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_X:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(0, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD6:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(1, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD8:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(0, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD7:
                case KeyEvent.VK_Q:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(-1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD9:
                case KeyEvent.VK_E:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(1, -1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD3:
                case KeyEvent.VK_C:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(1, 1);
                    }
                    break;
                case KeyEvent.VK_NUMPAD1:
                case KeyEvent.VK_Z:
                    if (!gm.usingAnItem() && !gm.isTeleporting()) {
                        gm.fireArrow(-1, 1);
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    if (gm.usingAnItem()) {
                        gm.setUsingAnItem(false);
                        Messager.showMessage(" ");
                    } else if (gm.isTeleporting()) {
                        gm.teleporting = false;
                        Messager.showMessage(" ");
                    }
                    break;
                default:
                    break;
                }
                if (gm.isPullInProgress()) {
                    gm.setPullInProgress(false);
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }

        // Handle windows
        @Override
        public void windowActivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosed(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent we) {
            try {
                final Application app = Worldz.getApplication();
                boolean success = false;
                int status = 0;
                if (app.getWorldManager().getDirty()) {
                    status = app.getWorldManager().showSaveDialog();
                    if (status == JOptionPane.YES_OPTION) {
                        success = app.getWorldManager().saveWorld();
                        if (success) {
                            app.getGameManager().exitGame();
                        }
                    } else if (status == JOptionPane.NO_OPTION) {
                        app.getGameManager().exitGame();
                    }
                } else {
                    app.getGameManager().exitGame();
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }

        @Override
        public void windowDeactivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowOpened(final WindowEvent we) {
            // Do nothing
        }

        // handle mouse
        @Override
        public void mousePressed(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseReleased(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            try {
                final GameManager gm = GameManager.this;
                if (gm.usingAnItem()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.useItemHandler(x, y);
                    gm.setUsingAnItem(false);
                } else if (e.isShiftDown()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.identifyObject(x, y);
                } else if (gm.isTeleporting()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.controllableTeleportHandler(x, y);
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            // Do nothing
        }
    }
}
