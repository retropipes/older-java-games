/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBoots;

public class RegularBoots extends GenericBoots {
    // Constructors
    public RegularBoots() {
	super();
    }

    @Override
    public String getName() {
	return "Regular Boots";
    }

    @Override
    public String getPluralName() {
	return "Pairs of Regular Boots";
    }

    @Override
    public byte getObjectID() {
	return (byte) 0;
    }

    @Override
    public String getDescription() {
	return "";
    }
}
