/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class SuperRandomPotion extends GenericPotion {
    // Fields
    private static final int MIN_EFFECT = -99;
    private static final int MAX_EFFECT = 99;

    // Constructors
    public SuperRandomPotion() {
	super(StatConstants.STAT_CURRENT_MP, true, SuperRandomPotion.MIN_EFFECT, SuperRandomPotion.MAX_EFFECT);
    }

    @Override
    public String getName() {
	return "Super Random Potion";
    }

    @Override
    public String getPluralName() {
	return "Super Random Potions";
    }

    @Override
    public byte getObjectID() {
	return (byte) 18;
    }

    @Override
    public String getDescription() {
	return "Super Random Potions might regenerate your magic almost fully or drain your magic almost fully when picked up.";
    }
}