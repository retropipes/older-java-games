/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.game;

import java.io.File;

import studio.ignitionigloogames.scoremanager.SavedScoreManager;
import studio.ignitionigloogames.scoremanager.ScoreManager;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.maze.Extension;

public class ScoreTracker {
	// Fields
	private String scoresFile;
	private SavedScoreManager ssMgr;
	private long score;
	private boolean scoreValid;
	private static final String MAC_PREFIX = "HOME";
	private static final String WIN_PREFIX = "APPDATA";
	private static final String UNIX_PREFIX = "HOME";
	private static final String MAC_DIR = "/Library/Import1/Scores/";
	private static final String WIN_DIR = "\\Import1\\Scores\\";
	private static final String UNIX_DIR = "/.fantastle/scores/";

	// Constructors
	public ScoreTracker() {
		this.scoresFile = "";
		this.scoreValid = false;
		this.score = 0L;
		this.ssMgr = null;
	}

	// Methods
	public boolean checkScore() {
		if (this.scoreValid) {
			return this.ssMgr.checkScore(new long[] { this.score });
		} else {
			return false;
		}
	}

	public void commitScore() {
		if (this.scoreValid) {
			final boolean result = this.ssMgr.addScore(this.score);
			if (result) {
				this.ssMgr.viewTable();
			}
		}
	}

	public void invalidateScore() {
		this.scoreValid = false;
	}

	public void resetScore(final String filename) {
		this.setScoreFile(filename);
		this.score = 0L;
	}

	public void setScoreFile(final String filename) {
		// Check validity
		if (this.scoreValid) {
			// Check filename argument
			if (filename != null) {
				if (filename.equals("")) {
					throw new IllegalArgumentException("Filename cannot be empty!");
				}
			} else {
				throw new IllegalArgumentException("Filename cannot be null!");
			}
			// Make sure the needed directories exist first
			final File sf = ScoreTracker.getScoresFile(filename);
			final File parent = new File(sf.getParent());
			if (!parent.exists()) {
				parent.mkdirs();
			}
			this.scoresFile = sf.getAbsolutePath();
			this.ssMgr = new SavedScoreManager(1, 10, ScoreManager.SORT_ORDER_ASCENDING, 0L, "Import1 Scores",
					new String[] { "points" }, this.scoresFile);
		}
	}

	public void incrementScore() {
		this.score++;
	}

	public void deductStep() {
		this.score--;
	}

	public void updateScore(final long increment) {
		this.score += increment;
	}

	public void validateScore() {
		this.scoreValid = true;
	}

	public long getScore() {
		return this.score;
	}

	public void setScore(final long newScore) {
		this.score = newScore;
	}

	public String getScoreUnits() {
		return "points";
	}

	public void showCurrentScore() {
		if (this.scoreValid) {
			Messager.showDialog("Your current score: " + this.score + " points");
		} else {
			Messager.showDialog("The current score is not available at this time.");
		}
	}

	public void showScoreTable() {
		this.ssMgr.viewTable();
	}

	private static String getScoreDirPrefix() {
		final String osName = System.getProperty("os.name");
		if (osName.indexOf("Mac OS X") != -1) {
			// Mac OS X
			return System.getenv(ScoreTracker.MAC_PREFIX);
		} else if (osName.indexOf("Windows") != -1) {
			// Windows
			return System.getenv(ScoreTracker.WIN_PREFIX);
		} else {
			// Other - assume UNIX-like
			return System.getenv(ScoreTracker.UNIX_PREFIX);
		}
	}

	private static String getScoreDirectory() {
		final String osName = System.getProperty("os.name");
		if (osName.indexOf("Mac OS X") != -1) {
			// Mac OS X
			return ScoreTracker.MAC_DIR;
		} else if (osName.indexOf("Windows") != -1) {
			// Windows
			return ScoreTracker.WIN_DIR;
		} else {
			// Other - assume UNIX-like
			return ScoreTracker.UNIX_DIR;
		}
	}

	private static File getScoresFile(final String filename) {
		final StringBuilder b = new StringBuilder();
		b.append(ScoreTracker.getScoreDirPrefix());
		b.append(ScoreTracker.getScoreDirectory());
		b.append(filename);
		b.append(Extension.getScoresExtensionWithPeriod());
		return new File(b.toString());
	}
}