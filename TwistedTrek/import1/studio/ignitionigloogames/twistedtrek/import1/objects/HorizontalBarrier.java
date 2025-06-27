/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBarrier;

public class HorizontalBarrier extends GenericBarrier {
    // Constructors
    public HorizontalBarrier() {
        super();
    }

    @Override
    public String getName() {
        return "Horizontal Barrier";
    }

    @Override
    public String getPluralName() {
        return "Horizontal Barriers";
    }

    @Override
    public byte getObjectID() {
        return (byte) 1;
    }

    @Override
    public String getDescription() {
        return "Horizontal Barriers are impassable - you'll need to go around them.";
    }
}