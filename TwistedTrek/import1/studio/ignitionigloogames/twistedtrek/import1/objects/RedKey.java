/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class RedKey extends GenericSingleKey {
    // Constructors
    public RedKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Key";
    }

    @Override
    public String getPluralName() {
        return "Red Keys";
    }

    @Override
    public byte getObjectID() {
        return (byte) 8;
    }

    @Override
    public String getDescription() {
        return "Red Keys will unlock Red Locks, and can only be used once.";
    }
}