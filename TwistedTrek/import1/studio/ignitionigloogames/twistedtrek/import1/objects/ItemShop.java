/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericDungeonObject;

public class ItemShop extends GenericDungeonObject {
    // Constructors
    public ItemShop() {
        super(false);
    }

    @Override
    public void postMoveActionHook() {
        Import1.getApplication().getItems().showShop();
    }

    @Override
    public String getName() {
        return "Item Shop";
    }

    @Override
    public String getPluralName() {
        return "Item Shops";
    }

    @Override
    public String getDescription() {
        return "Item Shops sell items used in battle.";
    }

    @Override
    public byte getObjectID() {
        return (byte) 0;
    }
}
