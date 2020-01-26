/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.objects.Empty;
import com.puttysoftware.fantastlex.maze.objects.EmptyVoid;
import com.puttysoftware.fantastlex.maze.objects.HorizontalBarrier;
import com.puttysoftware.fantastlex.maze.objects.VerticalBarrier;
import com.puttysoftware.fantastlex.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.fantastlex.maze.utilities.DirectionConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public abstract class AbstractGenerator extends AbstractWall {
    // Fields
    protected int SCAN_LIMIT = 6;
    protected int TIMER_DELAY = 12;

    // Constructors
    protected AbstractGenerator(final int tc) {
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
            final int dirY, final MazeObjectInventory inv) {
        // Remove barriers if present
        boolean scanResult = false;
        boolean flag = false;
        final Application app = FantastleX.getApplication();
        final int pz = app.getMazeManager().getMaze().getPlayerLocationZ();
        final int pw = app.getMazeManager().getMaze().getPlayerLocationW();
        String mo2Name, mo4Name, mo6Name, mo8Name, invalidName, horzName,
                vertName;
        invalidName = new EmptyVoid().getName();
        horzName = new HorizontalBarrier().getName();
        vertName = new VerticalBarrier().getName();
        final AbstractMazeObject mo2 = app.getMazeManager()
                .getMazeObject(dirX - 1, dirY, pz, MazeConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = invalidName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo2Name = invalidName;
        }
        final AbstractMazeObject mo4 = app.getMazeManager().getMazeObject(dirX,
                dirY - 1, pz, MazeConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = invalidName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo4Name = invalidName;
        }
        final AbstractMazeObject mo6 = app.getMazeManager().getMazeObject(dirX,
                dirY + 1, pz, MazeConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = invalidName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo6Name = invalidName;
        }
        final AbstractMazeObject mo8 = app.getMazeManager()
                .getMazeObject(dirX + 1, dirY, pz, MazeConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = invalidName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo8Name = invalidName;
        }
        if (mo2Name.equals(horzName)) {
            scanResult = this.scan(DirectionConstants.DIRECTION_WEST, dirX,
                    dirY, pz, this.SCAN_LIMIT, false);
            if (scanResult) {
                this.generate(DirectionConstants.DIRECTION_WEST, dirX, dirY, pz,
                        this.SCAN_LIMIT, false);
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
                this.generate(DirectionConstants.DIRECTION_EAST, dirX, dirY, pz,
                        this.SCAN_LIMIT, false);
                flag = true;
            }
        }
        if (flag) {
            SoundManager.playSound(SoundConstants.SOUND_GENERATE);
            this.activateTimer(this.TIMER_DELAY);
            app.getGameManager().redrawMaze();
        }
        return this.preMoveActionHook(dirX, dirY, pz, pw);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Generate barriers again
        boolean scanResult = false;
        boolean flag = false;
        final Application app = FantastleX.getApplication();
        final int pz = app.getMazeManager().getMaze().getPlayerLocationZ();
        String mo2Name, mo4Name, mo6Name, mo8Name, invalidName, horzName,
                vertName;
        invalidName = new EmptyVoid().getName();
        horzName = new HorizontalBarrier().getName();
        vertName = new VerticalBarrier().getName();
        final AbstractMazeObject mo2 = app.getMazeManager()
                .getMazeObject(dirX - 1, dirY, pz, MazeConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = invalidName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo2Name = invalidName;
        }
        final AbstractMazeObject mo4 = app.getMazeManager().getMazeObject(dirX,
                dirY - 1, pz, MazeConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = invalidName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo4Name = invalidName;
        }
        final AbstractMazeObject mo6 = app.getMazeManager().getMazeObject(dirX,
                dirY + 1, pz, MazeConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = invalidName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo6Name = invalidName;
        }
        final AbstractMazeObject mo8 = app.getMazeManager()
                .getMazeObject(dirX + 1, dirY, pz, MazeConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = invalidName;
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            mo8Name = invalidName;
        }
        if (!mo2Name.equals(horzName)) {
            scanResult = this.scan(DirectionConstants.DIRECTION_WEST, dirX,
                    dirY, pz, this.SCAN_LIMIT, true);
            if (scanResult) {
                this.generate(DirectionConstants.DIRECTION_WEST, dirX, dirY, pz,
                        this.SCAN_LIMIT, true);
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
                this.generate(DirectionConstants.DIRECTION_EAST, dirX, dirY, pz,
                        this.SCAN_LIMIT, true);
                flag = true;
            }
        }
        if (flag) {
            SoundManager.playSound(SoundConstants.SOUND_GENERATE);
            app.getGameManager().redrawMaze();
        }
        // Activate the timer again
        this.activateTimer(this.TIMER_DELAY);
    }

    protected boolean scan(final int dir, final int x, final int y, final int z,
            final int limit, final boolean o) {
        final Application app = FantastleX.getApplication();
        final String invalidName = new EmptyVoid().getName();
        if (dir == DirectionConstants.DIRECTION_EAST) {
            for (int l = 1; l < limit; l++) {
                final AbstractMazeObject mo = app.getMazeManager()
                        .getMazeObject(x + l, y, z, MazeConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (final NullPointerException np) {
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
                    } catch (final NullPointerException np) {
                        // Do nothing
                    }
                } else {
                    if (!moName.equals(new HorizontalBarrier().getName())) {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                return true;
                            }
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_NORTH) {
            for (int l = 1; l < limit; l++) {
                final AbstractMazeObject mo = app.getMazeManager()
                        .getMazeObject(x, y - l, z, MazeConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (final NullPointerException np) {
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
                    } catch (final NullPointerException np) {
                        // Do nothing
                    }
                } else {
                    if (!moName.equals(new VerticalBarrier().getName())) {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                return true;
                            }
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_SOUTH) {
            for (int l = 1; l < limit; l++) {
                final AbstractMazeObject mo = app.getMazeManager()
                        .getMazeObject(x, y + l, z, MazeConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (final NullPointerException np) {
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
                    } catch (final NullPointerException np) {
                        // Do nothing
                    }
                } else {
                    if (!moName.equals(new VerticalBarrier().getName())) {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                return true;
                            }
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_WEST) {
            for (int l = 1; l < limit; l++) {
                final AbstractMazeObject mo = app.getMazeManager()
                        .getMazeObject(x - l, y, z, MazeConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (final NullPointerException np) {
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
                    } catch (final NullPointerException np) {
                        // Do nothing
                    }
                } else {
                    if (!moName.equals(new HorizontalBarrier().getName())) {
                        try {
                            if (mo.isOfType(TypeConstants.TYPE_GENERATOR)) {
                                return true;
                            }
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                    }
                }
            }
        }
        return false;
    }

    protected void generate(final int dir, final int x, final int y,
            final int z, final int limit, final boolean o) {
        final Application app = FantastleX.getApplication();
        final String invalidName = new EmptyVoid().getName();
        if (dir == DirectionConstants.DIRECTION_EAST) {
            for (int l = 1; l < limit; l++) {
                final AbstractMazeObject mo = app.getMazeManager()
                        .getMazeObject(x + l, y, z, MazeConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (final NullPointerException np) {
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
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getMazeManager().getMaze().setCell(
                                    new HorizontalBarrier(), x + l, y, z,
                                    MazeConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException aioob) {
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
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getMazeManager().getMaze().setCell(new Empty(),
                                    x + l, y, z, MazeConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_NORTH) {
            for (int l = 1; l < limit; l++) {
                final AbstractMazeObject mo = app.getMazeManager()
                        .getMazeObject(x, y - l, z, MazeConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (final NullPointerException np) {
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
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getMazeManager().getMaze().setCell(
                                    new VerticalBarrier(), x, y - l, z,
                                    MazeConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException aioob) {
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
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getMazeManager().getMaze().setCell(new Empty(),
                                    x, y - l, z, MazeConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_SOUTH) {
            for (int l = 1; l < limit; l++) {
                final AbstractMazeObject mo = app.getMazeManager()
                        .getMazeObject(x, y + l, z, MazeConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (final NullPointerException np) {
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
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getMazeManager().getMaze().setCell(
                                    new VerticalBarrier(), x, y + l, z,
                                    MazeConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException aioob) {
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
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getMazeManager().getMaze().setCell(new Empty(),
                                    x, y + l, z, MazeConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                }
            }
        } else if (dir == DirectionConstants.DIRECTION_WEST) {
            for (int l = 1; l < limit; l++) {
                final AbstractMazeObject mo = app.getMazeManager()
                        .getMazeObject(x - l, y, z, MazeConstants.LAYER_OBJECT);
                String moName;
                try {
                    moName = mo.getName();
                } catch (final NullPointerException np) {
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
                            app.getMazeManager().getMaze().setCell(
                                    new HorizontalBarrier(), x - l, y, z,
                                    MazeConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException aioob) {
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
                        } catch (final NullPointerException np) {
                            // Do nothing
                        }
                        try {
                            app.getMazeManager().getMaze().setCell(new Empty(),
                                    x - l, y, z, MazeConstants.LAYER_OBJECT);
                        } catch (final ArrayIndexOutOfBoundsException aioob) {
                            // Do nothing
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final MazeObjectInventory inv) {
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
            int arrowType, MazeObjectInventory inv);

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
