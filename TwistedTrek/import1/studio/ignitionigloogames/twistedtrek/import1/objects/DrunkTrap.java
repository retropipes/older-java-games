/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.effects.EffectConstants;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class DrunkTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public DrunkTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Drunk Trap";
    }

    @Override
    public String getPluralName() {
        return "Drunk Traps";
    }

    @Override
    public byte getObjectID() {
        return (byte) 6;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        Messager.showMessage("You stumble around drunkenly!");
        Import1.getApplication().getGameManager().activateEffect(EffectConstants.EFFECT_DRUNK,
                DrunkTrap.EFFECT_DURATION);
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            MazeObject.playDrunkSound();
        }
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "drunk";
    }

    @Override
    public String getDescription() {
        return "Drunk Traps alter your movement in a way that resembles being intoxicated for 10 steps when stepped on.";
    }
}