/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.abc;

import java.io.IOException;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Empty;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.TypeConstants;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public abstract class AbstractCharacter extends AbstractGameObject {
    // Fields
    private AbstractGameObject savedObject;

    // Constructors
    protected AbstractCharacter() {
        super(false, false);
        this.savedObject = new Empty();
    }

    // Methods
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        // Do nothing
    }

    @Override
    public int getLayer() {
        return DungeonConstants.VIRTUAL_LAYER_CHARACTER;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CHARACTER);
    }

    @Override
    public int getCustomFormat() {
        return AbstractGameObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractGameObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    protected void writeGameObjectHook(final FileIOWriter writer)
            throws IOException {
        this.savedObject.writeGameObject(writer);
    }

    @Override
    protected AbstractGameObject readGameObjectHook(final FileIOReader reader,
            final int formatVersion) throws IOException {
        this.savedObject = Chrystalz.getApplication().getObjects()
                .readGameObject(reader, formatVersion);
        return this;
    }
}
