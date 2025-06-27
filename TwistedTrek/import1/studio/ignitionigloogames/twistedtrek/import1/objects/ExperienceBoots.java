/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PlayerCharacter;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericBoots;

public class ExperienceBoots extends GenericBoots {
    // Constants
    private static final int EXP_AMOUNT = 1;

    // Constructors
    public ExperienceBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Experience Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Experience Boots";
    }

    @Override
    public byte getObjectID() {
        return (byte) 9;
    }

    @Override
    public String getDescription() {
        return "Experience Boots give you experience as you walk. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public void stepAction() {
        final PlayerCharacter playerCharacter = PCManager.getPlayer();
        playerCharacter.offsetExperience(ExperienceBoots.EXP_AMOUNT);
        Import1.getApplication().getGameManager().getScoreTracker().incrementScore();
        // Level Up Check
        if (playerCharacter.checkLevelUp()) {
            playerCharacter.levelUp();
            Import1.getApplication().getGameManager().keepNextMessage();
            Messager.showMessage("You reached level " + playerCharacter.getLevel() + ".");
        }
    }
}
