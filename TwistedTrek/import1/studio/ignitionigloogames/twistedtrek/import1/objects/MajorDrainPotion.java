/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class MajorDrainPotion extends GenericPotion {
    // Fields
    private static final int MIN_DRAIN = -6;
    private static final int MAX_DRAIN = -50;

    // Constructors
    public MajorDrainPotion() {
        super(StatConstants.STAT_CURRENT_MP, true, MajorDrainPotion.MAX_DRAIN, MajorDrainPotion.MIN_DRAIN);
    }

    @Override
    public String getName() {
        return "Major Drain Potion";
    }

    @Override
    public String getPluralName() {
        return "Major Drain Potions";
    }

    @Override
    public byte getObjectID() {
        return (byte) 13;
    }

    @Override
    public String getDescription() {
        return "Major Drain Potions drain your magic significantly when picked up.";
    }
}