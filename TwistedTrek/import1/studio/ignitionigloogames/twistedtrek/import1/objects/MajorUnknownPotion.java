/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class MajorUnknownPotion extends GenericPotion {
    // Fields
    private static final int MIN_EFFECT = -25;
    private static final int MAX_EFFECT = 25;

    // Constructors
    public MajorUnknownPotion() {
        super(StatConstants.STAT_CURRENT_HP, true, MajorUnknownPotion.MIN_EFFECT, MajorUnknownPotion.MAX_EFFECT);
    }

    @Override
    public String getName() {
        return "Major Unknown Potion";
    }

    @Override
    public String getPluralName() {
        return "Major Unknown Potions";
    }

    @Override
    public byte getObjectID() {
        return (byte) 8;
    }

    @Override
    public String getDescription() {
        return "Major Unknown Potions might heal you or hurt you significantly when picked up.";
    }
}