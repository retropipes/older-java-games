/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWand;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class RotationWand extends GenericWand {
	// Fields
	private static final boolean CLOCKWISE = true;
	private static final boolean COUNTERCLOCKWISE = false;

	// Constructors
	public RotationWand() {
		super();
	}

	@Override
	public String getName() {
		return "Rotation Wand";
	}

	@Override
	public String getPluralName() {
		return "Rotation Wands";
	}

	@Override
	public byte getObjectID() {
		return (byte) 8;
	}

	@Override
	public void useHelper(final int x, final int y, final int z, final int w) {
		this.useAction(null, x, y, z, w);
	}

	@Override
	public void useAction(final MazeObject mo, final int x, final int y, final int z, final int w) {
		final Application app = Import1.getApplication();
		app.getGameManager().setRemoteAction(x, y, z, w);
		int r = 1;
		final String[] rChoices = new String[] { "1", "2", "3" };
		final String rres = Messager.showInputDialog("Rotation Radius:", "Import1", rChoices, rChoices[r - 1]);
		try {
			r = Integer.parseInt(rres);
		} catch (final NumberFormatException nf) {
			// Ignore
		}
		boolean d = RotationWand.CLOCKWISE;
		int di;
		if (d) {
			di = 0;
		} else {
			di = 1;
		}
		final String[] dChoices = new String[] { "Clockwise", "Counterclockwise" };
		final String dres = Messager.showInputDialog("Rotation Direction:", "Import1", dChoices, dChoices[di]);
		if (dres.equals(dChoices[0])) {
			d = RotationWand.CLOCKWISE;
		} else {
			d = RotationWand.COUNTERCLOCKWISE;
		}
		if (d) {
			Import1.getApplication().getGameManager().doClockwiseRotate(r);
		} else {
			Import1.getApplication().getGameManager().doCounterclockwiseRotate(r);
		}
		if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
			MazeObject.playRotatedSound();
		}
	}

	@Override
	public String getUseSoundName() {
		return "rotated";
	}

	@Override
	public String getDescription() {
		return "Rotation Wands will rotate part of the maze. You can choose the area of effect and the direction.";
	}
}
