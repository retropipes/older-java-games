/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class YellowKey extends GenericSingleKey {
    // Constructors
    public YellowKey() {
	super();
    }

    // Scriptability
    @Override
    public String getName() {
	return "Yellow Key";
    }

    @Override
    public String getPluralName() {
	return "Yellow Keys";
    }

    @Override
    public byte getObjectID() {
	return (byte) 10;
    }

    @Override
    public String getDescription() {
	return "Yellow Keys will unlock Yellow Locks, and can only be used once.";
    }
}