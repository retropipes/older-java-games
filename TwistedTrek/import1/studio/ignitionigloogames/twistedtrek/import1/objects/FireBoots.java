/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBoots;

public class FireBoots extends GenericBoots {
    // Constructors
    public FireBoots() {
	super();
    }

    @Override
    public String getName() {
	return "Fire Boots";
    }

    @Override
    public String getPluralName() {
	return "Pairs of Fire Boots";
    }

    @Override
    public byte getObjectID() {
	return (byte) 3;
    }

    @Override
    public String getDescription() {
	return "Fire Boots allow walking on lava. Note that you can only wear one pair of boots at once.";
    }
}
