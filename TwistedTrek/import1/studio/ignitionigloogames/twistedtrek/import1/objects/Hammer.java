/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class Hammer extends GenericSingleKey {
    // Constructors
    public Hammer() {
        super();
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public String getPluralName() {
        return "Hammers";
    }

    @Override
    public byte getObjectID() {
        return (byte) 2;
    }

    @Override
    public String getDescription() {
        return "Hammers are used to destroy Brick Walls, and can only be used once.";
    }
}