/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.items.combat;

import net.worldwizard.support.SystemLoader;

public class CombatItemList {
    // Fields
    private final CombatUsableItem[] allItems;

    // Constructor
    public CombatItemList() {
        this.allItems = SystemLoader.loadAllItems();
    }

    // Methods
    public CombatUsableItem[] getAllItems() {
        return this.allItems;
    }

    public String[] getAllNames() {
        final String[] allNames = new String[this.allItems.length];
        for (int x = 0; x < this.allItems.length; x++) {
            allNames[x] = this.allItems[x].getName();
        }
        return allNames;
    }

    public int[] getAllInitialUses() {
        final int[] allUses = new int[this.allItems.length];
        for (int x = 0; x < this.allItems.length; x++) {
            allUses[x] = this.allItems[x].getInitialUses();
        }
        return allUses;
    }

    public CombatUsableItem getItemByName(final String name) {
        for (final CombatUsableItem allItem : this.allItems) {
            if (name.equals(allItem.getName())) {
                return allItem;
            }
        }
        return null;
    }
}
