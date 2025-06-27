/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class MinorHealPotion extends GenericPotion {
    // Fields
    private static final int MIN_HEAL = 1;
    private static final int MAX_HEAL = 5;

    // Constructors
    public MinorHealPotion() {
        super(StatConstants.STAT_CURRENT_HP, true, MinorHealPotion.MIN_HEAL, MinorHealPotion.MAX_HEAL);
    }

    @Override
    public String getName() {
        return "Minor Heal Potion";
    }

    @Override
    public String getPluralName() {
        return "Minor Heal Potions";
    }

    @Override
    public byte getObjectID() {
        return (byte) 1;
    }

    @Override
    public String getDescription() {
        return "Minor Heal Potions heal you slightly when picked up.";
    }
}