/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBoots;

public class AquaBoots extends GenericBoots {
    // Constructors
    public AquaBoots() {
	super();
    }

    @Override
    public String getName() {
	return "Aqua Boots";
    }

    @Override
    public String getPluralName() {
	return "Pairs of Aqua Boots";
    }

    @Override
    public byte getObjectID() {
	return (byte) 1;
    }

    @Override
    public String getDescription() {
	return "Aqua Boots allow walking on water. Note that you can only wear one pair of boots at once.";
    }
}
