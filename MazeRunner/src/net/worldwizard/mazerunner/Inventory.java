package net.worldwizard.mazerunner;

import java.io.Serializable;

public class Inventory implements Serializable {
    // Properties
    private final String[] nameList;
    private final int[] contents;
    private final int[][] uses;
    private final int MAX_INVENTORY;
    private final int MAX_QUANTITY = 100;
    // Serialization
    private static final long serialVersionUID = 9000L;

    // Constructors
    public Inventory() {
        final MazeObjectList list = new MazeObjectList();
        this.nameList = list.getAllInventoryableNames();
        this.contents = new int[this.nameList.length];
        this.uses = new int[this.nameList.length][this.MAX_QUANTITY];
        this.MAX_INVENTORY = this.nameList.length;
    }

    // Accessors
    public int getItemCount(final MazeObject mo) {
        final int loc = this.indexOf(mo);
        return this.contents[loc];
    }

    public int getUses(final MazeObject mo) {
        final int loc = this.indexOf(mo);
        return this.uses[loc][this.contents[loc]];
    }

    private void setUses(final MazeObject mo, final int newUses) {
        final int loc = this.indexOf(mo);
        this.uses[loc][this.contents[loc]] = newUses;
    }

    public void use(final MazeObject mo, final int x, final int y, final int z,
            final int w) {
        int newUses = this.getUses(mo);
        if (mo.isUsable() && newUses > 0) {
            newUses--;
            this.setUses(mo, newUses);
            mo.useHelper(x, y, z, w);
            if (newUses == 0) {
                this.removeItem(mo);
            }
        }
    }

    public boolean isItemThere(final MazeObject mo) {
        final int loc = this.indexOf(mo);
        if (loc != -1) {
            if (this.contents[loc] != 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int getMaximum() {
        return this.MAX_INVENTORY;
    }

    public int getMaximumQuantity() {
        return this.MAX_QUANTITY;
    }

    // Transformers
    public void addItem(final MazeObject mo) {
        final int loc = this.indexOf(mo);
        if (this.contents[loc] < this.MAX_QUANTITY) {
            this.contents[loc]++;
            this.uses[loc][this.contents[loc]] = mo.getUses();
        }
    }

    public void removeItem(final MazeObject mo) {
        final int loc = this.indexOf(mo);
        if (this.contents[loc] != 0) {
            this.contents[loc]--;
        }
    }

    @Override
    public String toString() {
        int x, y;
        String result = "INVENTORY\n";
        for (x = 0; x < this.contents.length; x++) {
            result += Integer.valueOf(this.contents[x]) + "\n";
            for (y = 0; y < this.uses[x].length; y++) {
                result += Integer.valueOf(this.uses[x][y]) + "\n";
            }
        }
        return result;
    }

    // Helper methods
    private int indexOf(final MazeObject mo) {
        int x;
        for (x = 0; x < this.contents.length; x++) {
            if (mo.getName().equals(this.nameList[x])) {
                return x;
            }
        }
        return -1;
    }

    public void reconstruct(final int index, final int value,
            final int useIndex, final int useValue) {
        this.contents[index] = value;
        this.uses[index][useIndex] = useValue;
    }
}