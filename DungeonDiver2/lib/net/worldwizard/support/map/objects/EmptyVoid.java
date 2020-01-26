/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.Map;
import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.map.generic.GenericWall;
import net.worldwizard.support.map.generic.MapObject;

public class EmptyVoid extends GenericWall {
    // Properties
    private String currAppearance;

    // Constructors
    public EmptyVoid() {
        super();
        this.currAppearance = "Void";
    }

    @Override
    public String gameRenderHook(final int x, final int y, final int z,
            final Map map) {
        String mo1Name, mo2Name, mo3Name, mo4Name, mo6Name, mo7Name, mo8Name,
                mo9Name, thisName;
        thisName = this.getName();
        MapObject mo1 = null;
        try {
            mo1 = map.getCell(x - 1, y - 1, z, MapConstants.LAYER_OBJECT);
            mo1Name = mo1.getName();
        } catch (final NullPointerException np) {
            mo1Name = thisName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo1Name = thisName;
        }
        MapObject mo2 = null;
        try {
            mo2 = map.getCell(x - 1, y, z, MapConstants.LAYER_OBJECT);
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = thisName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo2Name = thisName;
        }
        MapObject mo3 = null;
        try {
            mo3 = map.getCell(x - 1, y + 1, z, MapConstants.LAYER_OBJECT);
            mo3Name = mo3.getName();
        } catch (final NullPointerException np) {
            mo3Name = thisName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo3Name = thisName;
        }
        MapObject mo4 = null;
        try {
            mo4 = map.getCell(x, y - 1, z, MapConstants.LAYER_OBJECT);
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = thisName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo4Name = thisName;
        }
        MapObject mo6 = null;
        try {
            mo6 = map.getCell(x, y + 1, z, MapConstants.LAYER_OBJECT);
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = thisName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo6Name = thisName;
        }
        MapObject mo7 = null;
        try {
            mo7 = map.getCell(x + 1, y - 1, z, MapConstants.LAYER_OBJECT);
            mo7Name = mo7.getName();
        } catch (final NullPointerException np) {
            mo7Name = thisName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo7Name = thisName;
        }
        MapObject mo8 = null;
        try {
            mo8 = map.getCell(x + 1, y, z, MapConstants.LAYER_OBJECT);
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = thisName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo8Name = thisName;
        }
        MapObject mo9 = null;
        try {
            mo9 = map.getCell(x + 1, y + 1, z, MapConstants.LAYER_OBJECT);
            mo9Name = mo9.getName();
        } catch (final NullPointerException np) {
            mo9Name = thisName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
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
            return super.gameRenderHook(x, y, z, map);
        }
    }

    @Override
    public boolean isConditionallySolid(final Map map, final int z) {
        // Disallow passing through Void under ANY circumstances
        return true;
    }

    @Override
    public void determineCurrentAppearance(final int x, final int y,
            final int z, final Map map) {
        String mo1Name, mo2Name, mo3Name, mo4Name, mo6Name, mo7Name, mo8Name,
                mo9Name, thisName;
        thisName = this.getName();
        final MapObject mo1 = map.getCell(x - 1, y - 1, z,
                MapConstants.LAYER_OBJECT);
        try {
            mo1Name = mo1.getName();
        } catch (final NullPointerException np) {
            mo1Name = thisName;
        }
        final MapObject mo2 = map.getCell(x - 1, y, z,
                MapConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = thisName;
        }
        final MapObject mo3 = map.getCell(x - 1, y + 1, z,
                MapConstants.LAYER_OBJECT);
        try {
            mo3Name = mo3.getName();
        } catch (final NullPointerException np) {
            mo3Name = thisName;
        }
        final MapObject mo4 = map.getCell(x, y - 1, z,
                MapConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = thisName;
        }
        final MapObject mo6 = map.getCell(x, y + 1, z,
                MapConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = thisName;
        }
        final MapObject mo7 = map.getCell(x + 1, y - 1, z,
                MapConstants.LAYER_OBJECT);
        try {
            mo7Name = mo7.getName();
        } catch (final NullPointerException np) {
            mo7Name = thisName;
        }
        final MapObject mo8 = map.getCell(x + 1, y, z,
                MapConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = thisName;
        }
        final MapObject mo9 = map.getCell(x + 1, y + 1, z,
                MapConstants.LAYER_OBJECT);
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
        return "The Void surrounds the map, and cannot be altered in any way.";
    }
}
