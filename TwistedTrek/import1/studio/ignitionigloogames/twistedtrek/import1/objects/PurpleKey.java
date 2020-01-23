/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class PurpleKey extends GenericSingleKey {
    // Constructors
    public PurpleKey() {
	super();
    }

    // Scriptability
    @Override
    public String getName() {
	return "Purple Key";
    }

    @Override
    public String getPluralName() {
	return "Purple Keys";
    }

    @Override
    public byte getObjectID() {
	return (byte) 7;
    }

    @Override
    public String getDescription() {
	return "Purple Keys will unlock Purple Locks, and can only be used once.";
    }
}