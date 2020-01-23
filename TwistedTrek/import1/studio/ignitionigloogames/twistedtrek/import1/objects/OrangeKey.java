/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class OrangeKey extends GenericSingleKey {
    // Constructors
    public OrangeKey() {
	super();
    }

    // Scriptability
    @Override
    public String getName() {
	return "Orange Key";
    }

    @Override
    public String getPluralName() {
	return "Orange Keys";
    }

    @Override
    public byte getObjectID() {
	return (byte) 6;
    }

    @Override
    public String getDescription() {
	return "Orange Keys will unlock Orange Locks, and can only be used once.";
    }
}