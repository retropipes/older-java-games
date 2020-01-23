/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.items.combat;

import studio.ignitionigloogames.twistedtrek.import1.effects.Effect;
import studio.ignitionigloogames.twistedtrek.import1.items.Item;
import studio.ignitionigloogames.twistedtrek.import1.items.ItemCategoryConstants;

public abstract class CombatUsableItem extends Item {
    // Fields
    private final char target;
    protected Effect e;
    protected String sound;

    // Constructors
    public CombatUsableItem(final String itemName, final int itemBuyPrice, final char itemTarget) {
	super(itemName, ItemCategoryConstants.ITEM_CATEGORY_USABLE, 1, 0);
	this.setCombatUsable(true);
	this.setBuyPrice(itemBuyPrice);
	this.target = itemTarget;
	this.defineFields();
    }

    // Methods
    public char getTarget() {
	return this.target;
    }

    public Effect getEffect() {
	return this.e;
    }

    public String getSound() {
	return this.sound;
    }

    protected abstract void defineFields();
}