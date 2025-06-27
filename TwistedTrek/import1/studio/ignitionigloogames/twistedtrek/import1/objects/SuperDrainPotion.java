/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class SuperDrainPotion extends GenericPotion {
    // Constructors
    public SuperDrainPotion() {
        super(StatConstants.STAT_CURRENT_MP, false);
    }

    @Override
    public String getName() {
        return "Super Drain Potion";
    }

    @Override
    public String getPluralName() {
        return "Super Drain Potions";
    }

    @Override
    public byte getObjectID() {
        return (byte) 15;
    }

    @Override
    public int getEffectValue() {
        return -PCManager.getPlayer().getCurrentMP();
    }

    @Override
    public String getDescription() {
        return "Super Drain Potions drain your magic completely when picked up.";
    }
}