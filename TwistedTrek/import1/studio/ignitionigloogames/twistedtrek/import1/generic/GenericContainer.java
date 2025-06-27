/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import java.io.IOException;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public abstract class GenericContainer extends GenericLock {
	// Fields
	private MazeObject inside;

	// Constructors
	protected GenericContainer(final GenericSingleKey mgk) {
		super(mgk);
	}

	protected GenericContainer(final GenericSingleKey mgk, final MazeObject insideObject) {
		super(mgk);
		this.inside = insideObject;
	}

	public MazeObject getInsideObject() {
		return this.inside;
	}

	@Override
	public boolean hasAdditionalProperties() {
		return true;
	}

	@Override
	public boolean defersSetProperties() {
		return true;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final GenericContainer other = (GenericContainer) obj;
		if (this.inside != other.inside && (this.inside == null || !this.inside.equals(other.inside))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 83 * hash + (this.inside != null ? this.inside.hashCode() : 0);
		return hash;
	}

	@Override
	public GenericContainer clone() {
		final GenericContainer copy = (GenericContainer) super.clone();
		copy.inside = this.inside.clone();
		return copy;
	}

	// Scriptability
	@Override
	public byte getGroupID() {
		return (byte) 32;
	}

	@Override
	public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
		final Application app = Import1.getApplication();
		if (!this.getKey().isInfinite()) {
			inv.removeItem(this.getKey());
		}
		final int pz = app.getGameManager().getPlayerManager().getPlayerLocationZ();
		final int pw = app.getGameManager().getPlayerManager().getPlayerLocationW();
		if (this.inside != null) {
			app.getGameManager().morph(this.inside, dirX, dirY, pz, pw);
		} else {
			app.getGameManager().decay();
		}
		if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
			this.playMoveSuccessSound();
		}
		app.getGameManager().backUpPlayer();
	}

	@Override
	public void editorProbeHook() {
		if (this.inside != null) {
			Messager.showMessage(this.getName() + ": Contains " + this.inside.getName());
		} else {
			Messager.showMessage(this.getName() + ": Contains Nothing");
		}
	}

	@Override
	public abstract MazeObject editorPropertiesHook();

	@Override
	protected void setTypes() {
		this.type.set(TypeConstants.TYPE_CONTAINER);
		this.type.set(TypeConstants.TYPE_SINGLE_LOCK);
		this.type.set(TypeConstants.TYPE_LOCK);
	}

	@Override
	protected MazeObject readMazeObjectHook(final XDataReader reader, final int formatVersion) throws IOException {
		final MazeObjectList objectList = Import1.getApplication().getObjects();
		this.inside = objectList.readMazeObject(reader, formatVersion);
		return this;
	}

	@Override
	protected void writeMazeObjectHook(final XDataWriter writer) throws IOException {
		this.inside.writeMazeObject(writer);
	}

	@Override
	public int getCustomFormat() {
		return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
	}
}