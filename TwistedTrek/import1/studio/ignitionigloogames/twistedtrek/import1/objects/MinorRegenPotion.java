/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class MinorRegenPotion extends GenericPotion {
    // Fields
    private static final int MIN_REGEN = 1;
    private static final int MAX_REGEN = 5;

    // Constructors
    public MinorRegenPotion() {
	super(StatConstants.STAT_CURRENT_MP, true, MinorRegenPotion.MIN_REGEN, MinorRegenPotion.MAX_REGEN);
    }

    @Override
    public String getName() {
	return "Minor Regen Potion";
    }

    @Override
    public String getPluralName() {
	return "Minor Regen Potions";
    }

    @Override
    public byte getObjectID() {
	return (byte) 10;
    }

    @Override
    public String getDescription() {
	return "Minor Regen Potions regenerate your magic slightly when picked up.";
    }
}