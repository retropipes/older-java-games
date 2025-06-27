/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class CyanKey extends GenericSingleKey {
    // Constructors
    public CyanKey() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Key";
    }

    @Override
    public String getPluralName() {
        return "Cyan Keys";
    }

    @Override
    public byte getObjectID() {
        return (byte) 11;
    }

    @Override
    public String getDescription() {
        return "Cyan Keys will unlock Cyan Locks, and can only be used once.";
    }
}