/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.editor;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.picturepicker.PicturePicker;
import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.GameManager;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.generic.WorldObjectList;
import net.worldwizard.worldz.objects.Empty;
import net.worldwizard.worldz.objects.EmptyVoid;
import net.worldwizard.worldz.objects.InvisibleOneShotTeleport;
import net.worldwizard.worldz.objects.InvisibleTeleport;
import net.worldwizard.worldz.objects.MetalButton;
import net.worldwizard.worldz.objects.OneShotTeleport;
import net.worldwizard.worldz.objects.Player;
import net.worldwizard.worldz.objects.RandomInvisibleOneShotTeleport;
import net.worldwizard.worldz.objects.RandomInvisibleTeleport;
import net.worldwizard.worldz.objects.RandomOneShotTeleport;
import net.worldwizard.worldz.objects.RandomTeleport;
import net.worldwizard.worldz.objects.StairsDown;
import net.worldwizard.worldz.objects.StairsUp;
import net.worldwizard.worldz.objects.Teleport;
import net.worldwizard.worldz.objects.TreasureChest;
import net.worldwizard.worldz.objects.TwoWayTeleport;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;
import net.worldwizard.worldz.world.World;
import net.worldwizard.worldz.world.WorldConstants;
import net.worldwizard.worldz.world.WorldManager;

public class WorldEditor {
    // Declarations
    private JFrame outputFrame;
    private final JFrame treasureFrame;
    private Container outputPane, secondaryPane, borderPane;
    private final Container treasurePane;
    private GridBagLayout gridbag;
    private GridBagConstraints c;
    private JScrollBar vertScroll, horzScroll;
    private final EventHandler mhandler;
    private final StartEventHandler shandler;
    private final TeleportEventHandler thandler;
    private final TreasureEventHandler rhandler;
    private final MetalButtonEventHandler mbhandler;
    private final LevelPreferencesManager lPrefs;
    private final WorldPreferencesManager mPrefs;
    private PicturePicker picker;
    private final PicturePicker treasurePicker;
    private final WorldObjectList objectList;
    private final String[] groundNames;
    private final String[] objectNames;
    private final WorldObject[] groundObjects;
    private final WorldObject[] objectObjects;
    private final BufferedImageIcon[] groundEditorAppearances;
    private final BufferedImageIcon[] objectEditorAppearances;
    private final String[] containableNames;
    private final WorldObject[] containableObjects;
    private final BufferedImageIcon[] containableEditorAppearances;
    private int TELEPORT_TYPE;
    private int currentObjectIndex;
    private UndoRedoEngine engine;
    private EditorLocationManager elMgr;
    EditorViewingWindowManager evMgr;
    private JLabel[][] drawGrid;
    private boolean worldChanged;
    boolean scriptMode;
    public static final int TELEPORT_TYPE_GENERIC = 0;
    public static final int TELEPORT_TYPE_INVISIBLE_GENERIC = 1;
    public static final int TELEPORT_TYPE_RANDOM = 2;
    public static final int TELEPORT_TYPE_RANDOM_INVISIBLE = 3;
    public static final int TELEPORT_TYPE_ONESHOT = 4;
    public static final int TELEPORT_TYPE_INVISIBLE_ONESHOT = 5;
    public static final int TELEPORT_TYPE_RANDOM_ONESHOT = 6;
    public static final int TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT = 7;
    public static final int TELEPORT_TYPE_TWOWAY = 8;
    public static final int STAIRS_UP = 0;
    public static final int STAIRS_DOWN = 1;

    public WorldEditor() {
        this.lPrefs = new LevelPreferencesManager();
        this.mPrefs = new WorldPreferencesManager();
        this.mhandler = new EventHandler();
        this.mbhandler = new MetalButtonEventHandler();
        this.shandler = new StartEventHandler();
        this.thandler = new TeleportEventHandler();
        this.engine = new UndoRedoEngine();
        this.objectList = Worldz.getApplication().getObjects();
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
        final Image iconlogo = Worldz.getApplication().getIconLogo();
        this.treasureFrame.setIconImage(iconlogo);
        this.treasureFrame
                .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.treasurePicker = new PicturePicker(
                this.containableEditorAppearances, this.containableNames);
        this.treasurePicker
                .setPickerDimensions(GraphicsManager.MAX_WINDOW_SIZE);
        this.treasurePane = this.treasurePicker.getPicker();
        this.treasureFrame.setContentPane(this.treasurePane);
        this.treasureFrame.addWindowListener(this.rhandler);
        this.treasureFrame.pack();
        this.worldChanged = true;
        this.scriptMode = false;
    }

    public void worldChanged() {
        this.worldChanged = true;
    }

    public EditorViewingWindowManager getViewManager() {
        return this.evMgr;
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
            Worldz.getApplication().getWorldManager().getWorld()
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
        Worldz.getApplication().getWorldManager().getWorld().switchLevel(w);
        this.fixLimits();
        this.setUpGUI();
        this.checkMenus();
        this.redrawEditor();
    }

    private void checkMenus() {
        final Application app = Worldz.getApplication();
        final World m = app.getWorldManager().getWorld();
        if (m.getLevels() == World.getMinLevels()) {
            app.getMenuManager().disableRemoveLevel();
        } else {
            app.getMenuManager().enableRemoveLevel();
        }
        if (m.getLevels() == World.getMaxLevels()) {
            app.getMenuManager().disableAddLevel();
        } else {
            app.getMenuManager().enableAddLevel();
        }
        try {
            if (app.getWorldManager().getWorld()
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
            if (app.getWorldManager().getWorld()
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
            if (this.elMgr
                    .getEditorLocationE() != WorldConstants.LAYER_GROUND) {
                app.getMenuManager().enableSetStartPoint();
                app.getMenuManager().enableToggleScript();
            } else {
                app.getMenuManager().disableSetStartPoint();
                app.getMenuManager().disableToggleScript();
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

    public void toggleLayer() {
        if (this.elMgr.getEditorLocationE() == WorldConstants.LAYER_GROUND) {
            this.elMgr.setEditorLocationE(WorldConstants.LAYER_OBJECT);
        } else {
            // Turn script mode off, if it's on
            if (this.scriptMode) {
                this.toggleScript();
                Worldz.getApplication().getMenuManager().toggleToggleScript();
            }
            this.elMgr.setEditorLocationE(WorldConstants.LAYER_GROUND);
        }
        this.updatePicker();
        this.redrawEditor();
        this.checkMenus();
    }

    public void toggleScript() {
        this.scriptMode = !this.scriptMode;
        if (this.scriptMode) {
            this.picker.disablePicker();
        } else {
            this.picker.enablePicker();
        }
        this.redrawEditor();
    }

    public void setWorldPrefs() {
        this.mPrefs.showPrefs();
    }

    public void setLevelPrefs() {
        this.lPrefs.showPrefs();
    }

    public void redrawEditor() {
        if (this.elMgr.getEditorLocationE() == WorldConstants.LAYER_GROUND) {
            this.redrawGround();
        } else {
            this.redrawGroundAndObjects();
        }
    }

    private void redrawGround() {
        // Draw the world in edit mode
        final Application app = Worldz.getApplication();
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
                    final String name1 = app.getWorldManager().getWorld()
                            .getCell(y, x, this.elMgr.getEditorLocationZ(),
                                    WorldConstants.LAYER_GROUND)
                            .editorRenderHook(y, x,
                                    this.elMgr.getEditorLocationZ());
                    this.drawGrid[xFix][yFix]
                            .setIcon(GraphicsManager.getImage(name1));
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.drawGrid[xFix][yFix]
                            .setIcon(GraphicsManager.getImage("Void"));
                } catch (final NullPointerException np) {
                    this.drawGrid[xFix][yFix]
                            .setIcon(GraphicsManager.getImage("Void"));
                }
            }
        }
        this.outputFrame.pack();
        this.outputFrame.setTitle("Object Editor (Ground Layer) - Floor "
                + (this.elMgr.getEditorLocationZ() + 1) + " Level " + (w + 1));
        this.showOutput();
    }

    private void redrawGroundAndObjects() {
        // Draw the world in edit mode
        final Application app = Worldz.getApplication();
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
                    final String name1 = app.getWorldManager().getWorld()
                            .getCell(y, x, this.elMgr.getEditorLocationZ(),
                                    WorldConstants.LAYER_GROUND)
                            .editorRenderHook(y, x,
                                    this.elMgr.getEditorLocationZ());
                    final String name2 = app.getWorldManager().getWorld()
                            .getCell(y, x, this.elMgr.getEditorLocationZ(),
                                    WorldConstants.LAYER_OBJECT)
                            .editorRenderHook(y, x,
                                    this.elMgr.getEditorLocationZ());
                    this.drawGrid[xFix][yFix].setIcon(
                            GraphicsManager.getCompositeImage(name1, name2));
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.drawGrid[xFix][yFix]
                            .setIcon(GraphicsManager.getImage("Void"));
                } catch (final NullPointerException np) {
                    this.drawGrid[xFix][yFix]
                            .setIcon(GraphicsManager.getImage("Void"));
                }
            }
        }
        this.outputFrame.pack();
        if (this.scriptMode) {
            this.outputFrame.setTitle("Script Editor (Object Layer) - Floor "
                    + (this.elMgr.getEditorLocationZ() + 1) + " Level "
                    + (w + 1));
        } else {
            this.outputFrame.setTitle("Object Editor (Object Layer) - Floor "
                    + (this.elMgr.getEditorLocationZ() + 1) + " Level "
                    + (w + 1));
        }
        this.showOutput();
    }

    public void editObject(final int x, final int y) {
        final Application app = Worldz.getApplication();
        this.currentObjectIndex = this.picker.getPicked();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int gridX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int gridY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        try {
            app.getGameManager().setSavedWorldObject(
                    app.getWorldManager().getWorld().getCell(gridX, gridY,
                            this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE()));
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return;
        }
        WorldObject[] choices = null;
        if (this.elMgr.getEditorLocationE() == WorldConstants.LAYER_GROUND) {
            choices = this.groundObjects;
        } else {
            choices = this.objectObjects;
        }
        final WorldObject mo = choices[this.currentObjectIndex].clone();
        this.elMgr.setEditorLocationX(gridX);
        this.elMgr.setEditorLocationY(gridY);
        mo.editorPlaceHook();
        try {
            this.checkTwoWayTeleportPair(this.elMgr.getEditorLocationZ());
            this.updateUndoHistory(app.getGameManager().getSavedWorldObject(),
                    gridX, gridY, this.elMgr.getEditorLocationZ(),
                    this.elMgr.getEditorLocationW(),
                    this.elMgr.getEditorLocationE());
            app.getWorldManager().getWorld().setCell(mo, gridX, gridY,
                    this.elMgr.getEditorLocationZ(),
                    this.elMgr.getEditorLocationE());
            this.checkStairPair(this.elMgr.getEditorLocationZ());
            app.getWorldManager().setDirty(true);
            this.checkMenus();
            this.redrawEditor();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            app.getWorldManager().getWorld().setCell(
                    app.getGameManager().getSavedWorldObject(), gridX, gridY,
                    this.elMgr.getEditorLocationZ(),
                    this.elMgr.getEditorLocationE());
            this.redrawEditor();
        }
    }

    void editScript(final int x, final int y) {
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int gridX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int gridY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int gridZ = this.elMgr.getEditorLocationZ();
        ScriptEditor.editScript(gridX, gridY, gridZ);
    }

    void probeScript(final int x, final int y) {
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int gridX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int gridY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int gridZ = this.elMgr.getEditorLocationZ();
        ScriptEditor.probeScript(gridX, gridY, gridZ);
    }

    void deleteScript(final int x, final int y) {
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int gridX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int gridY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int gridZ = this.elMgr.getEditorLocationZ();
        ScriptEditor.deleteScript(gridX, gridY, gridZ);
    }

    public void probeObjectProperties(final int x, final int y) {
        final Application app = Worldz.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int gridX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int gridY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        try {
            final WorldObject mo = app.getWorldManager().getWorld().getCell(
                    gridX, gridY, this.elMgr.getEditorLocationZ(),
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
        final Application app = Worldz.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int gridX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int gridY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        try {
            final WorldObject mo = app.getWorldManager().getWorld().getCell(
                    gridX, gridY, this.elMgr.getEditorLocationZ(),
                    this.elMgr.getEditorLocationE());
            this.elMgr.setEditorLocationX(gridX);
            this.elMgr.setEditorLocationY(gridY);
            if (!mo.defersSetProperties()) {
                final WorldObject mo2 = mo.editorPropertiesHook();
                if (mo2 == null) {
                    Messager.showMessage("This object has no properties");
                } else {
                    this.checkTwoWayTeleportPair(
                            this.elMgr.getEditorLocationZ());
                    this.updateUndoHistory(
                            app.getGameManager().getSavedWorldObject(), gridX,
                            gridY, this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationW(),
                            this.elMgr.getEditorLocationE());
                    app.getWorldManager().getWorld().setCell(mo2, gridX, gridY,
                            this.elMgr.getEditorLocationZ(),
                            this.elMgr.getEditorLocationE());
                    this.checkStairPair(this.elMgr.getEditorLocationZ());
                    this.checkMenus();
                    app.getWorldManager().setDirty(true);
                }
            } else {
                mo.editorPropertiesHook();
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    private void checkStairPair(final int z) {
        final Application app = Worldz.getApplication();
        final WorldObject mo1 = app.getWorldManager().getWorld().getCell(
                this.elMgr.getEditorLocationX(),
                this.elMgr.getEditorLocationY(), z,
                WorldConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2, name3;
        try {
            final WorldObject mo2 = app.getWorldManager().getWorld().getCell(
                    this.elMgr.getEditorLocationX(),
                    this.elMgr.getEditorLocationY(), z + 1,
                    WorldConstants.LAYER_OBJECT);
            name2 = mo2.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name2 = "";
        }
        try {
            final WorldObject mo3 = app.getWorldManager().getWorld().getCell(
                    this.elMgr.getEditorLocationX(),
                    this.elMgr.getEditorLocationY(), z - 1,
                    WorldConstants.LAYER_OBJECT);
            name3 = mo3.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name3 = "";
        }
        if (!name1.equals("Stairs Up")) {
            if (name2.equals("Stairs Down")) {
                this.unpairStairs(WorldEditor.STAIRS_UP, z);
            } else if (!name1.equals("Stairs Down")) {
                if (name3.equals("Stairs Up")) {
                    this.unpairStairs(WorldEditor.STAIRS_DOWN, z);
                }
            }
        }
    }

    private void reverseCheckStairPair(final int z) {
        final Application app = Worldz.getApplication();
        final WorldObject mo1 = app.getWorldManager().getWorld().getCell(
                this.elMgr.getEditorLocationX(),
                this.elMgr.getEditorLocationY(), z,
                WorldConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2, name3;
        try {
            final WorldObject mo2 = app.getWorldManager().getWorld().getCell(
                    this.elMgr.getEditorLocationX(),
                    this.elMgr.getEditorLocationY(), z + 1,
                    WorldConstants.LAYER_OBJECT);
            name2 = mo2.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name2 = "";
        }
        try {
            final WorldObject mo3 = app.getWorldManager().getWorld().getCell(
                    this.elMgr.getEditorLocationX(),
                    this.elMgr.getEditorLocationY(), z - 1,
                    WorldConstants.LAYER_OBJECT);
            name3 = mo3.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name3 = "";
        }
        if (name1.equals("Stairs Up")) {
            if (!name2.equals("Stairs Down")) {
                this.pairStairs(WorldEditor.STAIRS_UP, z);
            } else if (name1.equals("Stairs Down")) {
                if (!name3.equals("Stairs Up")) {
                    this.pairStairs(WorldEditor.STAIRS_DOWN, z);
                }
            }
        }
    }

    public void pairStairs(final int type) {
        final Application app = Worldz.getApplication();
        switch (type) {
            case STAIRS_UP:
                try {
                    app.getWorldManager().getWorld().setCell(new StairsDown(),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getEditorLocationZ() + 1,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            case STAIRS_DOWN:
                try {
                    app.getWorldManager().getWorld().setCell(new StairsUp(),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(),
                            this.elMgr.getEditorLocationZ() - 1,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            default:
                break;
        }
    }

    private void pairStairs(final int type, final int z) {
        final Application app = Worldz.getApplication();
        switch (type) {
            case STAIRS_UP:
                try {
                    app.getWorldManager().getWorld().setCell(new StairsDown(),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(), z + 1,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            case STAIRS_DOWN:
                try {
                    app.getWorldManager().getWorld().setCell(new StairsUp(),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(), z - 1,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            default:
                break;
        }
    }

    private void unpairStairs(final int type, final int z) {
        final Application app = Worldz.getApplication();
        switch (type) {
            case STAIRS_UP:
                try {
                    app.getWorldManager().getWorld().setCell(new Empty(),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(), z + 1,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            case STAIRS_DOWN:
                try {
                    app.getWorldManager().getWorld().setCell(new Empty(),
                            this.elMgr.getEditorLocationX(),
                            this.elMgr.getEditorLocationY(), z - 1,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            default:
                break;
        }
    }

    private void checkTwoWayTeleportPair(final int z) {
        final Application app = Worldz.getApplication();
        final WorldObject mo1 = app.getWorldManager().getWorld().getCell(
                this.elMgr.getEditorLocationX(),
                this.elMgr.getEditorLocationY(), z,
                WorldConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2;
        int destX, destY, destZ;
        if (name1.equals("Two-Way Teleport")) {
            final TwoWayTeleport twt = (TwoWayTeleport) mo1;
            destX = twt.getDestinationRow();
            destY = twt.getDestinationColumn();
            destZ = twt.getDestinationFloor();
            final WorldObject mo2 = app.getWorldManager().getWorld()
                    .getCell(destX, destY, destZ, WorldConstants.LAYER_OBJECT);
            name2 = mo2.getName();
            if (name2.equals("Two-Way Teleport")) {
                WorldEditor.unpairTwoWayTeleport(destX, destY, destZ);
            }
        }
    }

    private void reverseCheckTwoWayTeleportPair(final int z) {
        final Application app = Worldz.getApplication();
        final WorldObject mo1 = app.getWorldManager().getWorld().getCell(
                this.elMgr.getEditorLocationX(),
                this.elMgr.getEditorLocationY(), z,
                WorldConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2;
        int destX, destY, destZ;
        if (name1.equals("Two-Way Teleport")) {
            final TwoWayTeleport twt = (TwoWayTeleport) mo1;
            destX = twt.getDestinationRow();
            destY = twt.getDestinationColumn();
            destZ = twt.getDestinationFloor();
            final WorldObject mo2 = app.getWorldManager().getWorld()
                    .getCell(destX, destY, destZ, WorldConstants.LAYER_OBJECT);
            name2 = mo2.getName();
            if (!name2.equals("Two-Way Teleport")) {
                this.pairTwoWayTeleport(destX, destY, destZ);
            }
        }
    }

    public void pairTwoWayTeleport(final int destX, final int destY,
            final int destZ) {
        final Application app = Worldz.getApplication();
        app.getWorldManager().getWorld()
                .setCell(new TwoWayTeleport(this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(),
                        this.elMgr.getCameFromZ(), this.elMgr.getCameFromW()),
                        destX, destY, destZ, WorldConstants.LAYER_OBJECT);
    }

    private static void unpairTwoWayTeleport(final int destX, final int destY,
            final int destZ) {
        final Application app = Worldz.getApplication();
        app.getWorldManager().getWorld().setCell(new Empty(), destX, destY,
                destZ, WorldConstants.LAYER_OBJECT);
    }

    public WorldObject editTeleportDestination(final int type) {
        final Application app = Worldz.getApplication();
        String input1 = null, input2 = null;
        this.TELEPORT_TYPE = type;
        int destX = 0, destY = 0;
        switch (type) {
            case TELEPORT_TYPE_GENERIC:
            case TELEPORT_TYPE_INVISIBLE_GENERIC:
            case TELEPORT_TYPE_ONESHOT:
            case TELEPORT_TYPE_INVISIBLE_ONESHOT:
            case TELEPORT_TYPE_TWOWAY:
                Messager.showMessage("Click to set teleport destination");
                break;
            case TELEPORT_TYPE_RANDOM:
            case TELEPORT_TYPE_RANDOM_INVISIBLE:
            case TELEPORT_TYPE_RANDOM_ONESHOT:
            case TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT:
                input1 = Messager.showTextInputDialog("Random row range:",
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
                    input2 = Messager.showTextInputDialog("Random column range:",
                            "Editor");
                    break;
                default:
                    break;
            }
            if (input2 != null) {
                try {
                    destX = Integer.parseInt(input1);
                    destY = Integer.parseInt(input2);
                } catch (final NumberFormatException nf) {
                    Messager.showDialog(
                            "Row and column ranges must be integers.");
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
                    this.horzScroll.removeAdjustmentListener(this.mhandler);
                    this.vertScroll.removeAdjustmentListener(this.mhandler);
                    this.secondaryPane.removeMouseListener(this.mhandler);
                    this.horzScroll.addAdjustmentListener(this.thandler);
                    this.vertScroll.addAdjustmentListener(this.thandler);
                    this.secondaryPane.addMouseListener(this.thandler);
                    this.elMgr.setCameFromZ(this.elMgr.getEditorLocationZ());
                    this.elMgr.setCameFromW(this.elMgr.getEditorLocationW());
                    app.getMenuManager().disableDownOneLevel();
                    app.getMenuManager().disableUpOneLevel();
                    break;
                default:
                    break;
            }
        }
        return null;
    }

    public WorldObject editMetalButtonTarget() {
        Messager.showMessage("Click to set metal button target");
        final Application app = Worldz.getApplication();
        this.horzScroll.removeAdjustmentListener(this.mhandler);
        this.vertScroll.removeAdjustmentListener(this.mhandler);
        this.secondaryPane.removeMouseListener(this.mhandler);
        this.horzScroll.addAdjustmentListener(this.mbhandler);
        this.vertScroll.addAdjustmentListener(this.mbhandler);
        this.secondaryPane.addMouseListener(this.mbhandler);
        this.elMgr.setCameFromZ(this.elMgr.getEditorLocationZ());
        this.elMgr.setCameFromW(this.elMgr.getEditorLocationW());
        app.getMenuManager().disableDownOneLevel();
        app.getMenuManager().disableUpOneLevel();
        return null;
    }

    public WorldObject editTreasureChestContents() {
        Messager.showMessage("Pick treasure chest contents");
        this.setDefaultContents();
        this.disableOutput();
        this.treasureFrame.setVisible(true);
        return null;
    }

    private void setDefaultContents() {
        TreasureChest tc = null;
        WorldObject contents = null;
        int contentsIndex = 0;
        final Application app = Worldz.getApplication();
        try {
            tc = (TreasureChest) app.getWorldManager().getWorldObject(
                    this.elMgr.getEditorLocationX(),
                    this.elMgr.getEditorLocationY(), this.elMgr.getCameFromZ(),
                    WorldConstants.LAYER_OBJECT);
            contents = tc.getInsideObject();
            for (int x = 0; x < this.containableObjects.length; x++) {
                if (contents.getName()
                        .equals(this.containableObjects[x].getName())) {
                    contentsIndex = x;
                    break;
                }
            }
        } catch (final ClassCastException cce) {
            // Do nothing
        } catch (final NullPointerException npe) {
            // Do nothing
        }
        this.treasurePicker.setDefaultSelection(contentsIndex);
    }

    public void setTeleportDestination(final int x, final int y) {
        final Application app = Worldz.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int destX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = this.elMgr.getEditorLocationZ();
        final int destW = this.elMgr.getEditorLocationW();
        try {
            app.getWorldManager().getWorld().getCell(destX, destY, destZ,
                    WorldConstants.LAYER_OBJECT);
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
                app.getWorldManager().getWorld().setCell(
                        new Teleport(destX, destY, destZ, destW),
                        this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(), this.elMgr.getCameFromZ(),
                        WorldConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_INVISIBLE_GENERIC:
                app.getWorldManager().getWorld().setCell(
                        new InvisibleTeleport(destX, destY, destZ, destW),
                        this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(), this.elMgr.getCameFromZ(),
                        WorldConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_ONESHOT:
                app.getWorldManager().getWorld().setCell(
                        new OneShotTeleport(destX, destY, destZ, destW),
                        this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(), this.elMgr.getCameFromZ(),
                        WorldConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_INVISIBLE_ONESHOT:
                app.getWorldManager().getWorld().setCell(
                        new InvisibleOneShotTeleport(destX, destY, destZ, destW),
                        this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(), this.elMgr.getCameFromZ(),
                        WorldConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_TWOWAY:
                app.getWorldManager().getWorld().setCell(
                        new TwoWayTeleport(destX, destY, destZ, destW),
                        this.elMgr.getEditorLocationX(),
                        this.elMgr.getEditorLocationY(), this.elMgr.getCameFromZ(),
                        WorldConstants.LAYER_OBJECT);
                this.pairTwoWayTeleport(destX, destY, destZ);
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
        Messager.showMessage("Destination set.");
        app.getWorldManager().setDirty(true);
        this.redrawEditor();
    }

    public void setMetalButtonTarget(final int x, final int y) {
        final Application app = Worldz.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int destX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = this.elMgr.getEditorLocationZ();
        final int destW = this.elMgr.getEditorLocationW();
        try {
            app.getWorldManager().getWorld().getCell(destX, destY, destZ,
                    WorldConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.horzScroll.removeAdjustmentListener(this.mbhandler);
            this.vertScroll.removeAdjustmentListener(this.mbhandler);
            this.secondaryPane.removeMouseListener(this.mbhandler);
            this.horzScroll.addAdjustmentListener(this.mhandler);
            this.vertScroll.addAdjustmentListener(this.mhandler);
            this.secondaryPane.addMouseListener(this.mhandler);
            return;
        }
        app.getWorldManager().getWorld().setCell(
                new MetalButton(destX, destY, destZ, destW),
                this.elMgr.getEditorLocationX(),
                this.elMgr.getEditorLocationY(), this.elMgr.getCameFromZ(),
                WorldConstants.LAYER_OBJECT);
        this.horzScroll.removeAdjustmentListener(this.mbhandler);
        this.vertScroll.removeAdjustmentListener(this.mbhandler);
        this.secondaryPane.removeMouseListener(this.mbhandler);
        this.horzScroll.addAdjustmentListener(this.mhandler);
        this.vertScroll.addAdjustmentListener(this.mhandler);
        this.secondaryPane.addMouseListener(this.mhandler);
        this.checkMenus();
        Messager.showMessage("Target set.");
        app.getWorldManager().setDirty(true);
        this.redrawEditor();
    }

    public void setTreasureChestContents() {
        this.enableOutput();
        final Application app = Worldz.getApplication();
        final WorldObject contents = this.containableObjects[this.treasurePicker
                .getPicked()];
        app.getWorldManager().getWorld().setCell(new TreasureChest(contents),
                this.elMgr.getEditorLocationX(),
                this.elMgr.getEditorLocationY(), this.elMgr.getCameFromZ(),
                WorldConstants.LAYER_OBJECT);
        this.checkMenus();
        Messager.showMessage("Contents set.");
        app.getWorldManager().setDirty(true);
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
        Messager.showMessage("Click to set start point");
    }

    public void setPlayerLocation(final int x, final int y, final int z) {
        final Application app = Worldz.getApplication();
        final int oldX = app.getWorldManager().getWorld().getStartColumn();
        final int oldY = app.getWorldManager().getWorld().getStartRow();
        final int oldZ = app.getWorldManager().getWorld().getStartFloor();
        // Erase old player
        try {
            app.getWorldManager().getWorld().setCell(new Empty(), oldX, oldY,
                    oldZ, WorldConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Ignore
        }
        // Set new player
        app.getWorldManager().getWorld().setStartRow(y);
        app.getWorldManager().getWorld().setStartColumn(x);
        app.getWorldManager().getWorld().setStartFloor(z);
        app.getWorldManager().getWorld().setCell(new Player(), x, y, z,
                WorldConstants.LAYER_OBJECT);
    }

    public void setPlayerLocation() {
        final Application app = Worldz.getApplication();
        final int oldX = app.getWorldManager().getWorld().getStartColumn();
        final int oldY = app.getWorldManager().getWorld().getStartRow();
        final int oldZ = app.getWorldManager().getWorld().getStartFloor();
        // Erase old player
        try {
            app.getWorldManager().getWorld().setCell(new Empty(), oldX, oldY,
                    oldZ, WorldConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Ignore
        }
        // Set new player
        app.getWorldManager().getWorld()
                .setStartRow(this.elMgr.getEditorLocationY());
        app.getWorldManager().getWorld()
                .setStartColumn(this.elMgr.getEditorLocationX());
        app.getWorldManager().getWorld()
                .setStartFloor(this.elMgr.getEditorLocationZ());
        app.getWorldManager().getWorld().setCell(new Player(),
                this.elMgr.getEditorLocationX(),
                this.elMgr.getEditorLocationY(),
                this.elMgr.getEditorLocationZ(), WorldConstants.LAYER_OBJECT);
    }

    void setPlayerLocation(final int x, final int y) {
        final Application app = Worldz.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int destX = x / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / GraphicsManager.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int oldX = app.getWorldManager().getWorld().getStartColumn();
        final int oldY = app.getWorldManager().getWorld().getStartRow();
        final int oldZ = app.getWorldManager().getWorld().getStartFloor();
        // Erase old player
        try {
            app.getWorldManager().getWorld().setCell(new Empty(), oldX, oldY,
                    oldZ, WorldConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Ignore
        }
        // Set new player
        try {
            app.getWorldManager().getWorld().saveStart();
            app.getWorldManager().getWorld().setStartRow(destY);
            app.getWorldManager().getWorld().setStartColumn(destX);
            app.getWorldManager().getWorld()
                    .setStartFloor(this.elMgr.getEditorLocationZ());
            app.getWorldManager().getWorld().setCell(new Player(), destX, destY,
                    this.elMgr.getEditorLocationZ(),
                    WorldConstants.LAYER_OBJECT);
            Messager.showMessage("Start point set.");
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            app.getWorldManager().getWorld().restoreStart();
            try {
                app.getWorldManager().getWorld().setCell(new Player(), oldX,
                        oldY, oldZ, WorldConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException aioob2) {
                // Ignore
            }
            Messager.showMessage("Aim within the world");
        }
        // Swap event handlers
        this.horzScroll.removeAdjustmentListener(this.shandler);
        this.vertScroll.removeAdjustmentListener(this.shandler);
        this.secondaryPane.removeMouseListener(this.shandler);
        this.horzScroll.addAdjustmentListener(this.mhandler);
        this.vertScroll.addAdjustmentListener(this.mhandler);
        this.secondaryPane.addMouseListener(this.mhandler);
        // Set dirty flag
        app.getWorldManager().setDirty(true);
        this.redrawEditor();
    }

    public void editWorld() {
        final Application app = Worldz.getApplication();
        if (app.getWorldManager().getLoaded()) {
            app.getGUIManager().hideGUI();
            app.setInEditor(true);
            // Reset game state
            app.getGameManager().resetGameState();
            // Create the managers
            if (this.worldChanged) {
                this.elMgr = new EditorLocationManager();
                this.evMgr = new EditorViewingWindowManager();
                final int worldSizeX = app.getWorldManager().getWorld()
                        .getRows();
                final int worldSizeY = app.getWorldManager().getWorld()
                        .getColumns();
                app.getEditor().getViewManager()
                        .halfOffsetMaximumViewingWindowLocation(worldSizeX,
                                worldSizeY);
                this.worldChanged = false;
            }
            // Reset the level
            this.elMgr.setEditorLocationZ(0);
            this.elMgr.setEditorLocationW(0);
            app.getWorldManager().getWorld().switchLevel(0);
            this.elMgr.setLimitsFromWorld(app.getWorldManager().getWorld());
            this.evMgr.halfOffsetMaximumViewingWindowLocationsFromWorld(
                    app.getWorldManager().getWorld());
            this.setUpGUI();
            this.clearHistory();
            this.checkMenus();
            // Make sure message area is attached to border pane
            this.borderPane.removeAll();
            this.borderPane.add(app.getGameManager().getMessageLabel(),
                    BorderLayout.SOUTH);
            this.borderPane.add(this.outputPane, BorderLayout.CENTER);
            this.borderPane.add(this.picker.getPicker(), BorderLayout.EAST);
            this.redrawEditor();
            this.checkMenus();
        } else {
            Messager.showDialog("No World Opened");
        }
    }

    public boolean newWorld() {
        final Application app = Worldz.getApplication();
        boolean success = true;
        boolean saved = true;
        int status = 0;
        if (app.getWorldManager().getDirty()) {
            status = app.getWorldManager().showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = app.getWorldManager().saveWorld();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                app.getWorldManager().setDirty(false);
            }
        }
        if (saved) {
            app.getGameManager().getPlayerManager().resetPlayerLocation();
            app.getWorldManager().setWorld(new World());
            success = this.addLevelInternal(true);
            if (success) {
                app.getWorldManager().clearLastUsedFilenames();
                this.clearHistory();
            }
        } else {
            success = false;
        }
        if (success) {
            this.worldChanged = true;
            app.getGameManager().stateChanged();
        }
        return success;
    }

    public void fixLimits() {
        // Fix limits
        final Application app = Worldz.getApplication();
        if (app.getWorldManager().getWorld() != null && this.elMgr != null
                && this.evMgr != null) {
            this.elMgr.setLimitsFromWorld(app.getWorldManager().getWorld());
            this.evMgr.halfOffsetMaximumViewingWindowLocationsFromWorld(
                    app.getWorldManager().getWorld());
        }
    }

    private boolean confirmNonUndoable(final String task) {
        final int confirm = Messager.showConfirmDialog(
                "Are you sure you want to " + task + "?"
                        + " This action is NOT undoable and will clear the undo/redo history!",
                "Editor");
        if (confirm == JOptionPane.YES_OPTION) {
            this.clearHistory();
            return true;
        }
        return false;
    }

    public void fillLevel() {
        if (this.confirmNonUndoable(
                "overwrite the active level with default data")) {
            Worldz.getApplication().getWorldManager().getWorld()
                    .fillLevelDefault();
            Messager.showMessage("Level filled.");
            Worldz.getApplication().getWorldManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillFloor() {
        if (this.confirmNonUndoable(
                "overwrite the active floor within the active level with default data")) {
            Worldz.getApplication().getWorldManager().getWorld()
                    .fillFloorDefault(this.elMgr.getEditorLocationZ());
            Messager.showMessage("Floor filled.");
            Worldz.getApplication().getWorldManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillLevelRandomly() {
        if (this.confirmNonUndoable(
                "overwrite the active level with random data")) {
            if (Worldz.getApplication().getMenuManager().useFillRuleSets()) {
                Worldz.getApplication().getWorldManager().getWorld()
                        .fillLevelRandomlyCustom();
            } else {
                Worldz.getApplication().getWorldManager().getWorld()
                        .fillLevelRandomly();
            }
            Messager.showMessage("Level randomly filled.");
            Worldz.getApplication().getWorldManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillFloorRandomly() {
        if (this.confirmNonUndoable(
                "overwrite the active floor within the active level with random data")) {
            if (Worldz.getApplication().getMenuManager().useFillRuleSets()) {
                Worldz.getApplication().getWorldManager().getWorld()
                        .fillFloorRandomlyCustom(
                                this.elMgr.getEditorLocationZ());
            } else {
                Worldz.getApplication().getWorldManager().getWorld()
                        .fillFloorRandomly(this.elMgr.getEditorLocationZ());
            }
            Messager.showMessage("Floor randomly filled.");
            Worldz.getApplication().getWorldManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public boolean addLevel() {
        return this.addLevelInternal(false);
    }

    private boolean addLevelInternal(final boolean flag) {
        final Application app = Worldz.getApplication();
        int levelSizeX, levelSizeY, levelSizeZ;
        final int maxR = World.getMaxRows();
        final int minR = World.getMinRows();
        final int maxC = World.getMaxColumns();
        final int minC = World.getMinColumns();
        final int maxF = World.getMaxFloors();
        final int minF = World.getMinFloors();
        String msg = null;
        if (flag) {
            msg = "New World";
        } else {
            msg = "New Level";
        }
        boolean success = true;
        String input1, input2, input3;
        input1 = Messager.showTextInputDialog(
                "Number of rows (" + minR + "-" + maxR + ")?", msg);
        if (input1 != null) {
            input2 = Messager.showTextInputDialog(
                    "Number of columns (" + minC + "-" + maxC + ")?", msg);
            if (input2 != null) {
                input3 = Messager.showTextInputDialog(
                        "Number of floors (" + minF + "-" + maxF + ")?", msg);
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
                                    "Rows must be less than or equal to " + maxR
                                            + ".");
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
                        final int saveLevel = app.getWorldManager().getWorld()
                                .getActiveLevelNumber();
                        success = app.getWorldManager().getWorld()
                                .addLevel(levelSizeX, levelSizeY, levelSizeZ);
                        if (success) {
                            this.fixLimits();
                            if (!flag) {
                                this.evMgr.setViewingWindowLocationX(
                                        0 - (this.evMgr.getViewingWindowSizeX()
                                                - 1) / 2);
                                this.evMgr.setViewingWindowLocationY(
                                        0 - (this.evMgr.getViewingWindowSizeY()
                                                - 1) / 2);
                            }
                            app.getWorldManager().getWorld().fillLevel(
                                    app.getPrefsManager()
                                            .getEditorDefaultFill(),
                                    new Empty());
                            // Save the entire level
                            app.getWorldManager().getWorld().saveLevel();
                            app.getWorldManager().getWorld()
                                    .switchLevel(saveLevel);
                            this.checkMenus();
                        }
                    } catch (final NumberFormatException nf) {
                        Messager.showDialog(nf.getMessage());
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
        final Application app = Worldz.getApplication();
        int levelSizeX, levelSizeY, levelSizeZ;
        final int maxR = World.getMaxRows();
        final int minR = World.getMinRows();
        final int maxC = World.getMaxColumns();
        final int minC = World.getMinColumns();
        final int maxF = World.getMaxFloors();
        final int minF = World.getMinFloors();
        final String msg = "Resize Level";
        boolean success = true;
        String input1, input2, input3;
        input1 = Messager.showTextInputDialogWithDefault(
                "Number of rows (" + minR + "-" + maxR + ")?", msg,
                Integer.toString(app.getWorldManager().getWorld().getRows()));
        if (input1 != null) {
            input2 = Messager.showTextInputDialogWithDefault(
                    "Number of columns (" + minC + "-" + maxC + ")?", msg,
                    Integer.toString(
                            app.getWorldManager().getWorld().getColumns()));
            if (input2 != null) {
                input3 = Messager.showTextInputDialogWithDefault(
                        "Number of floors (" + minF + "-" + maxF + ")?", msg,
                        Integer.toString(
                                app.getWorldManager().getWorld().getFloors()));
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
                                    "Rows must be less than or equal to " + maxR
                                            + ".");
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
                        app.getWorldManager().getWorld().resize(levelSizeX,
                                levelSizeY, levelSizeZ);
                        this.fixLimits();
                        this.evMgr.setViewingWindowLocationX(0
                                - (this.evMgr.getViewingWindowSizeX() - 1) / 2);
                        this.evMgr.setViewingWindowLocationY(0
                                - (this.evMgr.getViewingWindowSizeY() - 1) / 2);
                        // Save the entire level
                        app.getWorldManager().getWorld().saveLevel();
                        this.checkMenus();
                        // Redraw
                        this.redrawEditor();
                    } catch (final NumberFormatException nf) {
                        Messager.showDialog(nf.getMessage());
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
        final Application app = Worldz.getApplication();
        int level;
        boolean success = true;
        String input;
        input = Messager.showTextInputDialog(
                "Level Number (1-"
                        + app.getWorldManager().getWorld().getLevels() + ")?",
                "Remove Level");
        if (input != null) {
            try {
                level = Integer.parseInt(input);
                if (level < 1 || level > app.getWorldManager().getWorld()
                        .getLevels()) {
                    throw new NumberFormatException(
                            "Level number must be in the range 1 to " + app
                                    .getWorldManager().getWorld().getLevels()
                                    + ".");
                }
                success = app.getWorldManager().getWorld().removeLevel();
                if (success) {
                    this.fixLimits();
                    if (level == this.elMgr.getEditorLocationW() + 1) {
                        // Deleted current level - go to level 1
                        this.updateEditorLevelAbsolute(0);
                    }
                    this.checkMenus();
                }
            } catch (final NumberFormatException nf) {
                Messager.showDialog(nf.getMessage());
                success = false;
            }
        } else {
            // User canceled
            success = false;
        }
        return success;
    }

    public void goToHandler() {
        int locX, locY, locZ, locW;
        final String msg = "Go To...";
        String input1, input2, input3, input4;
        input1 = Messager.showTextInputDialog("Row?", msg);
        if (input1 != null) {
            input2 = Messager.showTextInputDialog("Column?", msg);
            if (input2 != null) {
                input3 = Messager.showTextInputDialog("Floor?", msg);
                if (input3 != null) {
                    input4 = Messager.showTextInputDialog("Level?", msg);
                    if (input4 != null) {
                        try {
                            locX = Integer.parseInt(input1) - 1;
                            locY = Integer.parseInt(input2) - 1;
                            locZ = Integer.parseInt(input3) - 1;
                            locW = Integer.parseInt(input4) - 1;
                            this.updateEditorPosition(locX, locY, locZ, locW);
                        } catch (final NumberFormatException nf) {
                            Messager.showDialog(nf.getMessage());
                        }
                    }
                }
            }
        }
    }

    public boolean isOutputVisible() {
        if (this.outputFrame == null) {
            return false;
        } else {
            return this.outputFrame.isVisible();
        }
    }

    public void showOutput() {
        final Application app = Worldz.getApplication();
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
        final Application app = Worldz.getApplication();
        // Hide the editor
        this.hideOutput();
        app.setInEditor(false);
        final WorldManager mm = app.getWorldManager();
        final GameManager gm = app.getGameManager();
        // Save the entire level
        mm.getWorld().save();
        // Reset the viewing window
        gm.resetViewingWindowAndPlayerLocation();
        gm.stateChanged();
        Worldz.getApplication().getGUIManager().showGUI();
    }

    private void setUpGUI() {
        // Destroy the old GUI, if one exists
        if (this.outputFrame != null) {
            this.outputFrame.dispose();
        }
        final Application app = Worldz.getApplication();
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
        this.drawGrid = new JLabel[this.evMgr
                .getViewingWindowSizeX()][this.evMgr.getViewingWindowSizeY()];
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
        this.borderPane.add(app.getGameManager().getMessageLabel(),
                BorderLayout.SOUTH);
        this.gridbag = new GridBagLayout();
        this.c = new GridBagConstraints();
        this.outputPane.setLayout(this.gridbag);
        this.outputFrame.setResizable(false);
        this.c.fill = GridBagConstraints.BOTH;
        this.secondaryPane
                .setLayout(new GridLayout(this.evMgr.getViewingWindowSizeX(),
                        this.evMgr.getViewingWindowSizeY()));
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
        this.outputFrame.addWindowListener(this.mhandler);
        this.updatePicker();
        this.borderPane.add(this.picker.getPicker(), BorderLayout.EAST);
    }

    public void undo() {
        final Application app = Worldz.getApplication();
        this.engine.undo();
        final WorldObject obj = this.engine.getObject();
        final int x = this.engine.getX();
        final int y = this.engine.getY();
        final int z = this.engine.getZ();
        final int w = this.engine.getW();
        final int e = this.engine.getE();
        this.elMgr.setEditorLocationX(x);
        this.elMgr.setEditorLocationY(y);
        this.elMgr.setCameFromZ(z);
        this.elMgr.setCameFromW(w);
        if (x != -1 && y != -1 && z != -1 && w != -1) {
            final WorldObject oldObj = app.getWorldManager().getWorldObject(x,
                    y, z, e);
            if (!obj.getName().equals(new StairsUp().getName())
                    && !obj.getName().equals(new StairsDown().getName())) {
                if (obj.getName().equals(new TwoWayTeleport().getName())) {
                    app.getWorldManager().getWorld().setCell(obj, x, y, z, e);
                    this.reverseCheckTwoWayTeleportPair(z);
                    this.checkStairPair(z);
                } else {
                    this.checkTwoWayTeleportPair(z);
                    app.getWorldManager().getWorld().setCell(obj, x, y, z, e);
                    this.checkStairPair(z);
                }
            } else {
                app.getWorldManager().getWorld().setCell(obj, x, y, z, e);
                this.reverseCheckStairPair(z);
            }
            this.updateRedoHistory(oldObj, x, y, z, w, e);
            this.checkMenus();
            this.redrawEditor();
        } else {
            Messager.showMessage("Nothing to undo");
        }
    }

    public void redo() {
        final Application app = Worldz.getApplication();
        this.engine.redo();
        final WorldObject obj = this.engine.getObject();
        final int x = this.engine.getX();
        final int y = this.engine.getY();
        final int z = this.engine.getZ();
        final int w = this.engine.getW();
        final int e = this.engine.getE();
        this.elMgr.setEditorLocationX(x);
        this.elMgr.setEditorLocationY(y);
        this.elMgr.setCameFromZ(z);
        this.elMgr.setCameFromW(w);
        if (x != -1 && y != -1 && z != -1 && w != -1) {
            final WorldObject oldObj = app.getWorldManager().getWorldObject(x,
                    y, z, e);
            if (!obj.getName().equals(new StairsUp().getName())
                    && !obj.getName().equals(new StairsDown().getName())) {
                if (obj.getName().equals(new TwoWayTeleport().getName())) {
                    app.getWorldManager().getWorld().setCell(obj, x, y, z, e);
                    this.reverseCheckTwoWayTeleportPair(z);
                    this.checkStairPair(z);
                } else {
                    this.checkTwoWayTeleportPair(z);
                    app.getWorldManager().getWorld().setCell(obj, x, y, z, e);
                    this.checkStairPair(z);
                }
            } else {
                app.getWorldManager().getWorld().setCell(obj, x, y, z, e);
                this.reverseCheckStairPair(z);
            }
            this.updateUndoHistory(oldObj, x, y, z, w, e);
            this.checkMenus();
            this.redrawEditor();
        } else {
            Messager.showMessage("Nothing to redo");
        }
    }

    public void clearHistory() {
        this.engine = new UndoRedoEngine();
        this.checkMenus();
    }

    private void updateUndoHistory(final WorldObject obj, final int x,
            final int y, final int z, final int w, final int e) {
        this.engine.updateUndoHistory(obj, x, y, z, w, e);
    }

    private void updateRedoHistory(final WorldObject obj, final int x,
            final int y, final int z, final int w, final int e) {
        this.engine.updateRedoHistory(obj, x, y, z, w, e);
    }

    public void updatePicker() {
        BufferedImageIcon[] newImages = null;
        String[] newNames = null;
        if (this.elMgr.getEditorLocationE() == WorldConstants.LAYER_GROUND) {
            newImages = this.groundEditorAppearances;
            newNames = this.groundNames;
        } else {
            newImages = this.objectEditorAppearances;
            newNames = this.objectNames;
        }
        if (this.picker != null) {
            this.picker.updatePicker(newImages, newNames);
        } else {
            this.picker = new PicturePicker(newImages, newNames);
        }
        this.picker.setPickerDimensions(this.borderPane.getHeight());
    }

    public void handleCloseWindow() {
        try {
            final Application app = Worldz.getApplication();
            boolean success = false;
            int status = JOptionPane.DEFAULT_OPTION;
            if (app.getWorldManager().getDirty()) {
                status = app.getWorldManager().showSaveDialog();
                if (status == JOptionPane.YES_OPTION) {
                    success = app.getWorldManager().saveWorld();
                    if (success) {
                        this.exitEditor();
                    }
                } else if (status == JOptionPane.NO_OPTION) {
                    app.getWorldManager().setDirty(false);
                    this.exitEditor();
                }
            } else {
                this.exitEditor();
            }
        } catch (final Exception ex) {
            Worldz.getDebug().debug(ex);
        }
    }

    private class EventHandler
            implements AdjustmentListener, MouseListener, WindowListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final WorldEditor me = WorldEditor.this;
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
                Worldz.getDebug().debug(ex);
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
                final WorldEditor me = WorldEditor.this;
                final int x = e.getX();
                final int y = e.getY();
                if (me.scriptMode) {
                    if (e.isAltDown()) {
                        me.deleteScript(x, y);
                    } else if (e.isShiftDown()) {
                        me.probeScript(x, y);
                    } else {
                        me.editScript(x, y);
                    }
                } else {
                    if (e.isAltDown()) {
                        me.editObjectProperties(x, y);
                    } else if (e.isShiftDown()) {
                        me.probeObjectProperties(x, y);
                    } else {
                        me.editObject(x, y);
                    }
                }
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
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
            WorldEditor.this.handleCloseWindow();
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

    private class TreasureEventHandler implements WindowListener {
        public TreasureEventHandler() {
            // TODO Auto-generated constructor stub
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
            WorldEditor.this.setTreasureChestContents();
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

    private class StartEventHandler
            implements AdjustmentListener, MouseListener {
        public StartEventHandler() {
            // TODO Auto-generated constructor stub
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final WorldEditor me = WorldEditor.this;
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
                Worldz.getDebug().debug(ex);
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
                WorldEditor.this.setPlayerLocation(x, y);
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
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

    private class TeleportEventHandler
            implements AdjustmentListener, MouseListener {
        public TeleportEventHandler() {
            // TODO Auto-generated constructor stub
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final WorldEditor me = WorldEditor.this;
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
                Worldz.getDebug().debug(ex);
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
                WorldEditor.this.setTeleportDestination(x, y);
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
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

    private class MetalButtonEventHandler
            implements AdjustmentListener, MouseListener {
        public MetalButtonEventHandler() {
            // TODO Auto-generated constructor stub
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final WorldEditor me = WorldEditor.this;
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
                Worldz.getDebug().debug(ex);
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
                WorldEditor.this.setMetalButtonTarget(x, y);
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
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
