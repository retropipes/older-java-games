/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map;

import java.io.IOException;

import com.puttysoftware.gemma.support.map.generic.MapObject;
import com.puttysoftware.gemma.support.map.generic.MapObjectList;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class SavedTowerState implements Cloneable {
    // Properties
    private final int r, c, f;
    private final MapObject[][][][] saveData;

    // Constructors
    public SavedTowerState(final int rows, final int cols, final int floors) {
        this.saveData = new MapObject[cols][rows][floors][MapConstants.LAYER_COUNT];
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
                    for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
                        copy.saveData[x][y][z][e] = this.saveData[x][y][z][e]
                                .clone();
                    }
                }
            }
        }
        return copy;
    }

    public MapObject getDataCell(final int x, final int y, final int z,
            final int e) {
        return this.saveData[x][y][z][e];
    }

    public void setDataCell(final MapObject newData, final int x, final int y,
            final int z, final int e) {
        this.saveData[x][y][z][e] = newData;
    }

    public void writeSavedTowerStateX(final XDataWriter writer)
            throws IOException {
        int x, y, z, e;
        writer.writeInt(this.c);
        writer.writeInt(this.r);
        writer.writeInt(this.f);
        for (x = 0; x < this.c; x++) {
            for (y = 0; y < this.r; y++) {
                for (z = 0; z < this.f; z++) {
                    for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
                        this.saveData[x][y][z][e].writeMapObject(writer);
                    }
                }
            }
        }
    }

    public static SavedTowerState readSavedTowerStateX(final XDataReader reader,
            final int formatVersion) throws IOException {
        final MapObjectList objects = new MapObjectList();
        int x, y, z, e, sizeX, sizeY, sizeZ;
        sizeX = reader.readInt();
        sizeY = reader.readInt();
        sizeZ = reader.readInt();
        final SavedTowerState sts = new SavedTowerState(sizeY, sizeX, sizeZ);
        for (x = 0; x < sts.c; x++) {
            for (y = 0; y < sts.r; y++) {
                for (z = 0; z < sts.f; z++) {
                    for (e = 0; e < MapConstants.LAYER_COUNT; e++) {
                        sts.saveData[x][y][z][e] = objects
                                .readMapObjectX(reader, formatVersion);
                    }
                }
            }
        }
        return sts;
    }
}
