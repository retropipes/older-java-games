/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericMovableObject;

public class PullableBlock extends GenericMovableObject {
    // Constructors
    public PullableBlock() {
        super(false, true);
    }

    @Override
    public String getName() {
        return "Pullable Block";
    }

    @Override
    public String getPluralName() {
        return "Pullable Blocks";
    }

    @Override
    public byte getObjectID() {
        return (byte) 1;
    }

    @Override
    public String getDescription() {
        return "Pullable Blocks can only be pulled, not pushed.";
    }
}