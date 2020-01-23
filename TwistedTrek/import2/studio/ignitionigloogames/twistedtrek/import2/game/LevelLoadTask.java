/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.game;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.twistedtrek.import2.Application;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.creatures.party.PartyManager;
import studio.ignitionigloogames.twistedtrek.import2.maze.Maze;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractMazeObject;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.ImageColorConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.LogoManager;

public class LevelLoadTask extends Thread {
    // Fields
    private final JFrame loadFrame;
    private final int level;

    // Constructors
    public LevelLoadTask(final int offset) {
	this.level = offset;
	this.setName("Level Loader");
	this.loadFrame = new JFrame("Loading...");
	this.loadFrame.setIconImage(LogoManager.getIconLogo());
	final JProgressBar loadBar = new JProgressBar();
	loadBar.setIndeterminate(true);
	this.loadFrame.getContentPane().add(loadBar);
	this.loadFrame.setResizable(false);
	this.loadFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	this.loadFrame.pack();
    }

    // Methods
    @Override
    public void run() {
	try {
	    this.loadFrame.setVisible(true);
	    final Application app = Import2.getApplication();
	    final Maze gameMaze = app.getMazeManager().getMaze();
	    app.getGameManager().disableEvents();
	    gameMaze.switchLevelOffset(this.level);
	    gameMaze.offsetPlayerLocationW(this.level);
	    PartyManager.getParty().offsetTowerLevel(this.level);
	    AbstractMazeObject
		    .setTemplateColor(ImageColorConstants.getColorForLevel(PartyManager.getParty().getTowerLevel()));
	    app.getGameManager().resetViewingWindow();
	    app.getGameManager().enableEvents();
	    app.getGameManager().redrawMaze();
	} catch (final Exception ex) {
	    Import2.getErrorLogger().logError(ex);
	} finally {
	    this.loadFrame.setVisible(false);
	}
    }
}
