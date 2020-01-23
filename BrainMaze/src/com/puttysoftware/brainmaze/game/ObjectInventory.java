/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.game;

import java.io.IOException;

import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.generic.GenericBoots;
import com.puttysoftware.brainmaze.generic.MazeObject;
import com.puttysoftware.brainmaze.generic.MazeObjectList;
import com.puttysoftware.brainmaze.generic.TypeConstants;
import com.puttysoftware.brainmaze.objects.RegularBoots;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public final class ObjectInventory implements Cloneable {
    // Properties
    private final String[] nameList;
    private final int[][] typeList;
    private final int[] contents;
    private GenericBoots boots;
    private static final GenericBoots DEFAULT_BOOTS = new RegularBoots();

    // Constructors
    public ObjectInventory() {
        final MazeObjectList list = BrainMaze.getApplication().getObjects();
        final MazeObject[] invobj = list
                .getAllInventoryableObjectsMinusSpecial();
        this.nameList = list.getAllInventoryableNamesMinusSpecial();
        this.contents = new int[this.nameList.length];
        this.typeList = new int[this.nameList.length][];
        for (int x = 0; x < this.nameList.length; x++) {
            this.typeList[x] = invobj[x].getAllTypes();
        }
        this.boots = ObjectInventory.DEFAULT_BOOTS;
    }

    // Accessors
    public int getItemCount(final MazeObject mo) {
        if (ObjectInventory.isBoots(mo)) {
            return this.getBootsCount();
        } else {
            return this.getOtherCount(mo);
        }
    }

    public boolean isItemThere(final MazeObject mo) {
        if (ObjectInventory.isBoots(mo)) {
            return this.areBootsThere(mo);
        } else {
            return this.isOtherThere(mo);
        }
    }

    // Transformers
    void fireStepActions() {
        if (!this.boots.getName().equals(
                ObjectInventory.DEFAULT_BOOTS.getName())) {
            this.boots.stepAction();
        }
    }

    public void addItem(final MazeObject mo) {
        if (ObjectInventory.isBoots(mo)) {
            this.addBoots((GenericBoots) mo);
        } else {
            this.addOther(mo);
        }
    }

    public void removeItem(final MazeObject mo) {
        if (ObjectInventory.isBoots(mo)) {
            this.removeBoots();
        } else {
            this.removeOther(mo);
        }
    }

    public void removeAllItemsOfType(final int type) {
        for (int x = 0; x < this.typeList.length; x++) {
            for (int y = 0; y < this.typeList[x].length; y++) {
                if (this.typeList[x][y] == type) {
                    this.contents[x] = 0;
                }
            }
        }
    }

    public void removeAllBoots() {
        this.removeBoots();
    }

    public String[] generateInventoryStringArray() {
        final String[] result = new String[this.contents.length + 1];
        StringBuilder sb;
        for (int x = 0; x < this.contents.length; x++) {
            sb = new StringBuilder();
            sb.append("Slot ");
            sb.append(x + 1);
            sb.append(": ");
            sb.append(this.nameList[x]);
            sb.append(" (Qty: ");
            sb.append(this.contents[x]);
            sb.append(")");
            result[x] = sb.toString();
        }
        sb = new StringBuilder();
        sb.append("Slot ");
        sb.append(this.contents.length + 1);
        sb.append(": ");
        sb.append(this.boots.getName());
        sb.append(" (Qty: 1)");
        result[this.contents.length] = sb.toString();
        return result;
    }

    // Helper methods
    private int getBootsCount() {
        if (!this.boots.equals(ObjectInventory.DEFAULT_BOOTS)) {
            return 1;
        } else {
            return 0;
        }
    }

    private int getOtherCount(final MazeObject mo) {
        final int loc = this.indexOf(mo);
        return this.contents[loc];
    }

    private boolean isOtherThere(final MazeObject mo) {
        final int loc = this.indexOf(mo);
        if (loc != -1) {
            return this.contents[loc] != 0;
        } else {
            return false;
        }
    }

    private boolean areBootsThere(final MazeObject mo) {
        return this.boots.getName().equals(mo.getName());
    }

    private void addBoots(final GenericBoots mo) {
        this.boots = mo;
    }

    private void addOther(final MazeObject mo) {
        final int loc = this.indexOf(mo);
        this.contents[loc]++;
    }

    private void removeBoots() {
        this.boots = ObjectInventory.DEFAULT_BOOTS;
    }

    private void removeOther(final MazeObject mo) {
        final int loc = this.indexOf(mo);
        if (this.contents[loc] != 0) {
            this.contents[loc]--;
        }
    }

    private static boolean isBoots(final MazeObject mo) {
        return mo.isOfType(TypeConstants.TYPE_BOOTS);
    }

    private int indexOf(final MazeObject mo) {
        int x;
        for (x = 0; x < this.contents.length; x++) {
            if (mo.getName().equals(this.nameList[x])) {
                return x;
            }
        }
        return -1;
    }

    @Override
    public ObjectInventory clone() {
        final ObjectInventory clone = new ObjectInventory();
        for (int x = 0; x < this.contents.length; x++) {
            clone.contents[x] = this.contents[x];
        }
        clone.boots = this.boots;
        return clone;
    }

    public static ObjectInventory readInventory(final XDataReader reader,
            final int formatVersion) throws IOException {
        final MazeObjectList objects = BrainMaze.getApplication().getObjects();
        final ObjectInventory i = new ObjectInventory();
        i.boots = (GenericBoots) objects.readMazeObject(reader, formatVersion);
        if (i.boots == null) {
            i.boots = ObjectInventory.DEFAULT_BOOTS;
        }
        for (int x = 0; x < i.contents.length; x++) {
            i.contents[x] = reader.readInt();
        }
        return i;
    }

    public void writeInventory(final XDataWriter writer) throws IOException {
        this.boots.writeMazeObject(writer);
        for (final int content : this.contents) {
            writer.writeInt(content);
        }
    }
}
