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

public class ShockBomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 2;

    // Constructors
    public ShockBomb() {
        super(1);
    }

    @Override
    public String getName() {
        return "Shock Bomb";
    }

    @Override
    public String getPluralName() {
        return "Shock Bombs";
    }

    @Override
    public String getDescription() {
        return "Shock Bombs shock anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        // Act as if bomb was used
        this.useAction(null, locX, locY, locZ);
        // Destroy bomb
        Worldz.getApplication().getGameManager().morph(new Empty(), locX, locY,
                locZ);
        // Stop arrow
        return false;
    }

    @Override
    public void useAction(final WorldObject mo, final int x, final int y,
            final int z) {
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playUseSound();
        }
        // Shock objects that react to shock
        Worldz.getApplication().getWorldManager().getWorld()
                .radialScanShockObjects(x, y, z, ShockBomb.EFFECT_RADIUS);
        // Shock the ground, too
        Worldz.getApplication().getWorldManager().getWorld()
                .radialScanShockGround(x, y, z, ShockBomb.EFFECT_RADIUS);
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
