/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.editor;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DrawGrid;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.objects.EmptyVoid;
import com.puttysoftware.dungeondiver4.dungeon.objects.Player;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectList;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;
import com.puttysoftware.dungeondiver4.resourcemanagers.ImageTransformer;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageManager;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.picturepicker.PicturePicker;

class DungeonEditorGUI {
    // Fields
    private JFrame outputFrame;
    private final JFrame treasureFrame;
    private Container outputPane, borderPane;
    private final Container treasurePane;
    private JLabel messageLabel;
    private JScrollBar vertScroll, horzScroll;
    private final EventHandler mhandler;
    private final StartEventHandler shandler;
    private final TeleportEventHandler thandler;
    private final TreasureEventHandler rhandler;
    private final MetalButtonEventHandler mbhandler;
    private final ConditionalTeleportEventHandler cthandler;
    private final LevelPreferencesManager lPrefs;
    private final DungeonPreferencesManager mPrefs;
    private PicturePicker picker;
    private final PicturePicker treasurePicker;
    private final String[] groundNames;
    private final String[] objectNames;
    private final BufferedImageIcon[] groundEditorAppearances;
    private final BufferedImageIcon[] objectEditorAppearances;
    private final String[] containableNames;
    private final BufferedImageIcon[] containableEditorAppearances;
    private final EditorLocationManager elMgr;
    EditorViewingWindowManager evMgr;
    private DrawGrid drawGrid;
    private EditorDraw secondaryPane;
    private static EmptyVoid EMPTY_VOID = new EmptyVoid();

    DungeonEditorGUI() {
        this.lPrefs = new LevelPreferencesManager();
        this.mPrefs = new DungeonPreferencesManager();
        this.mhandler = new EventHandler();
        this.shandler = new StartEventHandler();
        this.thandler = new TeleportEventHandler();
        this.rhandler = new TreasureEventHandler();
        this.mbhandler = new MetalButtonEventHandler();
        this.cthandler = new ConditionalTeleportEventHandler();
        final DungeonObjectList objectList = DungeonDiver4.getApplication()
                .getObjects();
        this.groundNames = objectList.getAllGroundLayerNames();
        this.objectNames = objectList.getAllObjectLayerNames();
        this.groundEditorAppearances = objectList
                .getAllGroundLayerEditorAppearances();
        this.objectEditorAppearances = objectList
                .getAllObjectLayerEditorAppearances();
        this.elMgr = new EditorLocationManager();
        this.evMgr = new EditorViewingWindowManager();
        // Set up treasure picker
        this.containableNames = objectList.getAllContainableNames();
        this.containableEditorAppearances = objectList
                .getAllContainableObjectEditorAppearances();
        this.treasureFrame = new JFrame("Treasure Chest Contents");
        final Image iconlogo = DungeonDiver4.getApplication().getIconLogo();
        this.treasureFrame.setIconImage(iconlogo);
        this.treasureFrame
                .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.treasurePicker = new PicturePicker(
                this.containableEditorAppearances, this.containableNames,
                new Color(223, 223, 223));
        this.treasurePicker.changePickerColor(new Color(223, 223, 223));
        this.treasurePicker
                .setPickerDimensions(ImageTransformer.MAX_WINDOW_SIZE);
        this.treasurePane = this.treasurePicker.getPicker();
        this.treasureFrame.setContentPane(this.treasurePane);
        this.treasureFrame.addWindowListener(this.rhandler);
        this.treasureFrame.pack();
    }

    int[] computeGridValues(final int x, final int y) {
        int gridX, gridY;
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        gridX = x / ImageTransformer.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        gridY = y / ImageTransformer.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        return new int[] { gridX, gridY };
    }

    int getObjectPicked() {
        return this.picker.getPicked();
    }

    int getTreasurePicked() {
        return this.treasurePicker.getPicked();
    }

    void setTreasurePicked(final int index) {
        this.treasurePicker.selectLastPickedChoice(index);
    }

    void showTreasurePicker() {
        this.treasureFrame.setVisible(true);
    }

    void swapToTeleportHandler() {
        this.horzScroll.removeAdjustmentListener(this.mhandler);
        this.vertScroll.removeAdjustmentListener(this.mhandler);
        this.secondaryPane.removeMouseListener(this.mhandler);
        this.horzScroll.addAdjustmentListener(this.thandler);
        this.vertScroll.addAdjustmentListener(this.thandler);
        this.secondaryPane.addMouseListener(this.thandler);
    }

    void swapFromTeleportHandler() {
        this.horzScroll.removeAdjustmentListener(this.thandler);
        this.vertScroll.removeAdjustmentListener(this.thandler);
        this.secondaryPane.removeMouseListener(this.thandler);
        this.horzScroll.addAdjustmentListener(this.mhandler);
        this.vertScroll.addAdjustmentListener(this.mhandler);
        this.secondaryPane.addMouseListener(this.mhandler);
    }

    void swapToConditionalTeleportHandler() {
        this.horzScroll.removeAdjustmentListener(this.mhandler);
        this.vertScroll.removeAdjustmentListener(this.mhandler);
        this.secondaryPane.removeMouseListener(this.mhandler);
        this.horzScroll.addAdjustmentListener(this.cthandler);
        this.vertScroll.addAdjustmentListener(this.cthandler);
        this.secondaryPane.addMouseListener(this.cthandler);
    }

    void swapFromConditionalTeleportHandler() {
        this.horzScroll.removeAdjustmentListener(this.cthandler);
        this.vertScroll.removeAdjustmentListener(this.cthandler);
        this.secondaryPane.removeMouseListener(this.cthandler);
        this.horzScroll.addAdjustmentListener(this.mhandler);
        this.vertScroll.addAdjustmentListener(this.mhandler);
        this.secondaryPane.addMouseListener(this.mhandler);
    }

    void swapToStartHandler() {
        this.horzScroll.removeAdjustmentListener(this.mhandler);
        this.vertScroll.removeAdjustmentListener(this.mhandler);
        this.secondaryPane.removeMouseListener(this.mhandler);
        this.horzScroll.addAdjustmentListener(this.shandler);
        this.vertScroll.addAdjustmentListener(this.shandler);
        this.secondaryPane.addMouseListener(this.shandler);
    }

    void swapFromStartHandler() {
        this.horzScroll.removeAdjustmentListener(this.shandler);
        this.vertScroll.removeAdjustmentListener(this.shandler);
        this.secondaryPane.removeMouseListener(this.shandler);
        this.horzScroll.addAdjustmentListener(this.mhandler);
        this.vertScroll.addAdjustmentListener(this.mhandler);
        this.secondaryPane.addMouseListener(this.mhandler);
    }

    void swapToMetalButtonHandler() {
        this.horzScroll.removeAdjustmentListener(this.mhandler);
        this.vertScroll.removeAdjustmentListener(this.mhandler);
        this.secondaryPane.removeMouseListener(this.mhandler);
        this.horzScroll.addAdjustmentListener(this.mbhandler);
        this.vertScroll.addAdjustmentListener(this.mbhandler);
        this.secondaryPane.addMouseListener(this.mbhandler);
    }

    void swapFromMetalButtonHandler() {
        this.horzScroll.removeAdjustmentListener(this.mbhandler);
        this.vertScroll.removeAdjustmentListener(this.mbhandler);
        this.secondaryPane.removeMouseListener(this.mbhandler);
        this.horzScroll.addAdjustmentListener(this.mhandler);
        this.vertScroll.addAdjustmentListener(this.mhandler);
        this.secondaryPane.addMouseListener(this.mhandler);
    }

    void viewingWindowSizeChanged() {
        if (this.outputFrame != null) {
            this.fixLimits();
            this.setUpGUI();
            this.redrawEditor();
        }
    }

    EditorLocationManager getLocationManager() {
        return this.elMgr;
    }

    EditorViewingWindowManager getViewManager() {
        return this.evMgr;
    }

    void updateEditorPosition(final int x, final int y, final int z,
            final int w, final UndoRedoEngine engine) {
        this.elMgr.offsetEditorLocationW(w);
        this.evMgr.offsetViewingWindowLocationX(x);
        this.evMgr.offsetViewingWindowLocationY(y);
        this.elMgr.offsetEditorLocationZ(z);
        if (w != 0) {
            // Level Change
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .switchLevelOffset(w);
            this.fixLimits();
            this.setUpGUI();
        }
        this.checkMenus(engine);
        this.redrawEditor();
    }

    void updateEditorPositionAbsolute(final int x, final int y, final int z,
            final int w, final UndoRedoEngine engine) {
        final int oldW = this.elMgr.getEditorLocationW();
        this.elMgr.setEditorLocationW(w);
        this.evMgr.setViewingWindowCenterX(y);
        this.evMgr.setViewingWindowCenterY(x);
        this.elMgr.setEditorLocationZ(z);
        if (w != oldW) {
            // Level Change
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .switchLevelOffset(w);
            this.fixLimits();
            this.setUpGUI();
        }
        this.checkMenus(engine);
        this.redrawEditor();
    }

    void updateEditorLevelAbsolute(final int w, final UndoRedoEngine engine) {
        this.elMgr.setEditorLocationW(w);
        // Level Change
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .switchLevel(w);
        this.fixLimits();
        this.setUpGUI();
        this.checkMenus(engine);
        this.redrawEditor();
    }

    void checkMenus(final UndoRedoEngine engine) {
        final Application app = DungeonDiver4.getApplication();
        if (app.getMode() == Application.STATUS_EDITOR) {
            final Dungeon m = app.getDungeonManager().getDungeon();
            if (m.getLevels() == Dungeon.getMinLevels()) {
                app.getMenuManager().disableRemoveLevel();
            } else {
                app.getMenuManager().enableRemoveLevel();
            }
            if (m.getLevels() == Dungeon.getMaxLevels()) {
                app.getMenuManager().disableAddLevel();
            } else {
                app.getMenuManager().enableAddLevel();
            }
            try {
                if (app.getDungeonManager().getDungeon()
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
                if (app.getDungeonManager().getDungeon()
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
                        .getEditorLocationE() != DungeonConstants.LAYER_GROUND) {
                    app.getMenuManager().enableSetStartPoint();
                } else {
                    app.getMenuManager().disableSetStartPoint();
                }
            } else {
                app.getMenuManager().disableSetStartPoint();
            }
            if (!engine.tryUndo()) {
                app.getMenuManager().disableUndo();
            } else {
                app.getMenuManager().enableUndo();
            }
            if (!engine.tryRedo()) {
                app.getMenuManager().disableRedo();
            } else {
                app.getMenuManager().enableRedo();
            }
            if (engine.tryBoth()) {
                app.getMenuManager().disableClearHistory();
            } else {
                app.getMenuManager().enableClearHistory();
            }
        }
    }

    void toggleLayer(final UndoRedoEngine engine) {
        if (this.elMgr.getEditorLocationE() == DungeonConstants.LAYER_GROUND) {
            this.elMgr.setEditorLocationE(DungeonConstants.LAYER_OBJECT);
        } else {
            this.elMgr.setEditorLocationE(DungeonConstants.LAYER_GROUND);
        }
        this.updatePicker();
        this.redrawEditor();
        this.checkMenus(engine);
    }

    void setDungeonPrefs() {
        this.mPrefs.showPrefs();
    }

    void setLevelPrefs() {
        this.lPrefs.showPrefs();
    }

    void redrawEditor() {
        if (this.outputFrame.isVisible()) {
            if (this.elMgr
                    .getEditorLocationE() == DungeonConstants.LAYER_GROUND) {
                this.redrawGround();
            } else {
                this.redrawGroundAndObjects();
            }
        }
    }

    private void redrawGround() {
        // Draw the dungeon in edit mode
        final Application app = DungeonDiver4.getApplication();
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
                    final AbstractDungeonObject obj1 = app.getDungeonManager()
                            .getDungeon()
                            .getCell(y, x, this.elMgr.getEditorLocationZ(),
                                    DungeonConstants.LAYER_GROUND)
                            .editorRenderHook(y, x,
                                    this.elMgr.getEditorLocationZ());
                    this.drawGrid.setImageCell(
                            ObjectImageManager.getImage(obj1.getName(),
                                    obj1.getBaseID(), obj1.getTemplateColor(),
                                    obj1.getAttributeID(),
                                    obj1.getAttributeTemplateColor()),
                            xFix, yFix);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.drawGrid.setImageCell(ObjectImageManager.getImage(
                            DungeonEditorGUI.EMPTY_VOID.getName(),
                            DungeonEditorGUI.EMPTY_VOID.getBaseID(),
                            ColorConstants.COLOR_NONE,
                            DungeonEditorGUI.EMPTY_VOID.getAttributeID(),
                            ColorConstants.COLOR_NONE), xFix, yFix);
                } catch (final NullPointerException np) {
                    this.drawGrid.setImageCell(ObjectImageManager.getImage(
                            DungeonEditorGUI.EMPTY_VOID.getName(),
                            DungeonEditorGUI.EMPTY_VOID.getBaseID(),
                            ColorConstants.COLOR_NONE,
                            DungeonEditorGUI.EMPTY_VOID.getAttributeID(),
                            ColorConstants.COLOR_NONE), xFix, yFix);
                }
            }
        }
        this.outputFrame.pack();
        this.outputFrame.setTitle("Editor (Ground Layer) - Floor "
                + (this.elMgr.getEditorLocationZ() + 1) + " Level " + (w + 1));
        this.secondaryPane.repaint();
        this.showOutput();
    }

    private void redrawGroundAndObjects() {
        // Draw the dungeon in edit mode
        final Application app = DungeonDiver4.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
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
                    final AbstractDungeonObject obj1 = m
                            .getCell(y, x, this.elMgr.getEditorLocationZ(),
                                    DungeonConstants.LAYER_GROUND)
                            .editorRenderHook(y, x,
                                    this.elMgr.getEditorLocationZ());
                    final AbstractDungeonObject obj2 = m
                            .getCell(y, x, this.elMgr.getEditorLocationZ(),
                                    DungeonConstants.LAYER_OBJECT)
                            .editorRenderHook(y, x,
                                    this.elMgr.getEditorLocationZ());
                    final BufferedImageIcon img1 = ObjectImageManager.getImage(
                            obj1.getName(), obj1.getBaseID(),
                            obj1.getTemplateColor(), obj1.getAttributeID(),
                            obj1.getAttributeTemplateColor());
                    final BufferedImageIcon img2 = ObjectImageManager.getImage(
                            obj2.getName(), obj2.getBaseID(),
                            obj2.getTemplateColor(), obj2.getAttributeID(),
                            obj2.getAttributeTemplateColor());
                    if (u == y && v == x) {
                        final AbstractDungeonObject obj3 = new Player()
                                .editorRenderHook(y, x,
                                        this.elMgr.getEditorLocationZ());
                        final BufferedImageIcon img3 = ObjectImageManager
                                .getImage(obj3.getName(), obj3.getBaseID(),
                                        obj3.getTemplateColor(),
                                        obj3.getAttributeID(),
                                        obj3.getAttributeTemplateColor());
                        this.drawGrid.setImageCell(ImageTransformer
                                .getVirtualCompositeImage(img1, img2, img3),
                                xFix, yFix);
                    } else {
                        this.drawGrid.setImageCell(
                                ImageTransformer.getCompositeImage(img1, img2),
                                xFix, yFix);
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.drawGrid.setImageCell(ObjectImageManager.getImage(
                            DungeonEditorGUI.EMPTY_VOID.getName(),
                            DungeonEditorGUI.EMPTY_VOID.getBaseID(),
                            ColorConstants.COLOR_NONE,
                            DungeonEditorGUI.EMPTY_VOID.getAttributeID(),
                            ColorConstants.COLOR_NONE), xFix, yFix);
                } catch (final NullPointerException np) {
                    this.drawGrid.setImageCell(ObjectImageManager.getImage(
                            DungeonEditorGUI.EMPTY_VOID.getName(),
                            DungeonEditorGUI.EMPTY_VOID.getBaseID(),
                            ColorConstants.COLOR_NONE,
                            DungeonEditorGUI.EMPTY_VOID.getAttributeID(),
                            ColorConstants.COLOR_NONE), xFix, yFix);
                }
            }
        }
        this.outputFrame.pack();
        this.outputFrame.setTitle("Editor (Object Layer) - Floor "
                + (this.elMgr.getEditorLocationZ() + 1) + " Level " + (w + 1));
        this.secondaryPane.repaint();
        this.showOutput();
    }

    void redrawVirtual(final int x, final int y,
            final AbstractDungeonObject obj3) {
        // Draw the square
        final Application app = DungeonDiver4.getApplication();
        int xFix, yFix;
        xFix = y - this.evMgr.getViewingWindowLocationX();
        yFix = x - this.evMgr.getViewingWindowLocationY();
        try {
            final AbstractDungeonObject obj1 = app.getDungeonManager()
                    .getDungeon()
                    .getCell(y, x, this.elMgr.getEditorLocationZ(),
                            DungeonConstants.LAYER_GROUND)
                    .editorRenderHook(y, x, this.elMgr.getEditorLocationZ());
            final AbstractDungeonObject obj2 = app.getDungeonManager()
                    .getDungeon()
                    .getCell(y, x, this.elMgr.getEditorLocationZ(),
                            DungeonConstants.LAYER_OBJECT)
                    .editorRenderHook(y, x, this.elMgr.getEditorLocationZ());
            final BufferedImageIcon img1 = ObjectImageManager.getImage(
                    obj1.getName(), obj1.getBaseID(), obj1.getTemplateColor(),
                    obj1.getAttributeID(), obj1.getAttributeTemplateColor());
            final BufferedImageIcon img2 = ObjectImageManager.getImage(
                    obj2.getName(), obj2.getBaseID(), obj2.getTemplateColor(),
                    obj2.getAttributeID(), obj2.getAttributeTemplateColor());
            final AbstractDungeonObject obj4 = obj3.editorRenderHook(y, x,
                    this.elMgr.getEditorLocationZ());
            final BufferedImageIcon img3 = ObjectImageManager.getImage(
                    obj4.getName(), obj4.getBaseID(), obj4.getTemplateColor(),
                    obj4.getAttributeID(), obj4.getAttributeTemplateColor());
            this.drawGrid.setImageCell(
                    ImageTransformer.getVirtualCompositeImage(img1, img2, img3),
                    xFix, yFix);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Do nothing
        } catch (final NullPointerException np) {
            // Do nothing
        }
        this.secondaryPane.repaint();
        this.outputFrame.pack();
    }

    void probeObjectProperties(final int x, final int y) {
        final Application app = DungeonDiver4.getApplication();
        final int xOffset = this.vertScroll.getValue()
                - this.vertScroll.getMinimum();
        final int yOffset = this.horzScroll.getValue()
                - this.horzScroll.getMinimum();
        final int gridX = x / ImageTransformer.getGraphicSize()
                + this.evMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int gridY = y / ImageTransformer.getGraphicSize()
                + this.evMgr.getViewingWindowLocationY() + xOffset - yOffset;
        try {
            final AbstractDungeonObject mo = app.getDungeonManager()
                    .getDungeon().getCell(gridX, gridY,
                            this.elMgr.getEditorLocationZ(),
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

    void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
    }

    void fixLimits() {
        // Fix limits
        final Application app = DungeonDiver4.getApplication();
        if (app.getDungeonManager().getDungeon() != null && this.elMgr != null
                && this.evMgr != null) {
            this.elMgr
                    .setLimitsFromDungeon(app.getDungeonManager().getDungeon());
            this.evMgr.halfOffsetMaximumViewingWindowLocationsFromDungeon(
                    app.getDungeonManager().getDungeon());
        }
    }

    void showOutput() {
        final Application app = DungeonDiver4.getApplication();
        this.outputFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
        app.getMenuManager().setEditorMenus();
        this.outputFrame.setVisible(true);
        this.outputFrame.pack();
    }

    void hideOutput() {
        if (this.outputFrame != null) {
            this.outputFrame.setVisible(false);
        }
    }

    void disableOutput() {
        this.outputFrame.setEnabled(false);
    }

    void enableOutput(final UndoRedoEngine engine) {
        this.outputFrame.setEnabled(true);
        this.checkMenus(engine);
    }

    JFrame getOutputFrame() {
        if (this.outputFrame != null && this.outputFrame.isVisible()) {
            return this.outputFrame;
        } else {
            return null;
        }
    }

    void setUpGUI() {
        // Destroy the old GUI, if one exists
        if (this.outputFrame != null) {
            this.outputFrame.dispose();
        }
        this.messageLabel = new JLabel(" ");
        final Application app = DungeonDiver4.getApplication();
        this.outputFrame = new JFrame("Editor");
        final Image iconlogo = app.getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.outputPane = new Container();
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.drawGrid = new DrawGrid(PreferencesManager.getViewingWindowSize());
        this.secondaryPane = new EditorDraw(this.drawGrid);
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        final GridBagLayout gridbag = new GridBagLayout();
        final GridBagConstraints c = new GridBagConstraints();
        this.outputPane.setLayout(gridbag);
        this.outputFrame.setResizable(false);
        c.fill = GridBagConstraints.BOTH;
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

    void setUpBorderPane() {
        // Make sure message area is attached to border pane
        this.borderPane.removeAll();
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.picker.getPicker(), BorderLayout.EAST);
    }

    void updatePicker() {
        if (this.elMgr != null) {
            BufferedImageIcon[] newImages = null;
            String[] newNames = null;
            if (this.elMgr
                    .getEditorLocationE() == DungeonConstants.LAYER_GROUND) {
                newImages = this.groundEditorAppearances;
                newNames = this.groundNames;
            } else {
                newImages = this.objectEditorAppearances;
                newNames = this.objectNames;
            }
            if (this.picker != null) {
                this.picker.updatePicker(newImages, newNames);
            } else {
                this.picker = new PicturePicker(newImages, newNames,
                        new Color(223, 223, 223));
                this.picker.changePickerColor(new Color(223, 223, 223));
            }
            this.picker.setPickerDimensions(this.outputPane.getHeight());
        }
    }

    void handleCloseWindow() {
        try {
            final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                    .getEditor();
            final Application app = DungeonDiver4.getApplication();
            boolean success = false;
            int status = JOptionPane.DEFAULT_OPTION;
            if (app.getDungeonManager().getDirty()) {
                status = app.getDungeonManager().showSaveDialog();
                if (status == JOptionPane.YES_OPTION) {
                    success = app.getDungeonManager().saveDungeon();
                    if (success) {
                        mel.exitEditor();
                    }
                } else if (status == JOptionPane.NO_OPTION) {
                    app.getDungeonManager().setDirty(false);
                    mel.exitEditor();
                }
            } else {
                mel.exitEditor();
            }
        } catch (final Exception ex) {
            DungeonDiver4.getErrorLogger().logError(ex);
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
                final DungeonEditorGUI meg = DungeonEditorGUI.this;
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final Adjustable src = e.getAdjustable();
                final int dir = src.getOrientation();
                final int value = src.getValue();
                int relValue = 0;
                switch (dir) {
                case Adjustable.HORIZONTAL:
                    relValue = value - meg.evMgr.getViewingWindowLocationY();
                    mel.updateEditorPosition(0, relValue, 0, 0);
                    break;
                case Adjustable.VERTICAL:
                    relValue = value - meg.evMgr.getViewingWindowLocationX();
                    mel.updateEditorPosition(relValue, 0, 0, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
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
                final DungeonEditorGUI meg = DungeonEditorGUI.this;
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final int x = e.getX();
                final int y = e.getY();
                if (e.isAltDown()) {
                    if (!mel.inGoToDestMode()) {
                        mel.editObjectProperties(x, y);
                    }
                } else if (e.isShiftDown()) {
                    meg.probeObjectProperties(x, y);
                } else {
                    if (mel.inGoToDestMode()) {
                        mel.goToDestination(x, y);
                    } else {
                        mel.editObject(x, y, false);
                    }
                }
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
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
            DungeonEditorGUI.this.handleCloseWindow();
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
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final int x = e.getX();
                final int y = e.getY();
                mel.editObject(x, y, false);
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void mouseMoved(final MouseEvent e) {
            // Do nothing
        }
    }

    private class StartEventHandler
            implements AdjustmentListener, MouseListener {
        StartEventHandler() {
            // Do nothing
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final DungeonEditorGUI meg = DungeonEditorGUI.this;
                final Adjustable src = e.getAdjustable();
                final int dir = src.getOrientation();
                final int value = src.getValue();
                int relValue = 0;
                switch (dir) {
                case Adjustable.HORIZONTAL:
                    relValue = value - meg.evMgr.getViewingWindowLocationY();
                    mel.updateEditorPosition(0, relValue, 0, 0);
                    break;
                case Adjustable.VERTICAL:
                    relValue = value - meg.evMgr.getViewingWindowLocationX();
                    mel.updateEditorPosition(relValue, 0, 0, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
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
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final int x = e.getX();
                final int y = e.getY();
                mel.setPlayerLocation(x, y);
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
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
        TeleportEventHandler() {
            // Do nothing
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final DungeonEditorGUI meg = DungeonEditorGUI.this;
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final Adjustable src = e.getAdjustable();
                final int dir = src.getOrientation();
                final int value = src.getValue();
                int relValue = 0;
                switch (dir) {
                case Adjustable.HORIZONTAL:
                    relValue = value - meg.evMgr.getViewingWindowLocationY();
                    mel.updateEditorPosition(0, relValue, 0, 0);
                    break;
                case Adjustable.VERTICAL:
                    relValue = value - meg.evMgr.getViewingWindowLocationX();
                    mel.updateEditorPosition(relValue, 0, 0, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
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
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final int x = e.getX();
                final int y = e.getY();
                mel.setTeleportDestination(x, y);
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
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

    private class ConditionalTeleportEventHandler
            implements AdjustmentListener, MouseListener {
        ConditionalTeleportEventHandler() {
            // Do nothing
        }

        // handle scroll bars
        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final DungeonEditorGUI meg = DungeonEditorGUI.this;
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final Adjustable src = e.getAdjustable();
                final int dir = src.getOrientation();
                final int value = src.getValue();
                int relValue = 0;
                switch (dir) {
                case Adjustable.HORIZONTAL:
                    relValue = value - meg.evMgr.getViewingWindowLocationY();
                    mel.updateEditorPosition(0, relValue, 0, 0);
                    break;
                case Adjustable.VERTICAL:
                    relValue = value - meg.evMgr.getViewingWindowLocationX();
                    mel.updateEditorPosition(relValue, 0, 0, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
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
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final int x = e.getX();
                final int y = e.getY();
                mel.setConditionalTeleportDestination(x, y);
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
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
            final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                    .getEditor();
            mel.setTreasureChestContents();
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

    private class MetalButtonEventHandler
            implements AdjustmentListener, MouseListener {
        // handle scroll bars
        public MetalButtonEventHandler() {
            // Do nothing
        }

        @Override
        public void adjustmentValueChanged(final AdjustmentEvent e) {
            try {
                final DungeonEditorGUI meg = DungeonEditorGUI.this;
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final Adjustable src = e.getAdjustable();
                final int dir = src.getOrientation();
                final int value = src.getValue();
                int relValue = 0;
                switch (dir) {
                case Adjustable.HORIZONTAL:
                    relValue = value - meg.evMgr.getViewingWindowLocationY();
                    mel.updateEditorPosition(0, relValue, 0, 0);
                    break;
                case Adjustable.VERTICAL:
                    relValue = value - meg.evMgr.getViewingWindowLocationX();
                    mel.updateEditorPosition(relValue, 0, 0, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
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
                final DungeonEditorLogic mel = DungeonDiver4.getApplication()
                        .getEditor();
                final int x = e.getX();
                final int y = e.getY();
                mel.setMetalButtonTarget(x, y);
            } catch (final Exception ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
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
