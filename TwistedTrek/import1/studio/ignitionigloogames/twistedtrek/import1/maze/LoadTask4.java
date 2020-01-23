/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.maze;

import java.io.IOException;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.DataConstants;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.xio.XDataReader;

public class LoadTask4 extends Thread {
    // Fields
    private Maze gameMaze;
    private final String filename;
    private static final byte FORMAT_VERSION_MAJOR = (byte) 4;
    private static final byte FORMAT_VERSION_MINOR = (byte) 0;

    // Constructors
    public LoadTask4(final String file) {
	this.filename = file;
    }

    // Methods
    @Override
    public void run() {
	final Application app = Import1.getApplication();
	int startW;
	String sg;
	app.getGameManager().setSavedGameFlag(false);
	sg = "Maze";
	try (final XDataReader mazeFile = new XDataReader(this.filename, DataConstants.DATA_MODE_BINARY)) {
	    try {
		final boolean supported = LoadTask4.checkFormatVersion(LoadTask4.readFormatVersion(mazeFile));
		if (!supported) {
		    throw new InvalidMazeException("Unsupported maze format version.");
		}
		if (this.gameMaze == null) {
		    this.gameMaze = new Maze5();
		}
		this.gameMaze = this.gameMaze.readMaze(mazeFile, FormatConstants.MAZE_FORMAT_4);
		if (this.gameMaze == null) {
		    mazeFile.close();
		    throw new InvalidMazeException("Unknown object encountered.");
		}
		app.getMazeManager().setMaze(this.gameMaze);
		app.getGameManager().validateScore();
		mazeFile.close();
		startW = this.gameMaze.getStartLevel();
		final boolean playerExists = this.gameMaze.findPlayerOnLevel(startW);
		if (playerExists) {
		    this.gameMaze.findAllStarts();
		    app.getGameManager().getPlayerManager().setPlayerLocation(this.gameMaze.getFindResultColumn(startW),
			    this.gameMaze.getFindResultRow(startW), this.gameMaze.getFindResultFloor(startW), startW);
		    app.getGameManager().resetViewingWindow();
		}
		this.gameMaze.save();
		app.getMazeManager().setMaze(this.gameMaze);
		// Final cleanup
		final String lum = app.getMazeManager().getLastUsedMaze();
		app.getMazeManager().clearLastUsedFilenames();
		app.getMazeManager().setLastUsedMaze(lum);
		app.getEditor().mazeChanged();
		Messager.showDialog(sg + " file loaded.");
		app.getMazeManager().handleDeferredSuccess(true);
	    } catch (final IOException ie) {
		throw new InvalidMazeException("Error reading " + sg.toLowerCase() + " file.");
	    }
	} catch (final InvalidMazeException ime) {
	    Messager.showDialog(ime.getMessage());
	    app.getMazeManager().handleDeferredSuccess(false);
	} catch (final Exception ex) {
	    Messager.showDialog("Unknown error reading " + sg.toLowerCase() + " file.");
	    app.getMazeManager().handleDeferredSuccess(false);
	}
    }

    private static byte[] readFormatVersion(final XDataReader mazeFile) throws IOException {
	final byte major = mazeFile.readByte();
	final byte minor = mazeFile.readByte();
	return new byte[] { major, minor };
    }

    private static boolean checkFormatVersion(final byte[] version) {
	final byte major = version[0];
	final byte minor = version[1];
	if (major != LoadTask4.FORMAT_VERSION_MAJOR) {
	    return false;
	} else {
	    if (minor > LoadTask4.FORMAT_VERSION_MINOR) {
		return false;
	    } else {
		return true;
	    }
	}
    }
}
