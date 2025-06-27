/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGround;

public class Sand extends GenericGround {
    // Constructors
    public Sand() {
        super();
    }

    @Override
    public String getName() {
        return "Sand";
    }

    @Override
    public String getPluralName() {
        return "Squares of Sand";
    }

    @Override
    public byte getObjectID() {
        return (byte) 7;
    }

    @Override
    public String getDescription() {
        return "Sand is one of the many types of ground.";
    }
}