/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericWall;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.world.WorldConstants;

public class ExplodingWall extends GenericWall {
    // Constructors
    public ExplodingWall() {
        super(true, true);
    }

    @Override
    public boolean preMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        Messager.showMessage("BOOM!");
        return true;
    }

    @Override
    public void chainReactionAction(final int x, final int y, final int z) {
        // Explode this wall, and any exploding walls next to this wall as well
        final Application app = Worldz.getApplication();
        ExplodingWall curr = null;
        try {
            curr = (ExplodingWall) app.getWorldManager().getWorldObject(x, y, z,
                    WorldConstants.LAYER_OBJECT);
        } catch (final ClassCastException cce) {
            // We're not an exploding wall, so abort
            return;
        }
        String mo2Name, mo4Name, mo6Name, mo8Name, invalidName, currName;
        invalidName = new EmptyVoid().getName();
        currName = curr.getName();
        final WorldObject mo2 = app.getWorldManager().getWorldObject(x - 1, y,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = invalidName;
        }
        final WorldObject mo4 = app.getWorldManager().getWorldObject(x, y - 1,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = invalidName;
        }
        final WorldObject mo6 = app.getWorldManager().getWorldObject(x, y + 1,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = invalidName;
        }
        final WorldObject mo8 = app.getWorldManager().getWorldObject(x + 1, y,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = invalidName;
        }
        app.getGameManager().morph(new Empty(), x, y, z, "BOOM!");
        if (mo2Name.equals(currName)) {
            curr.chainReactionAction(x - 1, y, z);
        }
        if (mo4Name.equals(currName)) {
            curr.chainReactionAction(x, y - 1, z);
        }
        if (mo6Name.equals(currName)) {
            curr.chainReactionAction(x, y + 1, z);
        }
        if (mo8Name.equals(currName)) {
            curr.chainReactionAction(x + 1, y, z);
        }
        if (app.getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            curr.playChainReactSound();
        }
    }

    @Override
    public String getName() {
        return "Exploding Wall";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Exploding Walls";
    }

    @Override
    public String getDescription() {
        return "Exploding Walls explode when touched, causing other Exploding Walls nearby to also explode.";
    }
}