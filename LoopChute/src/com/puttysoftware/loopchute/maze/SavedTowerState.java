/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.maze;

import java.io.IOException;

import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class SavedTowerState implements Cloneable {
    // Properties
    private final int r, c, f;
    private final MazeObject[][][][] saveData;

    // Constructors
    public SavedTowerState(final int rows, final int cols, final int floors) {
        this.saveData = new MazeObject[cols][rows][floors][MazeConstants.LAYER_COUNT];
        this.c = cols;
        this.r = rows;
        this.f = floors;
    }

    @Override
    public SavedTowerState clone() {
        final SavedTowerState copy = new SavedTowerState(this.r, this.c,
                this.f);
        int x, y, z, e;
        for (x = 0; x < this.c; x++) {
            for (y = 0; y < this.r; y++) {
                for (z = 0; z < this.f; z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        copy.saveData[x][y][z][e] = this.saveData[x][y][z][e]
                                .clone();
                    }
                }
            }
        }
        return copy;
    }

    public MazeObject getDataCell(final int x, final int y, final int z,
            final int e) {
        return this.saveData[x][y][z][e];
    }

    public void setDataCell(final MazeObject newData, final int x, final int y,
            final int z, final int e) {
        this.saveData[x][y][z][e] = newData;
    }

    public void writeSavedTowerState(final XDataWriter writer)
            throws IOException {
        int x, y, z, e;
        writer.writeInt(this.c);
        writer.writeInt(this.r);
        writer.writeInt(this.f);
        for (x = 0; x < this.c; x++) {
            for (y = 0; y < this.r; y++) {
                for (z = 0; z < this.f; z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        this.saveData[x][y][z][e].writeMazeObject(writer);
                    }
                }
            }
        }
    }

    public static SavedTowerState readSavedTowerState(final XDataReader reader,
            final int formatVersion) throws IOException {
        int x, y, z, e, sizeX, sizeY, sizeZ;
        sizeX = reader.readInt();
        sizeY = reader.readInt();
        sizeZ = reader.readInt();
        final SavedTowerState sts = new SavedTowerState(sizeY, sizeX, sizeZ);
        for (x = 0; x < sts.c; x++) {
            for (y = 0; y < sts.r; y++) {
                for (z = 0; z < sts.f; z++) {
                    for (e = 0; e < MazeConstants.LAYER_COUNT; e++) {
                        sts.saveData[x][y][z][e] = LoopChute.getApplication()
                                .getObjects()
                                .readMazeObject(reader, formatVersion);
                    }
                }
            }
        }
        return sts;
    }
}
