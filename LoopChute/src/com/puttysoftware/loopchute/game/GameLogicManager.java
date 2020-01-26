/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.game;

import java.io.IOException;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.generic.ArrowTypeConstants;
import com.puttysoftware.loopchute.generic.GenericBow;
import com.puttysoftware.loopchute.generic.GenericMovableObject;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.generic.MazeObjectList;
import com.puttysoftware.loopchute.generic.TypeConstants;
import com.puttysoftware.loopchute.maze.Maze;
import com.puttysoftware.loopchute.maze.MazeConstants;
import com.puttysoftware.loopchute.maze.MazeManager;
import com.puttysoftware.loopchute.objects.Empty;
import com.puttysoftware.loopchute.objects.EmptyVoid;
import com.puttysoftware.loopchute.objects.HotBoots;
import com.puttysoftware.loopchute.objects.MoonStone;
import com.puttysoftware.loopchute.objects.PasswallBoots;
import com.puttysoftware.loopchute.objects.Player;
import com.puttysoftware.loopchute.objects.SlipperyBoots;
import com.puttysoftware.loopchute.objects.SunStone;
import com.puttysoftware.loopchute.objects.TeleportWand;
import com.puttysoftware.loopchute.objects.Wall;
import com.puttysoftware.loopchute.objects.WallBreakingWand;
import com.puttysoftware.loopchute.resourcemanagers.GraphicsManager;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class GameLogicManager {
    // Fields
    private MazeObject savedMazeObject, previousSavedMazeObject;
    private ObjectInventory objectInv, savedObjectInv;
    private boolean pullInProgress;
    private boolean savedGameFlag;
    private final GameViewingWindowManager vwMgr;
    private boolean teleporting;
    private boolean autoFinishThresholdEnabled;
    private boolean alternateAutoFinishThresholdEnabled;
    private int autoFinishThreshold;
    private int alternateAutoFinishThreshold;
    private boolean stateChanged;
    private final GameGUIManager gui;
    private int activeArrowType;
    private boolean arrowActive;
    private boolean using;
    private int lastUsedObjectIndex;
    private MazeObject objectBeingUsed;

    // Constructors
    public GameLogicManager() {
        this.objectInv = new ObjectInventory();
        this.vwMgr = new GameViewingWindowManager();
        this.gui = new GameGUIManager();
        this.setPullInProgress(false);
        this.savedMazeObject = new Empty();
        this.savedGameFlag = false;
        this.teleporting = false;
        this.stateChanged = true;
        this.autoFinishThreshold = 0;
        this.alternateAutoFinishThreshold = 0;
        this.arrowActive = false;
        this.activeArrowType = ArrowTypeConstants.ARROW_TYPE_PLAIN;
        this.using = false;
        this.lastUsedObjectIndex = 0;
        this.objectBeingUsed = null;
    }

    // Methods
    public boolean newGame() {
        return true;
    }

    private void setArrowType(final int type) {
        this.activeArrowType = type;
    }

    void arrowDone() {
        this.arrowActive = false;
    }

    public void fireArrow(final int x, final int y) {
        if (!this.arrowActive) {
            final ArrowTask at = new ArrowTask(x, y, this.activeArrowType);
            this.arrowActive = true;
            at.start();
        }
    }

    public boolean usingAnItem() {
        return this.using;
    }

    public void setUsingAnItem(final boolean isUsing) {
        this.using = isUsing;
    }

    public void viewingWindowSizeChanged() {
        this.gui.viewingWindowSizeChanged();
    }

    public void stateChanged() {
        this.stateChanged = true;
    }

    public GameViewingWindowManager getViewManager() {
        return this.vwMgr;
    }

    public boolean isFloorBelow() {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.getCell(m.getPlayerLocationX(), m.getPlayerLocationY(),
                    m.getPlayerLocationZ() - 1, MazeConstants.LAYER_OBJECT);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        } catch (final NullPointerException np) {
            return false;
        }
    }

    public boolean isFloorAbove() {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.getCell(m.getPlayerLocationX(), m.getPlayerLocationY(),
                    m.getPlayerLocationZ() + 1, MazeConstants.LAYER_OBJECT);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        } catch (final NullPointerException np) {
            return false;
        }
    }

    public boolean doesFloorExist(final int floor) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.getCell(m.getPlayerLocationX(), m.getPlayerLocationY(), floor,
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

    ObjectInventory getObjectInventory() {
        return this.objectInv;
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

    public boolean isTeleporting() {
        return this.teleporting;
    }

    void setTeleporting(final boolean tele) {
        this.teleporting = tele;
    }

    public final void setPullInProgress(final boolean pulling) {
        this.pullInProgress = pulling;
    }

    public boolean isPullInProgress() {
        return this.pullInProgress;
    }

    public void setStatusMessage(final String msg) {
        this.gui.setStatusMessage(msg);
    }

    private void fireStepActions() {
        final Maze m = LoopChute.getApplication().getMazeManager().getMaze();
        this.objectInv.fireStepActions();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        m.updateVisibleSquares(px, py, pz);
    }

    public void updatePositionRelative(final int dirX, final int dirY) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        this.previousSavedMazeObject = this.savedMazeObject;
        boolean redrawsSuspended = false;
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        final int fX = dirX;
        final int fY = dirY;
        boolean proceed = false;
        MazeObject o = new Empty();
        final MazeObject groundInto = new Empty();
        MazeObject below = null;
        MazeObject nextBelow = null;
        MazeObject nextAbove = null;
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
                m.savePlayerLocation();
                this.vwMgr.saveViewingWindow();
                try {
                    if (this.checkSolid(this.savedMazeObject, below, nextBelow,
                            nextAbove)) {
                        redrawsSuspended = this.updatePositionRelativePull(dirX,
                                dirY);
                    } else {
                        redrawsSuspended = this.updatePositionRelativePush(dirX,
                                dirY);
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.vwMgr.restoreViewingWindow();
                    m.restorePlayerLocation();
                    // Move failed - attempted to go outside the maze
                    o.moveFailedAction(false, m.getPlayerLocationX(),
                            m.getPlayerLocationY(), this.objectInv);
                    this.fireStepActions();
                    LoopChute.getApplication().showMessage("Can't go that way");
                    o = new Empty();
                    proceed = false;
                }
            } else {
                // Move failed - pre-move check failed
                o.moveFailedAction(false, px + fX, py + fY, this.objectInv);
                this.fireStepActions();
                proceed = false;
            }
            if (redrawsSuspended && !this.checkLoopCondition(proceed,
                    groundInto, below, nextBelow, nextAbove)) {
                // Redraw post-suspend
                this.redrawMaze();
                redrawsSuspended = false;
            }
        } while (this.checkLoopCondition(proceed, groundInto, below, nextBelow,
                nextAbove));
        this.checkAutoFinish();
    }

    private boolean updatePositionRelativePush(final int dirX, final int dirY) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        boolean redrawsSuspended = false;
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        final int fX = dirX;
        final int fY = dirY;
        MazeObject acted = new Empty();
        MazeObject groundInto = new Empty();
        MazeObject below = null;
        MazeObject nextBelow = null;
        MazeObject nextAbove = null;
        MazeObject nextNextBelow = null;
        MazeObject nextNextAbove = null;
        final boolean isXNonZero = fX != 0;
        final boolean isYNonZero = fY != 0;
        int pushX = 0, pushY = 0;
        if (isXNonZero) {
            final int signX = (int) Math.signum(fX);
            pushX = (Math.abs(fX) + 1) * signX;
        }
        if (isYNonZero) {
            final int signY = (int) Math.signum(fY);
            pushY = (Math.abs(fY) + 1) * signY;
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
        acted = m.getCell(px + fX, py + fY, pz, MazeConstants.LAYER_OBJECT);
        if (acted.isPushable()) {
            if (this.checkPush(fX, fY, pushX, pushY, acted, nextBelow,
                    nextNextBelow, nextNextAbove)) {
                m.setCell(this.savedMazeObject, px, py, pz,
                        MazeConstants.LAYER_OBJECT);
                m.offsetPlayerLocationX(fX);
                m.offsetPlayerLocationY(fY);
                px += fX;
                py += fY;
                this.vwMgr.offsetViewingWindowLocationX(fY);
                this.vwMgr.offsetViewingWindowLocationY(fX);
                this.savedMazeObject = m.getCell(px, py, pz,
                        MazeConstants.LAYER_OBJECT);
                app.getMazeManager().setDirty(true);
                this.fireStepActions();
                if (this.objectInv.isItemThere(new PasswallBoots())) {
                    redrawsSuspended = true;
                } else {
                    this.redrawMaze();
                }
                groundInto = m.getCell(px, py, pz, MazeConstants.LAYER_GROUND);
                m.setCell(groundInto, px, py, pz, MazeConstants.LAYER_GROUND);
                if (groundInto.overridesDefaultPostMove()) {
                    groundInto.postMoveAction(false, px, py, this.objectInv);
                    if (!this.savedMazeObject
                            .isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                        this.savedMazeObject.postMoveAction(false, px, py,
                                this.objectInv);
                    }
                } else {
                    this.savedMazeObject.postMoveAction(false, px, py,
                            this.objectInv);
                }
            } else {
                // Push failed - object can't move that way
                acted.pushFailedAction(this.objectInv, fX, fY, pushX, pushY);
                this.fireStepActions();
            }
        } else if (acted.doesChainReact()) {
            acted.chainReactionAction(px + fX, py + fY, pz);
        } else {
            // Move failed - object is solid in that direction
            this.fireMoveFailedActions(px + fX, py + fY, this.savedMazeObject,
                    below, nextBelow, nextAbove);
            this.fireStepActions();
        }
        return redrawsSuspended;
    }

    private boolean updatePositionRelativePull(final int dirX, final int dirY) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        boolean redrawsSuspended = false;
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        final int fX = dirX;
        final int fY = dirY;
        MazeObject acted = new Empty();
        MazeObject groundInto = new Empty();
        MazeObject below = null;
        MazeObject previousBelow = null;
        final boolean isXNonZero = fX != 0;
        final boolean isYNonZero = fY != 0;
        int pullX = 0, pullY = 0;
        if (isXNonZero) {
            final int signX = (int) Math.signum(fX);
            pullX = (Math.abs(fX) - 1) * signX;
        }
        if (isYNonZero) {
            final int signY = (int) Math.signum(fY);
            pullY = (Math.abs(fY) - 1) * signY;
        }
        try {
            below = m.getCell(px, py, pz, MazeConstants.LAYER_GROUND);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            below = new Empty();
        }
        try {
            previousBelow = m.getCell(px - fX, py - fY, pz,
                    MazeConstants.LAYER_GROUND);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            previousBelow = new Empty();
        }
        m.setCell(this.savedMazeObject, px, py, pz, MazeConstants.LAYER_OBJECT);
        acted = new Empty();
        try {
            acted = m.getCell(px - fX, py - fY, pz, MazeConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
        if (acted.isPullable() && this.isPullInProgress()) {
            if (!this.checkPull(fX, fY, pullX, pullY, acted, previousBelow,
                    below, this.savedMazeObject)) {
                // Pull failed - object can't move that way
                acted.pullFailedAction(this.objectInv, fX, fY, pullX, pullY);
            }
        } else if (!acted.isPullable() && this.isPullInProgress()) {
            // Pull failed - object not pullable
            acted.pullFailedAction(this.objectInv, fX, fY, pullX, pullY);
        }
        m.offsetPlayerLocationX(fX);
        m.offsetPlayerLocationY(fY);
        px += fX;
        py += fY;
        this.vwMgr.offsetViewingWindowLocationX(fY);
        this.vwMgr.offsetViewingWindowLocationY(fX);
        this.savedMazeObject = m.getCell(px, py, pz,
                MazeConstants.LAYER_OBJECT);
        app.getMazeManager().setDirty(true);
        this.fireStepActions();
        if (this.objectInv.isItemThere(new PasswallBoots())) {
            redrawsSuspended = true;
        } else {
            this.redrawMaze();
        }
        groundInto = m.getCell(px, py, pz, MazeConstants.LAYER_GROUND);
        m.setCell(groundInto, px, py, pz, MazeConstants.LAYER_GROUND);
        if (groundInto.overridesDefaultPostMove()) {
            groundInto.postMoveAction(false, px, py, this.objectInv);
            if (!this.savedMazeObject
                    .isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                this.savedMazeObject.postMoveAction(false, px, py,
                        this.objectInv);
            }
        } else {
            this.savedMazeObject.postMoveAction(false, px, py, this.objectInv);
        }
        return redrawsSuspended;
    }

    public void backUpPlayer(final MazeObject backUpObject) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        m.setCell(backUpObject, m.getPlayerLocationX(), m.getPlayerLocationY(),
                m.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
        this.vwMgr.restoreViewingWindow();
        m.restorePlayerLocation();
        this.decayTo(this.previousSavedMazeObject);
        this.redrawMazeNoRebuild();
    }

    private void checkAutoFinish() {
        // Check for auto-finish
        // Normal auto-finish
        if (this.autoFinishThresholdEnabled) {
            final int ssCount = this.objectInv.getItemCount(new SunStone());
            this.gui.updateAutoFinishProgress(ssCount);
            if (ssCount >= this.autoFinishThreshold) {
                // Auto-Finish
                SoundManager.playSound(
                        SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                        SoundConstants.SOUND_FINISH);
                this.solvedLevel();
            }
        }
        // Alternate auto-finish
        if (this.alternateAutoFinishThresholdEnabled) {
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

    private boolean checkLoopCondition(final boolean proceed,
            final MazeObject groundInto, final MazeObject below,
            final MazeObject nextBelow, final MazeObject nextAbove) {
        // Handle slippery boots and ice amulet
        if (this.objectInv.isItemThere(new SlipperyBoots())) {
            return proceed && this.checkSolid(this.savedMazeObject, below,
                    nextBelow, nextAbove);
        } else {
            return proceed
                    && !groundInto.hasFrictionConditionally(this.objectInv,
                            false)
                    && this.checkSolid(this.savedMazeObject, below, nextBelow,
                            nextAbove);
        }
    }

    private boolean checkSolid(final MazeObject inside, final MazeObject below,
            final MazeObject nextBelow, final MazeObject nextAbove) {
        final boolean insideSolid = inside.isConditionallySolid(this.objectInv);
        final boolean belowSolid = below.isConditionallySolid(this.objectInv);
        boolean nextBelowSolid = nextBelow.isConditionallySolid(this.objectInv);
        // Handle hot boots and slippery boots
        if (this.objectInv.isItemThere(new HotBoots())
                || this.objectInv.isItemThere(new SlipperyBoots())) {
            nextBelowSolid = false;
        }
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
        final boolean insideSolid = inside.isConditionallySolid(this.objectInv);
        final boolean belowSolid = below.isConditionallySolid(this.objectInv);
        final boolean nextBelowSolid = nextBelow
                .isConditionallySolid(this.objectInv);
        final boolean nextAboveSolid = nextAbove
                .isConditionallySolid(this.objectInv);
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
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
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
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
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
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final MazeManager mm = app.getMazeManager();
        MazeObject there = mm.getMazeObject(m.getPlayerLocationX() + cumX,
                m.getPlayerLocationY() + cumY, m.getPlayerLocationZ(),
                MazeConstants.LAYER_GROUND);
        if (there != null) {
            do {
                this.movePushedObjectPosition(cumX, cumY, cumPushX, cumPushY, o,
                        there);
                cumX += xInc;
                cumY += yInc;
                cumPushX += xInc;
                cumPushY += yInc;
                there = mm.getMazeObject(m.getPlayerLocationX() + cumX,
                        m.getPlayerLocationY() + cumY, m.getPlayerLocationZ(),
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
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(o.getSavedObject(), m.getPlayerLocationX() + x,
                    m.getPlayerLocationY() + y, m.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            m.setCell(o, m.getPlayerLocationX() + pushX,
                    m.getPlayerLocationY() + pushY, m.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            if (g.overridesDefaultPostMove()) {
                g.postMoveAction(false, m.getPlayerLocationX(),
                        m.getPlayerLocationY(), this.objectInv);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePulledPosition(final int x, final int y, final int pullX,
            final int pullY, final GenericMovableObject o) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(o.getSavedObject(), m.getPlayerLocationX() - x,
                    m.getPlayerLocationY() - y, m.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            m.setCell(o, m.getPlayerLocationX() - pullX,
                    m.getPlayerLocationY() - pullY, m.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePushedIntoPositionAbsolute(final int x, final int y,
            final int z, final int x2, final int y2, final int z2,
            final GenericMovableObject pushedInto, final MazeObject source) {
        final Application app = LoopChute.getApplication();
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
            final Application app = LoopChute.getApplication();
            final Maze m = app.getMazeManager().getMaze();
            final MazeObject below = m.getCell(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
                    MazeConstants.LAYER_GROUND);
            final MazeObject nextBelow = m.getCell(m.getPlayerLocationX() + x,
                    m.getPlayerLocationY() + y, m.getPlayerLocationZ(),
                    MazeConstants.LAYER_GROUND);
            final MazeObject nextAbove = m.getCell(m.getPlayerLocationX() + x,
                    m.getPlayerLocationY() + y, m.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            return this.checkSolid(this.savedMazeObject, below, nextBelow,
                    nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public void updatePositionAbsolute(final int x, final int y, final int z) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.getCell(x, y, z, MazeConstants.LAYER_OBJECT).preMoveAction(true,
                    x, y, this.objectInv);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
        m.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                m.setCell(this.savedMazeObject, m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                m.setPlayerLocationX(x);
                m.setPlayerLocationY(y);
                m.setPlayerLocationZ(z);
                this.vwMgr.setViewingWindowLocationX(
                        m.getPlayerLocationY() - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(
                        m.getPlayerLocationX() - this.vwMgr.getOffsetFactorY());
                this.savedMazeObject = m.getCell(m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                app.getMazeManager().setDirty(true);
                this.savedMazeObject.postMoveAction(false, x, y,
                        this.objectInv);
                this.checkAutoFinish();
                final int px = m.getPlayerLocationX();
                final int py = m.getPlayerLocationY();
                final int pz = m.getPlayerLocationZ();
                m.updateVisibleSquares(px, py, pz);
                this.redrawMaze();
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            LoopChute.getApplication().showMessage("Can't go outside the maze");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            LoopChute.getApplication().showMessage("Can't go outside the maze");
        }
    }

    public void updatePositionAbsoluteNoEvents(final int x, final int y,
            final int z, final int w) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        m.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.objectInv)) {
                m.setCell(this.savedMazeObject, m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                m.setPlayerLocationX(x);
                m.setPlayerLocationY(y);
                m.setPlayerLocationZ(z);
                m.setPlayerLocationW(w);
                this.vwMgr.setViewingWindowLocationX(
                        m.getPlayerLocationY() - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(
                        m.getPlayerLocationX() - this.vwMgr.getOffsetFactorY());
                this.savedMazeObject = m.getCell(m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                app.getMazeManager().setDirty(true);
                final int px = m.getPlayerLocationX();
                final int py = m.getPlayerLocationY();
                final int pz = m.getPlayerLocationZ();
                m.updateVisibleSquares(px, py, pz);
                this.redrawMaze();
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            LoopChute.getApplication().showMessage("Can't go outside the maze");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            LoopChute.getApplication().showMessage("Can't go outside the maze");
        }
    }

    public void redrawMaze() {
        this.gui.redrawMaze();
    }

    public void redrawMazeNoRebuild() {
        this.gui.redrawMazeNoRebuild();
    }

    public void redrawOneSquare(final int x, final int y,
            final boolean useDelay, final MazeObject obj3) {
        this.gui.redrawOneSquare(x, y, useDelay, obj3);
    }

    public void resetViewingWindowAndPlayerLocation() {
        this.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        this.vwMgr.setViewingWindowLocationX(
                m.getPlayerLocationY() - this.vwMgr.getOffsetFactorX());
        this.vwMgr.setViewingWindowLocationY(
                m.getPlayerLocationX() - this.vwMgr.getOffsetFactorY());
    }

    public void resetPlayerLocation() {
        this.resetPlayerLocation(0);
    }

    public void resetPlayerLocation(final int level) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        if (m != null) {
            m.switchLevel(level);
            m.setPlayerToStart();
        }
    }

    public void resetCurrentLevel() {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        this.resetLevel(m.getPlayerLocationW());
    }

    public void resetGameState() {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        app.getMazeManager().setDirty(false);
        m.restore();
        m.resetVisibleSquares();
        this.setSavedGameFlag(false);
        this.decay();
        this.objectInv = new ObjectInventory();
        this.savedObjectInv = new ObjectInventory();
        final int startW = m.getStartLevel();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            m.switchLevel(startW);
            m.setPlayerToStart();
            m.save();
        }
    }

    public void resetLevel(final int level) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        app.getMazeManager().setDirty(true);
        m.restore();
        m.resetVisibleSquares();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            this.resetPlayerLocation(level);
            this.resetViewingWindow();
            this.decay();
            this.restoreObjectInventory();
            this.redrawMaze();
        }
    }

    public void solvedLevel() {
        final Application app = LoopChute.getApplication();
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
            if (m.useOffset()) {
                m.switchLevelOffset(m.getNextLevel());
            } else {
                m.switchLevel(m.getNextLevel());
            }
            this.resetPlayerLocation(m.getPlayerLocationW() + 1);
            this.resetViewingWindow();
            this.decay();
            this.objectInv.removeAllItemsOfType(TypeConstants.TYPE_CHECK_KEY);
            this.saveObjectInventory();
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.redrawMaze();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedLevelAlternate() {
        final Application app = LoopChute.getApplication();
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
            if (m.useOffset()) {
                m.switchLevelOffset(m.getAlternateNextLevel());
            } else {
                m.switchLevel(m.getAlternateNextLevel());
            }
            this.resetPlayerLocation(m.getPlayerLocationW() + 1);
            this.resetViewingWindow();
            this.decay();
            this.objectInv.removeAllItemsOfType(TypeConstants.TYPE_CHECK_KEY);
            this.saveObjectInventory();
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.redrawMaze();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedMaze() {
        final Maze m = LoopChute.getApplication().getMazeManager().getMaze();
        CommonDialogs.showTitledDialog(m.getMazeEndMessage(), m.getMazeTitle());
        this.exitGame();
    }

    public void exitGame() {
        this.stateChanged = true;
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        // Restore the maze
        m.restore();
        m.resetVisibleSquares();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
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

    public JFrame getOutputFrame() {
        return this.gui.getOutputFrame();
    }

    public void decay() {
        this.savedMazeObject = new Empty();
    }

    public void decayTo(final MazeObject decay) {
        this.savedMazeObject = decay;
    }

    public void morph(final MazeObject morphInto, final int x, final int y,
            final int z) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            final int e = morphInto.getLayer();
            m.setCell(morphInto, x, y, z, e);
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
        final Application app = LoopChute.getApplication();
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
        final MazeObjectList list = LoopChute.getApplication().getObjects();
        final MazeObject[] choices = list.getAllUsableObjects();
        final String[] userChoices = this.objectInv.generateUseStringArray();
        final String result = CommonDialogs.showInputDialog("Use which item?",
                "loopchute", userChoices,
                userChoices[this.lastUsedObjectIndex]);
        try {
            for (x = 0; x < choices.length; x++) {
                if (result.equals(userChoices[x])) {
                    this.lastUsedObjectIndex = x;
                    this.objectBeingUsed = choices[x];
                    if (this.objectInv.getUses(this.objectBeingUsed) == 0) {
                        LoopChute.getApplication().showMessage(
                                "That item has no more uses left.");
                        this.setUsingAnItem(false);
                    } else if (this.objectBeingUsed
                            .isOfType(TypeConstants.TYPE_BOW)) {
                        if (this.objectBeingUsed instanceof GenericBow) {
                            final GenericBow bow = (GenericBow) this.objectBeingUsed;
                            final int at = bow.getArrowType();
                            this.setArrowType(at);
                            LoopChute.getApplication()
                                    .showMessage("Bow activated.");
                        }
                    } else {
                        LoopChute.getApplication()
                                .showMessage("Click to set target");
                    }
                    return;
                }
            }
        } catch (final NullPointerException np) {
            this.setUsingAnItem(false);
        }
    }

    void useItemHandler(final int x, final int y) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final int xOffset = this.vwMgr.getViewingWindowLocationX()
                - this.vwMgr.getOffsetFactorX();
        final int yOffset = this.vwMgr.getViewingWindowLocationY()
                - this.vwMgr.getOffsetFactorY();
        final int destX = x / GraphicsManager.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / GraphicsManager.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = m.getPlayerLocationZ();
        if (this.usingAnItem() && app.getMode() == Application.STATUS_GAME) {
            final boolean visible = app.getMazeManager().getMaze()
                    .isSquareVisible(m.getPlayerLocationX(),
                            m.getPlayerLocationY(), destX, destY);
            try {
                final MazeObject target = m.getCell(destX, destY, destZ,
                        MazeConstants.LAYER_OBJECT);
                final String name = this.objectBeingUsed.getName();
                if ((target.isSolid() || !visible)
                        && name.equals(new TeleportWand().getName())) {
                    this.setUsingAnItem(false);
                    LoopChute.getApplication()
                            .showMessage("Can't teleport there");
                }
                if (target.getName().equals(new Player().getName())) {
                    this.setUsingAnItem(false);
                    LoopChute.getApplication()
                            .showMessage("Don't aim at yourself!");
                }
                if (!target.isOfType(TypeConstants.TYPE_WALL)
                        && name.equals(new WallBreakingWand().getName())) {
                    this.setUsingAnItem(false);
                    LoopChute.getApplication().showMessage("Aim at a wall");
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.setUsingAnItem(false);
                LoopChute.getApplication().showMessage("Aim within the maze");
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
        LoopChute.getApplication().showMessage("Click to set destination");
    }

    void controllableTeleportHandler(final int x, final int y) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
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
            final int destZ = m.getPlayerLocationZ();
            this.updatePositionAbsolute(destX, destY, destZ);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_TELEPORT);
            this.teleporting = false;
        }
    }

    public void identifyObject(final int x, final int y) {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final int xOffset = this.vwMgr.getViewingWindowLocationX()
                - this.vwMgr.getOffsetFactorX();
        final int yOffset = this.vwMgr.getViewingWindowLocationY()
                - this.vwMgr.getOffsetFactorY();
        final int destX = x / GraphicsManager.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / GraphicsManager.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = m.getPlayerLocationZ();
        try {
            final MazeObject target1 = m.getCell(destX, destY, destZ,
                    MazeConstants.LAYER_GROUND);
            final MazeObject target2 = m.getCell(destX, destY, destZ,
                    MazeConstants.LAYER_OBJECT);
            target1.determineCurrentAppearance(destX, destY, destZ);
            target2.determineCurrentAppearance(destX, destY, destZ);
            final String gameName1 = target1.getGameName();
            final String gameName2 = target2.getGameName();
            LoopChute.getApplication()
                    .showMessage(gameName2 + " on " + gameName1);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_IDENTIFY);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            final EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(destX, destY, destZ);
            LoopChute.getApplication().showMessage(ev.getGameName());
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_IDENTIFY);
        }
    }

    public void loadGameHook(final XDataReader mazeFile,
            final int formatVersion) throws IOException {
        final Application app = LoopChute.getApplication();
        this.objectInv = ObjectInventory.readInventory(mazeFile, formatVersion);
        this.savedObjectInv = ObjectInventory.readInventory(mazeFile,
                formatVersion);
        app.getMazeManager().setScoresFileName(mazeFile.readString());
    }

    public void saveGameHook(final XDataWriter mazeFile) throws IOException {
        final Application app = LoopChute.getApplication();
        this.objectInv.writeInventory(mazeFile);
        this.savedObjectInv.writeInventory(mazeFile);
        mazeFile.writeString(app.getMazeManager().getScoresFileName());
    }

    public void playMaze() {
        final Application app = LoopChute.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        if (app.getMazeManager().getLoaded()) {
            this.gui.initViewManager();
            app.getGUIManager().hideGUI();
            app.setInGame(true);
            if (this.stateChanged) {
                // Initialize only if the maze state has changed
                app.getMazeManager().getMaze().switchLevel(
                        app.getMazeManager().getMaze().getStartLevel());
                this.savedMazeObject = new Empty();
                app.getMazeManager().getMaze().updateThresholds();
                this.autoFinishThreshold = app.getMazeManager().getMaze()
                        .getAutoFinishThreshold();
                this.autoFinishThresholdEnabled = this.autoFinishThreshold > 0;
                this.gui.updateAutoFinishProgress(0);
                this.alternateAutoFinishThreshold = app.getMazeManager()
                        .getMaze().getAlternateAutoFinishThreshold();
                this.alternateAutoFinishThresholdEnabled = this.alternateAutoFinishThreshold > 0;
                this.gui.updateAlternateAutoFinishProgress(0);
                // Update progress bar
                this.gui.setAutoFinishMax(this.autoFinishThreshold);
                // Update alternate progress bar
                this.gui.setAlternateAutoFinishMax(
                        this.alternateAutoFinishThreshold);
                if (!this.savedGameFlag) {
                    this.saveObjectInventory();
                }
                this.stateChanged = false;
            }
            // Make sure message area is attached to the border pane
            this.gui.resetBorderPane();
            // Make sure initial area player is in is visible
            final int px = m.getPlayerLocationX();
            final int py = m.getPlayerLocationY();
            final int pz = m.getPlayerLocationZ();
            m.updateVisibleSquares(px, py, pz);
            CommonDialogs.showTitledDialog(m.getMazeStartMessage(),
                    m.getMazeTitle());
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.showOutput();
            this.redrawMaze();
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
