/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.generic;

import java.awt.Color;
import java.io.IOException;
import java.util.BitSet;

import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.map.Map;
import com.puttysoftware.gemma.support.map.MapConstants;
import com.puttysoftware.gemma.support.map.objects.BattleCharacter;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.resourcemanagers.ImageManager;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntryArgument;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.storage.CloneableObject;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class MapObject extends CloneableObject
        implements TypeConstants, RandomGenerationRule {
    // Properties
    private boolean solid;
    private boolean blocksLOS;
    protected BitSet type;
    private MapObject saved;
    private TemplateTransform tt;
    public static final int DEFAULT_CUSTOM_VALUE = 0;
    protected static final int CUSTOM_FORMAT_MANUAL_OVERRIDE = -1;

    // Constructors
    MapObject(final boolean isSolid) {
        this.solid = isSolid;
        this.blocksLOS = false;
        this.tt = new TemplateTransform(Color.white);
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.setTypes();
    }

    public MapObject() {
        this.solid = false;
        this.blocksLOS = false;
        this.tt = new TemplateTransform(Color.white);
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.setTypes();
    }

    // Methods
    @Override
    public MapObject clone() {
        try {
            final MapObject copy = (MapObject) super.clone();
            copy.solid = this.solid;
            copy.type = (BitSet) this.type.clone();
            copy.blocksLOS = this.blocksLOS;
            return copy;
        } catch (final CloneNotSupportedException e) {
            Support.getErrorLogger().logError(e);
            return null;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.blocksLOS ? 1231 : 1237);
        result = prime * result
                + (this.saved == null ? 0 : this.saved.hashCode());
        result = prime * result + (this.solid ? 1231 : 1237);
        result = prime * result + (this.tt == null ? 0 : this.tt.hashCode());
        return prime * result + (this.type == null ? 0 : this.type.hashCode());
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
        if (this.blocksLOS != other.blocksLOS) {
            return false;
        }
        if (this.saved == null) {
            if (other.saved != null) {
                return false;
            }
        } else if (!this.saved.equals(other.saved)) {
            return false;
        }
        if (this.solid != other.solid) {
            return false;
        }
        if (this.tt == null) {
            if (other.tt != null) {
                return false;
            }
        } else if (!this.tt.equals(other.tt)) {
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

    public final MapObject getSavedObject() {
        return this.saved;
    }

    public final void setSavedObject(final MapObject newSaved) {
        this.saved = newSaved;
    }

    final TemplateTransform getTemplateTransform() {
        return this.tt;
    }

    public TemplateTransform getGameTemplateTransform() {
        return this.tt;
    }

    protected final void setTemplateTransform(final TemplateTransform newTT) {
        this.tt = newTT;
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

    public final boolean isSolid() {
        return this.solid;
    }

    public final boolean isSightBlocking() {
        return this.blocksLOS;
    }

    protected final void setSightBlocking(final boolean sb) {
        this.blocksLOS = sb;
    }

    public final boolean isSolidInBattle() {
        // Handle disabled objects
        if (this.enabledInBattle()) {
            return this.solid;
        } else {
            return false;
        }
    }

    public final boolean isOfType(final int testType) {
        return this.type.get(testType);
    }

    protected abstract void setTypes();

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

    public final boolean arrowHitCheck() {
        return !this.isSolid();
    }

    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param dirZ
     * @return
     */
    public InternalScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
        final InternalScript scpt = new InternalScript();
        final InternalScriptEntry act0 = new InternalScriptEntry();
        act0.setActionCode(InternalScriptActionCode.SOUND);
        act0.addActionArg(
                new InternalScriptEntryArgument(GameSoundConstants.SOUND_STEP));
        act0.finalizeActionArgs();
        scpt.addAction(act0);
        scpt.finalizeActions();
        return scpt;
    }

    /**
     *
     * @param invoker
     * @return
     */
    public InternalScript getBattlePostMoveScript(
            final BattleCharacter invoker) {
        return null;
    }

    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param dirZ
     * @return
     */
    public static InternalScript getMoveFailedScript(final boolean ie,
            final int dirX, final int dirY, final int dirZ) {
        final InternalScript scpt = new InternalScript();
        final InternalScriptEntry act0 = new InternalScriptEntry();
        act0.setActionCode(InternalScriptActionCode.SOUND);
        act0.addActionArg(
                new InternalScriptEntryArgument(GameSoundConstants.SOUND_OOF));
        act0.finalizeActionArgs();
        scpt.addAction(act0);
        final InternalScriptEntry act1 = new InternalScriptEntry();
        act1.setActionCode(InternalScriptActionCode.MESSAGE);
        act1.addActionArg(new InternalScriptEntryArgument("Can't go that way"));
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
        return this.getGameCacheName();
    }

    public BufferedImageIcon battleRenderHook() {
        return ImageManager.getMapImage(this.getGameCacheName(),
                this.getBattleName(), this.tt);
    }

    public boolean defersSetProperties() {
        return false;
    }

    public boolean overridesDefaultPostMove() {
        return false;
    }

    public static int getBattleMoveSoundID() {
        return GameSoundConstants.SOUND_STEP;
    }

    private String getBattleName() {
        if (this.enabledInBattle()) {
            return this.getBattleNameHook();
        } else {
            return "Empty";
        }
    }

    private String getBattleNameHook() {
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

    public boolean hideFromHelp() {
        return false;
    }

    abstract public String getName();

    private String getGameImageName() {
        return this.getGameImageNameHook();
    }

    private String getGameCacheName() {
        return this.getGameImageName();
    }

    final String getEditorImageName() {
        return this.getEditorImageNameHook();
    }

    protected String getGameImageNameHook() {
        return this.getGameName();
    }

    protected String getEditorImageNameHook() {
        return this.getName();
    }

    private String getIdentifier() {
        return this.getName();
    }

    public boolean enabledInBattle() {
        return true;
    }

    public static int getBattleAPCost() {
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

    @Override
    public boolean shouldGenerateObjectInBattle(final Map map, final int row,
            final int col, final int floor, final int level, final int layer) {
        if (!this.enabledInBattle()) {
            // Don't generate disabled objects
            return false;
        } else {
            // Generate other objects at 100%
            return true;
        }
    }

    @Override
    public int getMinimumRequiredQuantityInBattle(final Map map) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public int getMaximumRequiredQuantityInBattle(final Map map) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public boolean isRequiredInBattle() {
        return false;
    }

    public final void writeMapObject(final XDataWriter writer)
            throws IOException {
        writer.writeString(this.getIdentifier());
        final int cc = this.getCustomFormat();
        if (cc == MapObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
            this.writeMapObjectHook(writer);
        } else {
            for (int x = 0; x < cc; x++) {
                final int cx = this.getCustomProperty(x + 1);
                writer.writeInt(cx);
            }
        }
    }

    final MapObject readMapObject(final XDataReader reader, final String ident,
            final int ver) throws IOException {
        if (ident.equals(this.getIdentifier())) {
            final int cc = this.getCustomFormat();
            if (cc == MapObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                return this.readMapObjectHook(reader, ver);
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
    protected void writeMapObjectHook(final XDataWriter writer)
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
    protected MapObject readMapObjectHook(final XDataReader reader,
            final int formatVersion) throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }
}
