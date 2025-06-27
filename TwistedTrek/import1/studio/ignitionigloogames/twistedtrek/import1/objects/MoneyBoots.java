/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBoots;

public class MoneyBoots extends GenericBoots {
    // Constants
    private static final int GOLD_AMOUNT = 1;

    // Constructors
    public MoneyBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Money Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Money Boots";
    }

    @Override
    public byte getObjectID() {
        return (byte) 10;
    }

    @Override
    public String getDescription() {
        return "Money Boots give you money as you walk. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public void stepAction() {
        PCManager.getPlayer().offsetGold(MoneyBoots.GOLD_AMOUNT);
        Import1.getApplication().getGameManager().getScoreTracker().incrementScore();
    }
}
