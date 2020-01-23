/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.items.combat;

import com.puttysoftware.fantastlex.items.combat.predefined.Bomb;
import com.puttysoftware.fantastlex.items.combat.predefined.Rope;

public class CombatItemList {
    // Fields
    private final CombatItem[] allItems;

    // Constructor
    public CombatItemList() {
        this.allItems = new CombatItem[] { new Bomb(), new Rope() };
    }

    // Methods
    public CombatItem[] getAllItems() {
        return this.allItems;
    }

    public String[] getAllNames() {
        final String[] allNames = new String[this.allItems.length];
        for (int x = 0; x < this.allItems.length; x++) {
            allNames[x] = this.allItems[x].getName();
        }
        return allNames;
    }

    CombatItem getItemByName(final String name) {
        for (final CombatItem allItem : this.allItems) {
            if (name.equals(allItem.getName())) {
                return allItem;
            }
        }
        return null;
    }
}
