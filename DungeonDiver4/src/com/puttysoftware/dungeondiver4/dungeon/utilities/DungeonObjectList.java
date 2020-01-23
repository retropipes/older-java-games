/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.utilities;

import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.FormatConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.objects.*;
import com.puttysoftware.dungeondiver4.resourcemanagers.ImageTransformer;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageManager;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class DungeonObjectList {
    // Fields
    private final ArrayList<AbstractDungeonObject> allObjectList;
    private static final String OBJECT_GROUP = "object";
    private static final String OBJECT_ID_GROUP = "identifier";

    // Constructor
    public DungeonObjectList() {
        AbstractDungeonObject[] allObjects = { new Amethyst(),
                new AnnihilationWand(), new APlug(), new APort(),
                new AquaBoots(), new ArmorShop(), new ArrowTrap(), new Axe(),
                new Bank(), new BarrierGenerator(), new BioHazardBoots(),
                new BlackCrystal(), new BlockTeleport(), new BlueButton(),
                new BlueCarpet(), new BlueCrystal(), new BlueKey(),
                new BlueLock(), new BlueWallOff(), new BlueWallOn(),
                new Bolt(), new BoltWall(), new Bomb(), new BPlug(),
                new BPort(), new Bracer(), new BracerWall(),
                new BreakableWallHorizontal(), new BreakableWallVertical(),
                new BrickWall(), new BridgedLavaHorizontal(),
                new BridgedLavaVertical(), new BridgedSlimeHorizontal(),
                new BridgedSlimeVertical(), new BridgedWaterHorizontal(),
                new BridgedWaterVertical(), new BrightnessGem(), new Cap(),
                new CapWall(), new ChainTeleport(),
                new ClockwiseRotationTrap(), new ClosedDoor(),
                new ConditionalChainTeleport(), new ConditionalTeleport(),
                new ConfusionTrap(), new ControllableTeleport(),
                new CounterclockwiseRotationTrap(), new CounterpoisonAmulet(),
                new CPlug(), new CPort(), new CrackedWall(), new Crevasse(),
                new CrumblingWall(), new CrystalWall(), new CutTree(),
                new CyanButton(), new CyanCarpet(), new CyanCrystal(),
                new CyanKey(), new CyanLock(), new CyanWallOff(),
                new CyanWallOn(), new Dagger(), new DaggerWall(),
                new DamageableWall(), new DamagedWall(), new DarkBlueCrystal(),
                new DarkCyanCrystal(), new DarkGem(), new DarkGrayCrystal(),
                new DarkGreenCrystal(), new DarkMagentaCrystal(),
                new DarknessGem(), new DarkRedCrystal(), new DarkWand(),
                new DarkYellowCrystal(), new Diamond(), new DimnessGem(),
                new Dirt(), new DisarmTrapWand(), new DizzinessTrap(),
                new DPlug(), new DPort(), new DrunkTrap(), new Empty(),
                new EmptyVoid(), new EnergySphere(), new EnhancementShop(),
                new EnragedBarrierGenerator(), new EPlug(), new EPort(),
                new ExperienceBoots(), new ExplodingWall(), new Explore(),
                new ExploreWall(), new FadingWall(), new FaithPowerShop(),
                new FakeWall(), new FireAmulet(), new FireBomb(),
                new FireBoots(), new FireBow(), new ForceField(), new FPlug(),
                new FPort(), new GarnetSquare(), new GarnetWall(),
                new GhostAmulet(), new GhostBow(), new Glove(),
                new GloveWall(), new GlueBoots(), new GoldenSquare(),
                new GoldenWall(), new GPlug(), new GPort(), new Grass(),
                new GrayCrystal(), new GreenButton(), new GreenCarpet(),
                new GreenCrystal(), new GreenKey(), new GreenLock(),
                new GreenWallOff(), new GreenWallOn(), new Hammer(),
                new HealBoots(), new HealShop(), new HealTrap(),
                new HorizontalBarrier(), new HotBoots(), new HotRock(),
                new HPlug(), new HPort(), new HurtTrap(), new Ice(),
                new IceAmulet(), new IceBomb(), new IceBow(),
                new IcedBarrierGenerator(), new InvisibleBlockTeleport(),
                new InvisibleChainTeleport(),
                new InvisibleConditionalChainTeleport(),
                new InvisibleConditionalTeleport(),
                new InvisibleOneShotChainTeleport(),
                new InvisibleOneShotConditionalTeleport(),
                new InvisibleOneShotTeleport(), new InvisiblePit(),
                new InvisibleSpringboard(), new InvisibleTeleport(),
                new InvisibleWall(), new IPlug(), new IPort(), new ItemShop(),
                new JPlug(), new JPort(), new Key(), new KPlug(), new KPort(),
                new Lava(), new LightBlueCrystal(), new LightCyanCrystal(),
                new LightGem(), new LightGrayCrystal(),
                new LightGreenCrystal(), new LightMagentaCrystal(),
                new LightnessGem(), new LightRedCrystal(), new LightWand(),
                new LightYellowCrystal(), new Lock(), new LPlug(), new LPort(),
                new MagentaButton(), new MagentaCarpet(), new MagentaCrystal(),
                new MagentaKey(), new MagentaLock(), new MagentaWallOff(),
                new MagentaWallOn(), new MajorDrainPotion(),
                new MajorHealPotion(), new MajorHurtPotion(),
                new MajorRandomPotion(), new MajorRegenPotion(),
                new MajorUnknownPotion(), new MasterTrappedWall(),
                new MasterWallTrap(), new MetalBoots(), new MetalButton(),
                new MetalDoor(), new MetalKey(), new MinorDrainPotion(),
                new MinorHealPotion(), new MinorHurtPotion(),
                new MinorRandomPotion(), new MinorRegenPotion(),
                new MinorUnknownPotion(), new MoneyBoots(),
                new MonsterObject(), new MoonDoor(), new MoonStone(),
                new Move(), new MoveWall(), new MovingBlock(), new MPlug(),
                new MPort(), new Necklace(), new NecklaceWall(), new NoBlock(),
                new NoBoots(), new NoPlayer(), new NormalAmulet(), new NPlug(),
                new NPort(), new NWayTeleport(), new OneShotChainTeleport(),
                new OneShotConditionalTeleport(),
                new OneShotControllableTeleport(), new OneShotTeleport(),
                new OneWayEastWall(), new OneWayNorthWall(),
                new OneWaySouthWall(), new OneWayWestWall(), new OpenDoor(),
                new OPlug(), new OPort(), new OrangeButton(),
                new OrangeCarpet(), new OrangeCrystal(), new OrangeKey(),
                new OrangeLock(), new OrangeWallOff(), new OrangeWallOn(),
                new PasswallBoots(), new Pit(), new PlantCrystal(),
                new PoisonBomb(), new PoisonBow(),
                new PoisonedBarrierGenerator(), new PoisonousAmulet(),
                new PPlug(), new PPort(), new PullableBlock(),
                new PullableBlockOnce(), new PullableBlockThrice(),
                new PullableBlockTwice(), new PurpleButton(),
                new PurpleCarpet(), new PurpleCrystal(), new PurpleKey(),
                new PurpleLock(), new PurpleWallOff(), new PurpleWallOn(),
                new PushableBlock(), new PushableBlockOnce(),
                new PushableBlockThrice(), new PushableBlockTwice(),
                new PushablePullableBlock(), new QPlug(), new QPort(),
                new QuakeBomb(), new RandomInvisibleOneShotTeleport(),
                new RandomInvisibleTeleport(), new RandomOneShotTeleport(),
                new RandomTeleport(), new RedButton(), new RedCarpet(),
                new RedCrystal(), new RedKey(), new RedLock(),
                new RedWallOff(), new RedWallOn(), new RegenBoots(),
                new Regenerator(), new RemoteActionWand(), new RoseButton(),
                new RoseCarpet(), new RoseCrystal(), new RoseKey(),
                new RoseLock(), new RoseWallOff(), new RoseWallOn(),
                new RotationTrap(), new RotationWand(), new RPlug(),
                new RPort(), new Ruby(), new RubySquare(), new RubyWall(),
                new Sand(), new Sapphire(), new SapphireSquare(),
                new SapphireWall(), new SealedFinish(), new SealingWall(),
                new SeaweedButton(), new SeaweedCarpet(), new SeaweedCrystal(),
                new SeaweedKey(), new SeaweedLock(), new SeaweedWallOff(),
                new SeaweedWallOn(), new Shield(), new ShieldWall(),
                new ShockBomb(), new ShockBow(), new ShockedBarrierGenerator(),
                new ShuffleBomb(), new Sign(), new SilverSquare(),
                new SilverWall(), new SkyButton(), new SkyCarpet(),
                new SkyCrystal(), new SkyKey(), new SkyLock(),
                new SkyWallOff(), new SkyWallOn(), new Slime(),
                new SlipperyBoots(), new SmokeBomb(), new Snow(),
                new SocksShop(), new SpellShop(), new SPlug(), new SPort(),
                new Springboard(), new Staff(), new StaffWall(),
                new StairsDown(), new StairsUp(), new Stump(), new Suit(),
                new SuitWall(), new SunDoor(), new SunkenBlock(),
                new SunStone(), new SuperDrainPotion(), new SuperHealPotion(),
                new SuperHurtPotion(), new SuperRandomPotion(),
                new SuperRegenPotion(), new SuperUnknownPotion(), new Sword(),
                new SwordWall(), new Tablet(), new TabletSlot(),
                new Teleport(), new TeleportWand(), new Tile(),
                new TopazSquare(), new TopazWall(), new TPlug(), new TPort(),
                new TrappedWall0(), new TrappedWall1(), new TrappedWall10(),
                new TrappedWall11(), new TrappedWall12(), new TrappedWall13(),
                new TrappedWall14(), new TrappedWall15(), new TrappedWall16(),
                new TrappedWall17(), new TrappedWall18(), new TrappedWall19(),
                new TrappedWall2(), new TrappedWall3(), new TrappedWall4(),
                new TrappedWall5(), new TrappedWall6(), new TrappedWall7(),
                new TrappedWall8(), new TrappedWall9(), new TreasureChest(),
                new TreasureKey(), new Tree(), new TrueSightAmulet(),
                new Tundra(), new TwoWayTeleport(), new UPlug(), new UPort(),
                new UTurnTrap(), new VariableHealTrap(),
                new VariableHurtTrap(), new VerticalBarrier(), new VPlug(),
                new VPort(), new Wall(), new WallBreakingWand(),
                new WallMakingTrap(), new WallMakingWand(), new WallTrap0(),
                new WallTrap1(), new WallTrap10(), new WallTrap11(),
                new WallTrap12(), new WallTrap13(), new WallTrap14(),
                new WallTrap15(), new WallTrap16(), new WallTrap17(),
                new WallTrap18(), new WallTrap19(), new WallTrap2(),
                new WallTrap3(), new WallTrap4(), new WallTrap5(),
                new WallTrap6(), new WallTrap7(), new WallTrap8(),
                new WallTrap9(), new WarpBomb(), new WarpTrap(),
                new WarpWand(), new Water(), new WeaponsShop(),
                new WhiteButton(), new WhiteCarpet(), new WhiteCrystal(),
                new WhiteKey(), new WhiteLock(), new WhiteWallOff(),
                new WhiteWallOn(), new WPlug(), new WPort(), new XPlug(),
                new XPort(), new YellowButton(), new YellowCarpet(),
                new YellowCrystal(), new YellowKey(), new YellowLock(),
                new YellowWallOff(), new YellowWallOn(), new YPlug(),
                new YPort(), new ZPlug(), new ZPort() };
        this.allObjectList = new ArrayList<>();
        // Add all predefined objects to the list
        for (int z = 0; z < allObjects.length; z++) {
            this.allObjectList.add(allObjects[z]);
        }
    }

    // Methods
    public AbstractDungeonObject[] getAllObjects() {
        return this.allObjectList
                .toArray(new AbstractDungeonObject[this.allObjectList.size()]);
    }

    public void addObject(AbstractDungeonObject o) {
        this.allObjectList.add(o);
    }

    public String[] getAllNames() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        String[] allNames = new String[objects.length];
        for (int x = 0; x < objects.length; x++) {
            allNames[x] = objects[x].getName();
        }
        return allNames;
    }

    public String[] getAllDescriptions() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        String[] allDescriptions = new String[objects.length];
        for (int x = 0; x < objects.length; x++) {
            allDescriptions[x] = objects[x].getDescription();
        }
        return allDescriptions;
    }

    public AbstractDungeonObject[] getAllObjectsWithRuleSets() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllObjectsWithRuleSets = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].hasRuleSet()) {
                tempAllObjectsWithRuleSets[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllObjectsWithRuleSets.length; x++) {
            if (tempAllObjectsWithRuleSets[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allObjectsWithRuleSets = new AbstractDungeonObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllObjectsWithRuleSets.length; x++) {
            if (tempAllObjectsWithRuleSets[x] != null) {
                allObjectsWithRuleSets[objectCount] = tempAllObjectsWithRuleSets[x];
                objectCount++;
            }
        }
        return allObjectsWithRuleSets;
    }

    public AbstractDungeonObject[] getAllObjectsWithoutRuleSets() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllObjectsWithoutRuleSets = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (!objects[x].hasRuleSet()) {
                tempAllObjectsWithoutRuleSets[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllObjectsWithoutRuleSets.length; x++) {
            if (tempAllObjectsWithoutRuleSets[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allObjectsWithoutRuleSets = new AbstractDungeonObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllObjectsWithoutRuleSets.length; x++) {
            if (tempAllObjectsWithoutRuleSets[x] != null) {
                allObjectsWithoutRuleSets[objectCount] = tempAllObjectsWithoutRuleSets[x];
                objectCount++;
            }
        }
        return allObjectsWithoutRuleSets;
    }

    public AbstractDungeonObject[] getAllGroundLayerObjects() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllGroundLayerObjects = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == DungeonConstants.LAYER_GROUND) {
                tempAllGroundLayerObjects[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllGroundLayerObjects.length; x++) {
            if (tempAllGroundLayerObjects[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allGroundLayerObjects = new AbstractDungeonObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllGroundLayerObjects.length; x++) {
            if (tempAllGroundLayerObjects[x] != null) {
                allGroundLayerObjects[objectCount] = tempAllGroundLayerObjects[x];
                objectCount++;
            }
        }
        return allGroundLayerObjects;
    }

    public AbstractDungeonObject[] getAllGenerationEligibleTypedObjects() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllGenerationEligibleTypedObjects = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
                tempAllGenerationEligibleTypedObjects[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllGenerationEligibleTypedObjects.length; x++) {
            if (tempAllGenerationEligibleTypedObjects[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allGenerationEligibleTypedObjects = new AbstractDungeonObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllGenerationEligibleTypedObjects.length; x++) {
            if (tempAllGenerationEligibleTypedObjects[x] != null) {
                allGenerationEligibleTypedObjects[objectCount] = tempAllGenerationEligibleTypedObjects[x];
                objectCount++;
            }
        }
        return allGenerationEligibleTypedObjects;
    }

    public AbstractDungeonObject[] getAllGeneratedTypedObjects() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllGeneratedTypedObjects = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_GENERATED)) {
                tempAllGeneratedTypedObjects[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllGeneratedTypedObjects.length; x++) {
            if (tempAllGeneratedTypedObjects[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allGeneratedTypedObjects = new AbstractDungeonObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllGeneratedTypedObjects.length; x++) {
            if (tempAllGeneratedTypedObjects[x] != null) {
                allGeneratedTypedObjects[objectCount] = tempAllGeneratedTypedObjects[x];
                objectCount++;
            }
        }
        return allGeneratedTypedObjects;
    }

    public AbstractDungeonObject[] getAllObjectLayerObjects() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllObjectLayerObjects = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == DungeonConstants.LAYER_OBJECT) {
                tempAllObjectLayerObjects[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllObjectLayerObjects.length; x++) {
            if (tempAllObjectLayerObjects[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allObjectLayerObjects = new AbstractDungeonObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllObjectLayerObjects.length; x++) {
            if (tempAllObjectLayerObjects[x] != null) {
                allObjectLayerObjects[objectCount] = tempAllObjectLayerObjects[x];
                objectCount++;
            }
        }
        return allObjectLayerObjects;
    }

    public String[] getAllGroundLayerNames() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        String[] tempAllGroundLayerNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == DungeonConstants.LAYER_GROUND) {
                tempAllGroundLayerNames[x] = objects[x].getName();
            }
        }
        for (int x = 0; x < tempAllGroundLayerNames.length; x++) {
            if (tempAllGroundLayerNames[x] != null) {
                objectCount++;
            }
        }
        String[] allGroundLayerNames = new String[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllGroundLayerNames.length; x++) {
            if (tempAllGroundLayerNames[x] != null) {
                allGroundLayerNames[objectCount] = tempAllGroundLayerNames[x];
                objectCount++;
            }
        }
        return allGroundLayerNames;
    }

    public String[] getAllObjectLayerNames() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        String[] tempAllObjectLayerNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == DungeonConstants.LAYER_OBJECT) {
                tempAllObjectLayerNames[x] = objects[x].getName();
            }
        }
        for (int x = 0; x < tempAllObjectLayerNames.length; x++) {
            if (tempAllObjectLayerNames[x] != null) {
                objectCount++;
            }
        }
        String[] allObjectLayerNames = new String[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllObjectLayerNames.length; x++) {
            if (tempAllObjectLayerNames[x] != null) {
                allObjectLayerNames[objectCount] = tempAllObjectLayerNames[x];
                objectCount++;
            }
        }
        return allObjectLayerNames;
    }

    public BufferedImageIcon[] getAllEditorAppearances() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        BufferedImageIcon[] allEditorAppearances = new BufferedImageIcon[objects.length];
        for (int x = 0; x < allEditorAppearances.length; x++) {
            allEditorAppearances[x] = ImageTransformer
                    .getTransformedImage(ObjectImageManager.getImage(
                            objects[x].getName(), objects[x].getBaseID(),
                            objects[x].getTemplateColor(),
                            objects[x].getAttributeID(),
                            objects[x].getAttributeTemplateColor()));
        }
        return allEditorAppearances;
    }

    public BufferedImageIcon[] getAllGroundLayerEditorAppearances() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        BufferedImageIcon[] tempAllGroundLayerEditorAppearances = new BufferedImageIcon[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == DungeonConstants.LAYER_GROUND) {
                tempAllGroundLayerEditorAppearances[x] = ImageTransformer
                        .getTransformedImage(ObjectImageManager.getImage(
                                objects[x].getName(), objects[x].getBaseID(),
                                objects[x].getTemplateColor(),
                                objects[x].getAttributeID(),
                                objects[x].getAttributeTemplateColor()));
            }
        }
        for (int x = 0; x < tempAllGroundLayerEditorAppearances.length; x++) {
            if (tempAllGroundLayerEditorAppearances[x] != null) {
                objectCount++;
            }
        }
        BufferedImageIcon[] allGroundLayerEditorAppearances = new BufferedImageIcon[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllGroundLayerEditorAppearances.length; x++) {
            if (tempAllGroundLayerEditorAppearances[x] != null) {
                allGroundLayerEditorAppearances[objectCount] = tempAllGroundLayerEditorAppearances[x];
                objectCount++;
            }
        }
        return allGroundLayerEditorAppearances;
    }

    public BufferedImageIcon[] getAllObjectLayerEditorAppearances() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        BufferedImageIcon[] tempAllObjectLayerEditorAppearances = new BufferedImageIcon[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == DungeonConstants.LAYER_OBJECT) {
                tempAllObjectLayerEditorAppearances[x] = ImageTransformer
                        .getTransformedImage(ObjectImageManager.getImage(
                                objects[x].getName(), objects[x].getBaseID(),
                                objects[x].getTemplateColor(),
                                objects[x].getAttributeID(),
                                objects[x].getAttributeTemplateColor()));
            }
        }
        for (int x = 0; x < tempAllObjectLayerEditorAppearances.length; x++) {
            if (tempAllObjectLayerEditorAppearances[x] != null) {
                objectCount++;
            }
        }
        BufferedImageIcon[] allObjectLayerEditorAppearances = new BufferedImageIcon[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllObjectLayerEditorAppearances.length; x++) {
            if (tempAllObjectLayerEditorAppearances[x] != null) {
                allObjectLayerEditorAppearances[objectCount] = tempAllObjectLayerEditorAppearances[x];
                objectCount++;
            }
        }
        return allObjectLayerEditorAppearances;
    }

    public BufferedImageIcon[] getAllContainableObjectEditorAppearances() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        BufferedImageIcon[] tempAllContainableObjectEditorAppearances = new BufferedImageIcon[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_CONTAINABLE)) {
                tempAllContainableObjectEditorAppearances[x] = ImageTransformer
                        .getTransformedImage(ObjectImageManager.getImage(
                                objects[x].getName(), objects[x].getBaseID(),
                                objects[x].getTemplateColor(),
                                objects[x].getAttributeID(),
                                objects[x].getAttributeTemplateColor()));
            }
        }
        for (int x = 0; x < tempAllContainableObjectEditorAppearances.length; x++) {
            if (tempAllContainableObjectEditorAppearances[x] != null) {
                objectCount++;
            }
        }
        BufferedImageIcon[] allContainableObjectEditorAppearances = new BufferedImageIcon[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllContainableObjectEditorAppearances.length; x++) {
            if (tempAllContainableObjectEditorAppearances[x] != null) {
                allContainableObjectEditorAppearances[objectCount] = tempAllContainableObjectEditorAppearances[x];
                objectCount++;
            }
        }
        return allContainableObjectEditorAppearances;
    }

    public AbstractDungeonObject[] getAllContainableObjects() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllContainableObjects = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_CONTAINABLE)) {
                tempAllContainableObjects[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllContainableObjects.length; x++) {
            if (tempAllContainableObjects[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allContainableObjects = new AbstractDungeonObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllContainableObjects.length; x++) {
            if (tempAllContainableObjects[x] != null) {
                allContainableObjects[objectCount] = tempAllContainableObjects[x];
                objectCount++;
            }
        }
        return allContainableObjects;
    }

    public String[] getAllContainableNames() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        String[] tempAllContainableNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_CONTAINABLE)) {
                tempAllContainableNames[x] = objects[x].getName();
            }
        }
        for (int x = 0; x < tempAllContainableNames.length; x++) {
            if (tempAllContainableNames[x] != null) {
                objectCount++;
            }
        }
        String[] allContainableNames = new String[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllContainableNames.length; x++) {
            if (tempAllContainableNames[x] != null) {
                allContainableNames[objectCount] = tempAllContainableNames[x];
                objectCount++;
            }
        }
        return allContainableNames;
    }

    public AbstractDungeonObject[] getAllInventoryableObjectsMinusSpecial() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllInventoryableObjects = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isInventoryable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOW)
                    && !objects[x].isOfType(TypeConstants.TYPE_AMULET)
                    && !objects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllInventoryableObjects[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllInventoryableObjects.length; x++) {
            if (tempAllInventoryableObjects[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allInventoryableObjects = new AbstractDungeonObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllInventoryableObjects.length; x++) {
            if (tempAllInventoryableObjects[x] != null) {
                allInventoryableObjects[objectCount] = tempAllInventoryableObjects[x];
                objectCount++;
            }
        }
        return allInventoryableObjects;
    }

    public String[] getAllInventoryableNamesMinusSpecial() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        String[] tempAllInventoryableNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isInventoryable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOW)
                    && !objects[x].isOfType(TypeConstants.TYPE_AMULET)
                    && !objects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllInventoryableNames[x] = objects[x].getName();
            }
        }
        for (int x = 0; x < tempAllInventoryableNames.length; x++) {
            if (tempAllInventoryableNames[x] != null) {
                objectCount++;
            }
        }
        String[] allInventoryableNames = new String[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllInventoryableNames.length; x++) {
            if (tempAllInventoryableNames[x] != null) {
                allInventoryableNames[objectCount] = tempAllInventoryableNames[x];
                objectCount++;
            }
        }
        return allInventoryableNames;
    }

    public AbstractDungeonObject[] getAllProgrammableKeys() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllProgrammableKeys = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_PROGRAMMABLE_KEY)) {
                tempAllProgrammableKeys[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllProgrammableKeys.length; x++) {
            if (tempAllProgrammableKeys[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allProgrammableKeys = new AbstractDungeonObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllProgrammableKeys.length; x++) {
            if (tempAllProgrammableKeys[x] != null) {
                allProgrammableKeys[objectCount] = tempAllProgrammableKeys[x];
                objectCount++;
            }
        }
        return allProgrammableKeys;
    }

    public String[] getAllProgrammableKeyNames() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        String[] tempAllProgrammableKeyNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_PROGRAMMABLE_KEY)) {
                tempAllProgrammableKeyNames[x] = objects[x].getName();
            }
        }
        for (int x = 0; x < tempAllProgrammableKeyNames.length; x++) {
            if (tempAllProgrammableKeyNames[x] != null) {
                objectCount++;
            }
        }
        String[] allProgrammableKeyNames = new String[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllProgrammableKeyNames.length; x++) {
            if (tempAllProgrammableKeyNames[x] != null) {
                allProgrammableKeyNames[objectCount] = tempAllProgrammableKeyNames[x];
                objectCount++;
            }
        }
        return allProgrammableKeyNames;
    }

    public AbstractDungeonObject[] getAllUsableObjects() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllUsableObjects = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isUsable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOW)
                    && !objects[x].isOfType(TypeConstants.TYPE_AMULET)
                    && !objects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllUsableObjects[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllUsableObjects.length; x++) {
            if (tempAllUsableObjects[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allUsableObjects = new AbstractDungeonObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllUsableObjects.length; x++) {
            if (tempAllUsableObjects[x] != null) {
                allUsableObjects[objectCount] = tempAllUsableObjects[x];
                objectCount++;
            }
        }
        return allUsableObjects;
    }

    public String[] getAllUsableNamesMinusSpecial() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        String[] tempAllUsableNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isUsable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOW)
                    && !objects[x].isOfType(TypeConstants.TYPE_AMULET)
                    && !objects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllUsableNames[x] = objects[x].getName();
            }
        }
        for (int x = 0; x < tempAllUsableNames.length; x++) {
            if (tempAllUsableNames[x] != null) {
                objectCount++;
            }
        }
        String[] allUsableNames = new String[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllUsableNames.length; x++) {
            if (tempAllUsableNames[x] != null) {
                allUsableNames[objectCount] = tempAllUsableNames[x];
                objectCount++;
            }
        }
        return allUsableNames;
    }

    public AbstractDungeonObject[] getAllBows() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllUsableObjects = new AbstractDungeonObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_BOW)) {
                tempAllUsableObjects[x] = objects[x];
            }
        }
        for (int x = 0; x < tempAllUsableObjects.length; x++) {
            if (tempAllUsableObjects[x] != null) {
                objectCount++;
            }
        }
        AbstractDungeonObject[] allUsableObjects = new AbstractDungeonObject[objectCount + 1];
        objectCount = 0;
        for (int x = 0; x < tempAllUsableObjects.length - 1; x++) {
            if (tempAllUsableObjects[x] != null) {
                allUsableObjects[objectCount] = tempAllUsableObjects[x];
                objectCount++;
            }
        }
        allUsableObjects[allUsableObjects.length - 1] = new Bow();
        return allUsableObjects;
    }

    public String[] getAllBowNames() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        String[] tempAllUsableNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_BOW)) {
                tempAllUsableNames[x] = objects[x].getName();
            }
        }
        for (int x = 0; x < tempAllUsableNames.length; x++) {
            if (tempAllUsableNames[x] != null) {
                objectCount++;
            }
        }
        String[] allUsableNames = new String[objectCount + 1];
        objectCount = 0;
        for (int x = 0; x < tempAllUsableNames.length - 1; x++) {
            if (tempAllUsableNames[x] != null) {
                allUsableNames[objectCount] = tempAllUsableNames[x];
                objectCount++;
            }
        }
        allUsableNames[allUsableNames.length - 1] = new Bow().getName();
        return allUsableNames;
    }

    public final AbstractDungeonObject[] getAllRequired(int layer) {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllRequired = new AbstractDungeonObject[objects.length];
        int x;
        int count = 0;
        for (x = 0; x < objects.length; x++) {
            if ((objects[x].getLayer() == layer) && objects[x].isRequired()) {
                tempAllRequired[count] = objects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            AbstractDungeonObject[] allRequired = new AbstractDungeonObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final AbstractDungeonObject[] getAllWithoutPrerequisiteAndNotRequired(
            int layer) {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject[] tempAllWithoutPrereq = new AbstractDungeonObject[objects.length];
        int x;
        int count = 0;
        for (x = 0; x < objects.length; x++) {
            if ((objects[x].getLayer() == layer) && !(objects[x].isRequired())) {
                tempAllWithoutPrereq[count] = objects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            AbstractDungeonObject[] allWithoutPrereq = new AbstractDungeonObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final AbstractDungeonObject[] getAllRequiredInBattle(int layer) {
        AbstractDungeonObject[] objs = this.getAllObjects();
        AbstractDungeonObject[] tempAllRequired = new AbstractDungeonObject[objs.length];
        int x;
        int count = 0;
        for (x = 0; x < objs.length; x++) {
            if ((objs[x].getLayer() == layer) && objs[x].isRequiredInBattle()) {
                tempAllRequired[count] = objs[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            AbstractDungeonObject[] allRequired = new AbstractDungeonObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final AbstractDungeonObject[] getAllNotRequiredInBattle(int layer) {
        AbstractDungeonObject[] objs = this.getAllObjects();
        AbstractDungeonObject[] tempAllWithoutPrereq = new AbstractDungeonObject[objs.length];
        int x;
        int count = 0;
        for (x = 0; x < objs.length; x++) {
            if ((objs[x].getLayer() == layer)
                    && !(objs[x].isRequiredInBattle())) {
                tempAllWithoutPrereq[count] = objs[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            AbstractDungeonObject[] allWithoutPrereq = new AbstractDungeonObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public static final AbstractDungeonObject[] getAllRequiredSubset(
            AbstractDungeonObject[] objs, int layer) {
        if (objs == null) {
            return null;
        }
        AbstractDungeonObject[] tempAllRequired = new AbstractDungeonObject[objs.length];
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
            AbstractDungeonObject[] allRequired = new AbstractDungeonObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public static final AbstractDungeonObject[] getAllWithoutPrerequisiteAndNotRequiredSubset(
            AbstractDungeonObject[] objs, int layer) {
        if (objs == null) {
            return null;
        }
        AbstractDungeonObject[] tempAllWithoutPrereq = new AbstractDungeonObject[objs.length];
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
            AbstractDungeonObject[] allWithoutPrereq = new AbstractDungeonObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final AbstractDungeonObject getNewInstanceByName(String name) {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject instance = null;
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

    public AbstractDungeonObject readDungeonObject(XDataReader reader,
            int formatVersion) throws IOException {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject o = null;
        String UID = "";
        if (formatVersion == FormatConstants.DUNGEON_FORMAT_1) {
            reader.readOpeningGroup(OBJECT_GROUP);
            reader.readOpeningGroup(OBJECT_ID_GROUP);
            UID = reader.readString();
            reader.readClosingGroup(OBJECT_ID_GROUP);
        }
        for (int x = 0; x < objects.length; x++) {
            try {
                AbstractDungeonObject instance;
                if (objects[x].isOfType(TypeConstants.TYPE_GENERATED)) {
                    instance = objects[x].clone();
                } else {
                    instance = objects[x].getClass().newInstance();
                }
                if (formatVersion == FormatConstants.DUNGEON_FORMAT_1) {
                    o = instance.readDungeonObjectV1(reader, UID);
                    if (o != null) {
                        reader.readClosingGroup(OBJECT_GROUP);
                        return o;
                    }
                }
            } catch (InstantiationException ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
            } catch (IllegalAccessException ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
            }
        }
        return null;
    }

    public AbstractDungeonObject readSavedDungeonObject(XDataReader reader,
            String UID, int formatVersion) throws IOException {
        AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject o = null;
        for (int x = 0; x < objects.length; x++) {
            try {
                AbstractDungeonObject instance;
                if (objects[x].isOfType(TypeConstants.TYPE_GENERATED)) {
                    instance = objects[x].clone();
                } else {
                    instance = objects[x].getClass().newInstance();
                }
                if (formatVersion == FormatConstants.DUNGEON_FORMAT_1) {
                    o = instance.readDungeonObjectV1(reader, UID);
                    if (o != null) {
                        return o;
                    }
                }
            } catch (InstantiationException ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
            } catch (IllegalAccessException ex) {
                DungeonDiver4.getErrorLogger().logError(ex);
            }
        }
        return null;
    }

    public void readRuleSet(XDataReader reader, int rsFormat)
            throws IOException {
        AbstractDungeonObject[] objects = this.getAllObjects();
        // Read map length
        int mapLen = reader.readInt();
        boolean[] map = new boolean[mapLen];
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

    public void writeRuleSet(XDataWriter writer) throws IOException {
        AbstractDungeonObject[] objects = this.getAllObjects();
        boolean[] map = this.generateDungeon();
        // Write map length
        writer.writeInt(map.length);
        // Write map
        for (int x = 0; x < map.length; x++) {
            writer.writeBoolean(map[x]);
        }
        // Write data
        for (int x = 0; x < map.length; x++) {
            if (map[x]) {
                objects[x].getRuleSet().writeRuleSet(writer);
            }
        }
    }

    private boolean[] generateDungeon() {
        AbstractDungeonObject[] objects = this.getAllObjects();
        boolean[] map = new boolean[objects.length];
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
