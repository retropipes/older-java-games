/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class SuperRegenPotion extends GenericPotion {
    // Constructors
    public SuperRegenPotion() {
        super(StatConstants.STAT_CURRENT_MP, false);
    }

    @Override
    public String getName() {
        return "Super Regen Potion";
    }

    @Override
    public String getPluralName() {
        return "Super Regen Potions";
    }

    @Override
    public byte getObjectID() {
        return (byte) 14;
    }

    @Override
    public int getEffectValue() {
        return PCManager.getPlayer().getMaximumMP();
    }

    @Override
    public String getDescription() {
        return "Super Regen Potions regenerate your magic completely when picked up.";
    }
}