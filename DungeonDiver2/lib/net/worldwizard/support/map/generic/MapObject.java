/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.generic;

import java.io.IOException;
import java.util.BitSet;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.map.Map;
import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.resourcemanagers.MapObjectImageManager;
import net.worldwizard.support.scripts.game.GameActionCode;
import net.worldwizard.support.scripts.game.GameScript;
import net.worldwizard.support.scripts.game.GameScriptEntry;
import net.worldwizard.support.scripts.game.GameScriptEntryArgument;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public abstract class MapObject implements TypeConstants, RandomGenerationRule {
    // Properties
    private boolean solid;
    private boolean friction;
    protected BitSet type;
    private GameScript custom;
    private MapObject saved;
    private TemplateTransform tt;
    public static final int DEFAULT_CUSTOM_VALUE = 0;
    protected static final int CUSTOM_FORMAT_MANUAL_OVERRIDE = -1;

    // Constructors
    public MapObject(final boolean isSolid) {
        this.solid = isSolid;
        this.friction = true;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.setTypes();
    }

    public MapObject(final boolean isSolid, final boolean hasFriction) {
        this.solid = isSolid;
        this.friction = hasFriction;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.setTypes();
    }

    public MapObject() {
        this.solid = false;
        this.friction = true;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.setTypes();
    }

    // Methods
    @Override
    public MapObject clone() {
        try {
            final MapObject copy = this.getClass().newInstance();
            copy.solid = this.solid;
            copy.friction = this.friction;
            copy.type = (BitSet) this.type.clone();
            return copy;
        } catch (final InstantiationException e) {
            throw new AssertionError("Should not ever get here!");
        } catch (final IllegalAccessException e) {
            throw new AssertionError("Should not ever get here!");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.friction ? 1231 : 1237);
        result = prime * result + (this.solid ? 1231 : 1237);
        result = prime * result
                + (this.type == null ? 0 : this.type.hashCode());
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
        if (!(obj instanceof MapObject)) {
            return false;
        }
        final MapObject other = (MapObject) obj;
        if (this.friction != other.friction) {
            return false;
        }
        if (this.solid != other.solid) {
            return false;
        }
        if (this.type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!this.type.equals(other.type)) {
            return false;
        }
        return true;
    }

    public MapObject getSavedObject() {
        return this.saved;
    }

    public void setSavedObject(final MapObject newSaved) {
        this.saved = newSaved;
    }

    public TemplateTransform getTemplateTransform() {
        return this.tt;
    }

    protected void setTemplateTransform(final TemplateTransform newTT) {
        this.tt = newTT;
    }

    public GameScript getCustomScript() {
        return this.custom;
    }

    public boolean hasCustomScript() {
        return this.custom != null;
    }

    public void setCustomScript(final GameScript scpt) {
        this.custom = scpt;
    }

    /**
     *
     * @param map
     * @param z
     * @return
     */
    public boolean isConditionallySolid(final Map map, final int z) {
        return this.solid;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public final boolean isSolidInBattle() {
        // Handle disabled objects
        if (this.enabledInBattle()) {
            return this.solid;
        } else {
            return false;
        }
    }

    public boolean isOfType(final int testType) {
        return this.type.get(testType);
    }

    protected abstract void setTypes();

    public boolean hasFriction() {
        return this.friction;
    }

    /**
     *
     * @param moving
     * @return
     */
    public boolean hasFrictionConditionally(final boolean moving) {
        return this.hasFriction();
    }

    // Scripting
    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param dirZ
     * @param map
     * @return
     */
    public boolean preMoveCheck(final boolean ie, final int dirX,
            final int dirY, final int dirZ, final Map map) {
        return true;
    }

    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param dirZ
     * @param map
     * @return
     */
    public GameScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ, final Map map) {
        final GameScript scpt = new GameScript();
        final GameScriptEntry act0 = new GameScriptEntry();
        act0.setActionCode(GameActionCode.SOUND);
        act0.addActionArg(
                new GameScriptEntryArgument(GameSoundConstants.SOUND_WALK));
        act0.finalizeActionArgs();
        scpt.addAction(act0);
        scpt.finalizeActions();
        return scpt;
    }

    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param dirZ
     * @return
     */
    public GameScript getMoveFailedScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
        final GameScript scpt = new GameScript();
        final GameScriptEntry act0 = new GameScriptEntry();
        act0.setActionCode(GameActionCode.SOUND);
        act0.addActionArg(
                new GameScriptEntryArgument(GameSoundConstants.SOUND_OOF));
        act0.finalizeActionArgs();
        scpt.addAction(act0);
        final GameScriptEntry act1 = new GameScriptEntry();
        act1.setActionCode(GameActionCode.MESSAGE);
        act1.addActionArg(new GameScriptEntryArgument("Can't go that way"));
        act1.finalizeActionArgs();
        scpt.addAction(act1);
        scpt.finalizeActions();
        return scpt;
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @param map
     * @return
     */
    public String gameRenderHook(final int x, final int y, final int z,
            final Map map) {
        return this.getGameImageName();
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public BufferedImageIcon battleRenderHook(final int x, final int y) {
        return MapObjectImageManager.getImage(this.getGameImageName(),
                this.getBattleName(), this.tt);
    }

    public boolean defersSetProperties() {
        return false;
    }

    public boolean overridesDefaultPostMove() {
        return false;
    }

    public int getBattleMoveSoundID() {
        return GameSoundConstants.SOUND_WALK;
    }

    public final String getBattleName() {
        if (this.enabledInBattle()) {
            return this.getBattleNameHook();
        } else {
            if (this.getLayer() == MapConstants.LAYER_GROUND) {
                return "Tile";
            } else {
                return "Empty";
            }
        }
    }

    protected String getBattleNameHook() {
        return this.getName();
    }

    public String getGameName() {
        return this.getName();
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     * @param map
     */
    public void determineCurrentAppearance(final int x, final int y,
            final int z, final Map map) {
        // Do nothing
    }

    abstract public String getName();

    public final String getGameImageName() {
        String name;
        if (this.tt == null) {
            name = this.getGameImageNameHook();
        } else {
            final String ttName = this.tt.getName();
            if (ttName == null || ttName.isEmpty()) {
                name = this.getGameImageNameHook();
            } else {
                name = this.tt.getName() + " " + this.getGameImageNameHook();
            }
        }
        return name;
    }

    protected String getGameImageNameHook() {
        return this.getGameName();
    }

    public final String getXIdentifier() {
        return this.getName();
    }

    public boolean enabledInBattle() {
        return true;
    }

    public int getBattleAPCost() {
        return 1;
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
    public boolean shouldGenerateObject(final Map map, final int row,
            final int col, final int floor, final int level, final int layer) {
        if (layer == MapConstants.LAYER_OBJECT) {
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
    public int getMinimumRequiredQuantity(final Map map) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public int getMaximumRequiredQuantity(final Map map) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    public final void writeMapObjectX(final XDataWriter writer)
            throws IOException {
        writer.writeString(this.getXIdentifier());
        final boolean hasCustom = this.hasCustomScript();
        writer.writeBoolean(hasCustom);
        if (hasCustom) {
            this.custom.write(writer);
        }
        final int cc = this.getCustomFormat();
        if (cc == MapObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
            this.writeMapObjectHookX(writer);
        } else {
            for (int x = 0; x < cc; x++) {
                final int cx = this.getCustomProperty(x + 1);
                writer.writeInt(cx);
            }
        }
    }

    public final MapObject readMapObjectX(final XDataReader reader,
            final String ident, final int ver) throws IOException {
        if (ident.equals(this.getXIdentifier())) {
            final boolean hasCustom = reader.readBoolean();
            if (hasCustom) {
                this.custom = GameScript.read(reader);
            }
            final int cc = this.getCustomFormat();
            if (cc == MapObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                return this.readMapObjectHookX(reader, ver);
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
    protected void writeMapObjectHookX(final XDataWriter writer)
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
    protected MapObject readMapObjectHookX(final XDataReader reader,
            final int formatVersion) throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }
}
