/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWand;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class RemoteActionWand extends GenericWand {
    // Constructors
    public RemoteActionWand() {
	super();
    }

    @Override
    public String getName() {
	return "Remote Action Wand";
    }

    @Override
    public String getPluralName() {
	return "Remote Action Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
	this.useAction(null, x, y, z, w);
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y, final int z, final int w) {
	final Application app = Import1.getApplication();
	app.getGameManager().doRemoteAction(x, y, z, w);
    }

    @Override
    public byte getObjectID() {
	return (byte) 7;
    }

    @Override
    public String getDescription() {
	return "Remote Action Wands will act on the target object as if you were there, on top of it.";
    }
}
