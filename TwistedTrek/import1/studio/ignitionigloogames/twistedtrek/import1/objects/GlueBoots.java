/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBoots;

public class GlueBoots extends GenericBoots {
    // Constructors
    public GlueBoots() {
	super();
    }

    @Override
    public String getName() {
	return "Glue Boots";
    }

    @Override
    public String getPluralName() {
	return "Pairs of Glue Boots";
    }

    @Override
    public byte getObjectID() {
	return (byte) 6;
    }

    @Override
    public String getDescription() {
	return "Glue Boots allow walking on Ice without slipping. Note that you can only wear one pair of boots at once.";
    }
}
