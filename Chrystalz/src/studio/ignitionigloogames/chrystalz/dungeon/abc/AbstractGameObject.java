/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.abc;

import java.io.IOException;
import java.util.BitSet;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.ImageColorConstants;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.RandomGenerationRule;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.TypeConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.BattleImageManager;
import studio.ignitionigloogames.chrystalz.manager.asset.ObjectImageConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.ObjectImageManager;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundManager;
import studio.ignitionigloogames.chrystalz.manager.dungeon.FormatConstants;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;
import studio.ignitionigloogames.common.images.BufferedImageIcon;
import studio.ignitionigloogames.common.llds.CloneableObject;
import studio.ignitionigloogames.common.random.RandomRange;

public abstract class AbstractGameObject extends CloneableObject
        implements RandomGenerationRule {
    // Properties
    private boolean solid;
    private boolean friction;
    private final boolean blocksLOS;
    private static int templateColor = ImageColorConstants.COLOR_NONE;
    private int timerValue;
    private int initialTimerValue;
    private boolean timerActive;
    protected BitSet type;
    private AbstractGameObject saved;
    public static final int DEFAULT_CUSTOM_VALUE = 0;
    protected static final int CUSTOM_FORMAT_MANUAL_OVERRIDE = -1;

    // Constructors
    public AbstractGameObject(final boolean isSolid, final boolean sightBlock) {
        this.solid = isSolid;
        this.friction = true;
        this.blocksLOS = sightBlock;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.setTypes();
    }

    public AbstractGameObject(final boolean isSolid, final boolean hasFriction,
            final boolean sightBlock) {
        this.solid = isSolid;
        this.friction = hasFriction;
        this.blocksLOS = sightBlock;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.setTypes();
    }

    public AbstractGameObject() {
        this.solid = false;
        this.friction = true;
        this.blocksLOS = false;
        this.type = new BitSet(TypeConstants.TYPES_COUNT);
        this.timerValue = 0;
        this.initialTimerValue = 0;
        this.timerActive = false;
        this.setTypes();
    }

    // Methods
    @Override
    public AbstractGameObject clone() {
        try {
            final AbstractGameObject copy = (AbstractGameObject) super.clone();
            copy.solid = this.solid;
            copy.friction = this.friction;
            copy.type = (BitSet) this.type.clone();
            copy.timerValue = this.timerValue;
            copy.initialTimerValue = this.initialTimerValue;
            copy.timerActive = this.timerActive;
            copy.type = (BitSet) this.type.clone();
            return copy;
        } catch (final CloneNotSupportedException cnse) {
            Chrystalz.getErrorLogger().logError(cnse);
            return null;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.friction ? 1231 : 1237);
        result = prime * result + (this.solid ? 1231 : 1237);
        result = prime * result + this.timerValue;
        result = prime * result + this.initialTimerValue;
        result = prime * result + (this.timerActive ? 1231 : 1237);
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
        if (!(obj instanceof AbstractGameObject)) {
            return false;
        }
        final AbstractGameObject other = (AbstractGameObject) obj;
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
        if (this.timerActive != other.timerActive) {
            return false;
        }
        if (this.timerValue != other.timerValue) {
            return false;
        }
        if (this.initialTimerValue != other.initialTimerValue) {
            return false;
        }
        return true;
    }

    public AbstractGameObject getSavedObject() {
        if (this.saved == null) {
            throw new NullPointerException("Saved object == NULL!");
        }
        return this.saved;
    }

    public void setSavedObject(final AbstractGameObject newSaved) {
        if (newSaved == null) {
            throw new IllegalArgumentException("New saved object == NULL!");
        }
        this.saved = newSaved;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public boolean isSolidInBattle() {
        if (this.enabledInBattle()) {
            return this.isSolid();
        } else {
            return false;
        }
    }

    public boolean isSightBlocking() {
        return this.blocksLOS;
    }

    public boolean isOfType(final int testType) {
        return this.type.get(testType);
    }

    protected abstract void setTypes();

    public boolean hasFriction() {
        return this.friction;
    }

    public static int getTemplateColor() {
        return AbstractGameObject.templateColor;
    }

    public static void setTemplateColor(final int newTC) {
        AbstractGameObject.templateColor = newTC;
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
            final int dirY) {
        return true;
    }

    public abstract void postMoveAction(final boolean ie, final int dirX,
            final int dirY);

    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param inv
     */
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY) {
        SoundManager.playSound(SoundConstants.FAILED);
        Chrystalz.getApplication().showMessage("Can't go that way");
    }

    /**
     *
     * @param ie
     * @param dirX
     * @param dirY
     * @param inv
     */
    public void interactAction() {
        SoundManager.playSound(SoundConstants.FAILED);
        Chrystalz.getApplication().showMessage("Can't interact with that");
    }

    /**
     *
     * @param x
     * @param y
     */
    public void editorGenerateHook(final int x, final int y) {
        // Do nothing
    }

    public boolean arrowHitBattleCheck() {
        return !this.isSolid();
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public BufferedImageIcon gameRenderHook(final int x, final int y) {
        return ObjectImageManager.getImage(this.getName(), this.getBaseID());
    }

    public BufferedImageIcon battleRenderHook() {
        return BattleImageManager.getImage(this.getName(),
                this.getBattleBaseID());
    }

    public boolean defersSetProperties() {
        return false;
    }

    public boolean overridesDefaultPostMove() {
        return false;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void determineCurrentAppearance(final int x, final int y) {
        // Do nothing
    }

    public final void activateTimer(final int ticks) {
        this.timerActive = true;
        this.timerValue = ticks;
        this.initialTimerValue = ticks;
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

    abstract public String getName();

    public abstract int getBaseID();

    public int getBattleBaseID() {
        if (this.enabledInBattle()) {
            return this.getBaseID();
        } else {
            return ObjectImageConstants.NONE;
        }
    }

    public boolean enabledInBattle() {
        return true;
    }

    public final String getIdentifier() {
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
    public boolean shouldGenerateObject(final Dungeon dungeon, final int row,
            final int col, final int level, final int layer) {
        if (layer == DungeonConstants.LAYER_OBJECT) {
            // Handle object layer
            if (!this.isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                // Limit generation of other objects to 20%, unless required
                if (this.isRequired(dungeon)) {
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
    public int getMinimumRequiredQuantity(final Dungeon dungeon) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public int getMaximumRequiredQuantity(final Dungeon dungeon) {
        return RandomGenerationRule.NO_LIMIT;
    }

    @Override
    public boolean isRequired(final Dungeon dungeon) {
        return false;
    }

    public final void writeGameObject(final FileIOWriter writer)
            throws IOException {
        writer.writeString(this.getIdentifier());
        if (this.saved == null) {
            writer.writeString("NULL");
        } else {
            this.saved.writeGameObject(writer);
        }
        final int cc = this.getCustomFormat();
        if (cc == AbstractGameObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
            this.writeGameObjectHook(writer);
        } else {
            for (int x = 0; x < cc; x++) {
                final int cx = this.getCustomProperty(x + 1);
                writer.writeInt(cx);
            }
        }
    }

    public final AbstractGameObject readGameObjectV1(final FileIOReader reader,
            final String ident) throws IOException {
        if (ident.equals(this.getIdentifier())) {
            final String savedIdent = reader.readString();
            if (!savedIdent.equals("NULL")) {
                this.saved = Chrystalz.getApplication().getObjects()
                        .readSavedGameObject(reader, savedIdent,
                                FormatConstants.MAZE_FORMAT_LATEST);
            }
            final int cc = this.getCustomFormat();
            if (cc == AbstractGameObject.CUSTOM_FORMAT_MANUAL_OVERRIDE) {
                return this.readGameObjectHook(reader,
                        FormatConstants.MAZE_FORMAT_LATEST);
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
    protected void writeGameObjectHook(final FileIOWriter writer)
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
    protected AbstractGameObject readGameObjectHook(final FileIOReader reader,
            final int formatVersion) throws IOException {
        // Dummy implementation, subclasses can override
        return this;
    }

    public boolean isMoving() {
        return false;
    }
}
