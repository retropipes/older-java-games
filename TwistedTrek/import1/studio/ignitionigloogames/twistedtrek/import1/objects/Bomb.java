/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericUsableObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class Bomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 3;

    // Constructors
    public Bomb() {
        super(1);
    }

    @Override
    public String getName() {
        return "Bomb";
    }

    @Override
    public String getPluralName() {
        return "Bombs";
    }

    @Override
    public byte getObjectID() {
        return (byte) 0;
    }

    @Override
    public String getDescription() {
        return "Bombs kill Monsters in an area of radius 3 centered on the target point.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
            final int dirY, final int arrowType, final ObjectInventory inv) {
        // Act as if bomb was used
        this.useAction(null, locX, locY, locZ, locW);
        // Destroy bomb
        Import1.getApplication().getGameManager().morph(new Empty(), locX, locY, locZ, locW);
        // Stop arrow
        return false;
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y, final int z, final int w) {
        // Kill any monsters nearby
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playUseSound();
        }
        Import1.getApplication().getMazeManager().getMaze().radialScanKillMonsters(x, y, z, w, Maze.LAYER_OBJECT,
                Bomb.EFFECT_RADIUS);
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
        this.useAction(null, x, y, z, w);
    }

    @Override
    public String getUseSoundName() {
        return "explode";
    }
}