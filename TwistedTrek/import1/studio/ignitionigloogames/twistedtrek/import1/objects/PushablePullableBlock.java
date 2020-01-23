/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericMovableObject;

public class PushablePullableBlock extends GenericMovableObject {
    // Constructors
    public PushablePullableBlock() {
	super(true, true);
    }

    @Override
    public String getName() {
	return "Pushable/Pullable Block";
    }

    @Override
    public String getPluralName() {
	return "Pushable/Pullable Blocks";
    }

    @Override
    public byte getObjectID() {
	return (byte) 3;
    }

    @Override
    public String getDescription() {
	return "Pushable/Pullable Blocks can be both pushed and pulled.";
    }
}
