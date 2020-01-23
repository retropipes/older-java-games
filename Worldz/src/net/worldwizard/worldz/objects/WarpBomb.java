/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericUsableObject;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.world.WorldConstants;

public class WarpBomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 3;

    // Constructors
    public WarpBomb() {
        super(1);
    }

    @Override
    public String getName() {
        return "Warp Bomb";
    }

    @Override
    public String getPluralName() {
        return "Warp Bombs";
    }

    @Override
    public String getDescription() {
        return "Warp Bombs randomly teleport anything in an area of radius 3 centered on the target point.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY,
            final int arrowType, final ObjectInventory inv) {
        // Destroy bomb
        Worldz.getApplication().getGameManager()
                .morph(new Empty(), locX, locY, locZ);
        // Act as if bomb was used
        this.useAction(null, locX, locY, locZ);
        // Stop arrow
        return false;
    }

    @Override
    public void useAction(final WorldObject mo, final int x, final int y,
            final int z) {
        // Warp objects
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playUseSound();
        }
        Worldz.getApplication()
                .getWorldManager()
                .getWorld()
                .radialScanWarpObjects(x, y, z, WorldConstants.LAYER_OBJECT,
                        WarpBomb.EFFECT_RADIUS);
        // Player might have moved
        Worldz.getApplication().getGameManager().findPlayerAndAdjust();
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }

    @Override
    public String getUseSoundName() {
        return "explode";
    }
}