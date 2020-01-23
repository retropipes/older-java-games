/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class WhiteKey extends GenericSingleKey {
    // Constructors
    public WhiteKey() {
	super();
    }

    // Scriptability
    @Override
    public String getName() {
	return "White Key";
    }

    @Override
    public String getPluralName() {
	return "White Keys";
    }

    @Override
    public byte getObjectID() {
	return (byte) 9;
    }

    @Override
    public String getDescription() {
	return "White Keys will unlock White Locks, and can only be used once.";
    }
}