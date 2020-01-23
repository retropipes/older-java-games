/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class MinorDrainPotion extends GenericPotion {
    // Fields
    private static final int MIN_DRAIN = -1;
    private static final int MAX_DRAIN = -5;

    // Constructors
    public MinorDrainPotion() {
	super(StatConstants.STAT_CURRENT_MP, true, MinorDrainPotion.MAX_DRAIN, MinorDrainPotion.MIN_DRAIN);
    }

    @Override
    public String getName() {
	return "Minor Drain Potion";
    }

    @Override
    public String getPluralName() {
	return "Minor Drain Potions";
    }

    @Override
    public byte getObjectID() {
	return (byte) 11;
    }

    @Override
    public String getDescription() {
	return "Minor Drain Potions drain your magic slightly when picked up.";
    }
}