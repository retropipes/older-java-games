/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items.combat;

import com.puttysoftware.gemma.support.items.combat.predefined.Bomb;
import com.puttysoftware.gemma.support.items.combat.predefined.Rope;

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
        for (int x = 0; x < this.allItems.length; x++) {
            if (name.equals(this.allItems[x].getName())) {
                return this.allItems[x];
            }
        }
        return null;
    }
}
