/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericDungeonObject;

public class ArmorShop extends GenericDungeonObject {
    // Constructors
    public ArmorShop() {
        super(false);
    }

    @Override
    public void postMoveActionHook() {
        Import1.getApplication().getArmor().showShop();
    }

    @Override
    public String getName() {
        return "Armor Shop";
    }

    @Override
    public String getPluralName() {
        return "Armor Shops";
    }

    @Override
    public String getDescription() {
        return "Armor Shops sell protective armor.";
    }

    @Override
    public byte getObjectID() {
        return (byte) 1;
    }
}
