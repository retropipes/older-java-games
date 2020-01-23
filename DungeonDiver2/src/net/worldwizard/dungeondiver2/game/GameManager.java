/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.dungeondiver2.Application;
import net.worldwizard.dungeondiver2.DungeonDiverII;
import net.worldwizard.dungeondiver2.game.scripts.GameScriptRunner;
import net.worldwizard.dungeondiver2.prefs.PreferencesManager;
import net.worldwizard.dungeondiver2.resourcemanagers.MusicConstants;
import net.worldwizard.dungeondiver2.resourcemanagers.MusicManager;
import net.worldwizard.support.Support;
import net.worldwizard.support.creatures.PartyManager;
import net.worldwizard.support.creatures.PartyMember;
import net.worldwizard.support.map.Map;
import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.map.generic.GameSoundConstants;
import net.worldwizard.support.map.generic.GenericCharacter;
import net.worldwizard.support.map.generic.InfiniteRecursionException;
import net.worldwizard.support.map.generic.MapObject;
import net.worldwizard.support.map.generic.TypeConstants;
import net.worldwizard.support.map.objects.Empty;
import net.worldwizard.support.map.objects.EmptyVoid;
import net.worldwizard.support.map.objects.Player;
import net.worldwizard.support.map.objects.Tile;
import net.worldwizard.support.map.objects.Wall;
import net.worldwizard.support.resourcemanagers.ImageTransformer;
import net.worldwizard.support.resourcemanagers.MapObjectImageManager;
import net.worldwizard.support.resourcemanagers.SoundManager;
import net.worldwizard.support.scripts.game.GameScriptArea;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class GameManager {
    // Fields
    private JFrame outputFrame;
    private Container outputPane, borderPane;
    private JLabel messageLabel;
    private MapObject savedMapObject;
    private EventHandler handler;
    private boolean savedGameFlag;
    private final PlayerLocationManager plMgr;
    private final GameViewingWindowManager vwMgr;
    private final StatGUI sg;
    private final ScoreTracker st;
    private JLabel[][] drawGrid;
    private boolean stateChanged;

    // Constructors
    public GameManager() {
        this.plMgr = new PlayerLocationManager();
        this.vwMgr = new GameViewingWindowManager();
        this.sg = new StatGUI();
        this.st = new ScoreTracker();
        this.setUpGUI();
        this.savedMapObject = new Empty();
        this.savedGameFlag = false;
        this.stateChanged = true;
    }

    // Methods
    public boolean newGame() {
        this.st.setScoreFile(Support.getVariables().getVariablesID());
        if (this.savedGameFlag) {
            if (PartyManager.getParty() != null) {
                return true;
            } else {
                return PartyManager.createParty();
            }
        } else {
            return PartyManager.createParty();
        }
    }

    public void addToScore(final long points) {
        this.st.addToScore(points);
    }

    public void showCurrentScore() {
        this.st.showCurrentScore();
    }

    private static void fireStepActions() {
        PartyManager.getParty().fireStepActions();
    }

    public void updateStats() {
        this.getStatGUI().updateStats();
    }

    private StatGUI getStatGUI() {
        return this.sg;
    }

    public void stateChanged() {
        this.stateChanged = true;
    }

    public Map getTemporaryBattleCopy() {
        return DungeonDiverII.getApplication().getVariablesManager().getMap()
                .getTemporaryBattleCopy();
    }

    public PlayerLocationManager getPlayerManager() {
        return this.plMgr;
    }

    public MapObject getSavedMapObject() {
        return this.savedMapObject;
    }

    private void saveSavedMapObject() {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final GenericCharacter player = (GenericCharacter) DungeonDiverII
                .getApplication().getVariablesManager().getMap()
                .getCell(px, py, pz, MapConstants.LAYER_OBJECT);
        player.setSavedObject(this.savedMapObject);
    }

    private void restoreSavedMapObject() {
        final int px = this.plMgr.getPlayerLocationX();
        final int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final GenericCharacter player = (GenericCharacter) DungeonDiverII
                .getApplication().getVariablesManager().getMap()
                .getCell(px, py, pz, MapConstants.LAYER_OBJECT);
        this.savedMapObject = player.getSavedObject();
    }

    public void setSavedGameFlag(final boolean value) {
        this.savedGameFlag = value;
    }

    public void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
    }

    public void findPlayerAndAdjust() {
        // Find the player, adjust player location
        final Map m = DungeonDiverII.getApplication().getVariablesManager()
                .getMap();
        final int w = this.plMgr.getPlayerLocationW();
        m.findPlayer();
        this.plMgr.setPlayerLocation(m.getFindResultColumn(),
                m.getFindResultRow(), m.getFindResultFloor(), w);
        this.resetViewingWindow();
        this.redrawMap();
    }

    public void updatePositionRelative(final int x, final int y) {
        boolean redrawsSuspended = false;
        int px = this.plMgr.getPlayerLocationX();
        int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        boolean proceed = false;
        MapObject o = new Empty();
        MapObject groundInto = new Empty();
        MapObject below = null;
        MapObject nextBelow = null;
        MapObject nextAbove = null;
        do {
            try {
                try {
                    o = m.getCell(px + x, py + y, pz, MapConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    o = new Empty();
                }
                try {
                    below = m.getCell(px, py, pz, MapConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    below = new Empty();
                }
                try {
                    nextBelow = m.getCell(px + x, py + y, pz,
                            MapConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextBelow = new Empty();
                }
                try {
                    nextAbove = m.getCell(px + x, py + y, pz,
                            MapConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextAbove = new Wall();
                }
                try {
                    proceed = o.preMoveCheck(true, px + x, py + y, pz, m);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    proceed = true;
                } catch (final InfiniteRecursionException ir) {
                    proceed = false;
                }
            } catch (final NullPointerException np) {
                proceed = false;
                o = new Empty();
            }
            if (proceed) {
                this.plMgr.savePlayerLocation();
                this.vwMgr.saveViewingWindow();
                try {
                    if (this.checkSolid(this.savedMapObject, below, nextBelow,
                            nextAbove)) {
                        m.setCell(this.savedMapObject, px, py, pz,
                                MapConstants.LAYER_OBJECT);
                        this.plMgr.offsetPlayerLocationX(x);
                        this.plMgr.offsetPlayerLocationY(y);
                        px += x;
                        py += y;
                        this.vwMgr.offsetViewingWindowLocationX(y);
                        this.vwMgr.offsetViewingWindowLocationY(x);
                        this.savedMapObject = m.getCell(px, py, pz,
                                MapConstants.LAYER_OBJECT);
                        m.setCell(new Player(), px, py, pz,
                                MapConstants.LAYER_OBJECT);
                        app.getVariablesManager().setDirty(true);
                        this.redrawMap();
                        groundInto = m.getCell(px, py, pz,
                                MapConstants.LAYER_GROUND);
                        if (groundInto.overridesDefaultPostMove()) {
                            GameScriptRunner.runScript(groundInto
                                    .getPostMoveScript(false, px, py, pz, m));
                            if (!this.savedMapObject
                                    .isOfType(TypeConstants.TYPE_PASS_THROUGH)) {
                                GameScriptRunner
                                        .runScript(this.savedMapObject
                                                .getPostMoveScript(false, px,
                                                        py, pz, m));
                            }
                        } else {
                            GameScriptRunner.runScript(this.savedMapObject
                                    .getPostMoveScript(false, px, py, pz, m));
                        }
                    } else {
                        // Move failed - object is solid in that direction
                        this.fireMoveFailedActions(px + x, py + y,
                                this.savedMapObject, below, nextBelow,
                                nextAbove);
                        proceed = false;
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.vwMgr.restoreViewingWindow();
                    this.plMgr.restorePlayerLocation();
                    m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(),
                            this.plMgr.getPlayerLocationZ(),
                            MapConstants.LAYER_OBJECT);
                    // Move failed - attempted to go outside the map
                    DungeonDiverII.getApplication().showMessage(
                            "Can't go that way");
                    o = new Empty();
                    proceed = false;
                }
            } else {
                // Move failed - pre-move check failed
                GameScriptRunner.runScript(o.getMoveFailedScript(false, px + x,
                        py + y, pz));
                proceed = false;
            }
            if (redrawsSuspended
                    && !this.checkLoopCondition(proceed, groundInto, below,
                            nextBelow, nextAbove)) {
                // Redraw post-suspend
                this.redrawMap();
                redrawsSuspended = false;
            }
        } while (this.checkLoopCondition(proceed, groundInto, below, nextBelow,
                nextAbove));
        // Post-move actions
        final ArrayList<GameScriptArea> areaScripts = app.getVariablesManager()
                .getMap().getScriptAreasAtPoint(new Point(x, y));
        for (final GameScriptArea gsa : areaScripts) {
            GameScriptRunner.runScript(gsa);
        }
        GameManager.fireStepActions();
        this.getStatGUI().updateStats();
        this.checkGameOver();
    }

    public void updatePositionRelativeNoEvents(final int x, final int y) {
        boolean redrawsSuspended = false;
        int px = this.plMgr.getPlayerLocationX();
        int py = this.plMgr.getPlayerLocationY();
        final int pz = this.plMgr.getPlayerLocationZ();
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        boolean proceed = false;
        MapObject o = new Empty();
        MapObject groundInto = new Empty();
        MapObject below = null;
        MapObject nextBelow = null;
        MapObject nextAbove = null;
        do {
            try {
                try {
                    o = m.getCell(px + x, py + y, pz, MapConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    o = new Empty();
                }
                try {
                    below = m.getCell(px, py, pz, MapConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    below = new Empty();
                }
                try {
                    nextBelow = m.getCell(px + x, py + y, pz,
                            MapConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextBelow = new Empty();
                }
                try {
                    nextAbove = m.getCell(px + x, py + y, pz,
                            MapConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextAbove = new Wall();
                }
                try {
                    proceed = o.preMoveCheck(true, px + x, py + y, pz, m);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    proceed = true;
                } catch (final InfiniteRecursionException ir) {
                    proceed = false;
                }
            } catch (final NullPointerException np) {
                proceed = false;
                o = new Empty();
            }
            if (proceed) {
                this.plMgr.savePlayerLocation();
                this.vwMgr.saveViewingWindow();
                try {
                    if (this.checkSolid(this.savedMapObject, below, nextBelow,
                            nextAbove)) {
                        m.setCell(this.savedMapObject, px, py, pz,
                                MapConstants.LAYER_OBJECT);
                        this.plMgr.offsetPlayerLocationX(x);
                        this.plMgr.offsetPlayerLocationY(y);
                        px += x;
                        py += y;
                        this.vwMgr.offsetViewingWindowLocationX(y);
                        this.vwMgr.offsetViewingWindowLocationY(x);
                        this.savedMapObject = m.getCell(px, py, pz,
                                MapConstants.LAYER_OBJECT);
                        m.setCell(new Player(), px, py, pz,
                                MapConstants.LAYER_OBJECT);
                        app.getVariablesManager().setDirty(true);
                        this.redrawMap();
                        groundInto = m.getCell(px, py, pz,
                                MapConstants.LAYER_GROUND);
                        this.outputFrame.pack();
                    } else {
                        // Move failed - object is solid in that direction
                        proceed = false;
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.vwMgr.restoreViewingWindow();
                    this.plMgr.restorePlayerLocation();
                    m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                            this.plMgr.getPlayerLocationY(),
                            this.plMgr.getPlayerLocationZ(),
                            MapConstants.LAYER_OBJECT);
                    // Move failed - attempted to go outside the map
                    o = new Empty();
                    proceed = false;
                }
            } else {
                // Move failed - pre-move check failed
                proceed = false;
            }
            if (redrawsSuspended
                    && !this.checkLoopCondition(proceed, groundInto, below,
                            nextBelow, nextAbove)) {
                // Redraw post-suspend
                this.redrawMap();
                redrawsSuspended = false;
            }
        } while (this.checkLoopCondition(proceed, groundInto, below, nextBelow,
                nextAbove));
        this.getStatGUI().updateStats();
        this.checkGameOver();
    }

    private boolean checkLoopCondition(final boolean proceed,
            final MapObject groundInto, final MapObject below,
            final MapObject nextBelow, final MapObject nextAbove) {
        return proceed
                && !groundInto.hasFrictionConditionally(false)
                && this.checkSolid(this.savedMapObject, below, nextBelow,
                        nextAbove);
    }

    private boolean checkSolid(final MapObject inside, final MapObject below,
            final MapObject nextBelow, final MapObject nextAbove) {
        final Map m = DungeonDiverII.getApplication().getVariablesManager()
                .getMap();
        final int z = this.plMgr.getPlayerLocationZ();
        final boolean insideSolid = inside.isConditionallySolid(m, z);
        final boolean belowSolid = below.isConditionallySolid(m, z);
        final boolean nextBelowSolid = nextBelow.isConditionallySolid(m, z);
        final boolean nextAboveSolid = nextAbove.isConditionallySolid(m, z);
        if (insideSolid || belowSolid || nextBelowSolid || nextAboveSolid) {
            return false;
        } else {
            return true;
        }
    }

    private void fireMoveFailedActions(final int x, final int y,
            final MapObject inside, final MapObject below,
            final MapObject nextBelow, final MapObject nextAbove) {
        final Map m = DungeonDiverII.getApplication().getVariablesManager()
                .getMap();
        final int z = this.plMgr.getPlayerLocationZ();
        final boolean insideSolid = inside.isConditionallySolid(m, z);
        final boolean belowSolid = below.isConditionallySolid(m, z);
        final boolean nextBelowSolid = nextBelow.isConditionallySolid(m, z);
        final boolean nextAboveSolid = nextAbove.isConditionallySolid(m, z);
        if (insideSolid) {
            GameScriptRunner.runScript(inside.getMoveFailedScript(false, x, y,
                    z));
        }
        if (belowSolid) {
            GameScriptRunner.runScript(below
                    .getMoveFailedScript(false, x, y, z));
        }
        if (nextBelowSolid) {
            GameScriptRunner.runScript(nextBelow.getMoveFailedScript(false, x,
                    y, z));
        }
        if (nextAboveSolid) {
            GameScriptRunner.runScript(nextAbove.getMoveFailedScript(false, x,
                    y, z));
        }
    }

    public void updatePositionAbsolute(final int x, final int y, final int z) {
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        try {
            m.getCell(x, y, z, MapConstants.LAYER_OBJECT).preMoveCheck(true, x,
                    y, z, m);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
        this.plMgr.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MapConstants.LAYER_OBJECT)
                    .isConditionallySolid(m, z)) {
                m.setCell(this.savedMapObject, this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                this.plMgr.setPlayerLocation(x, y, z, 0);
                this.vwMgr.setViewingWindowLocationX(this.plMgr
                        .getPlayerLocationY() - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(this.plMgr
                        .getPlayerLocationX() - this.vwMgr.getOffsetFactorY());
                this.savedMapObject = m.getCell(
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                this.redrawMap();
                app.getVariablesManager().setDirty(true);
                GameScriptRunner.runScript(this.savedMapObject
                        .getPostMoveScript(false, x, y, z, m));
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(), MapConstants.LAYER_OBJECT);
            DungeonDiverII.getApplication().showMessage(
                    "Can't go outside the map");
        } catch (final NullPointerException np) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(), MapConstants.LAYER_OBJECT);
            DungeonDiverII.getApplication().showMessage(
                    "Can't go outside the map");
        }
        GameManager.fireStepActions();
    }

    public void updatePositionAbsoluteNoEvents(final int x, final int y,
            final int z) {
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        this.plMgr.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, MapConstants.LAYER_OBJECT)
                    .isConditionallySolid(m, z)) {
                m.setCell(this.savedMapObject, this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                this.plMgr.setPlayerLocation(x, y, z,
                        this.plMgr.getPlayerLocationW());
                this.vwMgr.setViewingWindowLocationX(this.plMgr
                        .getPlayerLocationY() - this.vwMgr.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(this.plMgr
                        .getPlayerLocationX() - this.vwMgr.getOffsetFactorY());
                this.savedMapObject = m.getCell(
                        this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                        this.plMgr.getPlayerLocationY(),
                        this.plMgr.getPlayerLocationZ(),
                        MapConstants.LAYER_OBJECT);
                this.redrawMap();
                app.getVariablesManager().setDirty(true);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(), MapConstants.LAYER_OBJECT);
            DungeonDiverII.getApplication().showMessage(
                    "Can't go outside the map");
        } catch (final NullPointerException np) {
            this.plMgr.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            m.setCell(new Player(), this.plMgr.getPlayerLocationX(),
                    this.plMgr.getPlayerLocationY(),
                    this.plMgr.getPlayerLocationZ(), MapConstants.LAYER_OBJECT);
            DungeonDiverII.getApplication().showMessage(
                    "Can't go outside the map");
        }
    }

    public void redrawMap() {
        // Draw the map, if it is visible
        if (this.outputFrame.isVisible()) {
            // Rebuild draw grid
            final EmptyBorder eb = new EmptyBorder(0, 0, 0, 0);
            this.outputPane.removeAll();
            for (int x = 0; x < this.vwMgr.getViewingWindowSizeX(); x++) {
                for (int y = 0; y < this.vwMgr.getViewingWindowSizeY(); y++) {
                    this.drawGrid[x][y] = new JLabel();
                    // Fix to make draw grid line up properly
                    this.drawGrid[x][y].setBorder(eb);
                    this.outputPane.add(this.drawGrid[x][y]);
                }
            }
            this.redrawMapNoRebuild();
        }
    }

    public void redrawMapNoRebuild() {
        // Draw the map, if it is visible
        if (this.outputFrame.isVisible()) {
            final Application app = DungeonDiverII.getApplication();
            int x, y, u, v;
            int xFix, yFix;
            boolean visible;
            u = this.plMgr.getPlayerLocationX();
            v = this.plMgr.getPlayerLocationY();
            for (x = this.vwMgr.getViewingWindowLocationX(); x <= this.vwMgr
                    .getLowerRightViewingWindowLocationX(); x++) {
                for (y = this.vwMgr.getViewingWindowLocationY(); y <= this.vwMgr
                        .getLowerRightViewingWindowLocationY(); y++) {
                    xFix = x - this.vwMgr.getViewingWindowLocationX();
                    yFix = y - this.vwMgr.getViewingWindowLocationY();
                    visible = app
                            .getVariablesManager()
                            .getMap()
                            .isSquareVisible(u, v, y, x,
                                    this.plMgr.getPlayerLocationZ());
                    try {
                        if (visible) {
                            MapObject obj1, obj2;
                            obj1 = app
                                    .getVariablesManager()
                                    .getMap()
                                    .getCell(y, x,
                                            this.plMgr.getPlayerLocationZ(),
                                            MapConstants.LAYER_GROUND);
                            obj2 = app
                                    .getVariablesManager()
                                    .getMap()
                                    .getCell(y, x,
                                            this.plMgr.getPlayerLocationZ(),
                                            MapConstants.LAYER_OBJECT);
                            String name1, name2;
                            name1 = obj1.gameRenderHook(y, x, this.plMgr
                                    .getPlayerLocationZ(), app
                                    .getVariablesManager().getMap());
                            name2 = obj2.gameRenderHook(y, x, this.plMgr
                                    .getPlayerLocationZ(), app
                                    .getVariablesManager().getMap());
                            this.drawGrid[xFix][yFix].setIcon(ImageTransformer
                                    .getCompositeImage(name1,
                                            obj1.getGameName(),
                                            obj1.getTemplateTransform(), name2,
                                            obj2.getGameName(),
                                            obj2.getTemplateTransform()));
                        } else {
                            this.drawGrid[xFix][yFix]
                                    .setIcon(MapObjectImageManager.getImage(
                                            "Darkness", "Darkness", null));
                        }
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        this.drawGrid[xFix][yFix]
                                .setIcon(MapObjectImageManager.getImage(
                                        new EmptyVoid()
                                                .gameRenderHook(
                                                        y,
                                                        x,
                                                        this.plMgr
                                                                .getPlayerLocationZ(),
                                                        app.getVariablesManager()
                                                                .getMap()),
                                        "Void", null));
                    } catch (final NullPointerException np) {
                        this.drawGrid[xFix][yFix]
                                .setIcon(MapObjectImageManager.getImage(
                                        new EmptyVoid()
                                                .gameRenderHook(
                                                        y,
                                                        x,
                                                        this.plMgr
                                                                .getPlayerLocationZ(),
                                                        app.getVariablesManager()
                                                                .getMap()),
                                        "Void", null));
                    }
                }
            }
            this.setStatusMessage(" ");
            this.outputFrame.pack();
        }
    }

    public void resetViewingWindowAndPlayerLocation() {
        this.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        this.vwMgr.setViewingWindowLocationX(this.plMgr.getPlayerLocationY()
                - this.vwMgr.getOffsetFactorX());
        this.vwMgr.setViewingWindowLocationY(this.plMgr.getPlayerLocationX()
                - this.vwMgr.getOffsetFactorY());
    }

    public void resetPlayerLocation() {
        this.resetPlayerLocation(0);
    }

    public void resetPlayerLocation(final int level) {
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        m.switchLevel(level);
        m.findStart();
        this.plMgr.setPlayerLocation(m.getStartColumn(), m.getStartRow(),
                m.getStartFloor(), level);
    }

    public void goToLevel(final int level) {
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        final boolean levelExists = m.doesLevelExist(level);
        if (levelExists) {
            this.saveSavedMapObject();
            m.switchLevel(level);
            this.plMgr.setPlayerLocationW(level);
            this.findPlayerAndAdjust();
            this.restoreSavedMapObject();
        } else {
            this.exitGame();
        }
    }

    public void goToLevelRelative(final int level) {
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        final boolean levelExists = m.doesLevelExistOffset(level);
        if (!levelExists && m.isLevelOffsetValid(level)) {
            // Create the level
            this.saveSavedMapObject();
            m.addLevel(Support.MAP_ROWS, Support.MAP_COLS, Support.MAP_FLOORS);
            m.fillLevelRandomly(new Tile());
            this.restoreSavedMapObject();
        }
        this.saveSavedMapObject();
        m.switchLevelOffset(level);
        this.plMgr.setPlayerLocationW(m.getActiveLevelNumber());
        this.findPlayerAndAdjust();
        this.restoreSavedMapObject();
    }

    public void goToFloor(final int floor) {
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        final boolean floorExists = m.doesFloorExist(floor);
        if (floorExists) {
            final int px = this.plMgr.getPlayerLocationX();
            final int py = this.plMgr.getPlayerLocationY();
            int pz = this.plMgr.getPlayerLocationZ();
            this.saveSavedMapObject();
            this.plMgr.setPlayerLocationZ(floor);
            m.setCell(this.savedMapObject, px, py, pz,
                    MapConstants.LAYER_OBJECT);
            pz = floor;
            this.savedMapObject = m.getCell(px, py, pz,
                    MapConstants.LAYER_OBJECT);
            m.setCell(new Player(), px, py, pz, MapConstants.LAYER_OBJECT);
            this.restoreSavedMapObject();
        }
    }

    public void goToFloorRelative(final int floor) {
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        final boolean floorExists = m.doesFloorExist(this.plMgr
                .getPlayerLocationZ() + floor);
        if (floorExists) {
            final int px = this.plMgr.getPlayerLocationX();
            final int py = this.plMgr.getPlayerLocationY();
            int pz = this.plMgr.getPlayerLocationZ();
            this.saveSavedMapObject();
            this.plMgr.setPlayerLocationZ(pz + floor);
            m.setCell(this.savedMapObject, px, py, pz,
                    MapConstants.LAYER_OBJECT);
            pz += floor;
            this.savedMapObject = m.getCell(px, py, pz,
                    MapConstants.LAYER_OBJECT);
            m.setCell(new Player(), px, py, pz, MapConstants.LAYER_OBJECT);
            this.restoreSavedMapObject();
        }
    }

    public void exitGame() {
        this.stateChanged = true;
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        // Restore the map
        m.restore();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            this.resetViewingWindowAndPlayerLocation();
        }
        // Reset saved game flag
        this.savedGameFlag = false;
        app.getVariablesManager().setDirty(false);
        // Exit game
        this.hideOutput();
        app.setInGame(false);
        app.getGUIManager().showGUI();
    }

    public void checkGameOver() {
        if (!PartyManager.getParty().isAlive()) {
            this.gameOver();
        }
    }

    private void gameOver() {
        SoundManager.playSound(GameSoundConstants.SOUND_PARTY_SLAIN);
        CommonDialogs.showDialog("You have died - Game Over!");
        if (this.st.checkScore()) {
            this.st.commitScore();
        }
        this.exitGame();
    }

    public JFrame getOutputFrame() {
        return this.outputFrame;
    }

    public void decay() {
        this.savedMapObject = new Empty();
    }

    public void showEquipmentDialog() {
        final PartyMember member = PartyManager.getParty().pickOnePartyMember();
        if (member != null) {
            final String[] equipString = member.getItems()
                    .generateEquipmentStringArray();
            CommonDialogs.showInputDialog("Equipment", "Equipment",
                    equipString, equipString[0]);
        }
    }

    public void showItemInventoryDialog() {
        final PartyMember member = PartyManager.getParty().pickOnePartyMember();
        if (member != null) {
            final String[] invString = member.getItems()
                    .generateInventoryStringArray();
            CommonDialogs.showInputDialog("Item Inventory", "Item Inventory",
                    invString, invString[0]);
        }
    }

    public void identifyObject(final int x, final int y) {
        final Application app = DungeonDiverII.getApplication();
        final Map m = app.getVariablesManager().getMap();
        final int xOffset = this.vwMgr.getViewingWindowLocationX()
                - this.vwMgr.getOffsetFactorX();
        final int yOffset = this.vwMgr.getViewingWindowLocationY()
                - this.vwMgr.getOffsetFactorY();
        final int destX = x / MapObjectImageManager.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / MapObjectImageManager.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = this.plMgr.getPlayerLocationZ();
        try {
            final MapObject target1 = m.getCell(destX, destY, destZ,
                    MapConstants.LAYER_GROUND);
            final MapObject target2 = m.getCell(destX, destY, destZ,
                    MapConstants.LAYER_OBJECT);
            target1.determineCurrentAppearance(destX, destY, destZ, m);
            target2.determineCurrentAppearance(destX, destY, destZ, m);
            final String gameName1 = target1.getGameName();
            final String gameName2 = target2.getGameName();
            DungeonDiverII.getApplication().showMessage(
                    gameName2 + " on " + gameName1);
            SoundManager.playSound(GameSoundConstants.SOUND_IDENTIFY);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            final EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(destX, destY, destZ, m);
            DungeonDiverII.getApplication().showMessage(ev.getGameName());
            SoundManager.playSound(GameSoundConstants.SOUND_IDENTIFY);
        }
    }

    public void loadGameHookX(final XDataReader mapFile) throws IOException {
        PartyManager.loadGameHook(mapFile);
    }

    public void saveGameHookX(final XDataWriter mapFile) throws IOException {
        PartyManager.saveGameHook(mapFile);
    }

    public void playMap() {
        final Application app = DungeonDiverII.getApplication();
        app.getGUIManager().hideGUI();
        app.setInGame(true);
        if (this.stateChanged) {
            // Initialize only if the map state has changed
            app.getVariablesManager().getMap().fillLevelRandomly(new Tile());
            this.savedMapObject = new Empty();
            this.stateChanged = false;
        }
        // Make sure message area is attached to the border pane
        this.borderPane.removeAll();
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane
                .add(this.getStatGUI().getStatsPane(), BorderLayout.EAST);
        this.resetViewingWindowAndPlayerLocation();
        this.showOutput();
        this.getStatGUI().updateImages();
        this.getStatGUI().updateStats();
        this.checkGameOver();
        this.redrawMap();
    }

    public void showOutput() {
        final Application app = DungeonDiverII.getApplication();
        app.getMenuManager().setGameMenus();
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_EXPLORING)) {
            MusicManager.stopMusic();
            MusicManager.playMusic(MusicConstants.MUSIC_EXPLORING);
        }
        this.outputFrame.setVisible(true);
        this.outputFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
    }

    public void hideOutput() {
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_EXPLORING)) {
            if (MusicManager.isMusicPlaying()) {
                MusicManager.stopMusic();
            }
        }
        if (this.outputFrame != null) {
            this.outputFrame.setVisible(false);
        }
    }

    private void setUpGUI() {
        this.handler = new EventHandler();
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        this.outputFrame = new JFrame(DungeonDiverII.getProgramName());
        final Image iconlogo = DungeonDiverII.getApplication().getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.outputPane = new Container();
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.outputPane.setLayout(new GridLayout(this.vwMgr
                .getViewingWindowSizeX(), this.vwMgr.getViewingWindowSizeY()));
        this.outputFrame.setResizable(false);
        this.outputFrame.addKeyListener(this.handler);
        this.outputFrame.addWindowListener(this.handler);
        this.outputPane.addMouseListener(this.handler);
        this.drawGrid = new JLabel[this.vwMgr.getViewingWindowSizeX()][this.vwMgr
                .getViewingWindowSizeY()];
        for (int x = 0; x < this.vwMgr.getViewingWindowSizeX(); x++) {
            for (int y = 0; y < this.vwMgr.getViewingWindowSizeY(); y++) {
                this.drawGrid[x][y] = new JLabel();
                // Mac OS X-specific fix to make draw grid line up properly
                if (System.getProperty("os.name").startsWith("Mac OS X")) {
                    this.drawGrid[x][y].setBorder(new EmptyBorder(0, 0, 0, 0));
                }
                this.outputPane.add(this.drawGrid[x][y]);
            }
        }
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane
                .add(this.getStatGUI().getStatsPane(), BorderLayout.EAST);
    }

    private class EventHandler implements KeyListener, WindowListener,
            MouseListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            if (!PreferencesManager.oneMove()) {
                this.handleMovement(e);
            }
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            if (PreferencesManager.oneMove()) {
                this.handleMovement(e);
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            // Do nothing
        }

        public void handleMovement(final KeyEvent e) {
            try {
                boolean modKeyDown;
                if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
                    modKeyDown = e.isMetaDown();
                } else {
                    modKeyDown = e.isControlDown();
                }
                if (!modKeyDown) {
                    final GameManager gm = GameManager.this;
                    final int keyCode = e.getKeyCode();
                    switch (keyCode) {
                    case KeyEvent.VK_NUMPAD4:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        gm.updatePositionRelative(-1, 0);
                        break;
                    case KeyEvent.VK_NUMPAD2:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_X:
                        gm.updatePositionRelative(0, 1);
                        break;
                    case KeyEvent.VK_NUMPAD6:
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        gm.updatePositionRelative(1, 0);
                        break;
                    case KeyEvent.VK_NUMPAD8:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        gm.updatePositionRelative(0, -1);
                        break;
                    case KeyEvent.VK_NUMPAD7:
                    case KeyEvent.VK_Q:
                        gm.updatePositionRelative(-1, -1);
                        break;
                    case KeyEvent.VK_NUMPAD9:
                    case KeyEvent.VK_E:
                        gm.updatePositionRelative(1, -1);
                        break;
                    case KeyEvent.VK_NUMPAD3:
                    case KeyEvent.VK_C:
                        gm.updatePositionRelative(1, 1);
                        break;
                    case KeyEvent.VK_NUMPAD1:
                    case KeyEvent.VK_Z:
                        gm.updatePositionRelative(-1, 1);
                        break;
                    case KeyEvent.VK_NUMPAD5:
                    case KeyEvent.VK_S:
                        gm.updatePositionRelative(0, 0);
                        break;
                    default:
                        break;
                    }
                }
            } catch (final Exception ex) {
                DungeonDiverII.getErrorLogger().logError(ex);
            }
        }

        // Handle windows
        @Override
        public void windowActivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosed(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent we) {
            try {
                final Application app = DungeonDiverII.getApplication();
                boolean success = false;
                int status = 0;
                if (app.getVariablesManager().getDirty()) {
                    status = app.getVariablesManager().showSaveDialog();
                    if (status == JOptionPane.YES_OPTION) {
                        success = app.getVariablesManager().saveGame();
                        if (success) {
                            app.getGameManager().exitGame();
                        }
                    } else if (status == JOptionPane.NO_OPTION) {
                        app.getGameManager().exitGame();
                    }
                } else {
                    app.getGameManager().exitGame();
                }
            } catch (final Exception ex) {
                DungeonDiverII.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void windowDeactivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowOpened(final WindowEvent we) {
            // Do nothing
        }

        // handle mouse
        @Override
        public void mousePressed(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseReleased(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            try {
                final GameManager gm = GameManager.this;
                if (e.isShiftDown()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.identifyObject(x, y);
                }
            } catch (final Exception ex) {
                DungeonDiverII.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            // Do nothing
        }
    }
}
