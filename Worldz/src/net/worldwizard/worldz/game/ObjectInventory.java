/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.game;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericBoots;
import net.worldwizard.worldz.generic.TypeConstants;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.generic.WorldObjectList;
import net.worldwizard.worldz.objects.RegularBoots;

public final class ObjectInventory implements Cloneable {
    // Properties
    private final String[] nameList;
    private final int[] contents;
    private final int[][] uses;
    private GenericBoots boots;
    private static final int MAX_QUANTITY = 100;
    private static final GenericBoots NULL_INSTANCE = new RegularBoots();

    // Constructors
    public ObjectInventory() {
        final WorldObjectList list = Worldz.getApplication().getObjects();
        this.nameList = list.getAllInventoryableNamesMinusBoots();
        this.contents = new int[this.nameList.length];
        this.uses = new int[this.nameList.length][ObjectInventory.MAX_QUANTITY];
        this.boots = ObjectInventory.NULL_INSTANCE;
    }

    // Accessors
    public int getItemCount(final WorldObject mo) {
        if (ObjectInventory.isBoots(mo)) {
            return this.getBootsCount();
        } else {
            return this.getNonBootsCount(mo);
        }
    }

    public int getUses(final WorldObject mo) {
        if (ObjectInventory.isBoots(mo)) {
            return 0;
        } else {
            return this.getNonBootsUses(mo);
        }
    }

    private void setUses(final WorldObject mo, final int newUses) {
        if (!ObjectInventory.isBoots(mo)) {
            this.setNonBootsUses(mo, newUses);
        }
    }

    public void use(final WorldObject mo, final int x, final int y, final int z) {
        int tempUses = this.getUses(mo);
        if (mo.isUsable() && tempUses > 0) {
            tempUses--;
            this.setUses(mo, tempUses);
            mo.useHelper(x, y, z);
            if (tempUses == 0) {
                this.removeItem(mo);
            }
        }
    }

    public boolean isItemThere(final WorldObject mo) {
        if (ObjectInventory.isBoots(mo)) {
            return this.areBootsThere(mo);
        } else {
            return this.areNonBootsThere(mo);
        }
    }

    public boolean isItemCategoryThere(final int cat) {
        final WorldObjectList list = Worldz.getApplication().getObjects();
        final WorldObject[] objects = list
                .getAllInventoryableObjectsMinusBoots();
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(cat) && this.contents[x] > 0) {
                return true;
            }
        }
        return false;
    }

    // Transformers
    void fireStepActions() {
        if (!this.boots.equals(ObjectInventory.NULL_INSTANCE)) {
            this.boots.stepAction();
        }
    }

    public void addItem(final WorldObject mo) {
        if (ObjectInventory.isBoots(mo)) {
            this.addBoots(mo);
        } else {
            this.addNonBoots(mo);
        }
    }

    public void removeItem(final WorldObject mo) {
        if (ObjectInventory.isBoots(mo)) {
            this.removeBoots();
        } else {
            this.removeNonBoots(mo);
        }
    }

    public void removeAllBoots() {
        this.removeBoots();
    }

    public String[] generateInventoryStringArray() {
        final String[] result = new String[this.contents.length + 1];
        final StringBuilder[] sb = new StringBuilder[this.contents.length + 1];
        for (int x = 0; x < this.contents.length; x++) {
            sb[x] = new StringBuilder();
            sb[x].append("Slot ");
            sb[x].append(x + 1);
            sb[x].append(": ");
            sb[x].append(this.nameList[x]);
            sb[x].append(" (Qty: ");
            sb[x].append(this.contents[x]);
            sb[x].append(", Uses: ");
            sb[x].append(this.uses[x][this.contents[x]]);
            sb[x].append(")");
            result[x] = sb[x].toString();
        }
        sb[this.contents.length] = new StringBuilder();
        sb[this.contents.length].append("Slot ");
        sb[this.contents.length].append(this.contents.length + 1);
        sb[this.contents.length].append(": ");
        sb[this.contents.length].append(this.boots.getName());
        sb[this.contents.length].append(" (Qty 1, Uses 0)");
        result[this.contents.length] = sb[this.contents.length].toString();
        return result;
    }

    public String[] generateUseStringArray() {
        final WorldObjectList list = Worldz.getApplication().getObjects();
        final String[] names = list.getAllUsableNames();
        final int len = names.length;
        final StringBuilder[] sb = new StringBuilder[len];
        final String[] result = new String[len];
        for (int x = 0; x < len; x++) {
            final int index = this.indexByName(names[x]);
            sb[x] = new StringBuilder();
            sb[x].append(names[x]);
            sb[x].append(" (Qty: ");
            sb[x].append(this.contents[index]);
            sb[x].append(", Uses: ");
            sb[x].append(this.uses[index][this.contents[index]]);
            sb[x].append(")");
            result[x] = sb[x].toString();
        }
        return result;
    }

    // Helper methods
    private int getBootsCount() {
        if (!this.boots.equals(ObjectInventory.NULL_INSTANCE)) {
            return 1;
        } else {
            return 0;
        }
    }

    private int getNonBootsCount(final WorldObject mo) {
        final int loc = this.indexOf(mo);
        return this.contents[loc];
    }

    private int getNonBootsUses(final WorldObject mo) {
        final int loc = this.indexOf(mo);
        return this.uses[loc][this.contents[loc]];
    }

    private void setNonBootsUses(final WorldObject mo, final int newUses) {
        final int loc = this.indexOf(mo);
        this.uses[loc][this.contents[loc]] = newUses;
    }

    private boolean areNonBootsThere(final WorldObject mo) {
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

    private boolean areBootsThere(final WorldObject mo) {
        if (this.boots != ObjectInventory.NULL_INSTANCE) {
            if (this.boots.getName().equals(mo.getName())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void addBoots(final WorldObject mo) {
        this.boots = (GenericBoots) mo;
    }

    private void addNonBoots(final WorldObject mo) {
        final int loc = this.indexOf(mo);
        if (this.contents[loc] < ObjectInventory.MAX_QUANTITY) {
            this.contents[loc]++;
            this.uses[loc][this.contents[loc]] = mo.getUses();
        }
    }

    private void removeBoots() {
        this.boots = new RegularBoots();
    }

    private void removeNonBoots(final WorldObject mo) {
        final int loc = this.indexOf(mo);
        if (this.contents[loc] != 0) {
            this.contents[loc]--;
        }
    }

    private static boolean isBoots(final WorldObject mo) {
        if (mo.isOfType(TypeConstants.TYPE_BOOTS)) {
            return true;
        } else {
            return false;
        }
    }

    private int indexOf(final WorldObject mo) {
        int x;
        for (x = 0; x < this.contents.length; x++) {
            if (mo.getName().equals(this.nameList[x])) {
                return x;
            }
        }
        return -1;
    }

    private int indexByName(final String name) {
        int x;
        for (x = 0; x < this.contents.length; x++) {
            if (name.equals(this.nameList[x])) {
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
        for (int x = 0; x < this.contents.length; x++) {
            for (int y = 0; y < ObjectInventory.MAX_QUANTITY; y++) {
                clone.uses[x][y] = this.uses[x][y];
            }
        }
        clone.boots = this.boots;
        return clone;
    }

    public static ObjectInventory readInventory(final DataReader reader,
            final int formatVersion) throws IOException {
        final WorldObjectList objects = Worldz.getApplication().getObjects();
        final ObjectInventory i = new ObjectInventory();
        i.boots = (GenericBoots) objects.readWorldObject(reader, formatVersion);
        if (i.boots == null) {
            i.boots = ObjectInventory.NULL_INSTANCE;
        }
        for (int x = 0; x < i.contents.length; x++) {
            i.contents[x] = reader.readInt();
        }
        for (int x = 0; x < i.contents.length; x++) {
            for (int y = 0; y < ObjectInventory.MAX_QUANTITY; y++) {
                i.uses[x][y] = reader.readInt();
            }
        }
        return i;
    }

    public void writeInventory(final DataWriter writer) throws IOException {
        this.boots.writeWorldObject(writer);
        for (final int content : this.contents) {
            writer.writeInt(content);
        }
        for (int x = 0; x < this.contents.length; x++) {
            for (int y = 0; y < ObjectInventory.MAX_QUANTITY; y++) {
                writer.writeInt(this.uses[x][y]);
            }
        }
    }
}
