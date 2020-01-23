/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericDungeonObject;

public class Healer extends GenericDungeonObject {
    // Constructors
    public Healer() {
	super(false);
    }

    @Override
    public void postMoveActionHook() {
	Import1.getApplication().getHealer().showShop();
    }

    @Override
    public String getName() {
	return "Healer";
    }

    @Override
    public String getPluralName() {
	return "Healers";
    }

    @Override
    public String getDescription() {
	return "Healers restore health, for a fee.";
    }

    @Override
    public byte getObjectID() {
	return (byte) 3;
    }
}
