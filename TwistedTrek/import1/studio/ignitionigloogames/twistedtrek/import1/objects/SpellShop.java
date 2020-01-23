/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericDungeonObject;

public class SpellShop extends GenericDungeonObject {
    // Constructors
    public SpellShop() {
	super(false);
    }

    @Override
    public void postMoveActionHook() {
	Import1.getApplication().getSpells().showShop();
    }

    @Override
    public String getName() {
	return "Spell Shop";
    }

    @Override
    public String getPluralName() {
	return "Spell Shops";
    }

    @Override
    public String getDescription() {
	return "Spell Shops teach spells, for a fee.";
    }

    @Override
    public byte getObjectID() {
	return (byte) 6;
    }
}
