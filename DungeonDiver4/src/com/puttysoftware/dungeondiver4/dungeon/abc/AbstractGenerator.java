/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.objects.EmptyVoid;
import com.puttysoftware.dungeondiver4.dungeon.objects.HorizontalBarrier;
import com.puttysoftware.dungeondiver4.dungeon.objects.VerticalBarrier;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ArrowTypeConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DirectionConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractGenerator extends AbstractWall {
    // Fields
    protected int SCAN_LIMIT = 6;
    protected int TIMER_DELAY = 12;

    // Constructors
    protected AbstractGenerator(int tc) {
        super(tc);
        this.activateTimer(this.TIMER_DELAY);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BARRIER_GENERATOR;
    }

    protected abstract boolean preMoveActionHook(int dirX, int dirY, int dirZ,
            int dirW);

    @Override
    public boolean preMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        // Remove barriers if present
        boolean scanResult = false;
        boolean flag = false;
        Application app = DungeonDiver4.getApplication();
        int pz = app.getDungeonManager().getDungeon().getPlayerLocationZ();
        int pw = app.getDungeonManager().getDungeon().getPlayerLocationW();
        String mo2Name, mo4Name, mo6Name, mo8Name, invalidName, horzName, vertName;
        invalidName = new EmptyVoid().getName();
        horzName = new HorizontalBarrier().getName();
        vertName = new VerticalBarrier().getName();
        AbstractDungeonObject mo2 = app.getDungeonManager().getDungeonObject(
                dirX - 1, dirY, pz, DungeonConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (NullPointerException np) {
            mo2Name = invalidName;
        } catch (ArrayIndexOutOfBoundsException aioob) {
            mo2Name = invalidName;
        }
        AbstractDungeonObject mo4 = app.getDungeonManager().getDungeonObject(
                dirX, dirY - 1, pz, DungeonConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (NullPointerException np) {
            mo4Name = invalidName;
        } catch (ArrayIndexOutOfBoundsException aioob) {
            mo4Name = invalidName;
        }
        AbstractDungeonObject mo6 = app.getDungeonManager().getDungeonObject(
                dirX, dirY + 1, pz, DungeonConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (NullPointerException np) {
            mo6Name = invalidName;
        } catch (ArrayIndexOutOfBoundsException aioob) {
            mo6Name = invalidName;
        }
        AbstractDungeonObject mo8 = app.getDungeonManager().getDungeonObject(
                dirX + 1, dirY, pz, DungeonConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (NullPointerException np) {
            mo8Name = invalidName;
        } catch (ArrayIndexOutOfBoundsException aioob) {
            mo8Name = invalidName;
        }
        if (mo2Name.equals(horzName)) {
            scanResult = this.scan(DirectionConstants.DIRECTION_WEST, dirX,
                    dirY, pz, this.SCAN_LIMIT, false);
            if (scanResult) {
                this.generate(DirectionConstants.DIRECTION_WEST, dirX, dirY,
                        pz, this.SCAN_LIMIT, false);
                flag = true;
            }
        }
        if (mo4Name.equals(vertName)) {
            scanResult = this.scan(DirectionConstants.DIRECTION_NORTH, dirX,
                    dirY, pz, this.SCAN_LIMIT, false);
            if (scanResult) {
                this.generate(DirectionConstants.DIRECTION_NORTH, dirX, dirY,
                        pz, this.SCAN_LIMIT, false);
                flag = true;
            }
        }
        if (mo6Name.equals(vertName)) {
            scanResult = this.scan(DirectionConstants.DIRECTION_SOUTH, dirX,
                    dirY, pz, this.SCAN_LIMIT, false);
            if (scanResult) {
                this.generate(DirectionConstants.DIRECTION_SOUTH, dirX, dirY,
                        pz, this.SCAN_LIMIT, false);
                flag = true;
            }
        }
        if (mo8Name.equals(horzName)) {
            scanResult = this.scan(DirectionConstants.DIRECTION_EAST, dirX,
                    dirY, pz, this.SCAN_LIMIT, false);
            if (scanResult) {
                this.generate(DirectionConstants.DIRECTION_EAST, dirX, dirY,
                        pz, this.SCAN_LIMIT, false);
                flag = true;
            }
        }
        if (flag) {
            SoundManager.playSound(SoundConstants.SOUND_GENERATE);
            this.activateTimer(this.TIMER_DELAY);
            app.getGameManager().redrawDungeon();
        }
        return this.preMoveActionHook(dirX, dirY, pz, pw);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void timerExpiredAction(int dirX, int dirY) {
        // Generate barriers again
        boolean scanResult = false;
        boolean flag = false;
        Application app = DungeonDiver4.getApplication();
        int pz = app.getDungeonManager().getDungeon().getPlayerLocationZ();
        String mo2Name, mo4Name, mo6Name, mo8Name, invalidName, horzName, vertName;
        invalidName = new EmptyVoid().getName();
        horzName = new HorizontalBarrier().getName();
        vertName = new VerticalBarrier().getName();
        AbstractDungeonObject mo2 = app.getDungeonManager().getDungeonObject(
                dirX - 1, dirY, pz, DungeonConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (NullPointerException np) {
            mo2Name = invalidName;
        } catch (ArrayIndexOutOfBoundsException aioob) {
            mo2Name = invalidName;
        }
        AbstractDungeonObject mo4 = app.getDungeonManager().getDungeonObject(
                dirX, dirY - 1, pz, DungeonConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (NullPointerException np) {
            mo4Name = invalidName;
        } catch (ArrayIndexOutOfBoundsException aioob) {
            mo4Name = invalidName;
        }
        AbstractDungeonObject mo6 = app.getDungeonManager().getDungeonObject(
                dirX, dirY + 1, pz, DungeonConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (NullPointerException np) {
            mo6Name = invalidName;
        } catch (ArrayIndexOutOfBoundsException aioob) {
            mo6Name = invalidName;
        }
        AbstractDungeonObject mo8 = app.getDungeonManager().getDungeonObject(
                dirX + 1, dirY, pz, DungeonConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (NullPointerException np) {
            mo8Name = invalidName;
        } catch (ArrayIndexOutOfBoundsException aioob) {
            mo8Name = invalidName;
        }
        if (!mo2Name.equals(horzName)) {
            scanResult = this.scan(DirectionConstants.DIRECTION_WEST, dirX,
                    dirY, pz, this.SCAN_LIMIT, true);
            if (scanResult) {
                this.generate(DirectionConstants.DIRECTION_WEST, dirX, dirY,
                        pz, this.SCAN_LIMIT, true);
                flag = true;
            }
        }
        if (!mo4Name.equals(vertName)) {
            scanResult = this.scan(DirectionConstants.DIRECTION_NORTH, dirX,
                    dirY, pz, this.SCAN_LIMIT, true);
            if (scanResult) {
                this.generate(DirectionConstants.DIRECTION_NORTH, dirX, dirY,
                        pz, this.SCAN_LIMIT, true);
                flag = true;
            }
        }
        if (!mo6Name.equals(vertName)) {
            scanResult = this.scan(DirectionConstants.DIRECTION_SOUTH, dirX,
                    dirY, pz, this.SCAN_LIMIT, true);
            if (scanResult) {
                this.generate(DirectionConstants.DIRECTION_SOUTH, dirX, dirY,
                        pz, this.SCAN_LIMIT, true);
                flag = true;
            }
        }
        if (!mo8Name.equals(horzName)) {
            scanResult = this.scan(DirectionConstants.DIRECTION_EAST, dirX,
                    dirY, pz, this.SCAN_LIMIT, true);
            if (scanResult) {
                this.generate(DirectionConstants.DIRECTION_EAST, dirX, dirY,
                        pz, this.SCAN_LIMIT, true);
                flag = true;
            }
        }
        if (flag) {
            SoundManager.playSound(SoundConstants.SOUND_GENERATE);
            app.getGameManager().redrawDungeon();
        }
        // Activate the timer again
        this.activateTimer(this.TIMER_DELAY);
    }

    protected boolean scan(int dir, int x, int y, int z, int limit, boolean o) {
        Application app = DungeonDiver4.getApplication();
        String invalidName = new EmptyVoid().getName();
        if (dir == DirectionConstants.DIRECTION_EAST) {
            for (int l = 1; l < limit; l++) {
                AbstractDungeonObject mo = app.getDungeonManager()
                        .getDungeonObject(x + l, y, z,
                                DungeonConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (NullPointerException np) {
                    moName = invalidName;
                }
                if (o) {
                    try {
                        if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                            return true;
                        }
                        if (mo.isSolid()) {
                            return false;
                        }
                    } catch (NullPointerException np) {
                        // Do nothing
                    }
                } else {
                    if (!moName.equals(new HorizontalBarrier().getName())) {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                return true;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_NORTH) {
            for (int l = 1; l < limit; l++) {
                AbstractDungeonObject mo = app.getDungeonManager()
                        .getDungeonObject(x, y - l, z,
                                DungeonConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (NullPointerException np) {
                    moName = invalidName;
                }
                if (o) {
                    try {
                        if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                            return true;
                        }
                        if (mo.isSolid()) {
                            return false;
                        }
                    } catch (NullPointerException np) {
                        // Do nothing
                    }
                } else {
                    if (!moName.equals(new VerticalBarrier().getName())) {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                return true;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_SOUTH) {
            for (int l = 1; l < limit; l++) {
                AbstractDungeonObject mo = app.getDungeonManager()
                        .getDungeonObject(x, y + l, z,
                                DungeonConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (NullPointerException np) {
                    moName = invalidName;
                }
                if (o) {
                    try {
                        if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                            return true;
                        }
                        if (mo.isSolid()) {
                            return false;
                        }
                    } catch (NullPointerException np) {
                        // Do nothing
                    }
                } else {
                    if (!moName.equals(new VerticalBarrier().getName())) {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                return true;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_WEST) {
            for (int l = 1; l < limit; l++) {
                AbstractDungeonObject mo = app.getDungeonManager()
                        .getDungeonObject(x - l, y, z,
                                DungeonConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (NullPointerException np) {
                    moName = invalidName;
                }
                if (o) {
                    try {
                        if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                            return true;
                        }
                        if (mo.isSolid()) {
                            return false;
                        }
                    } catch (NullPointerException np) {
                        // Do nothing
                    }
                } else {
                    if (!moName.equals(new HorizontalBarrier().getName())) {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                return true;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                    }
                }
            }
        }
        return false;
    }

    protected void generate(int dir, int x, int y, int z, int limit, boolean o) {
        Application app = DungeonDiver4.getApplication();
        String invalidName = new EmptyVoid().getName();
        if (dir == DirectionConstants.DIRECTION_EAST) {
            for (int l = 1; l < limit; l++) {
                AbstractDungeonObject mo = app.getDungeonManager()
                        .getDungeonObject(x + l, y, z,
                                DungeonConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (NullPointerException np) {
                    moName = invalidName;
                }
                if (o) {
                    if (moName.equals(new HorizontalBarrier().getName())) {
                        break;
                    } else {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                break;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getDungeonManager()
                                    .getDungeon()
                                    .setCell(new HorizontalBarrier(), x + l, y,
                                            z, DungeonConstants.LAYER_OBJECT);
                        } catch (ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                } else {
                    if (!moName.equals(new HorizontalBarrier().getName())) {
                        break;
                    } else {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                break;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getDungeonManager()
                                    .getDungeon()
                                    .setCell(new Empty(), x + l, y, z,
                                            DungeonConstants.LAYER_OBJECT);
                        } catch (ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_NORTH) {
            for (int l = 1; l < limit; l++) {
                AbstractDungeonObject mo = app.getDungeonManager()
                        .getDungeonObject(x, y - l, z,
                                DungeonConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (NullPointerException np) {
                    moName = invalidName;
                }
                if (o) {
                    if (moName.equals(new VerticalBarrier().getName())) {
                        break;
                    } else {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                break;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getDungeonManager()
                                    .getDungeon()
                                    .setCell(new VerticalBarrier(), x, y - l,
                                            z, DungeonConstants.LAYER_OBJECT);
                        } catch (ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                } else {
                    if (!moName.equals(new VerticalBarrier().getName())) {
                        break;
                    } else {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                break;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getDungeonManager()
                                    .getDungeon()
                                    .setCell(new Empty(), x, y - l, z,
                                            DungeonConstants.LAYER_OBJECT);
                        } catch (ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_SOUTH) {
            for (int l = 1; l < limit; l++) {
                AbstractDungeonObject mo = app.getDungeonManager()
                        .getDungeonObject(x, y + l, z,
                                DungeonConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (NullPointerException np) {
                    moName = invalidName;
                }
                if (o) {
                    if (moName.equals(new VerticalBarrier().getName())) {
                        break;
                    } else {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                break;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getDungeonManager()
                                    .getDungeon()
                                    .setCell(new VerticalBarrier(), x, y + l,
                                            z, DungeonConstants.LAYER_OBJECT);
                        } catch (ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                } else {
                    if (!moName.equals(new VerticalBarrier().getName())) {
                        break;
                    } else {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                break;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getDungeonManager()
                                    .getDungeon()
                                    .setCell(new Empty(), x, y + l, z,
                                            DungeonConstants.LAYER_OBJECT);
                        } catch (ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_WEST) {
            for (int l = 1; l < limit; l++) {
                AbstractDungeonObject mo = app.getDungeonManager()
                        .getDungeonObject(x - l, y, z,
                                DungeonConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (NullPointerException np) {
                    moName = invalidName;
                }
                if (o) {
                    if (moName.equals(new HorizontalBarrier().getName())) {
                        break;
                    } else {
                        if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                            break;
                        }
                        try {
                            app.getDungeonManager()
                                    .getDungeon()
                                    .setCell(new HorizontalBarrier(), x - l, y,
                                            z, DungeonConstants.LAYER_OBJECT);
                        } catch (ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                } else {
                    if (!moName.equals(new HorizontalBarrier().getName())) {
                        break;
                    } else {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                break;
                            }
                        } catch (NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getDungeonManager()
                                    .getDungeon()
                                    .setCell(new Empty(), x - l, y, z,
                                            DungeonConstants.LAYER_OBJECT);
                        } catch (ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldGenerateObject(Dungeon dungeon, int row, int col,
            int floor, int level, int layer) {
        // Blacklist object
        return false;
    }

    @Override
    public boolean arrowHitAction(int locX, int locY, int locZ, int dirX,
            int dirY, int arrowType, DungeonObjectInventory inv) {
        // Behave as if the generator was walked into, unless the arrow was an
        // ice arrow
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_PLAIN) {
            this.preMoveAction(false, locX, locY, inv);
        } else {
            this.arrowHitActionHook(locX, locY, locZ, arrowType, inv);
        }
        return false;
    }

    protected abstract void arrowHitActionHook(int locX, int locY, int locZ,
            int arrowType, DungeonObjectInventory inv);

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_REACTS_TO_ICE);
        this.type.set(TypeConstants.TYPE_REACTS_TO_FIRE);
        this.type.set(TypeConstants.TYPE_REACTS_TO_POISON);
        this.type.set(TypeConstants.TYPE_REACTS_TO_SHOCK);
        this.type.set(TypeConstants.TYPE_GENERATOR);
    }
}
