/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericWall;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.world.WorldConstants;

public class EmptyVoid extends GenericWall {
    // Properties
    private String currAppearance;

    // Constructors
    public EmptyVoid() {
        super(false, false);
        this.currAppearance = "Void";
    }

    @Override
    public String gameRenderHook(final int x, final int y, final int z) {
        final Application app = Worldz.getApplication();
        String mo1Name, mo2Name, mo3Name, mo4Name, mo6Name, mo7Name, mo8Name,
                mo9Name, thisName;
        thisName = this.getName();
        final WorldObject mo1 = app.getWorldManager().getWorldObject(x - 1,
                y - 1, z, WorldConstants.LAYER_OBJECT);
        try {
            mo1Name = mo1.getName();
        } catch (final NullPointerException np) {
            mo1Name = thisName;
        }
        final WorldObject mo2 = app.getWorldManager().getWorldObject(x - 1, y,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = thisName;
        }
        final WorldObject mo3 = app.getWorldManager().getWorldObject(x - 1,
                y + 1, z, WorldConstants.LAYER_OBJECT);
        try {
            mo3Name = mo3.getName();
        } catch (final NullPointerException np) {
            mo3Name = thisName;
        }
        final WorldObject mo4 = app.getWorldManager().getWorldObject(x, y - 1,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = thisName;
        }
        final WorldObject mo6 = app.getWorldManager().getWorldObject(x, y + 1,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = thisName;
        }
        final WorldObject mo7 = app.getWorldManager().getWorldObject(x + 1,
                y - 1, z, WorldConstants.LAYER_OBJECT);
        try {
            mo7Name = mo7.getName();
        } catch (final NullPointerException np) {
            mo7Name = thisName;
        }
        final WorldObject mo8 = app.getWorldManager().getWorldObject(x + 1, y,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = thisName;
        }
        final WorldObject mo9 = app.getWorldManager().getWorldObject(x + 1,
                y + 1, z, WorldConstants.LAYER_OBJECT);
        try {
            mo9Name = mo9.getName();
        } catch (final NullPointerException np) {
            mo9Name = thisName;
        }
        if (!thisName.equals(mo1Name) || !thisName.equals(mo2Name)
                || !thisName.equals(mo3Name) || !thisName.equals(mo4Name)
                || !thisName.equals(mo6Name) || !thisName.equals(mo7Name)
                || !thisName.equals(mo8Name) || !thisName.equals(mo9Name)) {
            this.currAppearance = "Sealing Wall";
            return this.currAppearance;
        } else {
            this.currAppearance = "Void";
            return super.gameRenderHook(x, y, z);
        }
    }

    @Override
    public void determineCurrentAppearance(final int x, final int y,
            final int z) {
        final Application app = Worldz.getApplication();
        String mo1Name, mo2Name, mo3Name, mo4Name, mo6Name, mo7Name, mo8Name,
                mo9Name, thisName;
        thisName = this.getName();
        final WorldObject mo1 = app.getWorldManager().getWorldObject(x - 1,
                y - 1, z, WorldConstants.LAYER_OBJECT);
        try {
            mo1Name = mo1.getName();
        } catch (final NullPointerException np) {
            mo1Name = thisName;
        }
        final WorldObject mo2 = app.getWorldManager().getWorldObject(x - 1, y,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = thisName;
        }
        final WorldObject mo3 = app.getWorldManager().getWorldObject(x - 1,
                y + 1, z, WorldConstants.LAYER_OBJECT);
        try {
            mo3Name = mo3.getName();
        } catch (final NullPointerException np) {
            mo3Name = thisName;
        }
        final WorldObject mo4 = app.getWorldManager().getWorldObject(x, y - 1,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = thisName;
        }
        final WorldObject mo6 = app.getWorldManager().getWorldObject(x, y + 1,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = thisName;
        }
        final WorldObject mo7 = app.getWorldManager().getWorldObject(x + 1,
                y - 1, z, WorldConstants.LAYER_OBJECT);
        try {
            mo7Name = mo7.getName();
        } catch (final NullPointerException np) {
            mo7Name = thisName;
        }
        final WorldObject mo8 = app.getWorldManager().getWorldObject(x + 1, y,
                z, WorldConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = thisName;
        }
        final WorldObject mo9 = app.getWorldManager().getWorldObject(x + 1,
                y + 1, z, WorldConstants.LAYER_OBJECT);
        try {
            mo9Name = mo9.getName();
        } catch (final NullPointerException np) {
            mo9Name = thisName;
        }
        if (!thisName.equals(mo1Name) || !thisName.equals(mo2Name)
                || !thisName.equals(mo3Name) || !thisName.equals(mo4Name)
                || !thisName.equals(mo6Name) || !thisName.equals(mo7Name)
                || !thisName.equals(mo8Name) || !thisName.equals(mo9Name)) {
            this.currAppearance = "Sealing Wall";
        } else {
            this.currAppearance = "Void";
        }
    }

    @Override
    public String getName() {
        return "Void";
    }

    @Override
    public String getGameName() {
        return this.currAppearance;
    }

    @Override
    public String getPluralName() {
        return "Voids";
    }

    @Override
    public String getDescription() {
        return "The Void surrounds the world, and cannot be altered in any way.";
    }
}
