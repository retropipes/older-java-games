/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.game;

import java.io.IOException;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.creatures.PartyManager;
import com.puttysoftware.mastermaze.maze.Maze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.MazeManager;
import com.puttysoftware.mastermaze.maze.effects.MazeEffectManager;
import com.puttysoftware.mastermaze.maze.generic.GenericCharacter;
import com.puttysoftware.mastermaze.maze.generic.GenericMovableObject;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;
import com.puttysoftware.mastermaze.maze.objects.Empty;
import com.puttysoftware.mastermaze.maze.objects.EmptyVoid;
import com.puttysoftware.mastermaze.maze.objects.HotBoots;
import com.puttysoftware.mastermaze.maze.objects.MoonStone;
import com.puttysoftware.mastermaze.maze.objects.PasswallBoots;
import com.puttysoftware.mastermaze.maze.objects.SlipperyBoots;
import com.puttysoftware.mastermaze.maze.objects.SunStone;
import com.puttysoftware.mastermaze.maze.objects.Wall;
import com.puttysoftware.mastermaze.resourcemanagers.ImageTransformer;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class GameLogicManager {
    // Fields
    private MazeObject savedMazeObject, previousSavedMazeObject;
    private boolean pullInProgress;
    private boolean savedGameFlag;
    private final GameViewingWindowManager vwMgr;
    private final ObjectInventoryManager oiMgr;
    private boolean teleporting;
    private boolean autoFinishEnabled;
    private boolean alternateAutoFinishEnabled;
    private int autoFinishThreshold;
    private int alternateAutoFinishThreshold;
    private boolean stateChanged;
    private int poisonCounter;
    private int[] remoteCoords;
    private boolean actingRemotely;
    private boolean delayedDecayActive;
    private MazeObject delayedDecayObject;
    private final GameGUIManager gui;
    private final ScoreTracker st;
    private final MazeEffectManager em;

    // Constructors
    public GameLogicManager() {
        this.oiMgr = new ObjectInventoryManager();
        this.vwMgr = new GameViewingWindowManager();
        this.em = new MazeEffectManager();
        this.gui = new GameGUIManager();
        this.st = new ScoreTracker();
        this.setPullInProgress(false);
        this.savedMazeObject = new Empty();
        this.savedGameFlag = false;
        this.teleporting = false;
        this.stateChanged = true;
        this.autoFinishThreshold = 0;
        this.alternateAutoFinishThreshold = 0;
    }

    // Methods
    public boolean newGame() {
        final JFrame owner = MasterMaze.getApplication().getOutputFrame();
        if (this.savedGameFlag) {
            if (PartyManager.getParty() != null) {
                return true;
            } else {
                return PartyManager.createParty(owner);
            }
        } else {
            return PartyManager.createParty(owner);
        }
    }

    public MazeObject getSavedMazeObject() {
        return this.savedMazeObject;
    }

    private void saveSavedMazeObject() {
        final Maze m = MasterMaze.getApplication().getMazeManager().getMaze();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        final GenericCharacter player = (GenericCharacter) m.getCell(px, py, pz,
                MazeConstants.LAYER_OBJECT);
        player.setSavedObject(this.savedMazeObject);
    }

    private void restoreSavedMazeObject() {
        final Maze m = MasterMaze.getApplication().getMazeManager().getMaze();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        final GenericCharacter player = (GenericCharacter) m.getCell(px, py, pz,
                MazeConstants.LAYER_OBJECT);
        this.savedMazeObject = player.getSavedObject();
    }

    public boolean usingAnItem() {
        return this.oiMgr.usingAnItem();
    }

    boolean isArrowActive() {
        return this.oiMgr.isArrowActive();
    }

    public void doClockwiseRotate(final int r) {
        final Maze m = MasterMaze.getApplication().getMazeManager().getMaze();
        boolean b = false;
        if (this.actingRemotely) {
            b = m.rotateRadiusClockwise(this.remoteCoords[0],
                    this.remoteCoords[1], this.remoteCoords[2], r);
        } else {
            b = m.rotateRadiusClockwise(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(), r);
        }
        if (!b) {
            this.keepNextMessage();
            MasterMaze.getApplication().showMessage("Rotation failed!");
        }
    }

    public void doCounterclockwiseRotate(final int r) {
        final Maze m = MasterMaze.getApplication().getMazeManager().getMaze();
        boolean b = false;
        if (this.actingRemotely) {
            b = m.rotateRadiusCounterclockwise(this.remoteCoords[0],
                    this.remoteCoords[1], this.remoteCoords[2], r);
        } else {
            b = m.rotateRadiusCounterclockwise(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(), r);
        }
        if (!b) {
            this.keepNextMessage();
            MasterMaze.getApplication().showMessage("Rotation failed!");
        }
    }

    public void fireArrow(final int x, final int y) {
        this.oiMgr.fireArrow(x, y);
    }

    void arrowDone() {
        this.oiMgr.arrowDone();
        this.gui.updateStats();
        this.checkGameOver();
    }

    public void setRemoteAction(final int x, final int y, final int z) {
        this.remoteCoords = new int[] { x, y, z };
        this.actingRemotely = true;
    }

    public void doRemoteAction(final int x, final int y, final int z) {
        this.setRemoteAction(x, y, z);
        final MazeObject acted = MasterMaze.getApplication().getMazeManager()
                .getMazeObject(x, y, z, MazeConstants.LAYER_OBJECT);
        acted.preMoveAction(false, x, y, this.getObjectInventory());
        acted.postMoveAction(false, x, y, this.getObjectInventory());
        if (acted.doesChainReact()) {
            acted.chainReactionAction(x, y, z);
        }
    }

    public void enableTrueSight() {
        this.gui.enableTrueSight();
    }

    public void disableTrueSight() {
        this.gui.disableTrueSight();
    }

    public void addToScore(final long points) {
        this.st.addToScore(points);
    }

    public void showCurrentScore() {
        this.st.showCurrentScore();
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
        final Application app = MasterMaze.getApplication();
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
        final Application app = MasterMaze.getApplication();
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
        final Application app = MasterMaze.getApplication();
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

    public void setSavedGameFlag(final boolean value) {
        this.savedGameFlag = value;
    }

    public void resetObjectInventory() {
        this.oiMgr.resetObjectInventory();
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
        final Maze m = MasterMaze.getApplication().getMazeManager().getMaze();
        this.oiMgr.fireStepActions();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        m.updateVisibleSquares(px, py, pz);
        m.tickTimers(pz);
        PartyManager.getParty().fireStepActions();
        if (m.getPoisonPower() > 0) {
            this.poisonCounter++;
            if (this.poisonCounter >= m.getPoisonPower()) {
                // Poison
                this.poisonCounter = 0;
                PartyManager.getParty().getLeader().doDamage(1);
            }
        }
    }

    public void updateStats() {
        this.gui.updateStats();
    }

    public void updatePositionRelative(final int dirX, final int dirY,
            final int dirZ) {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        this.previousSavedMazeObject = this.savedMazeObject;
        boolean redrawsSuspended = false;
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        int fX = dirX;
        int fY = dirY;
        final int fZ = dirZ;
        final int[] mod = this.doEffects(dirX, dirY);
        fX = mod[0];
        fY = mod[1];
        boolean proceed = false;
        MazeObject below = null;
        MazeObject nextBelow = null;
        MazeObject nextAbove = new Empty();
        do {
            try {
                try {
                    below = m.getCell(px, py, pz, MazeConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    below = new Empty();
                }
                try {
                    nextBelow = m.getCell(px + fX, py + fY, pz + fZ,
                            MazeConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextBelow = new Empty();
                }
                try {
                    nextAbove = m.getCell(px + fX, py + fY, pz + fZ,
                            MazeConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextAbove = new Wall();
                }
                try {
                    proceed = nextAbove.preMoveAction(true, px + fX, py + fY,
                            this.getObjectInventory());
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    proceed = true;
                } catch (final InfiniteRecursionException ir) {
                    proceed = false;
                }
            } catch (final NullPointerException np) {
                proceed = false;
                this.decayEffects();
                nextAbove = new Empty();
            }
            if (proceed) {
                m.savePlayerLocation();
                this.vwMgr.saveViewingWindow();
                try {
                    if (this.checkSolid(this.savedMazeObject, below, nextBelow,
                            nextAbove)) {
                        redrawsSuspended = this.updatePositionRelativePull(fX,
                                fY);
                    } else {
                        redrawsSuspended = this.updatePositionRelativePush(fX,
                                fY);
                    }
                    if (this.delayedDecayActive) {
                        this.doDelayedDecay();
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.vwMgr.restoreViewingWindow();
                    m.restorePlayerLocation();
                    // Move failed - attempted to go outside the maze
                    nextAbove.moveFailedAction(false, px, py,
                            this.getObjectInventory());
                    this.fireStepActions();
                    MasterMaze.getApplication()
                            .showMessage("Can't go that way");
                    nextAbove = new Empty();
                    this.decayEffects();
                    proceed = false;
                }
            } else {
                // Move failed - pre-move check failed
                nextAbove.moveFailedAction(false, px + fX, py + fY,
                        this.getObjectInventory());
                this.fireStepActions();
                this.decayEffects();
                proceed = false;
            }
            if (redrawsSuspended && !this.checkLoopCondition(proceed, below,
                    nextBelow, nextAbove)) {
                // Redraw post-suspend
                this.redrawMaze();
                redrawsSuspended = false;
            }
            px = m.getPlayerLocationX();
            py = m.getPlayerLocationY();
            pz = m.getPlayerLocationZ();
        } while (this.checkLoopCondition(proceed, below, nextBelow, nextAbove));
        this.checkAutoFinish();
    }

    private boolean updatePositionRelativePush(final int dirX, final int dirY) {
        final Application app = MasterMaze.getApplication();
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
                if (this.delayedDecayActive) {
                    this.doDelayedDecay();
                }
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
                if (this.getObjectInventory()
                        .isItemThere(new PasswallBoots())) {
                    redrawsSuspended = true;
                } else {
                    this.redrawMaze();
                }
                groundInto = m.getCell(px, py, pz, MazeConstants.LAYER_GROUND);
                m.setCell(groundInto, px, py, pz, MazeConstants.LAYER_GROUND);
                if (groundInto.overridesDefaultPostMove()) {
                    groundInto.postMoveAction(false, px, py,
                            this.getObjectInventory());
                    if (!this.savedMazeObject
                            .isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                        this.savedMazeObject.postMoveAction(false, px, py,
                                this.getObjectInventory());
                    }
                } else {
                    this.savedMazeObject.postMoveAction(false, px, py,
                            this.getObjectInventory());
                }
                this.decayEffects();
            } else {
                // Push failed - object can't move that way
                acted.pushFailedAction(this.getObjectInventory(), fX, fY, pushX,
                        pushY);
                this.fireStepActions();
                this.decayEffects();
            }
        } else if (acted.doesChainReact()) {
            acted.chainReactionAction(px + fX, py + fY, pz);
        } else {
            // Move failed - object is solid in that direction
            this.fireMoveFailedActions(px + fX, py + fY, this.savedMazeObject,
                    below, nextBelow, nextAbove);
            this.fireStepActions();
            this.decayEffects();
        }
        return redrawsSuspended;
    }

    private boolean updatePositionRelativePull(final int dirX, final int dirY) {
        final Application app = MasterMaze.getApplication();
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
                acted.pullFailedAction(this.getObjectInventory(), fX, fY, pullX,
                        pullY);
                this.decayEffects();
            }
        } else if (!acted.isPullable() && this.isPullInProgress()) {
            // Pull failed - object not pullable
            acted.pullFailedAction(this.getObjectInventory(), fX, fY, pullX,
                    pullY);
            this.decayEffects();
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
        this.decayEffects();
        if (this.getObjectInventory().isItemThere(new PasswallBoots())) {
            redrawsSuspended = true;
        } else {
            this.redrawMaze();
        }
        groundInto = m.getCell(px, py, pz, MazeConstants.LAYER_GROUND);
        m.setCell(groundInto, px, py, pz, MazeConstants.LAYER_GROUND);
        if (groundInto.overridesDefaultPostMove()) {
            groundInto.postMoveAction(false, px, py, this.getObjectInventory());
            if (!this.savedMazeObject
                    .isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                this.savedMazeObject.postMoveAction(false, px, py,
                        this.getObjectInventory());
            }
        } else {
            this.savedMazeObject.postMoveAction(false, px, py,
                    this.getObjectInventory());
        }
        return redrawsSuspended;
    }

    public void backUpPlayer(final MazeObject backUpObject) {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        m.setCell(backUpObject, m.getPlayerLocationX(), m.getPlayerLocationY(),
                m.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
        this.vwMgr.restoreViewingWindow();
        m.restorePlayerLocation();
        this.decayTo(this.previousSavedMazeObject);
        this.redrawMaze();
    }

    private void checkAutoFinish() {
        // Check for auto-finish
        // Normal auto-finish
        if (this.autoFinishEnabled) {
            final int ssCount = this.getObjectInventory()
                    .getItemCount(new SunStone());
            this.gui.updateAutoFinishProgress(ssCount);
            if (ssCount >= this.autoFinishThreshold) {
                // Auto-Finish
                SoundManager.playSound(SoundConstants.SOUND_FINISH);
                this.solvedLevel();
            }
        }
        // Alternate auto-finish
        if (this.alternateAutoFinishEnabled) {
            final int msCount = this.getObjectInventory()
                    .getItemCount(new MoonStone());
            this.gui.updateAlternateAutoFinishProgress(msCount);
            if (msCount >= this.alternateAutoFinishThreshold) {
                // Auto-Finish
                SoundManager.playSound(SoundConstants.SOUND_FINISH);
                this.solvedLevelAlternate();
            }
        }
    }

    private boolean checkLoopCondition(final boolean proceed,
            final MazeObject below, final MazeObject nextBelow,
            final MazeObject nextAbove) {
        // Handle slippery boots and ice amulet
        if (this.getObjectInventory().isItemThere(new SlipperyBoots())) {
            return proceed && this.checkSolid(this.savedMazeObject, below,
                    nextBelow, nextAbove);
        } else {
            return proceed
                    && !nextBelow.hasFrictionConditionally(
                            this.getObjectInventory(), false)
                    && this.checkSolid(this.savedMazeObject, below, nextBelow,
                            nextAbove);
        }
    }

    private boolean checkSolid(final MazeObject inside, final MazeObject below,
            final MazeObject nextBelow, final MazeObject nextAbove) {
        final boolean insideSolid = inside
                .isConditionallySolid(this.getObjectInventory());
        final boolean belowSolid = below
                .isConditionallySolid(this.getObjectInventory());
        boolean nextBelowSolid = nextBelow
                .isConditionallySolid(this.getObjectInventory());
        // Handle hot boots and slippery boots
        if (this.getObjectInventory().isItemThere(new HotBoots())
                || this.getObjectInventory().isItemThere(new SlipperyBoots())) {
            nextBelowSolid = false;
        }
        final boolean nextAboveSolid = nextAbove
                .isConditionallySolid(this.getObjectInventory());
        if (insideSolid || belowSolid || nextBelowSolid || nextAboveSolid) {
            return false;
        } else {
            return true;
        }
    }

    private void fireMoveFailedActions(final int x, final int y,
            final MazeObject inside, final MazeObject below,
            final MazeObject nextBelow, final MazeObject nextAbove) {
        final boolean insideSolid = inside
                .isConditionallySolid(this.getObjectInventory());
        final boolean belowSolid = below
                .isConditionallySolid(this.getObjectInventory());
        final boolean nextBelowSolid = nextBelow
                .isConditionallySolid(this.getObjectInventory());
        final boolean nextAboveSolid = nextAbove
                .isConditionallySolid(this.getObjectInventory());
        if (insideSolid) {
            inside.moveFailedAction(false, x, y, this.getObjectInventory());
        }
        if (belowSolid) {
            below.moveFailedAction(false, x, y, this.getObjectInventory());
        }
        if (nextBelowSolid) {
            nextBelow.moveFailedAction(false, x, y, this.getObjectInventory());
        }
        if (nextAboveSolid) {
            nextAbove.moveFailedAction(false, x, y, this.getObjectInventory());
        }
    }

    private boolean checkPush(final int x, final int y, final int pushX,
            final int pushY, final MazeObject acted, final MazeObject nextBelow,
            final MazeObject nextNextBelow, final MazeObject nextNextAbove) {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        final boolean nextBelowAccept = nextBelow.isPushableOut();
        final boolean nextNextBelowAccept = nextNextBelow.isPushableInto();
        final boolean nextNextAboveAccept = nextNextAbove.isPushableInto();
        if (nextBelowAccept && nextNextBelowAccept && nextNextAboveAccept) {
            nextBelow.pushOutAction(this.getObjectInventory(), acted,
                    px + pushX, py + pushY, pz);
            acted.pushAction(this.getObjectInventory(), nextNextAbove, x, y,
                    pushX, pushY);
            nextNextAbove.pushIntoAction(this.getObjectInventory(), acted,
                    px + pushX, py + pushY, pz);
            nextNextBelow.pushIntoAction(this.getObjectInventory(), acted,
                    px + pushX, py + pushY, pz);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPull(final int x, final int y, final int pullX,
            final int pullY, final MazeObject acted,
            final MazeObject previousBelow, final MazeObject below,
            final MazeObject above) {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        final boolean previousBelowAccept = previousBelow.isPullableOut();
        final boolean belowAccept = below.isPullableInto();
        final boolean aboveAccept = above.isPullableInto();
        if (previousBelowAccept && belowAccept && aboveAccept) {
            previousBelow.pullOutAction(this.getObjectInventory(), acted,
                    px - pullX, py - pullY, pz);
            acted.pullAction(this.getObjectInventory(), above, x, y, pullX,
                    pullY);
            above.pullIntoAction(this.getObjectInventory(), acted, px - pullX,
                    py - pullY, pz);
            below.pullIntoAction(this.getObjectInventory(), acted, px - pullX,
                    py - pullY, pz);
            return true;
        } else {
            return false;
        }
    }

    public void updatePushedPosition(final int x, final int y, final int pushX,
            final int pushY, final GenericMovableObject o) {
        final int xInc = (int) Math.signum(x), yInc = (int) Math.signum(y);
        int cumPushX = pushX, cumPushY = pushY, cumX = x, cumY = y;
        final Application app = MasterMaze.getApplication();
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
            } while (!there.hasFrictionConditionally(this.getObjectInventory(),
                    true));
        }
    }

    private void movePushedObjectPosition(final int x, final int y,
            final int pushX, final int pushY, final GenericMovableObject o,
            final MazeObject g) {
        final Application app = MasterMaze.getApplication();
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
                        m.getPlayerLocationY(), this.getObjectInventory());
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePulledPosition(final int x, final int y, final int pullX,
            final int pullY, final GenericMovableObject o) {
        final Application app = MasterMaze.getApplication();
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
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.getObjectInventory())) {
                final MazeObject saved = m.getCell(x, y, z,
                        MazeConstants.LAYER_OBJECT);
                m.setCell(pushedInto, x, y, z, MazeConstants.LAYER_OBJECT);
                pushedInto.setSavedObject(saved);
                m.setCell(source, x2, y2, z2, MazeConstants.LAYER_OBJECT);
                saved.pushIntoAction(this.getObjectInventory(), pushedInto, x2,
                        y2, z2 - 1);
                this.redrawMaze();
                app.getMazeManager().setDirty(true);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.setCell(new Empty(), x2, y2, z2, MazeConstants.LAYER_OBJECT);
        }
    }

    public boolean tryUpdatePositionRelative(final int x, final int y) {
        try {
            final Application app = MasterMaze.getApplication();
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

    public boolean tryUpdatePositionAbsolute(final int x, final int y,
            final int z) {
        try {
            final Application app = MasterMaze.getApplication();
            final Maze m = app.getMazeManager().getMaze();
            final MazeObject below = m.getCell(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
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

    private boolean checkSolidAbsolute(final MazeObject inside,
            final MazeObject below, final MazeObject nextBelow,
            final MazeObject nextAbove) {
        final boolean insideSolid = inside
                .isConditionallySolid(this.getObjectInventory());
        final boolean belowSolid = below
                .isConditionallySolid(this.getObjectInventory());
        final boolean nextBelowSolid = nextBelow
                .isConditionallySolid(this.getObjectInventory());
        final boolean nextAboveSolid = nextAbove
                .isConditionallySolid(this.getObjectInventory());
        if (insideSolid || belowSolid || nextBelowSolid || nextAboveSolid) {
            return false;
        } else {
            return true;
        }
    }

    public void updatePositionAbsolute(final int x, final int y, final int z) {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.getCell(x, y, z, MazeConstants.LAYER_OBJECT).preMoveAction(true,
                    x, y, this.getObjectInventory());
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
        m.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.getObjectInventory())) {
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
                        this.getObjectInventory());
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
            MasterMaze.getApplication()
                    .showMessage("Can't go outside the maze");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            MasterMaze.getApplication()
                    .showMessage("Can't go outside the maze");
        }
    }

    public void updatePositionAbsoluteNoEvents(final int x, final int y,
            final int z, final int w) {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        m.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.getObjectInventory())) {
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
            MasterMaze.getApplication()
                    .showMessage("Can't go outside the maze");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            MasterMaze.getApplication()
                    .showMessage("Can't go outside the maze");
        }
    }

    public void checkGameOver() {
        if (!PartyManager.getParty().isAlive()) {
            this.gameOver();
        }
    }

    private void gameOver() {
        SoundManager.playSound(SoundConstants.SOUND_GAME_OVER);
        CommonDialogs.showDialog("You have died - Game Over!");
        this.st.commitScore();
        this.exitGame();
    }

    public void redrawMaze() {
        this.gui.redrawMaze();
    }

    void redrawOneSquare(final int x, final int y, final boolean useDelay,
            final MazeObject obj4) {
        this.gui.redrawOneSquare(x, y, useDelay, obj4);
    }

    public void resetViewingWindowAndPlayerLocation() {
        this.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        final Application app = MasterMaze.getApplication();
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
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        if (m != null) {
            m.switchLevel(level);
            m.setPlayerToStart();
        }
    }

    public void resetCurrentLevel() {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        this.resetLevel(m.getPlayerLocationW());
    }

    public void resetGameState() {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        app.getMazeManager().setDirty(false);
        m.restore();
        m.resetVisibleSquares();
        this.setSavedGameFlag(false);
        this.decay();
        this.oiMgr.resetObjectInventory();
        this.oiMgr.saveObjectInventory();
        final int startW = m.getStartLevel();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            m.switchLevel(startW);
            m.setPlayerToStart();
            m.save();
        }
    }

    public void resetLevel(final int level) {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        app.getMazeManager().setDirty(true);
        m.restore();
        m.resetVisibleSquares();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            this.resetPlayerLocation(level);
            this.resetViewingWindow();
            this.decay();
            this.oiMgr.restoreObjectInventory();
            this.redrawMaze();
        }
    }

    public void goToLevel(final int level) {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final boolean levelExists = m.doesLevelExist(level);
        if (levelExists) {
            this.saveSavedMazeObject();
            m.switchLevel(level);
            m.setPlayerLocationW(level);
            this.restoreSavedMazeObject();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedLevel() {
        final Application app = MasterMaze.getApplication();
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
            this.getObjectInventory()
                    .removeAllItemsOfType(TypeConstants.TYPE_CHECK_KEY);
            this.oiMgr.saveObjectInventory();
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.redrawMaze();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedLevelAlternate() {
        final Application app = MasterMaze.getApplication();
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
            this.getObjectInventory()
                    .removeAllItemsOfType(TypeConstants.TYPE_CHECK_KEY);
            this.oiMgr.saveObjectInventory();
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.redrawMaze();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedLevelWarp(final int level) {
        this.deactivateAllEffects();
        final Application app = MasterMaze.getApplication();
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
            this.oiMgr.saveObjectInventory();
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.redrawMaze();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedMaze() {
        final Maze m = MasterMaze.getApplication().getMazeManager().getMaze();
        CommonDialogs.showTitledDialog(m.getMazeEndMessage(), m.getMazeTitle());
        this.exitGame();
    }

    public void exitGame() {
        this.stateChanged = true;
        final Application app = MasterMaze.getApplication();
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
        this.oiMgr.resetObjectInventory();
        // Reset saved game flag
        this.savedGameFlag = false;
        app.getMazeManager().setDirty(false);
        // Exit game
        this.hideOutput();
        app.getGUIManager().showGUI();
    }

    public JFrame getOutputFrame() {
        return this.gui.getOutputFrame();
    }

    public void decay() {
        this.savedMazeObject = new Empty();
    }

    public void decayTo(final MazeObject decay) {
        if (this.actingRemotely) {
            MasterMaze.getApplication().getMazeManager().getMaze().setCell(
                    decay, this.remoteCoords[0], this.remoteCoords[1],
                    this.remoteCoords[2], MazeConstants.LAYER_OBJECT);
        } else {
            this.savedMazeObject = decay;
        }
    }

    private void doDelayedDecay() {
        if (this.actingRemotely) {
            MasterMaze.getApplication().getMazeManager().getMaze().setCell(
                    this.delayedDecayObject, this.remoteCoords[0],
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
            final int z, final int e) {
        final Application app = MasterMaze.getApplication();
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

    public void morph(final MazeObject morphInto, final int x, final int y,
            final int z) {
        final Application app = MasterMaze.getApplication();
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
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, x, y, z, morphInto.getLayer());
            MasterMaze.getApplication().showMessage(msg);
            this.keepNextMessage();
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
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, m.getPlayerLocationX() + x,
                    m.getPlayerLocationY() + y, m.getPlayerLocationZ(), e);
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

    public void showUseDialog() {
        this.oiMgr.showUseDialog();
    }

    public void showSwitchBowDialog() {
        this.oiMgr.showSwitchBowDialog();
    }

    public void controllableTeleport() {
        this.teleporting = true;
        MasterMaze.getApplication().showMessage("Click to set destination");
    }

    void controllableTeleportHandler(final int x, final int y) {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        if (this.teleporting) {
            final int xOffset = this.vwMgr.getViewingWindowLocationX()
                    - this.vwMgr.getOffsetFactorX();
            final int yOffset = this.vwMgr.getViewingWindowLocationY()
                    - this.vwMgr.getOffsetFactorY();
            final int destX = x / ImageTransformer.getGraphicSize()
                    + this.vwMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            final int destY = y / ImageTransformer.getGraphicSize()
                    + this.vwMgr.getViewingWindowLocationY() + xOffset
                    - yOffset;
            final int destZ = m.getPlayerLocationZ();
            this.updatePositionAbsolute(destX, destY, destZ);
            SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
            this.teleporting = false;
        }
    }

    public void identifyObject(final int x, final int y) {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        final int xOffset = this.vwMgr.getViewingWindowLocationX()
                - this.vwMgr.getOffsetFactorX();
        final int yOffset = this.vwMgr.getViewingWindowLocationY()
                - this.vwMgr.getOffsetFactorY();
        final int destX = x / ImageTransformer.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / ImageTransformer.getGraphicSize()
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
            MasterMaze.getApplication()
                    .showMessage(gameName2 + " on " + gameName1);
            SoundManager.playSound(SoundConstants.SOUND_IDENTIFY);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            final EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(destX, destY, destZ);
            MasterMaze.getApplication().showMessage(ev.getGameName());
            SoundManager.playSound(SoundConstants.SOUND_IDENTIFY);
        }
    }

    void useItemHandler(final int x, final int y) {
        final int xOffset = this.vwMgr.getViewingWindowLocationX()
                - this.vwMgr.getOffsetFactorX();
        final int yOffset = this.vwMgr.getViewingWindowLocationY()
                - this.vwMgr.getOffsetFactorY();
        final int destX = x / ImageTransformer.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / ImageTransformer.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationY() + xOffset - yOffset;
        this.oiMgr.useItemHandler(destX, destY);
        this.redrawMaze();
    }

    public ObjectInventory getObjectInventory() {
        return this.oiMgr.getObjectInventory();
    }

    public void loadGameHook(final XDataReader mazeFile,
            final int formatVersion) throws IOException {
        final Application app = MasterMaze.getApplication();
        this.oiMgr.readObjectInventory(mazeFile, formatVersion);
        app.getMazeManager().setScoresFileName(mazeFile.readString());
        FileHooks.loadGameHook(mazeFile);
    }

    public void saveGameHook(final XDataWriter mazeFile) throws IOException {
        final Application app = MasterMaze.getApplication();
        this.oiMgr.writeObjectInventory(mazeFile);
        mazeFile.writeString(app.getMazeManager().getScoresFileName());
        FileHooks.saveGameHook(mazeFile);
    }

    public void playMaze() {
        final Application app = MasterMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        if (app.getMazeManager().getLoaded()) {
            this.gui.initViewManager();
            app.getGUIManager().hideGUI();
            app.setInGame();
            if (this.stateChanged) {
                // Initialize only if the maze state has changed
                app.getMazeManager().getMaze().switchLevel(
                        app.getMazeManager().getMaze().getStartLevel());
                this.savedMazeObject = new Empty();
                app.getMazeManager().getMaze().updateThresholds();
                this.autoFinishThreshold = app.getMazeManager().getMaze()
                        .getAutoFinishThreshold();
                this.autoFinishEnabled = app.getMazeManager().getMaze()
                        .getAutoFinishEnabled();
                this.gui.updateAutoFinishProgress(0);
                this.alternateAutoFinishThreshold = app.getMazeManager()
                        .getMaze().getAlternateAutoFinishThreshold();
                this.alternateAutoFinishEnabled = app.getMazeManager().getMaze()
                        .getAlternateAutoFinishEnabled();
                this.gui.updateAlternateAutoFinishProgress(0);
                // Update progress bar
                this.gui.setAutoFinishMax(this.autoFinishThreshold);
                // Update alternate progress bar
                this.gui.setAlternateAutoFinishMax(
                        this.alternateAutoFinishThreshold);
                if (!this.savedGameFlag) {
                    this.oiMgr.saveObjectInventory();
                }
                this.stateChanged = false;
            }
            // Make sure message area is attached to the border pane
            this.gui.updateGameGUI(this.em);
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

    public void setSavedMazeObject(final MazeObject saved) {
        this.savedMazeObject = saved;
    }
}
