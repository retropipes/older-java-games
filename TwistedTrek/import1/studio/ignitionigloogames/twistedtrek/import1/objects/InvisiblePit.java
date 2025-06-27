/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;

public class InvisiblePit extends Pit {
    // Constructors
    public InvisiblePit() {
        super();
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        Messager.showMessage("Some unseen force prevents movement that way...");
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        final Application app = Import1.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        Messager.showMessage("Invisible Pit!");
    }

    @Override
    public String getName() {
        return "Invisible Pit";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invislble Pits";
    }

    @Override
    public byte getObjectID() {
        return (byte) 11;
    }

    @Override
    public String getDescription() {
        return "Invisible Pits dump anything that wanders in to the floor below. If one of these is placed on the bottom-most floor, it is impassable.";
    }
}
