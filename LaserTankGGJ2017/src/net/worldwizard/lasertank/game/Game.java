package net.worldwizard.lasertank.game;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.worldwizard.lasertank.assets.GameImage;
import net.worldwizard.lasertank.assets.GameImageCache;
import net.worldwizard.lasertank.assets.GameSound;
import net.worldwizard.lasertank.loaders.ImageLoader;
import net.worldwizard.lasertank.loaders.SoundLoader;
import net.worldwizard.lasertank.map.GameMap;
import net.worldwizard.lasertank.objects.Box;
import net.worldwizard.lasertank.objects.Bridge;
import net.worldwizard.lasertank.objects.Empty;
import net.worldwizard.lasertank.objects.GameObject;
import net.worldwizard.lasertank.objects.GreenLaserHorizontal;
import net.worldwizard.lasertank.objects.GreenLaserVertical;
import net.worldwizard.lasertank.objects.Ground;
import net.worldwizard.lasertank.objects.TankEast;
import net.worldwizard.lasertank.objects.TankNorth;
import net.worldwizard.lasertank.objects.TankSouth;
import net.worldwizard.lasertank.objects.TankWest;
import net.worldwizard.lasertank.objects.Wall;

public class Game extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static final int MAP_SIZE = 16;
    static final int FACING_NORTH = 0;
    static final int FACING_SOUTH = 1;
    static final int FACING_WEST = 2;
    static final int FACING_EAST = 3;
    static final GameObject EMPTY = new Empty();
    static final GameObject TANK_NORTH = new TankNorth();
    static final GameObject TANK_SOUTH = new TankSouth();
    static final GameObject TANK_WEST = new TankWest();
    static final GameObject TANK_EAST = new TankEast();
    static final GameObject GREEN_HORZ = new GreenLaserHorizontal();
    static final GameObject GREEN_VERT = new GreenLaserVertical();
    static final GameObject BOX = new Box();
    static final GameObject WALL = new Wall();
    static final GameObject BRIDGE = new Bridge();
    static final GameObject GROUND = new Ground();
    private static final GameImage DIALOG_ICON = ImageLoader.loadUIImage("micrologo");
    // Fields
    EventHandler eh;
    GameMap map;
    GameDraw draw;
    int facing, laserFacing;
    int playerX, playerY, laserX, laserY;
    GameObject tank, laser;
    GameSound dead, goal, laserDead, pushBox, sink;

    public Game() {
	super("LaserTank");
	this.eh = new EventHandler();
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.facing = Game.FACING_NORTH;
	this.playerX = 0;
	this.playerY = 0;
	this.dead = SoundLoader.loadSound("die");
	this.goal = SoundLoader.loadSound("end_level");
	this.laserDead = SoundLoader.loadSound("laser_die");
	this.pushBox = SoundLoader.loadSound("push_box");
	this.sink = SoundLoader.loadSound("sink");
	this.map = new GameMap();
	this.map.fill();
	this.tank = this.map.get(this.playerX, this.playerY, 1);
	this.draw = new GameDraw(Game.MAP_SIZE, 32);
	this.getContentPane().setLayout(new FlowLayout());
	this.getContentPane().add(this.draw);
	this.addKeyListener(this.eh);
	this.pack();
    }

    public void startGame() {
	this.setVisible(true);
	new Animator().start();
	new LaserRunner().start();
    }

    void draw() {
	this.draw.draw();
    }

    boolean fixBounds(final int opx, final int opy) {
	if (this.playerX < 0) {
	    this.playerX = 0;
	}
	if (this.playerX >= Game.MAP_SIZE) {
	    this.playerX = Game.MAP_SIZE - 1;
	}
	if (this.playerY < 0) {
	    this.playerY = 0;
	}
	if (this.playerY >= Game.MAP_SIZE) {
	    this.playerY = Game.MAP_SIZE - 1;
	}
	final GameObject go0 = this.map.get(this.playerX, this.playerY, 0);
	final GameObject go1 = this.map.get(this.playerX, this.playerY, 1);
	if (go0.isSolid() || go1.isSolid()) {
	    this.playerX = opx;
	    this.playerY = opy;
	}
	if (go0.killsPlayer() || go1.killsPlayer()) {
	    this.dead.play();
	    this.map.set(Game.EMPTY, opx, opy, 1);
	    this.removeKeyListener(this.eh);
	    JOptionPane.showMessageDialog(this, "You are dead!", "LaserTank", JOptionPane.INFORMATION_MESSAGE,
		    Game.DIALOG_ICON);
	    return false;
	}
	if (go0.isGoal() || go1.isGoal()) {
	    this.goal.play();
	    this.map.set(Game.EMPTY, opx, opy, 1);
	    this.removeKeyListener(this.eh);
	    JOptionPane.showMessageDialog(this, "You win!", "LaserTank", JOptionPane.INFORMATION_MESSAGE,
		    Game.DIALOG_ICON);
	    return false;
	}
	return true;
    }

    boolean fixLaserBounds(final int olx, final int oly) {
	if (this.laserX < 0) {
	    return false;
	}
	if (this.laserX >= Game.MAP_SIZE) {
	    return false;
	}
	if (this.laserY < 0) {
	    return false;
	}
	if (this.laserY >= Game.MAP_SIZE) {
	    return false;
	}
	final GameObject go0 = this.map.get(this.laserX, this.laserY, 0);
	final GameObject go1 = this.map.get(this.laserX, this.laserY, 1);
	final int dirX = this.laserX - olx;
	final int dirY = this.laserY - oly;
	if (go0.isSolid() || go1.isSolid()) {
	    GameObject go0next = Game.GROUND;
	    try {
		go0next = this.map.get(this.laserX + dirX, this.laserY + dirY, 0);
	    } catch (final ArrayIndexOutOfBoundsException e) {
		// Ignore
	    }
	    GameObject go1next = Game.WALL;
	    try {
		go1next = this.map.get(this.laserX + dirX, this.laserY + dirY, 1);
	    } catch (final ArrayIndexOutOfBoundsException e) {
		// Ignore
	    }
	    if (go1.laserMoves() && !go1next.isSolid()) {
		this.pushBox.play();
		this.map.set(Game.EMPTY, this.laserX, this.laserY, 1);
		if (go0next.killsPlayer()) {
		    this.sink.play();
		    this.map.set(Game.BRIDGE, this.laserX + dirX, this.laserY + dirY, 0);
		} else {
		    this.map.set(Game.BOX, this.laserX + dirX, this.laserY + dirY, 1);
		}
	    } else {
		this.laserDead.play();
	    }
	    return false;
	}
	return true;
    }

    private class EventHandler implements KeyListener {
	private final GameSound move, turn, bump, fire;

	public EventHandler() {
	    this.move = SoundLoader.loadSound("move");
	    this.turn = SoundLoader.loadSound("turn");
	    this.bump = SoundLoader.loadSound("bump_head");
	    this.fire = SoundLoader.loadSound("fire_laser");
	}

	@Override
	public void keyTyped(final KeyEvent e) {
	    // Do nothing
	}

	@Override
	public void keyPressed(final KeyEvent e) {
	    final Game g = Game.this;
	    boolean spun = false;
	    boolean responded = false;
	    final int opx = g.playerX;
	    final int opy = g.playerY;
	    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		responded = true;
		if (g.facing == Game.FACING_EAST) {
		    // Move
		    g.playerX++;
		    this.move.play();
		} else {
		    // Turn
		    spun = true;
		    g.facing = Game.FACING_EAST;
		    g.tank = Game.TANK_EAST;
		    this.turn.play();
		}
	    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		responded = true;
		if (g.facing == Game.FACING_WEST) {
		    // Move
		    g.playerX--;
		    this.move.play();
		} else {
		    // Turn
		    spun = true;
		    g.facing = Game.FACING_WEST;
		    g.tank = Game.TANK_WEST;
		    this.turn.play();
		}
	    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		responded = true;
		if (g.facing == Game.FACING_SOUTH) {
		    // Move
		    g.playerY++;
		    this.move.play();
		} else {
		    // Turn
		    spun = true;
		    g.facing = Game.FACING_SOUTH;
		    g.tank = Game.TANK_SOUTH;
		    this.turn.play();
		}
	    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
		responded = true;
		if (g.facing == Game.FACING_NORTH) {
		    // Move
		    g.playerY--;
		    this.move.play();
		} else {
		    // Turn
		    spun = true;
		    g.facing = Game.FACING_NORTH;
		    g.tank = Game.TANK_NORTH;
		    this.turn.play();
		}
	    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		responded = true;
		// Shoot
		this.fire.play();
		spun = true;
		if (g.facing == Game.FACING_NORTH) {
		    if (g.playerY > 0) {
			g.map.set(Game.GREEN_VERT, g.playerX, g.playerY, 3);
			g.laser = Game.GREEN_VERT;
			g.laserFacing = Game.FACING_NORTH;
			g.laserX = g.playerX;
			g.laserY = g.playerY;
		    }
		} else if (g.facing == Game.FACING_SOUTH) {
		    if (g.playerY < Game.MAP_SIZE - 1) {
			g.map.set(Game.GREEN_VERT, g.playerX, g.playerY, 3);
			g.laser = Game.GREEN_VERT;
			g.laserFacing = Game.FACING_SOUTH;
			g.laserX = g.playerX;
			g.laserY = g.playerY;
		    }
		} else if (g.facing == Game.FACING_WEST) {
		    if (g.playerX > 0) {
			g.map.set(Game.GREEN_HORZ, g.playerX, g.playerY, 3);
			g.laser = Game.GREEN_HORZ;
			g.laserFacing = Game.FACING_WEST;
			g.laserX = g.playerX;
			g.laserY = g.playerY;
		    }
		} else if (g.facing == Game.FACING_EAST) {
		    if (g.playerX < Game.MAP_SIZE - 1) {
			g.map.set(Game.GREEN_HORZ, g.playerX, g.playerY, 3);
			g.laser = Game.GREEN_HORZ;
			g.laserFacing = Game.FACING_EAST;
			g.laserX = g.playerX;
			g.laserY = g.playerY;
		    }
		}
	    }
	    if (responded) {
		final boolean proceed = g.fixBounds(opx, opy);
		if (proceed) {
		    if (g.playerX == opx && g.playerY == opy && !spun) {
			this.bump.play();
		    } else {
			g.map.set(Game.EMPTY, opx, opy, 1);
			g.map.set(g.tank, g.playerX, g.playerY, 1);
		    }
		}
	    }
	}

	@Override
	public void keyReleased(final KeyEvent e) {
	    // Do nothing
	}
    }

    private class Animator extends Thread {
	public Animator() {
	    // Do nothing
	}

	@Override
	public void run() {
	    final Game g = Game.this;
	    while (true) {
		for (int x = 0; x < Game.MAP_SIZE; x++) {
		    for (int y = 0; y < Game.MAP_SIZE; y++) {
			g.map.get(x, y, 0).animate();
			g.map.get(x, y, 1).animate();
			g.map.get(x, y, 2).animate();
			g.map.get(x, y, 3).animate();
		    }
		}
		g.draw();
		try {
		    Thread.sleep(100);
		} catch (final InterruptedException e) {
		    // Ignore
		}
	    }
	}
    }

    private class LaserRunner extends Thread {
	public LaserRunner() {
	    // Do nothing
	}

	@Override
	public void run() {
	    final Game g = Game.this;
	    while (true) {
		if (g.laser != null) {
		    g.removeKeyListener(g.eh);
		    final int olx = g.laserX;
		    final int oly = g.laserY;
		    if (g.laserFacing == Game.FACING_NORTH) {
			g.laserY--;
		    } else if (g.laserFacing == Game.FACING_SOUTH) {
			g.laserY++;
		    } else if (g.laserFacing == Game.FACING_WEST) {
			g.laserX--;
		    } else if (g.laserFacing == Game.FACING_EAST) {
			g.laserX++;
		    }
		    final boolean laserAlive = g.fixLaserBounds(olx, oly);
		    g.map.set(Game.EMPTY, olx, oly, 3);
		    if (!laserAlive) {
			g.laser = null;
			g.addKeyListener(g.eh);
		    } else {
			g.map.set(g.laser, g.laserX, g.laserY, 3);
		    }
		    try {
			Thread.sleep(50);
		    } catch (final InterruptedException e) {
			// Ignore
		    }
		} else {
		    try {
			Thread.sleep(1000);
		    } catch (final InterruptedException e) {
			// Ignore
		    }
		}
	    }
	}
    }

    private class GameDraw extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final int tSize;

	public GameDraw(final int mapSize, final int tileSize) {
	    super();
	    this.setPreferredSize(new Dimension(mapSize * tileSize, mapSize * tileSize));
	    this.tSize = tileSize;
	}

	public void draw() {
	    final Game g = Game.this;
	    final Graphics gr = this.getGraphics();
	    if (gr != null) {
		for (int x = 0; x < Game.MAP_SIZE; x++) {
		    for (int y = 0; y < Game.MAP_SIZE; y++) {
			final GameImage gi0 = g.map.get(x, y, 0).getAppearance();
			final GameImage gi1 = g.map.get(x, y, 1).getAppearance();
			final GameImage gi2 = g.map.get(x, y, 2).getAppearance();
			final GameImage gi3 = g.map.get(x, y, 3).getAppearance();
			final GameImage gi = GameImageCache.getComposite(gi0, gi1, gi2, gi3);
			gr.drawImage(gi, x * this.tSize, y * this.tSize, null);
		    }
		}
	    }
	}
    }
}
