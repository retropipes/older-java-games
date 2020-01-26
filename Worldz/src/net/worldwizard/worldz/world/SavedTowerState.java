/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.world;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.WorldObject;

class SavedTowerState implements Cloneable {
    // Properties
    private final int r, c, f;
    private final WorldObject[][][][] saveData;

    // Constructors
    public SavedTowerState(final int rows, final int cols, final int floors) {
        this.saveData = new WorldObject[cols][rows][floors][WorldConstants.LAYER_COUNT];
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
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        copy.saveData[x][y][z][e] = this.saveData[x][y][z][e]
                                .clone();
                    }
                }
            }
        }
        return copy;
    }

    public WorldObject getDataCell(final int x, final int y, final int z,
            final int e) {
        return this.saveData[x][y][z][e];
    }

    public void setDataCell(final WorldObject newData, final int x, final int y,
            final int z, final int e) {
        this.saveData[x][y][z][e] = newData;
    }

    public void writeSavedTowerState(final DataWriter writer)
            throws IOException {
        int x, y, z, e;
        writer.writeInt(this.c);
        writer.writeInt(this.r);
        writer.writeInt(this.f);
        for (x = 0; x < this.c; x++) {
            for (y = 0; y < this.r; y++) {
                for (z = 0; z < this.f; z++) {
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        this.saveData[x][y][z][e].writeWorldObject(writer);
                    }
                }
            }
        }
    }

    public static SavedTowerState readSavedTowerState(final DataReader reader,
            final int formatVersion) throws IOException {
        int x, y, z, e, sizeX, sizeY, sizeZ;
        sizeX = reader.readInt();
        sizeY = reader.readInt();
        sizeZ = reader.readInt();
        final SavedTowerState sts = new SavedTowerState(sizeY, sizeX, sizeZ);
        for (x = 0; x < sts.c; x++) {
            for (y = 0; y < sts.r; y++) {
                for (z = 0; z < sts.f; z++) {
                    for (e = 0; e < WorldConstants.LAYER_COUNT; e++) {
                        sts.saveData[x][y][z][e] = Worldz.getApplication()
                                .getObjects()
                                .readWorldObject(reader, formatVersion);
                    }
                }
            }
        }
        return sts;
    }
}
