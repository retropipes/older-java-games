/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;

public class CrackedWall extends GenericWall {
    // Constructors
    public CrackedWall() {
        super();
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
            final int dirY, final int arrowType, final ObjectInventory inv) {
        this.moveFailedAction(true, locX, locY, inv);
        return false;
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        // Crack the wall
        final int pz = Import1.getApplication().getGameManager().getPlayerManager().getPlayerLocationZ();
        final int pw = Import1.getApplication().getGameManager().getPlayerManager().getPlayerLocationW();
        Import1.getApplication().getGameManager().morph(new DamagedWall(), dirX, dirY, pz, pw);
        // Play move failed sound, if it's enabled
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
        }
    }

    @Override
    public String getName() {
        return "Cracked Wall";
    }

    @Override
    public String getPluralName() {
        return "Cracked Walls";
    }

    @Override
    public String getMoveFailedSoundName() {
        return "crack";
    }

    @Override
    public byte getObjectID() {
        return (byte) 0;
    }

    @Override
    public String getDescription() {
        return "Cracked Walls turn into Damaged Walls when hit.";
    }
}