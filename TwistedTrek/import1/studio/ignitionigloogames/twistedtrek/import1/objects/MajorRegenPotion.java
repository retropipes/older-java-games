/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class MajorRegenPotion extends GenericPotion {
    // Fields
    private static final int MIN_REGEN = 6;
    private static final int MAX_REGEN = 50;

    // Constructors
    public MajorRegenPotion() {
	super(StatConstants.STAT_CURRENT_MP, true, MajorRegenPotion.MIN_REGEN, MajorRegenPotion.MAX_REGEN);
    }

    @Override
    public String getName() {
	return "Major Regen Potion";
    }

    @Override
    public String getPluralName() {
	return "Major Regen Potions";
    }

    @Override
    public byte getObjectID() {
	return (byte) 12;
    }

    @Override
    public String getDescription() {
	return "Major Regen Potions regenerate your magic significantly when picked up.";
    }
}