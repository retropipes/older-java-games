/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractDungeonObject;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractWall;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;

public class EmptyVoid extends AbstractWall {
    // Properties
    private String currAppearance;

    // Constructors
    public EmptyVoid() {
        super();
        this.currAppearance = "Void";
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_VOID;
    }

    @Override
    public AbstractDungeonObject gameRenderHook(final int x, final int y,
            final int z) {
        this.determineCurrentAppearance(x, y, z);
        if (this.currAppearance.equals(this.getName())) {
            return this;
        } else {
            return new SealingWall();
        }
    }

    @Override
    public void determineCurrentAppearance(final int x, final int y,
            final int z) {
        final Application app = DynamicDungeon.getApplication();
        String mo1Name, mo2Name, mo3Name, mo4Name, mo6Name, mo7Name, mo8Name,
                mo9Name, thisName;
        thisName = this.getName();
        final AbstractDungeonObject mo1 = app.getDungeonManager()
                .getDungeonObject(x - 1, y - 1, z,
                        DungeonConstants.LAYER_OBJECT);
        try {
            mo1Name = mo1.getName();
        } catch (final NullPointerException np) {
            mo1Name = thisName;
        }
        final AbstractDungeonObject mo2 = app.getDungeonManager()
                .getDungeonObject(x - 1, y, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = thisName;
        }
        final AbstractDungeonObject mo3 = app.getDungeonManager()
                .getDungeonObject(x - 1, y + 1, z,
                        DungeonConstants.LAYER_OBJECT);
        try {
            mo3Name = mo3.getName();
        } catch (final NullPointerException np) {
            mo3Name = thisName;
        }
        final AbstractDungeonObject mo4 = app.getDungeonManager()
                .getDungeonObject(x, y - 1, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = thisName;
        }
        final AbstractDungeonObject mo6 = app.getDungeonManager()
                .getDungeonObject(x, y + 1, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = thisName;
        }
        final AbstractDungeonObject mo7 = app.getDungeonManager()
                .getDungeonObject(x + 1, y - 1, z,
                        DungeonConstants.LAYER_OBJECT);
        try {
            mo7Name = mo7.getName();
        } catch (final NullPointerException np) {
            mo7Name = thisName;
        }
        final AbstractDungeonObject mo8 = app.getDungeonManager()
                .getDungeonObject(x + 1, y, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = thisName;
        }
        final AbstractDungeonObject mo9 = app.getDungeonManager()
                .getDungeonObject(x + 1, y + 1, z,
                        DungeonConstants.LAYER_OBJECT);
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
        return "The Void surrounds the maze, and cannot be altered in any way.";
    }
}
