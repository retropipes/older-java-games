/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericDungeonObject;

public class Bank extends GenericDungeonObject {
    // Constructors
    public Bank() {
        super(false);
    }

    @Override
    public void postMoveActionHook() {
        Import1.getApplication().getBank().showShop();
    }

    @Override
    public String getName() {
        return "Bank";
    }

    @Override
    public String getPluralName() {
        return "Banks";
    }

    @Override
    public String getDescription() {
        return "Banks store money for safe keeping.";
    }

    @Override
    public byte getObjectID() {
        return (byte) 2;
    }
}
