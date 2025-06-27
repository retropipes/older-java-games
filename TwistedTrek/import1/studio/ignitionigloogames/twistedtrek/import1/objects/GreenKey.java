/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class GreenKey extends GenericSingleKey {
    // Constructors
    public GreenKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Green Key";
    }

    @Override
    public String getPluralName() {
        return "Green Keys";
    }

    @Override
    public byte getObjectID() {
        return (byte) 4;
    }

    @Override
    public String getDescription() {
        return "Green Keys will unlock Green Locks, and can only be used once.";
    }
}