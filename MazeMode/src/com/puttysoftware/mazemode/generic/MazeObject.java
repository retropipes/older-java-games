/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.generic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.BitSet;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.editor.rulesets.RuleSet;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.maze.Maze;
import com.puttysoftware.mazemode.maze.MazeConstants;
import com.puttysoftware.mazemode.objects.GhostAmulet;
import com.puttysoftware.mazemode.objects.PasswallBoots;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;
import com.puttysoftware.randomnumbers.RandomRange;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class MazeObject implements DirectionConstants, TypeConstants,
        ArrowTypeConstants, RandomGenerationRule {
    // Properties
    private SolidProperties sp;
    private boolean pushable;
    private boolean pushableInto;
    private boolean pushableOut;
    private boolean pullable;
    private boolean pullableInto;
    private boolean pullableOut;
    private boolean friction;
    private boolean usable;
    private int uses;
    private boolean destroyable;
    private boolean chainReacts;
    private boolean isInventoryable;
    protected BitSet type;
    private int timerValue;
    private int initialTimerValue;
    private boolean timerActive;
    private MazeObject prerequisite;
    private RuleSet ruleSet;
    public static final int DEFAULT_CUSTOM_VALUE = 0;
    protected static final int CUSTOM_FORMAT_MANUAL_OVERRIDE = -1;

    // Constructors
    public MazeObject(final boolean isSolid) {
        this.sp = new SolidProperties();
        this.sp.setSolid(isSolid);
        this.pushable = false;
        this.pushableInto = false;
        this.pushableOut = false;
        this.pullable = false;
        this.pullableInto = false;
        this.pullableOut = false;
        this.friction = true;
        this.usable = false;
        this.uses = 0;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = false;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.setTypes();
    }

    public MazeObject(final boolean isSolidXN, final boolean isSolidXS,
            final boolean isSolidXE, final boolean isSolidXW,
            final boolean isSolidIN, final boolean isSolidIS,
            final boolean isSolidIE, final boolean isSolidIW) {
        this.sp = new SolidProperties();
        this.sp.setDirectionallySolid(true, DirectionConstants.DIRECTION_NORTH,
                isSolidXN);
        this.sp.setDirectionallySolid(true, DirectionConstants.DIRECTION_SOUTH,
                isSolidXS);
        this.sp.setDirectionallySolid(true, DirectionConstants.DIRECTION_EAST,
                isSolidXE);
        this.sp.setDirectionallySolid(true, DirectionConstants.DIRECTION_WEST,
                isSolidXW);
        this.sp.setDirectionallySolid(false, DirectionConstants.DIRECTION_NORTH,
                isSolidIN);
        this.sp.setDirectionallySolid(false, DirectionConstants.DIRECTION_SOUTH,
                isSolidIS);
        this.sp.setDirectionallySolid(false, DirectionConstants.DIRECTION_EAST,
                isSolidIE);
        this.sp.setDirectionallySolid(false, DirectionConstants.DIRECTION_WEST,
                isSolidIW);
        this.sp.setDirectionallySolid(true,
                DirectionConstants.DIRECTION_NORTHEAST, true);
        this.sp.setDirectionallySolid(true,
                DirectionConstants.DIRECTION_SOUTHEAST, true);
        this.sp.setDirectionallySolid(true,
                DirectionConstants.DIRECTION_NORTHWEST, true);
        this.sp.setDirectionallySolid(true,
                DirectionConstants.DIRECTION_SOUTHWEST, true);
        this.sp.setDirectionallySolid(false,
                DirectionConstants.DIRECTION_NORTHEAST, true);
        this.sp.setDirectionallySolid(false,
                DirectionConstants.DIRECTION_SOUTHEAST, true);
        this.sp.setDirectionallySolid(false,
                DirectionConstants.DIRECTION_NORTHWEST, true);
        this.sp.setDirectionallySolid(false,
                DirectionConstants.DIRECTION_SOUTHWEST, true);
        this.pushable = false;
        this.pushableInto = false;
        this.pushableOut = false;
        this.pullable = false;
        this.pullableInto = false;
        this.pullableOut = false;
        this.friction = true;
        this.usable = false;
        this.uses = 0;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = false;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.timerActive = false;
        this.setTypes();
    }

    public MazeObject(final boolean isSolid, final boolean isPushable,
            final boolean doesAcceptPushInto, final boolean doesAcceptPushOut,
            final boolean isPullable, final boolean doesAcceptPullInto,
            final boolean doesAcceptPullOut, final boolean hasFriction,
            final boolean isUsable, final int newUses) {
        this.sp = new SolidProperties();
        this.sp.setSolid(isSolid);
        this.pushable = isPushable;
        this.pushableInto = doesAcceptPushInto;
        this.pushableOut = doesAcceptPushOut;
        this.pullable = isPullable;
        this.pullableInto = doesAcceptPullInto;
        this.pullableOut = doesAcceptPullOut;
        this.friction = hasFriction;
        this.usable = isUsable;
        this.uses = newUses;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = false;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.timerActive = false;
        this.setTypes();
    }

    public MazeObject(final boolean isSolid, final boolean isPushable,
            final boolean doesAcceptPushInto, final boolean doesAcceptPushOut,
            final boolean isPullable, final boolean doesAcceptPullInto,
            final boolean doesAcceptPullOut, final boolean hasFriction,
            final boolean isUsable, final int newUses,
            final boolean isDestroyable, final boolean doesChainReact) {
        this.sp = new SolidProperties();
        this.sp.setSolid(isSolid);
        this.pushable = isPushable;
        this.pushableInto = doesAcceptPushInto;
        this.pushableOut = doesAcceptPushOut;
        this.pullable = isPullable;
        this.pullableInto = doesAcceptPullInto;
        this.pullableOut = doesAcceptPullOut;
        this.friction = hasFriction;
        this.usable = isUsable;
        this.uses = newUses;
        this.destroyable = isDestroyable;
        this.chainReacts = doesChainReact;
        this.isInventoryable = false;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.timerActive = false;
        this.setTypes();
    }

    public MazeObject(final boolean isSolid, final boolean isUsable,
            final int newUses, final boolean canBeInventoried) {
        this.sp = new SolidProperties();
        this.sp.setSolid(isSolid);
        this.pushable = false;
        this.pushableInto = false;
        this.pushableOut = false;
        this.pullable = false;
        this.pullableInto = false;
        this.pullableOut = false;
        this.friction = true;
        this.usable = isUsable;
        this.uses = newUses;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = canBeInventoried;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.timerActive = false;
        this.setTypes();
    }

    public MazeObject() {
        this.sp = new SolidProperties();
        this.pushable = false;
        this.pushableInto = false;
        this.pushableOut = false;
        this.pullable = false;
        this.pullableInto = false;
        this.pullableOut = false;
        this.friction = true;
        this.usable = false;
        this.uses = 0;
        this.destroyable = true;
        this.chainReacts = false;
        this.isInventoryable = false;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.timerActive = false;
        this.setTypes();
    }

    // Methods
    @Override
    public MazeObject clone() {
        try {
            final MazeObject copy = this.getClass().getConstructor().newInstance();
            copy.sp = this.sp.clone();
            copy.pushable = this.pushable;
            copy.pushableInto = this.pushableInto;
            copy.pushableOut = this.pushableOut;
            copy.pullable = this.pullable;
            copy.pullableInto = this.pullableInto;
            copy.pullableOut = this.pullableOut;
            copy.friction = this.friction;
            copy.usable = this.usable;
            copy.uses = this.uses;
            copy.destroyable = this.destroyable;
            copy.chainReacts = this.chainReacts;
            copy.isInventoryable = this.isInventoryable;
            copy.type = (BitSet) this.type.clone();
            copy.timerValue = this.timerValue;
            copy.initialTimerValue = this.initialTimerValue;
            copy.timerActive = this.timerActive;
            if (this.ruleSet != null) {
                copy.ruleSet = this.ruleSet.clone();
            }
            return copy;
        } catch (final InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new AssertionError("Should not ever get here!");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.chainReacts ? 1231 : 1237);
        result = prime * result + (this.destroyable ? 1231 : 1237);
        result = prime * result + (this.friction ? 1231 : 1237);
        result = prime * result + this.initialTimerValue;
        result = prime * result + (this.isInventoryable ? 1231 : 1237);
        result = prime * result + (this.prerequisite == null ? 0
                : this.prerequisite.hashCode());
        result = prime * result + (this.pullable ? 1231 : 1237);
        result = prime * result + (this.pullableInto ? 1231 : 1237);
        result = prime * result + (this.pullableOut ? 1231 : 1237);
        result = prime * result + (this.pushable ? 1231 : 1237);
        result = prime * result + (this.pushableInto ? 1231 : 1237);
        result = prime * result + (this.pushableOut ? 1231 : 1237);
        result = prime * result + (this.sp == null ? 0 : this.sp.hashCode());
        result = prime * result + (this.timerActive ? 1231 : 1237);
        result = prime * result + this.timerValue;
        result = prime * result
                + (this.type == null ? 0 : this.type.hashCode());
        result = prime * result + (this.usable ? 1231 : 1237);
        result = prime * result + this.uses;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MazeObject)) {
            return false;
        }
        final MazeObject other = (MazeObject) obj;
        if (this.chainReacts != other.chainReacts) {
            return false;
        }
        if (this.destroyable != other.destroyable) {
            return false;
        }
        if (this.friction != other.friction) {
            return false;
        }
        if (this.initialTimerValue != other.initialTimerValue) {
            return false;
        }
        if (this.isInventoryable != other.isInventoryable) {
            return false;
        }
        if (this.prerequisite == null) {
            if (other.prerequisite != null) {
                return false;
            }
        } else if (!this.prerequisite.equals(other.prerequisite)) {
            return false;
        }
        if (this.pullable != other.pullable) {
            return false;
        }
        if (this.pullableInto != other.pullableInto) {
            return false;
        }
        if (this.pullableOut != other.pullableOut) {
            return false;
        }
        if (this.pushable != other.pushable) {
            return false;
        }
        if (this.pushableInto != other.pushableInto) {
            return false;
        }
        if (this.pushableOut != other.pushableOut) {
            return false;
        }
        if (this.sp == null) {
            if (other.sp != null) {
                return false;
            }
        } else if (!this.sp.equals(other.sp)) {
            return false;
        }
        if (this.timerActive != other.timerActive) {
            return false;
        }
        if (this.timerValue != other.timerValue) {
            return false;
        }
        if (this.type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!this.type.equals(other.type)) {
            return false;
        }
        if (this.usable != other.usable) {
            return false;
        }
        if (this.uses != other.uses) {
            return false;
        }
        return true;
    }

    public boolean hasRuleSet() {
        return this.ruleSet != null;
    }

    public void giveRuleSet() {
        this.ruleSet = new RuleSet();
    }

    public void takeRuleSet() {
        this.ruleSet = null;
    }

    public RuleSet getRuleSet() {
        return this.ruleSet;
    }

    public boolean isConditionallySolid(final ObjectInventory inv) {
        // Handle ghost amulet and passwall boots
        if (inv.isItemThere(new GhostAmulet())
                || inv.isItemThere(new PasswallBoots())) {
            return false;
        } else {
            return this.sp.isSolid();
        }
    }

    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final ObjectInventory inv) {
        // Handle ghost amulet and passwall boots
        if (inv.isItemThere(new GhostAmulet())
                || inv.isItemThere(new PasswallBoots())) {
            return false;
        } else {
            return this.sp.isDirectionallySolid(ie, dirX, dirY);
        }
    }

    public boolean isSolid() {
        return this.sp.isSolid();
    }

    public boolean isDirectionallySolid(final boolean ie, final int dirX,
            final int dirY) {
        return this.sp.isDirectionallySolid(ie, dirX, dirY);
    }

    public boolean isOfType(final int testType) {
        return this.type.get(testType);
    }

    protected abstract void setTypes();

    public boolean isPushable() {
        return this.pushable;
    }

    public boolean isPullable() {
        return this.pullable;
    }

    public boolean isPullableInto() {
        return this.pullableInto;
    }

    public boolean isPushableInto() {
        return this.pushableInto;
    }

    public boolean isPullableOut() {
        return this.pullableOut;
    }

    public boolean isPushableOut() {
        return this.pushableOut;
    }

    public boolean hasFriction() {
        return this.friction;
    }

    public boolean isUsable() {
        return this.usable;
    }

    public int getUses() {
        return this.uses;
    }

    public boolean isDestroyable() {
        return this.destroyable;
    }

    public boolean doesChainReact() {
        return this.chainReacts;
    }

    public boolean isInventoryable() {
        return this.isInventoryable;
    }

    // Scripting
    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param inv
     * @return
     */
    public boolean preMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        return true;
    }

    public abstract void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv);

    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param inv
     */
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_FAILED);
        MazeMode.getApplication().showMessage("Can't go that way");
    }

    /**
     *
     * @param inv
     * @param moving
     * @return
     */
    public boolean hasFrictionConditionally(final ObjectInventory inv,
            final boolean moving) {
        return this.hasFriction();
    }

    public void gameProbeHook() {
        MazeMode.getApplication().showMessage(this.getName());
    }

    public void editorPlaceHook() {
        // Do nothing
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void editorGenerateHook(final int x, final int y, final int z) {
        // Do nothing
    }

    public void editorProbeHook() {
        MazeMode.getApplication().showMessage(this.getName());
    }

    public MazeObject editorPropertiesHook() {
        return null;
    }

    /**
     *
     * @param inv
     * @param mo
     * @param x
     * @param y
     * @param pushX
     * @param pushY
     */
    public void pushAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param pushed
     * @param x
     * @param y
     * @param z
     */
    public void pushIntoAction(final ObjectInventory inv,
            final MazeObject pushed, final int x, final int y, final int z) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param pushed
     * @param x
     * @param y
     * @param z
     */
    public void pushOutAction(final ObjectInventory inv,
            final MazeObject pushed, final int x, final int y, final int z) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param x
     * @param y
     * @param pushX
     * @param pushY
     */
    public void pushFailedAction(final ObjectInventory inv, final int x,
            final int y, final int pushX, final int pushY) {
        // Play push failed sound, if it's enabled
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_ACTION_FAILED);
        MazeMode.getApplication().getGameManager().keepNextMessage();
        MazeMode.getApplication().showMessage("Can't push that");
    }

    /**
     *
     * @param inv
     * @param mo
     * @param x
     * @param y
     * @param pullX
     * @param pullY
     */
    public void pullAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pullX, final int pullY) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param pulled
     * @param x
     * @param y
     * @param z
     */
    public void pullIntoAction(final ObjectInventory inv,
            final MazeObject pulled, final int x, final int y, final int z) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param pulled
     * @param x
     * @param y
     * @param z
     */
    public void pullOutAction(final ObjectInventory inv,
            final MazeObject pulled, final int x, final int y, final int z) {
        // Do nothing
    }

    /**
     *
     * @param inv
     * @param x
     * @param y
     * @param pullX
     * @param pullY
     */
    public void pullFailedAction(final ObjectInventory inv, final int x,
            final int y, final int pullX, final int pullY) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_ACTION_FAILED);
        MazeMode.getApplication().getGameManager().keepNextMessage();
        MazeMode.getApplication().showMessage("Can't pull that");
    }

    /**
     *
     * @param mo
     * @param x
     * @param y
     * @param z
     */
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        // Do nothing
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void useHelper(final int x, final int y, final int z) {
        // Do nothing
    }

    public final boolean isTimerActive() {
        return this.timerActive;
    }

    public final void activateTimer(final int ticks) {
        this.timerActive = true;
        this.timerValue = ticks;
        this.initialTimerValue = ticks;
    }

    public final void deactivateTimer() {
        this.timerActive = false;
        this.timerValue = 0;
        this.initialTimerValue = 0;
    }

    public final void extendTimer(final int ticks) {
        if (this.timerActive) {
            this.timerValue += ticks;
        }
    }

    public final void extendTimerByInitialValue() {
        if (this.timerActive) {
            this.timerValue += this.initialTimerValue;
        }
    }

    public final void resetTimer() {
        if (this.timerActive) {
            this.timerValue = this.initialTimerValue;
        }
    }

    public final void tickTimer(final int dirX, final int dirY) {
        if (this.timerActive) {
            this.timerValue--;
            if (this.timerValue == 0) {
                this.timerActive = false;
                this.initialTimerValue = 0;
                this.timerExpiredAction(dirX, dirY);
            }
        }
    }

    /**
     *
     * @param dirX
     * @param dirY
     */
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Do nothing
    }

    /**
     *
     * @param locX
     * @param locY
     * @param locZ
     * @param dirX
     * @param dirY
     * @param arrowType
     * @param inv
     * @return
     */
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        // Stop non-ghost arrows passing through solid objects
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_GHOST) {
            return true;
        } else {
            if (this.isConditionallySolid(inv)) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public String gameRenderHook(final int x, final int y, final int z) {
        return this.getGameName();
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public String editorRenderHook(final int x, final int y, final int z) {
        return this.getName();
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void chainReactionAction(final int x, final int y, final int z) {
        // Do nothing
    }

    public boolean defersSetProperties() {
        return false;
    }

    public boolean overridesDefaultPostMove() {
        return false;
    }

    public String getGameName() {
        return this.getName();
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void determineCurrentAppearance(final int x, final int y,
            final int z) {
        // Do nothing
    }

    public void stepAction() {
        // Do nothing
    }

    abstract public String getName();

    public boolean canMove() {
        return false;
    }

    public boolean isMoving() {
        return false;
    }

    public final String getXIdentifier() {
        return this.getName();
    }

    abstract public String getPluralName();

    abstract public String getDescription();

    abstract public int getLayer();

    abstract public int getCustomProperty(int propID);

    abstract public void setCustomProperty(int propID, int value);

    public int getCustomFormat() {
        return 0;
    }

    @Override
    public boolean shouldGenerateObject(final Maze maze, final int row,
            final int col, final int floor, final int level, final int layer) {
        if (layer == MazeConstants.LAYER_OBJECT) {
            // Handle object layer
            if (!this.isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                // Limit generation of other objects to 20%, unless required
                if (this.isRequired()) {
                    return true;
                } else {
                    final RandomRange r = new RandomRange(1, 100);
                    if (r.generate() <= 20) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                // Generate pass-through objects at 100%
                return true;
            }
        } else {
            // Handle ground layer
            if (this.isOfType(TypeConstants.TYPE_FIELD)) {
                // Limit generation of fields to 20%
                final RandomRange r = new RandomRange(1, 100);
                if (r.generate() <= 20) {
                    return true;
                } else {
                    return false;
                }
            } else {
                // Generate other ground at 100%
                return true;
            }
        }
    }

    @Override
    public int getMinimumRequiredQuantity(final Maze maze) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public int getMaximumRequiredQuantity(final Maze maze) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    public final boolean hasPrerequisite() {
        return this.prerequisite != null;
    }

    public final MazeObject getPrerequisite() {
        return this.prerequisite;
    }

    public final void setPrerequisite(final MazeObject prereq) {
        this.prerequisite = prereq;
    }

    public final boolean hasNthPrerequisite(final int N) {
        if (this.hasPrerequisite()) {
            final MazeObject pr = this.getPrerequisite();
            if (N == 1) {
                return true;
            } else {
                return pr.hasNthPrerequisite(N - 1);
            }
        } else {
            return false;
        }
    }

    public final void writeMazeObjectX(final XDataWriter writer)
            throws IOException {
        writer.writeString(this.getXIdentifier());
        final int cc = this.getCustomFormat();
        if (cc == MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
            this.writeMazeObjectHookX(writer);
        } else {
            for (int x = 0; x < cc; x++) {
                final int cx = this.getCustomProperty(x + 1);
                writer.writeInt(cx);
            }
        }
    }

    public final MazeObject readMazeObjectX(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getXIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                return this.readMazeObjectHookX(reader, ver);
            } else {
                for (int x = 0; x < cc; x++) {
                    final int cx = reader.readInt();
                    this.setCustomProperty(x + 1, cx);
                }
            }
            return this;
        } else {
            return null;
        }
    }

    public final MazeObject readMazeObjectX2(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getXIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                return this.readMazeObjectHookX(reader, ver);
            } else {
                for (int x = 0; x < cc; x++) {
                    final int cx = reader.readInt();
                    this.setCustomProperty(x + 1, cx);
                }
            }
            return this;
        } else {
            return null;
        }
    }

    public final MazeObject readMazeObjectX3(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getXIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                return this.readMazeObjectHookX(reader, ver);
            } else {
                for (int x = 0; x < cc; x++) {
                    final int cx = reader.readInt();
                    this.setCustomProperty(x + 1, cx);
                }
            }
            return this;
        } else {
            return null;
        }
    }

    public final MazeObject readMazeObjectX4(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getXIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                return this.readMazeObjectHookX(reader, ver);
            } else {
                for (int x = 0; x < cc; x++) {
                    final int cx = reader.readInt();
                    this.setCustomProperty(x + 1, cx);
                }
            }
            return this;
        } else {
            return null;
        }
    }

    /**
     *
     * @param writer
     * @throws IOException
     */
    protected void writeMazeObjectHookX(final XDataWriter writer)
            throws IOException {
        // Do nothing - but let subclasses override
    }

    /**
     *
     * @param reader
     * @param formatVersion
     * @return
     * @throws IOException
     */
    protected MazeObject readMazeObjectHookX(final XDataReader reader,
            final int formatVersion) throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }
}
