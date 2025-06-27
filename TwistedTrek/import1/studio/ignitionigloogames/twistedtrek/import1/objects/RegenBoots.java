/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBoots;

public class RegenBoots extends GenericBoots {
    // Constants
    private static final int REGEN_AMOUNT = 1;

    // Constructors
    public RegenBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Regen Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Regen Boots";
    }

    @Override
    public byte getObjectID() {
        return (byte) 8;
    }

    @Override
    public String getDescription() {
        return "Regen Boots restore your magic as you walk. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public void stepAction() {
        PCManager.getPlayer().regenerate(RegenBoots.REGEN_AMOUNT);
    }
}
