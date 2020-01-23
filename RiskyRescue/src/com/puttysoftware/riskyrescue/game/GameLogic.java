/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.game;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.riskyrescue.Application;
import com.puttysoftware.riskyrescue.RiskyRescue;
import com.puttysoftware.riskyrescue.Support;
import com.puttysoftware.riskyrescue.assets.MusicConstants;
import com.puttysoftware.riskyrescue.assets.MusicManager;
import com.puttysoftware.riskyrescue.assets.SoundConstants;
import com.puttysoftware.riskyrescue.assets.SoundManager;
import com.puttysoftware.riskyrescue.creatures.party.Party;
import com.puttysoftware.riskyrescue.creatures.party.PartyManager;
import com.puttysoftware.riskyrescue.game.scripts.InternalScriptRunner;
import com.puttysoftware.riskyrescue.map.Map;
import com.puttysoftware.riskyrescue.map.MapConstants;
import com.puttysoftware.riskyrescue.map.objects.Empty;
import com.puttysoftware.riskyrescue.map.objects.InfiniteRecursionException;
import com.puttysoftware.riskyrescue.map.objects.MapObject;
import com.puttysoftware.riskyrescue.map.objects.StairsDown;
import com.puttysoftware.riskyrescue.map.objects.StairsUp;
import com.puttysoftware.riskyrescue.map.objects.Tile;
import com.puttysoftware.riskyrescue.map.objects.Wall;
import com.puttysoftware.riskyrescue.prefs.PreferencesManager;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptArea;

public class GameLogic {
    // Fields
    private boolean savedGameFlag;
    private final ScoreTracker st;
    private boolean stateChanged;
    private final GameGUI gameGUI;
    private boolean runBattles;

    // Constructors
    public GameLogic() {
        this.gameGUI = new GameGUI();
        this.st = new ScoreTracker();
        this.savedGameFlag = false;
        this.stateChanged = true;
        this.runBattles = true;
    }

    // Methods
    public boolean newGame() {
        final JFrame owner = RiskyRescue.getApplication().getOutputFrame();
        if (this.savedGameFlag) {
            if (PartyManager.getParty() != null) {
                return true;
            } else {
                return PartyManager.createParty(owner);
            }
        } else {
            return PartyManager.createParty(owner);
        }
    }

    private GameViewingWindowManager getViewManager() {
        return this.gameGUI.getViewManager();
    }

    public void addToScore(final long points) {
        this.st.addToScore(points);
    }

    public void showCurrentScore() {
        this.st.showCurrentScore();
    }

    private static void fireStepActions(final int x, final int y, final int z) {
        RiskyRescue.getApplication().getScenarioManager().getMap()
                .updateVisibleSquares(x, y, z);
    }

    public void updateStats() {
        this.gameGUI.updateStats();
    }

    public void stateChanged() {
        this.stateChanged = true;
    }

    public void setSavedGameFlag(final boolean value) {
        this.savedGameFlag = value;
    }

    public void skipBattlesOnce() {
        this.runBattles = false;
    }

    public void setStatusMessage(final String msg) {
        this.gameGUI.setStatusMessage(msg);
    }

    public void updatePositionRelative(final int x, final int y, final int z) {
        final Map m = RiskyRescue.getApplication().getScenarioManager()
                .getMap();
        boolean redrawsSuspended = false;
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        final Application app = RiskyRescue.getApplication();
        boolean proceed = false;
        MapObject o = new Empty();
        MapObject groundInto = new Empty();
        MapObject below = null;
        MapObject nextBelow = null;
        MapObject nextAbove = null;
        try {
            o = m.getCell(px + x, py + y, pz + z, MapConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            o = new Empty();
        }
        try {
            below = m.getCell(px, py, pz, MapConstants.LAYER_GROUND);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            below = new Empty();
        }
        try {
            nextBelow = m.getCell(px + x, py + y, pz + z,
                    MapConstants.LAYER_GROUND);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            nextBelow = new Empty();
        }
        try {
            nextAbove = m.getCell(px + x, py + y, pz + z,
                    MapConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            nextAbove = new Wall();
        }
        try {
            proceed = o.preMoveCheck(true, px + x, py + y, pz + z, m);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            proceed = true;
        } catch (final InfiniteRecursionException ir) {
            proceed = false;
        }
        if (proceed) {
            m.savePlayerLocation();
            this.getViewManager().saveViewingWindow();
            try {
                if (GameLogic.checkSolid(pz + z, GameLogic.getSavedMapObject(),
                        below, nextBelow, nextAbove)) {
                    m.offsetPlayerLocationX(x);
                    m.offsetPlayerLocationY(y);
                    m.offsetPlayerLocationZ(z);
                    px += x;
                    py += y;
                    pz += z;
                    this.getViewManager().offsetViewingWindowLocationX(y);
                    this.getViewManager().offsetViewingWindowLocationY(x);
                    GameLogic.setSavedMapObject(
                            m.getCell(px, py, pz, MapConstants.LAYER_OBJECT));
                    app.getScenarioManager().setDirty(true);
                    this.redrawMap();
                    groundInto = m.getCell(px, py, pz,
                            MapConstants.LAYER_GROUND);
                    if (groundInto.overridesDefaultPostMove()) {
                        InternalScriptRunner.runScript(groundInto
                                .getPostMoveScript(false, px, py, pz));
                        if (!(GameLogic.getSavedMapObject() instanceof Empty)) {
                            InternalScriptRunner.runScript(GameLogic
                                    .getSavedMapObject()
                                    .getPostMoveScript(false, px, py, pz));
                        }
                    } else {
                        InternalScriptRunner
                                .runScript(GameLogic.getSavedMapObject()
                                        .getPostMoveScript(false, px, py, pz));
                    }
                } else {
                    // Move failed - object is solid in that direction
                    GameLogic.fireMoveFailedActions(px + x, py + y, pz + z,
                            GameLogic.getSavedMapObject(), below, nextBelow,
                            nextAbove);
                    proceed = false;
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.getViewManager().restoreViewingWindow();
                m.restorePlayerLocation();
                // Move failed - attempted to go outside the map
                RiskyRescue.getApplication().showMessage("Can't go that way");
                o = new Empty();
                proceed = false;
            }
        } else {
            // Move failed - pre-move check failed
            InternalScriptRunner.runScript(MapObject.getMoveFailedScript(false,
                    px + x, py + y, pz + z));
            proceed = false;
        }
        if (redrawsSuspended) {
            // Redraw post-suspend
            this.redrawMap();
            redrawsSuspended = false;
        }
        if (this.runBattles) {
            if (!Support.inDebugMode()) {
                // Process random battles
                final ArrayList<InternalScriptArea> areaScripts = app
                        .getScenarioManager().getMap()
                        .getScriptAreasAtPoint(new Point(px, py), pz);
                for (final InternalScriptArea isa : areaScripts) {
                    InternalScriptRunner.runScript(isa);
                }
                // Process step actions
                GameLogic.fireStepActions(px, py, pz);
            }
        }
        if (Support.inDebugMode() && this.runBattles) {
            // Process step actions otherwise skipped
            GameLogic.fireStepActions(px, py, pz);
        }
        // Process general events
        this.updateStats();
        this.checkGameOver();
        if (!this.runBattles) {
            // Run battles previously skipped
            this.runBattles = true;
        }
    }

    private static MapObject getSavedMapObject() {
        return PartyManager.getParty().getPlayer().getSavedObject();
    }

    private static void setSavedMapObject(final MapObject newSaved) {
        PartyManager.getParty().getPlayer().setSavedObject(newSaved);
    }

    private static boolean checkSolid(final int z, final MapObject inside,
            final MapObject below, final MapObject nextBelow,
            final MapObject nextAbove) {
        final Map m = RiskyRescue.getApplication().getScenarioManager()
                .getMap();
        final boolean insideSolid = inside.isConditionallySolid(m, z);
        final boolean belowSolid = below.isConditionallySolid(m, z);
        final boolean nextBelowSolid = nextBelow.isConditionallySolid(m, z);
        final boolean nextAboveSolid = nextAbove.isConditionallySolid(m, z);
        return !(insideSolid || belowSolid || nextBelowSolid || nextAboveSolid);
    }

    private static void fireMoveFailedActions(final int x, final int y,
            final int z, final MapObject inside, final MapObject below,
            final MapObject nextBelow, final MapObject nextAbove) {
        final Map m = RiskyRescue.getApplication().getScenarioManager()
                .getMap();
        final boolean insideSolid = inside.isConditionallySolid(m, z);
        final boolean belowSolid = below.isConditionallySolid(m, z);
        final boolean nextBelowSolid = nextBelow.isConditionallySolid(m, z);
        final boolean nextAboveSolid = nextAbove.isConditionallySolid(m, z);
        if (insideSolid) {
            InternalScriptRunner
                    .runScript(MapObject.getMoveFailedScript(false, x, y, z));
        }
        if (belowSolid) {
            InternalScriptRunner
                    .runScript(MapObject.getMoveFailedScript(false, x, y, z));
        }
        if (nextBelowSolid) {
            InternalScriptRunner
                    .runScript(MapObject.getMoveFailedScript(false, x, y, z));
        }
        if (nextAboveSolid) {
            InternalScriptRunner
                    .runScript(MapObject.getMoveFailedScript(false, x, y, z));
        }
    }

    public void goToLevelRelative(final int level) {
        // Level change
        final Application app = RiskyRescue.getApplication();
        final Map m = app.getScenarioManager().getMap();
        final boolean levelExists = m.doesLevelExistOffset(level);
        if (!levelExists && m.isLevelOffsetValid(level)) {
            // The player will spawn atop stairs that need saving
            if (level < 0) {
                // Going up
                GameLogic.setSavedMapObject(new StairsDown());
            } else {
                // Going down
                GameLogic.setSavedMapObject(new StairsUp());
            }
            // Create the level
            m.addLevel(Support.getGameMapSize(), Support.getGameMapSize(),
                    Support.getGameMapFloorSize());
            m.fillLevelRandomly(new Tile(), new Empty());
            this.resetViewingWindow();
            m.resetVisibleSquares();
            final int px = m.getPlayerLocationX();
            final int py = m.getPlayerLocationY();
            final int pz = m.getPlayerLocationZ();
            m.updateVisibleSquares(px, py, pz);
        } else if (levelExists && m.isLevelOffsetValid(level)) {
            // The player will spawn atop stairs that need saving
            if (level < 0) {
                // Going up
                GameLogic.setSavedMapObject(new StairsDown());
            } else {
                // Going down
                GameLogic.setSavedMapObject(new StairsUp());
            }
            m.switchLevelOffset(level);
        } else {
            // Attempted to leave the dungeon...
            final Party party = PartyManager.getParty();
            if (party.getActivePCCount() == 2 && party.isAlive()) {
                // If our buddy is with us and everyone is alive, we win!
                this.victory();
            } else if (party.getActivePCCount() != 2 && party.isAlive()) {
                // Our buddy is not with us...
                this.gameOverMessage(
                        "Leaving the dungeon without your buddy, are you? GAME OVER!");
            } else if (party.getActivePCCount() == 2 && !party.isAlive()) {
                // Our buddy is with us, but one of us is dead :(
                this.gameOverMessage(
                        "Both of you must be alive to achieve victory... GAME OVER!");
            } else {
                // Something else?
                this.gameOverMessage(
                        "I have no clue how you got here, so GAME OVER!");
            }
            // End of the line...
            return;
        }
        this.resetViewingWindow();
        GameLogic.fireStepActions(m.getPlayerLocationX(),
                m.getPlayerLocationY(), m.getPlayerLocationZ());
        this.redrawMap();
        GameLogic.playMusic();
    }

    public void redrawMap() {
        this.gameGUI.redrawMap();
    }

    public void resetViewingWindow() {
        this.gameGUI.resetViewingWindow();
    }

    public void victory() {
        // Play victory sound
        SoundManager.playSound(SoundConstants.WIN_GAME);
        // Display YOU WIN! message
        CommonDialogs.showTitledDialog(
                "You have successfully escaped with your buddy! YOU WIN!",
                "MISSION COMPLETE");
        this.st.commitScore();
        this.exitGame();
    }

    public void exitGame() {
        this.stateChanged = true;
        final Application app = RiskyRescue.getApplication();
        // Reset saved game flag
        this.savedGameFlag = false;
        app.getScenarioManager().setDirty(false);
        // Exit game
        this.hideOutput();
        app.getGUIManager().showGUI();
    }

    public void checkGameOver() {
        if (!PartyManager.getParty().isAlive()) {
            this.gameOver();
        }
    }

    private void gameOverMessage(final String message) {
        SoundManager.playSound(SoundConstants.DEFEAT);
        CommonDialogs.showErrorDialog(message, "MISSION FAILED");
        MusicManager.stopMusic();
        this.st.commitScore();
        this.exitGame();
    }

    private void gameOver() {
        SoundManager.playSound(SoundConstants.DEFEAT);
        CommonDialogs.showDialog("You have died - Game Over!");
        MusicManager.stopMusic();
        this.st.commitScore();
        this.exitGame();
    }

    public JFrame getOutputFrame() {
        return this.gameGUI.getOutputFrame();
    }

    public void decay() {
        GameLogic.setSavedMapObject(new Empty());
    }

    public void playMap() {
        Map m;
        final Application app = RiskyRescue.getApplication();
        app.getGUIManager().hideGUI();
        app.setInGame();
        if (app.getScenarioManager().getLoaded()) {
            this.stateChanged = false;
        }
        if (this.stateChanged) {
            // Initialize only if the map state has changed
            RiskyRescue.newScenario();
            m = new Map();
            app.getScenarioManager().setMap(m);
            m.createMaps();
            m.addLevel(Support.getGameMapSize(), Support.getGameMapSize(),
                    Support.getGameMapFloorSize());
            m.fillLevelRandomly(new Tile(), new Empty());
            this.resetViewingWindow();
            final int px = m.getPlayerLocationX();
            final int py = m.getPlayerLocationY();
            final int pz = m.getPlayerLocationZ();
            m.updateVisibleSquares(px, py, pz);
            this.stateChanged = false;
        }
        // Make sure message area is attached to the border pane
        this.gameGUI.updateGameGUI();
        this.showOutput();
        this.redrawMap();
    }

    private static void playMusic() {
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_DUNGEON)) {
            MusicManager.stopMusic();
            MusicManager.playMusic(MusicConstants.DUNGEON,
                    PartyManager.getMapLevel());
        }
    }

    public void showOutput() {
        this.gameGUI.showOutput();
        GameLogic.playMusic();
    }

    public void hideOutput() {
        this.gameGUI.hideOutput();
    }
}
