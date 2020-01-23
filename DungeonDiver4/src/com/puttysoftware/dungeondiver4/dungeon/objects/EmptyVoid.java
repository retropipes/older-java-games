/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class EmptyVoid extends AbstractWall {
    // Properties
    private String currAppearance;

    // Constructors
    public EmptyVoid() {
        super(false, false, ColorConstants.COLOR_NONE);
        this.currAppearance = "Void";
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_VOID;
    }

    @Override
    public AbstractDungeonObject gameRenderHook(int x, int y, int z) {
        this.determineCurrentAppearance(x, y, z);
        if (this.currAppearance.equals(this.getName())) {
            return this;
        } else {
            return new SealingWall();
        }
    }

    @Override
    public boolean isConditionallySolid(DungeonObjectInventory inv) {
        // Disallow passing through Void under ANY circumstances
        return true;
    }

    @Override
    public void determineCurrentAppearance(int x, int y, int z) {
        Application app = DungeonDiver4.getApplication();
        String mo1Name, mo2Name, mo3Name, mo4Name, mo6Name, mo7Name, mo8Name, mo9Name, thisName;
        thisName = this.getName();
        AbstractDungeonObject mo1 = app.getDungeonManager().getDungeonObject(
                x - 1, y - 1, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo1Name = mo1.getName();
        } catch (NullPointerException np) {
            mo1Name = thisName;
        }
        AbstractDungeonObject mo2 = app.getDungeonManager().getDungeonObject(
                x - 1, y, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (NullPointerException np) {
            mo2Name = thisName;
        }
        AbstractDungeonObject mo3 = app.getDungeonManager().getDungeonObject(
                x - 1, y + 1, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo3Name = mo3.getName();
        } catch (NullPointerException np) {
            mo3Name = thisName;
        }
        AbstractDungeonObject mo4 = app.getDungeonManager().getDungeonObject(x,
                y - 1, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (NullPointerException np) {
            mo4Name = thisName;
        }
        AbstractDungeonObject mo6 = app.getDungeonManager().getDungeonObject(x,
                y + 1, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (NullPointerException np) {
            mo6Name = thisName;
        }
        AbstractDungeonObject mo7 = app.getDungeonManager().getDungeonObject(
                x + 1, y - 1, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo7Name = mo7.getName();
        } catch (NullPointerException np) {
            mo7Name = thisName;
        }
        AbstractDungeonObject mo8 = app.getDungeonManager().getDungeonObject(
                x + 1, y, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (NullPointerException np) {
            mo8Name = thisName;
        }
        AbstractDungeonObject mo9 = app.getDungeonManager().getDungeonObject(
                x + 1, y + 1, z, DungeonConstants.LAYER_OBJECT);
        try {
            mo9Name = mo9.getName();
        } catch (NullPointerException np) {
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
        return "The Void surrounds the dungeon, and cannot be altered in any way.";
    }
}
