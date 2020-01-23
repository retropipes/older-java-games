/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import java.io.IOException;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.objects.*;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;
import net.worldwizard.worldz.world.FormatConstants;
import net.worldwizard.worldz.world.WorldConstants;

public class WorldObjectList {
    // Fields
    private final WorldObject[] allObjects = { new Empty(), new Grass(),
            new Dirt(), new Sand(), new Snow(), new Tundra(), new Tile(),
            new Ice(), new Water(), new Slime(), new Lava(), new SunkenBlock(),
            new ForceField(), new Player(), new Wall(), new InvisibleWall(),
            new FakeWall(), new BlueWallOff(), new BlueWallOn(),
            new GreenWallOff(), new GreenWallOn(), new MagentaWallOff(),
            new MagentaWallOn(), new OrangeWallOff(), new OrangeWallOn(),
            new PurpleWallOff(), new PurpleWallOn(), new RedWallOff(),
            new RedWallOn(), new WhiteWallOff(), new WhiteWallOn(),
            new YellowWallOff(), new YellowWallOn(), new CyanWallOff(),
            new CyanWallOn(), new OneWayEastWall(), new OneWayNorthWall(),
            new OneWaySouthWall(), new OneWayWestWall(), new ExplodingWall(),
            new BreakableWallHorizontal(), new BreakableWallVertical(),
            new FadingWall(), new DamageableWall(), new CrackedWall(),
            new DamagedWall(), new MasterTrappedWall(), new TrappedWall0(),
            new TrappedWall1(), new TrappedWall2(), new TrappedWall3(),
            new TrappedWall4(), new TrappedWall5(), new TrappedWall6(),
            new TrappedWall7(), new TrappedWall8(), new TrappedWall9(),
            new TrappedWall10(), new TrappedWall11(), new TrappedWall12(),
            new TrappedWall13(), new TrappedWall14(), new TrappedWall15(),
            new TrappedWall16(), new TrappedWall17(), new TrappedWall18(),
            new TrappedWall19(), new BrickWall(), new Hammer(), new Axe(),
            new Tree(), new CutTree(), new Tablet(), new TabletSlot(),
            new EnergySphere(), new APlug(), new APort(), new BPlug(),
            new BPort(), new CPlug(), new CPort(), new DPlug(), new DPort(),
            new EPlug(), new EPort(), new FPlug(), new FPort(), new GPlug(),
            new GPort(), new HPlug(), new HPort(), new IPlug(), new IPort(),
            new JPlug(), new JPort(), new KPlug(), new KPort(), new LPlug(),
            new LPort(), new MPlug(), new MPort(), new NPlug(), new NPort(),
            new OPlug(), new OPort(), new PPlug(), new PPort(), new QPlug(),
            new QPort(), new RPlug(), new RPort(), new SPlug(), new SPort(),
            new TPlug(), new TPort(), new UPlug(), new UPort(), new VPlug(),
            new VPort(), new WPlug(), new WPort(), new XPlug(), new XPort(),
            new YPlug(), new YPort(), new ZPlug(), new ZPort(),
            new GarnetSquare(), new GarnetWall(), new GoldenSquare(),
            new GoldenWall(), new RubySquare(), new RubyWall(),
            new SapphireSquare(), new SapphireWall(), new SilverSquare(),
            new SilverWall(), new TopazSquare(), new TopazWall(), new Key(),
            new Lock(), new BlueKey(), new BlueLock(), new GreenKey(),
            new GreenLock(), new MagentaKey(), new MagentaLock(),
            new OrangeKey(), new OrangeLock(), new PurpleKey(),
            new PurpleLock(), new RedKey(), new RedLock(), new WhiteKey(),
            new WhiteLock(), new YellowKey(), new YellowLock(), new CyanKey(),
            new CyanLock(), new MetalKey(), new MetalDoor(), new Door(),
            new BlueButton(), new GreenButton(), new MagentaButton(),
            new OrangeButton(), new PurpleButton(), new RedButton(),
            new WhiteButton(), new YellowButton(), new CyanButton(),
            new MetalButton(), new Teleport(), new InvisibleTeleport(),
            new RandomTeleport(), new RandomInvisibleTeleport(),
            new RandomOneShotTeleport(), new RandomInvisibleOneShotTeleport(),
            new OneShotTeleport(), new InvisibleOneShotTeleport(),
            new TwoWayTeleport(), new ControllableTeleport(),
            new OneShotControllableTeleport(), new StairsUp(),
            new StairsDown(), new Pit(), new InvisiblePit(),
            new PushableBlock(), new PullableBlock(),
            new PushablePullableBlock(), new PushableBlockOnce(),
            new PushableBlockTwice(), new PushableBlockThrice(),
            new PullableBlockOnce(), new PullableBlockTwice(),
            new PullableBlockThrice(), new MovingBlock(), new MetalBoots(),
            new NoBoots(), new HealBoots(), new GlueBoots(), new AquaBoots(),
            new BioHazardBoots(), new FireBoots(), new AnnihilationWand(),
            new WallMakingWand(), new TeleportWand(), new WallBreakingWand(),
            new DisarmTrapWand(), new RemoteActionWand(), new RotationWand(),
            new WarpWand(), new EmptyVoid(), new HealTrap(), new HurtTrap(),
            new VariableHealTrap(), new VariableHurtTrap(),
            new ClockwiseRotationTrap(), new CounterclockwiseRotationTrap(),
            new UTurnTrap(), new ConfusionTrap(), new DizzinessTrap(),
            new DrunkTrap(), new WallMakingTrap(), new RotationTrap(),
            new WarpTrap(), new ArrowTrap(), new MasterWallTrap(),
            new WallTrap0(), new WallTrap1(), new WallTrap2(), new WallTrap3(),
            new WallTrap4(), new WallTrap5(), new WallTrap6(), new WallTrap7(),
            new WallTrap8(), new WallTrap9(), new WallTrap10(),
            new WallTrap11(), new WallTrap12(), new WallTrap13(),
            new WallTrap14(), new WallTrap15(), new WallTrap16(),
            new WallTrap17(), new WallTrap18(), new WallTrap19(),
            new TreasureChest(), new DimnessGem(), new DarknessGem(),
            new LightnessGem(), new BrightnessGem(), new HorizontalBarrier(),
            new VerticalBarrier(), new BarrierGenerator(),
            new EnragedBarrierGenerator(), new IcedBarrierGenerator(),
            new PoisonedBarrierGenerator(), new ShockedBarrierGenerator(),
            new WarpBomb(), new IceBomb(), new FireBomb(), new PoisonBomb(),
            new ShockBomb(), new ShuffleBomb(), new IceBow(), new FireBow(),
            new PoisonBow(), new ShockBow(), new Sign(), new MinorHealPotion(),
            new MinorHurtPotion(), new MinorUnknownPotion(),
            new MajorHealPotion(), new MajorHurtPotion(),
            new MajorUnknownPotion(), new SuperHealPotion(),
            new SuperHurtPotion(), new SuperUnknownPotion(), new CrystalWall(),
            new BlackCrystal(), new BlueCrystal(), new CyanCrystal(),
            new DarkBlueCrystal(), new DarkCyanCrystal(),
            new DarkGrayCrystal(), new DarkGreenCrystal(),
            new DarkMagentaCrystal(), new DarkRedCrystal(),
            new DarkYellowCrystal(), new GrayCrystal(), new GreenCrystal(),
            new LightBlueCrystal(), new LightCyanCrystal(),
            new LightGrayCrystal(), new LightGreenCrystal(),
            new LightMagentaCrystal(), new LightRedCrystal(),
            new LightYellowCrystal(), new MagentaCrystal(),
            new OrangeCrystal(), new PlantCrystal(), new PurpleCrystal(),
            new RedCrystal(), new RoseCrystal(), new SeaweedCrystal(),
            new SkyCrystal(), new WhiteCrystal(), new YellowCrystal(),
            new ArmorShop(), new Bank(), new Healer(), new ItemShop(),
            new Regenerator(), new SocksShop(), new SpellShop(),
            new WeaponsShop() };

    public WorldObject[] getAllObjects() {
        return this.allObjects;
    }

    public String[] getAllNames() {
        final String[] allNames = new String[this.allObjects.length];
        for (int x = 0; x < this.allObjects.length; x++) {
            allNames[x] = this.allObjects[x].getName();
        }
        return allNames;
    }

    public String[] getAllPluralNames() {
        final String[] allNames = new String[this.allObjects.length];
        for (int x = 0; x < this.allObjects.length; x++) {
            allNames[x] = this.allObjects[x].getPluralName();
        }
        return allNames;
    }

    public String[] getAllDescriptions() {
        final String[] allDescriptions = new String[this.allObjects.length];
        for (int x = 0; x < this.allObjects.length; x++) {
            allDescriptions[x] = this.allObjects[x].getDescription();
        }
        return allDescriptions;
    }

    public WorldObject[] getAllObjectsWithRuleSets() {
        final WorldObject[] tempAllObjectsWithRuleSets = new WorldObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].hasRuleSet()) {
                tempAllObjectsWithRuleSets[x] = this.allObjects[x];
            }
        }
        for (final WorldObject tempAllObjectsWithRuleSet : tempAllObjectsWithRuleSets) {
            if (tempAllObjectsWithRuleSet != null) {
                objectCount++;
            }
        }
        final WorldObject[] allObjectsWithRuleSets = new WorldObject[objectCount];
        objectCount = 0;
        for (final WorldObject tempAllObjectsWithRuleSet : tempAllObjectsWithRuleSets) {
            if (tempAllObjectsWithRuleSet != null) {
                allObjectsWithRuleSets[objectCount] = tempAllObjectsWithRuleSet;
                objectCount++;
            }
        }
        return allObjectsWithRuleSets;
    }

    public WorldObject[] getAllObjectsWithoutRuleSets() {
        final WorldObject[] tempAllObjectsWithoutRuleSets = new WorldObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (!this.allObjects[x].hasRuleSet()) {
                tempAllObjectsWithoutRuleSets[x] = this.allObjects[x];
            }
        }
        for (final WorldObject tempAllObjectsWithoutRuleSet : tempAllObjectsWithoutRuleSets) {
            if (tempAllObjectsWithoutRuleSet != null) {
                objectCount++;
            }
        }
        final WorldObject[] allObjectsWithoutRuleSets = new WorldObject[objectCount];
        objectCount = 0;
        for (final WorldObject tempAllObjectsWithoutRuleSet : tempAllObjectsWithoutRuleSets) {
            if (tempAllObjectsWithoutRuleSet != null) {
                allObjectsWithoutRuleSets[objectCount] = tempAllObjectsWithoutRuleSet;
                objectCount++;
            }
        }
        return allObjectsWithoutRuleSets;
    }

    public WorldObject[] getAllGroundLayerObjects() {
        final WorldObject[] tempAllGroundLayerObjects = new WorldObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == WorldConstants.LAYER_GROUND) {
                tempAllGroundLayerObjects[x] = this.allObjects[x];
            }
        }
        for (final WorldObject tempAllGroundLayerObject : tempAllGroundLayerObjects) {
            if (tempAllGroundLayerObject != null) {
                objectCount++;
            }
        }
        final WorldObject[] allGroundLayerObjects = new WorldObject[objectCount];
        objectCount = 0;
        for (final WorldObject tempAllGroundLayerObject : tempAllGroundLayerObjects) {
            if (tempAllGroundLayerObject != null) {
                allGroundLayerObjects[objectCount] = tempAllGroundLayerObject;
                objectCount++;
            }
        }
        return allGroundLayerObjects;
    }

    public WorldObject[] getAllObjectLayerObjects() {
        final WorldObject[] tempAllObjectLayerObjects = new WorldObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == WorldConstants.LAYER_OBJECT) {
                tempAllObjectLayerObjects[x] = this.allObjects[x];
            }
        }
        for (final WorldObject tempAllObjectLayerObject : tempAllObjectLayerObjects) {
            if (tempAllObjectLayerObject != null) {
                objectCount++;
            }
        }
        final WorldObject[] allObjectLayerObjects = new WorldObject[objectCount];
        objectCount = 0;
        for (final WorldObject tempAllObjectLayerObject : tempAllObjectLayerObjects) {
            if (tempAllObjectLayerObject != null) {
                allObjectLayerObjects[objectCount] = tempAllObjectLayerObject;
                objectCount++;
            }
        }
        return allObjectLayerObjects;
    }

    public String[] getAllGroundLayerNames() {
        final String[] tempAllGroundLayerNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == WorldConstants.LAYER_GROUND) {
                tempAllGroundLayerNames[x] = this.allObjects[x].getName();
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
        final String[] tempAllObjectLayerNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == WorldConstants.LAYER_OBJECT) {
                tempAllObjectLayerNames[x] = this.allObjects[x].getName();
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
        final BufferedImageIcon[] allEditorAppearances = new BufferedImageIcon[this.allObjects.length];
        for (int x = 0; x < allEditorAppearances.length; x++) {
            allEditorAppearances[x] = GraphicsManager
                    .getTransformedImage(this.allObjects[x].getName());
        }
        return allEditorAppearances;
    }

    public BufferedImageIcon[] getAllGroundLayerEditorAppearances() {
        final BufferedImageIcon[] tempAllGroundLayerEditorAppearances = new BufferedImageIcon[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == WorldConstants.LAYER_GROUND) {
                tempAllGroundLayerEditorAppearances[x] = GraphicsManager
                        .getTransformedImage(this.allObjects[x].getName());
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
        final BufferedImageIcon[] tempAllObjectLayerEditorAppearances = new BufferedImageIcon[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == WorldConstants.LAYER_OBJECT) {
                tempAllObjectLayerEditorAppearances[x] = GraphicsManager
                        .getTransformedImage(this.allObjects[x].getName());
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

    public BufferedImageIcon[] getAllContainableObjectEditorAppearances() {
        final BufferedImageIcon[] tempAllContainableObjectEditorAppearances = new BufferedImageIcon[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isOfType(TypeConstants.TYPE_CONTAINABLE)) {
                tempAllContainableObjectEditorAppearances[x] = GraphicsManager
                        .getTransformedImage(this.allObjects[x].getName());
            }
        }
        for (final BufferedImageIcon tempAllContainableObjectEditorAppearance : tempAllContainableObjectEditorAppearances) {
            if (tempAllContainableObjectEditorAppearance != null) {
                objectCount++;
            }
        }
        final BufferedImageIcon[] allContainableObjectEditorAppearances = new BufferedImageIcon[objectCount];
        objectCount = 0;
        for (final BufferedImageIcon tempAllContainableObjectEditorAppearance : tempAllContainableObjectEditorAppearances) {
            if (tempAllContainableObjectEditorAppearance != null) {
                allContainableObjectEditorAppearances[objectCount] = tempAllContainableObjectEditorAppearance;
                objectCount++;
            }
        }
        return allContainableObjectEditorAppearances;
    }

    public WorldObject[] getAllContainableObjects() {
        final WorldObject[] tempAllContainableObjects = new WorldObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isOfType(TypeConstants.TYPE_CONTAINABLE)) {
                tempAllContainableObjects[x] = this.allObjects[x];
            }
        }
        for (final WorldObject tempAllContainableObject : tempAllContainableObjects) {
            if (tempAllContainableObject != null) {
                objectCount++;
            }
        }
        final WorldObject[] allContainableObjects = new WorldObject[objectCount];
        objectCount = 0;
        for (final WorldObject tempAllContainableObject : tempAllContainableObjects) {
            if (tempAllContainableObject != null) {
                allContainableObjects[objectCount] = tempAllContainableObject;
                objectCount++;
            }
        }
        return allContainableObjects;
    }

    public String[] getAllContainableNames() {
        final String[] tempAllContainableNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isOfType(TypeConstants.TYPE_CONTAINABLE)) {
                tempAllContainableNames[x] = this.allObjects[x].getName();
            }
        }
        for (final String tempAllContainableName : tempAllContainableNames) {
            if (tempAllContainableName != null) {
                objectCount++;
            }
        }
        final String[] allContainableNames = new String[objectCount];
        objectCount = 0;
        for (final String tempAllContainableName : tempAllContainableNames) {
            if (tempAllContainableName != null) {
                allContainableNames[objectCount] = tempAllContainableName;
                objectCount++;
            }
        }
        return allContainableNames;
    }

    public WorldObject[] getAllInventoryableObjects() {
        final WorldObject[] tempAllInventoryableObjects = new WorldObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isInventoryable()) {
                tempAllInventoryableObjects[x] = this.allObjects[x];
            }
        }
        for (final WorldObject tempAllInventoryableObject : tempAllInventoryableObjects) {
            if (tempAllInventoryableObject != null) {
                objectCount++;
            }
        }
        final WorldObject[] allInventoryableObjects = new WorldObject[objectCount];
        objectCount = 0;
        for (final WorldObject tempAllInventoryableObject : tempAllInventoryableObjects) {
            if (tempAllInventoryableObject != null) {
                allInventoryableObjects[objectCount] = tempAllInventoryableObject;
                objectCount++;
            }
        }
        return allInventoryableObjects;
    }

    public String[] getAllInventoryableNames() {
        final String[] tempAllInventoryableNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isInventoryable()) {
                tempAllInventoryableNames[x] = this.allObjects[x].getName();
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

    public String[] getAllInventoryablePluralNames() {
        final String[] tempAllInventoryableNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isInventoryable()) {
                tempAllInventoryableNames[x] = this.allObjects[x]
                        .getPluralName();
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

    public WorldObject[] getAllInventoryableObjectsMinusBoots() {
        final WorldObject[] tempAllInventoryableObjects = new WorldObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isInventoryable()
                    && !this.allObjects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllInventoryableObjects[x] = this.allObjects[x];
            }
        }
        for (final WorldObject tempAllInventoryableObject : tempAllInventoryableObjects) {
            if (tempAllInventoryableObject != null) {
                objectCount++;
            }
        }
        final WorldObject[] allInventoryableObjects = new WorldObject[objectCount];
        objectCount = 0;
        for (final WorldObject tempAllInventoryableObject : tempAllInventoryableObjects) {
            if (tempAllInventoryableObject != null) {
                allInventoryableObjects[objectCount] = tempAllInventoryableObject;
                objectCount++;
            }
        }
        return allInventoryableObjects;
    }

    public String[] getAllInventoryableNamesMinusBoots() {
        final String[] tempAllInventoryableNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isInventoryable()
                    && !this.allObjects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllInventoryableNames[x] = this.allObjects[x].getName();
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

    public String[] getAllInventoryablePluralNamesMinusBoots() {
        final String[] tempAllInventoryableNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isInventoryable()
                    && !this.allObjects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllInventoryableNames[x] = this.allObjects[x]
                        .getPluralName();
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

    public WorldObject[] getAllProgrammableKeys() {
        final WorldObject[] tempAllProgrammableKeys = new WorldObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x]
                    .isOfType(TypeConstants.TYPE_PROGRAMMABLE_KEY)) {
                tempAllProgrammableKeys[x] = this.allObjects[x];
            }
        }
        for (final WorldObject tempAllProgrammableKey : tempAllProgrammableKeys) {
            if (tempAllProgrammableKey != null) {
                objectCount++;
            }
        }
        final WorldObject[] allProgrammableKeys = new WorldObject[objectCount];
        objectCount = 0;
        for (final WorldObject tempAllProgrammableKey : tempAllProgrammableKeys) {
            if (tempAllProgrammableKey != null) {
                allProgrammableKeys[objectCount] = tempAllProgrammableKey;
                objectCount++;
            }
        }
        return allProgrammableKeys;
    }

    public String[] getAllProgrammableKeyNames() {
        final String[] tempAllProgrammableKeyNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x]
                    .isOfType(TypeConstants.TYPE_PROGRAMMABLE_KEY)) {
                tempAllProgrammableKeyNames[x] = this.allObjects[x].getName();
            }
        }
        for (final String tempAllProgrammableKeyName : tempAllProgrammableKeyNames) {
            if (tempAllProgrammableKeyName != null) {
                objectCount++;
            }
        }
        final String[] allProgrammableKeyNames = new String[objectCount];
        objectCount = 0;
        for (final String tempAllProgrammableKeyName : tempAllProgrammableKeyNames) {
            if (tempAllProgrammableKeyName != null) {
                allProgrammableKeyNames[objectCount] = tempAllProgrammableKeyName;
                objectCount++;
            }
        }
        return allProgrammableKeyNames;
    }

    public WorldObject[] getAllUsableObjects() {
        final WorldObject[] tempAllUsableObjects = new WorldObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isUsable()) {
                tempAllUsableObjects[x] = this.allObjects[x];
            }
        }
        for (final WorldObject tempAllUsableObject : tempAllUsableObjects) {
            if (tempAllUsableObject != null) {
                objectCount++;
            }
        }
        final WorldObject[] allUsableObjects = new WorldObject[objectCount];
        objectCount = 0;
        for (final WorldObject tempAllUsableObject : tempAllUsableObjects) {
            if (tempAllUsableObject != null) {
                allUsableObjects[objectCount] = tempAllUsableObject;
                objectCount++;
            }
        }
        return allUsableObjects;
    }

    public String[] getAllUsableNames() {
        final String[] tempAllUsableNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isUsable()) {
                tempAllUsableNames[x] = this.allObjects[x].getName();
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

    public String[] getAllUsablePluralNames() {
        final String[] tempAllUsableNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isUsable()) {
                tempAllUsableNames[x] = this.allObjects[x].getPluralName();
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

    public final WorldObject[] getAllRequired(final int layer) {
        final WorldObject[] tempAllRequired = new WorldObject[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == layer
                    && this.allObjects[x].isRequired()) {
                tempAllRequired[count] = this.allObjects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final WorldObject[] allRequired = new WorldObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final WorldObject[] getAllWithoutPrerequisiteAndNotRequired(
            final int layer) {
        final WorldObject[] tempAllWithoutPrereq = new WorldObject[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == layer
                    && !this.allObjects[x].hasPrerequisite()
                    && !this.allObjects[x].isRequired()) {
                tempAllWithoutPrereq[count] = this.allObjects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final WorldObject[] allWithoutPrereq = new WorldObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final static WorldObject[] getAllRequiredSubset(final WorldObject[] objs,
            final int layer) {
        if (objs == null) {
            return null;
        }
        final WorldObject[] tempAllRequired = new WorldObject[objs.length];
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
            final WorldObject[] allRequired = new WorldObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final static WorldObject[] getAllWithoutPrerequisiteAndNotRequiredSubset(
            final WorldObject[] objs, final int layer) {
        if (objs == null) {
            return null;
        }
        final WorldObject[] tempAllWithoutPrereq = new WorldObject[objs.length];
        int x;
        int count = 0;
        for (x = 0; x < objs.length; x++) {
            if (objs[x].hasRuleSet()) {
                if (objs[x].getLayer() == layer
                        && !objs[x].getRuleSet().isRequired()
                        && !objs[x].hasPrerequisite()) {
                    tempAllWithoutPrereq[count] = objs[x];
                    count++;
                }
            } else {
                if (objs[x].getLayer() == layer && !objs[x].isRequired()
                        && !objs[x].hasPrerequisite()) {
                    tempAllWithoutPrereq[count] = objs[x];
                    count++;
                }
            }
        }
        if (count == 0) {
            return null;
        } else {
            final WorldObject[] allWithoutPrereq = new WorldObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final WorldObject[] getAllWithNthPrerequisite(final int layer,
            final int N) {
        final WorldObject[] tempAllWithNthPrereq = new WorldObject[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == layer
                    && !this.allObjects[x].isRequired()
                    && this.allObjects[x].hasNthPrerequisite(N)) {
                tempAllWithNthPrereq[count] = this.allObjects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final WorldObject[] allWithNthPrereq = new WorldObject[count];
            for (x = 0; x < count; x++) {
                allWithNthPrereq[x] = tempAllWithNthPrereq[x];
            }
            return allWithNthPrereq;
        }
    }

    public final static WorldObject[] getAllWithNthPrerequisiteSubset(
            final WorldObject[] objs, final int layer, final int N) {
        if (objs == null) {
            return null;
        }
        final WorldObject[] tempAllWithNthPrereq = new WorldObject[objs.length];
        int x;
        int count = 0;
        for (x = 0; x < objs.length; x++) {
            if (objs[x].getLayer() == layer && objs[x].hasNthPrerequisite(N)) {
                tempAllWithNthPrereq[count] = objs[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final WorldObject[] allWithNthPrereq = new WorldObject[count];
            for (x = 0; x < count; x++) {
                allWithNthPrereq[x] = tempAllWithNthPrereq[x];
            }
            return allWithNthPrereq;
        }
    }

    public final WorldObject getNewInstanceByName(final String name) {
        WorldObject instance = null;
        int x;
        for (x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getName().equals(name)) {
                instance = this.allObjects[x];
                break;
            }
        }
        if (instance == null) {
            return null;
        } else {
            try {
                return instance.getClass().newInstance();
            } catch (final IllegalAccessException iae) {
                return null;
            } catch (final InstantiationException ie) {
                return null;
            }
        }
    }

    public WorldObject readWorldObject(final DataReader reader,
            final int formatVersion) throws IOException {
        WorldObject o = null;
        int UID = 0;
        if (formatVersion == FormatConstants.WORLD_FORMAT_1) {
            UID = reader.readInt();
        }
        for (final WorldObject allObject : this.allObjects) {
            try {
                final WorldObject instance = allObject.getClass().newInstance();
                if (formatVersion == FormatConstants.WORLD_FORMAT_1) {
                    o = instance.readWorldObject(reader, UID);
                }
                if (o != null) {
                    return o;
                }
            } catch (final InstantiationException ex) {
                Worldz.getDebug().debug(ex);
            } catch (final IllegalAccessException ex) {
                Worldz.getDebug().debug(ex);
            }
        }
        return null;
    }

    public void readRuleSet(final DataReader reader) throws IOException {
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
                this.allObjects[x].giveRuleSet();
                this.allObjects[x].getRuleSet().readRuleSet(reader);
            }
        }
    }

    public void writeRuleSet(final DataWriter writer) throws IOException {
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
                this.allObjects[x].getRuleSet().writeRuleSet(writer);
            }
        }
    }

    private boolean[] generateMap() {
        final boolean[] map = new boolean[this.allObjects.length];
        for (int x = 0; x < map.length; x++) {
            if (this.allObjects[x].hasRuleSet()) {
                map[x] = true;
            } else {
                map[x] = false;
            }
        }
        return map;
    }
}
