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

public class IceBomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 2;

    // Constructors
    public IceBomb() {
        super(1);
    }

    @Override
    public String getName() {
        return "Ice Bomb";
    }

    @Override
    public String getPluralName() {
        return "Ice Bombs";
    }

    @Override
    public String getDescription() {
        return "Ice Bombs freeze anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY,
            final int arrowType, final ObjectInventory inv) {
        // Act as if bomb was used
        this.useAction(null, locX, locY, locZ);
        // Destroy bomb
        Worldz.getApplication().getGameManager()
                .morph(new Empty(), locX, locY, locZ);
        // Stop arrow
        return false;
    }

    @Override
    public void useAction(final WorldObject mo, final int x, final int y,
            final int z) {
        // Freeze any monsters nearby
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playUseSound();
        }
        // Freeze objects that react to ice
        Worldz.getApplication().getWorldManager().getWorld()
                .radialScanFreezeObjects(x, y, z, IceBomb.EFFECT_RADIUS);
        // Freeze ground, too
        Worldz.getApplication().getWorldManager().getWorld()
                .radialScanFreezeGround(x, y, z, IceBomb.EFFECT_RADIUS);
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
