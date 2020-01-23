/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericGround;

public class Ice extends GenericGround {
    public Ice() {
	super(true, true, false, false, false);
    }

    @Override
    public String getName() {
	return "Ice";
    }

    @Override
    public String getPluralName() {
	return "Squares of Ice";
    }

    @Override
    public byte getObjectID() {
	return (byte) 4;
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "walkice";
    }

    @Override
    public boolean overridesDefaultPostMove() {
	return true;
    }

    @Override
    public String getDescription() {
	return "Ice is one of the many types of ground - it is frictionless. Anything that crosses it will slide.";
    }

    @Override
    public boolean hasFrictionConditionally(final ObjectInventory inv, final boolean moving) {
	if (inv.isItemThere(new GlueBoots())) {
	    if (moving) {
		return false;
	    } else {
		return true;
	    }
	} else {
	    return false;
	}
    }
}
