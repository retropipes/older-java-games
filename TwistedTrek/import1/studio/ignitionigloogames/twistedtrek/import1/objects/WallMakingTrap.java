/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;

public class WallMakingTrap extends GenericTrap {
    public WallMakingTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Wall-Making Trap";
    }

    @Override
    public String getPluralName() {
        return "Wall-Making Traps";
    }

    @Override
    public byte getObjectID() {
        return (byte) 7;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
        Import1.getApplication().getGameManager().delayedDecayTo(new Wall());
    }

    @Override
    public String getDescription() {
        return "Wall-Making Traps create a Wall when you step OFF them.";
    }
}
