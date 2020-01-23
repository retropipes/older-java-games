/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericMovableObject;

public class PushableBlock extends GenericMovableObject {
    // Constructors
    public PushableBlock() {
	super(true, false);
    }

    @Override
    public String getName() {
	return "Pushable Block";
    }

    @Override
    public String getPluralName() {
	return "Pushable Blocks";
    }

    @Override
    public byte getObjectID() {
	return (byte) 2;
    }

    @Override
    public String getDescription() {
	return "Pushable Blocks can only be pushed, not pulled.";
    }
}