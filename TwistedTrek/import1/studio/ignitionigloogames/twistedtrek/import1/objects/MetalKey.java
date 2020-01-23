/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericSingleKey;

public class MetalKey extends GenericSingleKey {
    // Constructors
    public MetalKey() {
	super();
    }

    // Scriptability
    @Override
    public String getName() {
	return "Metal Key";
    }

    @Override
    public String getPluralName() {
	return "Metal Keys";
    }

    @Override
    public byte getObjectID() {
	return (byte) 12;
    }

    @Override
    public String getDescription() {
	return "Metal Keys will open Metal Doors, and can only be used once.";
    }
}