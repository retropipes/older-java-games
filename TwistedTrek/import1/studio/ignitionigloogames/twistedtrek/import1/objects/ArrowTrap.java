/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;

public class ArrowTrap extends GenericTrap {
    // Constructors
    public ArrowTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Arrow Trap";
    }

    @Override
    public String getPluralName() {
        return "Arrow Traps";
    }

    @Override
    public byte getObjectID() {
        return (byte) 14;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        this.playMoveSuccessSound();
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
            final int dirY, final int arrowType, final ObjectInventory inv) {
        Messager.showMessage("The arrow is stopped!");
        return false;
    }

    @Override
    public String getDescription() {
        return "Arrow Traps stop arrows.";
    }
}