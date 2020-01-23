/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class MagentaKey extends GenericSingleKey {
    // Constructors
    public MagentaKey() {
	super();
    }

    // Scriptability
    @Override
    public String getName() {
	return "Magenta Key";
    }

    @Override
    public String getPluralName() {
	return "Magenta Keys";
    }

    @Override
    public byte getObjectID() {
	return (byte) 5;
    }

    @Override
    public String getDescription() {
	return "Magenta Keys will unlock Magenta Locks, and can only be used once.";
    }
}