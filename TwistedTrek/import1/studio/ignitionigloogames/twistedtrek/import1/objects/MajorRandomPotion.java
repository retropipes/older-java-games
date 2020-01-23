/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class MajorRandomPotion extends GenericPotion {
    // Fields
    private static final int MIN_EFFECT = -25;
    private static final int MAX_EFFECT = 25;

    // Constructors
    public MajorRandomPotion() {
	super(StatConstants.STAT_CURRENT_MP, true, MajorRandomPotion.MIN_EFFECT, MajorRandomPotion.MAX_EFFECT);
    }

    @Override
    public String getName() {
	return "Major Random Potion";
    }

    @Override
    public String getPluralName() {
	return "Major Random Potions";
    }

    @Override
    public byte getObjectID() {
	return (byte) 17;
    }

    @Override
    public String getDescription() {
	return "Major Random Potions might regenerate your magic or drain your magic significantly when picked up.";
    }
}