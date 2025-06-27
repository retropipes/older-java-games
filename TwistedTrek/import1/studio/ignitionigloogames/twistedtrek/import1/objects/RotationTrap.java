/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import java.io.IOException;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public class RotationTrap extends GenericTrap {
	// Fields
	private int radius;
	private boolean direction;
	private static final boolean CLOCKWISE = true;
	private static final boolean COUNTERCLOCKWISE = false;

	// Constructors
	public RotationTrap() {
		super();
		this.radius = 1;
		this.direction = RotationTrap.CLOCKWISE;
	}

	public RotationTrap(final int newRadius, final boolean newDirection) {
		super();
		this.radius = newRadius;
		this.direction = newDirection;
	}

	@Override
	public RotationTrap clone() {
		final RotationTrap copy = new RotationTrap();
		copy.radius = this.radius;
		copy.direction = this.direction;
		return copy;
	}

	@Override
	public void editorProbeHook() {
		String dir;
		if (this.direction == RotationTrap.CLOCKWISE) {
			dir = "Clockwise";
		} else {
			dir = "Counterclockwise";
		}
		Messager.showMessage(this.getName() + " (Radius " + this.radius + ", Direction " + dir + ")");
	}

	@Override
	public MazeObject editorPropertiesHook() {
		int r = this.radius;
		final String[] rChoices = new String[] { "1", "2", "3" };
		final String rres = Messager.showInputDialog("Rotation Radius:", "Editor", rChoices, rChoices[r - 1]);
		try {
			r = Integer.parseInt(rres);
		} catch (final NumberFormatException nf) {
			// Ignore
		}
		boolean d = this.direction;
		int di;
		if (d) {
			di = 0;
		} else {
			di = 1;
		}
		final String[] dChoices = new String[] { "Clockwise", "Counterclockwise" };
		final String dres = Messager.showInputDialog("Rotation Direction:", "Editor", dChoices, dChoices[di]);
		if (dres.equals(dChoices[0])) {
			d = RotationTrap.CLOCKWISE;
		} else {
			d = RotationTrap.COUNTERCLOCKWISE;
		}
		return new RotationTrap(r, d);
	}

	@Override
	public boolean hasAdditionalProperties() {
		return true;
	}

	@Override
	public String getName() {
		return "Rotation Trap";
	}

	@Override
	public String getPluralName() {
		return "Rotation Traps";
	}

	@Override
	public byte getObjectID() {
		return (byte) 8;
	}

	@Override
	protected MazeObject readMazeObjectHook(final XDataReader reader, final int formatVersion) throws IOException {
		this.radius = reader.readInt();
		this.direction = reader.readBoolean();
		return this;
	}

	@Override
	protected void writeMazeObjectHook(final XDataWriter writer) throws IOException {
		writer.writeInt(this.radius);
		writer.writeBoolean(this.direction);
	}

	@Override
	public int getCustomFormat() {
		return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
	}

	@Override
	public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
		if (this.direction) {
			Import1.getApplication().getGameManager().doClockwiseRotate(this.radius);
		} else {
			Import1.getApplication().getGameManager().doCounterclockwiseRotate(this.radius);
		}
		if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
			MazeObject.playRotatedSound();
		}
	}

	@Override
	public String getDescription() {
		return "Rotation Traps rotate part of the maze when stepped on.";
	}
}
