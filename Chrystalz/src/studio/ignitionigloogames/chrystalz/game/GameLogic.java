/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.game;

import javax.swing.JFrame;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.GenerateTask;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Empty;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;

public final class GameLogic {
    // Fields
    private boolean savedGameFlag;
    private final GameViewingWindowManager vwMgr;
    private boolean stateChanged;
    private final GameGUI gui;
    private final MovementTask mt;

    // Constructors
    public GameLogic() {
        this.vwMgr = new GameViewingWindowManager();
        this.gui = new GameGUI();
        this.mt = new MovementTask(this.vwMgr, this.gui);
        this.mt.start();
        this.savedGameFlag = false;
        this.stateChanged = true;
    }

    // Methods
    public boolean newGame() {
        final JFrame owner = Chrystalz.getApplication().getOutputFrame();
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

    public void enableEvents() {
        this.mt.fireStepActions();
        this.gui.enableEvents();
    }

    public void disableEvents() {
        this.gui.disableEvents();
    }

    public void stopMovement() {
        this.mt.stopMovement();
    }

    public void viewingWindowSizeChanged() {
        this.gui.viewingWindowSizeChanged();
        this.resetViewingWindow();
    }

    public void stateChanged() {
        this.stateChanged = true;
    }

    public GameViewingWindowManager getViewManager() {
        return this.vwMgr;
    }

    public void setSavedGameFlag(final boolean value) {
        this.savedGameFlag = value;
    }

    public void setStatusMessage(final String msg) {
        this.gui.setStatusMessage(msg);
    }

    public void updatePositionRelative(final int dirX, final int dirY) {
        this.mt.moveRelative(dirX, dirY);
    }

    public void updatePositionAbsolute(final int x, final int y) {
        this.mt.moveAbsolute(x, y);
    }

    public void redrawDungeon() {
        this.gui.redrawDungeon();
    }

    public void resetViewingWindowAndPlayerLocation() {
        GameLogic.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        if (m != null && this.vwMgr != null) {
            this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                    - GameViewingWindowManager.getOffsetFactorX());
            this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                    - GameViewingWindowManager.getOffsetFactorY());
        }
    }

    public static void resetPlayerLocation() {
        GameLogic.resetPlayerLocation(0);
    }

    public static void resetPlayerLocation(final int level) {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        if (m != null) {
            m.switchLevel(level);
            m.setPlayerToStart();
        }
    }

    public void goToLevelOffset(final int level) {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        final boolean levelExists = m.doesLevelExistOffset(level);
        this.stopMovement();
        if (levelExists) {
            new LevelLoadTask(level).start();
        } else {
            new GenerateTask(false).start();
        }
    }

    public void exitGame() {
        this.stateChanged = true;
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        // Restore the maze
        m.restore();
        m.resetVisibleSquares();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            this.resetViewingWindowAndPlayerLocation();
        } else {
            app.getDungeonManager().setLoaded(false);
        }
        // Reset saved game flag
        this.savedGameFlag = false;
        app.getDungeonManager().setDirty(false);
        // Exit game
        this.hideOutput();
        app.getGUIManager().showGUI();
    }

    public JFrame getOutputFrame() {
        return this.gui.getOutputFrame();
    }

    public static void decay() {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        m.setCell(new Empty(), m.getPlayerLocationX(), m.getPlayerLocationY(),
                DungeonConstants.LAYER_OBJECT);
    }

    public static void morph(final AbstractGameObject morphInto) {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        m.setCell(morphInto, m.getPlayerLocationX(), m.getPlayerLocationY(),
                morphInto.getLayer());
    }

    public void keepNextMessage() {
        this.gui.keepNextMessage();
    }

    public void playDungeon() {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        if (app.getDungeonManager().getLoaded()) {
            this.gui.initViewManager();
            app.getGUIManager().hideGUI();
            if (this.stateChanged) {
                // Initialize only if the maze state has changed
                app.getDungeonManager().getDungeon().switchLevel(
                        app.getDungeonManager().getDungeon().getStartLevel());
                this.stateChanged = false;
            }
            // Make sure message area is attached to the border pane
            this.gui.updateGameGUI();
            // Make sure initial area player is in is visible
            final int px = m.getPlayerLocationX();
            final int py = m.getPlayerLocationY();
            m.updateVisibleSquares(px, py);
            this.showOutput();
            this.redrawDungeon();
        } else {
            CommonDialogs.showDialog("No Dungeon Opened");
        }
    }

    public void showOutput() {
        Chrystalz.getApplication().setMode(Application.STATUS_GAME);
        this.gui.showOutput();
    }

    public void showOutputAndKeepMusic() {
        Chrystalz.getApplication().setMode(Application.STATUS_GAME);
        this.gui.showOutputAndKeepMusic();
    }

    public void hideOutput() {
        this.stopMovement();
        this.gui.hideOutput();
    }
}
