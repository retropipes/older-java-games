/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPassThroughObject;

public class Door extends GenericPassThroughObject {
    // Constructors
    public Door() {
        super();
    }

    // Scriptability
    @Override
    public String getName() {
        return "Door";
    }

    @Override
    public String getPluralName() {
        return "Doors";
    }

    @Override
    public byte getObjectID() {
        return (byte) 3;
    }

    @Override
    public String getDescription() {
        return "Doors are purely decorative, but they do stop arrows from passing through.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
            final int dirY, final int arrowType, final ObjectInventory inv) {
        return false;
    }
}