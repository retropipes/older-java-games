/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class MinorRandomPotion extends GenericPotion {
    // Fields
    private static final int MIN_EFFECT = -3;
    private static final int MAX_EFFECT = 3;

    // Constructors
    public MinorRandomPotion() {
	super(StatConstants.STAT_CURRENT_MP, true, MinorRandomPotion.MIN_EFFECT, MinorRandomPotion.MAX_EFFECT);
    }

    @Override
    public String getName() {
	return "Minor Random Potion";
    }

    @Override
    public String getPluralName() {
	return "Minor Random Potions";
    }

    @Override
    public byte getObjectID() {
	return (byte) 16;
    }

    @Override
    public String getDescription() {
	return "Minor Random Potions might regenerate your magic or drain your magic slightly when picked up.";
    }
}