/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBarrier;

public class VerticalBarrier extends GenericBarrier {
    // Constructors
    public VerticalBarrier() {
        super();
    }

    @Override
    public String getName() {
        return "Vertical Barrier";
    }

    @Override
    public String getPluralName() {
        return "Vertical Barriers";
    }

    @Override
    public byte getObjectID() {
        return (byte) 2;
    }

    @Override
    public String getDescription() {
        return "Vertical Barriers are impassable - you'll need to go around them.";
    }
}