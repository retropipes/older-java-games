/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.abc;

import java.io.IOException;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.MazeConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Empty;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.TypeConstants;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public abstract class AbstractCharacter extends AbstractMazeObject {
    // Fields
    private AbstractMazeObject savedObject;

    // Constructors
    protected AbstractCharacter() {
	super(false, false);
	this.savedObject = new Empty();
    }

    // Methods
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	// Do nothing
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return MazeConstants.VIRTUAL_LAYER_CHARACTER;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_CHARACTER);
    }

    @Override
    public int getCustomFormat() {
	return AbstractMazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    public int getCustomProperty(final int propID) {
	return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	// Do nothing
    }

    @Override
    protected void writeMazeObjectHook(final XDataWriter writer) throws IOException {
	this.savedObject.writeMazeObject(writer);
    }

    @Override
    protected AbstractMazeObject readMazeObjectHook(final XDataReader reader, final int formatVersion)
	    throws IOException {
	this.savedObject = Import2.getApplication().getObjects().readMazeObject(reader, formatVersion);
	return this;
    }
}
