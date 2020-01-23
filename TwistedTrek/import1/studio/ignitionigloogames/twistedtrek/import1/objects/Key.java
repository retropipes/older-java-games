/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class Key extends GenericSingleKey {
    // Constructors
    public Key() {
	super();
    }

    // Scriptability
    @Override
    public String getName() {
	return "Key";
    }

    @Override
    public String getPluralName() {
	return "Keys";
    }

    @Override
    public byte getObjectID() {
	return (byte) 1;
    }

    @Override
    public String getDescription() {
	return "Keys unlock Locks, and can only be used once.";
    }
}