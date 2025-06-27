/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBoots;

public class BioHazardBoots extends GenericBoots {
    // Constructors
    public BioHazardBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Bio-Hazard Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Bio-Hazard Boots";
    }

    @Override
    public byte getObjectID() {
        return (byte) 4;
    }

    @Override
    public String getDescription() {
        return "Bio-Hazard Boots allow walking on slime. Note that you can only wear one pair of boots at once.";
    }
}
