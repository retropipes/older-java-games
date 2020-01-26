/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.game;

import java.io.IOException;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.generic.ArrowTypeConstants;
import com.puttysoftware.widgetwarren.generic.GenericBow;
import com.puttysoftware.widgetwarren.generic.GenericMovableObject;
import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.generic.MazeObjectList;
import com.puttysoftware.widgetwarren.generic.TypeConstants;
import com.puttysoftware.widgetwarren.maze.Maze;
import com.puttysoftware.widgetwarren.maze.MazeConstants;
import com.puttysoftware.widgetwarren.maze.MazeManager;
import com.puttysoftware.widgetwarren.maze.effects.MazeEffectConstants;
import com.puttysoftware.widgetwarren.maze.effects.MazeEffectManager;
import com.puttysoftware.widgetwarren.objects.AnnihilationWand;
import com.puttysoftware.widgetwarren.objects.Bow;
import com.puttysoftware.widgetwarren.objects.DarkGem;
import com.puttysoftware.widgetwarren.objects.DarkWand;
import com.puttysoftware.widgetwarren.objects.DisarmTrapWand;
import com.puttysoftware.widgetwarren.objects.Empty;
import com.puttysoftware.widgetwarren.objects.EmptyVoid;
import com.puttysoftware.widgetwarren.objects.FinishMakingWand;
import com.puttysoftware.widgetwarren.objects.FireAmulet;
import com.puttysoftware.widgetwarren.objects.HotBoots;
import com.puttysoftware.widgetwarren.objects.IceAmulet;
import com.puttysoftware.widgetwarren.objects.LightGem;
import com.puttysoftware.widgetwarren.objects.LightWand;
import com.puttysoftware.widgetwarren.objects.MoonStone;
import com.puttysoftware.widgetwarren.objects.Player;
import com.puttysoftware.widgetwarren.objects.SlipperyBoots;
import com.puttysoftware.widgetwarren.objects.SunStone;
import com.puttysoftware.widgetwarren.objects.TeleportWand;
import com.puttysoftware.widgetwarren.objects.Wall;
import com.puttysoftware.widgetwarren.objects.WallBreakingWand;
import com.puttysoftware.widgetwarren.objects.WallMakingWand;
import com.puttysoftware.widgetwarren.resourcemanagers.ImageConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.MusicManager;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class GameManager implements MazeEffectConstants {
    // Fields
    private MazeObject objectBeingUsed;
    private GenericBow activeBow;
    private ObjectInventory objectInv, savedObjectInv;
    private boolean pullInProgress;
    private boolean using;
    private int lastUsedObjectIndex;
    private int lastUsedBowIndex;
    private boolean savedGameFlag;
    private int activeArrowType;
    private int poisonCounter;
    private final PlayerLocationManager plMgr;
    private final ScoreTracker st;
    private final MazeEffectManager em;
    private final GameGUI gui;
    private boolean delayedDecayActive;
    private MazeObject delayedDecayObject;
    private final MazeObject player;
    private boolean actingRemotely;
    private boolean teleporting;
    private int[] remoteCoords;
    private boolean autoFinishEnabled;
    private int autoFinishThreshold;
    private int alternateAutoFinishThreshold;
    private boolean stateChanged;

    // Constructors
    public GameManager() {
        this.plMgr = new PlayerLocationManager();
        this.st = new ScoreTracker();
        this.em = new MazeEffectManager();
        this.gui = new GameGUI(this.em);
        this.setPullInProgress(false);
        this.setUsingAnItem(false);
        this.player = new Player();
        this.lastUsedObjectIndex = 0;
        this.lastUsedBowIndex = WidgetWarren.getApplication().getObjects()
                .getAllBowNames().length - 1;
        this.savedGameFlag = false;
        this.delayedDecayActive = false;
        this.delayedDecayObject = null;
        this.actingRemotely = false;
        this.remoteCoords = new int[3];
        this.teleporting = false;
        this.activeArrowType = ArrowTypeConstants.ARROW_TYPE_PLAIN;
        this.stateChanged = true;
        this.poisonCounter = 0;
        this.activeBow = new Bow();
        this.autoFinishEnabled = false;
        this.autoFinishThreshold = 0;
        this.alternateAutoFinishThreshold = 0;
    }

    // Methods
    public boolean newGame() {
        return true;
    }

    public void stateChanged() {
        this.stateChanged = true;
    }

    public void enableTrueSight() {
        final int u = this.plMgr.getPlayerLocationX();
        final int v = this.plMgr.getPlayerLocationY();
        final int z = this.plMgr.getPlayerLocationZ();
        this.gui.enableTrueSight(u, v, z);
    }

    public void disableTrueSight() {
        final int u = this.plMgr.getPlayerLocationX();
        final int v = this.plMgr.getPlayerLocationY();
        final int z = this.plMgr.getPlayerLocationZ();
        this.gui.disableTrueSight(u, v, z);
    }

    public PlayerLocationManager getPlayerManager() {
        return this.plMgr;
    }

    private GameViewingWindowManager getViewManager() {
        return this.gui.getViewManager();
    }

    void arrowDone() {
        this.gui.setArrowActive(false);
        this.objectInv.useBow(this.activeBow);
        this.gui.updateStats();
        this.checkGameOver();
    }

    public MazeObject getSavedMazeObject() {
        return this.player.getSavedObject();
    }

    public boolean isFloorBelow() {
        final Application app = WidgetWarren.getApplication();
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
        final Application app = WidgetWarren.getApplication();
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

    public boolean doesFloorExist(final int floor) {
        final Application app = WidgetWarren.getApplication();
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

    void setTeleportingOff() {
        this.teleporting = false;
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
        this.gui.setStatusMessage(msg);
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
        final MazeObject acted = WidgetWarren.getApplication().getMazeManager()
                .getMazeObject(x, y, z, MazeConstants.LAYER_OBJECT);
        acted.preMoveAction(false, x, y, this.objectInv);
        acted.postMoveAction(false, x, y, this.objectInv);
        if (acted.doesChainReact()) {
            acted.chainReactionAction(x, y, z);
        }
    }

    public void doClockwiseRotate(final int r) {
        final Maze m = WidgetWarren.getApplication().getMazeManager().getMaze();
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
            WidgetWarren.getApplication().showMessage("Rotation failed!");
        }
    }

    public void findPlayerAndAdjust() {
        // Find the player, adjust player location
        final Maze m = WidgetWarren.getApplication().getMazeManager().getMaze();
        final int w = this.plMgr.getPlayerLocationW();
        m.findPlayer();
        this.plMgr.setPlayerLocation(m.getFindResultColumn(),
                m.getFindResultRow(), m.getFindResultFloor(), w);
        this.resetViewingWindow();
        this.redrawMaze();
    }

    private void fireStepActions() {
        final Maze m = WidgetWarren.getApplication().getMazeManager().getMaze();
        if (m.getPoisonPower() > 0) {
            this.poisonCounter++;
            if (this.poisonCounter >= m.getPoisonPower()) {
                // Poison
                this.poisonCounter = 0;
                m.doPoisonDamage();
            }
        }
        this.objectInv.fireStepActions();
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        m.updateVisibleSquares(px, py, pz);
    }

    public void doCounterclockwiseRotate(final int r) {
        final Maze m = WidgetWarren.getApplication().getMazeManager().getMaze();
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
            WidgetWarren.getApplication().showMessage("Rotation failed!");
        }
    }

    public void fireArrow(final int x, final int y) {
        if (this.objectInv.getUses(this.activeBow) == 0) {
            WidgetWarren.getApplication().showMessage("You're out of arrows!");
        } else {
            final ArrowTask at = new ArrowTask(x, y, this.activeArrowType);
            this.gui.setArrowActive(true);
            at.start();
        }
    }

    public void updatePositionRelative(final int dirX, final int dirY) {
        this.actingRemotely = false;
        boolean redrawsSuspended = true;
        int px = this.plMgr.getPlayerLocationX();
        int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final int[] mod = this.doEffects(dirX, dirY);
        final int fX = mod[0];
        final int fY = mod[1];
        final Application app = WidgetWarren.getApplication();
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
        final boolean isXNonZero = fX != 0;
        final boolean isYNonZero = fY != 0;
        int pullX = 0, pullY = 0, pushX = 0, pushY = 0;
        if (isXNonZero) {
            final int signX = (int) Math.signum(fX);
            pushX = (Math.abs(fX) + 1) * signX;
            pullX = (Math.abs(fX) - 1) * signX;
        }
        if (isYNonZero) {
            final int signY = (int) Math.signum(fY);
            pushY = (Math.abs(fY) + 1) * signY;
            pullY = (Math.abs(fY) - 1) * signY;
        }
        do {
            try {
                try {
                    o = m.getCell(px + fX, py + fY, pz,
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
                    nextBelow = m.getCell(px + fX, py + fY, pz,
                            MazeConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextBelow = new Empty();
                }
                try {
                    nextAbove = m.getCell(px + fX, py + fY, pz,
                            MazeConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextAbove = new Wall();
                }
                try {
                    previousBelow = m.getCell(px - fX, py - fY, pz,
                            MazeConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    previousBelow = new Empty();
                }
                try {
                    nextNextBelow = m.getCell(px + 2 * fX, py + 2 * fY, pz,
                            MazeConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextNextBelow = new Empty();
                }
                try {
                    nextNextAbove = m.getCell(px + 2 * fX, py + 2 * fY, pz,
                            MazeConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextNextAbove = new Wall();
                }
                try {
                    proceed = o.preMoveAction(true, px + fX, py + fY,
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
                this.getViewManager().saveViewingWindow();
                try {
                    if (this.checkSolid(fX + px, fY + py,
                            this.player.getSavedObject(), below, nextBelow,
                            nextAbove)) {
                        if (this.delayedDecayActive) {
                            this.doDelayedDecay();
                        }
                        m.setCell(this.player.getSavedObject(), px, py, pz,
                                MazeConstants.LAYER_OBJECT);
                        acted = new Empty();
                        try {
                            acted = m.getCell(px - fX, py - fY, pz,
                                    MazeConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException ae) {
                            // Do nothing
                        }
                        if (acted.isPullable() && this.isPullInProgress()) {
                            if (!this.checkPull(fX, fY, pullX, pullY, acted,
                                    previousBelow, below,
                                    this.player.getSavedObject())) {
                                // Pull failed - object can't move that way
                                acted.pullFailedAction(this.objectInv, fX, fY,
                                        pullX, pullY);
                                this.decayEffects();
                                proceed = false;
                            }
                        } else if (!acted.isPullable()
                                && this.isPullInProgress()) {
                            // Pull failed - object not pullable
                            acted.pullFailedAction(this.objectInv, fX, fY,
                                    pullX, pullY);
                            this.decayEffects();
                            proceed = false;
                        }
                        this.plMgr.offsetPlayerLocationX(fX);
                        this.plMgr.offsetPlayerLocationY(fY);
                        px += fX;
                        py += fY;
                        this.getViewManager().offsetViewingWindowLocationX(fY);
                        this.getViewManager().offsetViewingWindowLocationY(fX);
                        this.player.setSavedObject(m.getCell(px, py, pz,
                                MazeConstants.LAYER_OBJECT));
                        m.setCell(this.player, px, py, pz,
                                MazeConstants.LAYER_OBJECT);
                        this.decayEffects();
                        app.getMazeManager().setDirty(true);
                        this.fireStepActions();
                        groundInto = m.getCell(px, py, pz,
                                MazeConstants.LAYER_GROUND);
                        m.setCell(groundInto, px, py, pz,
                                MazeConstants.LAYER_GROUND);
                        if (groundInto.overridesDefaultPostMove()) {
                            groundInto.postMoveAction(false, px, py,
                                    this.objectInv);
                            if (!this.player.getSavedObject().isOfType(
                                    TypeConstants.TYPE_PASS_THROUGH)) {
                                this.player.getSavedObject().postMoveAction(
                                        false, px, py, this.objectInv);
                            }
                        } else {
                            this.player.getSavedObject().postMoveAction(false,
                                    px, py, this.objectInv);
                        }
                    } else {
                        acted = m.getCell(px + fX, py + fY, pz,
                                MazeConstants.LAYER_OBJECT);
                        if (acted.isPushable()) {
                            if (this.checkPush(fX, fY, pushX, pushY, acted,
                                    nextBelow, nextNextBelow, nextNextAbove)) {
                                if (this.delayedDecayActive) {
                                    this.doDelayedDecay();
                                }
                                m.setCell(this.player.getSavedObject(), px, py,
                                        pz, MazeConstants.LAYER_OBJECT);
                                this.plMgr.offsetPlayerLocationX(fX);
                                this.plMgr.offsetPlayerLocationY(fY);
                                px += fX;
                                py += fY;
                                this.getViewManager()
                                        .offsetViewingWindowLocationX(fY);
                                this.getViewManager()
                                        .offsetViewingWindowLocationY(fX);
                                this.player.setSavedObject(m.getCell(px, py, pz,
                                        MazeConstants.LAYER_OBJECT));
                                m.setCell(this.player, px, py, pz,
                                        MazeConstants.LAYER_OBJECT);
                                this.decayEffects();
                                app.getMazeManager().setDirty(true);
                                this.fireStepActions();
                                groundInto = m.getCell(px, py, pz,
                                        MazeConstants.LAYER_GROUND);
                                m.setCell(groundInto, px, py, pz,
                                        MazeConstants.LAYER_GROUND);
                                if (groundInto.overridesDefaultPostMove()) {
                                    groundInto.postMoveAction(false, px, py,
                                            this.objectInv);
                                    if (!this.player.getSavedObject().isOfType(
                                            TypeConstants.TYPE_PASS_THROUGH)) {
                                        this.player.getSavedObject()
                                                .postMoveAction(false, px, py,
                                                        this.objectInv);
                                    }
                                } else {
                                    this.player.getSavedObject().postMoveAction(
                                            false, px, py, this.objectInv);
                                }
                            } else {
                                // Push failed - object can't move that way
                                acted.pushFailedAction(this.objectInv, fX, fY,
                                        pushX, pushY);
                                this.decayEffects();
                                this.fireStepActions();
                                proceed = false;
                            }
                        } else if (acted.doesChainReact()) {
                            acted.chainReactionAction(px + fX, py + fY, pz);
                        } else {
                            // Move failed - object is solid in that direction
                            this.fireMoveFailedActions(px + fX, py + fY,
                                    this.player.getSavedObject(), below,
                                    nextBelow, nextAbove);
                            this.decayEffects();
                            this.fireStepActions();
                            proceed = false;
                        }
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.getViewManager().restoreViewingWindow();
                    this.plMgr.restorePlayerLocation();
                    m.setCell(this.player, this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(),
                            this.plMgr.getPlayerLocationZ(),
                            MazeConstants.LAYER_OBJECT);
                    // Move failed - attempted to go outside the maze
                    o.moveFailedAction(false, this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(), this.objectInv);
                    this.decayEffects();
                    this.fireStepActions();
                    WidgetWarren.getApplication()
                            .showMessage("Can't go that way");
                    o = new Empty();
                    proceed = false;
                }
            } else {
                // Move failed - pre-move check failed
                o.moveFailedAction(false, px + fX, py + fY, this.objectInv);
                this.decayEffects();
                this.fireStepActions();
                proceed = false;
            }
            if (redrawsSuspended && !this.checkLoopCondition(proceed, fX, fY,
                    groundInto, below, nextBelow, nextAbove)) {
                // Redraw post-suspend
                this.redrawMaze();
                redrawsSuspended = false;
            }
        } while (this.checkLoopCondition(proceed, fX, fY, groundInto, below,
                nextBelow, nextAbove));
        this.gui.updateStats();
        this.checkGameOver();
        // Check for auto-finish
        if (this.autoFinishEnabled) {
            // Normal auto-finish
            final int ssCount = this.objectInv.getItemCount(new SunStone());
            this.gui.updateAutoFinishProgress(ssCount);
            if (ssCount >= this.autoFinishThreshold) {
                // Auto-Finish
                SoundManager.playSound(
                        SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                        SoundConstants.SOUND_FINISH);
                this.solvedLevel();
            }
            // Alternate auto-finish
            final int msCount = this.objectInv.getItemCount(new MoonStone());
            this.gui.updateAlternateAutoFinishProgress(msCount);
            if (msCount >= this.alternateAutoFinishThreshold) {
                // Auto-Finish
                SoundManager.playSound(
                        SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                        SoundConstants.SOUND_FINISH);
                this.solvedLevelAlternate();
            }
        }
    }

    private boolean checkLoopCondition(final boolean proceed, final int x,
            final int y, final MazeObject groundInto, final MazeObject below,
            final MazeObject nextBelow, final MazeObject nextAbove) {
        // Handle slippery boots and ice amulet
        if (this.objectInv.isItemThere(new SlipperyBoots())
                || this.objectInv.isItemThere(new IceAmulet())) {
            return proceed && this.checkSolid(x, y,
                    this.player.getSavedObject(), below, nextBelow, nextAbove);
        } else {
            return proceed
                    && !groundInto.hasFrictionConditionally(this.objectInv,
                            false)
                    && this.checkSolid(x, y, this.player.getSavedObject(),
                            below, nextBelow, nextAbove);
        }
    }

    public void backUpPlayer() {
        final Maze m = WidgetWarren.getApplication().getMazeManager().getMaze();
        final int pz = this.plMgr.getPlayerLocationZ();
        this.plMgr.restorePlayerLocation();
        this.getViewManager().restoreViewingWindow();
        final int opx = this.plMgr.getPlayerLocationX();
        final int opy = this.plMgr.getPlayerLocationY();
        this.player.setSavedObject(
                m.getCell(opx, opy, pz, MazeConstants.LAYER_OBJECT));
        m.setCell(this.player, opx, opy, pz, MazeConstants.LAYER_OBJECT);
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
            final int pushY, final MazeObject acted, final MazeObject nextBelow,
            final MazeObject nextNextBelow, final MazeObject nextNextAbove) {
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
            final int pushY, final GenericMovableObject o) {
        final int xInc = (int) Math.signum(x), yInc = (int) Math.signum(y);
        int cumPushX = pushX, cumPushY = pushY, cumX = x, cumY = y;
        final Application app = WidgetWarren.getApplication();
        final MazeManager mm = app.getMazeManager();
        MazeObject there = mm.getMazeObject(
                this.plMgr.getPlayerLocationX() + cumX,
                this.plMgr.getPlayerLocationY() + cumY,
                this.plMgr.getPlayerLocationZ(), MazeConstants.LAYER_GROUND);
        if (there != null) {
            do {
                this.movePushedObjectPosition(cumX, cumY, cumPushX, cumPushY, o,
                        there);
                cumX += xInc;
                cumY += yInc;
                cumPushX += xInc;
                cumPushY += yInc;
                there = mm.getMazeObject(this.plMgr.getPlayerLocationX() + cumX,
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
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(o.getSavedObject(), this.plMgr.getPlayerLocationX() + x,
                    this.plMgr.getPlayerLocationY() + y,
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            m.setCell(o, this.plMgr.getPlayerLocationX() + pushX,
                    this.plMgr.getPlayerLocationY() + pushY,
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
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
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(o.getSavedObject(), this.plMgr.getPlayerLocationX() - x,
                    this.plMgr.getPlayerLocationY() - y,
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            m.setCell(o, this.plMgr.getPlayerLocationX() - pullX,
                    this.plMgr.getPlayerLocationY() - pullY,
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePushedIntoPositionAbsolute(final int x, final int y,
            final int z, final int x2, final int y2, final int z2,
            final GenericMovableObject pushedInto, final MazeObject source) {
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                final MazeObject saved = m.getCell(x, y, z,
                        MazeConstants.LAYER_OBJECT);
                m.setCell(pushedInto, x, y, z, MazeConstants.LAYER_OBJECT);
                pushedInto.setSavedObject(saved);
                m.setCell(source, x2, y2, z2, MazeConstants.LAYER_OBJECT);
                saved.pushIntoAction(this.objectInv, pushedInto, x2, y2,
                        z2 - 1);
                this.redrawMaze();
                app.getMazeManager().setDirty(true);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.setCell(new Empty(), x2, y2, z2, MazeConstants.LAYER_OBJECT);
        }
    }

    public boolean tryUpdatePositionRelative(final int x, final int y) {
        try {
            final Application app = WidgetWarren.getApplication();
            final Maze m = app.getMazeManager().getMaze();
            final MazeObject below = m.getCell(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_GROUND);
            final MazeObject nextBelow = m.getCell(
                    this.plMgr.getPlayerLocationX() + x,
                    this.plMgr.getPlayerLocationY() + y,
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_GROUND);
            final MazeObject nextAbove = m.getCell(
                    this.plMgr.getPlayerLocationX() + x,
                    this.plMgr.getPlayerLocationY() + y,
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            return this.checkSolid(x, y, this.player.getSavedObject(), below,
                    nextBelow, nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public boolean tryUpdatePositionAbsolute(final int x, final int y,
            final int z) {
        try {
            final Application app = WidgetWarren.getApplication();
            final Maze m = app.getMazeManager().getMaze();
            final MazeObject below = m.getCell(this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_GROUND);
            final MazeObject nextBelow = m.getCell(x, y, z,
                    MazeConstants.LAYER_GROUND);
            final MazeObject nextAbove = m.getCell(x, y, z,
                    MazeConstants.LAYER_OBJECT);
            return this.checkSolidAbsolute(this.player.getSavedObject(), below,
                    nextBelow, nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public void updatePositionAbsolute(final int x, final int y, final int z) {
        final Application app = WidgetWarren.getApplication();
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
        this.getViewManager().saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                m.setCell(this.player.getSavedObject(),
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                this.plMgr.setPlayerLocation(x, y, z, 0);
                this.getViewManager().setViewingWindowLocationX(
                        this.plMgr.getPlayerLocationY()
                                - this.getViewManager().getOffsetFactorX());
                this.getViewManager().setViewingWindowLocationY(
                        this.plMgr.getPlayerLocationX()
                                - this.getViewManager().getOffsetFactorY());
                this.player.setSavedObject(
                        m.getCell(this.plMgr.getPlayerLocationX(),
                                this.plMgr.getPlayerLocationY(),
                                this.plMgr.getPlayerLocationZ(),
                                MazeConstants.LAYER_OBJECT));
                m.setCell(this.player, this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                this.redrawMaze();
                app.getMazeManager().setDirty(true);
                this.player.getSavedObject().postMoveAction(false, x, y,
                        this.objectInv);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.plMgr.restorePlayerLocation();
            this.getViewManager().restoreViewingWindow();
            m.setCell(this.player, this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            WidgetWarren.getApplication()
                    .showMessage("Can't go outside the maze");
        } catch (final NullPointerException np) {
            this.plMgr.restorePlayerLocation();
            this.getViewManager().restoreViewingWindow();
            m.setCell(this.player, this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            WidgetWarren.getApplication()
                    .showMessage("Can't go outside the maze");
        }
    }

    public void updatePositionAbsoluteNoEvents(final int x, final int y,
            final int z, final int w) {
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        this.plMgr.savePlayerLocation();
        this.getViewManager().saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                m.setCell(this.player.getSavedObject(),
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                this.plMgr.setPlayerLocation(x, y, z, w);
                this.getViewManager().setViewingWindowLocationX(
                        this.plMgr.getPlayerLocationY()
                                - this.getViewManager().getOffsetFactorX());
                this.getViewManager().setViewingWindowLocationY(
                        this.plMgr.getPlayerLocationX()
                                - this.getViewManager().getOffsetFactorY());
                this.player.setSavedObject(
                        m.getCell(this.plMgr.getPlayerLocationX(),
                                this.plMgr.getPlayerLocationY(),
                                this.plMgr.getPlayerLocationZ(),
                                MazeConstants.LAYER_OBJECT));
                final MazeObject p = this.player;
                p.setSavedObject(this.player.getSavedObject());
                m.setCell(p, this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                this.redrawMaze();
                app.getMazeManager().setDirty(true);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.plMgr.restorePlayerLocation();
            this.getViewManager().restoreViewingWindow();
            m.setCell(this.player, this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            WidgetWarren.getApplication()
                    .showMessage("Can't go outside the maze");
        } catch (final NullPointerException np) {
            this.plMgr.restorePlayerLocation();
            this.getViewManager().restoreViewingWindow();
            m.setCell(this.player, this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            WidgetWarren.getApplication()
                    .showMessage("Can't go outside the maze");
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

    public void rebuildGrid() {
        final int u = this.plMgr.getPlayerLocationX();
        final int v = this.plMgr.getPlayerLocationY();
        final int z = this.plMgr.getPlayerLocationZ();
        this.gui.rebuildGrid(u, v, z);
    }

    public void redrawMaze() {
        final int u = this.plMgr.getPlayerLocationX();
        final int v = this.plMgr.getPlayerLocationY();
        final int z = this.plMgr.getPlayerLocationZ();
        this.gui.redrawMaze(u, v, z);
    }

    void redrawOneSquare(final int x, final int y, final boolean useDelay,
            final MazeObject obj3) {
        final int u = this.plMgr.getPlayerLocationX();
        final int v = this.plMgr.getPlayerLocationY();
        final int z = this.plMgr.getPlayerLocationZ();
        this.gui.redrawOneSquare(x, y, useDelay, obj3, u, v, z);
    }

    public void resetViewingWindowAndPlayerLocation() {
        this.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        final int u = this.plMgr.getPlayerLocationX();
        final int v = this.plMgr.getPlayerLocationY();
        this.gui.resetViewingWindow(u, v);
    }

    public void resetPlayerLocation() {
        this.resetPlayerLocation(0);
    }

    public void resetPlayerLocation(final int level) {
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        m.switchLevel(level);
        this.plMgr.setPlayerLocation(m.getStartColumn(), m.getStartRow(),
                m.getStartFloor(), level);
    }

    public void timerExpiredAction() {
        this.resetCurrentLevel();
    }

    public void resetCurrentLevel() {
        this.resetLevel(this.plMgr.getPlayerLocationW());
    }

    public void resetGameState() {
        this.deactivateAllEffects();
        final Application app = WidgetWarren.getApplication();
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
        final Application app = WidgetWarren.getApplication();
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
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final boolean levelExists = m.doesLevelExist(level);
        if (levelExists) {
            m.switchLevel(level);
            this.plMgr.setPlayerLocationW(level);
            this.findPlayerAndAdjust();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedLevel() {
        this.deactivateAllEffects();
        final Application app = WidgetWarren.getApplication();
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

    public void solvedLevelAlternate() {
        this.deactivateAllEffects();
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        CommonDialogs.showTitledDialog(m.getLevelEndMessage(),
                m.getLevelTitle());
        final boolean levelExists;
        if (m.useAlternateOffset()) {
            levelExists = m.doesLevelExistOffset(m.getAlternateNextLevel());
        } else {
            levelExists = m.doesLevelExist(m.getAlternateNextLevel());
        }
        if (levelExists) {
            m.restore();
            m.resetTimer();
            // Activate first moving finish, if one exists
            m.deactivateAllMovingFinishes();
            m.activateFirstMovingFinish();
            if (m.useOffset()) {
                m.switchLevelOffset(m.getAlternateNextLevel());
            } else {
                m.switchLevel(m.getAlternateNextLevel());
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
        final Application app = WidgetWarren.getApplication();
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
        final Maze m = WidgetWarren.getApplication().getMazeManager().getMaze();
        m.resetTimer();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WIN_GAME);
        CommonDialogs.showTitledDialog(m.getMazeEndMessage(), m.getMazeTitle());
        if (this.st.checkScore()) {
            WidgetWarren.getApplication().playHighScoreSound();
        }
        this.st.commitScore();
        this.exitGame();
    }

    public void exitGame() {
        this.stateChanged = true;
        this.deactivateAllEffects();
        final Application app = WidgetWarren.getApplication();
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
        MusicManager.stopMusic();
        this.hideOutput();
        app.setInGame(false);
        app.getGUIManager().showGUI();
    }

    public void checkGameOver() {
        if (!WidgetWarren.getApplication().getMazeManager().getMaze()
                .isAlive()) {
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
        return this.gui.getOutputFrame();
    }

    public void decay() {
        if (this.actingRemotely) {
            WidgetWarren.getApplication().getMazeManager().getMaze().setCell(
                    new Empty(), this.remoteCoords[0], this.remoteCoords[1],
                    this.remoteCoords[2], MazeConstants.LAYER_OBJECT);
        } else {
            this.player.setSavedObject(new Empty());
        }
    }

    public void decayTo(final MazeObject decay) {
        if (this.actingRemotely) {
            WidgetWarren.getApplication().getMazeManager().getMaze().setCell(
                    decay, this.remoteCoords[0], this.remoteCoords[1],
                    this.remoteCoords[2], MazeConstants.LAYER_OBJECT);
        } else {
            this.player.setSavedObject(decay);
        }
    }

    private void doDelayedDecay() {
        if (this.actingRemotely) {
            WidgetWarren.getApplication().getMazeManager().getMaze().setCell(
                    this.delayedDecayObject, this.remoteCoords[0],
                    this.remoteCoords[1], this.remoteCoords[2],
                    MazeConstants.LAYER_OBJECT);
        } else {
            this.player.setSavedObject(this.delayedDecayObject);
        }
        this.delayedDecayActive = false;
    }

    public void delayedDecayTo(final MazeObject obj) {
        this.delayedDecayActive = true;
        this.delayedDecayObject = obj;
    }

    public void morph(final MazeObject morphInto, final int x, final int y,
            final int z) {
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, x, y, z, morphInto.getLayer());
            this.redrawMaze();
            app.getMazeManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morph(final MazeObject morphInto, final int x, final int y,
            final int z, final String msg) {
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, x, y, z, morphInto.getLayer());
            WidgetWarren.getApplication().showMessage(msg);
            this.keepNextMessage();
            this.redrawMaze();
            app.getMazeManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morph(final MazeObject morphInto, final int x, final int y,
            final int z, final int e) {
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, x, y, z, e);
            this.redrawMaze();
            app.getMazeManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morphOther(final MazeObject morphInto, final int x, final int y,
            final int e) {
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, this.plMgr.getPlayerLocationX() + x,
                    this.plMgr.getPlayerLocationY() + y,
                    this.plMgr.getPlayerLocationZ(), e);
            this.redrawMaze();
            app.getMazeManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void keepNextMessage() {
        this.gui.keepNextMessage();
    }

    public void showInventoryDialog() {
        final String[] invString = this.objectInv
                .generateInventoryStringArray();
        CommonDialogs.showInputDialog("Inventory", "Inventory", invString,
                invString[0]);
    }

    public void showUseDialog() {
        int x;
        final MazeObjectList list = WidgetWarren.getApplication().getObjects();
        final MazeObject[] choices = list.getAllUsableObjects();
        final String[] userChoices = this.objectInv.generateUseStringArray();
        final String result = CommonDialogs.showInputDialog("Use which item?",
                "WidgetWarren", userChoices,
                userChoices[this.lastUsedObjectIndex]);
        try {
            for (x = 0; x < choices.length; x++) {
                if (result.equals(userChoices[x])) {
                    this.lastUsedObjectIndex = x;
                    this.objectBeingUsed = choices[x];
                    if (this.objectInv.getUses(this.objectBeingUsed) == 0) {
                        WidgetWarren.getApplication().showMessage(
                                "That item has no more uses left.");
                        this.setUsingAnItem(false);
                    } else {
                        WidgetWarren.getApplication()
                                .showMessage("Click to set target");
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
        final MazeObjectList list = WidgetWarren.getApplication().getObjects();
        final MazeObject[] choices = list.getAllBows();
        final String[] userChoices = this.objectInv.generateBowStringArray();
        final String result = CommonDialogs.showInputDialog(
                "Switch to which bow?", "WidgetWarren", userChoices,
                userChoices[this.lastUsedBowIndex]);
        try {
            for (x = 0; x < choices.length; x++) {
                if (result.equals(userChoices[x])) {
                    this.lastUsedBowIndex = x;
                    this.activeBow = (GenericBow) choices[x];
                    this.activeArrowType = this.activeBow.getArrowType();
                    if (this.objectInv.getUses(this.activeBow) == 0) {
                        WidgetWarren.getApplication()
                                .showMessage("That bow is out of arrows!");
                    } else {
                        WidgetWarren.getApplication().showMessage(
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

    public void useItemHandler(final int x, final int y) {
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final int xOffset = this.getViewManager().getViewingWindowLocationX()
                - this.getViewManager().getOffsetFactorX();
        final int yOffset = this.getViewManager().getViewingWindowLocationY()
                - this.getViewManager().getOffsetFactorY();
        final int destX = x / ImageConstants.getImageSize()
                + this.getViewManager().getViewingWindowLocationX() - xOffset
                + yOffset;
        final int destY = y / ImageConstants.getImageSize()
                + this.getViewManager().getViewingWindowLocationY() + xOffset
                - yOffset;
        final int destZ = this.plMgr.getPlayerLocationZ();
        if (this.usingAnItem() && app.getMode() == Application.STATUS_GAME) {
            final boolean visible = app.getMazeManager().getMaze()
                    .isSquareVisible(this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(), destX, destY);
            try {
                final MazeObject target = m.getCell(destX, destY, destZ,
                        MazeConstants.LAYER_OBJECT);
                final String name = this.objectBeingUsed.getName();
                if ((target.isSolid() || !visible)
                        && name.equals(new TeleportWand().getName())) {
                    this.setUsingAnItem(false);
                    WidgetWarren.getApplication()
                            .showMessage("Can't teleport there");
                }
                if (target.getName().equals(this.player.getName())) {
                    this.setUsingAnItem(false);
                    WidgetWarren.getApplication()
                            .showMessage("Don't aim at yourself!");
                }
                if (!target.isDestroyable()
                        && name.equals(new AnnihilationWand().getName())) {
                    this.setUsingAnItem(false);
                    WidgetWarren.getApplication()
                            .showMessage("Can't destroy that");
                }
                if (!target.isDestroyable()
                        && name.equals(new WallMakingWand().getName())) {
                    this.setUsingAnItem(false);
                    WidgetWarren.getApplication()
                            .showMessage("Can't create a wall there");
                }
                if (!target.isDestroyable()
                        && name.equals(new FinishMakingWand().getName())) {
                    this.setUsingAnItem(false);
                    WidgetWarren.getApplication()
                            .showMessage("Can't create a finish there");
                }
                if ((!target.isDestroyable()
                        || !target.isOfType(TypeConstants.TYPE_WALL))
                        && name.equals(new WallBreakingWand().getName())) {
                    this.setUsingAnItem(false);
                    WidgetWarren.getApplication().showMessage("Aim at a wall");
                }
                if ((!target.isDestroyable()
                        || !target.isOfType(TypeConstants.TYPE_TRAP))
                        && name.equals(new DisarmTrapWand().getName())) {
                    this.setUsingAnItem(false);
                    WidgetWarren.getApplication().showMessage("Aim at a trap");
                }
                if (!target.getName().equals(new Empty().getName())
                        && !target.getName().equals(new DarkGem().getName())
                        && name.equals(new LightWand().getName())) {
                    this.setUsingAnItem(false);
                    WidgetWarren.getApplication().showMessage(
                            "Aim at either an empty space or a Dark Gem");
                }
                if (!target.getName().equals(new Empty().getName())
                        && !target.getName().equals(new LightGem().getName())
                        && name.equals(new DarkWand().getName())) {
                    this.setUsingAnItem(false);
                    WidgetWarren.getApplication().showMessage(
                            "Aim at either an empty space or a Light Gem");
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.setUsingAnItem(false);
                WidgetWarren.getApplication()
                        .showMessage("Aim within the maze");
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
        WidgetWarren.getApplication().showMessage("Click to set destination");
    }

    void controllableTeleportHandler(final int x, final int y) {
        if (this.teleporting) {
            final int xOffset = this.getViewManager()
                    .getViewingWindowLocationX()
                    - this.getViewManager().getOffsetFactorX();
            final int yOffset = this.getViewManager()
                    .getViewingWindowLocationY()
                    - this.getViewManager().getOffsetFactorY();
            final int destX = x / ImageConstants.getImageSize()
                    + this.getViewManager().getViewingWindowLocationX()
                    - xOffset + yOffset;
            final int destY = y / ImageConstants.getImageSize()
                    + this.getViewManager().getViewingWindowLocationY()
                    + xOffset - yOffset;
            final int destZ = this.plMgr.getPlayerLocationZ();
            this.updatePositionAbsolute(destX, destY, destZ);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_TELEPORT);
            this.teleporting = false;
        }
    }

    public void identifyObject(final int x, final int y) {
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final int xOffset = this.getViewManager().getViewingWindowLocationX()
                - this.getViewManager().getOffsetFactorX();
        final int yOffset = this.getViewManager().getViewingWindowLocationY()
                - this.getViewManager().getOffsetFactorY();
        final int destX = x / ImageConstants.getImageSize()
                + this.getViewManager().getViewingWindowLocationX() - xOffset
                + yOffset;
        final int destY = y / ImageConstants.getImageSize()
                + this.getViewManager().getViewingWindowLocationY() + xOffset
                - yOffset;
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
            WidgetWarren.getApplication()
                    .showMessage(gameName2 + " on " + gameName1);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_IDENTIFY);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            final EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(destX, destY, destZ);
            WidgetWarren.getApplication().showMessage(ev.getGameName());
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_IDENTIFY);
        }
    }

    public void loadGameHookXML(final XDataReader mazeFile,
            final int formatVersion) throws IOException {
        final Application app = WidgetWarren.getApplication();
        this.objectInv = ObjectInventory.readInventoryXML(mazeFile,
                formatVersion);
        this.savedObjectInv = ObjectInventory.readInventoryXML(mazeFile,
                formatVersion);
        app.getMazeManager().setScoresFileName(mazeFile.readString());
        this.st.setScore(mazeFile.readLong());
    }

    public void saveGameHookXML(final XDataWriter mazeFile) throws IOException {
        final Application app = WidgetWarren.getApplication();
        this.objectInv.writeInventoryXML(mazeFile);
        this.savedObjectInv.writeInventoryXML(mazeFile);
        mazeFile.writeString(app.getMazeManager().getScoresFileName());
        mazeFile.writeLong(this.st.getScore());
    }

    public void playMaze() {
        final Application app = WidgetWarren.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        if (app.getMazeManager().getLoaded()) {
            app.getGUIManager().hideGUI();
            app.setInGame(true);
            if (this.stateChanged) {
                // Initialize only if the maze state has changed
                this.poisonCounter = 0;
                app.getMazeManager().getMaze().switchLevel(
                        app.getMazeManager().getMaze().getStartLevel());
                this.player.setSavedObject(new Empty());
                this.st.setScoreFile(app.getMazeManager().getScoresFileName());
                this.autoFinishEnabled = app.getMazeManager().getMaze()
                        .getAutoFinishThresholdEnabled();
                this.autoFinishThreshold = app.getMazeManager().getMaze()
                        .getAutoFinishThreshold();
                this.gui.initAutoFinishProgress();
                this.alternateAutoFinishThreshold = app.getMazeManager()
                        .getMaze().getAlternateAutoFinishThreshold();
                this.gui.initAlternateAutoFinishProgress();
                if (this.autoFinishEnabled) {
                    // Update progress bar
                    this.gui.setMaxAutoFinishProgress(this.autoFinishThreshold);
                    // Update alternate progress bar
                    this.gui.setMaxAlternateAutoFinishProgress(
                            this.alternateAutoFinishThreshold);
                }
                // Activate first moving finish, if one exists
                m.deactivateAllMovingFinishes();
                m.activateFirstMovingFinish();
                if (!this.savedGameFlag) {
                    this.saveObjectInventory();
                    this.st.resetScore(
                            app.getMazeManager().getScoresFileName());
                }
                this.stateChanged = false;
            }
            this.gui.configureBorderPane(this.em);
            CommonDialogs.showTitledDialog(m.getMazeStartMessage(),
                    m.getMazeTitle());
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.showOutput();
            this.gui.updateImages();
            this.gui.updateStats();
            this.checkGameOver();
            this.rebuildGrid();
            MusicManager.playExploringMusic();
        } else {
            CommonDialogs.showDialog("No Maze Opened");
        }
    }

    public void showOutput() {
        this.gui.showOutput();
    }

    public void hideOutput() {
        this.gui.hideOutput();
    }
}
