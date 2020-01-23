/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.game;

import java.io.IOException;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.DungeonManager;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMovableObject;
import com.puttysoftware.dungeondiver4.dungeon.effects.DungeonEffectManager;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.objects.EmptyVoid;
import com.puttysoftware.dungeondiver4.dungeon.objects.HotBoots;
import com.puttysoftware.dungeondiver4.dungeon.objects.PasswallBoots;
import com.puttysoftware.dungeondiver4.dungeon.objects.SlipperyBoots;
import com.puttysoftware.dungeondiver4.dungeon.objects.Wall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ImageTransformer;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class GameLogicManager {
    // Fields
    private AbstractDungeonObject savedDungeonObject,
            previousSavedDungeonObject;
    private boolean pullInProgress;
    private boolean savedGameFlag;
    private GameViewingWindowManager vwMgr;
    private ObjectInventoryManager oiMgr;
    private boolean teleporting;
    private boolean stateChanged;
    private int poisonCounter;
    private int[] remoteCoords;
    private boolean actingRemotely;
    private boolean delayedDecayActive;
    private AbstractDungeonObject delayedDecayObject;
    private GameGUIManager gui;
    private final ScoreTracker st;
    private DungeonEffectManager em;

    // Constructors
    public GameLogicManager() {
        this.oiMgr = new ObjectInventoryManager();
        this.vwMgr = new GameViewingWindowManager();
        this.em = new DungeonEffectManager();
        this.gui = new GameGUIManager();
        this.st = new ScoreTracker();
        this.setPullInProgress(false);
        this.savedDungeonObject = new Empty();
        this.savedGameFlag = false;
        this.teleporting = false;
        this.stateChanged = true;
    }

    // Methods
    public boolean newGame() {
        JFrame owner = DungeonDiver4.getApplication().getOutputFrame();
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

    public boolean usingAnItem() {
        return this.oiMgr.usingAnItem();
    }

    boolean isArrowActive() {
        return this.oiMgr.isArrowActive();
    }

    public void doClockwiseRotate(final int r) {
        Dungeon m = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon();
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
            DungeonDiver4.getApplication().showMessage("Rotation failed!");
        }
    }

    public void doCounterclockwiseRotate(final int r) {
        Dungeon m = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon();
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
            DungeonDiver4.getApplication().showMessage("Rotation failed!");
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
        AbstractDungeonObject acted = DungeonDiver4.getApplication()
                .getDungeonManager()
                .getDungeonObject(x, y, z, DungeonConstants.LAYER_OBJECT);
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
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.getCell(m.getPlayerLocationX(), m.getPlayerLocationY(),
                    m.getPlayerLocationZ() - 1, DungeonConstants.LAYER_OBJECT);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        } catch (final NullPointerException np) {
            return false;
        }
    }

    public boolean isFloorAbove() {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.getCell(m.getPlayerLocationX(), m.getPlayerLocationY(),
                    m.getPlayerLocationZ() + 1, DungeonConstants.LAYER_OBJECT);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        } catch (final NullPointerException np) {
            return false;
        }
    }

    public boolean doesFloorExist(final int floor) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.getCell(m.getPlayerLocationX(), m.getPlayerLocationY(), floor,
                    DungeonConstants.LAYER_OBJECT);
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
        Dungeon m = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon();
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
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        this.previousSavedDungeonObject = this.savedDungeonObject;
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
        AbstractDungeonObject below = null;
        AbstractDungeonObject nextBelow = null;
        AbstractDungeonObject nextAbove = new Empty();
        do {
            try {
                try {
                    below = m
                            .getCell(px, py, pz, DungeonConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    below = new Empty();
                }
                try {
                    nextBelow = m.getCell(px + fX, py + fY, pz + fZ,
                            DungeonConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextBelow = new Empty();
                }
                try {
                    nextAbove = m.getCell(px + fX, py + fY, pz + fZ,
                            DungeonConstants.LAYER_OBJECT);
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
                    if (this.checkSolid(this.savedDungeonObject, below,
                            nextBelow, nextAbove)) {
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
                    // Move failed - attempted to go outside the dungeon
                    nextAbove.moveFailedAction(false, px, py,
                            this.getObjectInventory());
                    this.fireStepActions();
                    DungeonDiver4.getApplication().showMessage(
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
                this.redrawDungeon();
                redrawsSuspended = false;
            }
            px = m.getPlayerLocationX();
            py = m.getPlayerLocationY();
            pz = m.getPlayerLocationZ();
        } while (this.checkLoopCondition(proceed, below, nextBelow, nextAbove));
    }

    private boolean updatePositionRelativePush(final int dirX, final int dirY) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        boolean redrawsSuspended = false;
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        int fX = dirX;
        int fY = dirY;
        AbstractDungeonObject acted = new Empty();
        AbstractDungeonObject groundInto = new Empty();
        AbstractDungeonObject below = null;
        AbstractDungeonObject nextBelow = null;
        AbstractDungeonObject nextAbove = null;
        AbstractDungeonObject nextNextBelow = null;
        AbstractDungeonObject nextNextAbove = null;
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
            below = m.getCell(px, py, pz, DungeonConstants.LAYER_GROUND);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            below = new Empty();
        }
        try {
            nextBelow = m.getCell(px + fX, py + fY, pz,
                    DungeonConstants.LAYER_GROUND);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            nextBelow = new Empty();
        }
        try {
            nextAbove = m.getCell(px + fX, py + fY, pz,
                    DungeonConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            nextAbove = new Wall();
        }
        try {
            nextNextBelow = m.getCell(px + 2 * fX, py + 2 * fY, pz,
                    DungeonConstants.LAYER_GROUND);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            nextNextBelow = new Empty();
        }
        try {
            nextNextAbove = m.getCell(px + 2 * fX, py + 2 * fY, pz,
                    DungeonConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            nextNextAbove = new Wall();
        }
        acted = m.getCell(px + fX, py + fY, pz, DungeonConstants.LAYER_OBJECT);
        if (acted.isPushable()) {
            if (this.checkPush(fX, fY, pushX, pushY, acted, nextBelow,
                    nextNextBelow, nextNextAbove)) {
                if (this.delayedDecayActive) {
                    this.doDelayedDecay();
                }
                m.setCell(this.savedDungeonObject, px, py, pz,
                        DungeonConstants.LAYER_OBJECT);
                m.offsetPlayerLocationX(fX);
                m.offsetPlayerLocationY(fY);
                px += fX;
                py += fY;
                this.vwMgr.offsetViewingWindowLocationX(fY);
                this.vwMgr.offsetViewingWindowLocationY(fX);
                this.savedDungeonObject = m.getCell(px, py, pz,
                        DungeonConstants.LAYER_OBJECT);
                app.getDungeonManager().setDirty(true);
                this.fireStepActions();
                if (this.getObjectInventory().isItemThere(new PasswallBoots())) {
                    redrawsSuspended = true;
                } else {
                    this.redrawDungeon();
                }
                groundInto = m.getCell(px, py, pz,
                        DungeonConstants.LAYER_GROUND);
                m.setCell(groundInto, px, py, pz, DungeonConstants.LAYER_GROUND);
                if (groundInto.overridesDefaultPostMove()) {
                    groundInto.postMoveAction(false, px, py,
                            this.getObjectInventory());
                    if (!this.savedDungeonObject
                            .isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                        this.savedDungeonObject.postMoveAction(false, px, py,
                                this.getObjectInventory());
                    }
                } else {
                    this.savedDungeonObject.postMoveAction(false, px, py,
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
            this.fireMoveFailedActions(px + fX, py + fY,
                    this.savedDungeonObject, below, nextBelow, nextAbove);
            this.fireStepActions();
            this.decayEffects();
        }
        return redrawsSuspended;
    }

    private boolean updatePositionRelativePull(final int dirX, final int dirY) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        boolean redrawsSuspended = false;
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        int fX = dirX;
        int fY = dirY;
        AbstractDungeonObject acted = new Empty();
        AbstractDungeonObject groundInto = new Empty();
        AbstractDungeonObject below = null;
        AbstractDungeonObject previousBelow = null;
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
            below = m.getCell(px, py, pz, DungeonConstants.LAYER_GROUND);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            below = new Empty();
        }
        try {
            previousBelow = m.getCell(px - fX, py - fY, pz,
                    DungeonConstants.LAYER_GROUND);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            previousBelow = new Empty();
        }
        m.setCell(this.savedDungeonObject, px, py, pz,
                DungeonConstants.LAYER_OBJECT);
        acted = new Empty();
        try {
            acted = m.getCell(px - fX, py - fY, pz,
                    DungeonConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
        if (acted.isPullable() && this.isPullInProgress()) {
            if (!this.checkPull(fX, fY, pullX, pullY, acted, previousBelow,
                    below, this.savedDungeonObject)) {
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
        this.savedDungeonObject = m.getCell(px, py, pz,
                DungeonConstants.LAYER_OBJECT);
        app.getDungeonManager().setDirty(true);
        this.fireStepActions();
        this.decayEffects();
        if (this.getObjectInventory().isItemThere(new PasswallBoots())) {
            redrawsSuspended = true;
        } else {
            this.redrawDungeon();
        }
        groundInto = m.getCell(px, py, pz, DungeonConstants.LAYER_GROUND);
        m.setCell(groundInto, px, py, pz, DungeonConstants.LAYER_GROUND);
        if (groundInto.overridesDefaultPostMove()) {
            groundInto.postMoveAction(false, px, py, this.getObjectInventory());
            if (!this.savedDungeonObject
                    .isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                this.savedDungeonObject.postMoveAction(false, px, py,
                        this.getObjectInventory());
            }
        } else {
            this.savedDungeonObject.postMoveAction(false, px, py,
                    this.getObjectInventory());
        }
        return redrawsSuspended;
    }

    public void backUpPlayer(AbstractDungeonObject backUpObject) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        m.setCell(backUpObject, m.getPlayerLocationX(), m.getPlayerLocationY(),
                m.getPlayerLocationZ(), DungeonConstants.LAYER_OBJECT);
        this.vwMgr.restoreViewingWindow();
        m.restorePlayerLocation();
        this.decayTo(this.previousSavedDungeonObject);
        this.redrawDungeon();
    }

    private boolean checkLoopCondition(boolean proceed,
            AbstractDungeonObject below, AbstractDungeonObject nextBelow,
            AbstractDungeonObject nextAbove) {
        // Handle slippery boots and ice amulet
        if (this.getObjectInventory().isItemThere(new SlipperyBoots())) {
            return proceed
                    && this.checkSolid(this.savedDungeonObject, below,
                            nextBelow, nextAbove);
        } else {
            return proceed
                    && !nextBelow.hasFrictionConditionally(
                            this.getObjectInventory(), false)
                    && this.checkSolid(this.savedDungeonObject, below,
                            nextBelow, nextAbove);
        }
    }

    private boolean checkSolid(final AbstractDungeonObject inside,
            final AbstractDungeonObject below,
            final AbstractDungeonObject nextBelow,
            final AbstractDungeonObject nextAbove) {
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
            final AbstractDungeonObject inside,
            final AbstractDungeonObject below,
            final AbstractDungeonObject nextBelow,
            final AbstractDungeonObject nextAbove) {
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
            final int pushY, final AbstractDungeonObject acted,
            final AbstractDungeonObject nextBelow,
            final AbstractDungeonObject nextNextBelow,
            final AbstractDungeonObject nextNextAbove) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
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
            final int pullY, final AbstractDungeonObject acted,
            final AbstractDungeonObject previousBelow,
            final AbstractDungeonObject below, final AbstractDungeonObject above) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
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
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        DungeonManager mm = app.getDungeonManager();
        AbstractDungeonObject there = mm.getDungeonObject(
                m.getPlayerLocationX() + cumX, m.getPlayerLocationY() + cumY,
                m.getPlayerLocationZ(), DungeonConstants.LAYER_GROUND);
        if (there != null) {
            do {
                this.movePushedObjectPosition(cumX, cumY, cumPushX, cumPushY,
                        o, there);
                cumX += xInc;
                cumY += yInc;
                cumPushX += xInc;
                cumPushY += yInc;
                there = mm.getDungeonObject(m.getPlayerLocationX() + cumX,
                        m.getPlayerLocationY() + cumY, m.getPlayerLocationZ(),
                        DungeonConstants.LAYER_GROUND);
                if (there == null) {
                    break;
                }
            } while (!there.hasFrictionConditionally(this.getObjectInventory(),
                    true));
        }
    }

    private void movePushedObjectPosition(final int x, final int y,
            final int pushX, final int pushY, final AbstractMovableObject o,
            final AbstractDungeonObject g) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.setCell(o.getSavedObject(), m.getPlayerLocationX() + x,
                    m.getPlayerLocationY() + y, m.getPlayerLocationZ(),
                    DungeonConstants.LAYER_OBJECT);
            m.setCell(o, m.getPlayerLocationX() + pushX, m.getPlayerLocationY()
                    + pushY, m.getPlayerLocationZ(),
                    DungeonConstants.LAYER_OBJECT);
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
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.setCell(o.getSavedObject(), m.getPlayerLocationX() - x,
                    m.getPlayerLocationY() - y, m.getPlayerLocationZ(),
                    DungeonConstants.LAYER_OBJECT);
            m.setCell(o, m.getPlayerLocationX() - pullX, m.getPlayerLocationY()
                    - pullY, m.getPlayerLocationZ(),
                    DungeonConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePushedIntoPositionAbsolute(final int x, final int y,
            final int z, final int x2, final int y2, final int z2,
            final AbstractMovableObject pushedInto,
            final AbstractDungeonObject source) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            if (!(m.getCell(x, y, z, DungeonConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.getObjectInventory()))) {
                AbstractDungeonObject saved = m.getCell(x, y, z,
                        DungeonConstants.LAYER_OBJECT);
                m.setCell(pushedInto, x, y, z, DungeonConstants.LAYER_OBJECT);
                pushedInto.setSavedObject(saved);
                m.setCell(source, x2, y2, z2, DungeonConstants.LAYER_OBJECT);
                saved.pushIntoAction(this.getObjectInventory(), pushedInto, x2,
                        y2, z2 - 1);
                this.redrawDungeon();
                app.getDungeonManager().setDirty(true);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.setCell(new Empty(), x2, y2, z2, DungeonConstants.LAYER_OBJECT);
        }
    }

    public boolean tryUpdatePositionRelative(final int x, final int y) {
        try {
            Application app = DungeonDiver4.getApplication();
            Dungeon m = app.getDungeonManager().getDungeon();
            AbstractDungeonObject below = m.getCell(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
                    DungeonConstants.LAYER_GROUND);
            AbstractDungeonObject nextBelow = m.getCell(m.getPlayerLocationX()
                    + x, m.getPlayerLocationY() + y, m.getPlayerLocationZ(),
                    DungeonConstants.LAYER_GROUND);
            AbstractDungeonObject nextAbove = m.getCell(m.getPlayerLocationX()
                    + x, m.getPlayerLocationY() + y, m.getPlayerLocationZ(),
                    DungeonConstants.LAYER_OBJECT);
            return this.checkSolid(this.savedDungeonObject, below, nextBelow,
                    nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public boolean tryUpdatePositionAbsolute(final int x, final int y,
            final int z) {
        try {
            Application app = DungeonDiver4.getApplication();
            Dungeon m = app.getDungeonManager().getDungeon();
            AbstractDungeonObject below = m.getCell(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
                    DungeonConstants.LAYER_GROUND);
            AbstractDungeonObject nextBelow = m.getCell(x, y, z,
                    DungeonConstants.LAYER_GROUND);
            AbstractDungeonObject nextAbove = m.getCell(x, y, z,
                    DungeonConstants.LAYER_OBJECT);
            return this.checkSolidAbsolute(this.savedDungeonObject, below,
                    nextBelow, nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    private boolean checkSolidAbsolute(final AbstractDungeonObject inside,
            final AbstractDungeonObject below,
            final AbstractDungeonObject nextBelow,
            final AbstractDungeonObject nextAbove) {
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
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.getCell(x, y, z, DungeonConstants.LAYER_OBJECT).preMoveAction(
                    true, x, y, this.getObjectInventory());
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
        m.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!(m.getCell(x, y, z, DungeonConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.getObjectInventory()))) {
                m.setCell(this.savedDungeonObject, m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        DungeonConstants.LAYER_OBJECT);
                m.setPlayerLocationX(x);
                m.setPlayerLocationY(y);
                m.setPlayerLocationZ(z);
                this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                        - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                        - this.vwMgr.getOffsetFactorY());
                this.savedDungeonObject = m.getCell(m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        DungeonConstants.LAYER_OBJECT);
                app.getDungeonManager().setDirty(true);
                this.savedDungeonObject.postMoveAction(false, x, y,
                        this.getObjectInventory());
                int px = m.getPlayerLocationX();
                int py = m.getPlayerLocationY();
                int pz = m.getPlayerLocationZ();
                m.updateVisibleSquares(px, py, pz);
                this.redrawDungeon();
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            DungeonDiver4.getApplication().showMessage(
                    "Can't go outside the dungeon");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            DungeonDiver4.getApplication().showMessage(
                    "Can't go outside the dungeon");
        }
    }

    public void updatePositionAbsoluteNoEvents(final int x, final int y,
            final int z, final int w) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        m.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!(m.getCell(x, y, z, DungeonConstants.LAYER_OBJECT)
                    .isConditionallySolid(this.getObjectInventory()))) {
                m.setCell(this.savedDungeonObject, m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        DungeonConstants.LAYER_OBJECT);
                m.setPlayerLocationX(x);
                m.setPlayerLocationY(y);
                m.setPlayerLocationZ(z);
                m.setPlayerLocationW(w);
                this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                        - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                        - this.vwMgr.getOffsetFactorY());
                this.savedDungeonObject = m.getCell(m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        DungeonConstants.LAYER_OBJECT);
                app.getDungeonManager().setDirty(true);
                int px = m.getPlayerLocationX();
                int py = m.getPlayerLocationY();
                int pz = m.getPlayerLocationZ();
                m.updateVisibleSquares(px, py, pz);
                this.redrawDungeon();
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            DungeonDiver4.getApplication().showMessage(
                    "Can't go outside the dungeon");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            DungeonDiver4.getApplication().showMessage(
                    "Can't go outside the dungeon");
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

    public void redrawDungeon() {
        this.gui.redrawDungeon();
    }

    void redrawOneSquare(int x, int y, boolean useDelay,
            AbstractDungeonObject obj4) {
        this.gui.redrawOneSquare(x, y, useDelay, obj4);
    }

    public void resetViewingWindowAndPlayerLocation() {
        this.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                - this.vwMgr.getOffsetFactorX());
        this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                - this.vwMgr.getOffsetFactorY());
    }

    public void resetPlayerLocation() {
        this.resetPlayerLocation(0);
    }

    public void resetPlayerLocation(int level) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        if (m != null) {
            m.switchLevel(level);
            m.setPlayerToStart();
        }
    }

    public void resetCurrentLevel() {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        this.resetLevel(m.getPlayerLocationW());
    }

    public void resetGameState() {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        app.getDungeonManager().setDirty(false);
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
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        app.getDungeonManager().setDirty(true);
        m.restore();
        m.resetVisibleSquares();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            this.resetPlayerLocation(level);
            this.resetViewingWindow();
            this.decay();
            this.oiMgr.restoreObjectInventory();
            this.redrawDungeon();
        }
    }

    public void exitGame() {
        this.stateChanged = true;
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        // Restore the dungeon
        m.restore();
        m.resetVisibleSquares();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            this.resetViewingWindowAndPlayerLocation();
        } else {
            app.getDungeonManager().setLoaded(false);
        }
        // Wipe the inventory
        this.oiMgr.resetObjectInventory();
        // Reset saved game flag
        this.savedGameFlag = false;
        app.getDungeonManager().setDirty(false);
        // Exit game
        this.hideOutput();
        app.getGUIManager().showGUI();
    }

    public JFrame getOutputFrame() {
        return this.gui.getOutputFrame();
    }

    public void decay() {
        this.savedDungeonObject = new Empty();
    }

    public void decayTo(AbstractDungeonObject decay) {
        if (this.actingRemotely) {
            DungeonDiver4
                    .getApplication()
                    .getDungeonManager()
                    .getDungeon()
                    .setCell(decay, this.remoteCoords[0], this.remoteCoords[1],
                            this.remoteCoords[2], DungeonConstants.LAYER_OBJECT);
        } else {
            this.savedDungeonObject = decay;
        }
    }

    private void doDelayedDecay() {
        if (this.actingRemotely) {
            DungeonDiver4
                    .getApplication()
                    .getDungeonManager()
                    .getDungeon()
                    .setCell(this.delayedDecayObject, this.remoteCoords[0],
                            this.remoteCoords[1], this.remoteCoords[2],
                            DungeonConstants.LAYER_OBJECT);
        } else {
            this.savedDungeonObject = this.delayedDecayObject;
        }
        this.delayedDecayActive = false;
    }

    public void delayedDecayTo(AbstractDungeonObject obj) {
        this.delayedDecayActive = true;
        this.delayedDecayObject = obj;
    }

    public void morph(final AbstractDungeonObject morphInto, final int x,
            final int y, final int z, final int e) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.setCell(morphInto, x, y, z, e);
            this.redrawDungeon();
            app.getDungeonManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morph(final AbstractDungeonObject morphInto, final int x,
            final int y, final int z) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.setCell(morphInto, x, y, z, morphInto.getLayer());
            this.redrawDungeon();
            app.getDungeonManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morph(final AbstractDungeonObject morphInto, final int x,
            final int y, final int z, final String msg) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.setCell(morphInto, x, y, z, morphInto.getLayer());
            DungeonDiver4.getApplication().showMessage(msg);
            this.keepNextMessage();
            this.redrawDungeon();
            app.getDungeonManager().setDirty(true);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    public void morphOther(final AbstractDungeonObject morphInto, final int x,
            final int y, final int e) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.setCell(morphInto, m.getPlayerLocationX() + x,
                    m.getPlayerLocationY() + y, m.getPlayerLocationZ(), e);
            this.redrawDungeon();
            app.getDungeonManager().setDirty(true);
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
        DungeonDiver4.getApplication().showMessage("Click to set destination");
    }

    void controllableTeleportHandler(final int x, final int y) {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
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
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
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
            AbstractDungeonObject target1 = m.getCell(destX, destY, destZ,
                    DungeonConstants.LAYER_GROUND);
            AbstractDungeonObject target2 = m.getCell(destX, destY, destZ,
                    DungeonConstants.LAYER_OBJECT);
            target1.determineCurrentAppearance(destX, destY, destZ);
            target2.determineCurrentAppearance(destX, destY, destZ);
            String gameName1 = target1.getGameName();
            String gameName2 = target2.getGameName();
            DungeonDiver4.getApplication().showMessage(
                    gameName2 + " on " + gameName1);
            SoundManager.playSound(SoundConstants.SOUND_IDENTIFY);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(destX, destY, destZ);
            DungeonDiver4.getApplication().showMessage(ev.getGameName());
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
        this.redrawDungeon();
    }

    public DungeonObjectInventory getObjectInventory() {
        return this.oiMgr.getObjectInventory();
    }

    public void loadGameHook(XDataReader dungeonFile, int formatVersion)
            throws IOException {
        Application app = DungeonDiver4.getApplication();
        this.oiMgr.readObjectInventory(dungeonFile, formatVersion);
        app.getDungeonManager().setScoresFileName(dungeonFile.readString());
        FileHooks.loadGameHook(dungeonFile);
    }

    public void saveGameHook(XDataWriter dungeonFile) throws IOException {
        Application app = DungeonDiver4.getApplication();
        this.oiMgr.writeObjectInventory(dungeonFile);
        dungeonFile.writeString(app.getDungeonManager().getScoresFileName());
        FileHooks.saveGameHook(dungeonFile);
    }

    public void playDungeon() {
        Application app = DungeonDiver4.getApplication();
        Dungeon m = app.getDungeonManager().getDungeon();
        if (app.getDungeonManager().getLoaded()) {
            this.gui.initViewManager();
            app.getGUIManager().hideGUI();
            app.setInGame();
            if (this.stateChanged) {
                // Initialize only if the dungeon state has changed
                app.getDungeonManager()
                        .getDungeon()
                        .switchLevel(
                                app.getDungeonManager().getDungeon()
                                        .getStartLevel());
                this.savedDungeonObject = new Empty();
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
            CommonDialogs.showTitledDialog(m.getDungeonStartMessage(),
                    m.getDungeonTitle());
            CommonDialogs.showTitledDialog(m.getLevelStartMessage(),
                    m.getLevelTitle());
            this.showOutput();
            this.redrawDungeon();
        } else {
            CommonDialogs.showDialog("No Dungeon Opened");
        }
    }

    public void showOutput() {
        this.gui.showOutput();
    }

    public void hideOutput() {
        this.gui.hideOutput();
    }

    public void setSavedDungeonObject(AbstractDungeonObject saved) {
        this.savedDungeonObject = saved;
    }
}
