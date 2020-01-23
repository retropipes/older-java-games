/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.ArrowTypeConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.DirectionConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.TypeConstants;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.SoundManager;

public class BarrierGenerator extends GenericWall {
    // Fields
    private static final int SCAN_LIMIT = 6;
    private static final int TIMER_DELAY = 8;

    // Constructors
    public BarrierGenerator() {
	super();
    }

    @Override
    public boolean preMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	// Remove barriers if present
	boolean scanResult = false;
	boolean flag = false;
	final Application app = Import1.getApplication();
	final int pz = app.getGameManager().getPlayerManager().getPlayerLocationZ();
	final int pw = app.getGameManager().getPlayerManager().getPlayerLocationW();
	String mo2Name, mo4Name, mo6Name, mo8Name, invalidName, horzName, vertName;
	invalidName = new EmptyVoid().getName();
	horzName = new HorizontalBarrier().getName();
	vertName = new VerticalBarrier().getName();
	final MazeObject mo2 = app.getMazeManager().getMazeObject(dirX - 1, dirY, pz, pw, Maze.LAYER_OBJECT);
	try {
	    mo2Name = mo2.getName();
	} catch (final NullPointerException np) {
	    mo2Name = invalidName;
	}
	final MazeObject mo4 = app.getMazeManager().getMazeObject(dirX, dirY - 1, pz, pw, Maze.LAYER_OBJECT);
	try {
	    mo4Name = mo4.getName();
	} catch (final NullPointerException np) {
	    mo4Name = invalidName;
	}
	final MazeObject mo6 = app.getMazeManager().getMazeObject(dirX, dirY + 1, pz, pw, Maze.LAYER_OBJECT);
	try {
	    mo6Name = mo6.getName();
	} catch (final NullPointerException np) {
	    mo6Name = invalidName;
	}
	final MazeObject mo8 = app.getMazeManager().getMazeObject(dirX + 1, dirY, pz, pw, Maze.LAYER_OBJECT);
	try {
	    mo8Name = mo8.getName();
	} catch (final NullPointerException np) {
	    mo8Name = invalidName;
	}
	if (mo2Name.equals(horzName)) {
	    scanResult = this.scan(DirectionConstants.DIRECTION_WEST, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
		    false);
	    if (scanResult) {
		this.generate(DirectionConstants.DIRECTION_WEST, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
			false);
		flag = true;
	    }
	}
	if (mo4Name.equals(vertName)) {
	    scanResult = this.scan(DirectionConstants.DIRECTION_NORTH, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
		    false);
	    if (scanResult) {
		this.generate(DirectionConstants.DIRECTION_NORTH, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
			false);
		flag = true;
	    }
	}
	if (mo6Name.equals(vertName)) {
	    scanResult = this.scan(DirectionConstants.DIRECTION_SOUTH, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
		    false);
	    if (scanResult) {
		this.generate(DirectionConstants.DIRECTION_SOUTH, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
			false);
		flag = true;
	    }
	}
	if (mo8Name.equals(horzName)) {
	    scanResult = this.scan(DirectionConstants.DIRECTION_EAST, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
		    false);
	    if (scanResult) {
		this.generate(DirectionConstants.DIRECTION_EAST, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
			false);
		flag = true;
	    }
	}
	if (flag) {
	    if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
		SoundManager.play("generate");
	    }
	    this.activateTimer(BarrierGenerator.TIMER_DELAY);
	    app.getGameManager().redrawMazeNoRebuild();
	}
	return true;
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	// Do nothing
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
	// Generate barriers again
	boolean scanResult = false;
	boolean flag = false;
	final Application app = Import1.getApplication();
	final int pz = app.getGameManager().getPlayerManager().getPlayerLocationZ();
	final int pw = app.getGameManager().getPlayerManager().getPlayerLocationW();
	String mo2Name, mo4Name, mo6Name, mo8Name, invalidName, horzName, vertName;
	invalidName = new EmptyVoid().getName();
	horzName = new HorizontalBarrier().getName();
	vertName = new VerticalBarrier().getName();
	final MazeObject mo2 = app.getMazeManager().getMazeObject(dirX - 1, dirY, pz, pw, Maze.LAYER_OBJECT);
	try {
	    mo2Name = mo2.getName();
	} catch (final NullPointerException np) {
	    mo2Name = invalidName;
	}
	final MazeObject mo4 = app.getMazeManager().getMazeObject(dirX, dirY - 1, pz, pw, Maze.LAYER_OBJECT);
	try {
	    mo4Name = mo4.getName();
	} catch (final NullPointerException np) {
	    mo4Name = invalidName;
	}
	final MazeObject mo6 = app.getMazeManager().getMazeObject(dirX, dirY + 1, pz, pw, Maze.LAYER_OBJECT);
	try {
	    mo6Name = mo6.getName();
	} catch (final NullPointerException np) {
	    mo6Name = invalidName;
	}
	final MazeObject mo8 = app.getMazeManager().getMazeObject(dirX + 1, dirY, pz, pw, Maze.LAYER_OBJECT);
	try {
	    mo8Name = mo8.getName();
	} catch (final NullPointerException np) {
	    mo8Name = invalidName;
	}
	if (!mo2Name.equals(horzName)) {
	    scanResult = this.scan(DirectionConstants.DIRECTION_WEST, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
		    true);
	    if (scanResult) {
		this.generate(DirectionConstants.DIRECTION_WEST, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT, true);
		flag = true;
	    }
	}
	if (!mo4Name.equals(vertName)) {
	    scanResult = this.scan(DirectionConstants.DIRECTION_NORTH, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
		    true);
	    if (scanResult) {
		this.generate(DirectionConstants.DIRECTION_NORTH, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
			true);
		flag = true;
	    }
	}
	if (!mo6Name.equals(vertName)) {
	    scanResult = this.scan(DirectionConstants.DIRECTION_SOUTH, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
		    true);
	    if (scanResult) {
		this.generate(DirectionConstants.DIRECTION_SOUTH, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
			true);
		flag = true;
	    }
	}
	if (!mo8Name.equals(horzName)) {
	    scanResult = this.scan(DirectionConstants.DIRECTION_EAST, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT,
		    true);
	    if (scanResult) {
		this.generate(DirectionConstants.DIRECTION_EAST, dirX, dirY, pz, pw, BarrierGenerator.SCAN_LIMIT, true);
		flag = true;
	    }
	}
	if (flag) {
	    if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
		SoundManager.play("generate");
	    }
	}
    }

    private boolean scan(final int dir, final int x, final int y, final int z, final int w, final int limit,
	    final boolean o) {
	final Application app = Import1.getApplication();
	final String invalidName = new EmptyVoid().getName();
	if (dir == DirectionConstants.DIRECTION_EAST) {
	    for (int l = 1; l < limit; l++) {
		final MazeObject mo = app.getMazeManager().getMazeObject(x + l, y, z, w, Maze.LAYER_OBJECT);
		String moName;
		try {
		    moName = mo.getName();
		} catch (final NullPointerException np) {
		    moName = invalidName;
		}
		if (o) {
		    if (moName.equals(this.getName())) {
			return true;
		    }
		} else {
		    if (!moName.equals(new HorizontalBarrier().getName())) {
			if (moName.equals(this.getName())) {
			    return true;
			}
		    }
		}
	    }
	} else if (dir == DirectionConstants.DIRECTION_NORTH) {
	    for (int l = 1; l < limit; l++) {
		final MazeObject mo = app.getMazeManager().getMazeObject(x, y - l, z, w, Maze.LAYER_OBJECT);
		String moName;
		try {
		    moName = mo.getName();
		} catch (final NullPointerException np) {
		    moName = invalidName;
		}
		if (o) {
		    if (moName.equals(this.getName())) {
			return true;
		    }
		} else {
		    if (!moName.equals(new VerticalBarrier().getName())) {
			if (moName.equals(this.getName())) {
			    return true;
			}
		    }
		}
	    }
	} else if (dir == DirectionConstants.DIRECTION_SOUTH) {
	    for (int l = 1; l < limit; l++) {
		final MazeObject mo = app.getMazeManager().getMazeObject(x, y + l, z, w, Maze.LAYER_OBJECT);
		String moName;
		try {
		    moName = mo.getName();
		} catch (final NullPointerException np) {
		    moName = invalidName;
		}
		if (o) {
		    if (moName.equals(this.getName())) {
			return true;
		    }
		} else {
		    if (!moName.equals(new VerticalBarrier().getName())) {
			if (moName.equals(this.getName())) {
			    return true;
			}
		    }
		}
	    }
	} else if (dir == DirectionConstants.DIRECTION_WEST) {
	    for (int l = 1; l < limit; l++) {
		final MazeObject mo = app.getMazeManager().getMazeObject(x - l, y, z, w, Maze.LAYER_OBJECT);
		String moName;
		try {
		    moName = mo.getName();
		} catch (final NullPointerException np) {
		    moName = invalidName;
		}
		if (o) {
		    if (moName.equals(this.getName())) {
			return true;
		    }
		} else {
		    if (!moName.equals(new HorizontalBarrier().getName())) {
			if (moName.equals(this.getName())) {
			    return true;
			}
		    }
		}
	    }
	}
	return false;
    }

    private void generate(final int dir, final int x, final int y, final int z, final int w, final int limit,
	    final boolean o) {
	final Application app = Import1.getApplication();
	final String invalidName = new EmptyVoid().getName();
	if (dir == DirectionConstants.DIRECTION_EAST) {
	    for (int l = 1; l < limit; l++) {
		final MazeObject mo = app.getMazeManager().getMazeObject(x + l, y, z, w, Maze.LAYER_OBJECT);
		String moName;
		try {
		    moName = mo.getName();
		} catch (final NullPointerException np) {
		    moName = invalidName;
		}
		if (o) {
		    if (moName.equals(new HorizontalBarrier().getName())) {
			break;
		    } else {
			if (moName.equals(this.getName())) {
			    break;
			}
			try {
			    app.getMazeManager().getMaze().setCell(new HorizontalBarrier(), x + l, y, z, w,
				    Maze.LAYER_OBJECT);
			} catch (final ArrayIndexOutOfBoundsException aioob) {
			    // Do nothing
			}
		    }
		} else {
		    if (!moName.equals(new HorizontalBarrier().getName())) {
			break;
		    } else {
			if (moName.equals(this.getName())) {
			    break;
			}
			try {
			    app.getMazeManager().getMaze().setCell(new Empty(), x + l, y, z, w, Maze.LAYER_OBJECT);
			} catch (final ArrayIndexOutOfBoundsException aioob) {
			    // Do nothing
			}
		    }
		}
	    }
	} else if (dir == DirectionConstants.DIRECTION_NORTH) {
	    for (int l = 1; l < limit; l++) {
		final MazeObject mo = app.getMazeManager().getMazeObject(x, y - l, z, w, Maze.LAYER_OBJECT);
		String moName;
		try {
		    moName = mo.getName();
		} catch (final NullPointerException np) {
		    moName = invalidName;
		}
		if (o) {
		    if (moName.equals(new VerticalBarrier().getName())) {
			break;
		    } else {
			if (moName.equals(this.getName())) {
			    break;
			}
			try {
			    app.getMazeManager().getMaze().setCell(new VerticalBarrier(), x, y - l, z, w,
				    Maze.LAYER_OBJECT);
			} catch (final ArrayIndexOutOfBoundsException aioob) {
			    // Do nothing
			}
		    }
		} else {
		    if (!moName.equals(new VerticalBarrier().getName())) {
			break;
		    } else {
			if (moName.equals(this.getName())) {
			    break;
			}
			try {
			    app.getMazeManager().getMaze().setCell(new Empty(), x, y - l, z, w, Maze.LAYER_OBJECT);
			} catch (final ArrayIndexOutOfBoundsException aioob) {
			    // Do nothing
			}
		    }
		}
	    }
	} else if (dir == DirectionConstants.DIRECTION_SOUTH) {
	    for (int l = 1; l < limit; l++) {
		final MazeObject mo = app.getMazeManager().getMazeObject(x, y + l, z, w, Maze.LAYER_OBJECT);
		String moName;
		try {
		    moName = mo.getName();
		} catch (final NullPointerException np) {
		    moName = invalidName;
		}
		if (o) {
		    if (moName.equals(new VerticalBarrier().getName())) {
			break;
		    } else {
			if (moName.equals(this.getName())) {
			    break;
			}
			try {
			    app.getMazeManager().getMaze().setCell(new VerticalBarrier(), x, y + l, z, w,
				    Maze.LAYER_OBJECT);
			} catch (final ArrayIndexOutOfBoundsException aioob) {
			    // Do nothing
			}
		    }
		} else {
		    if (!moName.equals(new VerticalBarrier().getName())) {
			break;
		    } else {
			if (moName.equals(this.getName())) {
			    break;
			}
			try {
			    app.getMazeManager().getMaze().setCell(new Empty(), x, y + l, z, w, Maze.LAYER_OBJECT);
			} catch (final ArrayIndexOutOfBoundsException aioob) {
			    // Do nothing
			}
		    }
		}
	    }
	} else if (dir == DirectionConstants.DIRECTION_WEST) {
	    for (int l = 1; l < limit; l++) {
		final MazeObject mo = app.getMazeManager().getMazeObject(x - l, y, z, w, Maze.LAYER_OBJECT);
		String moName;
		try {
		    moName = mo.getName();
		} catch (final NullPointerException np) {
		    moName = invalidName;
		}
		if (o) {
		    if (moName.equals(new HorizontalBarrier().getName())) {
			break;
		    } else {
			if (moName.equals(this.getName())) {
			    break;
			}
			try {
			    app.getMazeManager().getMaze().setCell(new HorizontalBarrier(), x - l, y, z, w,
				    Maze.LAYER_OBJECT);
			} catch (final ArrayIndexOutOfBoundsException aioob) {
			    // Do nothing
			}
		    }
		} else {
		    if (!moName.equals(new HorizontalBarrier().getName())) {
			break;
		    } else {
			if (moName.equals(this.getName())) {
			    break;
			}
			try {
			    app.getMazeManager().getMaze().setCell(new Empty(), x - l, y, z, w, Maze.LAYER_OBJECT);
			} catch (final ArrayIndexOutOfBoundsException aioob) {
			    // Do nothing
			}
		    }
		}
	    }
	}
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
	    final int dirY, final int arrowType, final ObjectInventory inv) {
	// Behave as if the generator was walked into, unless the arrow was an
	// ice arrow
	if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
	    final Application app = Import1.getApplication();
	    app.getGameManager().morph(new IcedBarrierGenerator(), locX, locY, locZ, locW);
	} else {
	    this.preMoveAction(false, locX, locY, inv);
	}
	return false;
    }

    @Override
    public String getName() {
	return "Barrier Generator";
    }

    @Override
    public String getPluralName() {
	return "Barrier Generators";
    }

    @Override
    public byte getObjectID() {
	return (byte) 9;
    }

    @Override
    public boolean hasAdditionalProperties() {
	// True because we use the timer feature
	return true;
    }

    @Override
    public String getDescription() {
	return "Barrier Generators create Barriers. When hit, they stop generating for a while, then resume generating.";
    }

    @Override
    protected void setTypes() {
	super.setTypes();
	this.type.set(TypeConstants.TYPE_REACTS_TO_ICE);
    }
}
