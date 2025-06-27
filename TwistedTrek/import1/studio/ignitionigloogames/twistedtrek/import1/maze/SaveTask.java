/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.maze;

import java.io.IOException;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.DataConstants;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.xio.XDataWriter;

public class SaveTask extends Thread {
	// Fields
	private String filename;
	private final boolean isSavedGame;
	private static final byte FORMAT_VERSION_MAJOR = (byte) 5;
	private static final byte FORMAT_VERSION_MINOR = (byte) 0;

	// Constructors
	public SaveTask(final String file, final boolean saved) {
		this.filename = file;
		this.isSavedGame = saved;
	}

	@Override
	public void run() {
		final Application app = Import1.getApplication();
		boolean success = true;
		final String sg;
		if (this.isSavedGame) {
			sg = "Saved Game";
		} else {
			sg = "Maze";
		}
		// filename check
		final boolean hasExtension = SaveTask.hasExtension(this.filename);
		if (!hasExtension) {
			if (this.isSavedGame) {
				this.filename += Extension.getGameExtensionWithPeriod();
			} else {
				this.filename += Extension.getMaze5ExtensionWithPeriod();
			}
		}
		try (final XDataWriter mazeFile = new XDataWriter(this.filename, DataConstants.DATA_MODE_BINARY)) {
			SaveTask.writeFormatVersion(mazeFile);
			app.getMazeManager().getMaze().writeMaze(mazeFile);
			if (this.isSavedGame) {
				app.getGameManager().saveGameHook(mazeFile);
				PCManager.saveGameHook(mazeFile);
			} else {
				app.getGameManager().validateScore();
			}
			mazeFile.close();
		} catch (final Exception ex) {
			Messager.showDialog("Unknown error writing " + sg.toLowerCase() + " file.");
			success = false;
		}
		if (success) {
			Messager.showMessage(sg + " file saved.");
		}
		app.getMazeManager().handleDeferredSuccess(success);
	}

	private static void writeFormatVersion(final XDataWriter mazeFile) throws IOException {
		mazeFile.writeByte(SaveTask.FORMAT_VERSION_MAJOR);
		mazeFile.writeByte(SaveTask.FORMAT_VERSION_MINOR);
	}

	private static boolean hasExtension(final String s) {
		String ext = null;
		final int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		if (ext == null) {
			return false;
		} else {
			return true;
		}
	}
}