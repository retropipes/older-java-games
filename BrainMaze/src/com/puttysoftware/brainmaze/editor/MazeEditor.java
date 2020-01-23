/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.editor;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.game.GameLogicManager;
import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericCheckpoint;
import com.puttysoftware.brainmaze.generic.GenericConditionalTeleport;
import com.puttysoftware.brainmaze.generic.GenericTeleport;
import com.puttysoftware.brainmaze.generic.MazeObject;
import com.puttysoftware.brainmaze.generic.MazeObjectList;
import com.puttysoftware.brainmaze.generic.TypeConstants;
import com.puttysoftware.brainmaze.maze.Maze;
import com.puttysoftware.brainmaze.maze.MazeConstants;
import com.puttysoftware.brainmaze.maze.MazeManager;
import com.puttysoftware.brainmaze.objects.BlockTeleport;
import com.puttysoftware.brainmaze.objects.ChainTeleport;
import com.puttysoftware.brainmaze.objects.Destination;
import com.puttysoftware.brainmaze.objects.Empty;
import com.puttysoftware.brainmaze.objects.EmptyVoid;
import com.puttysoftware.brainmaze.objects.GeneratedEdge;
import com.puttysoftware.brainmaze.objects.InvisibleBlockTeleport;
import com.puttysoftware.brainmaze.objects.InvisibleChainTeleport;
import com.puttysoftware.brainmaze.objects.InvisibleOneShotChainTeleport;
import com.puttysoftware.brainmaze.objects.InvisibleOneShotTeleport;
import com.puttysoftware.brainmaze.objects.InvisibleTeleport;
import com.puttysoftware.brainmaze.objects.OneShotChainTeleport;
import com.puttysoftware.brainmaze.objects.OneShotTeleport;
import com.puttysoftware.brainmaze.objects.Player;
import com.puttysoftware.brainmaze.objects.RandomInvisibleOneShotTeleport;
import com.puttysoftware.brainmaze.objects.RandomInvisibleTeleport;
import com.puttysoftware.brainmaze.objects.RandomOneShotTeleport;
import com.puttysoftware.brainmaze.objects.RandomTeleport;
import com.puttysoftware.brainmaze.objects.StairsDown;
import com.puttysoftware.brainmaze.objects.StairsUp;
import com.puttysoftware.brainmaze.objects.Teleport;
import com.puttysoftware.brainmaze.objects.TwoWayTeleport;
import com.puttysoftware.brainmaze.prefs.PreferencesManager;
import com.puttysoftware.brainmaze.resourcemanagers.GraphicsManager;
import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.picturepicker.PicturePicker;

public class MazeEditor {
    // Declarations
    private JFrame outputFrame;
    private Container outputPane, secondaryPane, borderPane;
    private JLabel messageLabel;
    private MazeObject savedMazeObject;
    private JScrollBar vertScroll, horzScroll;
    private final EventHandler mhandler;
    private final StartEventHandler shandler;
    private final TeleportEventHandler thandler;
    private final ConditionalTeleportEventHandler cthandler;
    private final LevelPreferencesManager lPrefs;
    private final MazePreferencesManager mPrefs;
    private PicturePicker picker;
    private final String[] groundNames;
    private final String[] objectNames;
    private final MazeObject[] groundObjects;
    private final MazeObject[] objectObjects;
    private final BufferedImageIcon[] groundEditorAppearances;
    private final BufferedImageIcon[] objectEditorAppearances;
    private int TELEPORT_TYPE;
    private GenericConditionalTeleport instanceBeingEdited;
    private int conditionalEditFlag;
    private int currentObjectIndex;
    private UndoRedoEngine engine;
    private final EditorLocationManager elMgr;
    EditorViewingWindowManager evMgr;
    private JLabel[][] drawGrid;
    private boolean mazeChanged;
    boolean goToDestMode;
    private static final int CEF_DEST1 = 1;
    private static final int CEF_DEST2 = 2;
    private static final int CEF_CONDITION = 3;
    private static final int CEF_TRIGGER_TYPE = 4;
    private static String[] choices = new String[] { "Edit Destination 1",
            "Edit Destination 2", "Edit Condition Trigger", "Edit Trigger Type" };
    public static final int TELEPORT_TYPE_GENERIC = 0;
    public static final int TELEPORT_TYPE_INVISIBLE_GENERIC = 1;
    public static final int TELEPORT_TYPE_RANDOM = 2;
    public static final int TELEPORT_TYPE_RANDOM_INVISIBLE = 3;
    public static final int TELEPORT_TYPE_ONESHOT = 4;
    public static final int TELEPORT_TYPE_INVISIBLE_ONESHOT = 5;
    public static final int TELEPORT_TYPE_RANDOM_ONESHOT = 6;
    public static final int TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT = 7;
    public static final int TELEPORT_TYPE_TWOWAY = 8;
    public static final int TELEPORT_TYPE_CHAIN = 9;
    public static final int TELEPORT_TYPE_INVISIBLE_CHAIN = 10;
    public static final int TELEPORT_TYPE_ONESHOT_CHAIN = 11;
    public static final int TELEPORT_TYPE_INVISIBLE_ONESHOT_CHAIN = 12;
    public static final int TELEPORT_TYPE_BLOCK = 13;
    public static final int TELEPORT_TYPE_INVISIBLE_BLOCK = 14;
    public static final int STAIRS_UP = 0;
    public static final int STAIRS_DOWN = 1;

    public MazeEditor() {
        this.savedMazeObject = new Empty();
        this.lPrefs = new LevelPreferencesManager();
        this.mPrefs = new MazePreferencesManager();
        this.mhandler = new EventHandler();
        this.shandler = new StartEventHandler();
        this.thandler = new TeleportEventHandler();
        this.cthandler = new ConditionalTeleportEventHandler();
        this.engine = new UndoRedoEngine();
        final MazeObjectList objectList = BrainMaze.getApplication()
                .getObjects();
        this.groundNames = objectList.getAllGroundLayerNames();
        this.objectNames = objectList.getAllObjectLayerNames();
        this.groundObjects = objectList.getAllGroundLayerObjects();
        this.objectObjects = objectList.getAllObjectLayerObjects();
        this.groundEditorAppearances = objectList
                .getAllGroundLayerEditorAppearances();
        this.objectEditorAppearances = objectList
                .getAllObjectLayerEditorAppearances();
        this.mazeChanged = true;
        this.goToDestMode = false;
        this.instanceBeingEdited = null;
        this.elMgr = new EditorLocationManager();
        this.evMgr = new EditorViewingWindowManager();
    }

    public void mazeChanged() {
        this.mazeChanged = true;
    }

    public void viewingWindowSizeChanged() {
        if (this.outputFrame != null) {
            this.fixLimits();
            this.setUpGUI();
            this.redrawEditor();
        }
    }

    public EditorLocationManager getLocationManager() {
        return this.elMgr;
    }

    public void updateEditorPosition(final int x, final int y, final int z,
            final int w) {
        this.elMgr.offsetEditorLocationW(w);
        this.evMgr.offsetViewingWindowLocationX(x);
        this.evMgr.offsetViewingWindowLocationY(y);
        this.elMgr.offsetEditorLocationZ(z);
        if (w != 0) {
            // Level Change
            BrainMaze.getApplication().getMazeManager().getMaze()
                    .switchLevelOffset(w);
            this.fixLimits();
            this.setUpGUI();
        }
        this.checkMenus();
        this.redrawEditor();
    }

    public void updateEditorPositionAbsolute(final int x, final int y,
            final int z, final int w) {
        final int oldW = this.elMgr.getEditorLocationW();
        this.elMgr.setEditorLocationW(w);
        this.evMgr.setViewingWindowCenterX(y);
        this.evMgr.setViewingWindowCenterY(x);
        this.elMgr.setEditorLocationZ(z);
        if (w != oldW) {
            // Level Change
            BrainMaze.getApplication().getMazeManager().getMaze()
                    .switchLevelOffset(w);
            this.fixLimits();
            this.setUpGUI();
        }
        this.checkMenus();
        this.redrawEditor();
    }

    public void updateEditorLevelAbsolute(final int w) {
        this.elMgr.setEditorLocationW(w);
        // Level Change
        BrainMaze.getApplication().getMazeManager().getMaze().switchLevel(w);
        this.fixLimits();
        this.setUpGUI();
        this.checkMenus();
        this.redrawEditor();
    }

    private void checkMenus() {
        final Application app = BrainMaze.getApplication();
        if (app.getMode() == Application.STATUS_EDITOR) {
            final Maze m = app.getMazeManager().getMaze();
            if (m.getLevels() == Maze.getMinLevels()) {
                app.getMenuManager().disableRemoveLevel();
            } else {
                app.getMenuManager().enableRemoveLevel();
            }
            if (m.getLevels() == Maze.getMaxLevels()) {
                app.getMenuManager().disableAddLevel();
            } else {
                app.getMenuManager().enableAddLevel();
            }
            try {
                if (app.getMazeManager().getMaze()
                        .is3rdDimensionWraparoundEnabled()) {
                    app.getMenuManager().enableDownOneFloor();
                } else {
                    if (this.elMgr.getEditorLocationZ() == this.elMgr
                            .getMinEditorLocationZ()) {
                        app.getMenuManager().disableDownOneFloor();
                    } else {
                        app.getMenuManager().enableDownOneFloor();
                    }
                }
                if (app.getMazeManager().getMaze()
                        .is3rdDimensionWraparoundEnabled()) {
                    app.getMenuManager().enableUpOneFloor();
                } else {
                    if (this.elMgr.getEditorLocationZ() == this.elMgr
                            .getMaxEditorLocationZ()) {
                        app.getMenuManager().disableUpOneFloor();
                    } else {
                        app.getMenuManager().enableUpOneFloor();
                    }
                }
                if (this.elMgr.getEditorLocationW() == this.elMgr
                        .getMinEditorLocationW()) {
                    app.getMenuManager().disableDownOneLevel();
                } else {
                    app.getMenuManager().enableDownOneLevel();
                }
                if (this.elMgr.getEditorLocationW() == this.elMgr
                        .getMaxEditorLocationW()) {
                    app.getMenuManager().disableUpOneLevel();
                } else {
                    app.getMenuManager().enableUpOneLevel();
                }
            } catch (final NullPointerException npe) {
                app.getMenuManager().disableDownOneFloor();
                app.getMenuManager().disableUpOneFloor();
                app.getMenuManager().disableDownOneLevel();
                app.getMenuManager().disableUpOneLevel();
            }
            if (this.elMgr != null) {
                if (this.elMgr.getEditorLocationE() != MazeConstants.LAYER_GROUND) {
                    app.getMenuManager().enableSetStartPoint();
                } else {
                    app.getMenuManager().disableSetStartPoint();
                }
            } else {
                app.getMenuManager().disableSetStartPoint();
            }
            if (!this.engine.tryUndo()) {
                app.getMenuManager().disableUndo();
            } else {
                app.getMenuManager().enableUndo();
            }
            if (!this.engine.tryRedo()) {
                app.getMenuManager().disableRedo();
            } else {
                app.getMenuManager().enableRedo();
            }
            if (this.engine.tryBoth()) {
                app.getMenuManager().disableClearHistory();
            } else {
                app.getMenuManager().enableClearHistory();
            }
        }
    }

    public void toggleLayer() {
        if (this.elMgr.getEditorLocationE() == MazeConstants.LAYER_GROUND) {
            this.elMgr.setEditorLocationE(MazeConstants.LAYER_OBJECT);
        } else {
            this.elMgr.setEditorLocationE(MazeConstants.LAYER_GROUND);
        }
        this.updatePicker();
        this.redrawEditor();
        this.checkMenus();
    }

    public void setMazePrefs() {
        this.mPrefs.showPrefs();
    }

    public void setLevelPrefs() {
        this.lPrefs.showPrefs();
    }

    public void redrawEditor() {
        if (this.outputFrame.isVisible()) {
            if (this.elMgr.getEditorLocationE() == MazeConstants.LAYER_GROUND) {
                this.redrawGround();
            } else {
                this.redrawGroundAndObjects();
            }
        }
    }

    private void redrawGround() {
        // Draw the maze in edit mode
        final Application app = BrainMaze.getApplication();
        int x, y, w;
        int xFix, yFix;
        w = this.elMgr.getEditorLocationW();
        for (x = this.evMgr.getViewingWindowLocationX(); x <= this.evMgr
                .getLowerRightViewingWindowLocationX(); x++) {
            for (y = this.evMgr.getViewingWindowLocationY(); y <= this.evMgr
                    .getLowerRightViewingWindowLocationY(); y++) {
                xFix = x - this.evMgr.getViewingWindowLocationX();
                yFix = y - this.evMgr.getViewingWindowLocationY();
                try {
                    final MazeObject obj1 = app
                            .getMazeManager()
                            .getMaze()
                            .getCell(y, x, this.elMgr.getEditorLocationZ(),
                                    MazeConstants.LAYER_GROUND);
                    final String name1 = obj1.editorRenderHook(y, x,
                            this.elMgr.getEditorLocationZ());
                    this.drawGrid[xFix][yFix].setIcon(GraphicsManager.getImage(
                            name1, obj1.getBaseName(), obj1.getTemplateColor(),
                            obj1.getAttributeName(),
                            obj1.getAttributeTemplateColor()));
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.drawGrid[xFix][yFix].setIcon(GraphicsManager.getImage(
                            "Void", "Void", ColorConstants.COLOR_NONE, "",
                            ColorConstants.COLOR_NONE));
                } catch (final NullPointerException np) {
                    this.drawGrid[xFix][yFix].setIcon(GraphicsManager.getImage(
                            "Void", "Void", ColorConstants.COLOR_NONE, "",
                            ColorConstants.COLOR_NONE));
                }
            }
        }
        this.outputFrame.pack();
        this.outputFrame.setTitle("Editor (Ground Layer) - Floor "
                + (this.elMgr.getEditorLocationZ() + 1) + " Level " + (w + 1));
        this.showOutput();
    }

    private void redrawGroundAndObjects() {
        // Draw the maze in edit mode
        final Application app = BrainMaze.getApplication();
        final Maze m = app.getMazeManager().getMaze();
        int x, y, w;
        int xFix, yFix;
        final int u = m.getStartRow();
        final int v = m.getStartColumn();
        w = this.elMgr.getEditorLocationW();
        for (x = this.evMgr.getViewingWindowLocationX(); x <= this.evMgr
                .getLowerRightViewingWindowLocationX(); x++) {
            for (y = this.evMgr.getViewingWindowLocationY(); y <= this.evMgr
                    .getLowerRightViewingWindowLocationY(); y++) {
                xFix = x - this.evMgr.getViewingWindowLocationX();
                yFix = y - this.evMgr.getViewingWindowLocationY();
                try {
                    final MazeObject obj1 = m.getCell(y, x,
                            this.elMgr.getEditorLocationZ(),
                            MazeConstants.LAYER_GROUND);
                    final MazeObject obj2 = m.getCell(y, x,
                            this.elMgr.getEditorLocationZ(),
                            MazeConstants.LAYER_OBJECT);
                    final BufferedImageIcon img1 = GraphicsManager.getImage(
                            obj1.editorRenderHook(y, x,
                                    this.elMgr.getEditorLocationZ()),
                            obj1.getBaseName(), obj1.getTemplateColor(),
                            obj1.getAttributeName(),
                            obj1.getAttributeTemplateColor());
                    final BufferedImageIcon img2 = GraphicsManager.getImage(
                            obj2.editorRenderHook(y, x,
                                    this.elMgr.getEditorLocationZ()),
                            obj2.getBaseName(), obj2.getTemplateColor(),
                            obj2.getAttributeName(),
                            obj2.getAttributeTemplateColor());
                    if (u == y && v == x) {
                        final MazeObject obj3 = new Player();
                        final BufferedImageIcon img3 = GraphicsManager
                                .getImage(
                                        obj3.gameRenderHook(y, x,
                                                m.getPlayerLocationZ()),
                                        obj3.getGameBaseName(),
                                        obj3.getGameTemplateColor(),
                                        obj3.getGameAttributeName(),
                                        obj3.getGameAttributeTemplateColor());
                        this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                .getVirtualCompositeImage(img1, img2, img3));
                    } else {
                        this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                                .getCompositeImage(img1, img2));
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.drawGrid[xFix][yFix].setIcon(GraphicsManager.getImage(
                            "Void", "Void", ColorConstants.COLOR_NONE, "",
                            ColorConstants.COLOR_NONE));
                } catch (final NullPointerException np) {
                    this.drawGrid[xFix][yFix].setIcon(GraphicsManager.getImage(
                            "Void", "Void", ColorConstants.COLOR_NONE, "",
                            ColorConstants.COLOR_NONE));
                }
            }
        }
        this.outputFrame.pack();
        this.outputFrame.setTitle("Editor (Object Layer) - Floor "
                + (this.elMgr.getEditorLocationZ() + 1) + " Level " + (w + 1));
        this.showOutput();
    }

    private void redrawVirtual(final int x, final int y, final MazeObject obj3) {
        // Draw the square
        final Application app = BrainMaze.getApplication();
        int xFix, yFix;
        xFix = y - this.evMgr.getViewingWindowLocationX();
        yFix = x - this.evMgr.getViewingWindowLocationY();
        try {
            final MazeObject obj1 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(y, x, this.elMgr.getEditorLocationZ(),
                            MazeConstants.LAYER_GROUND);
            final MazeObject obj2 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(y, x, this.elMgr.getEditorLocationZ(),
                            MazeConstants.LAYER_OBJECT);
            final BufferedImageIcon img1 = GraphicsManager.getImage(obj1
                    .editorRenderHook(y, x, this.elMgr.getEditorLocationZ()),
                    obj1.getBaseName(), obj1.getTemplateColor(), obj1
                            .getAttributeName(), obj1
                            .getAttributeTemplateColor());
            final BufferedImageIcon img2 = GraphicsManager.getImage(obj2
                    .editorRenderHook(y, x, this.elMgr.getEditorLocationZ()),
                    obj2.getBaseName(), obj2.getTemplateColor(), obj2
                            .getAttributeName(), obj2
                            .getAttributeTemplateColor());
            final BufferedImageIcon img3 = GraphicsManager.getImage(obj3
                    .editorRenderHook(y, x, this.elMgr.getEditorLocationZ()),
                    obj3.getBaseName(), obj3.getTemplateColor(), obj3
                            .getAttributeName(), obj3
                            .getAttributeTemplateColor());
            this.drawGrid[xFix][yFix].setIcon(GraphicsManager
                    .getVirtualCompositeImage(img1, img2, img3));
            this.drawGrid[xFix][yFix].repaint();
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
        this.outputFrame.pack();
    }

    public void editObject(final int x, final int y, final boolean nested) {
        final Application app = BrainMaze.getApplication();
        int gridX, gridY;
        MazeObject mo;
        if (!nested) {
            this.currentObjectIndex = this.picker.getPicked();
            final int xOffset = this.vertScroll.getValue()
                    - this.vertScroll.getMinimum();
            final int yOffset = this.horzScroll.getValue()
                    - this.horzScroll.getMinimum();
            gridX = x / GraphicsManager.getGraphicSize()
                    + this.evMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            gridY = y / GraphicsManager.getGraphicSize()
                    + this.evMgr.getViewingWindowLocationY() + xOffset
                    - yOffset;
            try {
                this.savedMazeObject = app
                        .getMazeManager()
                        .getMaze()
                        .getCell(gridX, gridY, this.elMgr.getEditorLocationZ(),
                                this.elMgr.getEditorLocationE());
            } catch (final ArrayIndexOutOfBoundsException ae) {
                return;
            }
            MazeObject[] objectChoices = null;
            if (this.elMgr.getEditorLocationE() == MazeConstants.LAYER_GROUND) {
                objectChoices = this.groundObjects;
            } else {
                objectChoices = this.objectObjects;
            }
            mo = objectChoices[this.currentObjectIndex];
        } else {
            gridX = x;
            gridY = y;
            mo = app.getObjects().getAllObjects()[this.currentObjectIndex];
        }
        final MazeObject instance = app.getObjects().getNewInstanceByName(
                mo.getName());
        this.elMgr.setEditorLocationX(gridX);
        this.elMgr.setEditorLocationY(gridY);
        mo.editorPlaceHook();
        try {
            this.checkTwoWayTeleportPair(this.elMgr.getEditorLocationZ());
            this.updateUndoHistory(this.savedMazeObject, gridX, gridY,
                    this.elMgr.getEditorLocationZ(),
                    this.elMgr.getEditorLocationW(),
                    this.elMgr.getEditorLocationE());
            app.getMazeManager()
                    .getMaze()
                    .setCell(instance, gridX, gridY,
                            this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
            this.checkStairPair(this.elMgr.getEditorLocationZ());
            if (PreferencesManager.getEditorAutoEdge() && !nested) {
                this.autoGenerateTransitions(instance, gridX, gridY);
            }
            app.getMazeManager().setDirty(true);
            this.checkMenus();
            this.redrawEditor();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            app.getMazeManager()
                    .getMaze()
                    .setCell(this.savedMazeObject, gridX, gridY,
                            this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
            this.redrawEditor();
        }
    }

    private void autoGenerateTransitions(final MazeObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        this.autoGenerateTransitions1(instance, gridX, gridY);
        this.autoGenerateTransitions2(instance, gridX, gridY);
        this.autoGenerateTransitions3(instance, gridX, gridY);
        this.autoGenerateTransitions4(instance, gridX, gridY);
        this.autoGenerateTransitions6(instance, gridX, gridY);
        this.autoGenerateTransitions7(instance, gridX, gridY);
        this.autoGenerateTransitions8(instance, gridX, gridY);
        this.autoGenerateTransitions9(instance, gridX, gridY);
    }

    private void autoGenerateTransitions1(final MazeObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = BrainMaze.getApplication();
        final MazeObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        MazeObject obj1;
        try {
            obj1 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(gridX - 1, gridY - 1,
                            this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
        } catch (final ArrayIndexOutOfBoundsException aioob2) {
            obj1 = new EmptyVoid();
        }
        if (!obj1.equals(instance)) {
            for (int z = 0; z < generatedObjects.length; z++) {
                if (generatedObjects[z] instanceof GeneratedEdge) {
                    final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                    if (ge.getSource1().equals(instance.getName())
                            && ge.getSource2().equals(obj1.getName())) {
                        // Inverted
                        if (ge.getDirectionName().equals("Southeast Inverted")) {
                            this.currentObjectIndex = generatedOffset + z;
                            this.editObject(gridX - 1, gridY - 1, true);
                            break;
                        }
                    } else if (ge.getSource1().equals(obj1.getName())
                            && ge.getSource2().equals(instance.getName())) {
                        // Normal
                        if (ge.getDirectionName().equals("Southeast")) {
                            this.currentObjectIndex = generatedOffset + z;
                            this.editObject(gridX - 1, gridY - 1, true);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void autoGenerateTransitions2(final MazeObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = BrainMaze.getApplication();
        final MazeObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        MazeObject obj2;
        try {
            obj2 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(gridX, gridY - 1, this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
        } catch (final ArrayIndexOutOfBoundsException aioob2) {
            obj2 = new EmptyVoid();
        }
        if (!obj2.equals(instance)) {
            if (obj2 instanceof GeneratedEdge) {
                final GeneratedEdge ge2 = (GeneratedEdge) obj2;
                for (int z = 0; z < generatedObjects.length; z++) {
                    if (generatedObjects[z] instanceof GeneratedEdge) {
                        final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                        if (ge.getSource1().equals(instance.getName())
                                && ge.getSource2().equals(ge2.getSource2())) {
                            // Inverted
                            if (ge.getDirectionName().equals("North")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX, gridY - 1, true);
                                break;
                            }
                        } else if (ge.getSource1().equals(ge2.getSource1())
                                && ge.getSource2().equals(instance.getName())) {
                            // Normal
                            if (ge.getDirectionName().equals("South")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX, gridY - 1, true);
                                break;
                            }
                        }
                    }
                }
            } else {
                for (int z = 0; z < generatedObjects.length; z++) {
                    if (generatedObjects[z] instanceof GeneratedEdge) {
                        final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                        if (ge.getSource1().equals(instance.getName())
                                && ge.getSource2().equals(obj2.getName())) {
                            // Inverted
                            if (ge.getDirectionName().equals("North")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX, gridY - 1, true);
                                break;
                            }
                        } else if (ge.getSource1().equals(obj2.getName())
                                && ge.getSource2().equals(instance.getName())) {
                            // Normal
                            if (ge.getDirectionName().equals("South")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX, gridY - 1, true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void autoGenerateTransitions3(final MazeObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = BrainMaze.getApplication();
        final MazeObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        MazeObject obj3;
        try {
            obj3 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(gridX + 1, gridY - 1,
                            this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
        } catch (final ArrayIndexOutOfBoundsException aioob2) {
            obj3 = new EmptyVoid();
        }
        if (!obj3.equals(instance)) {
            for (int z = 0; z < generatedObjects.length; z++) {
                if (generatedObjects[z] instanceof GeneratedEdge) {
                    final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                    if (ge.getSource1().equals(instance.getName())
                            && ge.getSource2().equals(obj3.getName())) {
                        // Inverted
                        if (ge.getDirectionName().equals("Southwest Inverted")) {
                            this.currentObjectIndex = generatedOffset + z;
                            this.editObject(gridX + 1, gridY - 1, true);
                            break;
                        }
                    } else if (ge.getSource1().equals(obj3.getName())
                            && ge.getSource2().equals(instance.getName())) {
                        // Normal
                        if (ge.getDirectionName().equals("Southwest")) {
                            this.currentObjectIndex = generatedOffset + z;
                            this.editObject(gridX + 1, gridY - 1, true);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void autoGenerateTransitions4(final MazeObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = BrainMaze.getApplication();
        final MazeObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        MazeObject obj4;
        try {
            obj4 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(gridX - 1, gridY, this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
        } catch (final ArrayIndexOutOfBoundsException aioob2) {
            obj4 = new EmptyVoid();
        }
        if (!obj4.equals(instance)) {
            if (obj4 instanceof GeneratedEdge) {
                final GeneratedEdge ge4 = (GeneratedEdge) obj4;
                for (int z = 0; z < generatedObjects.length; z++) {
                    if (generatedObjects[z] instanceof GeneratedEdge) {
                        final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                        if (ge.getSource1().equals(instance.getName())
                                && ge.getSource2().equals(ge4.getSource2())) {
                            // Inverted
                            if (ge.getDirectionName().equals("West")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX - 1, gridY, true);
                                break;
                            }
                        } else if (ge.getSource1().equals(ge4.getSource1())
                                && ge.getSource2().equals(instance.getName())) {
                            // Normal
                            if (ge.getDirectionName().equals("East")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX - 1, gridY, true);
                                break;
                            }
                        }
                    }
                }
            } else {
                for (int z = 0; z < generatedObjects.length; z++) {
                    if (generatedObjects[z] instanceof GeneratedEdge) {
                        final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                        if (ge.getSource1().equals(instance.getName())
                                && ge.getSource2().equals(obj4.getName())) {
                            // Inverted
                            if (ge.getDirectionName().equals("West")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX - 1, gridY, true);
                                break;
                            }
                        } else if (ge.getSource1().equals(obj4.getName())
                                && ge.getSource2().equals(instance.getName())) {
                            // Normal
                            if (ge.getDirectionName().equals("East")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX - 1, gridY, true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void autoGenerateTransitions6(final MazeObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = BrainMaze.getApplication();
        final MazeObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        MazeObject obj6;
        try {
            obj6 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(gridX + 1, gridY, this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
        } catch (final ArrayIndexOutOfBoundsException aioob2) {
            obj6 = new EmptyVoid();
        }
        if (!obj6.equals(instance)) {
            if (obj6 instanceof GeneratedEdge) {
                final GeneratedEdge ge6 = (GeneratedEdge) obj6;
                for (int z = 0; z < generatedObjects.length; z++) {
                    if (generatedObjects[z] instanceof GeneratedEdge) {
                        final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                        if (ge.getSource1().equals(instance.getName())
                                && ge.getSource2().equals(ge6.getSource2())) {
                            // Inverted
                            if (ge.getDirectionName().equals("East")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX + 1, gridY, true);
                                break;
                            }
                        } else if (ge.getSource1().equals(ge6.getSource1())
                                && ge.getSource2().equals(instance.getName())) {
                            // Normal
                            if (ge.getDirectionName().equals("West")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX + 1, gridY, true);
                                break;
                            }
                        }
                    }
                }
            } else {
                for (int z = 0; z < generatedObjects.length; z++) {
                    if (generatedObjects[z] instanceof GeneratedEdge) {
                        final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                        if (ge.getSource1().equals(instance.getName())
                                && ge.getSource2().equals(obj6.getName())) {
                            // Inverted
                            if (ge.getDirectionName().equals("East")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX + 1, gridY, true);
                                break;
                            }
                        } else if (ge.getSource1().equals(obj6.getName())
                                && ge.getSource2().equals(instance.getName())) {
                            // Normal
                            if (ge.getDirectionName().equals("West")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX + 1, gridY, true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void autoGenerateTransitions7(final MazeObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = BrainMaze.getApplication();
        final MazeObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        MazeObject obj7;
        try {
            obj7 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(gridX - 1, gridY + 1,
                            this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
        } catch (final ArrayIndexOutOfBoundsException aioob2) {
            obj7 = new EmptyVoid();
        }
        if (!obj7.equals(instance)) {
            for (int z = 0; z < generatedObjects.length; z++) {
                if (generatedObjects[z] instanceof GeneratedEdge) {
                    final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                    if (ge.getSource1().equals(instance.getName())
                            && ge.getSource2().equals(obj7.getName())) {
                        // Inverted
                        if (ge.getDirectionName().equals("Northeast Inverted")) {
                            this.currentObjectIndex = generatedOffset + z;
                            this.editObject(gridX - 1, gridY + 1, true);
                            break;
                        }
                    } else if (ge.getSource1().equals(obj7.getName())
                            && ge.getSource2().equals(instance.getName())) {
                        // Normal
                        if (ge.getDirectionName().equals("Northeast")) {
                            this.currentObjectIndex = generatedOffset + z;
                            this.editObject(gridX - 1, gridY + 1, true);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void autoGenerateTransitions8(final MazeObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = BrainMaze.getApplication();
        final MazeObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        MazeObject obj8;
        try {
            obj8 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(gridX, gridY + 1, this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
        } catch (final ArrayIndexOutOfBoundsException aioob2) {
            obj8 = new EmptyVoid();
        }
        if (!obj8.equals(instance)) {
            if (obj8 instanceof GeneratedEdge) {
                final GeneratedEdge ge8 = (GeneratedEdge) obj8;
                for (int z = 0; z < generatedObjects.length; z++) {
                    if (generatedObjects[z] instanceof GeneratedEdge) {
                        final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                        if (ge.getSource1().equals(instance.getName())
                                && ge.getSource2().equals(ge8.getSource2())) {
                            // Inverted
                            if (ge.getDirectionName().equals("South")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX, gridY + 1, true);
                                break;
                            }
                        } else if (ge.getSource1().equals(ge8.getSource1())
                                && ge.getSource2().equals(instance.getName())) {
                            // Normal
                            if (ge.getDirectionName().equals("North")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX, gridY + 1, true);
                                break;
                            }
                        }
                    }
                }
            } else {
                for (int z = 0; z < generatedObjects.length; z++) {
                    if (generatedObjects[z] instanceof GeneratedEdge) {
                        final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                        if (ge.getSource1().equals(instance.getName())
                                && ge.getSource2().equals(obj8.getName())) {
                            // Inverted
                            if (ge.getDirectionName().equals("South")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX, gridY + 1, true);
                                break;
                            }
                        } else if (ge.getSource1().equals(obj8.getName())
                                && ge.getSource2().equals(instance.getName())) {
                            // Normal
                            if (ge.getDirectionName().equals("North")) {
                                this.currentObjectIndex = generatedOffset + z;
                                this.editObject(gridX, gridY + 1, true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void autoGenerateTransitions9(final MazeObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = BrainMaze.getApplication();
        final MazeObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        MazeObject obj9;
        try {
            obj9 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(gridX + 1, gridY + 1,
                            this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
        } catch (final ArrayIndexOutOfBoundsException aioob2) {
            obj9 = new EmptyVoid();
        }
        if (!obj9.equals(instance)) {
            for (int z = 0; z < generatedObjects.length; z++) {
                if (generatedObjects[z] instanceof GeneratedEdge) {
                    final GeneratedEdge ge = (GeneratedEdge) generatedObjects[z];
                    if (ge.getSource1().equals(instance.getName())
                            && ge.getSource2().equals(obj9.getName())) {
                        // Inverted
                        if (ge.getDirectionName().equals("Northwest Inverted")) {
                            this.currentObjectIndex = generatedOffset + z;
                            this.editObject(gridX + 1, gridY + 1, true);
                            break;
                        }
                    } else if (ge.getSource1().equals(obj9.getName())
                            && ge.getSource2().equals(instance.getName())) {
                        // Normal
                        if (ge.getDirectionName().equals("Northwest")) {
                            this.currentObjectIndex = generatedOffset + z;
                            this.editObject(gridX + 1, gridY + 1, true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void probeObjectProperties(final int x, final int y) {
        final Application app = BrainMaze.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int gridX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int gridY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        try {
            final MazeObject mo = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(gridX, gridY, this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
            this.elMgr.setEditorLocationX(gridX);
            this.elMgr.setEditorLocationY(gridY);
            mo.editorProbeHook();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            final EmptyVoid ev = new EmptyVoid();
            ev.determineCurrentAppearance(gridX, gridY,
                    this.elMgr.getEditorLocationZ());
            ev.editorProbeHook();
        }
    }

    public void editObjectProperties(final int x, final int y) {
        final Application app = BrainMaze.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int gridX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int gridY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        try {
            final MazeObject mo = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(gridX, gridY, this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
            this.elMgr.setEditorLocationX(gridX);
            this.elMgr.setEditorLocationY(gridY);
            if (!mo.defersSetProperties()) {
                final MazeObject mo2 = mo.editorPropertiesHook();
                if (mo2 == null) {
                    BrainMaze.getApplication().showMessage(
                            "This object has no properties");
                } else {
                    this.checkTwoWayTeleportPair(this.elMgr
                            .getEditorLocationZ());
                    this.updateUndoHistory(this.savedMazeObject, gridX, gridY,
                            this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationW(),
                            this.elMgr.getEditorLocationE());
                    app.getMazeManager()
                            .getMaze()
                            .setCell(mo2, gridX, gridY,
                                    this.elMgr.getEditorLocationZ(),
                                    this.elMgr.getEditorLocationE());
                    this.checkStairPair(this.elMgr.getEditorLocationZ());
                    this.checkMenus();
                    app.getMazeManager().setDirty(true);
                }
            } else {
                mo.editorPropertiesHook();
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    public void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
    }

    private void checkStairPair(final int z) {
        final Application app = BrainMaze.getApplication();
        final MazeObject mo1 = app
                .getMazeManager()
                .getMaze()
                .getCell(this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(), z,
                        MazeConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2, name3;
        try {
            final MazeObject mo2 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(), z + 1,
                            MazeConstants.LAYER_OBJECT);
            name2 = mo2.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name2 = "";
        }
        try {
            final MazeObject mo3 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(), z - 1,
                            MazeConstants.LAYER_OBJECT);
            name3 = mo3.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name3 = "";
        }
        if (!name1.equals("Stairs Up")) {
            if (name2.equals("Stairs Down")) {
                this.unpairStairs(MazeEditor.STAIRS_UP, z);
            } else if (!name1.equals("Stairs Down")) {
                if (name3.equals("Stairs Up")) {
                    this.unpairStairs(MazeEditor.STAIRS_DOWN, z);
                }
            }
        }
    }

    private void reverseCheckStairPair(final int z) {
        final Application app = BrainMaze.getApplication();
        final MazeObject mo1 = app
                .getMazeManager()
                .getMaze()
                .getCell(this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(), z,
                        MazeConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2, name3;
        try {
            final MazeObject mo2 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(), z + 1,
                            MazeConstants.LAYER_OBJECT);
            name2 = mo2.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name2 = "";
        }
        try {
            final MazeObject mo3 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(), z - 1,
                            MazeConstants.LAYER_OBJECT);
            name3 = mo3.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name3 = "";
        }
        if (name1.equals("Stairs Up")) {
            if (!name2.equals("Stairs Down")) {
                this.pairStairs(MazeEditor.STAIRS_UP, z);
            } else if (name1.equals("Stairs Down")) {
                if (!name3.equals("Stairs Up")) {
                    this.pairStairs(MazeEditor.STAIRS_DOWN, z);
                }
            }
        }
    }

    public void pairStairs(final int type) {
        final Application app = BrainMaze.getApplication();
        switch (type) {
        case STAIRS_UP:
            try {
                app.getMazeManager()
                        .getMaze()
                        .setCell(new StairsDown(),
                                this.elMgr.getEditorLocationX(),
                                this.elMgr.getEditorLocationY(),
                                this.elMgr.getEditorLocationZ() + 1,
                                MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        case STAIRS_DOWN:
            try {
                app.getMazeManager()
                        .getMaze()
                        .setCell(new StairsUp(),
                                this.elMgr.getEditorLocationX(),
                                this.elMgr.getEditorLocationY(),
                                this.elMgr.getEditorLocationZ() - 1,
                                MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        default:
            break;
        }
    }

    private void pairStairs(final int type, final int z) {
        final Application app = BrainMaze.getApplication();
        switch (type) {
        case STAIRS_UP:
            try {
                app.getMazeManager()
                        .getMaze()
                        .setCell(new StairsDown(),
                                this.elMgr.getEditorLocationX(),
                                this.elMgr.getEditorLocationY(), z + 1,
                                MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        case STAIRS_DOWN:
            try {
                app.getMazeManager()
                        .getMaze()
                        .setCell(new StairsUp(),
                                this.elMgr.getEditorLocationX(),
                                this.elMgr.getEditorLocationY(), z - 1,
                                MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        default:
            break;
        }
    }

    private void unpairStairs(final int type, final int z) {
        final Application app = BrainMaze.getApplication();
        switch (type) {
        case STAIRS_UP:
            try {
                app.getMazeManager()
                        .getMaze()
                        .setCell(new Empty(), this.elMgr.getEditorLocationX(),
                                this.elMgr.getEditorLocationY(), z + 1,
                                MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        case STAIRS_DOWN:
            try {
                app.getMazeManager()
                        .getMaze()
                        .setCell(new Empty(), this.elMgr.getEditorLocationX(),
                                this.elMgr.getEditorLocationY(), z - 1,
                                MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException e) {
                // Do nothing
            }
            break;
        default:
            break;
        }
    }

    private void checkTwoWayTeleportPair(final int z) {
        final Application app = BrainMaze.getApplication();
        final MazeObject mo1 = app
                .getMazeManager()
                .getMaze()
                .getCell(this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(), z,
                        MazeConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2;
        int destX, destY, destZ;
        if (name1.equals("Two-Way Teleport")) {
            final TwoWayTeleport twt = (TwoWayTeleport) mo1;
            destX = twt.getDestinationRow();
            destY = twt.getDestinationColumn();
            destZ = twt.getDestinationFloor();
            final MazeObject mo2 = app.getMazeManager().getMaze()
                    .getCell(destX, destY, destZ, MazeConstants.LAYER_OBJECT);
            name2 = mo2.getName();
            if (name2.equals("Two-Way Teleport")) {
                MazeEditor.unpairTwoWayTeleport(destX, destY, destZ);
            }
        }
    }

    private void reverseCheckTwoWayTeleportPair(final int z) {
        final Application app = BrainMaze.getApplication();
        final MazeObject mo1 = app
                .getMazeManager()
                .getMaze()
                .getCell(this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(), z,
                        MazeConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2;
        int destX, destY, destZ;
        if (name1.equals("Two-Way Teleport")) {
            final TwoWayTeleport twt = (TwoWayTeleport) mo1;
            destX = twt.getDestinationRow();
            destY = twt.getDestinationColumn();
            destZ = twt.getDestinationFloor();
            final MazeObject mo2 = app.getMazeManager().getMaze()
                    .getCell(destX, destY, destZ, MazeConstants.LAYER_OBJECT);
            name2 = mo2.getName();
            if (!name2.equals("Two-Way Teleport")) {
                this.pairTwoWayTeleport(destX, destY, destZ);
            }
        }
    }

    public void pairTwoWayTeleport(final int destX, final int destY,
            final int destZ) {
        final Application app = BrainMaze.getApplication();
        app.getMazeManager()
                .getMaze()
                .setCell(
                        new TwoWayTeleport(this.elMgr.getEditorLocationX(),
                                this.elMgr.getEditorLocationY(),
                                this.elMgr.getCameFromZ()), destX, destY,
                        destZ, MazeConstants.LAYER_OBJECT);
    }

    private static void unpairTwoWayTeleport(final int destX, final int destY,
            final int destZ) {
        final Application app = BrainMaze.getApplication();
        app.getMazeManager()
                .getMaze()
                .setCell(new Empty(), destX, destY, destZ,
                        MazeConstants.LAYER_OBJECT);
    }

    public void editCheckpointProperties(final GenericCheckpoint checkpoint) {
        final String def = Integer.toString(checkpoint.getKeyCount());
        final String resp = CommonDialogs.showTextInputDialogWithDefault(
                "Key Count (Number of Sun/Moon Stones needed)", "Editor", def);
        int respVal = -1;
        try {
            respVal = Integer.parseInt(resp);
            if (respVal < 0) {
                throw new NumberFormatException(resp);
            }
        } catch (final NumberFormatException nfe) {
            CommonDialogs
                    .showDialog("The value must be a non-negative integer.");
            return;
        }
        checkpoint.setKeyCount(respVal);
    }

    public void editConditionalTeleportDestination(
            final GenericConditionalTeleport instance) {
        final Application app = BrainMaze.getApplication();
        final String choice = CommonDialogs.showInputDialog("Edit What?",
                "Editor", MazeEditor.choices, MazeEditor.choices[0]);
        if (choice != null) {
            this.instanceBeingEdited = instance;
            this.conditionalEditFlag = 0;
            for (int x = 0; x < MazeEditor.choices.length; x++) {
                if (MazeEditor.choices[x].equals(choice)) {
                    this.conditionalEditFlag = x + 1;
                    break;
                }
            }
            if (this.conditionalEditFlag != 0) {
                if (this.conditionalEditFlag == MazeEditor.CEF_CONDITION) {
                    final String def = Integer.toString(instance
                            .getTriggerValue());
                    final String resp = CommonDialogs
                            .showTextInputDialogWithDefault(
                                    "Condition Trigger Value (Number of Sun/Moon Stones needed)",
                                    "Editor", def);
                    int respVal = -1;
                    try {
                        respVal = Integer.parseInt(resp);
                        if (respVal < 0) {
                            throw new NumberFormatException(resp);
                        }
                    } catch (final NumberFormatException nfe) {
                        CommonDialogs
                                .showDialog("The value must be a non-negative integer.");
                        this.instanceBeingEdited = null;
                        return;
                    }
                    instance.setTriggerValue(respVal);
                } else if (this.conditionalEditFlag == MazeEditor.CEF_TRIGGER_TYPE) {
                    final int respIndex = instance.getSunMoon();
                    final String[] ttChoices = new String[] { "Sun Stones",
                            "Moon Stones" };
                    final String ttChoice = CommonDialogs.showInputDialog(
                            "Condition Trigger Type?", "Editor", ttChoices,
                            ttChoices[respIndex - 1]);
                    if (ttChoice != null) {
                        int newResp = -1;
                        for (int x = 0; x < MazeEditor.choices.length; x++) {
                            if (ttChoices[x].equals(ttChoice)) {
                                newResp = x + 1;
                                break;
                            }
                        }
                        if (newResp != -1) {
                            instance.setSunMoon(newResp);
                        }
                    }
                } else {
                    this.horzScroll.removeAdjustmentListener(this.mhandler);
                    this.vertScroll.removeAdjustmentListener(this.mhandler);
                    this.secondaryPane.removeMouseListener(this.mhandler);
                    this.horzScroll.addAdjustmentListener(this.cthandler);
                    this.vertScroll.addAdjustmentListener(this.cthandler);
                    this.secondaryPane.addMouseListener(this.cthandler);
                    this.elMgr.setCameFromZ(this.elMgr.getEditorLocationZ());
                    app.getMenuManager().disableDownOneLevel();
                    app.getMenuManager().disableUpOneLevel();
                }
            } else {
                this.instanceBeingEdited = null;
            }
        }
    }

    public MazeObject editTeleportDestination(final int type) {
        final Application app = BrainMaze.getApplication();
        this.TELEPORT_TYPE = type;
        switch (type) {
        case TELEPORT_TYPE_GENERIC:
        case TELEPORT_TYPE_INVISIBLE_GENERIC:
        case TELEPORT_TYPE_ONESHOT:
        case TELEPORT_TYPE_INVISIBLE_ONESHOT:
        case TELEPORT_TYPE_TWOWAY:
        case TELEPORT_TYPE_CHAIN:
        case TELEPORT_TYPE_INVISIBLE_CHAIN:
        case TELEPORT_TYPE_ONESHOT_CHAIN:
        case TELEPORT_TYPE_INVISIBLE_ONESHOT_CHAIN:
        case TELEPORT_TYPE_BLOCK:
        case TELEPORT_TYPE_INVISIBLE_BLOCK:
            BrainMaze.getApplication().showMessage(
                    "Click to set teleport destination");
            break;
        case TELEPORT_TYPE_RANDOM:
        case TELEPORT_TYPE_RANDOM_INVISIBLE:
        case TELEPORT_TYPE_RANDOM_ONESHOT:
        case TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT:
            return this.editRandomTeleportDestination(type);
        default:
            break;
        }
        this.horzScroll.removeAdjustmentListener(this.mhandler);
        this.vertScroll.removeAdjustmentListener(this.mhandler);
        this.secondaryPane.removeMouseListener(this.mhandler);
        this.horzScroll.addAdjustmentListener(this.thandler);
        this.vertScroll.addAdjustmentListener(this.thandler);
        this.secondaryPane.addMouseListener(this.thandler);
        this.elMgr.setCameFromZ(this.elMgr.getEditorLocationZ());
        app.getMenuManager().disableDownOneLevel();
        app.getMenuManager().disableUpOneLevel();
        return null;
    }

    private MazeObject editRandomTeleportDestination(final int type) {
        String input1 = null, input2 = null;
        this.TELEPORT_TYPE = type;
        int destX = 0, destY = 0;
        switch (type) {
        case TELEPORT_TYPE_RANDOM:
        case TELEPORT_TYPE_RANDOM_INVISIBLE:
        case TELEPORT_TYPE_RANDOM_ONESHOT:
        case TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT:
            input1 = CommonDialogs.showTextInputDialog("Random row range:",
                    "Editor");
            if (input1 != null) {
                input2 = CommonDialogs.showTextInputDialog(
                        "Random column range:", "Editor");
                if (input2 != null) {
                    try {
                        destX = Integer.parseInt(input1);
                        destY = Integer.parseInt(input2);
                    } catch (final NumberFormatException nf) {
                        CommonDialogs
                                .showDialog("Row and column ranges must be integers.");
                    }
                    switch (type) {
                    case TELEPORT_TYPE_RANDOM:
                        return new RandomTeleport(destX, destY);
                    case TELEPORT_TYPE_RANDOM_INVISIBLE:
                        return new RandomInvisibleTeleport(destX, destY);
                    case TELEPORT_TYPE_RANDOM_ONESHOT:
                        return new RandomOneShotTeleport(destX, destY);
                    case TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT:
                        return new RandomInvisibleOneShotTeleport(destX, destY);
                    default:
                        break;
                    }
                }
            }
            break;
        default:
            break;
        }
        return null;
    }

    public void setTeleportDestination(final int x, final int y) {
        final Application app = BrainMaze.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int destX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = this.elMgr.getEditorLocationZ();
        try {
            app.getMazeManager().getMaze()
                    .getCell(destX, destY, destZ, MazeConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.horzScroll.removeAdjustmentListener(this.thandler);
            this.vertScroll.removeAdjustmentListener(this.thandler);
            this.secondaryPane.removeMouseListener(this.thandler);
            this.horzScroll.addAdjustmentListener(this.mhandler);
            this.vertScroll.addAdjustmentListener(this.mhandler);
            this.secondaryPane.addMouseListener(this.mhandler);
            return;
        }
        switch (this.TELEPORT_TYPE) {
        case TELEPORT_TYPE_GENERIC:
            app.getMazeManager()
                    .getMaze()
                    .setCell(new Teleport(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            break;
        case TELEPORT_TYPE_INVISIBLE_GENERIC:
            app.getMazeManager()
                    .getMaze()
                    .setCell(new InvisibleTeleport(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            break;
        case TELEPORT_TYPE_ONESHOT:
            app.getMazeManager()
                    .getMaze()
                    .setCell(new OneShotTeleport(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            break;
        case TELEPORT_TYPE_INVISIBLE_ONESHOT:
            app.getMazeManager()
                    .getMaze()
                    .setCell(new InvisibleOneShotTeleport(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            break;
        case TELEPORT_TYPE_TWOWAY:
            app.getMazeManager()
                    .getMaze()
                    .setCell(new TwoWayTeleport(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            this.pairTwoWayTeleport(destX, destY, destZ);
            break;
        case TELEPORT_TYPE_CHAIN:
            app.getMazeManager()
                    .getMaze()
                    .setCell(new ChainTeleport(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            break;
        case TELEPORT_TYPE_INVISIBLE_CHAIN:
            app.getMazeManager()
                    .getMaze()
                    .setCell(new InvisibleChainTeleport(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            break;
        case TELEPORT_TYPE_ONESHOT_CHAIN:
            app.getMazeManager()
                    .getMaze()
                    .setCell(new OneShotChainTeleport(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            break;
        case TELEPORT_TYPE_INVISIBLE_ONESHOT_CHAIN:
            app.getMazeManager()
                    .getMaze()
                    .setCell(
                            new InvisibleOneShotChainTeleport(destX, destY,
                                    destZ), this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            break;
        case TELEPORT_TYPE_BLOCK:
            app.getMazeManager()
                    .getMaze()
                    .setCell(new BlockTeleport(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            break;
        case TELEPORT_TYPE_INVISIBLE_BLOCK:
            app.getMazeManager()
                    .getMaze()
                    .setCell(new InvisibleBlockTeleport(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            break;
        default:
            break;
        }
        this.horzScroll.removeAdjustmentListener(this.thandler);
        this.vertScroll.removeAdjustmentListener(this.thandler);
        this.secondaryPane.removeMouseListener(this.thandler);
        this.horzScroll.addAdjustmentListener(this.mhandler);
        this.vertScroll.addAdjustmentListener(this.mhandler);
        this.secondaryPane.addMouseListener(this.mhandler);
        this.checkMenus();
        BrainMaze.getApplication().showMessage("Destination set.");
        app.getMazeManager().setDirty(true);
        this.redrawEditor();
    }

    void setConditionalTeleportDestination(final int x, final int y) {
        final Application app = BrainMaze.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int destX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = this.elMgr.getEditorLocationZ();
        if (this.instanceBeingEdited != null) {
            if (this.conditionalEditFlag == MazeEditor.CEF_DEST1) {
                this.instanceBeingEdited.setDestinationRow(destX);
                this.instanceBeingEdited.setDestinationColumn(destY);
                this.instanceBeingEdited.setDestinationFloor(destZ);
            } else if (this.conditionalEditFlag == MazeEditor.CEF_DEST2) {
                this.instanceBeingEdited.setDestinationRow2(destX);
                this.instanceBeingEdited.setDestinationColumn2(destY);
                this.instanceBeingEdited.setDestinationFloor2(destZ);
            }
            this.instanceBeingEdited = null;
        }
        this.horzScroll.removeAdjustmentListener(this.cthandler);
        this.vertScroll.removeAdjustmentListener(this.cthandler);
        this.secondaryPane.removeMouseListener(this.cthandler);
        this.horzScroll.addAdjustmentListener(this.mhandler);
        this.vertScroll.addAdjustmentListener(this.mhandler);
        this.secondaryPane.addMouseListener(this.mhandler);
        this.checkMenus();
        BrainMaze.getApplication().showMessage("Destination set.");
        app.getMazeManager().setDirty(true);
        this.redrawEditor();
    }

    public void editPlayerLocation() {
        // Swap event handlers
        this.horzScroll.removeAdjustmentListener(this.mhandler);
        this.vertScroll.removeAdjustmentListener(this.mhandler);
        this.secondaryPane.removeMouseListener(this.mhandler);
        this.horzScroll.addAdjustmentListener(this.shandler);
        this.vertScroll.addAdjustmentListener(this.shandler);
        this.secondaryPane.addMouseListener(this.shandler);
        BrainMaze.getApplication().showMessage("Click to set start point");
    }

    void setPlayerLocation(final int x, final int y) {
        final Application app = BrainMaze.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int destX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        // Set new player
        try {
            app.getMazeManager().getMaze().saveStart();
            app.getMazeManager().getMaze().setStartRow(destX);
            app.getMazeManager().getMaze().setStartColumn(destY);
            app.getMazeManager().getMaze()
                    .setStartFloor(this.elMgr.getEditorLocationZ());
            BrainMaze.getApplication().showMessage("Start point set.");
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            app.getMazeManager().getMaze().restoreStart();
            BrainMaze.getApplication().showMessage("Aim within the maze");
        }
        // Swap event handlers
        this.horzScroll.removeAdjustmentListener(this.shandler);
        this.vertScroll.removeAdjustmentListener(this.shandler);
        this.secondaryPane.removeMouseListener(this.shandler);
        this.horzScroll.addAdjustmentListener(this.mhandler);
        this.vertScroll.addAdjustmentListener(this.mhandler);
        this.secondaryPane.addMouseListener(this.mhandler);
        // Set dirty flag
        app.getMazeManager().setDirty(true);
        this.redrawEditor();
    }

    public void editMaze() {
        final Application app = BrainMaze.getApplication();
        if (app.getMazeManager().getLoaded()) {
            app.getGUIManager().hideGUI();
            app.setInEditor(true);
            // Reset game state
            app.getGameManager().resetGameState();
            // Create the managers
            if (this.mazeChanged) {
                this.fixLimits();
                this.mazeChanged = false;
            }
            this.fixLimits();
            this.setUpGUI();
            this.clearHistory();
            // Make sure message area is attached to border pane
            this.borderPane.removeAll();
            this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
            this.borderPane.add(this.outputPane, BorderLayout.CENTER);
            this.borderPane.add(this.picker.getPicker(), BorderLayout.EAST);
            this.showOutput();
            this.redrawEditor();
            this.checkMenus();
        } else {
            CommonDialogs.showDialog("No Maze Opened");
        }
    }

    public boolean newMaze() {
        final Application app = BrainMaze.getApplication();
        boolean success = true;
        boolean saved = true;
        int status = 0;
        if (app.getMazeManager().getDirty()) {
            status = app.getMazeManager().showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = app.getMazeManager().saveMaze();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                app.getMazeManager().setDirty(false);
            }
        }
        if (saved) {
            app.getGameManager().resetPlayerLocation();
            app.getMazeManager().setMaze(new Maze());
            success = app.getMazeManager().getMaze().addLevel(32, 32, 1);
            if (success) {
                this.fixLimits();
                app.getMazeManager()
                        .getMaze()
                        .fillLevel(PreferencesManager.getEditorDefaultFill(),
                                new Empty());
                // Save the entire level
                app.getMazeManager().getMaze().save();
                this.checkMenus();
            }
            if (success) {
                app.getMazeManager().clearLastUsedFilenames();
                this.clearHistory();
            }
        } else {
            success = false;
        }
        if (success) {
            this.mazeChanged = true;
            app.getGameManager().stateChanged();
            CommonDialogs.showTitledDialog("Maze Created!", "Editor");
        }
        return success;
    }

    public void fixLimits() {
        // Fix limits
        final Application app = BrainMaze.getApplication();
        if (app.getMazeManager().getMaze() != null && this.elMgr != null
                && this.evMgr != null) {
            this.elMgr.setLimitsFromMaze(app.getMazeManager().getMaze());
            this.evMgr.halfOffsetMaximumViewingWindowLocationsFromMaze(app
                    .getMazeManager().getMaze());
        }
    }

    private boolean confirmNonUndoable(final String task) {
        final int confirm = CommonDialogs
                .showConfirmDialog(
                        "Are you sure you want to "
                                + task
                                + "?"
                                + " This action is NOT undoable and will clear the undo/redo history!",
                        "Editor");
        if (confirm == JOptionPane.YES_OPTION) {
            this.clearHistory();
            return true;
        }
        return false;
    }

    public void fillLevel() {
        if (this.confirmNonUndoable("overwrite the active level with default data")) {
            BrainMaze.getApplication().getMazeManager().getMaze().fill();
            BrainMaze.getApplication().showMessage("Level filled.");
            BrainMaze.getApplication().getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillFloor() {
        if (this.confirmNonUndoable("overwrite the active floor within the active level with default data")) {
            BrainMaze.getApplication().getMazeManager().getMaze()
                    .fillFloor(this.elMgr.getEditorLocationZ());
            BrainMaze.getApplication().showMessage("Floor filled.");
            BrainMaze.getApplication().getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillLevelRandomly() {
        if (this.confirmNonUndoable("overwrite the active level with random data")) {
            if (BrainMaze.getApplication().getMenuManager().useFillRuleSets()) {
                BrainMaze.getApplication().getMazeManager().getMaze()
                        .fillLevelRandomlyCustom();
            } else {
                BrainMaze.getApplication().getMazeManager().getMaze()
                        .fillLevelRandomly();
            }
            BrainMaze.getApplication().showMessage("Level randomly filled.");
            BrainMaze.getApplication().getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillFloorRandomly() {
        if (this.confirmNonUndoable("overwrite the active floor within the active level with random data")) {
            if (BrainMaze.getApplication().getMenuManager().useFillRuleSets()) {
                BrainMaze
                        .getApplication()
                        .getMazeManager()
                        .getMaze()
                        .fillFloorRandomlyCustom(
                                this.elMgr.getEditorLocationZ());
            } else {
                BrainMaze.getApplication().getMazeManager().getMaze()
                        .fillFloorRandomly(this.elMgr.getEditorLocationZ());
            }
            BrainMaze.getApplication().showMessage("Floor randomly filled.");
            BrainMaze.getApplication().getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillLevelAndLayer() {
        if (this.confirmNonUndoable("overwrite the active layer on the active level with default data")) {
            BrainMaze.getApplication().getMazeManager().getMaze()
                    .fillLayer(this.elMgr.getEditorLocationE());
            BrainMaze.getApplication().showMessage("Level and layer filled.");
            BrainMaze.getApplication().getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillFloorAndLayer() {
        if (this.confirmNonUndoable("overwrite the active layer on the active floor within the active level with default data")) {
            BrainMaze
                    .getApplication()
                    .getMazeManager()
                    .getMaze()
                    .fillFloorAndLayer(this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
            BrainMaze.getApplication().showMessage("Floor and layer filled.");
            BrainMaze.getApplication().getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillLevelAndLayerRandomly() {
        if (this.confirmNonUndoable("overwrite the active layer on the active level with random data")) {
            if (BrainMaze.getApplication().getMenuManager().useFillRuleSets()) {
                BrainMaze
                        .getApplication()
                        .getMazeManager()
                        .getMaze()
                        .fillLevelAndLayerRandomlyCustom(
                                this.elMgr.getEditorLocationE());
            } else {
                BrainMaze
                        .getApplication()
                        .getMazeManager()
                        .getMaze()
                        .fillLevelAndLayerRandomly(
                                this.elMgr.getEditorLocationE());
            }
            BrainMaze.getApplication().showMessage(
                    "Level and layer randomly filled.");
            BrainMaze.getApplication().getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillFloorAndLayerRandomly() {
        if (this.confirmNonUndoable("overwrite the active layer on the active floor within the active level with random data")) {
            if (BrainMaze.getApplication().getMenuManager().useFillRuleSets()) {
                BrainMaze
                        .getApplication()
                        .getMazeManager()
                        .getMaze()
                        .fillFloorAndLayerRandomlyCustom(
                                this.elMgr.getEditorLocationZ(),
                                this.elMgr.getEditorLocationE());
            } else {
                BrainMaze
                        .getApplication()
                        .getMazeManager()
                        .getMaze()
                        .fillFloorAndLayerRandomly(
                                this.elMgr.getEditorLocationZ(),
                                this.elMgr.getEditorLocationE());
            }
            BrainMaze.getApplication().showMessage(
                    "Floor and layer randomly filled.");
            BrainMaze.getApplication().getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public boolean addLevel() {
        final Application app = BrainMaze.getApplication();
        int levelSizeX, levelSizeY, levelSizeZ;
        final int maxR = Maze.getMaxRows();
        final int minR = Maze.getMinRows();
        final int maxC = Maze.getMaxColumns();
        final int minC = Maze.getMinColumns();
        final int maxF = Maze.getMaxFloors();
        final int minF = Maze.getMinFloors();
        final String msg = "New Level";
        boolean success = true;
        String input1, input2, input3;
        input1 = CommonDialogs.showTextInputDialog("Number of rows (" + minR
                + "-" + maxR + ")?", msg);
        if (input1 != null) {
            input2 = CommonDialogs.showTextInputDialog("Number of columns ("
                    + minC + "-" + maxC + ")?", msg);
            if (input2 != null) {
                input3 = CommonDialogs.showTextInputDialog("Number of floors ("
                        + minF + "-" + maxF + ")?", msg);
                if (input3 != null) {
                    try {
                        levelSizeX = Integer.parseInt(input1);
                        levelSizeY = Integer.parseInt(input2);
                        levelSizeZ = Integer.parseInt(input3);
                        if (levelSizeX < minR) {
                            throw new NumberFormatException(
                                    "Rows must be at least " + minR + ".");
                        }
                        if (levelSizeX > maxR) {
                            throw new NumberFormatException(
                                    "Rows must be less than or equal to "
                                            + maxR + ".");
                        }
                        if (levelSizeY < minC) {
                            throw new NumberFormatException(
                                    "Columns must be at least " + minC + ".");
                        }
                        if (levelSizeY > maxC) {
                            throw new NumberFormatException(
                                    "Columns must be less than or equal to "
                                            + maxC + ".");
                        }
                        if (levelSizeZ < minF) {
                            throw new NumberFormatException(
                                    "Floors must be at least " + minF + ".");
                        }
                        if (levelSizeZ > maxF) {
                            throw new NumberFormatException(
                                    "Floors must be less than or equal to "
                                            + maxF + ".");
                        }
                        final int saveLevel = app.getMazeManager().getMaze()
                                .getActiveLevelNumber();
                        success = app.getMazeManager().getMaze()
                                .addLevel(levelSizeX, levelSizeY, levelSizeZ);
                        if (success) {
                            this.fixLimits();
                            this.evMgr
                                    .setViewingWindowLocationX(0 - (this.evMgr
                                            .getViewingWindowSizeX() - 1) / 2);
                            this.evMgr
                                    .setViewingWindowLocationY(0 - (this.evMgr
                                            .getViewingWindowSizeY() - 1) / 2);
                            app.getMazeManager()
                                    .getMaze()
                                    .fillLevel(
                                            PreferencesManager
                                                    .getEditorDefaultFill(),
                                            new Empty());
                            // Save the entire level
                            app.getMazeManager().getMaze().save();
                            app.getMazeManager().getMaze()
                                    .switchLevel(saveLevel);
                            this.checkMenus();
                        }
                    } catch (final NumberFormatException nf) {
                        CommonDialogs.showDialog(nf.getMessage());
                        success = false;
                    }
                } else {
                    // User canceled
                    success = false;
                }
            } else {
                // User canceled
                success = false;
            }
        } else {
            // User canceled
            success = false;
        }
        return success;
    }

    public boolean resizeLevel() {
        final Application app = BrainMaze.getApplication();
        int levelSizeX, levelSizeY, levelSizeZ;
        final int maxR = Maze.getMaxRows();
        final int minR = Maze.getMinRows();
        final int maxC = Maze.getMaxColumns();
        final int minC = Maze.getMinColumns();
        final int maxF = Maze.getMaxFloors();
        final int minF = Maze.getMinFloors();
        final String msg = "Resize Level";
        boolean success = true;
        String input1, input2, input3;
        input1 = CommonDialogs.showTextInputDialogWithDefault(
                "Number of rows (" + minR + "-" + maxR + ")?", msg,
                Integer.toString(app.getMazeManager().getMaze().getRows()));
        if (input1 != null) {
            input2 = CommonDialogs.showTextInputDialogWithDefault(
                    "Number of columns (" + minC + "-" + maxC + ")?",
                    msg,
                    Integer.toString(app.getMazeManager().getMaze()
                            .getColumns()));
            if (input2 != null) {
                input3 = CommonDialogs.showTextInputDialogWithDefault(
                        "Number of floors (" + minF + "-" + maxF + ")?",
                        msg,
                        Integer.toString(app.getMazeManager().getMaze()
                                .getFloors()));
                if (input3 != null) {
                    try {
                        levelSizeX = Integer.parseInt(input1);
                        levelSizeY = Integer.parseInt(input2);
                        levelSizeZ = Integer.parseInt(input3);
                        if (levelSizeX < minR) {
                            throw new NumberFormatException(
                                    "Rows must be at least " + minR + ".");
                        }
                        if (levelSizeX > maxR) {
                            throw new NumberFormatException(
                                    "Rows must be less than or equal to "
                                            + maxR + ".");
                        }
                        if (levelSizeY < minC) {
                            throw new NumberFormatException(
                                    "Columns must be at least " + minC + ".");
                        }
                        if (levelSizeY > maxC) {
                            throw new NumberFormatException(
                                    "Columns must be less than or equal to "
                                            + maxC + ".");
                        }
                        if (levelSizeZ < minF) {
                            throw new NumberFormatException(
                                    "Floors must be at least " + minF + ".");
                        }
                        if (levelSizeZ > maxF) {
                            throw new NumberFormatException(
                                    "Floors must be less than or equal to "
                                            + maxF + ".");
                        }
                        app.getMazeManager().getMaze()
                                .resize(levelSizeX, levelSizeY, levelSizeZ);
                        this.fixLimits();
                        this.evMgr.setViewingWindowLocationX(0 - (this.evMgr
                                .getViewingWindowSizeX() - 1) / 2);
                        this.evMgr.setViewingWindowLocationY(0 - (this.evMgr
                                .getViewingWindowSizeY() - 1) / 2);
                        // Save the entire level
                        app.getMazeManager().getMaze().save();
                        this.checkMenus();
                        // Redraw
                        this.redrawEditor();
                    } catch (final NumberFormatException nf) {
                        CommonDialogs.showDialog(nf.getMessage());
                        success = false;
                    }
                } else {
                    // User canceled
                    success = false;
                }
            } else {
                // User canceled
                success = false;
            }
        } else {
            // User canceled
            success = false;
        }
        return success;
    }

    public boolean removeLevel() {
        final Application app = BrainMaze.getApplication();
        int level;
        boolean success = true;
        String input;
        input = CommonDialogs.showTextInputDialog("Level Number (1-"
                + app.getMazeManager().getMaze().getLevels() + ")?",
                "Remove Level");
        if (input != null) {
            try {
                level = Integer.parseInt(input);
                if (level < 1
                        || level > app.getMazeManager().getMaze().getLevels()) {
                    throw new NumberFormatException(
                            "Level number must be in the range 1 to "
                                    + app.getMazeManager().getMaze()
                                            .getLevels() + ".");
                }
                success = app.getMazeManager().getMaze().removeLevel();
                if (success) {
                    this.fixLimits();
                    if (level == this.elMgr.getEditorLocationW() + 1) {
                        // Deleted current level - go to level 1
                        this.updateEditorLevelAbsolute(0);
                    }
                    this.checkMenus();
                }
            } catch (final NumberFormatException nf) {
                CommonDialogs.showDialog(nf.getMessage());
                success = false;
            }
        } else {
            // User canceled
            success = false;
        }
        return success;
    }

    public void goToLocationHandler() {
        int locX, locY, locZ, locW;
        final String msg = "Go To Location...";
        String input1, input2, input3, input4;
        input1 = CommonDialogs.showTextInputDialog("Row?", msg);
        if (input1 != null) {
            input2 = CommonDialogs.showTextInputDialog("Column?", msg);
            if (input2 != null) {
                input3 = CommonDialogs.showTextInputDialog("Floor?", msg);
                if (input3 != null) {
                    input4 = CommonDialogs.showTextInputDialog("Level?", msg);
                    if (input4 != null) {
                        try {
                            locX = Integer.parseInt(input1) - 1;
                            locY = Integer.parseInt(input2) - 1;
                            locZ = Integer.parseInt(input3) - 1;
                            locW = Integer.parseInt(input4) - 1;
                            this.updateEditorPositionAbsolute(locX, locY, locZ,
                                    locW);
                        } catch (final NumberFormatException nf) {
                            CommonDialogs.showDialog(nf.getMessage());
                        }
                    }
                }
            }
        }
    }

    public void goToDestinationHandler() {
        if (!this.goToDestMode) {
            this.goToDestMode = true;
            BrainMaze.getApplication().showMessage(
                    "Click a teleport to go to its destination");
        }
    }

    void goToDestination(final int x, final int y) {
        if (this.goToDestMode) {
            this.goToDestMode = false;
            final int xOffset = this.vertScroll.getValue()
                    - this.vertScroll.getMinimum();
            final int yOffset = this.horzScroll.getValue()
                    - this.horzScroll.getMinimum();
            final int locX = x / GraphicsManager.getGraphicSize()
                    + this.evMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            final int locY = y / GraphicsManager.getGraphicSize()
                    + this.evMgr.getViewingWindowLocationY() + xOffset
                    - yOffset;
            final int locZ = this.elMgr.getEditorLocationZ();
            final MazeObject there = BrainMaze
                    .getApplication()
                    .getMazeManager()
                    .getMazeObject(locX, locY, locZ, MazeConstants.LAYER_OBJECT);
            if (there instanceof GenericTeleport) {
                final GenericTeleport gt = (GenericTeleport) there;
                final int destX = gt.getDestinationRow();
                final int destY = gt.getDestinationColumn();
                final int destZ = gt.getDestinationFloor();
                final int destW = this.elMgr.getEditorLocationW();
                this.updateEditorPositionAbsolute(destX, destY, destZ, destW);
                BrainMaze.getApplication().showMessage("");
                this.redrawVirtual(destX, destY, new Destination());
            } else {
                BrainMaze.getApplication().showMessage(
                        "This object does not have a destination.");
            }
        }
    }

    public void showOutput() {
        final Application app = BrainMaze.getApplication();
        this.outputFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        app.getMenuManager().setEditorMenus();
        this.outputFrame.setVisible(true);
        this.outputFrame.pack();
    }

    public void hideOutput() {
        if (this.outputFrame != null) {
            this.outputFrame.setVisible(false);
        }
    }

    void disableOutput() {
        this.outputFrame.setEnabled(false);
    }

    void enableOutput() {
        this.outputFrame.setEnabled(true);
        this.checkMenus();
    }

    public JFrame getOutputFrame() {
        if (this.outputFrame != null && this.outputFrame.isVisible()) {
            return this.outputFrame;
        } else {
            return null;
        }
    }

    public void exitEditor() {
        final Application app = BrainMaze.getApplication();
        // Hide the editor
        this.hideOutput();
        app.setInEditor(false);
        final MazeManager mm = app.getMazeManager();
        final GameLogicManager gm = app.getGameManager();
        // Save the entire level
        mm.getMaze().save();
        // Reset the viewing window
        gm.resetViewingWindowAndPlayerLocation();
        gm.stateChanged();
        BrainMaze.getApplication().getGUIManager().showGUI();
    }

    private void setUpGUI() {
        // Destroy the old GUI, if one exists
        if (this.outputFrame != null) {
            this.outputFrame.dispose();
        }
        this.messageLabel = new JLabel(" ");
        final Application app = BrainMaze.getApplication();
        this.outputFrame = new JFrame("Editor");
        final Image iconlogo = app.getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.outputPane = new Container();
        this.secondaryPane = new Container();
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.drawGrid = new JLabel[this.evMgr.getViewingWindowSizeX()][this.evMgr
                .getViewingWindowSizeY()];
        for (int x = 0; x < this.evMgr.getViewingWindowSizeX(); x++) {
            for (int y = 0; y < this.evMgr.getViewingWindowSizeY(); y++) {
                this.drawGrid[x][y] = new JLabel();
                // Mac OS X-specific fix to make draw grid line up properly
                if (System.getProperty("os.name").startsWith("Mac OS X")) {
                    this.drawGrid[x][y].setBorder(new EmptyBorder(0, 0, 0, 0));
                }
                this.secondaryPane.add(this.drawGrid[x][y]);
            }
        }
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        final GridBagLayout gridbag = new GridBagLayout();
        final GridBagConstraints c = new GridBagConstraints();
        this.outputPane.setLayout(gridbag);
        this.outputFrame.setResizable(false);
        c.fill = GridBagConstraints.BOTH;
        this.secondaryPane.setLayout(new GridLayout(this.evMgr
                .getViewingWindowSizeX(), this.evMgr.getViewingWindowSizeY()));
        this.horzScroll = new JScrollBar(Adjustable.HORIZONTAL,
                this.evMgr.getMinimumViewingWindowLocationY(),
                this.evMgr.getViewingWindowSizeY(),
                this.evMgr.getMinimumViewingWindowLocationY(),
                this.evMgr.getMaximumViewingWindowLocationY());
        this.vertScroll = new JScrollBar(Adjustable.VERTICAL,
                this.evMgr.getMinimumViewingWindowLocationX(),
                this.evMgr.getViewingWindowSizeX(),
                this.evMgr.getMinimumViewingWindowLocationX(),
                this.evMgr.getMaximumViewingWindowLocationX());
        c.gridx = 0;
        c.gridy = 0;
        gridbag.setConstraints(this.secondaryPane, c);
        this.outputPane.add(this.secondaryPane);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(this.vertScroll, c);
        this.outputPane.add(this.vertScroll);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(this.horzScroll, c);
        this.outputPane.add(this.horzScroll);
        this.horzScroll.addAdjustmentListener(this.mhandler);
        this.vertScroll.addAdjustmentListener(this.mhandler);
        this.secondaryPane.addMouseListener(this.mhandler);
        this.secondaryPane.addMouseMotionListener(this.mhandler);
        this.outputFrame.addWindowListener(this.mhandler);
        this.updatePicker();
        this.borderPane.add(this.picker.getPicker(), BorderLayout.EAST);
    }

    public void undo() {
        final Application app = BrainMaze.getApplication();
        this.engine.undo();
        final MazeObject obj = this.engine.getObject();
        final int x = this.engine.getX();
        final int y = this.engine.getY();
        final int z = this.engine.getZ();
        final int w = this.engine.getW();
        final int e = this.engine.getE();
        this.elMgr.setEditorLocationX(x);
        this.elMgr.setEditorLocationY(y);
        this.elMgr.setCameFromZ(z);
        if (x != -1 && y != -1 && z != -1 && w != -1) {
            final MazeObject oldObj = app.getMazeManager().getMazeObject(x, y,
                    z, e);
            if (!obj.getName().equals(new StairsUp().getName())
                    && !obj.getName().equals(new StairsDown().getName())) {
                if (obj.getName().equals(new TwoWayTeleport().getName())) {
                    app.getMazeManager().getMaze().setCell(obj, x, y, z, e);
                    this.reverseCheckTwoWayTeleportPair(z);
                    this.checkStairPair(z);
                } else {
                    this.checkTwoWayTeleportPair(z);
                    app.getMazeManager().getMaze().setCell(obj, x, y, z, e);
                    this.checkStairPair(z);
                }
            } else {
                app.getMazeManager().getMaze().setCell(obj, x, y, z, e);
                this.reverseCheckStairPair(z);
            }
            this.updateRedoHistory(oldObj, x, y, z, w, e);
            this.checkMenus();
            this.redrawEditor();
        } else {
            BrainMaze.getApplication().showMessage("Nothing to undo");
        }
    }

    public void redo() {
        final Application app = BrainMaze.getApplication();
        this.engine.redo();
        final MazeObject obj = this.engine.getObject();
        final int x = this.engine.getX();
        final int y = this.engine.getY();
        final int z = this.engine.getZ();
        final int w = this.engine.getW();
        final int e = this.engine.getE();
        this.elMgr.setEditorLocationX(x);
        this.elMgr.setEditorLocationY(y);
        this.elMgr.setCameFromZ(z);
        if (x != -1 && y != -1 && z != -1 && w != -1) {
            final MazeObject oldObj = app.getMazeManager().getMazeObject(x, y,
                    z, e);
            if (!obj.getName().equals(new StairsUp().getName())
                    && !obj.getName().equals(new StairsDown().getName())) {
                if (obj.getName().equals(new TwoWayTeleport().getName())) {
                    app.getMazeManager().getMaze().setCell(obj, x, y, z, e);
                    this.reverseCheckTwoWayTeleportPair(z);
                    this.checkStairPair(z);
                } else {
                    this.checkTwoWayTeleportPair(z);
                    app.getMazeManager().getMaze().setCell(obj, x, y, z, e);
                    this.checkStairPair(z);
                }
            } else {
                app.getMazeManager().getMaze().setCell(obj, x, y, z, e);
                this.reverseCheckStairPair(z);
            }
            this.updateUndoHistory(oldObj, x, y, z, w, e);
            this.checkMenus();
            this.redrawEditor();
        } else {
            BrainMaze.getApplication().showMessage("Nothing to redo");
        }
    }

    public void clearHistory() {
        this.engine = new UndoRedoEngine();
        this.checkMenus();
    }

    private void updateUndoHistory(final MazeObject obj, final int x,
            final int y, final int z, final int w, final int e) {
        this.engine.updateUndoHistory(obj, x, y, z, w, e);
    }

    private void updateRedoHistory(final MazeObject obj, final int x,
            final int y, final int z, final int w, final int e) {
        this.engine.updateRedoHistory(obj, x, y, z, w, e);
    }

    public void updatePicker() {
        if (this.elMgr != null) {
            BufferedImageIcon[] newImages = null;
            String[] newNames = null;
            if (this.elMgr.getEditorLocationE() == MazeConstants.LAYER_GROUND) {
                newImages = this.groundEditorAppearances;
                newNames = this.groundNames;
            } else {
                newImages = this.objectEditorAppearances;
                newNames = this.objectNames;
            }
            if (this.picker != null) {
                this.picker.updatePicker(newImages, newNames);
            } else {
                this.picker = new PicturePicker(newImages, newNames, new Color(
                        223, 223, 223));
                this.picker.changePickerColor(new Color(223, 223, 223));
            }
            this.picker.setPickerDimensions(this.outputPane.getHeight());
        }
    }

    public void handleCloseWindow() {
        try {
            final Application app = BrainMaze.getApplication();
            boolean success = false;
            int status = JOptionPane.DEFAULT_OPTION;
            if (app.getMazeManager().getDirty()) {
                status = app.getMazeManager().showSaveDialog();
                if (status == JOptionPane.YES_OPTION) {
                    success = app.getMazeManager().saveMaze();
                    if (success) {
                        this.exitEditor();
                    }
                } else if (status == JOptionPane.NO_OPTION) {
                    app.getMazeManager().setDirty(false);
                    this.exitEditor();
                }
            } else {
                this.exitEditor();
            }
        } catch (final Exception ex) {
            BrainMaze.getErrorLogger().logError(ex);
        }
    }

    private class EventHandler implements AdjustmentListener, MouseListener,
            MouseMotionListener, WindowListener {
        EventHandler() {
            // Do nothing
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final MazeEditor me = MazeEditor.this;
                final Adjustable src = e.getAdjustable();
                final int dir = src.getOrientation();
                final int value = src.getValue();
                int relValue = 0;
                switch (dir) {
                case Adjustable.HORIZONTAL:
                    relValue = value - me.evMgr.getViewingWindowLocationY();
                    me.updateEditorPosition(0, relValue, 0, 0);
                    break;
                case Adjustable.VERTICAL:
                    relValue = value - me.evMgr.getViewingWindowLocationX();
                    me.updateEditorPosition(relValue, 0, 0, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                BrainMaze.getErrorLogger().logError(ex);
            }
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
                final MazeEditor me = MazeEditor.this;
                final int x = e.getX();
                final int y = e.getY();
                if (e.isAltDown()) {
                    if (!me.goToDestMode) {
                        me.editObjectProperties(x, y);
                    }
                } else if (e.isShiftDown()) {
                    me.probeObjectProperties(x, y);
                } else {
                    if (me.goToDestMode) {
                        me.goToDestination(x, y);
                    } else {
                        me.editObject(x, y, false);
                    }
                }
            } catch (final Exception ex) {
                BrainMaze.getErrorLogger().logError(ex);
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
            MazeEditor.this.handleCloseWindow();
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

        @Override
        public void mouseDragged(final MouseEvent e) {
            try {
                final MazeEditor me = MazeEditor.this;
                final int x = e.getX();
                final int y = e.getY();
                me.editObject(x, y, false);
            } catch (final Exception ex) {
                BrainMaze.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void mouseMoved(final MouseEvent e) {
            // Do nothing
        }
    }

    private class StartEventHandler implements AdjustmentListener,
            MouseListener {
        StartEventHandler() {
            // Do nothing
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final MazeEditor me = MazeEditor.this;
                final Adjustable src = e.getAdjustable();
                final int dir = src.getOrientation();
                final int value = src.getValue();
                int relValue = 0;
                switch (dir) {
                case Adjustable.HORIZONTAL:
                    relValue = value - me.evMgr.getViewingWindowLocationY();
                    me.updateEditorPosition(0, relValue, 0, 0);
                    break;
                case Adjustable.VERTICAL:
                    relValue = value - me.evMgr.getViewingWindowLocationX();
                    me.updateEditorPosition(relValue, 0, 0, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                BrainMaze.getErrorLogger().logError(ex);
            }
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
                final int x = e.getX();
                final int y = e.getY();
                MazeEditor.this.setPlayerLocation(x, y);
            } catch (final Exception ex) {
                BrainMaze.getErrorLogger().logError(ex);
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

    private class TeleportEventHandler implements AdjustmentListener,
            MouseListener {
        TeleportEventHandler() {
            // Do nothing
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final MazeEditor me = MazeEditor.this;
                final Adjustable src = e.getAdjustable();
                final int dir = src.getOrientation();
                final int value = src.getValue();
                int relValue = 0;
                switch (dir) {
                case Adjustable.HORIZONTAL:
                    relValue = value - me.evMgr.getViewingWindowLocationY();
                    me.updateEditorPosition(0, relValue, 0, 0);
                    break;
                case Adjustable.VERTICAL:
                    relValue = value - me.evMgr.getViewingWindowLocationX();
                    me.updateEditorPosition(relValue, 0, 0, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                BrainMaze.getErrorLogger().logError(ex);
            }
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
                final int x = e.getX();
                final int y = e.getY();
                MazeEditor.this.setTeleportDestination(x, y);
            } catch (final Exception ex) {
                BrainMaze.getErrorLogger().logError(ex);
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

    private class ConditionalTeleportEventHandler implements
            AdjustmentListener, MouseListener {
        ConditionalTeleportEventHandler() {
            // Do nothing
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final MazeEditor me = MazeEditor.this;
                final Adjustable src = e.getAdjustable();
                final int dir = src.getOrientation();
                final int value = src.getValue();
                int relValue = 0;
                switch (dir) {
                case Adjustable.HORIZONTAL:
                    relValue = value - me.evMgr.getViewingWindowLocationY();
                    me.updateEditorPosition(0, relValue, 0, 0);
                    break;
                case Adjustable.VERTICAL:
                    relValue = value - me.evMgr.getViewingWindowLocationX();
                    me.updateEditorPosition(relValue, 0, 0, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                BrainMaze.getErrorLogger().logError(ex);
            }
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
                final int x = e.getX();
                final int y = e.getY();
                MazeEditor.this.setConditionalTeleportDestination(x, y);
            } catch (final Exception ex) {
                BrainMaze.getErrorLogger().logError(ex);
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
