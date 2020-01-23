/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericWall;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.world.WorldConstants;

public class BreakableWallHorizontal extends GenericWall {
    // Constructors
    public BreakableWallHorizontal() {
        super(true, true);
    }

    @Override
    public void chainReactionAction(final int x, final int y, final int z) {
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playChainReactSound();
        }
        BreakableWallHorizontal.doChainReact(x, y, z);
    }

    private static void doChainReact(final int x, final int y, final int z) {
        final Application app = Worldz.getApplication();
        BreakableWallHorizontal curr = null;
        try {
            curr = (BreakableWallHorizontal) app.getWorldManager()
                    .getWorldObject(x, y, z, WorldConstants.LAYER_OBJECT);
        } catch (final ClassCastException cce) {
            // We're not a breakable wall horizontal, so abort
            return;
        }
        String mo4Name, mo6Name, invalidName, currName;
        invalidName = new EmptyVoid().getName();
        currName = curr.getName();
        final WorldObject mo4 = app.getWorldManager().getWorldObject(x - 1, y,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = invalidName;
        }
        final WorldObject mo6 = app.getWorldManager().getWorldObject(x + 1, y,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = invalidName;
        }
        app.getGameManager().morph(new Empty(), x, y, z);
        if (mo4Name.equals(currName)) {
            BreakableWallHorizontal.doChainReact(x - 1, y, z);
        }
        if (mo6Name.equals(currName)) {
            BreakableWallHorizontal.doChainReact(x + 1, y, z);
        }
    }

    @Override
    public String getName() {
        return "Breakable Wall Horizontal";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Breakable Walls Horizontal";
    }

    @Override
    public String getDescription() {
        return "Breakable Walls Horizontal disintegrate when touched, causing other Breakable Walls Horizontal nearby to also disintegrate.";
    }

    @Override
    public String getChainReactSoundName() {
        return "crack";
    }
}
