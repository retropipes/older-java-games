/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class SuperUnknownPotion extends GenericPotion {
    // Fields
    private static final int MIN_EFFECT = -99;
    private static final int MAX_EFFECT = 99;

    // Constructors
    public SuperUnknownPotion() {
        super(StatConstants.STAT_CURRENT_HP, true, SuperUnknownPotion.MIN_EFFECT, SuperUnknownPotion.MAX_EFFECT);
    }

    @Override
    public String getName() {
        return "Super Unknown Potion";
    }

    @Override
    public String getPluralName() {
        return "Super Unknown Potions";
    }

    @Override
    public byte getObjectID() {
        return (byte) 9;
    }

    @Override
    public String getDescription() {
        return "Super Unknown Potions might heal you almost fully or hurt you to the brink of death when picked up.";
    }
}