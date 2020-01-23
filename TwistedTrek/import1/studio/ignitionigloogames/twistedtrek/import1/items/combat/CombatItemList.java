/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.items.combat;

public class CombatItemList {
    // Fields
    private final CombatUsableItem[] allItems = { new Bomb(), new Rope() };

    // Methods
    public CombatUsableItem[] getAllItems() {
	return this.allItems;
    }

    // Methods
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
