/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
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
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.puttysoftware.mazemode.Application;
import com.puttysoftware.mazemode.CommonDialogs;
import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.generic.ArrowTypeConstants;
import com.puttysoftware.mazemode.generic.GenericBow;
import com.puttysoftware.mazemode.generic.GenericCharacter;
import com.puttysoftware.mazemode.generic.GenericMovableObject;
import com.puttysoftware.mazemode.generic.MazeObject;
import com.puttysoftware.mazemode.generic.MazeObjectList;
import com.puttysoftware.mazemode.generic.TypeConstants;
import com.puttysoftware.mazemode.maze.Maze;
import com.puttysoftware.mazemode.maze.MazeConstants;
import com.puttysoftware.mazemode.maze.MazeManager;
import com.puttysoftware.mazemode.maze.effects.MazeEffectConstants;
import com.puttysoftware.mazemode.maze.effects.MazeEffectManager;
import com.puttysoftware.mazemode.objects.AnnihilationWand;
import com.puttysoftware.mazemode.objects.Bow;
import com.puttysoftware.mazemode.objects.DarkGem;
import com.puttysoftware.mazemode.objects.DarkWand;
import com.puttysoftware.mazemode.objects.DisarmTrapWand;
import com.puttysoftware.mazemode.objects.Empty;
import com.puttysoftware.mazemode.objects.EmptyVoid;
import com.puttysoftware.mazemode.objects.FinishMakingWand;
import com.puttysoftware.mazemode.objects.FireAmulet;
import com.puttysoftware.mazemode.objects.HotBoots;
import com.puttysoftware.mazemode.objects.IceAmulet;
import com.puttysoftware.mazemode.objects.LightGem;
import com.puttysoftware.mazemode.objects.LightWand;
import com.puttysoftware.mazemode.objects.PasswallBoots;
import com.puttysoftware.mazemode.objects.Player;
import com.puttysoftware.mazemode.objects.SlipperyBoots;
import com.puttysoftware.mazemode.objects.SunStone;
import com.puttysoftware.mazemode.objects.TeleportWand;
import com.puttysoftware.mazemode.objects.Wall;
import com.puttysoftware.mazemode.objects.WallBreakingWand;
import com.puttysoftware.mazemode.objects.WallMakingWand;
import com.puttysoftware.mazemode.prefs.PreferencesManager;
import com.puttysoftware.mazemode.resourcemanagers.GraphicsManager;
import com.puttysoftware.mazemode.resourcemanagers.MusicConstants;
import com.puttysoftware.mazemode.resourcemanagers.MusicManager;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class GameManager implements MazeEffectConstants {
    // Fields
    private JFrame outputFrame;
    private Container outputPane, borderPane, progressPane;
    private JLabel messageLabel;
    private JProgressBar autoFinishProgress;
    private MazeObject savedMazeObject, objectBeingUsed;
    private GenericBow activeBow;
    private EventHandler handler;
    private ObjectInventory objectInv, savedObjectInv;
    private boolean pullInProgress;
    private boolean using;
    private int lastUsedObjectIndex;
    private int lastUsedBowIndex;
    private boolean knm;
    private boolean savedGameFlag;
    private int activeArrowType;
    private int poisonCounter;
    private final PlayerLocationManager plMgr;
    private final GameViewingWindowManager vwMgr;
    private final ScoreTracker st;
    private final StatGUI sg;
    private final MazeEffectManager em;
    private JLabel[][] drawGrid;
    private boolean delayedDecayActive;
    private MazeObject delayedDecayObject;
    private boolean actingRemotely;
    boolean arrowActive;
    boolean teleporting;
    private int[] remoteCoords;
    private boolean autoFinishEnabled;
    private int autoFinishThreshold;
    private boolean stateChanged;
    private boolean trueSightFlag;

    // Constructors
    public GameManager() {
        this.plMgr = new PlayerLocationManager();
        this.vwMgr = new GameViewingWindowManager();
        this.st = new ScoreTracker();
        this.em = new MazeEffectManager();
        this.sg = new StatGUI();
        this.setUpGUI();
        this.setPullInProgress(false);
        this.setUsingAnItem(false);
        this.savedMazeObject = new Empty();
        this.lastUsedObjectIndex = 0;
        this.lastUsedBowIndex = MazeMode.getApplication().getObjects()
                .getAllBowNames().length - 1;
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
        this.activeBow = new Bow();
        this.autoFinishEnabled = false;
        this.autoFinishThreshold = 0;
        this.trueSightFlag = false;
    }

    // Methods
    public boolean newGame() {
        return true;
    }

    private StatGUI getStatGUI() {
        return this.sg;
    }

    public void stateChanged() {
        this.stateChanged = true;
    }

    public void enableTrueSight() {
        this.trueSightFlag = true;
        this.redrawMaze();
    }

    public void disableTrueSight() {
        this.trueSightFlag = false;
        this.redrawMaze();
    }

    public PlayerLocationManager getPlayerManager() {
        return this.plMgr;
    }

    public ScoreTracker getScoreTracker() {
        return this.st;
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
        this.objectInv.useBow(this.activeBow);
        this.getStatGUI().updateStats();
        this.checkGameOver();
    }

    public void updateStatImages() {
        this.getStatGUI().updateImages();
    }

    public MazeObject getSavedMazeObject() {
        return this.savedMazeObject;
    }

    public void setSavedMazeObject(final MazeObject newSavedObject) {
        this.savedMazeObject = newSavedObject;
    }

    private void saveSavedMazeObject() {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final GenericCharacter player = (GenericCharacter) MazeMode
                .getApplication().getMazeManager().getMaze()
                .getCell(px, py, pz, MazeConstants.LAYER_OBJECT);
        player.setSavedObject(this.savedMazeObject);
    }

    private void restoreSavedMazeObject() {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final GenericCharacter player = (GenericCharacter) MazeMode
                .getApplication().getMazeManager().getMaze()
                .getCell(px, py, pz, MazeConstants.LAYER_OBJECT);
        this.savedMazeObject = player.getSavedObject();
    }

    public boolean isFloorBelow() {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.getCell(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ() - 1,
                    MazeConstants.LAYER_OBJECT);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        } catch (final NullPointerException np) {
            return false;
        }
    }

    public boolean isFloorAbove() {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.getCell(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ() + 1,
                    MazeConstants.LAYER_OBJECT);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        } catch (final NullPointerException np) {
            return false;
        }
    }

    public boolean isLevelBelow() {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        return m.doesLevelExistOffset(-1);
    }

    public boolean isLevelAbove() {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        return m.doesLevelExistOffset(1);
    }

    public boolean doesFloorExist(final int floor) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.getCell(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(), floor,
                    MazeConstants.LAYER_OBJECT);
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

    private void saveObjectInventory() {
        this.savedObjectInv = this.objectInv.clone();
    }

    private void restoreObjectInventory() {
        if (this.savedObjectInv != null) {
            this.objectInv = this.savedObjectInv.clone();
        } else {
            this.objectInv = new ObjectInventory();
        }
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

    public boolean isEffectActive(final int effectID) {
        return this.em.isEffectActive(effectID);
    }

    private void decayEffects() {
        this.em.decayEffects();
    }

    public void activateEffect(final int effectID, final int duration) {
        this.em.activateEffect(effectID, duration);
    }

    public void deactivateEffect(final int effectID) {
        this.em.deactivateEffect(effectID);
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
        final MazeObject acted = MazeMode.getApplication().getMazeManager()
                .getMazeObject(x, y, z, MazeConstants.LAYER_OBJECT);
        acted.preMoveAction(false, x, y, this.objectInv);
        acted.postMoveAction(false, x, y, this.objectInv);
        if (acted.doesChainReact()) {
            acted.chainReactionAction(x, y, z);
        }
    }

    public void doClockwiseRotate(final int r) {
        final Maze m = MazeMode.getApplication().getMazeManager().getMaze();
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
            MazeMode.getApplication().showMessage("Rotation failed!");
        }
    }

    public void findPlayerAndAdjust() {
        // Find the player, adjust player location
        final Maze m = MazeMode.getApplication().getMazeManager().getMaze();
        final int w = this.plMgr.getPlayerLocationW();
        m.findPlayer();
        this.plMgr.setPlayerLocation(m.getFindResultColumn(),
                m.getFindResultRow(), m.getFindResultFloor(), w);
        this.resetViewingWindow();
        this.redrawMaze();
    }

    private void fireStepActions() {
        final Maze m = MazeMode.getApplication().getMazeManager().getMaze();
        if (m.getPoisonPower() > 0) {
            this.poisonCounter++;
            if (this.poisonCounter >= m.getPoisonPower()) {
                // Poison
                this.poisonCounter = 0;
                m.doPoisonDamage();
            }
        }
        this.objectInv.fireStepActions();
    }

    public void doCounterclockwiseRotate(final int r) {
        final Maze m = MazeMode.getApplication().getMazeManager().getMaze();
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
            MazeMode.getApplication().showMessage("Rotation failed!");
        }
    }

    public void fireArrow(final int x, final int y) {
        if (this.objectInv.getUses(this.activeBow) == 0) {
            MazeMode.getApplication().showMessage("You're out of arrows!");
        } else {
            final ArrowTask at = new ArrowTask(x, y, this.activeArrowType);
            this.arrowActive = true;
            at.start();
        }
    }

    public void updatePositionRelative(int ox, int oy) {
        this.actingRemotely = false;
        boolean redrawsSuspended = false;
        int px = this.plMgr.getPlayerLocationX();
        int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final int[] mod = this.doEffects(ox, oy);
        int x = mod[0];
        int y = mod[1];
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        m.tickTimers(pz);
        boolean proceed = false;
        MazeObject o = new Empty();
        MazeObject acted = new Empty();
        MazeObject groundInto = new Empty();
        MazeObject below = null;
        MazeObject previousBelow = null;
        MazeObject nextBelow = null;
        MazeObject nextAbove = null;
        MazeObject nextNextBelow = null;
        MazeObject nextNextAbove = null;
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
                            MazeConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    o = new Empty();
                }
                try {
                    below = m.getCell(px, py, pz, MazeConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    below = new Empty();
                }
                try {
                    nextBelow = m.getCell(px + x, py + y, pz,
                            MazeConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextBelow = new Empty();
                }
                try {
                    nextAbove = m.getCell(px + x, py + y, pz,
                            MazeConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextAbove = new Wall();
                }
                try {
                    previousBelow = m.getCell(px - x, py - y, pz,
                            MazeConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    previousBelow = new Empty();
                }
                try {
                    nextNextBelow = m.getCell(px + 2 * x, py + 2 * y, pz,
                            MazeConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextNextBelow = new Empty();
                }
                try {
                    nextNextAbove = m.getCell(px + 2 * x, py + 2 * y, pz,
                            MazeConstants.LAYER_OBJECT);
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
                    if (this.checkSolid(x + px, y + py, this.savedMazeObject,
                            below, nextBelow, nextAbove)) {
                        if (this.delayedDecayActive) {
                            this.doDelayedDecay();
                        }
                        m.setCell(this.savedMazeObject, px, py, pz,
                                MazeConstants.LAYER_OBJECT);
                        acted = new Empty();
                        try {
                            acted = m.getCell(px - x, py - y, pz,
                                    MazeConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException ae) {
                            // Do nothing
                        }
                        if (acted.isPullable() && this.isPullInProgress()) {
                            if (!this.checkPull(x, y, pullX, pullY, acted,
                                    previousBelow, below, this.savedMazeObject)) {
                                // Pull failed - object can't move that way
                                acted.pullFailedAction(this.objectInv, x, y,
                                        pullX, pullY);
                                this.decayEffects();
                                proceed = false;
                            }
                        } else if (!acted.isPullable()
                                && this.isPullInProgress()) {
                            // Pull failed - object not pullable
                            acted.pullFailedAction(this.objectInv, x, y, pullX,
                                    pullY);
                            this.decayEffects();
                            proceed = false;
                        }
                        this.plMgr.offsetPlayerLocationX(x);
                        this.plMgr.offsetPlayerLocationY(y);
                        px += x;
                        py += y;
                        this.vwMgr.offsetViewingWindowLocationX(y);
                        this.vwMgr.offsetViewingWindowLocationY(x);
                        this.savedMazeObject = m.getCell(px, py, pz,
                                MazeConstants.LAYER_OBJECT);
                        m.setCell(new Player(), px, py, pz,
                                MazeConstants.LAYER_OBJECT);
                        this.decayEffects();
                        app.getMazeManager().setDirty(true);
                        this.fireStepActions();
                        if (this.objectInv.isItemThere(new IceAmulet())
                                || this.objectInv
                                        .isItemThere(new PasswallBoots())) {
                            redrawsSuspended = true;
                        } else {
                            this.redrawMaze();
                        }
                        groundInto = m.getCell(px, py, pz,
                                MazeConstants.LAYER_GROUND);
                        m.setCell(groundInto, px, py, pz,
                                MazeConstants.LAYER_GROUND);
                        if (groundInto.overridesDefaultPostMove()) {
                            groundInto.postMoveAction(false, px, py,
                                    this.objectInv);
                            if (!this.savedMazeObject
                                    .isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                                this.savedMazeObject.postMoveAction(false, px,
                                        py, this.objectInv);
                            }
                        } else {
                            this.savedMazeObject.postMoveAction(false, px, py,
                                    this.objectInv);
                        }
                        this.outputFrame.pack();
                    } else {
                        acted = m.getCell(px + x, py + y, pz,
                                MazeConstants.LAYER_OBJECT);
                        if (acted.isPushable()) {
                            if (this.checkPush(x, y, pushX, pushY, acted,
                                    nextBelow, nextNextBelow, nextNextAbove)) {
                                if (this.delayedDecayActive) {
                                    this.doDelayedDecay();
                                }
                                m.setCell(this.savedMazeObject, px, py, pz,
                                        MazeConstants.LAYER_OBJECT);
                                this.plMgr.offsetPlayerLocationX(x);
                                this.plMgr.offsetPlayerLocationY(y);
                                px += x;
                                py += y;
                                this.vwMgr.offsetViewingWindowLocationX(y);
                                this.vwMgr.offsetViewingWindowLocationY(x);
                                this.savedMazeObject = m.getCell(px, py, pz,
                                        MazeConstants.LAYER_OBJECT);
                                m.setCell(new Player(), px, py, pz,
                                        MazeConstants.LAYER_OBJECT);
                                this.decayEffects();
                                app.getMazeManager().setDirty(true);
                                this.fireStepActions();
                                if (this.objectInv.isItemThere(new IceAmulet())
                                        || this.objectInv
                                                .isItemThere(new PasswallBoots())) {
                                    redrawsSuspended = true;
                                } else {
                                    this.redrawMaze();
                                }
                                groundInto = m.getCell(px, py, pz,
                                        MazeConstants.LAYER_GROUND);
                                m.setCell(groundInto, px, py, pz,
                                        MazeConstants.LAYER_GROUND);
                                if (groundInto.overridesDefaultPostMove()) {
                                    groundInto.postMoveAction(false, px, py,
                                            this.objectInv);
                                    if (!this.savedMazeObject
                                            .isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                                        this.savedMazeObject.postMoveAction(
                                                false, px, py, this.objectInv);
                                    }
                                } else {
                                    this.savedMazeObject.postMoveAction(false,
                                            px, py, this.objectInv);
                                }
                                this.outputFrame.pack();
                            } else {
                                // Push failed - object can't move that way
                                acted.pushFailedAction(this.objectInv, x, y,
                                        pushX, pushY);
                                this.decayEffects();
                                this.fireStepActions();
                                proceed = false;
                            }
                        } else if (acted.doesChainReact()) {
                            acted.chainReactionAction(px + x, py + y, pz);
                        } else {
                            // Move failed - object is solid in that direction
                            this.fireMoveFailedActions(px + x, py + y,
                                    this.savedMazeObject, below, nextBelow,
                                    nextAbove);
                            this.decayEffects();
                            this.fireStepActions();
                            proceed = false;
                        }
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.vwMgr.restoreViewingWindow();
                    this.plMgr.restorePlayerLocation();
                    m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(),
                            this.plMgr.getPlayerLocationZ(),
                            MazeConstants.LAYER_OBJECT);
                    // Move failed - attempted to go outside the maze
                    o.moveFailedAction(false, this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(), this.objectInv);
                    this.decayEffects();
                    this.fireStepActions();
                    MazeMode.getApplication().showMessage("Can't go that way");
                    o = new Empty();
                    proceed = false;
                }
            } else {
                // Move failed - pre-move check failed
                o.moveFailedAction(false, px + x, py + y, this.objectInv);
                this.decayEffects();
                this.fireStepActions();
                proceed = false;
            }
            if (redrawsSuspended
                    && !this.checkLoopCondition(proceed, x, y, groundInto,
                            below, nextBelow, nextAbove)) {
                // Redraw post-suspend
                this.redrawMaze();
                redrawsSuspended = false;
            }
        } while (this.checkLoopCondition(proceed, x, y, groundInto, below,
                nextBelow, nextAbove));
        this.getStatGUI().updateStats();
        this.checkGameOver();
        // Check for auto-finish
        if (this.autoFinishEnabled) {
            final int ssCount = this.objectInv.getItemCount(new SunStone());
            this.autoFinishProgress.setValue(ssCount);
            this.autoFinishProgress
                    .setString((int) ((double) this.autoFinishProgress
                            .getValue()
                            / (double) this.autoFinishProgress.getMaximum() * 100.0)
                            + "%");
            if (ssCount >= this.autoFinishThreshold) {
                // Auto-Finish
                SoundManager.playSound(
                        SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                        SoundConstants.SOUND_FINISH);
                this.solvedLevel();
            }
        }
    }

    private boolean checkLoopCondition(final boolean proceed, final int x,
            final int y, final MazeObject groundInto, final MazeObject below,
            final MazeObject nextBelow, final MazeObject nextAbove) {
        // Handle slippery boots and ice amulet
        if (this.objectInv.isItemThere(new SlipperyBoots())
                || this.objectInv.isItemThere(new IceAmulet())) {
            return proceed
                    && this.checkSolid(x, y, this.savedMazeObject, below,
                            nextBelow, nextAbove);
        } else {
            return proceed
                    && !groundInto.hasFrictionConditionally(this.objectInv,
                            false)
                    && this.checkSolid(x, y, this.savedMazeObject, below,
                            nextBelow, nextAbove);
        }
    }

    public void backUpPlayer() {
        final Maze m = MazeMode.getApplication().getMazeManager().getMaze();
        final int pz = this.plMgr.getPlayerLocationZ();
        this.plMgr.restorePlayerLocation();
        this.vwMgr.restoreViewingWindow();
        final int opx = this.plMgr.getPlayerLocationX();
        final int opy = this.plMgr.getPlayerLocationY();
        this.savedMazeObject = m.getCell(opx, opy, pz,
                MazeConstants.LAYER_OBJECT);
        m.setCell(new Player(), opx, opy, pz, MazeConstants.LAYER_OBJECT);
        this.redrawMaze();
    }

    private boolean checkSolid(final int x, final int y,
            final MazeObject inside, final MazeObject below,
            final MazeObject nextBelow, final MazeObject nextAbove) {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final boolean insideSolid = inside.isConditionallyDirectionallySolid(
                false, x - px, y - py, this.objectInv);
        final boolean belowSolid = below.isConditionallyDirectionallySolid(
                false, x - px, y - py, this.objectInv);
        boolean nextBelowSolid = nextBelow.isConditionallyDirectionallySolid(
                true, x - px, y - py, this.objectInv);
        // Handle hot boots, slippery boots, fire amulet, and ice amulet
        if (this.objectInv.isItemThere(new HotBoots())
                || this.objectInv.isItemThere(new SlipperyBoots())
                || this.objectInv.isItemThere(new FireAmulet())
                || this.objectInv.isItemThere(new IceAmulet())) {
            nextBelowSolid = false;
        }
        final boolean nextAboveSolid = nextAbove
                .isConditionallyDirectionallySolid(true, x - px, y - py,
                        this.objectInv);
        if (insideSolid || belowSolid || nextBelowSolid || nextAboveSolid) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkSolidAbsolute(final MazeObject inside,
            final MazeObject below, final MazeObject nextBelow,
            final MazeObject nextAbove) {
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
            final MazeObject inside, final MazeObject below,
            final MazeObject nextBelow, final MazeObject nextAbove) {
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
            final int pushY, final MazeObject acted,
            final MazeObject nextBelow, final MazeObject nextNextBelow,
            final MazeObject nextNextAbove) {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final boolean nextBelowAccept = nextBelow.isPushableOut();
        final boolean nextNextBelowAccept = nextNextBelow.isPushableInto();
        final boolean nextNextAboveAccept = nextNextAbove.isPushableInto();
        if (nextBelowAccept && nextNextBelowAccept && nextNextAboveAccept) {
            nextBelow.pushOutAction(this.objectInv, acted, px + pushX, py
                    + pushY, pz);
            acted.pushAction(this.objectInv, nextNextAbove, x, y, pushX, pushY);
            nextNextAbove.pushIntoAction(this.objectInv, acted, px + pushX, py
                    + pushY, pz);
            nextNextBelow.pushIntoAction(this.objectInv, acted, px + pushX, py
                    + pushY, pz);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPull(final int x, final int y, final int pullX,
            final int pullY, final MazeObject acted,
            final MazeObject previousBelow, final MazeObject below,
            final MazeObject above) {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final boolean previousBelowAccept = previousBelow.isPullableOut();
        final boolean belowAccept = below.isPullableInto();
        final boolean aboveAccept = above.isPullableInto();
        if (previousBelowAccept && belowAccept && aboveAccept) {
            previousBelow.pullOutAction(this.objectInv, acted, px - pullX, py
                    - pullY, pz);
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
            final int pushY, final GenericMovableObject o) {
        final int xInc = (int) Math.signum(x), yInc = (int) Math.signum(y);
        int cumPushX = pushX, cumPushY = pushY, cumX = x, cumY = y;
        final Application app = MazeMode.getApplication();
        final MazeManager mm = app.getMazeManager();
        MazeObject there = mm.getMazeObject(this.plMgr.getPlayerLocationX()
                + cumX, this.plMgr.getPlayerLocationY() + cumY,
                this.plMgr.getPlayerLocationZ(), MazeConstants.LAYER_GROUND);
        if (there != null) {
            do {
                this.movePushedObjectPosition(cumX, cumY, cumPushX, cumPushY,
                        o, there);
                cumX += xInc;
                cumY += yInc;
                cumPushX += xInc;
                cumPushY += yInc;
                there = mm.getMazeObject(
                        this.plMgr.getPlayerLocationX() + cumX,
                        this.plMgr.getPlayerLocationY() + cumY,
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_GROUND);
                if (there == null) {
                    break;
                }
            } while (!there.hasFrictionConditionally(this.objectInv, true));
        }
    }

    private void movePushedObjectPosition(final int x, final int y,
            final int pushX, final int pushY, final GenericMovableObject o,
            final MazeObject g) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(o.getSavedObject(), this.plMgr.getPlayerLocationX() + x,
                    this.plMgr.getPlayerLocationY() + y,
                    this.plMgr.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
            m.setCell(o, this.plMgr.getPlayerLocationX() + pushX,
                    this.plMgr.getPlayerLocationY() + pushY,
                    this.plMgr.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
            if (g.overridesDefaultPostMove()) {
                g.postMoveAction(false, this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(), this.objectInv);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePulledPosition(final int x, final int y, final int pullX,
            final int pullY, final GenericMovableObject o) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(o.getSavedObject(), this.plMgr.getPlayerLocationX() - x,
                    this.plMgr.getPlayerLocationY() - y,
                    this.plMgr.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
            m.setCell(o, this.plMgr.getPlayerLocationX() - pullX,
                    this.plMgr.getPlayerLocationY() - pullY,
                    this.plMgr.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePushedIntoPositionAbsolute(final int x, final int y,
            final int z, final int x2, final int y2, final int z2,
            final GenericMovableObject pushedInto, final MazeObject source) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                final MazeObject saved = m.getCell(x, y, z,
                        MazeConstants.LAYER_OBJECT);
                m.setCell(pushedInto, x, y, z, MazeConstants.LAYER_OBJECT);
                pushedInto.setSavedObject(saved);
                m.setCell(source, x2, y2, z2, MazeConstants.LAYER_OBJECT);
                saved.pushIntoAction(this.objectInv, pushedInto, x2, y2, z2 - 1);
                this.redrawMaze();
                app.getMazeManager().setDirty(true);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.setCell(new Empty(), x2, y2, z2, MazeConstants.LAYER_OBJECT);
        }
    }

    public boolean tryUpdatePositionRelative(final int x, final int y) {
        try {
            final Application app = MazeMode.getApplication();
            final Maze m = app.getMazeManager().getMaze();
            final MazeObject below = m
                    .getCell(this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(),
                            this.plMgr.getPlayerLocationZ(),
                            MazeConstants.LAYER_GROUND);
            final MazeObject nextBelow = m
                    .getCell(this.plMgr.getPlayerLocationX() + x,
                            this.plMgr.getPlayerLocationY() + y,
                            this.plMgr.getPlayerLocationZ(),
                            MazeConstants.LAYER_GROUND);
            final MazeObject nextAbove = m
                    .getCell(this.plMgr.getPlayerLocationX() + x,
                            this.plMgr.getPlayerLocationY() + y,
                            this.plMgr.getPlayerLocationZ(),
                            MazeConstants.LAYER_OBJECT);
            return this.checkSolid(x, y, this.savedMazeObject, below,
                    nextBelow, nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public boolean tryUpdatePositionAbsolute(final int x, final int y,
            final int z) {
        try {
            final Application app = MazeMode.getApplication();
            final Maze m = app.getMazeManager().getMaze();
            final MazeObject below = m
                    .getCell(this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(),
                            this.plMgr.getPlayerLocationZ(),
                            MazeConstants.LAYER_GROUND);
            final MazeObject nextBelow = m.getCell(x, y, z,
                    MazeConstants.LAYER_GROUND);
            final MazeObject nextAbove = m.getCell(x, y, z,
                    MazeConstants.LAYER_OBJECT);
            return this.checkSolidAbsolute(this.savedMazeObject, below,
                    nextBelow, nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public void updatePositionAbsolute(final int x, final int y, final int z) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.getCell(x, y, z, MazeConstants.LAYER_OBJECT).preMoveAction(true,
                    x, y, this.objectInv);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
        this.plMgr.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                m.setCell(this.savedMazeObject,
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                this.plMgr.setPlayerLocation(x, y, z, 0);
                this.vwMgr.setViewingWindowLocationX(this.plMgr
                        .getPlayerLocationY() - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(this.plMgr
                        .getPlayerLocationX() - this.vwMgr.getOffsetFactorY());
                this.savedMazeObject = m.getCell(
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                this.redrawMaze();
                app.getMazeManager().setDirty(true);
                this.savedMazeObject
                        .postMoveAction(false, x, y, this.objectInv);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
            MazeMode.getApplication().showMessage("Can't go outside the maze");
        } catch (final NullPointerException np) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
            MazeMode.getApplication().showMessage("Can't go outside the maze");
        }
    }

    public void updatePositionAbsoluteNoEvents(final int x, final int y,
            final int z, final int w) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        this.plMgr.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                m.setCell(this.savedMazeObject,
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                this.plMgr.setPlayerLocation(x, y, z, w);
                this.vwMgr.setViewingWindowLocationX(this.plMgr
                        .getPlayerLocationY() - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(this.plMgr
                        .getPlayerLocationX() - this.vwMgr.getOffsetFactorY());
                this.savedMazeObject = m.getCell(
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                this.redrawMaze();
                app.getMazeManager().setDirty(true);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
            MazeMode.getApplication().showMessage("Can't go outside the maze");
        } catch (final NullPointerException np) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
            MazeMode.getApplication().showMessage("Can't go outside the maze");
        }
    }

    public void showCurrentScore() {
        this.st.showCurrentScore();
    }

    public void showScoreTable() {
        this.st.showScoreTable();
    }

    public void addToScore(final long value) {
        this.st.addToScore(value);
    }

    public void redrawMaze() {
        // Draw the maze, if it is visible
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
            this.redrawMazeNoRebuild();
        }
    }

    public void redrawMazeNoRebuild() {
        // Draw the maze, if it is visible
        if (this.outputFrame.isVisible()) {
            final Application app = MazeMode.getApplication();
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
                    visible = app.getMazeManager().getMaze()
                            .isSquareVisible(u, v, y, x);
                    try {
                        if (visible) {
                            String name1, name2;
                            if (this.trueSightFlag) {
                                name1 = app
                                        .getMazeManager()
                                        .getMaze()
                                        .getCell(
                                                y,
                                                x,
                                                this.plMgr.getPlayerLocationZ(),
                                                MazeConstants.LAYER_GROUND)
                                        .editorRenderHook(y, x,
                                                this.plMgr.getPlayerLocationZ());
                                name2 = app
                                        .getMazeManager()
                                        .getMaze()
                                        .getCell(
                                                y,
                                                x,
                                                this.plMgr.getPlayerLocationZ(),
                                                MazeConstants.LAYER_OBJECT)
                                        .editorRenderHook(y, x,
                                                this.plMgr.getPlayerLocationZ());
                            } else {
                                name1 = app
                                        .getMazeManager()
                                        .getMaze()
                                        .getCell(
                                                y,
                                                x,
                                                this.plMgr.getPlayerLocationZ(),
                                                MazeConstants.LAYER_GROUND)
                                        .gameRenderHook(y, x,
                                                this.plMgr.getPlayerLocationZ());
                                name2 = app
                                        .getMazeManager()
                                        .getMaze()
                                        .getCell(
                                                y,
                                                x,
                                                this.plMgr.getPlayerLocationZ(),
                                                MazeConstants.LAYER_OBJECT)
                                        .gameRenderHook(y, x,
                                                this.plMgr.getPlayerLocationZ());
                            }
                            this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                    .getCompositeImage(name1, name2));
                        } else {
                            this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                    .getImage("Darkness"));
                        }
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        if (this.trueSightFlag) {
                            this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                    .getImage(new EmptyVoid().editorRenderHook(
                                            y, x,
                                            this.plMgr.getPlayerLocationZ())));
                        } else {
                            this.drawGrid[xFix][yFix]
                                    .setIcon(GraphicsManager.getImage(new EmptyVoid()
                                            .gameRenderHook(y, x, this.plMgr
                                                    .getPlayerLocationZ())));
                        }
                    } catch (final NullPointerException np) {
                        if (this.trueSightFlag) {
                            this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                    .getImage(new EmptyVoid().editorRenderHook(
                                            y, x,
                                            this.plMgr.getPlayerLocationZ())));
                        } else {
                            this.drawGrid[xFix][yFix]
                                    .setIcon(GraphicsManager.getImage(new EmptyVoid()
                                            .gameRenderHook(y, x, this.plMgr
                                                    .getPlayerLocationZ())));
                        }
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
        // Draw the square, if the maze is visible
        final Application app = MazeMode.getApplication();
        if (this.outputFrame.isVisible()) {
            int xFix, yFix;
            boolean visible;
            xFix = y - this.vwMgr.getViewingWindowLocationX();
            yFix = x - this.vwMgr.getViewingWindowLocationY();
            visible = app
                    .getMazeManager()
                    .getMaze()
                    .isSquareVisible(this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(), x, y);
            try {
                if (visible) {
                    String name1, name2;
                    if (this.trueSightFlag) {
                        name1 = app
                                .getMazeManager()
                                .getMaze()
                                .getCell(x, y, this.plMgr.getPlayerLocationZ(),
                                        MazeConstants.LAYER_GROUND)
                                .editorRenderHook(x, y,
                                        this.plMgr.getPlayerLocationZ());
                        name2 = app
                                .getMazeManager()
                                .getMaze()
                                .getCell(x, y, this.plMgr.getPlayerLocationZ(),
                                        MazeConstants.LAYER_OBJECT)
                                .editorRenderHook(x, y,
                                        this.plMgr.getPlayerLocationZ());
                    } else {
                        name1 = app
                                .getMazeManager()
                                .getMaze()
                                .getCell(x, y, this.plMgr.getPlayerLocationZ(),
                                        MazeConstants.LAYER_GROUND)
                                .gameRenderHook(x, y,
                                        this.plMgr.getPlayerLocationZ());
                        name2 = app
                                .getMazeManager()
                                .getMaze()
                                .getCell(x, y, this.plMgr.getPlayerLocationZ(),
                                        MazeConstants.LAYER_OBJECT)
                                .gameRenderHook(x, y,
                                        this.plMgr.getPlayerLocationZ());
                    }
                    this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                            .getVirtualCompositeImage(name1, name2, name3));
                } else {
                    this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                            .getImage("Darkness"));
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
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        m.switchLevel(level);
        this.plMgr.setPlayerLocation(m.getStartColumn(), m.getStartRow(),
                m.getStartFloor(), level);
    }

    public void resetCurrentLevel() {
        this.resetLevel(this.plMgr.getPlayerLocationW());
    }

    public void resetGameState() {
        this.deactivateAllEffects();
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        app.getMazeManager().setDirty(false);
        m.restore();
        m.resetTimer();
        this.setSavedGameFlag(false);
        this.st.resetScore();
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

    public void resetLevel(final int level) {
        this.deactivateAllEffects();
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        app.getMazeManager().setDirty(true);
        m.restore();
        m.resetTimer();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            // Activate first moving finish, if one exists
            m.deactivateAllMovingFinishes();
            m.activateFirstMovingFinish();
            this.st.resetScore(app.getMazeManager().getScoresFileName());
            this.resetPlayerLocation(level);
            this.resetViewingWindow();
            m.resetVisionRadius();
            this.decay();
            this.restoreObjectInventory();
            this.redrawMaze();
        }
    }

    public void goToLevel(final int level) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final boolean levelExists = m.doesLevelExist(level);
        if (levelExists) {
            this.saveSavedMazeObject();
            m.switchLevel(level);
            this.plMgr.setPlayerLocationW(level);
            this.findPlayerAndAdjust();
            this.restoreSavedMazeObject();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedLevel() {
        this.deactivateAllEffects();
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        CommonDialogs.showTitledDialog(m.getLevelEndMessage(),
                m.getLevelTitle());
        final boolean levelExists;
        if (m.useOffset()) {
            levelExists = m.doesLevelExistOffset(m.getNextLevel());
        } else {
            levelExists = m.doesLevelExist(m.getNextLevel());
        }
        if (levelExists) {
            m.restore();
            m.resetTimer();
            // Activate first moving finish, if one exists
            m.deactivateAllMovingFinishes();
            m.activateFirstMovingFinish();
            if (m.useOffset()) {
                m.switchLevelOffset(m.getNextLevel());
            } else {
                m.switchLevel(m.getNextLevel());
            }
            this.resetPlayerLocation(this.plMgr.getPlayerLocationW() + 1);
            this.resetViewingWindow();
            m.resetVisionRadius();
            // Activate first moving finish, if one exists
            m.deactivateAllMovingFinishes();
            m.activateFirstMovingFinish();
            this.decay();
            this.saveObjectInventory();
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.redrawMaze();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedLevelWarp(final int level) {
        this.deactivateAllEffects();
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        CommonDialogs.showTitledDialog(m.getLevelEndMessage(),
                m.getLevelTitle());
        final boolean levelExists = m.doesLevelExist(level);
        if (levelExists) {
            m.restore();
            m.resetTimer();
            // Activate first moving finish, if one exists
            m.deactivateAllMovingFinishes();
            m.activateFirstMovingFinish();
            m.switchLevel(level);
            this.resetPlayerLocation(level);
            this.resetViewingWindow();
            m.resetVisionRadius();
            // Activate first moving finish, if one exists
            m.deactivateAllMovingFinishes();
            m.activateFirstMovingFinish();
            this.decay();
            this.saveObjectInventory();
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.redrawMaze();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedMaze() {
        final Maze m = MazeMode.getApplication().getMazeManager().getMaze();
        m.resetTimer();
        CommonDialogs.showTitledDialog(m.getMazeEndMessage(), m.getMazeTitle());
        if (this.st.checkScore()) {
            MazeMode.getApplication().playHighScoreSound();
        }
        this.st.commitScore();
        this.exitGame();
    }

    public void exitGame() {
        this.stateChanged = true;
        this.deactivateAllEffects();
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        // Restore the maze
        m.restore();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            m.healFully();
            this.resetViewingWindowAndPlayerLocation();
        } else {
            app.getMazeManager().setLoaded(false);
        }
        // Wipe the inventory
        this.objectInv = new ObjectInventory();
        // Reset saved game flag
        this.savedGameFlag = false;
        app.getMazeManager().setDirty(false);
        // Exit game
        this.hideOutput();
        app.setInGame(false);
        app.getGUIManager().showGUI();
    }

    public void checkGameOver() {
        if (!MazeMode.getApplication().getMazeManager().getMaze().isAlive()) {
            this.gameOver();
        }
    }

    private void gameOver() {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_USER_INTERFACE,
                SoundConstants.SOUND_GAME_OVER);
        CommonDialogs.showDialog("You have died - Game Over!");
        this.exitGame();
    }

    public JFrame getOutputFrame() {
        return this.outputFrame;
    }

    public void decay() {
        if (this.actingRemotely) {
            MazeMode.getApplication()
                    .getMazeManager()
                    .getMaze()
                    .setCell(new Empty(), this.remoteCoords[0],
                            this.remoteCoords[1], this.remoteCoords[2],
                            MazeConstants.LAYER_OBJECT);
        } else {
            this.savedMazeObject = new Empty();
        }
    }

    public void decayTo(final MazeObject decay) {
        if (this.actingRemotely) {
            MazeMode.getApplication()
                    .getMazeManager()
                    .getMaze()
                    .setCell(decay, this.remoteCoords[0], this.remoteCoords[1],
                            this.remoteCoords[2], MazeConstants.LAYER_OBJECT);
        } else {
            this.savedMazeObject = decay;
        }
    }

    private void doDelayedDecay() {
        if (this.actingRemotely) {
            MazeMode.getApplication()
                    .getMazeManager()
                    .getMaze()
                    .setCell(this.delayedDecayObject, this.remoteCoords[0],
                            this.remoteCoords[1], this.remoteCoords[2],
                            MazeConstants.LAYER_OBJECT);
        } else {
            this.savedMazeObject = this.delayedDecayObject;
        }
        this.delayedDecayActive = false;
    }

    public void delayedDecayTo(final MazeObject obj) {
        this.delayedDecayActive = true;
        this.delayedDecayObject = obj;
    }

    public void morph(final MazeObject morphInto, final int x, final int y,
            final int z) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, x, y, z, morphInto.getLayer());
            this.redrawMazeNoRebuild();
            app.getMazeManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morph(final MazeObject morphInto, final int x, final int y,
            final int z, final String msg) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, x, y, z, morphInto.getLayer());
            MazeMode.getApplication().showMessage(msg);
            this.keepNextMessage();
            this.redrawMazeNoRebuild();
            app.getMazeManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morph(final MazeObject morphInto, final int x, final int y,
            final int z, final int e) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, x, y, z, e);
            this.redrawMazeNoRebuild();
            app.getMazeManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morphOther(final MazeObject morphInto, final int x,
            final int y, final int e) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, this.plMgr.getPlayerLocationX() + x,
                    this.plMgr.getPlayerLocationY() + y,
                    this.plMgr.getPlayerLocationZ(), e);
            this.redrawMazeNoRebuild();
            app.getMazeManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void keepNextMessage() {
        this.knm = true;
    }

    public void showInventoryDialog() {
        final String[] invString = this.objectInv
                .generateInventoryStringArray();
        CommonDialogs.showInputDialog("Inventory", "Inventory", invString,
                invString[0]);
    }

    public void showUseDialog() {
        int x;
        final MazeObjectList list = MazeMode.getApplication().getObjects();
        final MazeObject[] choices = list.getAllUsableObjects();
        final String[] userChoices = this.objectInv.generateUseStringArray();
        final String result = CommonDialogs.showInputDialog("Use which item?",
                MazeMode.getProgramName(), userChoices,
                userChoices[this.lastUsedObjectIndex]);
        try {
            for (x = 0; x < choices.length; x++) {
                if (result.equals(userChoices[x])) {
                    this.lastUsedObjectIndex = x;
                    this.objectBeingUsed = choices[x];
                    if (this.objectInv.getUses(this.objectBeingUsed) == 0) {
                        MazeMode.getApplication().showMessage(
                                "That item has no more uses left.");
                        this.setUsingAnItem(false);
                    } else {
                        MazeMode.getApplication().showMessage(
                                "Click to set target");
                    }
                    return;
                }
            }
        } catch (final NullPointerException np) {
            this.setUsingAnItem(false);
        }
    }

    public void showSwitchBowDialog() {
        int x;
        final MazeObjectList list = MazeMode.getApplication().getObjects();
        final MazeObject[] choices = list.getAllBows();
        final String[] userChoices = this.objectInv.generateBowStringArray();
        final String result = CommonDialogs.showInputDialog(
                "Switch to which bow?", MazeMode.getProgramName(), userChoices,
                userChoices[this.lastUsedBowIndex]);
        try {
            for (x = 0; x < choices.length; x++) {
                if (result.equals(userChoices[x])) {
                    this.lastUsedBowIndex = x;
                    this.activeBow = (GenericBow) choices[x];
                    this.activeArrowType = this.activeBow.getArrowType();
                    if (this.objectInv.getUses(this.activeBow) == 0) {
                        MazeMode.getApplication().showMessage(
                                "That bow is out of arrows!");
                    } else {
                        MazeMode.getApplication().showMessage(
                                this.activeBow.getName() + " activated.");
                    }
                    return;
                }
            }
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public ObjectInventory getObjectInventory() {
        return this.objectInv;
    }

    public void setObjectInventory(final ObjectInventory newObjectInventory) {
        this.objectInv = newObjectInventory;
    }

    public void useItemHandler(final int x, final int y) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
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
            final boolean visible = app
                    .getMazeManager()
                    .getMaze()
                    .isSquareVisible(this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(), destX, destY);
            try {
                final MazeObject target = m.getCell(destX, destY, destZ,
                        MazeConstants.LAYER_OBJECT);
                final String name = this.objectBeingUsed.getName();
                if ((target.isSolid() || !visible)
                        && name.equals(new TeleportWand().getName())) {
                    this.setUsingAnItem(false);
                    MazeMode.getApplication().showMessage(
                            "Can't teleport there");
                }
                if (target.getName().equals(new Player().getName())) {
                    this.setUsingAnItem(false);
                    MazeMode.getApplication().showMessage(
                            "Don't aim at yourself!");
                }
                if (!target.isDestroyable()
                        && name.equals(new AnnihilationWand().getName())) {
                    this.setUsingAnItem(false);
                    MazeMode.getApplication().showMessage("Can't destroy that");
                }
                if (!target.isDestroyable()
                        && name.equals(new WallMakingWand().getName())) {
                    this.setUsingAnItem(false);
                    MazeMode.getApplication().showMessage(
                            "Can't create a wall there");
                }
                if (!target.isDestroyable()
                        && name.equals(new FinishMakingWand().getName())) {
                    this.setUsingAnItem(false);
                    MazeMode.getApplication().showMessage(
                            "Can't create a finish there");
                }
                if ((!target.isDestroyable() || !target
                        .isOfType(TypeConstants.TYPE_WALL))
                        && name.equals(new WallBreakingWand().getName())) {
                    this.setUsingAnItem(false);
                    MazeMode.getApplication().showMessage("Aim at a wall");
                }
                if ((!target.isDestroyable() || !target
                        .isOfType(TypeConstants.TYPE_TRAP))
                        && name.equals(new DisarmTrapWand().getName())) {
                    this.setUsingAnItem(false);
                    MazeMode.getApplication().showMessage("Aim at a trap");
                }
                if (!target.getName().equals(new Empty().getName())
                        && !target.getName().equals(new DarkGem().getName())
                        && name.equals(new LightWand().getName())) {
                    this.setUsingAnItem(false);
                    MazeMode.getApplication().showMessage(
                            "Aim at either an empty space or a Dark Gem");
                }
                if (!target.getName().equals(new Empty().getName())
                        && !target.getName().equals(new LightGem().getName())
                        && name.equals(new DarkWand().getName())) {
                    this.setUsingAnItem(false);
                    MazeMode.getApplication().showMessage(
                            "Aim at either an empty space or a Light Gem");
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.setUsingAnItem(false);
                MazeMode.getApplication().showMessage("Aim within the maze");
            } catch (final NullPointerException np) {
                this.setUsingAnItem(false);
            }
        }
        if (this.usingAnItem()) {
            this.objectInv.use(this.objectBeingUsed, destX, destY, destZ);
            this.redrawMaze();
        }
    }

    public void controllableTeleport() {
        this.teleporting = true;
        MazeMode.getApplication().showMessage("Click to set destination");
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
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_TELEPORT);
            this.teleporting = false;
        }
    }

    public void identifyObject(final int x, final int y) {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
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
            final MazeObject target1 = m.getCell(destX, destY, destZ,
                    MazeConstants.LAYER_GROUND);
            final MazeObject target2 = m.getCell(destX, destY, destZ,
                    MazeConstants.LAYER_OBJECT);
            target1.determineCurrentAppearance(destX, destY, destZ);
            target2.determineCurrentAppearance(destX, destY, destZ);
            final String gameName1 = target1.getGameName();
            final String gameName2 = target2.getGameName();
            MazeMode.getApplication().showMessage(
                    gameName2 + " on " + gameName1);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_IDENTIFY);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            final EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(destX, destY, destZ);
            MazeMode.getApplication().showMessage(ev.getGameName());
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_IDENTIFY);
        }
    }

    public void loadGameHookX(final XDataReader mazeFile,
            final int formatVersion) throws IOException {
        final Application app = MazeMode.getApplication();
        this.objectInv = ObjectInventory
                .readInventoryX(mazeFile, formatVersion);
        this.savedObjectInv = ObjectInventory.readInventoryX(mazeFile,
                formatVersion);
        app.getMazeManager().setScoresFileName(mazeFile.readString());
        this.st.setScore(mazeFile.readLong());
    }

    public void saveGameHookX(final XDataWriter mazeFile) throws IOException {
        final Application app = MazeMode.getApplication();
        this.objectInv.writeInventoryX(mazeFile);
        this.savedObjectInv.writeInventoryX(mazeFile);
        mazeFile.writeString(app.getMazeManager().getScoresFileName());
        mazeFile.writeLong(this.st.getScore());
    }

    public void playMaze() {
        final Application app = MazeMode.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        if (app.getMazeManager().getLoaded()) {
            app.getGUIManager().hideGUI();
            app.setInGame(true);
            if (this.stateChanged) {
                // Initialize only if the maze state has changed
                this.poisonCounter = 0;
                app.getMazeManager()
                        .getMaze()
                        .switchLevel(
                                app.getMazeManager().getMaze().getStartLevel());
                this.savedMazeObject = new Empty();
                this.st.setScoreFile(app.getMazeManager().getScoresFileName());
                this.autoFinishEnabled = app.getMazeManager().getMaze()
                        .getAutoFinishThresholdEnabled();
                this.autoFinishThreshold = app.getMazeManager().getMaze()
                        .getAutoFinishThreshold();
                this.autoFinishProgress.setValue(0);
                this.autoFinishProgress.setString("0%");
                if (this.autoFinishEnabled) {
                    // Update progress bar
                    this.autoFinishProgress.setEnabled(true);
                    this.autoFinishProgress
                            .setMaximum(this.autoFinishThreshold);
                } else {
                    this.autoFinishProgress.setEnabled(false);
                }
                // Activate first moving finish, if one exists
                m.deactivateAllMovingFinishes();
                m.activateFirstMovingFinish();
                if (!this.savedGameFlag) {
                    this.saveObjectInventory();
                    this.st.resetScore(app.getMazeManager().getScoresFileName());
                }
                this.stateChanged = false;
            }
            // Make sure message area is attached to the border pane
            this.borderPane.removeAll();
            this.borderPane.add(this.outputPane, BorderLayout.CENTER);
            this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
            this.borderPane.add(this.getStatGUI().getStatsPane(),
                    BorderLayout.EAST);
            this.borderPane.add(this.progressPane, BorderLayout.WEST);
            this.borderPane.add(this.em.getEffectMessageContainer(),
                    BorderLayout.SOUTH);
            CommonDialogs.showTitledDialog(m.getMazeStartMessage(),
                    m.getMazeTitle());
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.showOutput();
            this.getStatGUI().updateImages();
            this.getStatGUI().updateStats();
            this.checkGameOver();
            this.redrawMaze();
        } else {
            CommonDialogs.showDialog("No Maze Opened");
        }
    }

    public void showOutput() {
        final Application app = MazeMode.getApplication();
        app.getMenuManager().setGameMenus();
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_EXPLORING)) {
            if (!MusicManager.isMusicPlaying()) {
                MusicManager.playMusic(MusicConstants.MUSIC_EXPLORING);
            }
        }
        this.outputFrame.setVisible(true);
        this.outputFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
    }

    public void hideOutput() {
        if (PreferencesManager
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
        this.progressPane = new Container();
        this.progressPane.setLayout(new FlowLayout());
        this.autoFinishProgress = new JProgressBar(SwingConstants.VERTICAL);
        this.autoFinishProgress.setStringPainted(true);
        this.progressPane.add(this.autoFinishProgress);
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        this.outputFrame = new JFrame(MazeMode.getProgramName());
        final Image iconlogo = MazeMode.getApplication().getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.outputPane = new Container();
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.outputPane.setLayout(new GridLayout(this.vwMgr
                .getViewingWindowSizeX(), this.vwMgr.getViewingWindowSizeY()));
        this.outputFrame.setResizable(false);
        this.outputFrame.addKeyListener(this.handler);
        this.outputFrame.addWindowListener(this.handler);
        this.outputPane.addMouseListener(this.handler);
        this.drawGrid = new JLabel[this.vwMgr.getViewingWindowSizeX()][this.vwMgr
                .getViewingWindowSizeY()];
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
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.progressPane, BorderLayout.WEST);
        this.borderPane.add(this.em.getEffectMessageContainer(),
                BorderLayout.SOUTH);
        this.borderPane
                .add(this.getStatGUI().getStatsPane(), BorderLayout.EAST);
    }

    private class EventHandler implements KeyListener, WindowListener,
            MouseListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            if (!GameManager.this.arrowActive) {
                if (!PreferencesManager.oneMove()) {
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
                if (PreferencesManager.oneMove()) {
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
                        MazeMode.getApplication().showMessage(" ");
                    } else if (gm.isTeleporting()) {
                        gm.teleporting = false;
                        MazeMode.getApplication().showMessage(" ");
                    }
                    break;
                default:
                    break;
                }
                if (gm.isPullInProgress()) {
                    gm.setPullInProgress(false);
                }
            } catch (final Exception ex) {
                MazeMode.getErrorLogger().logError(ex);
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
                        MazeMode.getApplication().showMessage(" ");
                    } else if (gm.isTeleporting()) {
                        gm.teleporting = false;
                        MazeMode.getApplication().showMessage(" ");
                    }
                    break;
                default:
                    break;
                }
                if (gm.isPullInProgress()) {
                    gm.setPullInProgress(false);
                }
            } catch (final Exception ex) {
                MazeMode.getErrorLogger().logError(ex);
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
                final Application app = MazeMode.getApplication();
                boolean success = false;
                int status = 0;
                if (app.getMazeManager().getDirty()) {
                    status = app.getMazeManager().showSaveDialog();
                    if (status == JOptionPane.YES_OPTION) {
                        success = app.getMazeManager().saveMaze();
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
                MazeMode.getErrorLogger().logError(ex);
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
                MazeMode.getErrorLogger().logError(ex);
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
