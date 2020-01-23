/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericTransientObject extends MazeObject {
    // Fields
    private String name;
    private final String baseName;

    // Constructors
    protected GenericTransientObject(final String newBaseName) {
	super(true);
	this.baseName = newBaseName;
	this.name = newBaseName;
    }

    // Methods
    @Override
    public final String getName() {
	return this.name;
    }

    @Override
    public String getPluralName() {
	return this.name + "s";
    }

    @Override
    public String getDescription() {
	return null;
    }

    public final void setNameSuffix(final String suffix) {
	this.name = this.baseName + " " + suffix;
    }

    @Override
    public int getLayer() {
	return Maze.LAYER_OBJECT;
    }

    @Override
    protected final void setTypes() {
	// Do nothing
    }

    @Override
    public final byte getGroupID() {
	return (byte) 0;
    }

    @Override
    public final byte getObjectID() {
	return (byte) 0;
    }

    @Override
    public int getCustomProperty(final int propID) {
	return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	// Do nothing
    }
}