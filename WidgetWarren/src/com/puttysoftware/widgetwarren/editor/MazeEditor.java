/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.editor;

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

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.picturepicker.PicturePicker;
import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.GameManager;
import com.puttysoftware.widgetwarren.generic.GenericConditionalTeleport;
import com.puttysoftware.widgetwarren.generic.GenericTeleport;
import com.puttysoftware.widgetwarren.generic.GenericTeleportTo;
import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.generic.MazeObjectList;
import com.puttysoftware.widgetwarren.maze.Maze;
import com.puttysoftware.widgetwarren.maze.MazeConstants;
import com.puttysoftware.widgetwarren.maze.MazeManager;
import com.puttysoftware.widgetwarren.objects.ChainTeleport;
import com.puttysoftware.widgetwarren.objects.Destination;
import com.puttysoftware.widgetwarren.objects.Empty;
import com.puttysoftware.widgetwarren.objects.EmptyVoid;
import com.puttysoftware.widgetwarren.objects.InvisibleChainTeleport;
import com.puttysoftware.widgetwarren.objects.InvisibleOneShotChainTeleport;
import com.puttysoftware.widgetwarren.objects.InvisibleOneShotTeleport;
import com.puttysoftware.widgetwarren.objects.InvisibleTeleport;
import com.puttysoftware.widgetwarren.objects.MetalButton;
import com.puttysoftware.widgetwarren.objects.MovingFinish;
import com.puttysoftware.widgetwarren.objects.OneShotChainTeleport;
import com.puttysoftware.widgetwarren.objects.OneShotTeleport;
import com.puttysoftware.widgetwarren.objects.Player;
import com.puttysoftware.widgetwarren.objects.RandomInvisibleOneShotTeleport;
import com.puttysoftware.widgetwarren.objects.RandomInvisibleTeleport;
import com.puttysoftware.widgetwarren.objects.RandomOneShotTeleport;
import com.puttysoftware.widgetwarren.objects.RandomTeleport;
import com.puttysoftware.widgetwarren.objects.StairsDown;
import com.puttysoftware.widgetwarren.objects.StairsUp;
import com.puttysoftware.widgetwarren.objects.Teleport;
import com.puttysoftware.widgetwarren.objects.TreasureChest;
import com.puttysoftware.widgetwarren.objects.TwoWayTeleport;
import com.puttysoftware.widgetwarren.prefs.PreferencesManager;
import com.puttysoftware.widgetwarren.resourcemanagers.GUIConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.ImageConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.ImageModifier;
import com.puttysoftware.widgetwarren.resourcemanagers.LogoManager;
import com.puttysoftware.widgetwarren.resourcemanagers.ObjectImageManager;

public class MazeEditor {
    // Declarations
    private JFrame outputFrame;
    private final JFrame treasureFrame;
    private Container outputPane, secondaryPane, borderPane;
    private final Container treasurePane;
    private JLabel messageLabel;
    private MazeObject savedMazeObject;
    private GridBagLayout gridbag;
    private GridBagConstraints c;
    private JScrollBar vertScroll, horzScroll;
    private final EventHandler mhandler;
    private final StartEventHandler shandler;
    private final TeleportEventHandler thandler;
    private final TreasureEventHandler rhandler;
    private final MetalButtonEventHandler mbhandler;
    private final ConditionalTeleportEventHandler cthandler;
    private final LevelPreferencesManager lPrefs;
    private final MazePreferencesManager mPrefs;
    private PicturePicker picker;
    private final PicturePicker treasurePicker;
    private final MazeObjectList objectList;
    private final String[] groundNames;
    private final String[] objectNames;
    private final MazeObject[] groundObjects;
    private final MazeObject[] objectObjects;
    private final BufferedImageIcon[] groundEditorAppearances;
    private final BufferedImageIcon[] objectEditorAppearances;
    private final String[] containableNames;
    private final MazeObject[] containableObjects;
    private final BufferedImageIcon[] containableEditorAppearances;
    private int TELEPORT_TYPE;
    private GenericConditionalTeleport instanceBeingEdited;
    private int conditionalEditFlag;
    private int currentObjectIndex;
    private UndoRedoEngine engine;
    private EditorLocationManager elMgr;
    EditorViewingWindowManager evMgr;
    private JLabel[][] drawGrid;
    private boolean mazeChanged;
    boolean goToDestMode;
    private boolean viewMode;
    private static final EmptyVoid VOID = new EmptyVoid();
    private static final Destination DEST = new Destination();
    private static final int CEF_DEST1 = 1;
    private static final int CEF_DEST2 = 2;
    private static final int CEF_CONDITION = 3;
    private static final int CEF_TRIGGER_TYPE = 4;
    public static final int TELEPORT_TYPE_GENERIC = 0;
    public static final int TELEPORT_TYPE_INVISIBLE_GENERIC = 1;
    public static final int TELEPORT_TYPE_RANDOM = 2;
    public static final int TELEPORT_TYPE_RANDOM_INVISIBLE = 3;
    public static final int TELEPORT_TYPE_ONESHOT = 4;
    public static final int TELEPORT_TYPE_INVISIBLE_ONESHOT = 5;
    public static final int TELEPORT_TYPE_RANDOM_ONESHOT = 6;
    public static final int TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT = 7;
    public static final int TELEPORT_TYPE_TWOWAY = 8;
    public static final int TELEPORT_TYPE_MOVING_FINISH = 9;
    public static final int TELEPORT_TYPE_FIRST_MOVING_FINISH = 10;
    public static final int TELEPORT_TYPE_CHAIN = 11;
    public static final int TELEPORT_TYPE_INVISIBLE_CHAIN = 12;
    public static final int TELEPORT_TYPE_ONESHOT_CHAIN = 15;
    public static final int TELEPORT_TYPE_INVISIBLE_ONESHOT_CHAIN = 16;
    public static final int STAIRS_UP = 0;
    public static final int STAIRS_DOWN = 1;

    public MazeEditor() {
        this.savedMazeObject = new Empty();
        this.lPrefs = new LevelPreferencesManager();
        this.mPrefs = new MazePreferencesManager();
        this.mhandler = new EventHandler();
        this.mbhandler = new MetalButtonEventHandler();
        this.shandler = new StartEventHandler();
        this.thandler = new TeleportEventHandler();
        this.cthandler = new ConditionalTeleportEventHandler();
        this.engine = new UndoRedoEngine();
        this.objectList = WidgetWarren.getApplication().getObjects();
        this.rhandler = new TreasureEventHandler();
        this.groundNames = this.objectList.getAllGroundLayerNames();
        this.objectNames = this.objectList.getAllObjectLayerNames();
        this.groundObjects = this.objectList.getAllGroundLayerObjects();
        this.objectObjects = this.objectList.getAllObjectLayerObjects();
        this.groundEditorAppearances = this.objectList
                .getAllGroundLayerEditorAppearances();
        this.objectEditorAppearances = this.objectList
                .getAllObjectLayerEditorAppearances();
        // Set up treasure picker
        this.containableNames = this.objectList.getAllContainableNames();
        this.containableObjects = this.objectList.getAllContainableObjects();
        this.containableEditorAppearances = this.objectList
                .getAllContainableObjectEditorAppearances();
        this.treasureFrame = new JFrame("Treasure Chest Contents");
        final Image iconlogo = LogoManager.getLogo();
        this.treasureFrame.setIconImage(iconlogo);
        this.treasureFrame
                .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.treasurePicker = new PicturePicker(
                this.containableEditorAppearances, this.containableNames,
                new Color(223, 223, 223));
        this.treasurePicker.updatePickerLayout(GUIConstants.MAX_WINDOW_SIZE);
        this.treasurePane = this.treasurePicker.getPicker();
        this.treasureFrame.setContentPane(this.treasurePane);
        this.treasureFrame.addWindowListener(this.rhandler);
        this.treasureFrame.pack();
        this.mazeChanged = true;
        this.goToDestMode = false;
        this.instanceBeingEdited = null;
        this.viewMode = false;
    }

    public void toggleViewMode() {
        this.viewMode = !this.viewMode;
        this.updatePicker();
        this.lPrefs.viewModeChanged(this.viewMode);
        this.mPrefs.viewModeChanged(this.viewMode);
    }

    public void mazeChanged() {
        this.mazeChanged = true;
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
            WidgetWarren.getApplication().getMazeManager().getMaze()
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
            WidgetWarren.getApplication().getMazeManager().getMaze()
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
        WidgetWarren.getApplication().getMazeManager().getMaze().switchLevel(w);
        this.fixLimits();
        this.setUpGUI();
        this.checkMenus();
        this.redrawEditor();
    }

    private void checkMenus() {
        final Application app = WidgetWarren.getApplication();
        if (app.getMode() == Application.STATUS_EDITOR) {
            final Maze m = app.getMazeManager().getMaze();
            if (m.getLevels() == Maze.getMinLevels() || this.viewMode) {
                app.getMenuManager().disableRemoveLevel();
            } else {
                app.getMenuManager().enableRemoveLevel();
            }
            if (m.getLevels() == Maze.getMaxLevels() || this.viewMode) {
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
                if (this.elMgr.getEditorLocationE() != MazeConstants.LAYER_GROUND
                        && !this.viewMode) {
                    app.getMenuManager().enableSetStartPoint();
                } else {
                    app.getMenuManager().disableSetStartPoint();
                }
            } else {
                app.getMenuManager().disableSetStartPoint();
            }
            if (!this.engine.tryUndo() || this.viewMode) {
                app.getMenuManager().disableUndo();
            } else {
                app.getMenuManager().enableUndo();
            }
            if (!this.engine.tryRedo() || this.viewMode) {
                app.getMenuManager().disableRedo();
            } else {
                app.getMenuManager().enableRedo();
            }
            if (this.engine.tryBoth() || this.viewMode) {
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
        if (this.elMgr.getEditorLocationE() == MazeConstants.LAYER_GROUND) {
            this.redrawGround();
        } else {
            this.redrawGroundAndObjects();
        }
    }

    private void redrawGround() {
        // Draw the maze in edit mode
        final Application app = WidgetWarren.getApplication();
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
                    this.drawGrid[xFix][yFix].setIcon(ObjectImageManager
                            .getObjectImage(obj1, false));
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.drawGrid[xFix][yFix].setIcon(ObjectImageManager
                            .getObjectImage(VOID, false));
                } catch (final NullPointerException np) {
                    this.drawGrid[xFix][yFix].setIcon(ObjectImageManager
                            .getObjectImage(VOID, false));
                }
            }
        }
        this.outputFrame.pack();
        this.outputFrame.setTitle("Editor (Object Layer) - Floor "
                + (this.elMgr.getEditorLocationZ() + 1) + " Level " + (w + 1));
        this.showOutput();
    }

    private void redrawGroundAndObjects() {
        // Draw the maze in edit mode
        final Application app = WidgetWarren.getApplication();
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
                    final BufferedImageIcon icon1 = ObjectImageManager
                            .getObjectImage(obj1, false);
                    final MazeObject obj2 = app
                            .getMazeManager()
                            .getMaze()
                            .getCell(y, x, this.elMgr.getEditorLocationZ(),
                                    MazeConstants.LAYER_OBJECT);
                    final BufferedImageIcon icon2 = ObjectImageManager
                            .getObjectImage(obj2, false);
                    this.drawGrid[xFix][yFix].setIcon(ImageModifier
                            .getCompositeImage(icon1, icon2));
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.drawGrid[xFix][yFix].setIcon(ObjectImageManager
                            .getObjectImage(VOID, false));
                } catch (final NullPointerException np) {
                    this.drawGrid[xFix][yFix].setIcon(ObjectImageManager
                            .getObjectImage(VOID, false));
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
        final Application app = WidgetWarren.getApplication();
        int xFix, yFix;
        xFix = y - this.evMgr.getViewingWindowLocationX();
        yFix = x - this.evMgr.getViewingWindowLocationY();
        try {
            MazeObject obj1, obj2;
            obj1 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(y, x, this.elMgr.getEditorLocationZ(),
                            MazeConstants.LAYER_GROUND);
            final BufferedImageIcon icon1 = ObjectImageManager.getObjectImage(
                    obj1, false);
            obj2 = app
                    .getMazeManager()
                    .getMaze()
                    .getCell(y, x, this.elMgr.getEditorLocationZ(),
                            MazeConstants.LAYER_OBJECT);
            final BufferedImageIcon icon2 = ObjectImageManager.getObjectImage(
                    obj2, false);
            final BufferedImageIcon icon3 = ObjectImageManager.getObjectImage(
                    obj3, false);
            this.drawGrid[xFix][yFix].setIcon(ImageModifier.getCompositeImage(
                    icon1, icon2, icon3));
            this.drawGrid[xFix][yFix].repaint();
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
        this.outputFrame.pack();
    }

    public void editObject(final int x, final int y) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            this.currentObjectIndex = this.picker.getPicked();
            final int xOffset = this.vertScroll.getValue()
                    - this.vertScroll.getMinimum();
            final int yOffset = this.horzScroll.getValue()
                    - this.horzScroll.getMinimum();
            final int gridX = x / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            final int gridY = y / ImageConstants.getImageSize()
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
            MazeObject[] choices = null;
            if (this.elMgr.getEditorLocationE() == MazeConstants.LAYER_GROUND) {
                choices = this.groundObjects;
            } else {
                choices = this.objectObjects;
            }
            final MazeObject mo = choices[this.currentObjectIndex];
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
    }

    public void probeObjectProperties(final int x, final int y) {
        final Application app = WidgetWarren.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int gridX = x / ImageConstants.getImageSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int gridY = y / ImageConstants.getImageSize()
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
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            final int xOffset = this.vertScroll.getValue()
                    - this.vertScroll.getMinimum();
            final int yOffset = this.horzScroll.getValue()
                    - this.horzScroll.getMinimum();
            final int gridX = x / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            final int gridY = y / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationY() + xOffset
                    - yOffset;
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
                        WidgetWarren.getApplication().showMessage(
                                "This object has no properties");
                    } else {
                        this.checkTwoWayTeleportPair(this.elMgr
                                .getEditorLocationZ());
                        this.updateUndoHistory(this.savedMazeObject, gridX,
                                gridY, this.elMgr.getEditorLocationZ(),
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
    }

    public void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
    }

    private void checkStairPair(final int z) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
    }

    private void reverseCheckStairPair(final int z) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
    }

    public void pairStairs(final int type) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
    }

    private void pairStairs(final int type, final int z) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
    }

    private void unpairStairs(final int type, final int z) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            switch (type) {
            case STAIRS_UP:
                try {
                    app.getMazeManager()
                            .getMaze()
                            .setCell(new Empty(),
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
                            .setCell(new Empty(),
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
    }

    private void checkTwoWayTeleportPair(final int z) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
                final MazeObject mo2 = app
                        .getMazeManager()
                        .getMaze()
                        .getCell(destX, destY, destZ,
                                MazeConstants.LAYER_OBJECT);
                name2 = mo2.getName();
                if (name2.equals("Two-Way Teleport")) {
                    this.unpairTwoWayTeleport(destX, destY, destZ);
                }
            }
        }
    }

    private void reverseCheckTwoWayTeleportPair(final int z) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
                final MazeObject mo2 = app
                        .getMazeManager()
                        .getMaze()
                        .getCell(destX, destY, destZ,
                                MazeConstants.LAYER_OBJECT);
                name2 = mo2.getName();
                if (!name2.equals("Two-Way Teleport")) {
                    this.pairTwoWayTeleport(destX, destY, destZ);
                }
            }
        }
    }

    public void pairTwoWayTeleport(final int destX, final int destY,
            final int destZ) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            app.getMazeManager()
                    .getMaze()
                    .setCell(
                            new TwoWayTeleport(this.elMgr.getEditorLocationX(),
                                    this.elMgr.getEditorLocationY(),
                                    this.elMgr.getCameFromZ()), destX, destY,
                            destZ, MazeConstants.LAYER_OBJECT);
        }
    }

    private void unpairTwoWayTeleport(final int destX, final int destY,
            final int destZ) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            app.getMazeManager()
                    .getMaze()
                    .setCell(new Empty(), destX, destY, destZ,
                            MazeConstants.LAYER_OBJECT);
        }
    }

    public void editConditionalTeleportDestination(
            final GenericConditionalTeleport instance) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            final String[] choices = new String[] { "Edit Destination 1",
                    "Edit Destination 2", "Edit Condition Trigger",
                    "Edit Trigger Type" };
            final String choice = CommonDialogs.showInputDialog("Edit What?",
                    "Editor", choices, choices[0]);
            if (choice != null) {
                this.instanceBeingEdited = instance;
                this.conditionalEditFlag = 0;
                for (int x = 0; x < choices.length; x++) {
                    if (choices[x].equals(choice)) {
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
                            for (int x = 0; x < choices.length; x++) {
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
                        this.elMgr
                                .setCameFromZ(this.elMgr.getEditorLocationZ());
                        app.getMenuManager().disableDownOneLevel();
                        app.getMenuManager().disableUpOneLevel();
                    }
                } else {
                    this.instanceBeingEdited = null;
                }
            }
        }
    }

    public MazeObject editTeleportDestination(final int type) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            String input1 = null, input2 = null;
            this.TELEPORT_TYPE = type;
            int destX = 0, destY = 0;
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
                WidgetWarren.getApplication().showMessage(
                        "Click to set teleport destination");
                break;
            case TELEPORT_TYPE_MOVING_FINISH:
                WidgetWarren.getApplication().showMessage(
                        "Click to set next moving finish");
                break;
            case TELEPORT_TYPE_FIRST_MOVING_FINISH:
                WidgetWarren.getApplication().showMessage(
                        "Click to set first moving finish");
                break;
            case TELEPORT_TYPE_RANDOM:
            case TELEPORT_TYPE_RANDOM_INVISIBLE:
            case TELEPORT_TYPE_RANDOM_ONESHOT:
            case TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT:
                input1 = CommonDialogs.showTextInputDialog("Random row range:",
                        "Editor");
                break;
            default:
                break;
            }
            if (input1 != null) {
                switch (type) {
                case TELEPORT_TYPE_RANDOM:
                case TELEPORT_TYPE_RANDOM_INVISIBLE:
                case TELEPORT_TYPE_RANDOM_ONESHOT:
                case TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT:
                    input2 = CommonDialogs.showTextInputDialog(
                            "Random column range:", "Editor");
                    break;
                default:
                    break;
                }
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
            } else {
                switch (type) {
                case TELEPORT_TYPE_GENERIC:
                case TELEPORT_TYPE_INVISIBLE_GENERIC:
                case TELEPORT_TYPE_ONESHOT:
                case TELEPORT_TYPE_INVISIBLE_ONESHOT:
                case TELEPORT_TYPE_TWOWAY:
                case TELEPORT_TYPE_MOVING_FINISH:
                case TELEPORT_TYPE_FIRST_MOVING_FINISH:
                case TELEPORT_TYPE_CHAIN:
                case TELEPORT_TYPE_INVISIBLE_CHAIN:
                case TELEPORT_TYPE_ONESHOT_CHAIN:
                case TELEPORT_TYPE_INVISIBLE_ONESHOT_CHAIN:
                    this.horzScroll.removeAdjustmentListener(this.mhandler);
                    this.vertScroll.removeAdjustmentListener(this.mhandler);
                    this.secondaryPane.removeMouseListener(this.mhandler);
                    this.horzScroll.addAdjustmentListener(this.thandler);
                    this.vertScroll.addAdjustmentListener(this.thandler);
                    this.secondaryPane.addMouseListener(this.thandler);
                    this.elMgr.setCameFromZ(this.elMgr.getEditorLocationZ());
                    app.getMenuManager().disableDownOneLevel();
                    app.getMenuManager().disableUpOneLevel();
                    break;
                default:
                    break;
                }
            }
        }
        return null;
    }

    public MazeObject editMetalButtonTarget() {
        if (!this.viewMode) {
            WidgetWarren.getApplication().showMessage(
                    "Click to set metal button target");
            final Application app = WidgetWarren.getApplication();
            this.horzScroll.removeAdjustmentListener(this.mhandler);
            this.vertScroll.removeAdjustmentListener(this.mhandler);
            this.secondaryPane.removeMouseListener(this.mhandler);
            this.horzScroll.addAdjustmentListener(this.mbhandler);
            this.vertScroll.addAdjustmentListener(this.mbhandler);
            this.secondaryPane.addMouseListener(this.mbhandler);
            this.elMgr.setCameFromZ(this.elMgr.getEditorLocationZ());
            app.getMenuManager().disableDownOneLevel();
            app.getMenuManager().disableUpOneLevel();
        }
        return null;
    }

    public MazeObject editTreasureChestContents() {
        if (!this.viewMode) {
            WidgetWarren.getApplication().showMessage(
                    "Pick treasure chest contents");
            this.setDefaultContents();
            this.disableOutput();
            this.treasureFrame.setVisible(true);
        }
        return null;
    }

    private void setDefaultContents() {
        if (!this.viewMode) {
            TreasureChest tc = null;
            MazeObject contents = null;
            int contentsIndex = 0;
            final Application app = WidgetWarren.getApplication();
            try {
                tc = (TreasureChest) app.getMazeManager().getMazeObject(
                        this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(),
                        this.elMgr.getCameFromZ(), MazeConstants.LAYER_OBJECT);
                contents = tc.getInsideObject();
                for (int x = 0; x < this.containableObjects.length; x++) {
                    if (contents.getName().equals(
                            this.containableObjects[x].getName())) {
                        contentsIndex = x;
                        break;
                    }
                }
            } catch (final ClassCastException cce) {
                // Do nothing
            } catch (final NullPointerException npe) {
                // Do nothing
            }
            this.treasurePicker.selectLastPickedChoice(contentsIndex);
        }
    }

    public void editTeleportToDestination(final GenericTeleportTo ft) {
        if (!this.viewMode) {
            String input1 = null;
            int destW = 0;
            input1 = CommonDialogs.showTextInputDialog("Destination Level:",
                    "Editor");
            if (input1 != null) {
                try {
                    destW = Integer.parseInt(input1) - 1;
                    ft.setDestinationLevel(destW);
                } catch (final NumberFormatException nf) {
                    CommonDialogs
                            .showDialog("Destination level must be an integer greater than 0.");
                }
            }
        }
    }

    public void setTeleportDestination(final int x, final int y) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            final int xOffset = this.vertScroll.getValue()
                    - this.vertScroll.getMinimum();
            final int yOffset = this.horzScroll.getValue()
                    - this.horzScroll.getMinimum();
            final int destX = x / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            final int destY = y / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationY() + xOffset
                    - yOffset;
            final int destZ = this.elMgr.getEditorLocationZ();
            try {
                app.getMazeManager()
                        .getMaze()
                        .getCell(destX, destY, destZ,
                                MazeConstants.LAYER_OBJECT);
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
                        .setCell(
                                new InvisibleOneShotTeleport(destX, destY,
                                        destZ),
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
            case TELEPORT_TYPE_MOVING_FINISH:
                app.getMazeManager()
                        .getMaze()
                        .setCell(new MovingFinish(destX, destY, destZ),
                                this.elMgr.getEditorLocationX(),
                                this.elMgr.getEditorLocationY(),
                                this.elMgr.getCameFromZ(),
                                MazeConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_FIRST_MOVING_FINISH:
                final Maze m = app.getMazeManager().getMaze();
                m.setFirstMovingFinishX(destX);
                m.setFirstMovingFinishY(destY);
                m.setFirstMovingFinishZ(destZ);
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
                        .setCell(
                                new InvisibleChainTeleport(destX, destY, destZ),
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
                                        destZ),
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
            if (this.TELEPORT_TYPE == TELEPORT_TYPE_MOVING_FINISH) {
                WidgetWarren.getApplication().showMessage(
                        "Next moving finish set.");
            } else if (this.TELEPORT_TYPE == TELEPORT_TYPE_FIRST_MOVING_FINISH) {
                WidgetWarren.getApplication().showMessage(
                        "First moving finish set.");
            } else {
                WidgetWarren.getApplication().showMessage("Destination set.");
            }
            app.getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    void setConditionalTeleportDestination(final int x, final int y) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            final int xOffset = this.vertScroll.getValue()
                    - this.vertScroll.getMinimum();
            final int yOffset = this.horzScroll.getValue()
                    - this.horzScroll.getMinimum();
            final int destX = x / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            final int destY = y / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationY() + xOffset
                    - yOffset;
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
            WidgetWarren.getApplication().showMessage("Destination set.");
            app.getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void setMetalButtonTarget(final int x, final int y) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            final int xOffset = this.vertScroll.getValue()
                    - this.vertScroll.getMinimum();
            final int yOffset = this.horzScroll.getValue()
                    - this.horzScroll.getMinimum();
            final int destX = x / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            final int destY = y / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationY() + xOffset
                    - yOffset;
            final int destZ = this.elMgr.getEditorLocationZ();
            try {
                app.getMazeManager()
                        .getMaze()
                        .getCell(destX, destY, destZ,
                                MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.horzScroll.removeAdjustmentListener(this.mbhandler);
                this.vertScroll.removeAdjustmentListener(this.mbhandler);
                this.secondaryPane.removeMouseListener(this.mbhandler);
                this.horzScroll.addAdjustmentListener(this.mhandler);
                this.vertScroll.addAdjustmentListener(this.mhandler);
                this.secondaryPane.addMouseListener(this.mhandler);
                return;
            }
            app.getMazeManager()
                    .getMaze()
                    .setCell(new MetalButton(destX, destY, destZ),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            this.horzScroll.removeAdjustmentListener(this.mbhandler);
            this.vertScroll.removeAdjustmentListener(this.mbhandler);
            this.secondaryPane.removeMouseListener(this.mbhandler);
            this.horzScroll.addAdjustmentListener(this.mhandler);
            this.vertScroll.addAdjustmentListener(this.mhandler);
            this.secondaryPane.addMouseListener(this.mhandler);
            this.checkMenus();
            WidgetWarren.getApplication().showMessage("Target set.");
            app.getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void setTreasureChestContents() {
        if (!this.viewMode) {
            this.enableOutput();
            final Application app = WidgetWarren.getApplication();
            final MazeObject contents = this.containableObjects[this.treasurePicker
                    .getPicked()];
            app.getMazeManager()
                    .getMaze()
                    .setCell(new TreasureChest(contents),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getCameFromZ(),
                            MazeConstants.LAYER_OBJECT);
            this.checkMenus();
            WidgetWarren.getApplication().showMessage("Contents set.");
            app.getMazeManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void editPlayerLocation() {
        if (!this.viewMode) {
            // Swap event handlers
            this.horzScroll.removeAdjustmentListener(this.mhandler);
            this.vertScroll.removeAdjustmentListener(this.mhandler);
            this.secondaryPane.removeMouseListener(this.mhandler);
            this.horzScroll.addAdjustmentListener(this.shandler);
            this.vertScroll.addAdjustmentListener(this.shandler);
            this.secondaryPane.addMouseListener(this.shandler);
            WidgetWarren.getApplication().showMessage(
                    "Click to set start point");
        }
    }

    public void setPlayerLocation(final int x, final int y, final int z) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            final int oldX = app.getMazeManager().getMaze().getStartColumn();
            final int oldY = app.getMazeManager().getMaze().getStartRow();
            final int oldZ = app.getMazeManager().getMaze().getStartFloor();
            // Erase old player
            try {
                app.getMazeManager()
                        .getMaze()
                        .setCell(new Empty(), oldX, oldY, oldZ,
                                MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Ignore
            }
            // Set new player
            app.getMazeManager().getMaze().setStartRow(y);
            app.getMazeManager().getMaze().setStartColumn(x);
            app.getMazeManager().getMaze().setStartFloor(z);
            app.getMazeManager().getMaze()
                    .setCell(new Player(), x, y, z, MazeConstants.LAYER_OBJECT);
        }
    }

    public void setPlayerLocation() {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            final int oldX = app.getMazeManager().getMaze().getStartColumn();
            final int oldY = app.getMazeManager().getMaze().getStartRow();
            final int oldZ = app.getMazeManager().getMaze().getStartFloor();
            // Erase old player
            try {
                app.getMazeManager()
                        .getMaze()
                        .setCell(new Empty(), oldX, oldY, oldZ,
                                MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Ignore
            }
            // Set new player
            app.getMazeManager().getMaze()
                    .setStartRow(this.elMgr.getEditorLocationY());
            app.getMazeManager().getMaze()
                    .setStartColumn(this.elMgr.getEditorLocationX());
            app.getMazeManager().getMaze()
                    .setStartFloor(this.elMgr.getEditorLocationZ());
            app.getMazeManager()
                    .getMaze()
                    .setCell(new Player(), this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getEditorLocationZ(),
                            MazeConstants.LAYER_OBJECT);
        }
    }

    void setPlayerLocation(final int x, final int y) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            final int xOffset = this.vertScroll.getValue()
                    - this.vertScroll.getMinimum();
            final int yOffset = this.horzScroll.getValue()
                    - this.horzScroll.getMinimum();
            final int destX = x / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            final int destY = y / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationY() + xOffset
                    - yOffset;
            final int oldX = app.getMazeManager().getMaze().getStartColumn();
            final int oldY = app.getMazeManager().getMaze().getStartRow();
            final int oldZ = app.getMazeManager().getMaze().getStartFloor();
            // Erase old player
            try {
                app.getMazeManager()
                        .getMaze()
                        .setCell(new Empty(), oldX, oldY, oldZ,
                                MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Ignore
            }
            // Set new player
            try {
                app.getMazeManager().getMaze().saveStart();
                app.getMazeManager().getMaze().setStartRow(destY);
                app.getMazeManager().getMaze().setStartColumn(destX);
                app.getMazeManager().getMaze()
                        .setStartFloor(this.elMgr.getEditorLocationZ());
                app.getMazeManager()
                        .getMaze()
                        .setCell(new Player(), destX, destY,
                                this.elMgr.getEditorLocationZ(),
                                MazeConstants.LAYER_OBJECT);
                WidgetWarren.getApplication().showMessage("Start point set.");
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                app.getMazeManager().getMaze().restoreStart();
                try {
                    app.getMazeManager()
                            .getMaze()
                            .setCell(new Player(), oldX, oldY, oldZ,
                                    MazeConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException aioob2) {
                    // Ignore
                }
                WidgetWarren.getApplication()
                        .showMessage("Aim within the maze");
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
    }

    public void editMaze() {
        final Application app = WidgetWarren.getApplication();
        if (app.getMazeManager().getLoaded()) {
            app.getGUIManager().hideGUI();
            app.setInEditor(true);
            // Reset game state
            app.getGameManager().resetGameState();
            // Create the managers
            if (this.mazeChanged) {
                this.elMgr = new EditorLocationManager();
                this.evMgr = new EditorViewingWindowManager();
                this.elMgr.setLimitsFromMaze(app.getMazeManager().getMaze());
                this.evMgr.halfOffsetMaximumViewingWindowLocationsFromMaze(app
                        .getMazeManager().getMaze());
                this.mazeChanged = false;
            }
            this.setUpGUI();
            this.clearHistory();
            // Make sure message area is attached to border pane
            this.borderPane.removeAll();
            this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
            this.borderPane.add(this.outputPane, BorderLayout.CENTER);
            this.borderPane.add(this.picker.getPicker(), BorderLayout.EAST);
            this.redrawEditor();
            this.checkMenus();
        } else {
            CommonDialogs.showDialog("No Maze Opened");
        }
    }

    public boolean newMaze() {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
                app.getGameManager().getPlayerManager().resetPlayerLocation();
                app.getMazeManager().setMaze(new Maze());
                success = this.addLevelInternal(true);
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
            }
            return success;
        } else {
            CommonDialogs.showErrorDialog(
                    "View mode is enabled. No edits can be performed.",
                    "Editor");
            return false;
        }
    }

    public void fixLimits() {
        // Fix limits
        final Application app = WidgetWarren.getApplication();
        if (app.getMazeManager().getMaze() != null && this.elMgr != null
                && this.evMgr != null) {
            this.elMgr.setLimitsFromMaze(app.getMazeManager().getMaze());
            this.evMgr.halfOffsetMaximumViewingWindowLocationsFromMaze(app
                    .getMazeManager().getMaze());
        }
    }

    private boolean confirmNonUndoable(final String task) {
        if (!this.viewMode) {
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
        }
        return false;
    }

    public void fillLevel() {
        if (!this.viewMode) {
            if (this.confirmNonUndoable("overwrite the active level with default data")) {
                WidgetWarren.getApplication().getMazeManager().getMaze()
                        .fillLevelDefault();
                WidgetWarren.getApplication().showMessage("Level filled.");
                WidgetWarren.getApplication().getMazeManager().setDirty(true);
                this.redrawEditor();
            }
        }
    }

    public void fillFloor() {
        if (!this.viewMode) {
            if (this.confirmNonUndoable("overwrite the active floor within the active level with default data")) {
                WidgetWarren.getApplication().getMazeManager().getMaze()
                        .fillFloorDefault(this.elMgr.getEditorLocationZ());
                WidgetWarren.getApplication().showMessage("Floor filled.");
                WidgetWarren.getApplication().getMazeManager().setDirty(true);
                this.redrawEditor();
            }
        }
    }

    public void fillLevelRandomly() {
        if (!this.viewMode) {
            if (this.confirmNonUndoable("overwrite the active level with random data")) {
                if (WidgetWarren.getApplication().getMenuManager()
                        .useFillRuleSets()) {
                    WidgetWarren.getApplication().getMazeManager().getMaze()
                            .fillLevelRandomlyCustom();
                } else {
                    WidgetWarren.getApplication().getMazeManager().getMaze()
                            .fillLevelRandomly();
                }
                WidgetWarren.getApplication().showMessage(
                        "Level randomly filled.");
                WidgetWarren.getApplication().getMazeManager().setDirty(true);
                this.redrawEditor();
            }
        }
    }

    public void fillFloorRandomly() {
        if (!this.viewMode) {
            if (this.confirmNonUndoable("overwrite the active floor within the active level with random data")) {
                if (WidgetWarren.getApplication().getMenuManager()
                        .useFillRuleSets()) {
                    WidgetWarren
                            .getApplication()
                            .getMazeManager()
                            .getMaze()
                            .fillFloorRandomlyCustom(
                                    this.elMgr.getEditorLocationZ());
                } else {
                    WidgetWarren.getApplication().getMazeManager().getMaze()
                            .fillFloorRandomly(this.elMgr.getEditorLocationZ());
                }
                WidgetWarren.getApplication().showMessage(
                        "Floor randomly filled.");
                WidgetWarren.getApplication().getMazeManager().setDirty(true);
                this.redrawEditor();
            }
        }
    }

    public boolean addLevel() {
        return this.addLevelInternal(false);
    }

    private boolean addLevelInternal(final boolean flag) {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
            int levelSizeX, levelSizeY, levelSizeZ;
            final int maxR = Maze.getMaxRows();
            final int minR = Maze.getMinRows();
            final int maxC = Maze.getMaxColumns();
            final int minC = Maze.getMinColumns();
            final int maxF = Maze.getMaxFloors();
            final int minF = Maze.getMinFloors();
            String msg = null;
            if (flag) {
                msg = "New Maze";
            } else {
                msg = "New Level";
            }
            boolean success = true;
            String input1, input2, input3;
            input1 = CommonDialogs.showTextInputDialog("Number of rows ("
                    + minR + "-" + maxR + ")?", msg);
            if (input1 != null) {
                input2 = CommonDialogs.showTextInputDialog(
                        "Number of columns (" + minC + "-" + maxC + ")?", msg);
                if (input2 != null) {
                    input3 = CommonDialogs.showTextInputDialog(
                            "Number of floors (" + minF + "-" + maxF + ")?",
                            msg);
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
                                        "Columns must be at least " + minC
                                                + ".");
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
                            final int saveLevel = app.getMazeManager()
                                    .getMaze().getActiveLevelNumber();
                            success = app
                                    .getMazeManager()
                                    .getMaze()
                                    .addLevel(levelSizeX, levelSizeY,
                                            levelSizeZ);
                            if (success) {
                                this.fixLimits();
                                if (!flag) {
                                    this.evMgr
                                            .setViewingWindowLocationX(0 - (this.evMgr
                                                    .getViewingWindowSizeX() - 1) / 2);
                                    this.evMgr
                                            .setViewingWindowLocationY(0 - (this.evMgr
                                                    .getViewingWindowSizeY() - 1) / 2);
                                }
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
        } else {
            return false;
        }
    }

    public boolean resizeLevel() {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
                                        "Columns must be at least " + minC
                                                + ".");
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
                            this.evMgr
                                    .setViewingWindowLocationX(0 - (this.evMgr
                                            .getViewingWindowSizeX() - 1) / 2);
                            this.evMgr
                                    .setViewingWindowLocationY(0 - (this.evMgr
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
        } else {
            return false;
        }
    }

    public boolean removeLevel() {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
                            || level > app.getMazeManager().getMaze()
                                    .getLevels()) {
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
        } else {
            return false;
        }
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
            WidgetWarren.getApplication().showMessage(
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
            final int locX = x / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationX() - xOffset
                    + yOffset;
            final int locY = y / ImageConstants.getImageSize()
                    + this.evMgr.getViewingWindowLocationY() + xOffset
                    - yOffset;
            final int locZ = this.elMgr.getEditorLocationZ();
            final MazeObject there = WidgetWarren
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
                WidgetWarren.getApplication().showMessage("");
                this.redrawVirtual(destX, destY, DEST);
            } else {
                WidgetWarren.getApplication().showMessage(
                        "This object does not have a destination.");
            }
        }
    }

    public void showOutput() {
        final Application app = WidgetWarren.getApplication();
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
        if ((this.outputFrame != null) && this.outputFrame.isVisible()) {
            return this.outputFrame;
        } else {
            return null;
        }
    }

    public void exitEditor() {
        final Application app = WidgetWarren.getApplication();
        // Hide the editor
        this.hideOutput();
        app.setInEditor(false);
        final MazeManager mm = app.getMazeManager();
        final GameManager gm = app.getGameManager();
        // Save the entire level
        mm.getMaze().save();
        // Reset the viewing window
        gm.resetViewingWindowAndPlayerLocation();
        gm.stateChanged();
        WidgetWarren.getApplication().getGUIManager().showGUI();
    }

    private void setUpGUI() {
        // Destroy the old GUI, if one exists
        if (this.outputFrame != null) {
            this.outputFrame.dispose();
        }
        this.messageLabel = new JLabel(" ");
        this.outputFrame = new JFrame("Editor");
        final Image iconlogo = LogoManager.getLogo();
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
        this.gridbag = new GridBagLayout();
        this.c = new GridBagConstraints();
        this.outputPane.setLayout(this.gridbag);
        this.outputFrame.setResizable(false);
        this.c.fill = GridBagConstraints.BOTH;
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
        this.c.gridx = 0;
        this.c.gridy = 0;
        this.gridbag.setConstraints(this.secondaryPane, this.c);
        this.outputPane.add(this.secondaryPane);
        this.c.gridx = 1;
        this.c.gridy = 0;
        this.c.gridwidth = GridBagConstraints.REMAINDER;
        this.gridbag.setConstraints(this.vertScroll, this.c);
        this.outputPane.add(this.vertScroll);
        this.c.gridx = 0;
        this.c.gridy = 1;
        this.c.gridwidth = 1;
        this.c.gridheight = GridBagConstraints.REMAINDER;
        this.gridbag.setConstraints(this.horzScroll, this.c);
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
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
                final MazeObject oldObj = app.getMazeManager().getMazeObject(x,
                        y, z, e);
                if (!(obj.getName().equals(new StairsUp().getName()))
                        && !(obj.getName().equals(new StairsDown().getName()))) {
                    if ((obj.getName().equals(new TwoWayTeleport().getName()))) {
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
                WidgetWarren.getApplication().showMessage("Nothing to undo");
            }
        }
    }

    public void redo() {
        if (!this.viewMode) {
            final Application app = WidgetWarren.getApplication();
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
                final MazeObject oldObj = app.getMazeManager().getMazeObject(x,
                        y, z, e);
                if (!(obj.getName().equals(new StairsUp().getName()))
                        && !(obj.getName().equals(new StairsDown().getName()))) {
                    if ((obj.getName().equals(new TwoWayTeleport().getName()))) {
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
                WidgetWarren.getApplication().showMessage("Nothing to redo");
            }
        }
    }

    public void clearHistory() {
        if (!this.viewMode) {
            this.engine = new UndoRedoEngine();
            this.checkMenus();
        }
    }

    private void updateUndoHistory(final MazeObject obj, final int x,
            final int y, final int z, final int w, final int e) {
        if (!this.viewMode) {
            this.engine.updateUndoHistory(obj, x, y, z, w, e);
        }
    }

    private void updateRedoHistory(final MazeObject obj, final int x,
            final int y, final int z, final int w, final int e) {
        if (!this.viewMode) {
            this.engine.updateRedoHistory(obj, x, y, z, w, e);
        }
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
            }
            this.picker.updatePickerLayout(this.outputPane.getHeight());
            if (this.viewMode) {
                this.picker.disablePicker();
            } else {
                this.picker.enablePicker();
            }
        }
    }

    public void handleCloseWindow() {
        try {
            final Application app = WidgetWarren.getApplication();
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
            WidgetWarren.getErrorLogger().logError(ex);
        }
    }

    private class EventHandler implements AdjustmentListener, MouseListener,
            MouseMotionListener, WindowListener {
        public EventHandler() {
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
                WidgetWarren.getErrorLogger().logError(ex);
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
                        me.editObject(x, y);
                    }
                }
            } catch (final Exception ex) {
                WidgetWarren.getErrorLogger().logError(ex);
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
        public void mouseDragged(MouseEvent e) {
            try {
                final MazeEditor me = MazeEditor.this;
                final int x = e.getX();
                final int y = e.getY();
                if (e.isAltDown() && !me.goToDestMode && e.isShiftDown()) {
                    me.editObject(x, y);
                }
            } catch (final Exception ex) {
                WidgetWarren.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // Do nothing
        }
    }

    private class TreasureEventHandler implements WindowListener {
        public TreasureEventHandler() {
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
            MazeEditor.this.setTreasureChestContents();
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
    }

    private class StartEventHandler implements AdjustmentListener,
            MouseListener {
        public StartEventHandler() {
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
                WidgetWarren.getErrorLogger().logError(ex);
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
                WidgetWarren.getErrorLogger().logError(ex);
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
        public TeleportEventHandler() {
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
                WidgetWarren.getErrorLogger().logError(ex);
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
                WidgetWarren.getErrorLogger().logError(ex);
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
        public ConditionalTeleportEventHandler() {
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
                WidgetWarren.getErrorLogger().logError(ex);
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
                WidgetWarren.getErrorLogger().logError(ex);
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

    private class MetalButtonEventHandler implements AdjustmentListener,
            MouseListener {
        public MetalButtonEventHandler() {
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
                WidgetWarren.getErrorLogger().logError(ex);
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
                MazeEditor.this.setMetalButtonTarget(x, y);
            } catch (final Exception ex) {
                WidgetWarren.getErrorLogger().logError(ex);
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
