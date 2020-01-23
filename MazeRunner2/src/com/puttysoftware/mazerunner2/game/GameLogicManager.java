/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.game;

import java.io.IOException;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.creatures.party.PartyManager;
import com.puttysoftware.mazerunner2.maze.Maze;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.MazeManager;
import com.puttysoftware.mazerunner2.maze.abc.AbstractCharacter;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMovableObject;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.effects.MazeEffectManager;
import com.puttysoftware.mazerunner2.maze.objects.Empty;
import com.puttysoftware.mazerunner2.maze.objects.EmptyVoid;
import com.puttysoftware.mazerunner2.maze.objects.HotBoots;
import com.puttysoftware.mazerunner2.maze.objects.MoonStone;
import com.puttysoftware.mazerunner2.maze.objects.PasswallBoots;
import com.puttysoftware.mazerunner2.maze.objects.SlipperyBoots;
import com.puttysoftware.mazerunner2.maze.objects.SunStone;
import com.puttysoftware.mazerunner2.maze.objects.Wall;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ImageTransformer;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;
import com.puttysoftware.xio.legacy.XLegacyDataReader;

public class GameLogicManager {
    // Fields
    private AbstractMazeObject savedMazeObject, previousSavedMazeObject;
    private boolean pullInProgress;
    private boolean savedGameFlag;
    private GameViewingWindowManager vwMgr;
    private ObjectInventoryManager oiMgr;
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
    private AbstractMazeObject delayedDecayObject;
    private GameGUIManager gui;
    private final ScoreTracker st;
    private MazeEffectManager em;

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
        JFrame owner = MazeRunnerII.getApplication().getOutputFrame();
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

    public AbstractMazeObject getSavedMazeObject() {
        return this.savedMazeObject;
    }

    private void saveSavedMazeObject() {
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        AbstractCharacter player = (AbstractCharacter) m.getCell(px, py, pz,
                MazeConstants.LAYER_OBJECT);
        player.setSavedObject(this.savedMazeObject);
    }

    private void restoreSavedMazeObject() {
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        AbstractCharacter player = (AbstractCharacter) m.getCell(px, py, pz,
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
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
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
            MazeRunnerII.getApplication().showMessage("Rotation failed!");
        }
    }

    public void doCounterclockwiseRotate(final int r) {
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
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
            MazeRunnerII.getApplication().showMessage("Rotation failed!");
        }
    }

    public void fireArrow(int x, int y) {
        this.oiMgr.fireArrow(x, y);
    }

    void arrowDone() {
        this.oiMgr.arrowDone();
        this.gui.updateStats();
        this.checkGameOver();
    }

    public void setRemoteAction(int x, int y, int z) {
        this.remoteCoords = new int[] { x, y, z };
        this.actingRemotely = true;
    }

    public void doRemoteAction(int x, int y, int z) {
        this.setRemoteAction(x, y, z);
        AbstractMazeObject acted = MazeRunnerII.getApplication()
                .getMazeManager()
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

    public void addToScore(long points) {
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
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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

    public void setSavedGameFlag(boolean value) {
        this.savedGameFlag = value;
    }

    public void resetObjectInventory() {
        this.oiMgr.resetObjectInventory();
    }

    public boolean isEffectActive(int effectID) {
        return this.em.isEffectActive(effectID);
    }

    private void decayEffects() {
        this.em.decayEffects();
    }

    public void activateEffect(int effectID, int duration) {
        this.em.activateEffect(effectID, duration);
    }

    public void deactivateEffect(int effectID) {
        this.em.deactivateEffect(effectID);
    }

    private void deactivateAllEffects() {
        this.em.deactivateAllEffects();
    }

    int[] doEffects(int x, int y) {
        return this.em.doEffects(x, y);
    }

    public boolean isTeleporting() {
        return this.teleporting;
    }

    void setTeleporting(boolean tele) {
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
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
        this.oiMgr.fireStepActions();
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
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

    public void updatePositionRelative(final int dirX, final int dirY, int dirZ) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        this.previousSavedMazeObject = this.savedMazeObject;
        boolean redrawsSuspended = false;
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        int fX = dirX;
        int fY = dirY;
        int fZ = dirZ;
        int[] mod = this.doEffects(dirX, dirY);
        fX = mod[0];
        fY = mod[1];
        boolean proceed = false;
        AbstractMazeObject below = null;
        AbstractMazeObject nextBelow = null;
        AbstractMazeObject nextAbove = new Empty();
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
            } catch (NullPointerException np) {
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
                    MazeRunnerII.getApplication().showMessage(
                            "Can't go that way");
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
            if (redrawsSuspended
                    && !this.checkLoopCondition(proceed, below, nextBelow,
                            nextAbove)) {
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
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        boolean redrawsSuspended = false;
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        int fX = dirX;
        int fY = dirY;
        AbstractMazeObject acted = new Empty();
        AbstractMazeObject groundInto = new Empty();
        AbstractMazeObject below = null;
        AbstractMazeObject nextBelow = null;
        AbstractMazeObject nextAbove = null;
        AbstractMazeObject nextNextBelow = null;
        AbstractMazeObject nextNextAbove = null;
        final boolean isXNonZero = (fX != 0);
        final boolean isYNonZero = (fY != 0);
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
                if (this.getObjectInventory().isItemThere(new PasswallBoots())) {
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
                acted.pushFailedAction(this.getObjectInventory(), fX, fY,
                        pushX, pushY);
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
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        boolean redrawsSuspended = false;
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        int fX = dirX;
        int fY = dirY;
        AbstractMazeObject acted = new Empty();
        AbstractMazeObject groundInto = new Empty();
        AbstractMazeObject below = null;
        AbstractMazeObject previousBelow = null;
        final boolean isXNonZero = (fX != 0);
        final boolean isYNonZero = (fY != 0);
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
                acted.pullFailedAction(this.getObjectInventory(), fX, fY,
                        pullX, pullY);
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
        this.savedMazeObject = m
                .getCell(px, py, pz, MazeConstants.LAYER_OBJECT);
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
            if (!this.savedMazeObject.isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                this.savedMazeObject.postMoveAction(false, px, py,
                        this.getObjectInventory());
            }
        } else {
            this.savedMazeObject.postMoveAction(false, px, py,
                    this.getObjectInventory());
        }
        return redrawsSuspended;
    }

    public void backUpPlayer(AbstractMazeObject backUpObject) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
            int ssCount = this.getObjectInventory()
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
            int msCount = this.getObjectInventory().getItemCount(
                    new MoonStone());
            this.gui.updateAlternateAutoFinishProgress(msCount);
            if (msCount >= this.alternateAutoFinishThreshold) {
                // Auto-Finish
                SoundManager.playSound(SoundConstants.SOUND_FINISH);
                this.solvedLevelAlternate();
            }
        }
    }

    private boolean checkLoopCondition(boolean proceed,
            AbstractMazeObject below, AbstractMazeObject nextBelow,
            AbstractMazeObject nextAbove) {
        // Handle slippery boots and ice amulet
        if (this.getObjectInventory().isItemThere(new SlipperyBoots())) {
            return proceed
                    && this.checkSolid(this.savedMazeObject, below, nextBelow,
                            nextAbove);
        } else {
            return proceed
                    && !nextBelow.hasFrictionConditionally(
                            this.getObjectInventory(), false)
                    && this.checkSolid(this.savedMazeObject, below, nextBelow,
                            nextAbove);
        }
    }

    private boolean checkSolid(final AbstractMazeObject inside,
            final AbstractMazeObject below, final AbstractMazeObject nextBelow,
            final AbstractMazeObject nextAbove) {
        boolean insideSolid = inside.isConditionallySolid(this
                .getObjectInventory());
        boolean belowSolid = below.isConditionallySolid(this
                .getObjectInventory());
        boolean nextBelowSolid = nextBelow.isConditionallySolid(this
                .getObjectInventory());
        // Handle hot boots and slippery boots
        if (this.getObjectInventory().isItemThere(new HotBoots())
                || this.getObjectInventory().isItemThere(new SlipperyBoots())) {
            nextBelowSolid = false;
        }
        boolean nextAboveSolid = nextAbove.isConditionallySolid(this
                .getObjectInventory());
        if (insideSolid || belowSolid || nextBelowSolid || nextAboveSolid) {
            return false;
        } else {
            return true;
        }
    }

    private void fireMoveFailedActions(final int x, final int y,
            final AbstractMazeObject inside, final AbstractMazeObject below,
            final AbstractMazeObject nextBelow,
            final AbstractMazeObject nextAbove) {
        boolean insideSolid = inside.isConditionallySolid(this
                .getObjectInventory());
        boolean belowSolid = below.isConditionallySolid(this
                .getObjectInventory());
        boolean nextBelowSolid = nextBelow.isConditionallySolid(this
                .getObjectInventory());
        boolean nextAboveSolid = nextAbove.isConditionallySolid(this
                .getObjectInventory());
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
            final int pushY, final AbstractMazeObject acted,
            final AbstractMazeObject nextBelow,
            final AbstractMazeObject nextNextBelow,
            final AbstractMazeObject nextNextAbove) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        boolean nextBelowAccept = nextBelow.isPushableOut();
        boolean nextNextBelowAccept = nextNextBelow.isPushableInto();
        boolean nextNextAboveAccept = nextNextAbove.isPushableInto();
        if (nextBelowAccept && nextNextBelowAccept && nextNextAboveAccept) {
            nextBelow.pushOutAction(this.getObjectInventory(), acted, px
                    + pushX, py + pushY, pz);
            acted.pushAction(this.getObjectInventory(), nextNextAbove, x, y,
                    pushX, pushY);
            nextNextAbove.pushIntoAction(this.getObjectInventory(), acted, px
                    + pushX, py + pushY, pz);
            nextNextBelow.pushIntoAction(this.getObjectInventory(), acted, px
                    + pushX, py + pushY, pz);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPull(final int x, final int y, final int pullX,
            final int pullY, final AbstractMazeObject acted,
            final AbstractMazeObject previousBelow,
            final AbstractMazeObject below, final AbstractMazeObject above) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        boolean previousBelowAccept = previousBelow.isPullableOut();
        boolean belowAccept = below.isPullableInto();
        boolean aboveAccept = above.isPullableInto();
        if (previousBelowAccept && belowAccept && aboveAccept) {
            previousBelow.pullOutAction(this.getObjectInventory(), acted, px
                    - pullX, py - pullY, pz);
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
            final int pushY, final AbstractMovableObject o) {
        int xInc = (int) Math.signum(x), yInc = (int) Math.signum(y);
        int cumPushX = pushX, cumPushY = pushY, cumX = x, cumY = y;
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        MazeManager mm = app.getMazeManager();
        AbstractMazeObject there = mm.getMazeObject(m.getPlayerLocationX()
                + cumX, m.getPlayerLocationY() + cumY, m.getPlayerLocationZ(),
                MazeConstants.LAYER_GROUND);
        if (there != null) {
            do {
                this.movePushedObjectPosition(cumX, cumY, cumPushX, cumPushY,
                        o, there);
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
            final int pushX, final int pushY, final AbstractMovableObject o,
            final AbstractMazeObject g) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(o.getSavedObject(), m.getPlayerLocationX() + x,
                    m.getPlayerLocationY() + y, m.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            m.setCell(o, m.getPlayerLocationX() + pushX, m.getPlayerLocationY()
                    + pushY, m.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
            if (g.overridesDefaultPostMove()) {
                g.postMoveAction(false, m.getPlayerLocationX(),
                        m.getPlayerLocationY(), this.getObjectInventory());
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePulledPosition(final int x, final int y, final int pullX,
            final int pullY, final AbstractMovableObject o) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(o.getSavedObject(), m.getPlayerLocationX() - x,
                    m.getPlayerLocationY() - y, m.getPlayerLocationZ(),
                    MazeConstants.LAYER_OBJECT);
            m.setCell(o, m.getPlayerLocationX() - pullX, m.getPlayerLocationY()
                    - pullY, m.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePushedIntoPositionAbsolute(final int x, final int y,
            final int z, final int x2, final int y2, final int z2,
            final AbstractMovableObject pushedInto,
            final AbstractMazeObject source) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        try {
            if (!(m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.getObjectInventory()))) {
                AbstractMazeObject saved = m.getCell(x, y, z,
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
            Application app = MazeRunnerII.getApplication();
            Maze m = app.getMazeManager().getMaze();
            AbstractMazeObject below = m.getCell(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
                    MazeConstants.LAYER_GROUND);
            AbstractMazeObject nextBelow = m.getCell(
                    m.getPlayerLocationX() + x, m.getPlayerLocationY() + y,
                    m.getPlayerLocationZ(), MazeConstants.LAYER_GROUND);
            AbstractMazeObject nextAbove = m.getCell(
                    m.getPlayerLocationX() + x, m.getPlayerLocationY() + y,
                    m.getPlayerLocationZ(), MazeConstants.LAYER_OBJECT);
            return this.checkSolid(this.savedMazeObject, below, nextBelow,
                    nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public boolean tryUpdatePositionAbsolute(final int x, final int y,
            final int z) {
        try {
            Application app = MazeRunnerII.getApplication();
            Maze m = app.getMazeManager().getMaze();
            AbstractMazeObject below = m.getCell(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
                    MazeConstants.LAYER_GROUND);
            AbstractMazeObject nextBelow = m.getCell(x, y, z,
                    MazeConstants.LAYER_GROUND);
            AbstractMazeObject nextAbove = m.getCell(x, y, z,
                    MazeConstants.LAYER_OBJECT);
            return this.checkSolidAbsolute(this.savedMazeObject, below,
                    nextBelow, nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    private boolean checkSolidAbsolute(final AbstractMazeObject inside,
            final AbstractMazeObject below, final AbstractMazeObject nextBelow,
            final AbstractMazeObject nextAbove) {
        boolean insideSolid = inside.isConditionallySolid(this
                .getObjectInventory());
        boolean belowSolid = below.isConditionallySolid(this
                .getObjectInventory());
        boolean nextBelowSolid = nextBelow.isConditionallySolid(this
                .getObjectInventory());
        boolean nextAboveSolid = nextAbove.isConditionallySolid(this
                .getObjectInventory());
        if (insideSolid || belowSolid || nextBelowSolid || nextAboveSolid) {
            return false;
        } else {
            return true;
        }
    }

    public void updatePositionAbsolute(final int x, final int y, final int z) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
            if (!(m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.getObjectInventory()))) {
                m.setCell(this.savedMazeObject, m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                m.setPlayerLocationX(x);
                m.setPlayerLocationY(y);
                m.setPlayerLocationZ(z);
                this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                        - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                        - this.vwMgr.getOffsetFactorY());
                this.savedMazeObject = m.getCell(m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                app.getMazeManager().setDirty(true);
                this.savedMazeObject.postMoveAction(false, x, y,
                        this.getObjectInventory());
                this.checkAutoFinish();
                int px = m.getPlayerLocationX();
                int py = m.getPlayerLocationY();
                int pz = m.getPlayerLocationZ();
                m.updateVisibleSquares(px, py, pz);
                this.redrawMaze();
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            MazeRunnerII.getApplication().showMessage(
                    "Can't go outside the maze");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            MazeRunnerII.getApplication().showMessage(
                    "Can't go outside the maze");
        }
    }

    public void updatePositionAbsoluteNoEvents(final int x, final int y,
            final int z, final int w) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        m.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!(m.getCell(x, y, z, MazeConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.getObjectInventory()))) {
                m.setCell(this.savedMazeObject, m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                m.setPlayerLocationX(x);
                m.setPlayerLocationY(y);
                m.setPlayerLocationZ(z);
                m.setPlayerLocationW(w);
                this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                        - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                        - this.vwMgr.getOffsetFactorY());
                this.savedMazeObject = m.getCell(m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        MazeConstants.LAYER_OBJECT);
                app.getMazeManager().setDirty(true);
                int px = m.getPlayerLocationX();
                int py = m.getPlayerLocationY();
                int pz = m.getPlayerLocationZ();
                m.updateVisibleSquares(px, py, pz);
                this.redrawMaze();
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            MazeRunnerII.getApplication().showMessage(
                    "Can't go outside the maze");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            MazeRunnerII.getApplication().showMessage(
                    "Can't go outside the maze");
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

    void redrawOneSquare(int x, int y, boolean useDelay, AbstractMazeObject obj4) {
        this.gui.redrawOneSquare(x, y, useDelay, obj4);
    }

    public void resetViewingWindowAndPlayerLocation() {
        this.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                - this.vwMgr.getOffsetFactorX());
        this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                - this.vwMgr.getOffsetFactorY());
    }

    public void resetPlayerLocation() {
        this.resetPlayerLocation(0);
    }

    public void resetPlayerLocation(int level) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        if (m != null) {
            m.switchLevel(level);
            m.setPlayerToStart();
        }
    }

    public void resetCurrentLevel() {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        this.resetLevel(m.getPlayerLocationW());
    }

    public void resetGameState() {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        app.getMazeManager().setDirty(false);
        m.restore();
        m.resetVisibleSquares();
        this.setSavedGameFlag(false);
        this.decay();
        this.oiMgr.resetObjectInventory();
        this.oiMgr.saveObjectInventory();
        int startW = m.getStartLevel();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            m.switchLevel(startW);
            m.setPlayerToStart();
            m.save();
        }
    }

    public void resetLevel(int level) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
            this.getObjectInventory().removeAllItemsOfType(
                    TypeConstants.TYPE_CHECK_KEY);
            this.oiMgr.saveObjectInventory();
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.redrawMaze();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedLevelAlternate() {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
            this.getObjectInventory().removeAllItemsOfType(
                    TypeConstants.TYPE_CHECK_KEY);
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
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
        Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
        CommonDialogs.showTitledDialog(m.getMazeEndMessage(), m.getMazeTitle());
        this.exitGame();
    }

    public void exitGame() {
        this.stateChanged = true;
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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

    public void decayTo(AbstractMazeObject decay) {
        if (this.actingRemotely) {
            MazeRunnerII
                    .getApplication()
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
            MazeRunnerII
                    .getApplication()
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

    public void delayedDecayTo(AbstractMazeObject obj) {
        this.delayedDecayActive = true;
        this.delayedDecayObject = obj;
    }

    public void morph(final AbstractMazeObject morphInto, final int x,
            final int y, final int z, final int e) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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

    public void morph(final AbstractMazeObject morphInto, final int x,
            final int y, final int z) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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

    public void morph(final AbstractMazeObject morphInto, final int x,
            final int y, final int z, final String msg) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        try {
            m.setCell(morphInto, x, y, z, morphInto.getLayer());
            MazeRunnerII.getApplication().showMessage(msg);
            this.keepNextMessage();
            this.redrawMaze();
            app.getMazeManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morphOther(final AbstractMazeObject morphInto, final int x,
            final int y, final int e) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
        MazeRunnerII.getApplication().showMessage("Click to set destination");
    }

    void controllableTeleportHandler(final int x, final int y) {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
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
            AbstractMazeObject target1 = m.getCell(destX, destY, destZ,
                    MazeConstants.LAYER_GROUND);
            AbstractMazeObject target2 = m.getCell(destX, destY, destZ,
                    MazeConstants.LAYER_OBJECT);
            target1.determineCurrentAppearance(destX, destY, destZ);
            target2.determineCurrentAppearance(destX, destY, destZ);
            String gameName1 = target1.getGameName();
            String gameName2 = target2.getGameName();
            MazeRunnerII.getApplication().showMessage(
                    gameName2 + " on " + gameName1);
            SoundManager.playSound(SoundConstants.SOUND_IDENTIFY);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(destX, destY, destZ);
            MazeRunnerII.getApplication().showMessage(ev.getGameName());
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

    public MazeObjectInventory getObjectInventory() {
        return this.oiMgr.getObjectInventory();
    }

    public void loadLegacyGameHook(XLegacyDataReader mazeFile, int formatVersion)
            throws IOException {
        Application app = MazeRunnerII.getApplication();
        this.oiMgr.readLegacyObjectInventory(mazeFile, formatVersion);
        app.getMazeManager().setScoresFileName(mazeFile.readString());
        FileHooks.loadLegacyGameHook(mazeFile);
    }

    public void loadGameHook(XDataReader mazeFile, int formatVersion)
            throws IOException {
        Application app = MazeRunnerII.getApplication();
        this.oiMgr.readObjectInventory(mazeFile, formatVersion);
        app.getMazeManager().setScoresFileName(mazeFile.readString());
        FileHooks.loadGameHook(mazeFile);
    }

    public void saveGameHook(XDataWriter mazeFile) throws IOException {
        Application app = MazeRunnerII.getApplication();
        this.oiMgr.writeObjectInventory(mazeFile);
        mazeFile.writeString(app.getMazeManager().getScoresFileName());
        FileHooks.saveGameHook(mazeFile);
    }

    public void playMaze() {
        Application app = MazeRunnerII.getApplication();
        Maze m = app.getMazeManager().getMaze();
        if (app.getMazeManager().getLoaded()) {
            this.gui.initViewManager();
            app.getGUIManager().hideGUI();
            app.setInGame();
            if (this.stateChanged) {
                // Initialize only if the maze state has changed
                app.getMazeManager()
                        .getMaze()
                        .switchLevel(
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
                this.alternateAutoFinishEnabled = app.getMazeManager()
                        .getMaze().getAlternateAutoFinishEnabled();
                this.gui.updateAlternateAutoFinishProgress(0);
                // Update progress bar
                this.gui.setAutoFinishMax(this.autoFinishThreshold);
                // Update alternate progress bar
                this.gui.setAlternateAutoFinishMax(this.alternateAutoFinishThreshold);
                if (!this.savedGameFlag) {
                    this.oiMgr.saveObjectInventory();
                }
                this.stateChanged = false;
            }
            // Make sure message area is attached to the border pane
            this.gui.updateGameGUI(this.em);
            // Make sure initial area player is in is visible
            int px = m.getPlayerLocationX();
            int py = m.getPlayerLocationY();
            int pz = m.getPlayerLocationZ();
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

    public void setSavedMazeObject(AbstractMazeObject saved) {
        this.savedMazeObject = saved;
    }
}
