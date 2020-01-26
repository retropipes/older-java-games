package net.worldwizard.mazerunner;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class MazeRunner {
    // Static fields
    private static MazeRunner application;
    // Fields
    private JFrame outputFrame, prefFrame, menuFrame;
    private Container outputPane, mainPrefPane, subPrefPane1, subPrefPane2,
            menuPane, borderPane;
    private JButton prefsOK, prefsCancel;
    private JComboBox<String> sets, sizes;
    private JCheckBox msgCannotGoOutsideMaze, msgFakeExit, msgInvisibleWall,
            msgCannotGoThatWay, msgSpecificMazeErrors, msgInvisibleTeleporter;
    private JMenuBar mainMenuBar;
    private JMenu fileMenu, editMenu, playMenu, gameMenu;
    private JMenuItem fileNew, fileOpen, fileClose, fileSave, fileSaveAs,
            fileExit;
    private JMenuItem editUndo, editRedo, editCut, editCopy, editPaste,
            editPreferences, editClearHistory;
    private JMenuItem playPlayMaze, playEditMaze;
    private JMenuItem gameInventory, gameUse;
    private KeyStroke fileNewAccel, fileOpenAccel, fileCloseAccel,
            fileSaveAccel, fileSaveAsAccel;
    private KeyStroke editUndoAccel, editRedoAccel, editCutAccel, editCopyAccel,
            editPasteAccel, editPreferencesAccel, editClearHistoryAccel;
    private KeyStroke playPlayMazeAccel, playEditMazeAccel;
    private KeyStroke gameInventoryAccel, gameUseAccel;
    private JLabel messageLabel;
    private Maze gameMaze;
    private int playerLocationX, playerLocationY, playerLocationZ,
            playerLocationW;
    private int mazeSizeX, mazeSizeY, mazeSizeZ, mazeSizeW;
    private int oldPlayerLocationX, oldPlayerLocationY, oldPlayerLocationZ,
            oldPlayerLocationW;
    private int upperLeftViewingWindowX, upperLeftViewingWindowY,
            lowerRightViewingWindowX, lowerRightViewingWindowY;
    private int oldUpperLeftViewingWindowX, oldUpperLeftViewingWindowY,
            oldLowerRightViewingWindowX, oldLowerRightViewingWindowY;
    private MazeObject savedMazeObject, objectBeingUsed;
    private MazeRunnerEventHandler handler;
    private Inventory inv;
    private boolean IN_GAME;
    private boolean IN_MENU;
    private boolean messageCannotGoOutsideMazeEnabled;
    private boolean messageFakeExitEnabled;
    private boolean messageInvisibleWallEnabled;
    private boolean messageCannotGoThatWayEnabled;
    private boolean messageSpecificMazeErrorsEnabled;
    private boolean messageInvisibleTeleporterEnabled;
    private boolean loaded, isDirty;
    private final int MAX_INVENTORY;
    private boolean pullInProgress, pushInProgress;
    private boolean using;
    // Public constants
    public static final int MESSAGE_CANNOT_GO_OUTSIDE_MAZE = 1;
    public static final int MESSAGE_FAKE_EXIT = 2;
    public static final int MESSAGE_INVISIBLE_WALL = 3;
    public static final int MESSAGE_CANNOT_GO_THAT_WAY = 4;
    public static final int MESSAGE_SPECIFIC_MAZE_ERRORS = 5;
    public static final int MESSAGE_INVISIBLE_TELEPORTER = 6;
    public static final int VIEWING_WINDOW_SIZE_X = 9;
    public static final int VIEWING_WINDOW_SIZE_Y = 9;
    public static final int MIN_VIEWING_WINDOW_X = -(MazeRunner.VIEWING_WINDOW_SIZE_X
            / 2);
    public static final int MIN_VIEWING_WINDOW_Y = -(MazeRunner.VIEWING_WINDOW_SIZE_Y
            / 2);

    // Constructors
    public MazeRunner() {
        this.setUpGUI();
        this.setPullInProgress(false);
        this.isDirty = false;
        this.MAX_INVENTORY = this.inv.getMaximum();
        this.setDefaultPrefs();
        this.setUsingAnItem(false);
        this.savedMazeObject = new MazeGround();
        this.showMenu();
    }

    // Static methods
    public static MazeRunner getApplication() {
        return MazeRunner.application;
    }

    // Methods
    public JLabel getMessageLabel() {
        return this.messageLabel;
    }

    public MazeObject getSavedMazeObject() {
        return this.savedMazeObject;
    }

    public void setSavedMazeObject(final MazeObject newSavedObject) {
        this.savedMazeObject = newSavedObject;
    }

    public boolean getInGame() {
        return this.IN_GAME;
    }

    public void setInGame(final boolean inGame) {
        this.IN_GAME = inGame;
    }

    public Maze getMaze() {
        return this.gameMaze;
    }

    public void setMaze(final Maze newMaze) {
        this.gameMaze = newMaze;
    }

    public MazeRunnerEventHandler getEventHandler() {
        return this.handler;
    }

    public MazeObject getMazeObject(final int x, final int y, final int z,
            final int w) {
        try {
            return this.gameMaze.getCell(x, y, z, w);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return null;
        }
    }

    public boolean usingAnItem() {
        return this.using;
    }

    public void setUsingAnItem(final boolean isUsing) {
        this.using = isUsing;
    }

    public boolean isFloorBelow() {
        try {
            this.gameMaze.getCell(this.playerLocationX, this.playerLocationY,
                    this.playerLocationZ - 1, this.playerLocationW);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public boolean isFloorAbove() {
        try {
            this.gameMaze.getCell(this.playerLocationX, this.playerLocationY,
                    this.playerLocationZ + 1, this.playerLocationW);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public boolean isLevelBelow() {
        try {
            this.gameMaze.getCell(this.playerLocationX, this.playerLocationY,
                    this.playerLocationZ, this.playerLocationW - 1);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public boolean isLevelAbove() {
        try {
            this.gameMaze.getCell(this.playerLocationX, this.playerLocationY,
                    this.playerLocationZ, this.playerLocationW + 1);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public boolean doesLevelExist(final int level) {
        try {
            this.gameMaze.getCell(this.playerLocationX, this.playerLocationY,
                    this.playerLocationZ, level);
            return true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public void setPullInProgress(final boolean pulling) {
        this.pullInProgress = pulling;
    }

    public boolean isPullInProgress() {
        return this.pullInProgress;
    }

    public void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
    }

    public boolean getMessageEnabled(final int msg) {
        switch (msg) {
        case MESSAGE_CANNOT_GO_OUTSIDE_MAZE:
            return this.messageCannotGoOutsideMazeEnabled;
        case MESSAGE_FAKE_EXIT:
            return this.messageFakeExitEnabled;
        case MESSAGE_INVISIBLE_WALL:
            return this.messageInvisibleWallEnabled;
        case MESSAGE_CANNOT_GO_THAT_WAY:
            return this.messageCannotGoThatWayEnabled;
        case MESSAGE_SPECIFIC_MAZE_ERRORS:
            return this.messageSpecificMazeErrorsEnabled;
        case MESSAGE_INVISIBLE_TELEPORTER:
            return this.messageInvisibleTeleporterEnabled;
        default:
            return false;
        }
    }

    private void setMessageEnabled(final int msg, final boolean status) {
        switch (msg) {
        case MESSAGE_CANNOT_GO_OUTSIDE_MAZE:
            this.messageCannotGoOutsideMazeEnabled = status;
            break;
        case MESSAGE_FAKE_EXIT:
            this.messageFakeExitEnabled = status;
            break;
        case MESSAGE_INVISIBLE_WALL:
            this.messageInvisibleWallEnabled = status;
            break;
        case MESSAGE_CANNOT_GO_THAT_WAY:
            this.messageCannotGoThatWayEnabled = status;
            break;
        case MESSAGE_SPECIFIC_MAZE_ERRORS:
            this.messageSpecificMazeErrorsEnabled = status;
            break;
        case MESSAGE_INVISIBLE_TELEPORTER:
            this.messageInvisibleTeleporterEnabled = status;
            break;
        default:
            break;
        }
    }

    public int showSaveDialog() {
        int status = 0;
        if (this.IN_GAME) {
            status = JOptionPane.showConfirmDialog(this.outputFrame,
                    "Do you want to save your game?", "Maze Runner",
                    JOptionPane.YES_NO_CANCEL_OPTION);
        } else {
            status = JOptionPane.showConfirmDialog(MazeMaker.getOutputFrame(),
                    "Do you want to save your maze?", "Maze Maker",
                    JOptionPane.YES_NO_CANCEL_OPTION);
        }
        return status;
    }

    public boolean getLoaded() {
        return this.loaded;
    }

    public void setLoaded(final boolean status) {
        this.loaded = status;
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        this.isDirty = newDirty;
    }

    public void updatePosition(final int x, final int y) {
        MazeObject o = new MazeWall();
        MazeObject pushedInto = null;
        MazeObject acted = null;
        final boolean isXNonZero = x != 0;
        final boolean isYNonZero = y != 0;
        int pullX = 0, pullY = 0, pushX = 0, pushY = 0;
        if (isXNonZero) {
            final int signX = (int) Math.signum(x);
            pushX = (Math.abs(x) + 1) * signX;
            pullX = (Math.abs(x) - 1) * signX;
        }
        if (isYNonZero) {
            final int signY = (int) Math.signum(y);
            pushY = (Math.abs(y) + 1) * signY;
            pullY = (Math.abs(y) - 1) * signY;
        }
        try {
            pushedInto = this.gameMaze.getCell(this.playerLocationX + pushX,
                    this.playerLocationY + pushY, this.playerLocationZ,
                    this.playerLocationW);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            pushedInto = new MazeWall();
        }
        do {
            try {
                o = this.gameMaze.getCell(this.playerLocationX + x,
                        this.playerLocationY + y, this.playerLocationZ,
                        this.playerLocationW);
                o.playSound();
                o.preMoveAction(true, this.playerLocationX + x,
                        this.playerLocationY + y, this.inv);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                o = new MazeGround();
            }
            this.oldPlayerLocationX = this.playerLocationX;
            this.oldPlayerLocationY = this.playerLocationY;
            this.oldUpperLeftViewingWindowX = this.upperLeftViewingWindowX;
            this.oldUpperLeftViewingWindowY = this.upperLeftViewingWindowY;
            this.oldLowerRightViewingWindowX = this.lowerRightViewingWindowX;
            this.oldLowerRightViewingWindowY = this.lowerRightViewingWindowY;
            try {
                if (!this.savedMazeObject.isConditionallyDirectionallySolid(
                        false, this.playerLocationX + x,
                        this.playerLocationY + y, this.inv)) {
                    if (!this.gameMaze
                            .getCell(this.playerLocationX + x,
                                    this.playerLocationY + y,
                                    this.playerLocationZ, this.playerLocationW)
                            .isConditionallyDirectionallySolid(true,
                                    this.playerLocationX + x,
                                    this.playerLocationY + y, this.inv)) {
                        this.gameMaze.setCell(this.savedMazeObject,
                                this.playerLocationX, this.playerLocationY,
                                this.playerLocationZ, this.playerLocationW);
                        acted = new MazeGround();
                        try {
                            acted = this.gameMaze.getCell(
                                    this.playerLocationX - x,
                                    this.playerLocationY - y,
                                    this.playerLocationZ, this.playerLocationW);
                        } catch (final ArrayIndexOutOfBoundsException ae) {
                            // Do nothing
                        }
                        if (acted.isPullable() && this.isPullInProgress()) {
                            final MazeGenericMovableObject pullable = (MazeGenericMovableObject) acted;
                            final MazeObject savedObject = pullable
                                    .getSavedObject();
                            final MazeObject pulledInto = this.gameMaze.getCell(
                                    this.playerLocationX + pullX,
                                    this.playerLocationY + pullY,
                                    this.playerLocationZ, this.playerLocationW);
                            if (savedObject.doesAcceptPullOut()
                                    && pulledInto.doesAcceptPullInto()) {
                                savedObject.pullOutAction(this.inv);
                                acted.pullAction(this.inv, pulledInto, x, y,
                                        pullX, pullY);
                                pulledInto.pullIntoAction(this.inv);
                            }
                        }
                        this.playerLocationX += x;
                        this.playerLocationY += y;
                        this.upperLeftViewingWindowX += y;
                        this.upperLeftViewingWindowY += x;
                        this.lowerRightViewingWindowX += y;
                        this.lowerRightViewingWindowY += x;
                        this.savedMazeObject = this.gameMaze.getCell(
                                this.playerLocationX, this.playerLocationY,
                                this.playerLocationZ, this.playerLocationW);
                        this.gameMaze.setCell(new MazePlayer(),
                                this.playerLocationX, this.playerLocationY,
                                this.playerLocationZ, this.playerLocationW);
                        this.redrawMaze();
                        this.isDirty = true;
                        this.savedMazeObject.postMoveAction(false,
                                this.playerLocationX, this.playerLocationY,
                                this.inv);
                    } else {
                        acted = this.gameMaze.getCell(this.playerLocationX + x,
                                this.playerLocationY + y, this.playerLocationZ,
                                this.playerLocationW);
                        if (acted.isPushable()) {
                            this.pushInProgress = true;
                            final MazeGenericMovableObject pushable = (MazeGenericMovableObject) acted;
                            final MazeObject savedObject = pushable
                                    .getSavedObject();
                            o = savedObject;
                            pushedInto = this.gameMaze.getCell(
                                    this.playerLocationX + pushX,
                                    this.playerLocationY + pushY,
                                    this.playerLocationZ, this.playerLocationW);
                            if (savedObject.doesAcceptPushOut()
                                    && pushedInto.doesAcceptPushInto()) {
                                this.gameMaze.setCell(this.savedMazeObject,
                                        this.playerLocationX,
                                        this.playerLocationY,
                                        this.playerLocationZ,
                                        this.playerLocationW);
                                savedObject.pushOutAction(this.inv);
                                acted.pushAction(this.inv, pushedInto, x, y,
                                        pushX, pushY);
                                pushedInto.pushIntoAction(this.inv, acted,
                                        this.playerLocationX + pushX,
                                        this.playerLocationY + pushY,
                                        this.playerLocationZ,
                                        this.playerLocationW);
                                o = pushable.getSavedObject();
                                this.playerLocationX += x;
                                this.playerLocationY += y;
                                this.upperLeftViewingWindowX += y;
                                this.upperLeftViewingWindowY += x;
                                this.lowerRightViewingWindowX += y;
                                this.lowerRightViewingWindowY += x;
                                this.savedMazeObject = this.gameMaze.getCell(
                                        this.playerLocationX,
                                        this.playerLocationY,
                                        this.playerLocationZ,
                                        this.playerLocationW);
                                this.gameMaze.setCell(new MazePlayer(),
                                        this.playerLocationX,
                                        this.playerLocationY,
                                        this.playerLocationZ,
                                        this.playerLocationW);
                                this.redrawMaze();
                                this.isDirty = true;
                                this.savedMazeObject.postMoveAction(false,
                                        this.playerLocationX,
                                        this.playerLocationY, this.inv);
                            }
                        } else if (acted.doesChainReact()) {
                            acted.chainReactionAction(this.playerLocationX + x,
                                    this.playerLocationY + y,
                                    this.playerLocationZ, this.playerLocationW);
                        }
                    }
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.playerLocationX = this.oldPlayerLocationX;
                this.playerLocationY = this.oldPlayerLocationY;
                this.upperLeftViewingWindowX = this.oldUpperLeftViewingWindowX;
                this.upperLeftViewingWindowY = this.oldUpperLeftViewingWindowY;
                this.lowerRightViewingWindowX = this.oldLowerRightViewingWindowX;
                this.lowerRightViewingWindowY = this.oldLowerRightViewingWindowY;
                this.gameMaze.setCell(new MazePlayer(), this.playerLocationX,
                        this.playerLocationY, this.playerLocationZ,
                        this.playerLocationW);
                if (this.getMessageEnabled(
                        MazeRunner.MESSAGE_CANNOT_GO_OUTSIDE_MAZE)) {
                    Messager.showMessage("Can't go outside the maze");
                }
                o = new MazeGround();
            }
        } while (!o.hasFriction() && !this.pushInProgress || this.pushInProgress
                && !o.hasFriction() && !pushedInto.hasFriction());
        this.pushInProgress = false;
    }

    public void updatePushedPosition(final int x, final int y, final int pushX,
            final int pushY, final MazeObject o1, final MazeObject o2) {
        try {
            this.gameMaze.setCell(o1, this.playerLocationX + x,
                    this.playerLocationY + y, this.playerLocationZ,
                    this.playerLocationW);
            this.gameMaze.setCell(o2, this.playerLocationX + pushX,
                    this.playerLocationY + pushY, this.playerLocationZ,
                    this.playerLocationW);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePulledPosition(final int x, final int y, final int pullX,
            final int pullY, final MazeObject o1, final MazeObject o2) {
        try {
            this.gameMaze.setCell(o1, this.playerLocationX - x,
                    this.playerLocationY - y, this.playerLocationZ,
                    this.playerLocationW);
            this.gameMaze.setCell(o2, this.playerLocationX - pullX,
                    this.playerLocationY - pullY, this.playerLocationZ,
                    this.playerLocationW);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void updatePushedIntoPositionAbsolute(final int x, final int y,
            final int z, final int w, final int x2, final int y2, final int z2,
            final int w2, final MazeGenericMovableObject pushedInto,
            final MazeObject source) {
        try {
            if (!this.gameMaze.getCell(x, y, z, w)
                    .isConditionallySolid(this.inv)) {
                pushedInto.setSavedObject(this.gameMaze.getCell(x, y, z, w));
                this.gameMaze.setCell(pushedInto, x, y, z, w);
                this.gameMaze.setCell(source, x2, y2, z2, w2);
                pushedInto.getSavedObject().pushIntoAction(this.inv, pushedInto,
                        x2, y2, z2 - 1, w2);
                this.redrawMaze();
                this.isDirty = true;
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.gameMaze.setCell(new MazeTile(), x2, y2, z2, w2);
        }
    }

    public void updatePositionAbsolute(final int x, final int y, final int z,
            final int w) {
        try {
            this.gameMaze.getCell(x, y, z, w).playSound();
            this.gameMaze.getCell(x, y, z, w).preMoveAction(true, x, y,
                    this.inv);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
        this.oldPlayerLocationX = this.playerLocationX;
        this.oldPlayerLocationY = this.playerLocationY;
        this.oldPlayerLocationZ = this.playerLocationZ;
        this.oldPlayerLocationW = this.playerLocationW;
        this.oldUpperLeftViewingWindowX = this.upperLeftViewingWindowX;
        this.oldUpperLeftViewingWindowY = this.upperLeftViewingWindowY;
        this.oldLowerRightViewingWindowX = this.lowerRightViewingWindowX;
        this.oldLowerRightViewingWindowY = this.lowerRightViewingWindowY;
        try {
            if (!this.gameMaze.getCell(x, y, z, w)
                    .isConditionallySolid(this.inv)) {
                this.gameMaze.setCell(this.savedMazeObject,
                        this.playerLocationX, this.playerLocationY,
                        this.playerLocationZ, this.playerLocationW);
                this.playerLocationX = x;
                this.playerLocationY = y;
                this.playerLocationZ = z;
                this.playerLocationW = w;
                this.upperLeftViewingWindowX = this.playerLocationY
                        - (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
                this.upperLeftViewingWindowY = this.playerLocationX
                        - (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
                this.lowerRightViewingWindowX = this.playerLocationY
                        + (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
                this.lowerRightViewingWindowY = this.playerLocationX
                        + (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
                this.savedMazeObject = this.gameMaze.getCell(
                        this.playerLocationX, this.playerLocationY,
                        this.playerLocationZ, this.playerLocationW);
                this.gameMaze.setCell(new MazePlayer(), this.playerLocationX,
                        this.playerLocationY, this.playerLocationZ,
                        this.playerLocationW);
                this.redrawMaze();
                this.isDirty = true;
                this.savedMazeObject.postMoveAction(false, x, y, this.inv);
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.playerLocationX = this.oldPlayerLocationX;
            this.playerLocationY = this.oldPlayerLocationY;
            this.playerLocationZ = this.oldPlayerLocationZ;
            this.playerLocationW = this.oldPlayerLocationW;
            this.upperLeftViewingWindowX = this.oldUpperLeftViewingWindowX;
            this.upperLeftViewingWindowY = this.oldUpperLeftViewingWindowY;
            this.lowerRightViewingWindowX = this.oldLowerRightViewingWindowX;
            this.lowerRightViewingWindowY = this.oldLowerRightViewingWindowY;
            this.gameMaze.setCell(new MazePlayer(), this.playerLocationX,
                    this.playerLocationY, this.playerLocationZ,
                    this.playerLocationW);
            if (this.getMessageEnabled(
                    MazeRunner.MESSAGE_CANNOT_GO_OUTSIDE_MAZE)) {
                Messager.showMessage("Can't go outside the maze");
            }
        }
    }

    public void updatePositionAbsoluteNoEvents(final int x, final int y,
            final int z, final int w) {
        try {
            this.gameMaze
                    .getCell(this.playerLocationX, this.playerLocationY,
                            this.playerLocationZ, this.playerLocationW)
                    .playSound();
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
        this.oldPlayerLocationX = this.playerLocationX;
        this.oldPlayerLocationY = this.playerLocationY;
        this.oldPlayerLocationZ = this.playerLocationZ;
        this.oldPlayerLocationW = this.playerLocationW;
        this.oldUpperLeftViewingWindowX = this.upperLeftViewingWindowX;
        this.oldUpperLeftViewingWindowY = this.upperLeftViewingWindowY;
        this.oldLowerRightViewingWindowX = this.lowerRightViewingWindowX;
        this.oldLowerRightViewingWindowY = this.lowerRightViewingWindowY;
        try {
            if (!this.gameMaze.getCell(x, y, z, w)
                    .isConditionallySolid(this.inv)) {
                this.gameMaze.setCell(this.savedMazeObject,
                        this.playerLocationX, this.playerLocationY,
                        this.playerLocationZ, this.playerLocationW);
                this.playerLocationX = x;
                this.playerLocationY = y;
                this.playerLocationZ = z;
                this.playerLocationW = w;
                this.upperLeftViewingWindowX = this.playerLocationY
                        - (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
                this.upperLeftViewingWindowY = this.playerLocationX
                        - (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
                this.lowerRightViewingWindowX = this.playerLocationY
                        + (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
                this.lowerRightViewingWindowY = this.playerLocationX
                        + (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
                this.savedMazeObject = this.gameMaze.getCell(
                        this.playerLocationX, this.playerLocationY,
                        this.playerLocationZ, this.playerLocationW);
                this.gameMaze.setCell(new MazePlayer(), this.playerLocationX,
                        this.playerLocationY, this.playerLocationZ,
                        this.playerLocationW);
                this.redrawMaze();
                this.isDirty = true;
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.playerLocationX = this.oldPlayerLocationX;
            this.playerLocationY = this.oldPlayerLocationY;
            this.playerLocationZ = this.oldPlayerLocationZ;
            this.playerLocationW = this.oldPlayerLocationW;
            this.upperLeftViewingWindowX = this.oldUpperLeftViewingWindowX;
            this.upperLeftViewingWindowY = this.oldUpperLeftViewingWindowY;
            this.lowerRightViewingWindowX = this.oldLowerRightViewingWindowX;
            this.lowerRightViewingWindowY = this.oldLowerRightViewingWindowY;
            this.gameMaze.setCell(new MazePlayer(), this.playerLocationX,
                    this.playerLocationY, this.playerLocationZ,
                    this.playerLocationW);
            if (this.getMessageEnabled(
                    MazeRunner.MESSAGE_CANNOT_GO_OUTSIDE_MAZE)) {
                Messager.showMessage("Can't go outside the maze");
            }
        }
    }

    private void redrawMaze() {
        // Draw the maze, if it is visible
        if (this.outputFrame.isVisible()) {
            int x, y;
            this.borderPane.removeAll();
            this.outputPane.removeAll();
            for (x = this.upperLeftViewingWindowX; x <= this.lowerRightViewingWindowX; x++) {
                for (y = this.upperLeftViewingWindowY; y <= this.lowerRightViewingWindowY; y++) {
                    try {
                        this.outputPane.add(new JLabel("",
                                this.gameMaze
                                        .getCell(y, x, this.playerLocationZ,
                                                this.playerLocationW)
                                        .gameRenderHook(y, x,
                                                this.playerLocationZ,
                                                this.playerLocationW),
                                SwingConstants.CENTER));
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        this.outputPane.add(new JLabel("",
                                new MazeVoid().gameRenderHook(y, x,
                                        this.playerLocationZ,
                                        this.playerLocationW),
                                SwingConstants.CENTER));
                    }
                }
            }
            this.borderPane.add(this.outputPane, BorderLayout.CENTER);
            this.borderPane.add(this.messageLabel, BorderLayout.SOUTH);
            this.setStatusMessage(" ");
            this.outputFrame.pack();
            this.showOutput();
        }
    }

    private void redrawMazeKeepMessage(final String msg) {
        this.redrawMaze();
        this.setStatusMessage(msg);
    }

    public void solvedLevel() {
        final boolean playerExists = this.gameMaze
                .findPlayerOnLevel(this.playerLocationW + 1);
        if (playerExists) {
            this.gameMaze.restoreLevel(this.playerLocationW);
            this.playerLocationX = this.gameMaze
                    .getStartColumn(this.playerLocationW + 1);
            this.playerLocationY = this.gameMaze
                    .getStartRow(this.playerLocationW + 1);
            this.playerLocationZ = this.gameMaze
                    .getStartFloor(this.playerLocationW + 1);
            this.playerLocationW = this.gameMaze.getStartLevel();
            this.upperLeftViewingWindowX = this.playerLocationX
                    - (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
            this.upperLeftViewingWindowY = this.playerLocationY
                    - (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
            this.lowerRightViewingWindowX = this.playerLocationX
                    + (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
            this.lowerRightViewingWindowY = this.playerLocationY
                    + (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
            this.decay();
            this.redrawMaze();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedLevelWarp(final int level) {
        final boolean playerExists = this.gameMaze.findPlayerOnLevel(level);
        if (playerExists) {
            this.gameMaze.restoreLevel(this.playerLocationW);
            this.playerLocationX = this.gameMaze.getStartColumn(level);
            this.playerLocationY = this.gameMaze.getStartRow(level);
            this.playerLocationZ = this.gameMaze.getStartFloor(level);
            this.playerLocationW = this.gameMaze.getStartLevel();
            this.upperLeftViewingWindowX = this.playerLocationX
                    - (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
            this.upperLeftViewingWindowY = this.playerLocationY
                    - (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
            this.lowerRightViewingWindowX = this.playerLocationX
                    + (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
            this.lowerRightViewingWindowY = this.playerLocationY
                    + (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
            this.decay();
            this.redrawMaze();
        } else {
            this.solvedMaze();
        }
    }

    public void solvedMaze() {
        if (this.IN_GAME) {
            // Restore the maze
            try {
                this.gameMaze.restoreLevel(this.playerLocationW);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                // Do nothing
            }
            final boolean playerExists = this.gameMaze.findPlayerOnLevel(0);
            if (playerExists) {
                this.playerLocationW = this.gameMaze.getStartLevel();
                this.playerLocationX = this.gameMaze
                        .getStartColumn(this.playerLocationW);
                this.playerLocationY = this.gameMaze
                        .getStartRow(this.playerLocationW);
                this.playerLocationZ = this.gameMaze
                        .getStartFloor(this.playerLocationW);
                this.upperLeftViewingWindowX = this.playerLocationX
                        - (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
                this.upperLeftViewingWindowY = this.playerLocationY
                        - (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
                this.lowerRightViewingWindowX = this.playerLocationX
                        + (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
                this.lowerRightViewingWindowY = this.playerLocationY
                        + (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
            } else {
                this.loaded = false;
            }
            // Wipe the inventory
            this.inv = new Inventory();
        }
        this.isDirty = false;
        if (this.IN_GAME) {
            this.hideOutput();
        } else {
            MazeMaker.hideOutput();
        }
        this.IN_GAME = true;
        this.showMenu();
    }

    public JFrame getOutputFrame() {
        if (!this.IN_GAME) {
            return MazeMaker.getOutputFrame();
        } else {
            if (this.outputFrame != null && this.outputFrame.isVisible()) {
                return this.outputFrame;
            } else if (this.menuFrame.isVisible()) {
                return this.menuFrame;
            } else {
                return null;
            }
        }
    }

    public JMenuBar getMainMenuBar() {
        return this.mainMenuBar;
    }

    private void setGameMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileClose.setEnabled(true);
        this.fileSave.setEnabled(true);
        this.fileSaveAs.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCut.setEnabled(false);
        this.editCopy.setEnabled(false);
        this.editPaste.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.playPlayMaze.setEnabled(false);
        this.playEditMaze.setEnabled(false);
        this.gameInventory.setEnabled(true);
        this.gameUse.setEnabled(true);
    }

    public void setEditorMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileClose.setEnabled(true);
        this.fileSave.setEnabled(true);
        this.fileSaveAs.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(true);
        this.editRedo.setEnabled(true);
        this.editCut.setEnabled(false);
        this.editCopy.setEnabled(false);
        this.editPaste.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(true);
        this.playPlayMaze.setEnabled(false);
        this.playEditMaze.setEnabled(false);
        this.gameInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
    }

    private void setPrefMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCut.setEnabled(false);
        this.editCopy.setEnabled(false);
        this.editPaste.setEnabled(false);
        this.editPreferences.setEnabled(false);
        this.editClearHistory.setEnabled(false);
        this.playPlayMaze.setEnabled(false);
        this.playEditMaze.setEnabled(false);
        this.gameInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
    }

    private void setMainMenus() {
        this.fileNew.setEnabled(true);
        this.fileOpen.setEnabled(true);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(true);
        this.fileSaveAs.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCut.setEnabled(false);
        this.editCopy.setEnabled(false);
        this.editPaste.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.playPlayMaze.setEnabled(true);
        this.playEditMaze.setEnabled(true);
        this.gameInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
    }

    public void decay() {
        this.savedMazeObject = new MazeGround();
    }

    public void morph(final MazeObject morphInto, final int x, final int y,
            final int z, final int w) {
        try {
            this.gameMaze.setCell(morphInto, x, y, z, w);
            this.redrawMaze();
            this.isDirty = true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void morph(final MazeObject morphInto, final int x, final int y,
            final int z, final int w, final String msg) {
        try {
            this.gameMaze.setCell(morphInto, x, y, z, w);
            this.redrawMazeKeepMessage(msg);
            this.isDirty = true;
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        }
    }

    public void showInventoryDialog() {
        final int keyCount = this.inv.getItemCount(new MazeKey());
        final int ikeyCount = this.inv.getItemCount(new MazeTablet());
        final int bombCount = this.inv.getItemCount(new MazeBomb());
        final int AKeyCount = this.inv.getItemCount(new MazeAKey());
        final int CKeyCount = this.inv.getItemCount(new MazeCKey());
        final int DKeyCount = this.inv.getItemCount(new MazeDKey());
        final int FKeyCount = this.inv.getItemCount(new MazeFKey());
        final int GKeyCount = this.inv.getItemCount(new MazeGKey());
        final int KKeyCount = this.inv.getItemCount(new MazeKKey());
        final int LKeyCount = this.inv.getItemCount(new MazeLKey());
        final int MKeyCount = this.inv.getItemCount(new MazeMKey());
        final int NKeyCount = this.inv.getItemCount(new MazeNKey());
        final int PKeyCount = this.inv.getItemCount(new MazePKey());
        final int RKeyCount = this.inv.getItemCount(new MazeRKey());
        final int UKeyCount = this.inv.getItemCount(new MazeUKey());
        final int ZKeyCount = this.inv.getItemCount(new MazeZKey());
        final int bootsCount = this.inv.getItemCount(new MazeBoots());
        final int sphereCount = this.inv.getItemCount(new MazeEnergySphere());
        final int AWCount = this.inv.getItemCount(new MazeAnnihilationWand());
        final int FMWCount = this.inv.getItemCount(new MazeFinishMakingWand());
        final int WMWCount = this.inv.getItemCount(new MazeWallMakingWand());
        final int TWCount = this.inv.getItemCount(new MazeTeleportWand());
        String hasikey, hasLetters, hasBoots, hasSphere;
        hasLetters = "";
        if (ikeyCount > 0) {
            hasikey = "Yes";
        } else {
            hasikey = "No";
        }
        if (AKeyCount > 0) {
            hasLetters += "A";
        }
        if (CKeyCount > 0) {
            hasLetters += "C";
        }
        if (DKeyCount > 0) {
            hasLetters += "D";
        }
        if (FKeyCount > 0) {
            hasLetters += "F";
        }
        if (GKeyCount > 0) {
            hasLetters += "G";
        }
        if (KKeyCount > 0) {
            hasLetters += "K";
        }
        if (LKeyCount > 0) {
            hasLetters += "L";
        }
        if (MKeyCount > 0) {
            hasLetters += "M";
        }
        if (NKeyCount > 0) {
            hasLetters += "N";
        }
        if (PKeyCount > 0) {
            hasLetters += "P";
        }
        if (RKeyCount > 0) {
            hasLetters += "R";
        }
        if (UKeyCount > 0) {
            hasLetters += "U";
        }
        if (ZKeyCount > 0) {
            hasLetters += "Z";
        }
        if (bootsCount > 0) {
            hasBoots = "Yes";
        } else {
            hasBoots = "No";
        }
        if (sphereCount > 0) {
            hasSphere = "Yes";
        } else {
            hasSphere = "No";
        }
        if (hasLetters.equals("")) {
            hasLetters = "None";
        }
        Messager.showDialog("Keys: " + keyCount + "\nTablet: " + hasikey
                + "\nBombs: " + bombCount + "\nLetters: " + hasLetters
                + "\nWater-Walking Boots: " + hasBoots + "\nEnergy Sphere: "
                + hasSphere + "\nAnnihilation Wands: " + AWCount
                + "\nFinish-Making Wands: " + FMWCount + "\nWall-Making Wands: "
                + WMWCount + "\nTeleport Wands: " + TWCount);
    }

    public void showUseDialog() {
        int x;
        final int AWCount = this.inv.getItemCount(new MazeAnnihilationWand());
        final int FMWCount = this.inv.getItemCount(new MazeFinishMakingWand());
        final int WMWCount = this.inv.getItemCount(new MazeWallMakingWand());
        final int TWCount = this.inv.getItemCount(new MazeTeleportWand());
        final MazeObject[] choices = { new MazeAnnihilationWand(),
                new MazeFinishMakingWand(), new MazeWallMakingWand(),
                new MazeTeleportWand() };
        final Object[] userChoices = {
                new MazeAnnihilationWand().getName() + " ("
                        + Integer.valueOf(AWCount) + ")",
                new MazeFinishMakingWand().getName() + " ("
                        + Integer.valueOf(FMWCount) + ")",
                new MazeWallMakingWand().getName() + " ("
                        + Integer.valueOf(WMWCount) + ")",
                new MazeTeleportWand().getName() + " ("
                        + Integer.valueOf(TWCount) + ")" };
        final String result = Messager.showInputDialog("Use which item?",
                "Maze Runner", userChoices,
                new MazeAnnihilationWand().getName());
        try {
            final int beginIndex = 0;
            final int endIndex = result.indexOf('(') - 1;
            final String objectName = result.substring(beginIndex, endIndex);
            for (x = 0; x < choices.length; x++) {
                if (objectName.equals(choices[x].getName())) {
                    this.objectBeingUsed = choices[x];
                    if (this.inv.getUses(this.objectBeingUsed) == 0) {
                        Messager.showMessage(
                                "That item has no more uses left.");
                        this.setUsingAnItem(false);
                    } else {
                        Messager.showMessage("Click to set target");
                    }
                    return;
                }
            }
        } catch (final NullPointerException np) {
            this.setUsingAnItem(false);
        }
    }

    public int getPlayerLocation(final boolean outer, final boolean inner) {
        if (outer) {
            if (inner) {
                return this.playerLocationW;
            } else {
                return this.playerLocationZ;
            }
        } else {
            if (inner) {
                return this.playerLocationY;
            } else {
                return this.playerLocationX;
            }
        }
    }

    public void setPlayerLocation(final boolean outer, final boolean inner,
            final int newLoc) {
        if (outer) {
            if (inner) {
                this.playerLocationW = newLoc;
            } else {
                this.playerLocationZ = newLoc;
            }
        } else {
            if (inner) {
                this.playerLocationY = newLoc;
            } else {
                this.playerLocationX = newLoc;
            }
        }
    }

    public int getViewingWindowLocation(final boolean flag) {
        if (flag) {
            return this.upperLeftViewingWindowY;
        } else {
            return this.upperLeftViewingWindowX;
        }
    }

    public int getLowerViewingWindowLocation(final boolean flag) {
        if (flag) {
            return this.lowerRightViewingWindowY;
        } else {
            return this.lowerRightViewingWindowX;
        }
    }

    public void setViewingWindowLocation(final boolean flag,
            final int newCoord) {
        if (flag) {
            this.upperLeftViewingWindowY = newCoord;
        } else {
            this.upperLeftViewingWindowX = newCoord;
        }
    }

    public void setLowerViewingWindowLocation(final boolean flag,
            final int newCoord) {
        if (flag) {
            this.lowerRightViewingWindowY = newCoord;
        } else {
            this.lowerRightViewingWindowX = newCoord;
        }
    }

    public int getViewingWindowSize(final boolean flag) {
        if (flag) {
            return MazeRunner.VIEWING_WINDOW_SIZE_Y;
        } else {
            return MazeRunner.VIEWING_WINDOW_SIZE_X;
        }
    }

    public void useItemHandler(final int x, final int y) {
        if (this.usingAnItem() && this.IN_GAME) {
            final int xOffset = this.upperLeftViewingWindowX
                    - MazeRunner.MIN_VIEWING_WINDOW_X;
            final int yOffset = this.upperLeftViewingWindowY
                    - MazeRunner.MIN_VIEWING_WINDOW_Y;
            final int destX = x / MazeObject.getSize()
                    + this.upperLeftViewingWindowX - xOffset + yOffset;
            final int destY = y / MazeObject.getSize()
                    + this.upperLeftViewingWindowY + xOffset - yOffset;
            final int destZ = this.playerLocationZ;
            final int destW = this.playerLocationW;
            try {
                final MazeObject target = this.gameMaze.getCell(destX, destY,
                        destZ, destW);
                final String name = this.objectBeingUsed.getName();
                if (target.isSolid()
                        && name.equals(new MazeTeleportWand().getName())) {
                    this.setUsingAnItem(false);
                    Messager.showMessage("Can't teleport there");
                }
                if (target.getName().equals(new MazePlayer().getName())) {
                    this.setUsingAnItem(false);
                    Messager.showMessage("Don't aim at yourself!");
                }
                if (!target.isDestroyable()
                        && name.equals(new MazeAnnihilationWand().getName())) {
                    this.setUsingAnItem(false);
                    Messager.showMessage("Can't destroy that");
                }
                if (!target.isDestroyable()
                        && name.equals(new MazeWallMakingWand().getName())) {
                    this.setUsingAnItem(false);
                    Messager.showMessage("Can't create a wall there");
                }
                if (!target.isDestroyable()
                        && name.equals(new MazeFinishMakingWand().getName())) {
                    this.setUsingAnItem(false);
                    Messager.showMessage("Can't create a finish there");
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.setUsingAnItem(false);
                Messager.showMessage("Aim within the maze");
            } catch (final NullPointerException np) {
                this.setUsingAnItem(false);
            }
            if (this.usingAnItem()) {
                this.inv.use(this.objectBeingUsed, destX, destY, destZ, destW);
                this.redrawMaze();
            }
        }
    }

    public boolean loadMaze() {
        int status = 0;
        boolean success = false;
        boolean saved = true;
        String filename, extension;
        final JFileChooser fc = new JFileChooser();
        final MazeFilter mf = new MazeFilter();
        final SavedGameFilter sg = new SavedGameFilter();
        if (this.getDirty()) {
            status = this.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = this.saveMaze();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                this.isDirty = false;
            }
        }
        if (saved) {
            fc.setAcceptAllFileFilterUsed(false);
            if (this.IN_GAME) {
                fc.addChoosableFileFilter(mf);
                fc.addChoosableFileFilter(sg);
                fc.setFileFilter(mf);
            } else {
                fc.addChoosableFileFilter(mf);
                fc.setFileFilter(mf);
            }
            final int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                filename = file.getAbsolutePath();
                extension = MazeRunner.getExtension(file);
                this.inv = new Inventory();
                if (extension.equals("mrv")) {
                    success = this.loadGame(filename);
                    if (success) {
                        this.isDirty = false;
                    }
                } else {
                    success = this.loadTextMaze(filename);
                    if (success) {
                        this.isDirty = false;
                    }
                }
            }
        }
        return success;
    }

    private boolean loadTextMaze(final String filename) {
        int x = 0, y = 0, z = 0, w = 0;
        boolean success = true;
        try {
            try (BufferedReader mazeFile = new BufferedReader(
                    new FileReader(filename))) {
                try {
                    String inputLine = mazeFile.readLine();
                    this.mazeSizeX = Integer.parseInt(inputLine);
                    inputLine = mazeFile.readLine();
                    this.mazeSizeY = Integer.parseInt(inputLine);
                    inputLine = mazeFile.readLine();
                    this.mazeSizeZ = Integer.parseInt(inputLine);
                    inputLine = mazeFile.readLine();
                    this.mazeSizeW = Integer.parseInt(inputLine);
                    this.playerLocationX = -1;
                    this.playerLocationY = -1;
                    this.playerLocationZ = -1;
                    this.playerLocationW = -1;
                    this.gameMaze = new Maze(this.mazeSizeX, this.mazeSizeY,
                            this.mazeSizeZ, this.mazeSizeW);
                    for (w = 0; w < this.mazeSizeW; w++) {
                        for (z = 0; z < this.mazeSizeZ; z++) {
                            for (y = 0; y < this.mazeSizeY; y++) {
                                for (x = 0; x < this.mazeSizeX; x++) {
                                    this.textMazeParser(x, y, z, w, mazeFile);
                                }
                            }
                        }
                    }
                    if (this.playerLocationX == -1 || this.playerLocationY == -1
                            || this.playerLocationZ == -1
                            || this.playerLocationW == -1) {
                        throw new InvalidMazeException("No player found.");
                    } else {
                        final boolean playerExists = this.gameMaze
                                .findPlayerOnLevel(0);
                        if (playerExists) {
                            this.playerLocationX = this.gameMaze
                                    .getStartColumn(0);
                            this.playerLocationY = this.gameMaze.getStartRow(0);
                            this.playerLocationZ = this.gameMaze
                                    .getStartFloor(0);
                            this.playerLocationW = this.gameMaze
                                    .getStartLevel();
                            for (x = 0; x < this.mazeSizeW; x++) {
                                this.gameMaze.findPlayerOnLevel(x);
                                this.gameMaze.saveLevel(x);
                            }
                        } else {
                            throw new InvalidMazeException("No player found.");
                        }
                        this.upperLeftViewingWindowX = this.playerLocationX
                                - (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
                        this.upperLeftViewingWindowY = this.playerLocationY
                                - (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
                        this.lowerRightViewingWindowX = this.playerLocationX
                                + (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
                        this.lowerRightViewingWindowY = this.playerLocationY
                                + (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
                    }
                    try {
                        mazeFile.close();
                    } catch (final IOException ie) {
                        throw new InvalidMazeException(
                                "Maze file could not be closed.");
                    }
                    if (w != this.mazeSizeW) {
                        throw new InvalidMazeException("Maze file too small.");
                    }
                    MazeMaker.setMaxViewingWindow(
                            this.mazeSizeX
                                    + MazeRunner.VIEWING_WINDOW_SIZE_X / 2,
                            this.mazeSizeY
                                    + MazeRunner.VIEWING_WINDOW_SIZE_Y / 2);
                } catch (final IOException ie) {
                    throw new InvalidMazeException("Error reading maze file.");
                }
            } catch (final IOException ie) {
                throw new InvalidMazeException("Maze file couldn't be opened.");
            }
        } catch (final InvalidMazeException ime) {
            if (this.getMessageEnabled(
                    MazeRunner.MESSAGE_SPECIFIC_MAZE_ERRORS)) {
                Messager.showDialog(ime.getMessage());
            } else {
                Messager.showDialog("Invalid Maze File");
            }
            success = false;
        } catch (final Exception ex) {
            Messager.showDialog("Unknown error reading maze file.");
            success = false;
        }
        return success;
    }

    private boolean loadGame(final String filename) {
        int x = 0, y = 0, z = 0, w = 0, u = 0, startW;
        boolean success = true;
        try {
            try (BufferedReader mazeFile = new BufferedReader(
                    new FileReader(filename))) {
                try {
                    String inputLine = mazeFile.readLine();
                    startW = Integer.parseInt(inputLine);
                    inputLine = mazeFile.readLine();
                    this.mazeSizeX = Integer.parseInt(inputLine);
                    inputLine = mazeFile.readLine();
                    this.mazeSizeY = Integer.parseInt(inputLine);
                    inputLine = mazeFile.readLine();
                    this.mazeSizeZ = Integer.parseInt(inputLine);
                    inputLine = mazeFile.readLine();
                    this.mazeSizeW = Integer.parseInt(inputLine);
                    this.playerLocationX = -1;
                    this.playerLocationY = -1;
                    this.playerLocationZ = -1;
                    this.playerLocationW = -1;
                    this.gameMaze = new Maze(this.mazeSizeX, this.mazeSizeY,
                            this.mazeSizeZ, this.mazeSizeW);
                    for (w = 0; w < this.mazeSizeW; w++) {
                        for (z = 0; z < this.mazeSizeZ; z++) {
                            for (y = 0; y < this.mazeSizeY; y++) {
                                for (x = 0; x < this.mazeSizeX; x++) {
                                    this.textMazeParser(x, y, z, w, mazeFile);
                                }
                            }
                        }
                    }
                    if (this.playerLocationX == -1 || this.playerLocationY == -1
                            || this.playerLocationZ == -1
                            || this.playerLocationW == -1) {
                        throw new InvalidMazeException("No player found.");
                    } else {
                        final boolean playerExists = this.gameMaze
                                .findPlayerOnLevel(startW);
                        if (playerExists) {
                            this.playerLocationX = this.gameMaze
                                    .getStartColumn(startW);
                            this.playerLocationY = this.gameMaze
                                    .getStartRow(startW);
                            this.playerLocationZ = this.gameMaze
                                    .getStartFloor(startW);
                            this.playerLocationW = startW;
                            for (x = 0; x < this.mazeSizeW; x++) {
                                this.gameMaze.findPlayerOnLevel(x);
                                this.gameMaze.saveLevel(x);
                            }
                        } else {
                            throw new InvalidMazeException("No player found.");
                        }
                        this.upperLeftViewingWindowX = this.playerLocationX
                                - (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
                        this.upperLeftViewingWindowY = this.playerLocationY
                                - (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
                        this.lowerRightViewingWindowX = this.playerLocationX
                                + (MazeRunner.VIEWING_WINDOW_SIZE_X - 1) / 2;
                        this.lowerRightViewingWindowY = this.playerLocationY
                                + (MazeRunner.VIEWING_WINDOW_SIZE_Y - 1) / 2;
                        final String temp = mazeFile.readLine();
                        if (!temp.equals("INVENTORY")) {
                            throw new InvalidMazeException(
                                    "No inventory state found.");
                        } else {
                            for (x = 0; x < this.MAX_INVENTORY; x++) {
                                y = Integer.parseInt(mazeFile.readLine());
                                for (z = 0; z < this.inv
                                        .getMaximumQuantity(); z++) {
                                    u = Integer.parseInt(mazeFile.readLine());
                                    this.inv.reconstruct(x, y, z, u);
                                }
                            }
                        }
                    }
                    try {
                        mazeFile.close();
                    } catch (final IOException ie) {
                        throw new InvalidMazeException(
                                "Saved game file could not be closed.");
                    }
                    if (w != this.mazeSizeW) {
                        throw new InvalidMazeException(
                                "Saved game file too small.");
                    }
                    MazeMaker.setMaxViewingWindow(
                            this.mazeSizeX
                                    + MazeRunner.VIEWING_WINDOW_SIZE_X / 2,
                            this.mazeSizeY
                                    + MazeRunner.VIEWING_WINDOW_SIZE_Y / 2);
                } catch (final IOException ie) {
                    throw new InvalidMazeException(
                            "Error reading saved game file.");
                }
            } catch (final IOException ie) {
                throw new InvalidMazeException(
                        "Saved game file couldn't be opened.");
            }
        } catch (final InvalidMazeException ime) {
            if (this.getMessageEnabled(
                    MazeRunner.MESSAGE_SPECIFIC_MAZE_ERRORS)) {
                Messager.showDialog(ime.getMessage());
            } else {
                Messager.showDialog("Invalid Saved Game File");
            }
            success = false;
        } catch (final Exception ex) {
            Messager.showDialog("Unknown error reading saved game file.");
            success = false;
        }
        return success;
    }

    private void textMazeParser(final int x, final int y, final int z,
            final int w, final BufferedReader mazeFile)
            throws InvalidMazeException, IOException {
        int destX, destY, destZ, destW;
        final String inputLine = mazeFile.readLine();
        if (inputLine != null) {
            if (inputLine.equals("G")) {
                this.gameMaze.setCell(new MazeGround(), x, y, z, w);
            } else if (inputLine.equals("W")) {
                this.gameMaze.setCell(new MazeWall(), x, y, z, w);
            } else if (inputLine.equals("P")) {
                this.gameMaze.setCell(new MazePlayer(), x, y, z, w);
                this.playerLocationX = x;
                this.playerLocationY = y;
                this.playerLocationZ = z;
                this.playerLocationW = w;
            } else if (inputLine.equals("IW")) {
                this.gameMaze.setCell(new MazeInvisibleWall(), x, y, z, w);
            } else if (inputLine.equals("FW")) {
                this.gameMaze.setCell(new MazeFakeWall(), x, y, z, w);
            } else if (inputLine.equals("FF")) {
                this.gameMaze.setCell(new MazeFakeFinish(), x, y, z, w);
            } else if (inputLine.equals("F")) {
                this.gameMaze.setCell(new MazeFinish(), x, y, z, w);
            } else if (inputLine.equals("L")) {
                this.gameMaze.setCell(new MazeLock(), x, y, z, w);
            } else if (inputLine.equals("K")) {
                this.gameMaze.setCell(new MazeKey(), x, y, z, w);
            } else if (inputLine.equals("L1")) {
                this.gameMaze.setCell(new MazeCrackedWall(), x, y, z, w);
            } else if (inputLine.equals("K1")) {
                this.gameMaze.setCell(new MazeBomb(), x, y, z, w);
            } else if (inputLine.equals("IL")) {
                this.gameMaze.setCell(new MazeTabletSlot(), x, y, z, w);
            } else if (inputLine.equals("IK")) {
                this.gameMaze.setCell(new MazeTablet(), x, y, z, w);
            } else if (inputLine.equals("AL")) {
                this.gameMaze.setCell(new MazeALock(), x, y, z, w);
            } else if (inputLine.equals("AK")) {
                this.gameMaze.setCell(new MazeAKey(), x, y, z, w);
            } else if (inputLine.equals("CL")) {
                this.gameMaze.setCell(new MazeCLock(), x, y, z, w);
            } else if (inputLine.equals("CK")) {
                this.gameMaze.setCell(new MazeCKey(), x, y, z, w);
            } else if (inputLine.equals("DL")) {
                this.gameMaze.setCell(new MazeDLock(), x, y, z, w);
            } else if (inputLine.equals("DK")) {
                this.gameMaze.setCell(new MazeDKey(), x, y, z, w);
            } else if (inputLine.equals("FL")) {
                this.gameMaze.setCell(new MazeFLock(), x, y, z, w);
            } else if (inputLine.equals("FK")) {
                this.gameMaze.setCell(new MazeFKey(), x, y, z, w);
            } else if (inputLine.equals("GL")) {
                this.gameMaze.setCell(new MazeGLock(), x, y, z, w);
            } else if (inputLine.equals("GK")) {
                this.gameMaze.setCell(new MazeGKey(), x, y, z, w);
            } else if (inputLine.equals("KL")) {
                this.gameMaze.setCell(new MazeKLock(), x, y, z, w);
            } else if (inputLine.equals("KK")) {
                this.gameMaze.setCell(new MazeKKey(), x, y, z, w);
            } else if (inputLine.equals("LL")) {
                this.gameMaze.setCell(new MazeLLock(), x, y, z, w);
            } else if (inputLine.equals("LK")) {
                this.gameMaze.setCell(new MazeLKey(), x, y, z, w);
            } else if (inputLine.equals("ML")) {
                this.gameMaze.setCell(new MazeMLock(), x, y, z, w);
            } else if (inputLine.equals("MK")) {
                this.gameMaze.setCell(new MazeMKey(), x, y, z, w);
            } else if (inputLine.equals("NL")) {
                this.gameMaze.setCell(new MazeNLock(), x, y, z, w);
            } else if (inputLine.equals("NK")) {
                this.gameMaze.setCell(new MazeNKey(), x, y, z, w);
            } else if (inputLine.equals("PL")) {
                this.gameMaze.setCell(new MazePLock(), x, y, z, w);
            } else if (inputLine.equals("PK")) {
                this.gameMaze.setCell(new MazePKey(), x, y, z, w);
            } else if (inputLine.equals("RL")) {
                this.gameMaze.setCell(new MazeRLock(), x, y, z, w);
            } else if (inputLine.equals("RK")) {
                this.gameMaze.setCell(new MazeRKey(), x, y, z, w);
            } else if (inputLine.equals("UL")) {
                this.gameMaze.setCell(new MazeULock(), x, y, z, w);
            } else if (inputLine.equals("UK")) {
                this.gameMaze.setCell(new MazeUKey(), x, y, z, w);
            } else if (inputLine.equals("ZL")) {
                this.gameMaze.setCell(new MazeZLock(), x, y, z, w);
            } else if (inputLine.equals("ZK")) {
                this.gameMaze.setCell(new MazeZKey(), x, y, z, w);
            } else if (inputLine.equals("T")) {
                destY = Integer.parseInt(mazeFile.readLine());
                destX = Integer.parseInt(mazeFile.readLine());
                destZ = Integer.parseInt(mazeFile.readLine());
                destW = Integer.parseInt(mazeFile.readLine());
                this.gameMaze.setCell(
                        new MazeTeleporter(destX, destY, destZ, destW), x, y, z,
                        w);
            } else if (inputLine.equals("IT")) {
                destY = Integer.parseInt(mazeFile.readLine());
                destX = Integer.parseInt(mazeFile.readLine());
                destZ = Integer.parseInt(mazeFile.readLine());
                destW = Integer.parseInt(mazeFile.readLine());
                this.gameMaze.setCell(
                        new MazeInvisibleTeleporter(destX, destY, destZ, destW),
                        x, y, z, w);
            } else if (inputLine.equals("RT")) {
                destY = Integer.parseInt(mazeFile.readLine());
                destX = Integer.parseInt(mazeFile.readLine());
                this.gameMaze.setCell(new MazeRandomTeleporter(destX, destY), x,
                        y, z, w);
            } else if (inputLine.equals("OT")) {
                destY = Integer.parseInt(mazeFile.readLine());
                destX = Integer.parseInt(mazeFile.readLine());
                destZ = Integer.parseInt(mazeFile.readLine());
                destW = Integer.parseInt(mazeFile.readLine());
                this.gameMaze.setCell(
                        new MazeOneShotTeleporter(destX, destY, destZ, destW),
                        x, y, z, w);
            } else if (inputLine.equals("ROT")) {
                destY = Integer.parseInt(mazeFile.readLine());
                destX = Integer.parseInt(mazeFile.readLine());
                this.gameMaze.setCell(
                        new MazeRandomOneShotTeleporter(destX, destY), x, y, z,
                        w);
            } else if (inputLine.equals("RIT")) {
                destY = Integer.parseInt(mazeFile.readLine());
                destX = Integer.parseInt(mazeFile.readLine());
                this.gameMaze.setCell(
                        new MazeRandomInvisibleTeleporter(destX, destY), x, y,
                        z, w);
            } else if (inputLine.equals("IOT")) {
                destY = Integer.parseInt(mazeFile.readLine());
                destX = Integer.parseInt(mazeFile.readLine());
                destZ = Integer.parseInt(mazeFile.readLine());
                destW = Integer.parseInt(mazeFile.readLine());
                this.gameMaze.setCell(new MazeInvisibleOneShotTeleporter(destX,
                        destY, destZ, destW), x, y, z, w);
            } else if (inputLine.equals("RIOT")) {
                destY = Integer.parseInt(mazeFile.readLine());
                destX = Integer.parseInt(mazeFile.readLine());
                this.gameMaze.setCell(
                        new MazeRandomInvisibleOneShotTeleporter(destX, destY),
                        x, y, z, w);
            } else if (inputLine.equals("OWEW")) {
                this.gameMaze.setCell(new MazeOneWayEastWall(), x, y, z, w);
            } else if (inputLine.equals("OWNW")) {
                this.gameMaze.setCell(new MazeOneWayNorthWall(), x, y, z, w);
            } else if (inputLine.equals("OWSW")) {
                this.gameMaze.setCell(new MazeOneWaySouthWall(), x, y, z, w);
            } else if (inputLine.equals("OWWW")) {
                this.gameMaze.setCell(new MazeOneWayWestWall(), x, y, z, w);
            } else if (inputLine.equals("DOWN")) {
                this.gameMaze.setCell(new MazeStairsDown(), x, y, z, w);
            } else if (inputLine.equals("UP")) {
                this.gameMaze.setCell(new MazeStairsUp(), x, y, z, w);
            } else if (inputLine.equals("T2")) {
                destY = Integer.parseInt(mazeFile.readLine());
                destX = Integer.parseInt(mazeFile.readLine());
                destZ = Integer.parseInt(mazeFile.readLine());
                destW = Integer.parseInt(mazeFile.readLine());
                this.gameMaze.setCell(
                        new MazeTwoWayTeleporter(destX, destY, destZ, destW), x,
                        y, z, w);
            } else if (inputLine.equals("PIT")) {
                this.gameMaze.setCell(new MazePit(), x, y, z, w);
            } else if (inputLine.equals("TILE")) {
                this.gameMaze.setCell(new MazeTile(), x, y, z, w);
            } else if (inputLine.equals("PB")) {
                final MazePushableBlock pb = new MazePushableBlock();
                final String innerObjStr = mazeFile.readLine();
                if (innerObjStr.equals("TILE")) {
                    pb.setSavedObject(new MazeTile());
                } else if (innerObjStr.equals("SB")) {
                    pb.setSavedObject(new MazeSunkenBlock());
                }
                this.gameMaze.setCell(pb, x, y, z, w);
            } else if (inputLine.equals("UB")) {
                final MazePullableBlock pb = new MazePullableBlock();
                final String innerObjStr = mazeFile.readLine();
                if (innerObjStr.equals("TILE")) {
                    pb.setSavedObject(new MazeTile());
                } else if (innerObjStr.equals("SB")) {
                    pb.setSavedObject(new MazeSunkenBlock());
                }
                this.gameMaze.setCell(pb, x, y, z, w);
            } else if (inputLine.equals("PUB")) {
                final MazePushablePullableBlock pb = new MazePushablePullableBlock();
                final String innerObjStr = mazeFile.readLine();
                if (innerObjStr.equals("TILE")) {
                    pb.setSavedObject(new MazeTile());
                } else if (innerObjStr.equals("SB")) {
                    pb.setSavedObject(new MazeSunkenBlock());
                }
                this.gameMaze.setCell(pb, x, y, z, w);
            } else if (inputLine.equals("ICE")) {
                this.gameMaze.setCell(new MazeIce(), x, y, z, w);
            } else if (inputLine.equals("FT")) {
                destW = Integer.parseInt(mazeFile.readLine());
                this.gameMaze.setCell(new MazeFinishTo(destW), x, y, z, w);
            } else if (inputLine.equals("BOOTS")) {
                this.gameMaze.setCell(new MazeBoots(), x, y, z, w);
            } else if (inputLine.equals("WATER")) {
                this.gameMaze.setCell(new MazeWater(), x, y, z, w);
            } else if (inputLine.equals("SB")) {
                this.gameMaze.setCell(new MazeSunkenBlock(), x, y, z, w);
            } else if (inputLine.equals("ES")) {
                this.gameMaze.setCell(new MazeEnergySphere(), x, y, z, w);
            } else if (inputLine.equals("FORCE")) {
                this.gameMaze.setCell(new MazeForceField(), x, y, z, w);
            } else if (inputLine.equals("AW")) {
                this.gameMaze.setCell(new MazeAnnihilationWand(), x, y, z, w);
            } else if (inputLine.equals("FMW")) {
                this.gameMaze.setCell(new MazeFinishMakingWand(), x, y, z, w);
            } else if (inputLine.equals("WMW")) {
                this.gameMaze.setCell(new MazeWallMakingWand(), x, y, z, w);
            } else if (inputLine.equals("TW")) {
                this.gameMaze.setCell(new MazeTeleportWand(), x, y, z, w);
            } else if (inputLine.equals("V")) {
                this.gameMaze.setCell(new MazeVoid(), x, y, z, w);
            } else if (inputLine.equals("EW")) {
                this.gameMaze.setCell(new MazeExplodingWall(), x, y, z, w);
            } else {
                throw new InvalidMazeException("Unknown object encountered.");
            }
        } else {
            throw new InvalidMazeException("Maze file too small.");
        }
    }

    public boolean saveMaze() {
        boolean success = false;
        String filename, extension;
        final JFileChooser fc = new JFileChooser();
        final MazeFilter mf = new MazeFilter();
        final SavedGameFilter sg = new SavedGameFilter();
        fc.setAcceptAllFileFilterUsed(false);
        if (this.IN_GAME) {
            fc.addChoosableFileFilter(sg);
            fc.setFileFilter(sg);
        } else {
            fc.addChoosableFileFilter(mf);
            fc.setFileFilter(mf);
        }
        final int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File file = fc.getSelectedFile();
            extension = MazeRunner.getExtension(file);
            filename = file.getAbsolutePath();
            if (this.IN_GAME) {
                if (extension != null) {
                    if (!extension.equals("mrv")) {
                        filename = MazeRunner.getNameWithoutExtension(file)
                                + ".mrv";
                    }
                } else {
                    filename += ".mrv";
                }
                success = this.saveGame(filename);
                if (success) {
                    this.isDirty = false;
                }
            } else {
                if (extension != null) {
                    if (!extension.equals("mrm")) {
                        filename = MazeRunner.getNameWithoutExtension(file)
                                + ".mrm";
                    }
                } else {
                    filename += ".mrm";
                }
                success = MazeMaker.saveTextMaze(filename);
                if (success) {
                    this.isDirty = false;
                }
            }
        }
        return success;
    }

    private boolean saveGame(final String filename) {
        int x = 0, y = 0, z = 0, w = 0;
        boolean success = true;
        try {
            try (PrintWriter mazeFile = new PrintWriter(
                    new BufferedWriter(new FileWriter(filename)))) {
                mazeFile.print(this.playerLocationW);
                mazeFile.print("\n");
                mazeFile.print(this.mazeSizeX);
                mazeFile.print("\n");
                mazeFile.print(this.mazeSizeY);
                mazeFile.print("\n");
                mazeFile.print(this.mazeSizeZ);
                mazeFile.print("\n");
                mazeFile.print(this.mazeSizeW);
                mazeFile.print("\n");
                for (w = 0; w < this.mazeSizeW; w++) {
                    for (z = 0; z < this.mazeSizeZ; z++) {
                        for (y = 0; y < this.mazeSizeY; y++) {
                            for (x = 0; x < this.mazeSizeX; x++) {
                                mazeFile.print(this.gameMaze.getCell(x, y, z, w)
                                        .toString());
                                mazeFile.print("\n");
                            }
                        }
                    }
                }
                mazeFile.print(this.inv.toString());
                mazeFile.close();
                if (mazeFile.checkError()) {
                    success = false;
                }
            } catch (final IOException ie) {
                throw new InvalidMazeException(
                        "Saved game file couldn't be written to.");
            }
        } catch (final InvalidMazeException ime) {
            if (this.getMessageEnabled(
                    MazeRunner.MESSAGE_SPECIFIC_MAZE_ERRORS)) {
                Messager.showDialog(ime.getMessage());
            } else {
                Messager.showDialog("Saved game couldn't be created.");
            }
            success = false;
        } catch (final Exception ex) {
            Messager.showDialog("Unknown error writing saved game file.");
            success = false;
        }
        return success;
    }

    private static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getNameWithoutExtension(final File f) {
        String ext = null;
        final String s = f.getAbsolutePath();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i - 1);
        }
        return ext;
    }

    public void playMaze() {
        if (this.loaded) {
            try {
                this.hideMenu();
                this.IN_GAME = true;
                this.outputFrame = new JFrame("Maze Runner");
                this.outputPane = new Container();
                this.outputFrame.setContentPane(this.borderPane);
                this.outputFrame.setDefaultCloseOperation(
                        WindowConstants.DO_NOTHING_ON_CLOSE);
                this.borderPane.add(this.outputPane, BorderLayout.CENTER);
                this.borderPane.add(this.messageLabel, BorderLayout.SOUTH);
                this.outputPane.setLayout(
                        new GridLayout(MazeRunner.VIEWING_WINDOW_SIZE_X,
                                MazeRunner.VIEWING_WINDOW_SIZE_Y));
                this.outputFrame.setResizable(false);
                this.outputFrame.addKeyListener(this.handler);
                this.outputFrame.addWindowListener(this.handler);
                this.outputPane.addMouseListener(this.handler);
                this.savedMazeObject = new MazeGround();
                this.showOutput();
                this.redrawMaze();
            } catch (final IllegalArgumentException ia) {
                Messager.showDialog("The maze size specified is too small.");
                this.setLoaded(false);
                this.showMenu();
            }
        } else {
            Messager.showDialog("No Maze Opened");
        }
    }

    public void showPrefs() {
        this.prefFrame.setJMenuBar(this.mainMenuBar);
        this.setPrefMenus();
        this.prefFrame.setVisible(true);
    }

    public void hidePrefs() {
        this.prefFrame.setVisible(false);
        if (this.IN_MENU) {
            this.showMenu();
        } else {
            if (this.IN_GAME) {
                this.showOutput();
            } else {
                MazeMaker.showOutput();
            }
        }
    }

    public void showMenu() {
        this.IN_MENU = true;
        this.menuFrame.setJMenuBar(this.mainMenuBar);
        this.setMainMenus();
        this.menuFrame.setVisible(true);
    }

    public void hideMenu() {
        this.IN_MENU = false;
        this.menuFrame.setVisible(false);
    }

    public void showOutput() {
        this.outputFrame.setJMenuBar(this.mainMenuBar);
        this.setGameMenus();
        this.outputFrame.setVisible(true);
    }

    public void hideOutput() {
        this.outputFrame.setVisible(false);
    }

    public void setPrefs() {
        String oldSet;
        int oldSize;
        boolean result;
        this.hidePrefs();
        oldSet = MazeObject.getSet();
        oldSize = MazeObject.getSize();
        MazeObject.setSet((String) this.sets.getSelectedItem());
        final String retVal = (String) this.sizes.getSelectedItem();
        if (retVal.equals("Small")) {
            MazeObject.setSize(16);
        } else if (retVal.equals("Medium")) {
            MazeObject.setSize(32);
        } else if (retVal.equals("Large")) {
            MazeObject.setSize(64);
        } else {
            MazeObject.setSize(ImageSetManager.getDefaultSize());
        }
        result = this.msgCannotGoOutsideMaze.isSelected();
        if (result) {
            this.setMessageEnabled(MazeRunner.MESSAGE_CANNOT_GO_OUTSIDE_MAZE,
                    true);
        } else {
            this.setMessageEnabled(MazeRunner.MESSAGE_CANNOT_GO_OUTSIDE_MAZE,
                    false);
        }
        result = this.msgFakeExit.isSelected();
        if (result) {
            this.setMessageEnabled(MazeRunner.MESSAGE_FAKE_EXIT, true);
        } else {
            this.setMessageEnabled(MazeRunner.MESSAGE_FAKE_EXIT, false);
        }
        result = this.msgInvisibleWall.isSelected();
        if (result) {
            this.setMessageEnabled(MazeRunner.MESSAGE_INVISIBLE_WALL, true);
        } else {
            this.setMessageEnabled(MazeRunner.MESSAGE_INVISIBLE_WALL, false);
        }
        result = this.msgCannotGoThatWay.isSelected();
        if (result) {
            this.setMessageEnabled(MazeRunner.MESSAGE_CANNOT_GO_THAT_WAY, true);
        } else {
            this.setMessageEnabled(MazeRunner.MESSAGE_CANNOT_GO_THAT_WAY,
                    false);
        }
        result = this.msgSpecificMazeErrors.isSelected();
        if (result) {
            this.setMessageEnabled(MazeRunner.MESSAGE_SPECIFIC_MAZE_ERRORS,
                    true);
        } else {
            this.setMessageEnabled(MazeRunner.MESSAGE_SPECIFIC_MAZE_ERRORS,
                    false);
        }
        result = this.msgInvisibleTeleporter.isSelected();
        if (result) {
            this.setMessageEnabled(MazeRunner.MESSAGE_INVISIBLE_TELEPORTER,
                    true);
        } else {
            this.setMessageEnabled(MazeRunner.MESSAGE_INVISIBLE_TELEPORTER,
                    false);
        }
        if (oldSet != MazeObject.getSet() || oldSize != MazeObject.getSize()) {
            this.updateGraphics();
            if (this.loaded) {
                if (this.IN_GAME) {
                    if (this.outputFrame.isVisible()) {
                        this.redrawMaze();
                    }
                } else {
                    if (MazeMaker.isOutputVisible()) {
                        MazeMaker.redrawEditor();
                    }
                }
            }
        }
    }

    public void setDefaultPrefs() {
        MazeObject.setSet(ImageSetManager.getDefaultSet());
        MazeObject.setSize(ImageSetManager.getDefaultSize());
        this.setMessageEnabled(MazeRunner.MESSAGE_CANNOT_GO_OUTSIDE_MAZE, true);
        this.setMessageEnabled(MazeRunner.MESSAGE_FAKE_EXIT, true);
        this.setMessageEnabled(MazeRunner.MESSAGE_INVISIBLE_WALL, true);
        this.setMessageEnabled(MazeRunner.MESSAGE_CANNOT_GO_THAT_WAY, true);
        this.setMessageEnabled(MazeRunner.MESSAGE_SPECIFIC_MAZE_ERRORS, false);
        this.setMessageEnabled(MazeRunner.MESSAGE_INVISIBLE_TELEPORTER, true);
    }

    private void updateGraphics() {
        int x, y, z, w;
        if (this.getLoaded()) {
            for (w = 0; w < this.mazeSizeW; w++) {
                for (z = 0; z < this.mazeSizeZ; z++) {
                    for (y = 0; y < this.mazeSizeY; y++) {
                        for (x = 0; x < this.mazeSizeX; x++) {
                            this.gameMaze.getCell(x, y, z, w).updateGraphics();
                        }
                    }
                }
            }
        }
        this.savedMazeObject.updateGraphics();
    }

    private void setUpGUI() {
        try {
            // Tell the UIManager to use the platform look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final Exception e) {
            // Do nothing
        }
        final String[] sizeChoices = { "Small", "Medium", "Large" };
        final String[] setChoices = { "Modern", "Classic" };
        this.IN_GAME = true;
        this.IN_MENU = true;
        this.loaded = false;
        this.inv = new Inventory();
        this.handler = new MazeRunnerEventHandler();
        this.menuFrame = new JFrame("Maze Runner");
        this.menuPane = this.menuFrame.getContentPane();
        this.menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.menuFrame.setLayout(new FlowLayout());
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        this.createAccelerators();
        this.createMenus();
        final ImageIcon logo = ImageSetManager.getLogo();
        this.menuPane.add(new JLabel("", logo, SwingConstants.CENTER));
        this.menuFrame.pack();
        this.prefFrame = new JFrame("Preferences");
        this.mainPrefPane = new Container();
        this.subPrefPane1 = new Container();
        this.subPrefPane2 = new Container();
        this.prefsOK = new JButton("OK");
        this.prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(this.prefsOK);
        this.prefsCancel = new JButton("Cancel");
        this.prefsOK.setDefaultCapable(false);
        this.sets = new JComboBox<>(setChoices);
        this.sizes = new JComboBox<>(sizeChoices);
        this.sets.setEditable(false);
        this.sizes.setEditable(false);
        this.sizes.setSelectedIndex(1);
        this.msgCannotGoOutsideMaze = new JCheckBox(
                "Enable \"Cannot go outside maze\" message", true);
        this.msgFakeExit = new JCheckBox("Enable \"Fake exit!\" message", true);
        this.msgInvisibleWall = new JCheckBox(
                "Enable \"Invisible wall!\" message", true);
        this.msgCannotGoThatWay = new JCheckBox(
                "Enable \"Cannot go that way\" message", true);
        this.msgSpecificMazeErrors = new JCheckBox(
                "Enable specific maze error messages", false);
        this.msgInvisibleTeleporter = new JCheckBox(
                "Enable \"Invisible Teleporter!\" message", true);
        this.prefFrame.setContentPane(this.mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        this.mainPrefPane.add(this.subPrefPane1, BorderLayout.CENTER);
        this.mainPrefPane.add(this.subPrefPane2, BorderLayout.SOUTH);
        this.subPrefPane1.setLayout(new GridLayout(10, 1));
        this.subPrefPane1.add(new JLabel("Graphics Set"));
        this.subPrefPane1.add(this.sets);
        this.subPrefPane1.add(new JLabel("Graphics Size"));
        this.subPrefPane1.add(this.sizes);
        this.subPrefPane1.add(this.msgCannotGoOutsideMaze);
        this.subPrefPane1.add(this.msgFakeExit);
        this.subPrefPane1.add(this.msgInvisibleWall);
        this.subPrefPane1.add(this.msgCannotGoThatWay);
        this.subPrefPane1.add(this.msgSpecificMazeErrors);
        this.subPrefPane1.add(this.msgInvisibleTeleporter);
        this.subPrefPane2.setLayout(new FlowLayout());
        this.subPrefPane2.add(this.prefsOK);
        this.subPrefPane2.add(this.prefsCancel);
        this.prefsOK.addActionListener(this.handler);
        this.prefsCancel.addActionListener(this.handler);
        this.prefFrame.pack();
        this.menuFrame.setResizable(false);
    }

    private void createAccelerators() {
        if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.fileNewAccel = KeyStroke.getKeyStroke(KeyEvent.VK_N,
                    InputEvent.META_MASK);
            this.fileOpenAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O,
                    InputEvent.META_MASK);
            this.fileCloseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_W,
                    InputEvent.META_MASK);
            this.fileSaveAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    InputEvent.META_MASK);
            this.fileSaveAsAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    InputEvent.META_MASK | InputEvent.SHIFT_MASK);
            this.editUndoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                    InputEvent.META_MASK);
            this.editRedoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                    InputEvent.META_MASK | InputEvent.SHIFT_MASK);
            this.editCutAccel = KeyStroke.getKeyStroke(KeyEvent.VK_X,
                    InputEvent.META_MASK);
            this.editCopyAccel = KeyStroke.getKeyStroke(KeyEvent.VK_C,
                    InputEvent.META_MASK);
            this.editPasteAccel = KeyStroke.getKeyStroke(KeyEvent.VK_V,
                    InputEvent.META_MASK);
            this.editPreferencesAccel = KeyStroke
                    .getKeyStroke(KeyEvent.VK_PERIOD, InputEvent.META_MASK);
            this.editClearHistoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                    InputEvent.META_MASK);
            this.playPlayMazeAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P,
                    InputEvent.META_MASK);
            this.playEditMazeAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E,
                    InputEvent.META_MASK);
            this.gameInventoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_I,
                    InputEvent.META_MASK);
            this.gameUseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_U,
                    InputEvent.META_MASK);
        } else {
            this.fileNewAccel = KeyStroke.getKeyStroke(KeyEvent.VK_N,
                    InputEvent.CTRL_MASK);
            this.fileOpenAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O,
                    InputEvent.CTRL_MASK);
            this.fileCloseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_W,
                    InputEvent.CTRL_MASK);
            this.fileSaveAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    InputEvent.CTRL_MASK);
            this.fileSaveAsAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK);
            this.editUndoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                    InputEvent.CTRL_MASK);
            this.editRedoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                    InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK);
            this.editCutAccel = KeyStroke.getKeyStroke(KeyEvent.VK_X,
                    InputEvent.CTRL_MASK);
            this.editCopyAccel = KeyStroke.getKeyStroke(KeyEvent.VK_C,
                    InputEvent.CTRL_MASK);
            this.editPasteAccel = KeyStroke.getKeyStroke(KeyEvent.VK_V,
                    InputEvent.CTRL_MASK);
            this.editPreferencesAccel = KeyStroke
                    .getKeyStroke(KeyEvent.VK_COMMA, InputEvent.CTRL_MASK);
            this.editClearHistoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                    InputEvent.CTRL_MASK);
            this.playPlayMazeAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P,
                    InputEvent.CTRL_MASK);
            this.playEditMazeAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E,
                    InputEvent.CTRL_MASK);
            this.gameInventoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_I,
                    InputEvent.CTRL_MASK);
            this.gameUseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_U,
                    InputEvent.CTRL_MASK);
        }
    }

    private void createMenus() {
        this.mainMenuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.editMenu = new JMenu("Edit");
        this.playMenu = new JMenu("Play");
        this.gameMenu = new JMenu("Game");
        this.fileNew = new JMenuItem("New...");
        this.fileNew.setAccelerator(this.fileNewAccel);
        this.fileOpen = new JMenuItem("Open...");
        this.fileOpen.setAccelerator(this.fileOpenAccel);
        this.fileClose = new JMenuItem("Close");
        this.fileClose.setAccelerator(this.fileCloseAccel);
        this.fileSave = new JMenuItem("Save...");
        this.fileSave.setAccelerator(this.fileSaveAccel);
        this.fileSaveAs = new JMenuItem("Save As...");
        this.fileSaveAs.setAccelerator(this.fileSaveAsAccel);
        this.fileExit = new JMenuItem("Exit");
        this.editUndo = new JMenuItem("Undo");
        this.editUndo.setAccelerator(this.editUndoAccel);
        this.editRedo = new JMenuItem("Redo");
        this.editRedo.setAccelerator(this.editRedoAccel);
        this.editCut = new JMenuItem("Cut");
        this.editCut.setAccelerator(this.editCutAccel);
        this.editCopy = new JMenuItem("Copy");
        this.editCopy.setAccelerator(this.editCopyAccel);
        this.editPaste = new JMenuItem("Paste");
        this.editPaste.setAccelerator(this.editPasteAccel);
        this.editPreferences = new JMenuItem("Preferences...");
        this.editPreferences.setAccelerator(this.editPreferencesAccel);
        this.editClearHistory = new JMenuItem("Clear History");
        this.editClearHistory.setAccelerator(this.editClearHistoryAccel);
        this.playPlayMaze = new JMenuItem("Play Maze");
        this.playPlayMaze.setAccelerator(this.playPlayMazeAccel);
        this.playEditMaze = new JMenuItem("Edit Maze");
        this.playEditMaze.setAccelerator(this.playEditMazeAccel);
        this.gameInventory = new JMenuItem("Show Inventory...");
        this.gameInventory.setAccelerator(this.gameInventoryAccel);
        this.gameUse = new JMenuItem("Use an Item...");
        this.gameUse.setAccelerator(this.gameUseAccel);
        this.fileNew.addActionListener(this.handler);
        this.fileOpen.addActionListener(this.handler);
        this.fileClose.addActionListener(this.handler);
        this.fileSave.addActionListener(this.handler);
        this.fileSaveAs.addActionListener(this.handler);
        this.fileExit.addActionListener(this.handler);
        this.editUndo.addActionListener(this.handler);
        this.editRedo.addActionListener(this.handler);
        // this.editCut.addActionListener(this.handler);
        // this.editCopy.addActionListener(this.handler);
        // this.editPaste.addActionListener(this.handler);
        this.editPreferences.addActionListener(this.handler);
        this.editClearHistory.addActionListener(this.handler);
        this.playPlayMaze.addActionListener(this.handler);
        this.playEditMaze.addActionListener(this.handler);
        this.gameInventory.addActionListener(this.handler);
        this.gameUse.addActionListener(this.handler);
        this.fileMenu.add(this.fileNew);
        this.fileMenu.add(this.fileOpen);
        this.fileMenu.add(this.fileClose);
        this.fileMenu.add(this.fileSave);
        this.fileMenu.add(this.fileSaveAs);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.fileMenu.add(this.fileExit);
        }
        this.editMenu.add(this.editUndo);
        this.editMenu.add(this.editRedo);
        this.editMenu.add(this.editCut);
        this.editMenu.add(this.editCopy);
        this.editMenu.add(this.editPaste);
        this.editMenu.add(this.editPreferences);
        this.editMenu.add(this.editClearHistory);
        this.playMenu.add(this.playPlayMaze);
        this.playMenu.add(this.playEditMaze);
        this.gameMenu.add(this.gameInventory);
        this.gameMenu.add(this.gameUse);
        this.mainMenuBar.add(this.fileMenu);
        this.mainMenuBar.add(this.editMenu);
        this.mainMenuBar.add(this.playMenu);
        this.mainMenuBar.add(this.gameMenu);
    }

    public static void main(final String args[]) {
        MazeRunner.application = new MazeRunner();
        MazeMaker.initEditor();
    }
}