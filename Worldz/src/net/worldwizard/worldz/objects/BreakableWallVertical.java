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

public class BreakableWallVertical extends GenericWall {
    // Constructors
    public BreakableWallVertical() {
        super(true, true);
    }

    @Override
    public void chainReactionAction(final int x, final int y, final int z) {
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playChainReactSound();
        }
        this.doChainReact(x, y, z);
    }

    public void doChainReact(final int x, final int y, final int z) {
        final Application app = Worldz.getApplication();
        BreakableWallVertical curr = null;
        try {
            curr = (BreakableWallVertical) app.getWorldManager()
                    .getWorldObject(x, y, z, WorldConstants.LAYER_OBJECT);
        } catch (final ClassCastException cce) {
            // We're not a breakable wall vertical, so abort
            return;
        }
        String mo2Name, mo8Name, invalidName, currName;
        invalidName = new EmptyVoid().getName();
        currName = curr.getName();
        final WorldObject mo2 = app.getWorldManager().getWorldObject(x, y - 1,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = invalidName;
        }
        final WorldObject mo8 = app.getWorldManager().getWorldObject(x, y + 1,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = invalidName;
        }
        app.getGameManager().morph(new Empty(), x, y, z);
        if (mo2Name.equals(currName)) {
            curr.doChainReact(x, y - 1, z);
        }
        if (mo8Name.equals(currName)) {
            curr.doChainReact(x, y + 1, z);
        }
    }

    @Override
    public String getName() {
        return "Breakable Wall Vertical";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Breakable Walls Vertical";
    }

    @Override
    public String getDescription() {
        return "Breakable Walls Vertical disintegrate when touched, causing other Breakable Walls Vertical nearby to also disintegrate.";
    }

    @Override
    public String getChainReactSoundName() {
        return "crack";
    }
}
