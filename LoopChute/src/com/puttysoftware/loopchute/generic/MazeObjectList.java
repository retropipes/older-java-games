/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.generic;

import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.maze.FormatConstants;
import com.puttysoftware.loopchute.maze.MazeConstants;
import com.puttysoftware.loopchute.objects.*;
import com.puttysoftware.loopchute.resourcemanagers.GraphicsManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class MazeObjectList {
    // Fields
    private final ArrayList<MazeObject> allObjectList;

    // Constructor
    public MazeObjectList() {
        final MazeObject[] allObjects = { new Empty(), new Grass(), new Dirt(),
                new Sand(), new Snow(), new Tundra(), new Tile(), new Ice(),
                new Water(), new HotRock(), new Slime(), new Lava(),
                new SunkenBlock(), new BridgedLavaHorizontal(),
                new BridgedLavaVertical(), new BridgedSlimeHorizontal(),
                new BridgedSlimeVertical(), new BridgedWaterHorizontal(),
                new BridgedWaterVertical(), new BlueCarpet(), new CyanCarpet(),
                new GreenCarpet(), new MagentaCarpet(), new OrangeCarpet(),
                new PurpleCarpet(), new RedCarpet(), new RoseCarpet(),
                new SeaweedCarpet(), new SkyCarpet(), new WhiteCarpet(),
                new YellowCarpet(), new Player(), new SunStone(),
                new MoonStone(), new Wall(), new InvisibleWall(),
                new FakeWall(), new BreakableWallHorizontal(),
                new BreakableWallVertical(), new BlueWallOff(),
                new BlueWallOn(), new GreenWallOff(), new GreenWallOn(),
                new MagentaWallOff(), new MagentaWallOn(), new OrangeWallOff(),
                new OrangeWallOn(), new PurpleWallOff(), new PurpleWallOn(),
                new RedWallOff(), new RedWallOn(), new RoseWallOff(),
                new RoseWallOn(), new SeaweedWallOff(), new SeaweedWallOn(),
                new SkyWallOff(), new SkyWallOn(), new WhiteWallOff(),
                new WhiteWallOn(), new YellowWallOff(), new YellowWallOn(),
                new CyanWallOff(), new CyanWallOn(), new Key(), new Lock(),
                new BlueKey(), new BlueLock(), new GreenKey(), new GreenLock(),
                new MagentaKey(), new MagentaLock(), new OrangeKey(),
                new OrangeLock(), new PurpleKey(), new PurpleLock(),
                new RedKey(), new RedLock(), new RoseKey(), new RoseLock(),
                new SeaweedKey(), new SeaweedLock(), new SkyKey(),
                new SkyLock(), new WhiteKey(), new WhiteLock(), new YellowKey(),
                new YellowLock(), new CyanKey(), new CyanLock(),
                new BlueCrystal(), new BlueCrystalWall(), new GreenCrystal(),
                new GreenCrystalWall(), new MagentaCrystal(),
                new MagentaCrystalWall(), new OrangeCrystal(),
                new OrangeCrystalWall(), new PurpleCrystal(),
                new PurpleCrystalWall(), new RedCrystal(), new RedCrystalWall(),
                new RoseCrystal(), new RoseCrystalWall(), new SeaweedCrystal(),
                new SeaweedCrystalWall(), new SkyCrystal(),
                new SkyCrystalWall(), new WhiteCrystal(),
                new WhiteCrystalWall(), new YellowCrystal(),
                new YellowCrystalWall(), new CyanCrystal(),
                new CyanCrystalWall(), new Door(), new SunDoor(),
                new MoonDoor(), new BlueButton(), new GreenButton(),
                new MagentaButton(), new OrangeButton(), new PurpleButton(),
                new RedButton(), new RoseButton(), new SeaweedButton(),
                new SkyButton(), new WhiteButton(), new YellowButton(),
                new CyanButton(), new Teleport(), new InvisibleTeleport(),
                new RandomTeleport(), new RandomInvisibleTeleport(),
                new RandomOneShotTeleport(),
                new RandomInvisibleOneShotTeleport(), new OneShotTeleport(),
                new InvisibleOneShotTeleport(), new TwoWayTeleport(),
                new ControllableTeleport(), new OneShotControllableTeleport(),
                new ConditionalTeleport(), new InvisibleConditionalTeleport(),
                new OneShotConditionalTeleport(),
                new InvisibleOneShotConditionalTeleport(), new ChainTeleport(),
                new InvisibleChainTeleport(), new OneShotChainTeleport(),
                new InvisibleOneShotChainTeleport(),
                new ConditionalChainTeleport(),
                new InvisibleConditionalChainTeleport(), new BlockTeleport(),
                new InvisibleBlockTeleport(), new StairsUp(), new StairsDown(),
                new Pit(), new InvisiblePit(), new Springboard(),
                new InvisibleSpringboard(), new PushableBlock(),
                new PullableBlock(), new PushablePullableBlock(), new NoBoots(),
                new GlueBoots(), new AquaBoots(), new BioHazardBoots(),
                new FireBoots(), new HotBoots(), new PasswallBoots(),
                new SlipperyBoots(), new EmptyVoid(), new Sign(), new NoBlock(),
                new NoPlayer(), new HorizontalBarrier(), new VerticalBarrier(),
                new BarrierGenerator(), new EnragedBarrierGenerator(),
                new IcedBarrierGenerator(), new PoisonedBarrierGenerator(),
                new ShockedBarrierGenerator(), new Bow(), new IceBow(),
                new FireBow(), new PoisonBow(), new ShockBow(), new GhostBow(),
                new Crevasse(), new AnnihilationWand(), new WallMakingWand(),
                new TeleportWand(), new WallBreakingWand(), new GarnetSquare(),
                new GarnetWall(), new GoldenSquare(), new GoldenWall(),
                new RubySquare(), new RubyWall(), new SapphireSquare(),
                new SapphireWall(), new SilverSquare(), new SilverWall(),
                new TopazSquare(), new TopazWall() };
        this.allObjectList = new ArrayList<>();
        // Add all predefined objects to the list
        for (final MazeObject allObject : allObjects) {
            this.allObjectList.add(allObject);
        }
    }

    // Methods
    public MazeObject[] getAllObjects() {
        return this.allObjectList
                .toArray(new MazeObject[this.allObjectList.size()]);
    }

    public void addObject(final MazeObject o) {
        this.allObjectList.add(o);
    }

    public String[] getAllNames() {
        final MazeObject[] objects = this.getAllObjects();
        final String[] allNames = new String[objects.length];
        for (int x = 0; x < objects.length; x++) {
            allNames[x] = objects[x].getName();
        }
        return allNames;
    }

    public String[] getAllDescriptions() {
        final MazeObject[] objects = this.getAllObjects();
        final String[] allDescriptions = new String[objects.length];
        for (int x = 0; x < objects.length; x++) {
            allDescriptions[x] = objects[x].getDescription();
        }
        return allDescriptions;
    }

    public MazeObject[] getAllObjectsWithRuleSets() {
        final MazeObject[] objects = this.getAllObjects();
        final MazeObject[] tempAllObjectsWithRuleSets = new MazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].hasRuleSet()) {
                tempAllObjectsWithRuleSets[x] = objects[x];
            }
        }
        for (final MazeObject tempAllObjectsWithRuleSet : tempAllObjectsWithRuleSets) {
            if (tempAllObjectsWithRuleSet != null) {
                objectCount++;
            }
        }
        final MazeObject[] allObjectsWithRuleSets = new MazeObject[objectCount];
        objectCount = 0;
        for (final MazeObject tempAllObjectsWithRuleSet : tempAllObjectsWithRuleSets) {
            if (tempAllObjectsWithRuleSet != null) {
                allObjectsWithRuleSets[objectCount] = tempAllObjectsWithRuleSet;
                objectCount++;
            }
        }
        return allObjectsWithRuleSets;
    }

    public MazeObject[] getAllObjectsWithoutRuleSets() {
        final MazeObject[] objects = this.getAllObjects();
        final MazeObject[] tempAllObjectsWithoutRuleSets = new MazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (!objects[x].hasRuleSet()) {
                tempAllObjectsWithoutRuleSets[x] = objects[x];
            }
        }
        for (final MazeObject tempAllObjectsWithoutRuleSet : tempAllObjectsWithoutRuleSets) {
            if (tempAllObjectsWithoutRuleSet != null) {
                objectCount++;
            }
        }
        final MazeObject[] allObjectsWithoutRuleSets = new MazeObject[objectCount];
        objectCount = 0;
        for (final MazeObject tempAllObjectsWithoutRuleSet : tempAllObjectsWithoutRuleSets) {
            if (tempAllObjectsWithoutRuleSet != null) {
                allObjectsWithoutRuleSets[objectCount] = tempAllObjectsWithoutRuleSet;
                objectCount++;
            }
        }
        return allObjectsWithoutRuleSets;
    }

    public MazeObject[] getAllGroundLayerObjects() {
        final MazeObject[] objects = this.getAllObjects();
        final MazeObject[] tempAllGroundLayerObjects = new MazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == MazeConstants.LAYER_GROUND) {
                tempAllGroundLayerObjects[x] = objects[x];
            }
        }
        for (final MazeObject tempAllGroundLayerObject : tempAllGroundLayerObjects) {
            if (tempAllGroundLayerObject != null) {
                objectCount++;
            }
        }
        final MazeObject[] allGroundLayerObjects = new MazeObject[objectCount];
        objectCount = 0;
        for (final MazeObject tempAllGroundLayerObject : tempAllGroundLayerObjects) {
            if (tempAllGroundLayerObject != null) {
                allGroundLayerObjects[objectCount] = tempAllGroundLayerObject;
                objectCount++;
            }
        }
        return allGroundLayerObjects;
    }

    public MazeObject[] getAllGenerationEligibleTypedObjects() {
        final MazeObject[] objects = this.getAllObjects();
        final MazeObject[] tempAllGenerationEligibleTypedObjects = new MazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
                tempAllGenerationEligibleTypedObjects[x] = objects[x];
            }
        }
        for (final MazeObject tempAllGenerationEligibleTypedObject : tempAllGenerationEligibleTypedObjects) {
            if (tempAllGenerationEligibleTypedObject != null) {
                objectCount++;
            }
        }
        final MazeObject[] allGenerationEligibleTypedObjects = new MazeObject[objectCount];
        objectCount = 0;
        for (final MazeObject tempAllGenerationEligibleTypedObject : tempAllGenerationEligibleTypedObjects) {
            if (tempAllGenerationEligibleTypedObject != null) {
                allGenerationEligibleTypedObjects[objectCount] = tempAllGenerationEligibleTypedObject;
                objectCount++;
            }
        }
        return allGenerationEligibleTypedObjects;
    }

    public MazeObject[] getAllGeneratedTypedObjects() {
        final MazeObject[] objects = this.getAllObjects();
        final MazeObject[] tempAllGeneratedTypedObjects = new MazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_GENERATED)) {
                tempAllGeneratedTypedObjects[x] = objects[x];
            }
        }
        for (final MazeObject tempAllGeneratedTypedObject : tempAllGeneratedTypedObjects) {
            if (tempAllGeneratedTypedObject != null) {
                objectCount++;
            }
        }
        final MazeObject[] allGeneratedTypedObjects = new MazeObject[objectCount];
        objectCount = 0;
        for (final MazeObject tempAllGeneratedTypedObject : tempAllGeneratedTypedObjects) {
            if (tempAllGeneratedTypedObject != null) {
                allGeneratedTypedObjects[objectCount] = tempAllGeneratedTypedObject;
                objectCount++;
            }
        }
        return allGeneratedTypedObjects;
    }

    public MazeObject[] getAllObjectLayerObjects() {
        final MazeObject[] objects = this.getAllObjects();
        final MazeObject[] tempAllObjectLayerObjects = new MazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == MazeConstants.LAYER_OBJECT) {
                tempAllObjectLayerObjects[x] = objects[x];
            }
        }
        for (final MazeObject tempAllObjectLayerObject : tempAllObjectLayerObjects) {
            if (tempAllObjectLayerObject != null) {
                objectCount++;
            }
        }
        final MazeObject[] allObjectLayerObjects = new MazeObject[objectCount];
        objectCount = 0;
        for (final MazeObject tempAllObjectLayerObject : tempAllObjectLayerObjects) {
            if (tempAllObjectLayerObject != null) {
                allObjectLayerObjects[objectCount] = tempAllObjectLayerObject;
                objectCount++;
            }
        }
        return allObjectLayerObjects;
    }

    public String[] getAllGroundLayerNames() {
        final MazeObject[] objects = this.getAllObjects();
        final String[] tempAllGroundLayerNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == MazeConstants.LAYER_GROUND) {
                tempAllGroundLayerNames[x] = objects[x].getName();
            }
        }
        for (final String tempAllGroundLayerName : tempAllGroundLayerNames) {
            if (tempAllGroundLayerName != null) {
                objectCount++;
            }
        }
        final String[] allGroundLayerNames = new String[objectCount];
        objectCount = 0;
        for (final String tempAllGroundLayerName : tempAllGroundLayerNames) {
            if (tempAllGroundLayerName != null) {
                allGroundLayerNames[objectCount] = tempAllGroundLayerName;
                objectCount++;
            }
        }
        return allGroundLayerNames;
    }

    public String[] getAllObjectLayerNames() {
        final MazeObject[] objects = this.getAllObjects();
        final String[] tempAllObjectLayerNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == MazeConstants.LAYER_OBJECT) {
                tempAllObjectLayerNames[x] = objects[x].getName();
            }
        }
        for (final String tempAllObjectLayerName : tempAllObjectLayerNames) {
            if (tempAllObjectLayerName != null) {
                objectCount++;
            }
        }
        final String[] allObjectLayerNames = new String[objectCount];
        objectCount = 0;
        for (final String tempAllObjectLayerName : tempAllObjectLayerNames) {
            if (tempAllObjectLayerName != null) {
                allObjectLayerNames[objectCount] = tempAllObjectLayerName;
                objectCount++;
            }
        }
        return allObjectLayerNames;
    }

    public BufferedImageIcon[] getAllEditorAppearances() {
        final MazeObject[] objects = this.getAllObjects();
        final BufferedImageIcon[] allEditorAppearances = new BufferedImageIcon[objects.length];
        for (int x = 0; x < allEditorAppearances.length; x++) {
            allEditorAppearances[x] = GraphicsManager.getTransformedImage(
                    objects[x].getName(), objects[x].getBaseName(),
                    objects[x].getTemplateColor(),
                    objects[x].getAttributeName(),
                    objects[x].getAttributeTemplateColor());
        }
        return allEditorAppearances;
    }

    public BufferedImageIcon[] getAllGroundLayerEditorAppearances() {
        final MazeObject[] objects = this.getAllObjects();
        final BufferedImageIcon[] tempAllGroundLayerEditorAppearances = new BufferedImageIcon[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == MazeConstants.LAYER_GROUND) {
                tempAllGroundLayerEditorAppearances[x] = GraphicsManager
                        .getTransformedImage(objects[x].getName(),
                                objects[x].getBaseName(),
                                objects[x].getTemplateColor(),
                                objects[x].getAttributeName(),
                                objects[x].getAttributeTemplateColor());
            }
        }
        for (final BufferedImageIcon tempAllGroundLayerEditorAppearance : tempAllGroundLayerEditorAppearances) {
            if (tempAllGroundLayerEditorAppearance != null) {
                objectCount++;
            }
        }
        final BufferedImageIcon[] allGroundLayerEditorAppearances = new BufferedImageIcon[objectCount];
        objectCount = 0;
        for (final BufferedImageIcon tempAllGroundLayerEditorAppearance : tempAllGroundLayerEditorAppearances) {
            if (tempAllGroundLayerEditorAppearance != null) {
                allGroundLayerEditorAppearances[objectCount] = tempAllGroundLayerEditorAppearance;
                objectCount++;
            }
        }
        return allGroundLayerEditorAppearances;
    }

    public BufferedImageIcon[] getAllObjectLayerEditorAppearances() {
        final MazeObject[] objects = this.getAllObjects();
        final BufferedImageIcon[] tempAllObjectLayerEditorAppearances = new BufferedImageIcon[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == MazeConstants.LAYER_OBJECT) {
                tempAllObjectLayerEditorAppearances[x] = GraphicsManager
                        .getTransformedImage(objects[x].getName(),
                                objects[x].getBaseName(),
                                objects[x].getTemplateColor(),
                                objects[x].getAttributeName(),
                                objects[x].getAttributeTemplateColor());
            }
        }
        for (final BufferedImageIcon tempAllObjectLayerEditorAppearance : tempAllObjectLayerEditorAppearances) {
            if (tempAllObjectLayerEditorAppearance != null) {
                objectCount++;
            }
        }
        final BufferedImageIcon[] allObjectLayerEditorAppearances = new BufferedImageIcon[objectCount];
        objectCount = 0;
        for (final BufferedImageIcon tempAllObjectLayerEditorAppearance : tempAllObjectLayerEditorAppearances) {
            if (tempAllObjectLayerEditorAppearance != null) {
                allObjectLayerEditorAppearances[objectCount] = tempAllObjectLayerEditorAppearance;
                objectCount++;
            }
        }
        return allObjectLayerEditorAppearances;
    }

    public MazeObject[] getAllInventoryableObjectsMinusSpecial() {
        final MazeObject[] objects = this.getAllObjects();
        final MazeObject[] tempAllInventoryableObjects = new MazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isInventoryable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllInventoryableObjects[x] = objects[x];
            }
        }
        for (final MazeObject tempAllInventoryableObject : tempAllInventoryableObjects) {
            if (tempAllInventoryableObject != null) {
                objectCount++;
            }
        }
        final MazeObject[] allInventoryableObjects = new MazeObject[objectCount];
        objectCount = 0;
        for (final MazeObject tempAllInventoryableObject : tempAllInventoryableObjects) {
            if (tempAllInventoryableObject != null) {
                allInventoryableObjects[objectCount] = tempAllInventoryableObject;
                objectCount++;
            }
        }
        return allInventoryableObjects;
    }

    public String[] getAllInventoryableNamesMinusSpecial() {
        final MazeObject[] objects = this.getAllObjects();
        final String[] tempAllInventoryableNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isInventoryable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllInventoryableNames[x] = objects[x].getName();
            }
        }
        for (final String tempAllInventoryableName : tempAllInventoryableNames) {
            if (tempAllInventoryableName != null) {
                objectCount++;
            }
        }
        final String[] allInventoryableNames = new String[objectCount];
        objectCount = 0;
        for (final String tempAllInventoryableName : tempAllInventoryableNames) {
            if (tempAllInventoryableName != null) {
                allInventoryableNames[objectCount] = tempAllInventoryableName;
                objectCount++;
            }
        }
        return allInventoryableNames;
    }

    public MazeObject[] getAllUsableObjects() {
        final MazeObject[] objects = this.getAllObjects();
        final MazeObject[] tempAllUsableObjects = new MazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isUsable()) {
                tempAllUsableObjects[x] = objects[x];
            }
        }
        for (final MazeObject tempAllUsableObject : tempAllUsableObjects) {
            if (tempAllUsableObject != null) {
                objectCount++;
            }
        }
        final MazeObject[] allUsableObjects = new MazeObject[objectCount];
        objectCount = 0;
        for (final MazeObject tempAllUsableObject : tempAllUsableObjects) {
            if (tempAllUsableObject != null) {
                allUsableObjects[objectCount] = tempAllUsableObject;
                objectCount++;
            }
        }
        return allUsableObjects;
    }

    public String[] getAllUsableNamesMinusSpecial() {
        final MazeObject[] objects = this.getAllObjects();
        final String[] tempAllUsableNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isUsable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOW)) {
                tempAllUsableNames[x] = objects[x].getName();
            }
        }
        for (final String tempAllUsableName : tempAllUsableNames) {
            if (tempAllUsableName != null) {
                objectCount++;
            }
        }
        final String[] allUsableNames = new String[objectCount];
        objectCount = 0;
        for (final String tempAllUsableName : tempAllUsableNames) {
            if (tempAllUsableName != null) {
                allUsableNames[objectCount] = tempAllUsableName;
                objectCount++;
            }
        }
        return allUsableNames;
    }

    public final MazeObject[] getAllRequired(final int layer) {
        final MazeObject[] objects = this.getAllObjects();
        final MazeObject[] tempAllRequired = new MazeObject[objects.length];
        int x;
        int count = 0;
        for (x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == layer && objects[x].isRequired()) {
                tempAllRequired[count] = objects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final MazeObject[] allRequired = new MazeObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final MazeObject[] getAllWithoutPrerequisiteAndNotRequired(
            final int layer) {
        final MazeObject[] objects = this.getAllObjects();
        final MazeObject[] tempAllWithoutPrereq = new MazeObject[objects.length];
        int x;
        int count = 0;
        for (x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == layer && !objects[x].isRequired()) {
                tempAllWithoutPrereq[count] = objects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final MazeObject[] allWithoutPrereq = new MazeObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public static final MazeObject[] getAllRequiredSubset(
            final MazeObject[] objs, final int layer) {
        if (objs == null) {
            return null;
        }
        final MazeObject[] tempAllRequired = new MazeObject[objs.length];
        int x;
        int count = 0;
        for (x = 0; x < objs.length; x++) {
            if (objs[x].hasRuleSet()) {
                if (objs[x].getLayer() == layer
                        && objs[x].getRuleSet().isRequired()) {
                    tempAllRequired[count] = objs[x];
                    count++;
                }
            } else {
                if (objs[x].getLayer() == layer && objs[x].isRequired()) {
                    tempAllRequired[count] = objs[x];
                    count++;
                }
            }
        }
        if (count == 0) {
            return null;
        } else {
            final MazeObject[] allRequired = new MazeObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public static final MazeObject[] getAllWithoutPrerequisiteAndNotRequiredSubset(
            final MazeObject[] objs, final int layer) {
        if (objs == null) {
            return null;
        }
        final MazeObject[] tempAllWithoutPrereq = new MazeObject[objs.length];
        int x;
        int count = 0;
        for (x = 0; x < objs.length; x++) {
            if (objs[x].hasRuleSet()) {
                if (objs[x].getLayer() == layer
                        && !objs[x].getRuleSet().isRequired()) {
                    tempAllWithoutPrereq[count] = objs[x];
                    count++;
                }
            } else {
                if (objs[x].getLayer() == layer && !objs[x].isRequired()) {
                    tempAllWithoutPrereq[count] = objs[x];
                    count++;
                }
            }
        }
        if (count == 0) {
            return null;
        } else {
            final MazeObject[] allWithoutPrereq = new MazeObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final MazeObject getNewInstanceByName(final String name) {
        final MazeObject[] objects = this.getAllObjects();
        MazeObject instance = null;
        int x;
        for (x = 0; x < objects.length; x++) {
            if (objects[x].getName().equals(name)) {
                instance = objects[x];
                break;
            }
        }
        if (instance == null) {
            return null;
        } else {
            try {
                if (instance.isOfType(TypeConstants.TYPE_GENERATED)) {
                    return instance.clone();
                } else {
                    return instance.getClass().newInstance();
                }
            } catch (final IllegalAccessException iae) {
                return null;
            } catch (final InstantiationException ie) {
                return null;
            }
        }
    }

    public MazeObject readMazeObject(final XDataReader reader,
            final int formatVersion) throws IOException {
        final MazeObject[] objects = this.getAllObjects();
        MazeObject o = null;
        String UID = "";
        if (formatVersion == FormatConstants.MAZE_FORMAT_1
                || formatVersion == FormatConstants.MAZE_FORMAT_2
                || formatVersion == FormatConstants.MAZE_FORMAT_3) {
            UID = reader.readString();
        }
        for (final MazeObject object : objects) {
            try {
                MazeObject instance;
                if (object.isOfType(TypeConstants.TYPE_GENERATED)) {
                    instance = object.clone();
                } else {
                    instance = object.getClass().newInstance();
                }
                if (formatVersion == FormatConstants.MAZE_FORMAT_1
                        || formatVersion == FormatConstants.MAZE_FORMAT_2
                        || formatVersion == FormatConstants.MAZE_FORMAT_3) {
                    o = instance.readMazeObject(reader, UID, formatVersion);
                }
                if (o != null) {
                    // Fix older mazes
                    if (o.isOfType(TypeConstants.TYPE_CHARACTER)) {
                        return new Empty();
                    } else {
                        return o;
                    }
                }
            } catch (final InstantiationException ex) {
                LoopChute.getErrorLogger().logError(ex);
            } catch (final IllegalAccessException ex) {
                LoopChute.getErrorLogger().logError(ex);
            }
        }
        return null;
    }

    public void readRuleSet(final XDataReader reader, final int rsFormat)
            throws IOException {
        final MazeObject[] objects = this.getAllObjects();
        // Read map length
        final int mapLen = reader.readInt();
        final boolean[] map = new boolean[mapLen];
        // Read map
        for (int x = 0; x < mapLen; x++) {
            map[x] = reader.readBoolean();
        }
        // Read data
        for (int x = 0; x < mapLen; x++) {
            if (map[x]) {
                objects[x].giveRuleSet();
                objects[x].getRuleSet().readRuleSet(reader, rsFormat);
            }
        }
    }

    public void writeRuleSet(final XDataWriter writer) throws IOException {
        final MazeObject[] objects = this.getAllObjects();
        final boolean[] map = this.generateMap();
        // Write map length
        writer.writeInt(map.length);
        // Write map
        for (final boolean element : map) {
            writer.writeBoolean(element);
        }
        // Write data
        for (int x = 0; x < map.length; x++) {
            if (map[x]) {
                objects[x].getRuleSet().writeRuleSet(writer);
            }
        }
    }

    private boolean[] generateMap() {
        final MazeObject[] objects = this.getAllObjects();
        final boolean[] map = new boolean[objects.length];
        for (int x = 0; x < map.length; x++) {
            if (objects[x].hasRuleSet()) {
                map[x] = true;
            } else {
                map[x] = false;
            }
        }
        return map;
    }
}
