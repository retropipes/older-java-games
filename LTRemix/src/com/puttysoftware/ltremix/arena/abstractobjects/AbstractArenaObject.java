/*  LTRemix: An Arena-Solving Game
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ltremix.arena.abstractobjects;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.BitSet;

import com.puttysoftware.llds.CloneableObject;
import com.puttysoftware.ltremix.LTRemix;
import com.puttysoftware.ltremix.arena.objects.Empty;
import com.puttysoftware.ltremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ltremix.resourcemanagers.SoundManager;
import com.puttysoftware.ltremix.stringmanagers.StringConstants;
import com.puttysoftware.ltremix.stringmanagers.StringLoader;
import com.puttysoftware.ltremix.utilities.ColorConstants;
import com.puttysoftware.ltremix.utilities.ColorResolver;
import com.puttysoftware.ltremix.utilities.DirectionConstants;
import com.puttysoftware.ltremix.utilities.DirectionResolver;
import com.puttysoftware.ltremix.utilities.LaserTypeConstants;
import com.puttysoftware.ltremix.utilities.MaterialConstants;
import com.puttysoftware.ltremix.utilities.RangeTypeConstants;
import com.puttysoftware.ltremix.utilities.TypeConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class AbstractArenaObject extends CloneableObject {
    // Properties
    private boolean solid;
    private boolean pushable;
    private boolean friction;
    protected BitSet type;
    private int timerValue;
    private int initialTimerValue;
    private boolean timerActive;
    private int frameNumber;
    private int frameCount;
    private int direction;
    private boolean diagonalOnly;
    private int color;
    private int material;
    private boolean imageEnabled;
    static final int DEFAULT_CUSTOM_VALUE = 0;
    protected static final int CUSTOM_FORMAT_MANUAL_OVERRIDE = -1;
    private static final int PLASTIC_MINIMUM_REACTION_FORCE = 0;
    private static final int DEFAULT_MINIMUM_REACTION_FORCE = 1;
    private static final int METAL_MINIMUM_REACTION_FORCE = 2;
    private AbstractArenaObject savedObject;
    private AbstractArenaObject previousState;
    private int instanceNum;

    // Constructors
    AbstractArenaObject(final boolean isSolid) {
        this.solid = isSolid;
        this.pushable = false;
        this.friction = true;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.frameNumber = 0;
        this.frameCount = 3;
        this.direction = -1;
        this.diagonalOnly = false;
        this.color = -1;
        this.material = MaterialConstants.MATERIAL_DEFAULT;
        this.imageEnabled = true;
        this.instanceNum = 0;
    }

    AbstractArenaObject(final boolean isSolid, final boolean isPushable,
            final boolean hasFriction) {
        this.solid = isSolid;
        this.pushable = isPushable;
        this.friction = hasFriction;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.timerActive = false;
        this.frameNumber = 0;
        this.frameCount = 3;
        this.direction = -1;
        this.diagonalOnly = false;
        this.color = -1;
        this.material = MaterialConstants.MATERIAL_DEFAULT;
        this.imageEnabled = true;
        this.instanceNum = 0;
    }

    public AbstractArenaObject() {
        this.solid = false;
        this.pushable = false;
        this.friction = true;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.timerActive = false;
        this.frameNumber = 0;
        this.frameCount = 3;
        this.direction = -1;
        this.diagonalOnly = false;
        this.color = -1;
        this.material = MaterialConstants.MATERIAL_DEFAULT;
        this.imageEnabled = true;
        this.instanceNum = 0;
    }

    // Methods
    @Override
    public AbstractArenaObject clone() {
        try {
            final AbstractArenaObject copy = this.getClass().getConstructor().newInstance();
            copy.solid = this.solid;
            copy.pushable = this.pushable;
            copy.friction = this.friction;
            copy.type = (BitSet) this.type.clone();
            copy.timerValue = this.timerValue;
            copy.initialTimerValue = this.initialTimerValue;
            copy.timerActive = this.timerActive;
            copy.frameNumber = this.frameNumber;
            copy.frameCount = this.frameCount;
            copy.direction = this.direction;
            copy.diagonalOnly = this.diagonalOnly;
            copy.color = this.color;
            copy.material = this.material;
            copy.instanceNum = this.instanceNum;
            return copy;
        } catch (final InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            LTRemix.getErrorLogger().logError(e);
            return null;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.friction ? 1231 : 1237);
        result = prime * result + this.initialTimerValue;
        result = prime * result + (this.pushable ? 1231 : 1237);
        result = prime * result + (this.solid ? 1231 : 1237);
        result = prime * result + (this.timerActive ? 1231 : 1237);
        result = prime * result + this.timerValue;
        result = prime * result
                + (this.type == null ? 0 : this.type.hashCode());
        result = prime * result + this.direction;
        result = prime * result + this.color;
        result = prime * result + this.instanceNum;
        return prime * result + this.material;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractArenaObject)) {
            return false;
        }
        final AbstractArenaObject other = (AbstractArenaObject) obj;
        if (this.friction != other.friction) {
            return false;
        }
        if (this.initialTimerValue != other.initialTimerValue) {
            return false;
        }
        if (this.pushable != other.pushable) {
            return false;
        }
        if (this.solid != other.solid) {
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
        if (this.direction != other.direction) {
            return false;
        }
        if (this.color != other.color) {
            return false;
        }
        if (this.instanceNum != other.instanceNum) {
            return false;
        }
        if (this.material != other.material) {
            return false;
        }
        return true;
    }

    public int getInstanceNum() {
        return this.instanceNum;
    }

    public void setInstanceNum(final int value) {
        this.instanceNum = value;
    }

    public boolean isEnabled() {
        return this.imageEnabled;
    }

    public void setEnabled(final boolean value) {
        this.imageEnabled = value;
    }

    public final AbstractArenaObject getSavedObject() {
        return this.savedObject;
    }

    public final void setSavedObject(final AbstractArenaObject obj) {
        this.savedObject = obj;
    }

    public final boolean hasPreviousState() {
        return this.previousState != null;
    }

    public final AbstractArenaObject getPreviousState() {
        return this.previousState;
    }

    public final void setPreviousState(final AbstractArenaObject obj) {
        this.previousState = obj;
    }

    public final void setFrameCount(final int frames) {
        this.frameCount = frames;
    }

    public final int getFrameNumber() {
        return this.frameNumber;
    }

    public final void setFrameNumber(final int frame) {
        this.frameNumber = frame;
    }

    public final void toggleFrameNumber() {
        if (this.isAnimated()) {
            this.frameNumber++;
            if (this.frameNumber > this.frameCount) {
                this.frameNumber = 1;
            }
        }
    }

    private final boolean isAnimated() {
        return this.frameNumber > 0;
    }

    public final int getDirection() {
        return this.direction;
    }

    public final void toggleDirection() {
        if (this.hasDirection()) {
            this.direction += 2;
            if (this.direction >= DirectionConstants.COUNT) {
                if (this.diagonalOnly) {
                    this.direction = DirectionConstants.NORTHWEST;
                } else {
                    this.direction = DirectionConstants.NORTH;
                }
            }
        }
    }

    public final boolean hitReflectiveSide(final int dir) {
        int trigger1, trigger2;
        trigger1 = this.direction - 1;
        if (trigger1 < 0) {
            trigger1 = DirectionConstants.COUNT - 1;
        }
        if (trigger1 >= DirectionConstants.COUNT) {
            trigger1 = 0;
        }
        trigger2 = this.direction + 1;
        if (trigger2 < 0) {
            trigger2 = DirectionConstants.COUNT - 1;
        }
        if (trigger2 >= DirectionConstants.COUNT) {
            trigger2 = 0;
        }
        return dir == trigger1 || dir == trigger2;
    }

    public final void setDirection(final int dir) {
        if (this.isDirectional()) {
            if (this.diagonalOnly) {
                if (dir == DirectionConstants.NORTH) {
                    this.direction = DirectionConstants.NORTHWEST;
                } else if (dir == DirectionConstants.EAST) {
                    this.direction = DirectionConstants.NORTHEAST;
                } else if (dir == DirectionConstants.SOUTH) {
                    this.direction = DirectionConstants.SOUTHEAST;
                } else if (dir == DirectionConstants.WEST) {
                    this.direction = DirectionConstants.SOUTHWEST;
                } else {
                    this.direction = dir;
                }
            } else {
                this.direction = dir;
            }
        }
    }

    private final boolean hasDirection() {
        return this.direction >= 0;
    }

    public final int getMaterial() {
        return this.material;
    }

    protected final void setMaterial(final int mat) {
        this.material = mat;
    }

    /**
     *
     * @param materialID
     * @return
     */
    public AbstractArenaObject changesToOnExposure(final int materialID) {
        return this;
    }

    public final int getColor() {
        return this.color;
    }

    public final void setColor(final int col) {
        this.color = col;
    }

    private final boolean hasColor() {
        return this.color >= 0;
    }

    private final void toggleColor() {
        if (this.hasColor()) {
            this.color++;
            if (this.color >= ColorConstants.COLOR_COUNT) {
                this.color = ColorConstants.COLOR_GRAY;
            }
        }
    }

    public final void setDiagonalOnly(final boolean value) {
        this.diagonalOnly = value;
    }

    public final boolean isPushable() {
        return this.pushable;
    }

    public final boolean isSolid() {
        return this.solid;
    }

    public boolean isConditionallySolid() {
        return this.solid;
    }

    public final boolean isOfType(final int testType) {
        return this.type.get(testType);
    }

    public final boolean hasFriction() {
        return this.friction;
    }

    // Scripting
    public abstract void postMoveAction(final int dirX, final int dirY,
            int dirZ);

    /**
     *
     * @param locX
     * @param locY
     * @param locZ
     */
    public void moveFailedAction(final int locX, final int locY,
            final int locZ) {
        SoundManager.playSound(SoundConstants.SOUND_BUMP_HEAD);
    }

    public AbstractArenaObject attributeGameRenderHook() {
        return null;
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public boolean editorPlaceHook(final int x, final int y, final int z) {
        return true;
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void editorRemoveHook(final int x, final int y, final int z) {
        // Do nothing
    }

    public AbstractArenaObject editorPropertiesHook() {
        if (this.hasDirection()) {
            this.toggleDirection();
            return this;
        } else if (this.hasColor()) {
            this.toggleColor();
            return this;
        } else {
            return null;
        }
    }

    /**
     *
     * @param pushed
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean pushIntoAction(final AbstractMovableObject pushed,
            final int x, final int y, final int z) {
        // Do nothing
        return true;
    }

    /**
     *
     * @param pushed
     * @param x
     * @param y
     * @param z
     */
    public void pushCollideAction(final AbstractMovableObject pushed,
            final int x, final int y, final int z) {
        // Do nothing
    }

    protected void pushCrushAction(final int x, final int y, final int z) {
        // Object crushed
        SoundManager.playSound(SoundConstants.SOUND_CRUSH);
        LTRemix.getApplication().getGameManager().morph(new Empty(), x, y, z,
                this.getPrimaryLayer());
    }

    /**
     *
     * @param pushed
     * @param x
     * @param y
     * @param z
     */
    public void pushOutAction(final AbstractMovableObject pushed, final int x,
            final int y, final int z) {
        // Do nothing
    }

    public final void activateTimer(final int ticks) {
        this.timerActive = true;
        this.timerValue = ticks;
        this.initialTimerValue = ticks;
    }

    public final void tickTimer(final int dirX, final int dirY,
            final int actionType) {
        if (this.timerActive) {
            if (this.acceptTick(actionType)) {
                this.timerValue--;
                if (this.timerValue == 0) {
                    this.timerActive = false;
                    this.initialTimerValue = 0;
                    this.timerExpiredAction(dirX, dirY);
                }
            }
        }
    }

    /**
     *
     * @param actionType
     * @return
     */
    public boolean acceptTick(final int actionType) {
        return true;
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
     * @param rangeType
     * @param forceUnits
     * @return
     */
    public boolean rangeAction(final int locX, final int locY, final int locZ,
            final int dirX, final int dirY, final int rangeType,
            final int forceUnits) {
        if (RangeTypeConstants.getMaterialForRangeType(
                rangeType) == MaterialConstants.MATERIAL_FIRE
                && this.getMaterial() == MaterialConstants.MATERIAL_WOODEN
                && this.changesToOnExposure(
                        MaterialConstants.MATERIAL_FIRE) != null) {
            // Burn wooden object
            SoundManager.playSound(SoundConstants.SOUND_WOOD_BURN);
            LTRemix.getApplication().getGameManager().morph(
                    this.changesToOnExposure(MaterialConstants.MATERIAL_FIRE),
                    locX + dirX, locY + dirY, locZ, this.getPrimaryLayer());
            return true;
        } else if (RangeTypeConstants.getMaterialForRangeType(
                rangeType) == MaterialConstants.MATERIAL_ICE
                && (this.getMaterial() == MaterialConstants.MATERIAL_METALLIC
                        || this.getMaterial() == MaterialConstants.MATERIAL_WOODEN
                        || this.getMaterial() == MaterialConstants.MATERIAL_PLASTIC)
                && this.changesToOnExposure(
                        MaterialConstants.MATERIAL_ICE) != null) {
            // Freeze metal, wooden, or plastic object
            SoundManager.playSound(SoundConstants.SOUND_FROZEN);
            LTRemix.getApplication().getGameManager().morph(
                    this.changesToOnExposure(MaterialConstants.MATERIAL_ICE),
                    locX + dirX, locY + dirY, locZ, this.getPrimaryLayer());
            return true;
        } else if (RangeTypeConstants.getMaterialForRangeType(
                rangeType) == MaterialConstants.MATERIAL_FIRE
                && this.getMaterial() == MaterialConstants.MATERIAL_ICE
                && this.changesToOnExposure(
                        MaterialConstants.MATERIAL_FIRE) != null) {
            // Melt icy object
            SoundManager.playSound(SoundConstants.SOUND_DEFROST);
            LTRemix.getApplication().getGameManager().morph(
                    this.changesToOnExposure(MaterialConstants.MATERIAL_FIRE),
                    locX + dirX, locY + dirY, locZ, this.getPrimaryLayer());
            return true;
        } else if (RangeTypeConstants.getMaterialForRangeType(
                rangeType) == MaterialConstants.MATERIAL_ICE
                && this.getMaterial() == MaterialConstants.MATERIAL_FIRE
                && this.changesToOnExposure(
                        MaterialConstants.MATERIAL_ICE) != null) {
            // Cool hot object
            SoundManager.playSound(SoundConstants.SOUND_COOL_OFF);
            LTRemix.getApplication().getGameManager().morph(
                    this.changesToOnExposure(MaterialConstants.MATERIAL_ICE),
                    locX + dirX, locY + dirY, locZ, this.getPrimaryLayer());
            return true;
        } else if (RangeTypeConstants.getMaterialForRangeType(
                rangeType) == MaterialConstants.MATERIAL_FIRE
                && this.getMaterial() == MaterialConstants.MATERIAL_METALLIC
                && this.changesToOnExposure(
                        MaterialConstants.MATERIAL_FIRE) != null) {
            // Melt metal object
            SoundManager.playSound(SoundConstants.SOUND_MELT);
            LTRemix.getApplication().getGameManager().morph(
                    this.changesToOnExposure(MaterialConstants.MATERIAL_FIRE),
                    locX + dirX, locY + dirY, locZ, this.getPrimaryLayer());
            return true;
        }
        return false;
    }

    /**
     *
     * @param locX
     * @param locY
     * @param locZ
     * @param dirX
     * @param dirY
     * @param laserType
     * @param forceUnits
     * @return
     */
    public int laserEnteredAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int laserType,
            final int forceUnits) {
        if (this.isSolid()) {
            if (forceUnits > this.getMinimumReactionForce() && this.canMove()) {
                // Object crushed by impact
                this.pushCrushAction(locX, locY, locZ);
            } else {
                final AbstractArenaObject adj = LTRemix.getApplication()
                        .getArenaManager().getArena().getCell(locX - dirX,
                                locY - dirY, locZ, this.getPrimaryLayer());
                if (adj != null
                        && !adj.rangeAction(locX - 2 * dirX, locY - 2 * dirY,
                                locZ, dirX, dirY, LaserTypeConstants
                                        .getRangeTypeForLaserType(laserType),
                                1)) {
                    SoundManager.playSound(SoundConstants.SOUND_LASER_DIE);
                }
            }
            return DirectionConstants.NONE;
        } else {
            return DirectionResolver.resolveRelativeDirection(dirX, dirY);
        }
    }

    /**
     *
     * @param locX
     * @param locY
     * @param locZ
     * @param dirX
     * @param dirY
     * @param laserType
     * @return
     */
    public int laserExitedAction(final int locX, final int locY, final int locZ,
            final int dirX, final int dirY, final int laserType) {
        return DirectionResolver.resolveRelativeDirection(dirX, dirY);
    }

    public void laserDoneAction() {
        // Do nothing
    }

    public boolean defersSetProperties() {
        return false;
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public final void determineCurrentAppearance(final int x, final int y,
            final int z) {
        // Do nothing
    }

    public final String getImageName() {
        return this.getColorPrefix() + this.getBaseImageName()
                + this.getDirectionSuffix() + this.getFrameSuffix();
    }

    public final String getBaseImageName() {
        return StringLoader.loadImageString(StringConstants.OBJECT_STRINGS_FILE,
                this.getStringBaseID() * 3 + 0);
    }

    private final String getColorPrefix() {
        if (this.hasColor()) {
            return ColorResolver.resolveColorConstantToImageName(this.color)
                    + StringConstants.COMMON_STRING_SPACE;
        } else {
            return StringConstants.COMMON_STRING_EMPTY;
        }
    }

    private final String getLocalColorPrefix() {
        if (this.hasColor()) {
            return ColorResolver.resolveColorConstantToName(this.color)
                    + StringConstants.COMMON_STRING_SPACE;
        } else {
            return StringConstants.COMMON_STRING_EMPTY;
        }
    }

    private final String getDirectionSuffix() {
        if (this.hasDirection()) {
            return StringConstants.COMMON_STRING_SPACE + DirectionResolver
                    .resolveDirectionConstantToImageName(this.direction);
        } else {
            return StringConstants.COMMON_STRING_EMPTY;
        }
    }

    private final String getFrameSuffix() {
        if (this.isAnimated()) {
            return StringConstants.COMMON_STRING_SPACE + this.frameNumber;
        } else {
            return StringConstants.COMMON_STRING_EMPTY;
        }
    }

    public static final int getImbuedRangeForce(final int material) {
        if (material == MaterialConstants.MATERIAL_PLASTIC) {
            return AbstractArenaObject.PLASTIC_MINIMUM_REACTION_FORCE;
        } else if (material == MaterialConstants.MATERIAL_METALLIC) {
            return AbstractArenaObject.METAL_MINIMUM_REACTION_FORCE;
        } else {
            return AbstractArenaObject.DEFAULT_MINIMUM_REACTION_FORCE;
        }
    }

    public final int getMinimumReactionForce() {
        if (this.material == MaterialConstants.MATERIAL_PLASTIC) {
            return AbstractArenaObject.PLASTIC_MINIMUM_REACTION_FORCE;
        } else if (this.material == MaterialConstants.MATERIAL_METALLIC) {
            return AbstractArenaObject.METAL_MINIMUM_REACTION_FORCE;
        } else {
            return AbstractArenaObject.DEFAULT_MINIMUM_REACTION_FORCE;
        }
    }

    public boolean canMove() {
        return false;
    }

    public boolean canShoot() {
        return false;
    }

    public boolean killsOnMove() {
        return false;
    }

    public boolean solvesOnMove() {
        return false;
    }

    public boolean doLasersPassThrough() {
        return true;
    }

    public boolean isDirectional() {
        return false;
    }

    private final String getIdentifier() {
        return this.getBaseImageName();
    }

    abstract public int getStringBaseID();

    public int getBlockHeight() {
        return 1;
    }

    public final String getBaseName() {
        return StringLoader.loadString(StringConstants.OBJECT_STRINGS_FILE,
                this.getStringBaseID() * 3 + 0);
    }

    public final String getIdentityName() {
        return this.getLocalColorPrefix()
                + StringLoader.loadString(StringConstants.OBJECT_STRINGS_FILE,
                        this.getStringBaseID() * 3 + 0);
    }

    public final String getDescription() {
        return StringLoader.loadString(StringConstants.OBJECT_STRINGS_FILE,
                this.getStringBaseID() * 3 + 2);
    }

    abstract public int getPrimaryLayer();

    public int[] getSecondaryLayers() {
        return new int[0];
    }

    public final boolean hasSecondaryLayers() {
        final int[] secondary = this.getSecondaryLayers();
        return secondary != null && secondary.length != 0;
    }

    public final int[] getAllLayers() {
        final int primary = this.getPrimaryLayer();
        final int[] secondary = this.getSecondaryLayers();
        if (this.hasSecondaryLayers()) {
            final int[] all = new int[secondary.length + 1];
            all[0] = primary;
            for (int x = 1; x < all.length; x++) {
                all[x] = secondary[x - 1];
            }
            return all;
        } else {
            return new int[] { primary };
        }
    }

    public final boolean isOnLayer(final int layer) {
        final int[] all = this.getAllLayers();
        for (final int element : all) {
            if (layer == element) {
                return true;
            }
        }
        return false;
    }

    abstract public int getCustomProperty(int propID);

    abstract public void setCustomProperty(int propID, int value);

    public int getCustomFormat() {
        return 0;
    }

    public String getCustomText() {
        return null;
    }

    public Color getCustomTextColor() {
        return null;
    }

    public final void writeArenaObject(final XDataWriter writer)
            throws IOException {
        writer.writeString(this.getIdentifier());
        final int cc = this.getCustomFormat();
        if (cc == AbstractArenaObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
            writer.writeInt(this.direction);
            writer.writeInt(this.color);
            this.writeArenaObjectHook(writer);
        } else {
            writer.writeInt(this.direction);
            writer.writeInt(this.color);
            for (int x = 0; x < cc; x++) {
                final int cx = this.getCustomProperty(x + 1);
                writer.writeInt(cx);
            }
        }
    }

    public final AbstractArenaObject readArenaObjectG2(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == AbstractArenaObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                this.direction = reader.readInt();
                this.color = reader.readInt();
                return this.readArenaObjectHookG2(reader, ver);
            } else {
                this.direction = reader.readInt();
                this.color = reader.readInt();
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

    public final AbstractArenaObject readArenaObjectG3(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == AbstractArenaObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                this.direction = reader.readInt();
                this.color = reader.readInt();
                // Discard material
                reader.readInt();
                return this.readArenaObjectHookG3(reader, ver);
            } else {
                this.direction = reader.readInt();
                this.color = reader.readInt();
                // Discard material
                reader.readInt();
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

    public final AbstractArenaObject readArenaObjectG4(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == AbstractArenaObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                this.direction = reader.readInt();
                this.color = reader.readInt();
                return this.readArenaObjectHookG4(reader, ver);
            } else {
                this.direction = reader.readInt();
                this.color = reader.readInt();
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

    public final AbstractArenaObject readArenaObjectG5(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == AbstractArenaObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                this.direction = reader.readInt();
                this.color = reader.readInt();
                return this.readArenaObjectHookG5(reader, ver);
            } else {
                this.direction = reader.readInt();
                this.color = reader.readInt();
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

    public final AbstractArenaObject readArenaObjectG6(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == AbstractArenaObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                this.direction = reader.readInt();
                this.color = reader.readInt();
                return this.readArenaObjectHookG6(reader, ver);
            } else {
                this.direction = reader.readInt();
                this.color = reader.readInt();
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

    public final AbstractArenaObject readArenaObjectG7(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == AbstractArenaObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                this.direction = reader.readInt();
                this.color = reader.readInt();
                return this.readArenaObjectHookG7(reader, ver);
            } else {
                this.direction = reader.readInt();
                this.color = reader.readInt();
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
    protected void writeArenaObjectHook(final XDataWriter writer)
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
    protected AbstractArenaObject readArenaObjectHookG2(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }

    /**
     *
     * @param reader
     * @param formatVersion
     * @return
     * @throws IOException
     */
    protected AbstractArenaObject readArenaObjectHookG3(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }

    /**
     *
     * @param reader
     * @param formatVersion
     * @return
     * @throws IOException
     */
    protected AbstractArenaObject readArenaObjectHookG4(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }

    /**
     *
     * @param reader
     * @param formatVersion
     * @return
     * @throws IOException
     */
    protected AbstractArenaObject readArenaObjectHookG5(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }

    /**
     *
     * @param reader
     * @param formatVersion
     * @return
     * @throws IOException
     */
    protected AbstractArenaObject readArenaObjectHookG6(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }

    /**
     *
     * @param reader
     * @param formatVersion
     * @return
     * @throws IOException
     */
    protected AbstractArenaObject readArenaObjectHookG7(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }
}
