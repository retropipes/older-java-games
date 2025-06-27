/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBoots;

public class MetalBoots extends GenericBoots {
    // Constructors
    public MetalBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Metal Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Metal Boots";
    }

    @Override
    public byte getObjectID() {
        return (byte) 5;
    }

    @Override
    public String getDescription() {
        return "Metal Boots allow Metal Buttons to be triggered. Note that you can only wear one pair of boots at once.";
    }
}
