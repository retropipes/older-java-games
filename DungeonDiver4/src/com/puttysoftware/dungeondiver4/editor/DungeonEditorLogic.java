/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.editor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.DungeonManager;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractCheckpoint;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractConditionalTeleport;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.BlockTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.ChainTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.Destination;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.objects.EmptyVoid;
import com.puttysoftware.dungeondiver4.dungeon.objects.GeneratedEdge;
import com.puttysoftware.dungeondiver4.dungeon.objects.InvisibleBlockTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.InvisibleChainTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.InvisibleOneShotChainTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.InvisibleOneShotTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.InvisibleTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.MetalButton;
import com.puttysoftware.dungeondiver4.dungeon.objects.NWayTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.OneShotChainTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.OneShotTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.RandomInvisibleOneShotTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.RandomInvisibleTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.RandomOneShotTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.RandomTeleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.StairsDown;
import com.puttysoftware.dungeondiver4.dungeon.objects.StairsUp;
import com.puttysoftware.dungeondiver4.dungeon.objects.Teleport;
import com.puttysoftware.dungeondiver4.dungeon.objects.TreasureChest;
import com.puttysoftware.dungeondiver4.dungeon.objects.TwoWayTeleport;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectList;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.game.GameLogicManager;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;

public class DungeonEditorLogic {
    // Fields
    private final DungeonEditorGUI meg;
    private AbstractDungeonObject savedDungeonObject;
    private final AbstractDungeonObject[] groundObjects;
    private final AbstractDungeonObject[] objectObjects;
    private final AbstractDungeonObject[] containableObjects;
    private int TELEPORT_TYPE;
    private AbstractConditionalTeleport instanceBeingEdited;
    private int conditionalEditFlag;
    private int currentObjectIndex;
    private boolean dungeonChanged;
    boolean goToDestMode;
    private UndoRedoEngine engine;
    private NWayTeleport nWayEdited;
    private int nWayDestID;
    private int nWayDestCount;
    private static final int CEF_DEST1 = 1;
    private static final int CEF_DEST2 = 2;
    private static final int CEF_CONDITION = 3;
    private static final int CEF_TRIGGER_TYPE = 4;
    private static String[] conditionalChoices = new String[] {
            "Edit Destination 1", "Edit Destination 2",
            "Edit Condition Trigger", "Edit Trigger Type" };
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
    public static final int TELEPORT_TYPE_N_WAY = 15;
    public static final int STAIRS_UP = 0;
    public static final int STAIRS_DOWN = 1;

    public DungeonEditorLogic() {
        this.meg = new DungeonEditorGUI();
        this.savedDungeonObject = new Empty();
        this.engine = new UndoRedoEngine();
        final DungeonObjectList objectList = DungeonDiver4.getApplication()
                .getObjects();
        this.groundObjects = objectList.getAllGroundLayerObjects();
        this.objectObjects = objectList.getAllObjectLayerObjects();
        this.containableObjects = objectList.getAllContainableObjects();
        this.dungeonChanged = true;
        this.goToDestMode = false;
        this.instanceBeingEdited = null;
        this.nWayDestID = 0;
    }

    public void setNWayDestCount(final int value) {
        this.nWayDestCount = value;
    }

    public void setNWayEdited(final NWayTeleport nwt) {
        this.nWayEdited = nwt;
    }

    public void dungeonChanged() {
        this.dungeonChanged = true;
    }

    public boolean inGoToDestMode() {
        return this.goToDestMode;
    }

    public void viewingWindowSizeChanged() {
        this.meg.viewingWindowSizeChanged();
    }

    public EditorLocationManager getLocationManager() {
        return this.meg.getLocationManager();
    }

    private EditorViewingWindowManager getViewManager() {
        return this.meg.getViewManager();
    }

    public void updateEditorPosition(final int x, final int y, final int z,
            final int w) {
        this.meg.updateEditorPosition(x, y, z, w, this.engine);
    }

    public void updateEditorPositionAbsolute(final int x, final int y,
            final int z, final int w) {
        this.meg.updateEditorPositionAbsolute(x, y, z, w, this.engine);
    }

    public void updateEditorLevelAbsolute(final int w) {
        this.meg.updateEditorLevelAbsolute(w, this.engine);
    }

    private void checkMenus() {
        this.meg.checkMenus(this.engine);
    }

    public void toggleLayer() {
        this.meg.toggleLayer(this.engine);
    }

    public void setDungeonPrefs() {
        this.meg.setDungeonPrefs();
    }

    public void setLevelPrefs() {
        this.meg.setLevelPrefs();
    }

    public void redrawEditor() {
        this.meg.redrawEditor();
    }

    public void editObject(final int x, final int y, final boolean nested) {
        final Application app = DungeonDiver4.getApplication();
        int gridX, gridY;
        AbstractDungeonObject mo;
        if (!nested) {
            this.currentObjectIndex = this.meg.getObjectPicked();
            final int[] grid = this.meg.computeGridValues(x, y);
            gridX = grid[0];
            gridY = grid[1];
            try {
                this.savedDungeonObject = app.getDungeonManager().getDungeon()
                        .getCell(gridX, gridY,
                                this.getLocationManager().getEditorLocationZ(),
                                this.getLocationManager().getEditorLocationE());
            } catch (final ArrayIndexOutOfBoundsException ae) {
                return;
            }
            AbstractDungeonObject[] objectChoices = null;
            if (this.getLocationManager()
                    .getEditorLocationE() == DungeonConstants.LAYER_GROUND) {
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
        final AbstractDungeonObject instance = app.getObjects()
                .getNewInstanceByName(mo.getName());
        this.getLocationManager().setEditorLocationX(gridX);
        this.getLocationManager().setEditorLocationY(gridY);
        mo.editorPlaceHook();
        // Special handling for N-Way Teleports
        if (instance instanceof NWayTeleport) {
            final NWayTeleport nwt = (NWayTeleport) instance;
            nwt.setDestinationCount(this.nWayDestCount);
        }
        try {
            this.checkTwoWayTeleportPair(
                    this.getLocationManager().getEditorLocationZ());
            this.updateUndoHistory(this.savedDungeonObject, gridX, gridY,
                    this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationW(),
                    this.getLocationManager().getEditorLocationE());
            app.getDungeonManager().getDungeon().setCell(instance, gridX, gridY,
                    this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationE());
            this.checkStairPair(this.getLocationManager().getEditorLocationZ());
            if (PreferencesManager.getEditorAutoEdge() && !nested) {
                this.autoGenerateTransitions(instance, gridX, gridY);
            }
            app.getDungeonManager().setDirty(true);
            this.checkMenus();
            this.redrawEditor();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            app.getDungeonManager().getDungeon().setCell(
                    this.savedDungeonObject, gridX, gridY,
                    this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationE());
            this.redrawEditor();
        }
    }

    private void autoGenerateTransitions(final AbstractDungeonObject instance,
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

    private void autoGenerateTransitions1(final AbstractDungeonObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        AbstractDungeonObject obj1;
        try {
            obj1 = app.getDungeonManager().getDungeon().getCell(gridX - 1,
                    gridY - 1, this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationE());
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
                        if (ge.getDirectionName()
                                .equals("Southeast Inverted")) {
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

    private void autoGenerateTransitions2(final AbstractDungeonObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        AbstractDungeonObject obj2;
        try {
            obj2 = app.getDungeonManager().getDungeon().getCell(gridX,
                    gridY - 1, this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationE());
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

    private void autoGenerateTransitions3(final AbstractDungeonObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        AbstractDungeonObject obj3;
        try {
            obj3 = app.getDungeonManager().getDungeon().getCell(gridX + 1,
                    gridY - 1, this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationE());
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
                        if (ge.getDirectionName()
                                .equals("Southwest Inverted")) {
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

    private void autoGenerateTransitions4(final AbstractDungeonObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        AbstractDungeonObject obj4;
        try {
            obj4 = app.getDungeonManager().getDungeon().getCell(gridX - 1,
                    gridY, this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationE());
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

    private void autoGenerateTransitions6(final AbstractDungeonObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        AbstractDungeonObject obj6;
        try {
            obj6 = app.getDungeonManager().getDungeon().getCell(gridX + 1,
                    gridY, this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationE());
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

    private void autoGenerateTransitions7(final AbstractDungeonObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        AbstractDungeonObject obj7;
        try {
            obj7 = app.getDungeonManager().getDungeon().getCell(gridX - 1,
                    gridY + 1, this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationE());
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
                        if (ge.getDirectionName()
                                .equals("Northeast Inverted")) {
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

    private void autoGenerateTransitions8(final AbstractDungeonObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        AbstractDungeonObject obj8;
        try {
            obj8 = app.getDungeonManager().getDungeon().getCell(gridX,
                    gridY + 1, this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationE());
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

    private void autoGenerateTransitions9(final AbstractDungeonObject instance,
            final int gridX, final int gridY) {
        // Generate transitions automatically
        if (!instance.isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
            return;
        }
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject[] generatedObjects = app.getObjects()
                .getAllGeneratedTypedObjects();
        final int generatedOffset = app.getObjects().getAllObjects().length
                - generatedObjects.length;
        AbstractDungeonObject obj9;
        try {
            obj9 = app.getDungeonManager().getDungeon().getCell(gridX + 1,
                    gridY + 1, this.getLocationManager().getEditorLocationZ(),
                    this.getLocationManager().getEditorLocationE());
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
                        if (ge.getDirectionName()
                                .equals("Northwest Inverted")) {
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

    public void editObjectProperties(final int x, final int y) {
        final Application app = DungeonDiver4.getApplication();
        final int[] grid = this.meg.computeGridValues(x, y);
        final int gridX = grid[0];
        final int gridY = grid[1];
        try {
            final AbstractDungeonObject mo = app.getDungeonManager()
                    .getDungeon().getCell(gridX, gridY,
                            this.getLocationManager().getEditorLocationZ(),
                            this.getLocationManager().getEditorLocationE());
            this.getLocationManager().setEditorLocationX(gridX);
            this.getLocationManager().setEditorLocationY(gridY);
            if (!mo.defersSetProperties()) {
                final AbstractDungeonObject mo2 = mo.editorPropertiesHook();
                if (mo2 == null) {
                    DungeonDiver4.getApplication()
                            .showMessage("This object has no properties");
                } else {
                    this.checkTwoWayTeleportPair(
                            this.getLocationManager().getEditorLocationZ());
                    this.updateUndoHistory(this.savedDungeonObject, gridX,
                            gridY,
                            this.getLocationManager().getEditorLocationZ(),
                            this.getLocationManager().getEditorLocationW(),
                            this.getLocationManager().getEditorLocationE());
                    app.getDungeonManager().getDungeon().setCell(mo2, gridX,
                            gridY,
                            this.getLocationManager().getEditorLocationZ(),
                            this.getLocationManager().getEditorLocationE());
                    this.checkStairPair(
                            this.getLocationManager().getEditorLocationZ());
                    this.checkMenus();
                    app.getDungeonManager().setDirty(true);
                }
            } else {
                mo.editorPropertiesHook();
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    public void setStatusMessage(final String msg) {
        this.meg.setStatusMessage(msg);
    }

    private void checkStairPair(final int z) {
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject mo1 = app.getDungeonManager().getDungeon()
                .getCell(this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(), z,
                        DungeonConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2, name3;
        try {
            final AbstractDungeonObject mo2 = app.getDungeonManager()
                    .getDungeon()
                    .getCell(this.getLocationManager().getEditorLocationX(),
                            this.getLocationManager().getEditorLocationY(),
                            z + 1, DungeonConstants.LAYER_OBJECT);
            name2 = mo2.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name2 = "";
        }
        try {
            final AbstractDungeonObject mo3 = app.getDungeonManager()
                    .getDungeon()
                    .getCell(this.getLocationManager().getEditorLocationX(),
                            this.getLocationManager().getEditorLocationY(),
                            z - 1, DungeonConstants.LAYER_OBJECT);
            name3 = mo3.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name3 = "";
        }
        if (!name1.equals("Stairs Up")) {
            if (name2.equals("Stairs Down")) {
                this.unpairStairs(DungeonEditorLogic.STAIRS_UP, z);
            } else if (!name1.equals("Stairs Down")) {
                if (name3.equals("Stairs Up")) {
                    this.unpairStairs(DungeonEditorLogic.STAIRS_DOWN, z);
                }
            }
        }
    }

    private void reverseCheckStairPair(final int z) {
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject mo1 = app.getDungeonManager().getDungeon()
                .getCell(this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(), z,
                        DungeonConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2, name3;
        try {
            final AbstractDungeonObject mo2 = app.getDungeonManager()
                    .getDungeon()
                    .getCell(this.getLocationManager().getEditorLocationX(),
                            this.getLocationManager().getEditorLocationY(),
                            z + 1, DungeonConstants.LAYER_OBJECT);
            name2 = mo2.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name2 = "";
        }
        try {
            final AbstractDungeonObject mo3 = app.getDungeonManager()
                    .getDungeon()
                    .getCell(this.getLocationManager().getEditorLocationX(),
                            this.getLocationManager().getEditorLocationY(),
                            z - 1, DungeonConstants.LAYER_OBJECT);
            name3 = mo3.getName();
        } catch (final ArrayIndexOutOfBoundsException e) {
            name3 = "";
        }
        if (name1.equals("Stairs Up")) {
            if (!name2.equals("Stairs Down")) {
                this.pairStairs(DungeonEditorLogic.STAIRS_UP, z);
            } else if (name1.equals("Stairs Down")) {
                if (!name3.equals("Stairs Up")) {
                    this.pairStairs(DungeonEditorLogic.STAIRS_DOWN, z);
                }
            }
        }
    }

    public void pairStairs(final int type) {
        final Application app = DungeonDiver4.getApplication();
        switch (type) {
            case STAIRS_UP:
                try {
                    app.getDungeonManager().getDungeon().setCell(new StairsDown(),
                            this.getLocationManager().getEditorLocationX(),
                            this.getLocationManager().getEditorLocationY(),
                            this.getLocationManager().getEditorLocationZ() + 1,
                            DungeonConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            case STAIRS_DOWN:
                try {
                    app.getDungeonManager().getDungeon().setCell(new StairsUp(),
                            this.getLocationManager().getEditorLocationX(),
                            this.getLocationManager().getEditorLocationY(),
                            this.getLocationManager().getEditorLocationZ() - 1,
                            DungeonConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            default:
                break;
        }
    }

    private void pairStairs(final int type, final int z) {
        final Application app = DungeonDiver4.getApplication();
        switch (type) {
            case STAIRS_UP:
                try {
                    app.getDungeonManager().getDungeon().setCell(new StairsDown(),
                            this.getLocationManager().getEditorLocationX(),
                            this.getLocationManager().getEditorLocationY(), z + 1,
                            DungeonConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            case STAIRS_DOWN:
                try {
                    app.getDungeonManager().getDungeon().setCell(new StairsUp(),
                            this.getLocationManager().getEditorLocationX(),
                            this.getLocationManager().getEditorLocationY(), z - 1,
                            DungeonConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            default:
                break;
        }
    }

    private void unpairStairs(final int type, final int z) {
        final Application app = DungeonDiver4.getApplication();
        switch (type) {
            case STAIRS_UP:
                try {
                    app.getDungeonManager().getDungeon().setCell(new Empty(),
                            this.getLocationManager().getEditorLocationX(),
                            this.getLocationManager().getEditorLocationY(), z + 1,
                            DungeonConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            case STAIRS_DOWN:
                try {
                    app.getDungeonManager().getDungeon().setCell(new Empty(),
                            this.getLocationManager().getEditorLocationX(),
                            this.getLocationManager().getEditorLocationY(), z - 1,
                            DungeonConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // Do nothing
                }
                break;
            default:
                break;
        }
    }

    private void checkTwoWayTeleportPair(final int z) {
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject mo1 = app.getDungeonManager().getDungeon()
                .getCell(this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(), z,
                        DungeonConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2;
        int destX, destY, destZ;
        if (name1.equals("Two-Way Teleport")) {
            final TwoWayTeleport twt = (TwoWayTeleport) mo1;
            destX = twt.getDestinationRow();
            destY = twt.getDestinationColumn();
            destZ = twt.getDestinationFloor();
            final AbstractDungeonObject mo2 = app.getDungeonManager()
                    .getDungeon().getCell(destX, destY, destZ,
                            DungeonConstants.LAYER_OBJECT);
            name2 = mo2.getName();
            if (name2.equals("Two-Way Teleport")) {
                DungeonEditorLogic.unpairTwoWayTeleport(destX, destY, destZ);
            }
        }
    }

    private void reverseCheckTwoWayTeleportPair(final int z) {
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject mo1 = app.getDungeonManager().getDungeon()
                .getCell(this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(), z,
                        DungeonConstants.LAYER_OBJECT);
        final String name1 = mo1.getName();
        String name2;
        int destX, destY, destZ;
        if (name1.equals("Two-Way Teleport")) {
            final TwoWayTeleport twt = (TwoWayTeleport) mo1;
            destX = twt.getDestinationRow();
            destY = twt.getDestinationColumn();
            destZ = twt.getDestinationFloor();
            final AbstractDungeonObject mo2 = app.getDungeonManager()
                    .getDungeon().getCell(destX, destY, destZ,
                            DungeonConstants.LAYER_OBJECT);
            name2 = mo2.getName();
            if (!name2.equals("Two-Way Teleport")) {
                this.pairTwoWayTeleport(destX, destY, destZ);
            }
        }
    }

    public void pairTwoWayTeleport(final int destX, final int destY,
            final int destZ) {
        final Application app = DungeonDiver4.getApplication();
        app.getDungeonManager().getDungeon().setCell(
                new TwoWayTeleport(
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ()),
                destX, destY, destZ, DungeonConstants.LAYER_OBJECT);
    }

    private static void unpairTwoWayTeleport(final int destX, final int destY,
            final int destZ) {
        final Application app = DungeonDiver4.getApplication();
        app.getDungeonManager().getDungeon().setCell(new Empty(), destX, destY,
                destZ, DungeonConstants.LAYER_OBJECT);
    }

    public void editCheckpointProperties(final AbstractCheckpoint checkpoint) {
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
            final AbstractConditionalTeleport instance) {
        final Application app = DungeonDiver4.getApplication();
        final String choice = CommonDialogs.showInputDialog("Edit What?",
                "Editor", DungeonEditorLogic.conditionalChoices,
                DungeonEditorLogic.conditionalChoices[0]);
        if (choice != null) {
            this.instanceBeingEdited = instance;
            this.conditionalEditFlag = 0;
            for (int x = 0; x < DungeonEditorLogic.conditionalChoices.length; x++) {
                if (DungeonEditorLogic.conditionalChoices[x].equals(choice)) {
                    this.conditionalEditFlag = x + 1;
                    break;
                }
            }
            if (this.conditionalEditFlag != 0) {
                if (this.conditionalEditFlag == DungeonEditorLogic.CEF_CONDITION) {
                    final String def = Integer
                            .toString(instance.getTriggerValue());
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
                        CommonDialogs.showDialog(
                                "The value must be a non-negative integer.");
                        this.instanceBeingEdited = null;
                        return;
                    }
                    instance.setTriggerValue(respVal);
                } else if (this.conditionalEditFlag == DungeonEditorLogic.CEF_TRIGGER_TYPE) {
                    final int respIndex = instance.getSunMoon();
                    final String[] ttChoices = new String[] { "Sun Stones",
                            "Moon Stones" };
                    final String ttChoice = CommonDialogs.showInputDialog(
                            "Condition Trigger Type?", "Editor", ttChoices,
                            ttChoices[respIndex - 1]);
                    if (ttChoice != null) {
                        int newResp = -1;
                        for (int x = 0; x < DungeonEditorLogic.conditionalChoices.length; x++) {
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
                    this.meg.swapToConditionalTeleportHandler();
                    this.getLocationManager().setCameFromZ(
                            this.getLocationManager().getEditorLocationZ());
                    app.getMenuManager().disableDownOneLevel();
                    app.getMenuManager().disableUpOneLevel();
                }
            } else {
                this.instanceBeingEdited = null;
            }
        }
    }

    public AbstractDungeonObject editTeleportDestination(final int type) {
        final Application app = DungeonDiver4.getApplication();
        this.TELEPORT_TYPE = type;
        switch (type) {
            case TELEPORT_TYPE_N_WAY:
                final String[] dests = new String[this.nWayDestCount];
                for (int d = 0; d < this.nWayDestCount; d++) {
                    dests[d] = "Destination " + (d + 1);
                }
                if (this.nWayDestID == -1) {
                    this.nWayDestID = 0;
                }
                final String resp = CommonDialogs.showInputDialog(
                        "Edit Which Destination?", "Editor", dests,
                        dests[this.nWayDestID]);
                if (resp == null) {
                    return null;
                }
                this.nWayDestID = -1;
                for (int z = 0; z < this.nWayDestCount; z++) {
                    if (resp.equals(dests[z])) {
                        this.nWayDestID = z;
                        break;
                    }
                }
                if (this.nWayDestID == -1) {
                    return null;
                }
                DungeonDiver4.getApplication()
                        .showMessage("Click to set teleport destination #"
                                + (this.nWayDestID + 1));
                break;
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
                DungeonDiver4.getApplication()
                        .showMessage("Click to set teleport destination");
                break;
            case TELEPORT_TYPE_RANDOM:
            case TELEPORT_TYPE_RANDOM_INVISIBLE:
            case TELEPORT_TYPE_RANDOM_ONESHOT:
            case TELEPORT_TYPE_RANDOM_INVISIBLE_ONESHOT:
                return this.editRandomTeleportDestination(type);
            default:
                break;
        }
        this.meg.swapToTeleportHandler();
        this.getLocationManager()
                .setCameFromZ(this.getLocationManager().getEditorLocationZ());
        app.getMenuManager().disableDownOneLevel();
        app.getMenuManager().disableUpOneLevel();
        return null;
    }

    private AbstractDungeonObject editRandomTeleportDestination(
            final int type) {
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
                    input2 = CommonDialogs
                            .showTextInputDialog("Random column range:", "Editor");
                    if (input2 != null) {
                        try {
                            destX = Integer.parseInt(input1);
                            destY = Integer.parseInt(input2);
                        } catch (final NumberFormatException nf) {
                            CommonDialogs.showDialog(
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
                }
                break;
            default:
                break;
        }
        return null;
    }

    public AbstractDungeonObject editMetalButtonTarget() {
        DungeonDiver4.getApplication()
                .showMessage("Click to set metal button target");
        final Application app = DungeonDiver4.getApplication();
        this.meg.swapToMetalButtonHandler();
        this.getLocationManager()
                .setCameFromZ(this.getLocationManager().getEditorLocationZ());
        app.getMenuManager().disableDownOneLevel();
        app.getMenuManager().disableUpOneLevel();
        return null;
    }

    public AbstractDungeonObject editTreasureChestContents() {
        DungeonDiver4.getApplication()
                .showMessage("Pick treasure chest contents");
        this.setDefaultContents();
        this.disableOutput();
        this.meg.showTreasurePicker();
        return null;
    }

    private void setDefaultContents() {
        TreasureChest tc = null;
        AbstractDungeonObject contents = null;
        int contentsIndex = 0;
        final Application app = DungeonDiver4.getApplication();
        try {
            tc = (TreasureChest) app.getDungeonManager().getDungeonObject(
                    this.getLocationManager().getEditorLocationX(),
                    this.getLocationManager().getEditorLocationY(),
                    this.getLocationManager().getCameFromZ(),
                    DungeonConstants.LAYER_OBJECT);
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
        this.meg.setTreasurePicked(contentsIndex);
    }

    public void setTeleportDestination(final int x, final int y) {
        final Application app = DungeonDiver4.getApplication();
        final int[] grid = this.meg.computeGridValues(x, y);
        final int destX = grid[0];
        final int destY = grid[1];
        final int destZ = this.getLocationManager().getEditorLocationZ();
        try {
            app.getDungeonManager().getDungeon().getCell(destX, destY, destZ,
                    DungeonConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.meg.swapFromTeleportHandler();
            return;
        }
        switch (this.TELEPORT_TYPE) {
            case TELEPORT_TYPE_N_WAY:
                if (this.nWayDestID == 0) {
                    this.nWayEdited.setDestinationRow(destX);
                    this.nWayEdited.setDestinationColumn(destY);
                    this.nWayEdited.setDestinationFloor(destZ);
                } else {
                    this.nWayEdited.setDestinationRowN(this.nWayDestID, destX);
                    this.nWayEdited.setDestinationColumnN(this.nWayDestID, destY);
                    this.nWayEdited.setDestinationFloorN(this.nWayDestID, destZ);
                }
                app.getDungeonManager().getDungeon().setCell(this.nWayEdited,
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_GENERIC:
                app.getDungeonManager().getDungeon().setCell(
                        new Teleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_INVISIBLE_GENERIC:
                app.getDungeonManager().getDungeon().setCell(
                        new InvisibleTeleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_ONESHOT:
                app.getDungeonManager().getDungeon().setCell(
                        new OneShotTeleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_INVISIBLE_ONESHOT:
                app.getDungeonManager().getDungeon().setCell(
                        new InvisibleOneShotTeleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_TWOWAY:
                app.getDungeonManager().getDungeon().setCell(
                        new TwoWayTeleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                this.pairTwoWayTeleport(destX, destY, destZ);
                break;
            case TELEPORT_TYPE_CHAIN:
                app.getDungeonManager().getDungeon().setCell(
                        new ChainTeleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_INVISIBLE_CHAIN:
                app.getDungeonManager().getDungeon().setCell(
                        new InvisibleChainTeleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_ONESHOT_CHAIN:
                app.getDungeonManager().getDungeon().setCell(
                        new OneShotChainTeleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_INVISIBLE_ONESHOT_CHAIN:
                app.getDungeonManager().getDungeon().setCell(
                        new InvisibleOneShotChainTeleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_BLOCK:
                app.getDungeonManager().getDungeon().setCell(
                        new BlockTeleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            case TELEPORT_TYPE_INVISIBLE_BLOCK:
                app.getDungeonManager().getDungeon().setCell(
                        new InvisibleBlockTeleport(destX, destY, destZ),
                        this.getLocationManager().getEditorLocationX(),
                        this.getLocationManager().getEditorLocationY(),
                        this.getLocationManager().getCameFromZ(),
                        DungeonConstants.LAYER_OBJECT);
                break;
            default:
                break;
        }
        this.meg.swapFromTeleportHandler();
        this.checkMenus();
        DungeonDiver4.getApplication().showMessage("Destination set.");
        app.getDungeonManager().setDirty(true);
        this.redrawEditor();
    }

    void setConditionalTeleportDestination(final int x, final int y) {
        final Application app = DungeonDiver4.getApplication();
        final int[] grid = this.meg.computeGridValues(x, y);
        final int destX = grid[0];
        final int destY = grid[1];
        final int destZ = this.getLocationManager().getEditorLocationZ();
        if (this.instanceBeingEdited != null) {
            if (this.conditionalEditFlag == DungeonEditorLogic.CEF_DEST1) {
                this.instanceBeingEdited.setDestinationRow(destX);
                this.instanceBeingEdited.setDestinationColumn(destY);
                this.instanceBeingEdited.setDestinationFloor(destZ);
            } else if (this.conditionalEditFlag == DungeonEditorLogic.CEF_DEST2) {
                this.instanceBeingEdited.setDestinationRow2(destX);
                this.instanceBeingEdited.setDestinationColumn2(destY);
                this.instanceBeingEdited.setDestinationFloor2(destZ);
            }
            this.instanceBeingEdited = null;
        }
        this.meg.swapFromConditionalTeleportHandler();
        this.checkMenus();
        DungeonDiver4.getApplication().showMessage("Destination set.");
        app.getDungeonManager().setDirty(true);
        this.redrawEditor();
    }

    public void setMetalButtonTarget(final int x, final int y) {
        final Application app = DungeonDiver4.getApplication();
        final int[] grid = this.meg.computeGridValues(x, y);
        final int destX = grid[0];
        final int destY = grid[1];
        final int destZ = this.getLocationManager().getEditorLocationZ();
        final int destW = this.getLocationManager().getEditorLocationW();
        try {
            app.getDungeonManager().getDungeon().getCell(destX, destY, destZ,
                    DungeonConstants.LAYER_OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            this.meg.swapFromMetalButtonHandler();
            return;
        }
        app.getDungeonManager().getDungeon().setCell(
                new MetalButton(destX, destY, destZ, destW),
                this.getLocationManager().getEditorLocationX(),
                this.getLocationManager().getEditorLocationY(),
                this.getLocationManager().getCameFromZ(),
                DungeonConstants.LAYER_OBJECT);
        this.meg.swapFromMetalButtonHandler();
        this.checkMenus();
        DungeonDiver4.getApplication().showMessage("Target set.");
        app.getDungeonManager().setDirty(true);
        this.redrawEditor();
    }

    public void setTreasureChestContents() {
        this.enableOutput();
        final Application app = DungeonDiver4.getApplication();
        final AbstractDungeonObject contents = this.containableObjects[this.meg
                .getTreasurePicked()];
        app.getDungeonManager().getDungeon().setCell(
                new TreasureChest(contents),
                this.getLocationManager().getEditorLocationX(),
                this.getLocationManager().getEditorLocationY(),
                this.getLocationManager().getCameFromZ(),
                DungeonConstants.LAYER_OBJECT);
        this.checkMenus();
        DungeonDiver4.getApplication().showMessage("Contents set.");
        app.getDungeonManager().setDirty(true);
        this.redrawEditor();
    }

    public void editPlayerLocation() {
        // Swap event handlers
        this.meg.swapToStartHandler();
        DungeonDiver4.getApplication().showMessage("Click to set start point");
    }

    void setPlayerLocation(final int x, final int y) {
        final Application app = DungeonDiver4.getApplication();
        final int[] grid = this.meg.computeGridValues(x, y);
        final int destX = grid[0];
        final int destY = grid[1];
        // Set new player
        try {
            app.getDungeonManager().getDungeon().saveStart();
            app.getDungeonManager().getDungeon().setStartRow(destX);
            app.getDungeonManager().getDungeon().setStartColumn(destY);
            app.getDungeonManager().getDungeon().setStartFloor(
                    this.getLocationManager().getEditorLocationZ());
            DungeonDiver4.getApplication().showMessage("Start point set.");
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            app.getDungeonManager().getDungeon().restoreStart();
            DungeonDiver4.getApplication()
                    .showMessage("Aim within the dungeon");
        }
        // Swap event handlers
        this.meg.swapFromStartHandler();
        // Set dirty flag
        app.getDungeonManager().setDirty(true);
        this.redrawEditor();
    }

    public void editDungeon() {
        final Application app = DungeonDiver4.getApplication();
        if (app.getDungeonManager().getLoaded()) {
            app.getGUIManager().hideGUI();
            app.setInEditor();
            // Reset game state
            app.getGameManager().resetGameState();
            // Create the managers
            if (this.dungeonChanged) {
                this.fixLimits();
                this.dungeonChanged = false;
            }
            this.fixLimits();
            this.meg.setUpGUI();
            this.clearHistory();
            this.meg.setUpBorderPane();
            this.showOutput();
            this.redrawEditor();
            this.checkMenus();
        } else {
            CommonDialogs.showDialog("No Dungeon Opened");
        }
    }

    public boolean newDungeon() {
        final Application app = DungeonDiver4.getApplication();
        boolean success = true;
        boolean saved = true;
        int status = 0;
        if (app.getDungeonManager().getDirty()) {
            status = app.getDungeonManager().showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = app.getDungeonManager().saveDungeon();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                app.getDungeonManager().setDirty(false);
            }
        }
        if (saved) {
            app.getGameManager().resetPlayerLocation();
            app.getDungeonManager().setDungeon(new Dungeon());
            success = app.getDungeonManager().getDungeon().addLevel(32, 32, 1);
            if (success) {
                this.fixLimits();
                app.getDungeonManager().getDungeon().fillLevel(
                        PreferencesManager.getEditorDefaultFill(), new Empty());
                // Save the entire level
                app.getDungeonManager().getDungeon().save();
                this.checkMenus();
            }
            if (success) {
                app.getDungeonManager().clearLastUsedFilenames();
                this.clearHistory();
            }
        } else {
            success = false;
        }
        if (success) {
            this.dungeonChanged = true;
            app.getGameManager().stateChanged();
            CommonDialogs.showTitledDialog("Dungeon Created!", "Editor");
        }
        return success;
    }

    public void fixLimits() {
        this.meg.fixLimits();
    }

    private boolean confirmNonUndoable(final String task) {
        final int confirm = CommonDialogs.showConfirmDialog(
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
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .fill();
            DungeonDiver4.getApplication().showMessage("Level filled.");
            DungeonDiver4.getApplication().getDungeonManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillFloor() {
        if (this.confirmNonUndoable(
                "overwrite the active floor within the active level with default data")) {
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .fillFloor(this.getLocationManager().getEditorLocationZ());
            DungeonDiver4.getApplication().showMessage("Floor filled.");
            DungeonDiver4.getApplication().getDungeonManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillLevelRandomly() {
        if (this.confirmNonUndoable(
                "overwrite the active level with random data")) {
            if (DungeonDiver4.getApplication().getMenuManager()
                    .useFillRuleSets()) {
                DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                        .fillLevelRandomlyCustom();
            } else {
                DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                        .fillLevelRandomly();
            }
            DungeonDiver4.getApplication()
                    .showMessage("Level randomly filled.");
            DungeonDiver4.getApplication().getDungeonManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillFloorRandomly() {
        if (this.confirmNonUndoable(
                "overwrite the active floor within the active level with random data")) {
            if (DungeonDiver4.getApplication().getMenuManager()
                    .useFillRuleSets()) {
                DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                        .fillFloorRandomlyCustom(
                                this.getLocationManager().getEditorLocationZ());
            } else {
                DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                        .fillFloorRandomly(
                                this.getLocationManager().getEditorLocationZ());
            }
            DungeonDiver4.getApplication()
                    .showMessage("Floor randomly filled.");
            DungeonDiver4.getApplication().getDungeonManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillLevelAndLayer() {
        if (this.confirmNonUndoable(
                "overwrite the active layer on the active level with default data")) {
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .fillLayer(this.getLocationManager().getEditorLocationE());
            DungeonDiver4.getApplication()
                    .showMessage("Level and layer filled.");
            DungeonDiver4.getApplication().getDungeonManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillFloorAndLayer() {
        if (this.confirmNonUndoable(
                "overwrite the active layer on the active floor within the active level with default data")) {
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .fillFloorAndLayer(
                            this.getLocationManager().getEditorLocationZ(),
                            this.getLocationManager().getEditorLocationE());
            DungeonDiver4.getApplication()
                    .showMessage("Floor and layer filled.");
            DungeonDiver4.getApplication().getDungeonManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillLevelAndLayerRandomly() {
        if (this.confirmNonUndoable(
                "overwrite the active layer on the active level with random data")) {
            if (DungeonDiver4.getApplication().getMenuManager()
                    .useFillRuleSets()) {
                DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                        .fillLevelAndLayerRandomlyCustom(
                                this.getLocationManager().getEditorLocationE());
            } else {
                DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                        .fillLevelAndLayerRandomly(
                                this.getLocationManager().getEditorLocationE());
            }
            DungeonDiver4.getApplication()
                    .showMessage("Level and layer randomly filled.");
            DungeonDiver4.getApplication().getDungeonManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public void fillFloorAndLayerRandomly() {
        if (this.confirmNonUndoable(
                "overwrite the active layer on the active floor within the active level with random data")) {
            if (DungeonDiver4.getApplication().getMenuManager()
                    .useFillRuleSets()) {
                DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                        .fillFloorAndLayerRandomlyCustom(
                                this.getLocationManager().getEditorLocationZ(),
                                this.getLocationManager().getEditorLocationE());
            } else {
                DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                        .fillFloorAndLayerRandomly(
                                this.getLocationManager().getEditorLocationZ(),
                                this.getLocationManager().getEditorLocationE());
            }
            DungeonDiver4.getApplication()
                    .showMessage("Floor and layer randomly filled.");
            DungeonDiver4.getApplication().getDungeonManager().setDirty(true);
            this.redrawEditor();
        }
    }

    public boolean addLevel() {
        final Application app = DungeonDiver4.getApplication();
        int levelSizeX, levelSizeY, levelSizeZ;
        final int maxR = Dungeon.getMaxRows();
        final int minR = Dungeon.getMinRows();
        final int maxC = Dungeon.getMaxColumns();
        final int minC = Dungeon.getMinColumns();
        final int maxF = Dungeon.getMaxFloors();
        final int minF = Dungeon.getMinFloors();
        final String msg = "New Level";
        boolean success = true;
        String input1, input2, input3;
        input1 = CommonDialogs.showTextInputDialog(
                "Number of rows (" + minR + "-" + maxR + ")?", msg);
        if (input1 != null) {
            input2 = CommonDialogs.showTextInputDialog(
                    "Number of columns (" + minC + "-" + maxC + ")?", msg);
            if (input2 != null) {
                input3 = CommonDialogs.showTextInputDialog(
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
                        final int saveLevel = app.getDungeonManager()
                                .getDungeon().getActiveLevelNumber();
                        success = app.getDungeonManager().getDungeon()
                                .addLevel(levelSizeX, levelSizeY, levelSizeZ);
                        if (success) {
                            this.fixLimits();
                            this.getViewManager().setViewingWindowLocationX(
                                    0 - (this.getViewManager()
                                            .getViewingWindowSizeX() - 1) / 2);
                            this.getViewManager().setViewingWindowLocationY(
                                    0 - (this.getViewManager()
                                            .getViewingWindowSizeY() - 1) / 2);
                            app.getDungeonManager().getDungeon().fillLevel(
                                    PreferencesManager.getEditorDefaultFill(),
                                    new Empty());
                            // Save the entire level
                            app.getDungeonManager().getDungeon().save();
                            app.getDungeonManager().getDungeon()
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
        final Application app = DungeonDiver4.getApplication();
        int levelSizeX, levelSizeY, levelSizeZ;
        final int maxR = Dungeon.getMaxRows();
        final int minR = Dungeon.getMinRows();
        final int maxC = Dungeon.getMaxColumns();
        final int minC = Dungeon.getMinColumns();
        final int maxF = Dungeon.getMaxFloors();
        final int minF = Dungeon.getMinFloors();
        final String msg = "Resize Level";
        boolean success = true;
        String input1, input2, input3;
        input1 = CommonDialogs.showTextInputDialogWithDefault(
                "Number of rows (" + minR + "-" + maxR + ")?", msg,
                Integer.toString(
                        app.getDungeonManager().getDungeon().getRows()));
        if (input1 != null) {
            input2 = CommonDialogs.showTextInputDialogWithDefault(
                    "Number of columns (" + minC + "-" + maxC + ")?", msg,
                    Integer.toString(
                            app.getDungeonManager().getDungeon().getColumns()));
            if (input2 != null) {
                input3 = CommonDialogs.showTextInputDialogWithDefault(
                        "Number of floors (" + minF + "-" + maxF + ")?", msg,
                        Integer.toString(app.getDungeonManager().getDungeon()
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
                        app.getDungeonManager().getDungeon().resize(levelSizeX,
                                levelSizeY, levelSizeZ);
                        this.fixLimits();
                        this.getViewManager().setViewingWindowLocationX(0
                                - (this.getViewManager().getViewingWindowSizeX()
                                        - 1) / 2);
                        this.getViewManager().setViewingWindowLocationY(0
                                - (this.getViewManager().getViewingWindowSizeY()
                                        - 1) / 2);
                        // Save the entire level
                        app.getDungeonManager().getDungeon().save();
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
        final Application app = DungeonDiver4.getApplication();
        int level;
        boolean success = true;
        String input;
        input = CommonDialogs.showTextInputDialog("Level Number (1-"
                + app.getDungeonManager().getDungeon().getLevels() + ")?",
                "Remove Level");
        if (input != null) {
            try {
                level = Integer.parseInt(input);
                if (level < 1 || level > app.getDungeonManager().getDungeon()
                        .getLevels()) {
                    throw new NumberFormatException(
                            "Level number must be in the range 1 to "
                                    + app.getDungeonManager().getDungeon()
                                            .getLevels()
                                    + ".");
                }
                success = app.getDungeonManager().getDungeon().removeLevel();
                if (success) {
                    this.fixLimits();
                    if (level == this.getLocationManager().getEditorLocationW()
                            + 1) {
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
            DungeonDiver4.getApplication()
                    .showMessage("Click a teleport to go to its destination");
        }
    }

    void goToDestination(final int x, final int y) {
        if (this.goToDestMode) {
            this.goToDestMode = false;
            final int[] grid = this.meg.computeGridValues(x, y);
            final int locX = grid[0];
            final int locY = grid[1];
            final int locZ = this.getLocationManager().getEditorLocationZ();
            final AbstractDungeonObject there = DungeonDiver4.getApplication()
                    .getDungeonManager().getDungeonObject(locX, locY, locZ,
                            DungeonConstants.LAYER_OBJECT);
            if (there instanceof AbstractTeleport) {
                final AbstractTeleport gt = (AbstractTeleport) there;
                final int destX = gt.getDestinationRow();
                final int destY = gt.getDestinationColumn();
                final int destZ = gt.getDestinationFloor();
                final int destW = this.getLocationManager()
                        .getEditorLocationW();
                this.updateEditorPositionAbsolute(destX, destY, destZ, destW);
                DungeonDiver4.getApplication().showMessage("");
                this.meg.redrawVirtual(destX, destY, new Destination());
            } else {
                DungeonDiver4.getApplication().showMessage(
                        "This object does not have a destination.");
            }
        }
    }

    public void showOutput() {
        this.meg.showOutput();
    }

    public void hideOutput() {
        this.meg.hideOutput();
    }

    void disableOutput() {
        this.meg.disableOutput();
    }

    void enableOutput() {
        this.meg.enableOutput(this.engine);
    }

    public JFrame getOutputFrame() {
        return this.meg.getOutputFrame();
    }

    public void exitEditor() {
        final Application app = DungeonDiver4.getApplication();
        // Hide the editor
        this.hideOutput();
        final DungeonManager mm = app.getDungeonManager();
        final GameLogicManager gm = app.getGameManager();
        // Save the entire level
        mm.getDungeon().save();
        // Reset the viewing window
        gm.resetViewingWindowAndPlayerLocation();
        gm.stateChanged();
        DungeonDiver4.getApplication().getGUIManager().showGUI();
    }

    public void undo() {
        final Application app = DungeonDiver4.getApplication();
        this.engine.undo();
        final AbstractDungeonObject obj = this.engine.getObject();
        final int x = this.engine.getX();
        final int y = this.engine.getY();
        final int z = this.engine.getZ();
        final int w = this.engine.getW();
        final int e = this.engine.getE();
        this.getLocationManager().setEditorLocationX(x);
        this.getLocationManager().setEditorLocationY(y);
        this.getLocationManager().setCameFromZ(z);
        if (x != -1 && y != -1 && z != -1 && w != -1) {
            final AbstractDungeonObject oldObj = app.getDungeonManager()
                    .getDungeonObject(x, y, z, e);
            if (!obj.getName().equals(new StairsUp().getName())
                    && !obj.getName().equals(new StairsDown().getName())) {
                if (obj.getName().equals(new TwoWayTeleport().getName())) {
                    app.getDungeonManager().getDungeon().setCell(obj, x, y, z,
                            e);
                    this.reverseCheckTwoWayTeleportPair(z);
                    this.checkStairPair(z);
                } else {
                    this.checkTwoWayTeleportPair(z);
                    app.getDungeonManager().getDungeon().setCell(obj, x, y, z,
                            e);
                    this.checkStairPair(z);
                }
            } else {
                app.getDungeonManager().getDungeon().setCell(obj, x, y, z, e);
                this.reverseCheckStairPair(z);
            }
            this.updateRedoHistory(oldObj, x, y, z, w, e);
            this.checkMenus();
            this.redrawEditor();
        } else {
            DungeonDiver4.getApplication().showMessage("Nothing to undo");
        }
    }

    public void redo() {
        final Application app = DungeonDiver4.getApplication();
        this.engine.redo();
        final AbstractDungeonObject obj = this.engine.getObject();
        final int x = this.engine.getX();
        final int y = this.engine.getY();
        final int z = this.engine.getZ();
        final int w = this.engine.getW();
        final int e = this.engine.getE();
        this.getLocationManager().setEditorLocationX(x);
        this.getLocationManager().setEditorLocationY(y);
        this.getLocationManager().setCameFromZ(z);
        if (x != -1 && y != -1 && z != -1 && w != -1) {
            final AbstractDungeonObject oldObj = app.getDungeonManager()
                    .getDungeonObject(x, y, z, e);
            if (!obj.getName().equals(new StairsUp().getName())
                    && !obj.getName().equals(new StairsDown().getName())) {
                if (obj.getName().equals(new TwoWayTeleport().getName())) {
                    app.getDungeonManager().getDungeon().setCell(obj, x, y, z,
                            e);
                    this.reverseCheckTwoWayTeleportPair(z);
                    this.checkStairPair(z);
                } else {
                    this.checkTwoWayTeleportPair(z);
                    app.getDungeonManager().getDungeon().setCell(obj, x, y, z,
                            e);
                    this.checkStairPair(z);
                }
            } else {
                app.getDungeonManager().getDungeon().setCell(obj, x, y, z, e);
                this.reverseCheckStairPair(z);
            }
            this.updateUndoHistory(oldObj, x, y, z, w, e);
            this.checkMenus();
            this.redrawEditor();
        } else {
            DungeonDiver4.getApplication().showMessage("Nothing to redo");
        }
    }

    public void clearHistory() {
        this.engine = new UndoRedoEngine();
        this.checkMenus();
    }

    private void updateUndoHistory(final AbstractDungeonObject obj, final int x,
            final int y, final int z, final int w, final int e) {
        this.engine.updateUndoHistory(obj, x, y, z, w, e);
    }

    private void updateRedoHistory(final AbstractDungeonObject obj, final int x,
            final int y, final int z, final int w, final int e) {
        this.engine.updateRedoHistory(obj, x, y, z, w, e);
    }

    public void handleCloseWindow() {
        try {
            final Application app = DungeonDiver4.getApplication();
            boolean success = false;
            int status = JOptionPane.DEFAULT_OPTION;
            if (app.getDungeonManager().getDirty()) {
                status = app.getDungeonManager().showSaveDialog();
                if (status == JOptionPane.YES_OPTION) {
                    success = app.getDungeonManager().saveDungeon();
                    if (success) {
                        this.exitEditor();
                    }
                } else if (status == JOptionPane.NO_OPTION) {
                    app.getDungeonManager().setDirty(false);
                    this.exitEditor();
                }
            } else {
                this.exitEditor();
            }
        } catch (final Exception ex) {
            DungeonDiver4.getErrorLogger().logError(ex);
        }
    }
}
