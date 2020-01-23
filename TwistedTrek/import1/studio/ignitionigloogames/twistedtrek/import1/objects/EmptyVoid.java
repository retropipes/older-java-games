/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class EmptyVoid extends GenericWall {
    // Properties
    private String currAppearance;

    // Constructors
    public EmptyVoid() {
	super(false, false);
	this.currAppearance = "Void";
    }

    @Override
    public String gameRenderHook(final int x, final int y, final int z, final int w) {
	final Application app = Import1.getApplication();
	String mo1Name, mo2Name, mo3Name, mo4Name, mo6Name, mo7Name, mo8Name, mo9Name, thisName;
	thisName = this.getName();
	final MazeObject mo1 = app.getMazeManager().getMazeObject(x - 1, y - 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo1Name = mo1.getName();
	} catch (final NullPointerException np) {
	    mo1Name = thisName;
	}
	final MazeObject mo2 = app.getMazeManager().getMazeObject(x - 1, y, z, w, Maze.LAYER_OBJECT);
	try {
	    mo2Name = mo2.getName();
	} catch (final NullPointerException np) {
	    mo2Name = thisName;
	}
	final MazeObject mo3 = app.getMazeManager().getMazeObject(x - 1, y + 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo3Name = mo3.getName();
	} catch (final NullPointerException np) {
	    mo3Name = thisName;
	}
	final MazeObject mo4 = app.getMazeManager().getMazeObject(x, y - 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo4Name = mo4.getName();
	} catch (final NullPointerException np) {
	    mo4Name = thisName;
	}
	final MazeObject mo6 = app.getMazeManager().getMazeObject(x, y + 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo6Name = mo6.getName();
	} catch (final NullPointerException np) {
	    mo6Name = thisName;
	}
	final MazeObject mo7 = app.getMazeManager().getMazeObject(x + 1, y - 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo7Name = mo7.getName();
	} catch (final NullPointerException np) {
	    mo7Name = thisName;
	}
	final MazeObject mo8 = app.getMazeManager().getMazeObject(x + 1, y, z, w, Maze.LAYER_OBJECT);
	try {
	    mo8Name = mo8.getName();
	} catch (final NullPointerException np) {
	    mo8Name = thisName;
	}
	final MazeObject mo9 = app.getMazeManager().getMazeObject(x + 1, y + 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo9Name = mo9.getName();
	} catch (final NullPointerException np) {
	    mo9Name = thisName;
	}
	if (!thisName.equals(mo1Name) || !thisName.equals(mo2Name) || !thisName.equals(mo3Name)
		|| !thisName.equals(mo4Name) || !thisName.equals(mo6Name) || !thisName.equals(mo7Name)
		|| !thisName.equals(mo8Name) || !thisName.equals(mo9Name)) {
	    this.currAppearance = "Sealing Wall";
	    return this.currAppearance;
	} else {
	    this.currAppearance = "Void";
	    return super.gameRenderHook(x, y, z, w);
	}
    }

    @Override
    public void determineCurrentAppearance(final int x, final int y, final int z, final int w) {
	final Application app = Import1.getApplication();
	String mo1Name, mo2Name, mo3Name, mo4Name, mo6Name, mo7Name, mo8Name, mo9Name, thisName;
	thisName = this.getName();
	final MazeObject mo1 = app.getMazeManager().getMazeObject(x - 1, y - 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo1Name = mo1.getName();
	} catch (final NullPointerException np) {
	    mo1Name = thisName;
	}
	final MazeObject mo2 = app.getMazeManager().getMazeObject(x - 1, y, z, w, Maze.LAYER_OBJECT);
	try {
	    mo2Name = mo2.getName();
	} catch (final NullPointerException np) {
	    mo2Name = thisName;
	}
	final MazeObject mo3 = app.getMazeManager().getMazeObject(x - 1, y + 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo3Name = mo3.getName();
	} catch (final NullPointerException np) {
	    mo3Name = thisName;
	}
	final MazeObject mo4 = app.getMazeManager().getMazeObject(x, y - 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo4Name = mo4.getName();
	} catch (final NullPointerException np) {
	    mo4Name = thisName;
	}
	final MazeObject mo6 = app.getMazeManager().getMazeObject(x, y + 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo6Name = mo6.getName();
	} catch (final NullPointerException np) {
	    mo6Name = thisName;
	}
	final MazeObject mo7 = app.getMazeManager().getMazeObject(x + 1, y - 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo7Name = mo7.getName();
	} catch (final NullPointerException np) {
	    mo7Name = thisName;
	}
	final MazeObject mo8 = app.getMazeManager().getMazeObject(x + 1, y, z, w, Maze.LAYER_OBJECT);
	try {
	    mo8Name = mo8.getName();
	} catch (final NullPointerException np) {
	    mo8Name = thisName;
	}
	final MazeObject mo9 = app.getMazeManager().getMazeObject(x + 1, y + 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo9Name = mo9.getName();
	} catch (final NullPointerException np) {
	    mo9Name = thisName;
	}
	if (!thisName.equals(mo1Name) || !thisName.equals(mo2Name) || !thisName.equals(mo3Name)
		|| !thisName.equals(mo4Name) || !thisName.equals(mo6Name) || !thisName.equals(mo7Name)
		|| !thisName.equals(mo8Name) || !thisName.equals(mo9Name)) {
	    this.currAppearance = "Sealing Wall";
	} else {
	    this.currAppearance = "Void";
	}
    }

    @Override
    public String getName() {
	return "Void";
    }

    @Override
    public String getGameName() {
	return this.currAppearance;
    }

    @Override
    public String getPluralName() {
	return "Voids";
    }

    @Override
    public byte getObjectID() {
	return (byte) 1;
    }

    @Override
    public String getDescription() {
	return "The Void surrounds the maze, and cannot be altered in any way.";
    }
}
