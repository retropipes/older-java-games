/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.objects;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.effects.MazeEffectConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public class CounterclockwiseRotationTrap extends AbstractTrap {
    // Constructors
    public CounterclockwiseRotationTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_CCW_ROTATION_TRAP);
    }

    @Override
    public String getName() {
        return "Counterclockwise Rotation Trap";
    }

    @Override
    public String getPluralName() {
        return "Counterclockwise Rotation Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
        Import2.getApplication().showMessage("Your controls are rotated!");
        Import2.getApplication().getGameManager().activateEffect(MazeEffectConstants.EFFECT_ROTATED_COUNTERCLOCKWISE);
    }

    @Override
    public String getDescription() {
        return "Counterclockwise Rotation Traps rotate your controls counterclockwise for 6 steps when stepped on.";
    }
}