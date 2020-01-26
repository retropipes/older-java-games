/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.utilities;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.FormatConstants;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.objects.*;
import com.puttysoftware.fantastlex.resourcemanagers.ImageTransformer;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageManager;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class MazeObjectList {
    // Fields
    private final ArrayList<AbstractMazeObject> allObjectList;
    private static final String OBJECT_GROUP = "object";
    private static final String OBJECT_ID_GROUP = "identifier";

    // Constructor
    public MazeObjectList() {
        final AbstractMazeObject[] allObjects = { new Amethyst(),
                new AnnihilationWand(), new APlug(), new APort(),
                new AquaBoots(), new ArmorShop(), new ArrowTrap(), new Axe(),
                new Bank(), new BarrierGenerator(), new BioHazardBoots(),
                new BlackCrystal(), new BlockTeleport(), new BlueButton(),
                new BlueCarpet(), new BlueCrystal(), new BlueHouse(),
                new BlueKey(), new BlueLock(), new BlueWallOff(),
                new BlueWallOn(), new Bolt(), new BoltWall(), new Bomb(),
                new BPlug(), new BPort(), new Bracer(), new BracerWall(),
                new BreakableWallHorizontal(), new BreakableWallVertical(),
                new BrickWall(), new BridgedLavaHorizontal(),
                new BridgedLavaVertical(), new BridgedSlimeHorizontal(),
                new BridgedSlimeVertical(), new BridgedWaterHorizontal(),
                new BridgedWaterVertical(), new BrightnessGem(), new Cap(),
                new CapWall(), new ChainTeleport(), new ClockwiseRotationTrap(),
                new ClosedDoor(), new ConditionalChainTeleport(),
                new ConditionalTeleport(), new ConfusionTrap(),
                new ControllableTeleport(), new CounterclockwiseRotationTrap(),
                new CounterpoisonAmulet(), new CPlug(), new CPort(),
                new CrackedWall(), new Crevasse(), new CrumblingWall(),
                new CrystalWall(), new CutTree(), new CyanButton(),
                new CyanCarpet(), new CyanCrystal(), new CyanHouse(),
                new CyanKey(), new CyanLock(), new CyanWallOff(),
                new CyanWallOn(), new Dagger(), new DaggerWall(),
                new DamageableWall(), new DamagedWall(), new DarkBlueCrystal(),
                new DarkCyanCrystal(), new DarkGem(), new DarkGrayCrystal(),
                new DarkGreenCrystal(), new DarkMagentaCrystal(),
                new DarknessGem(), new DarkRedCrystal(), new DarkWand(),
                new DarkYellowCrystal(), new Diamond(), new DimnessGem(),
                new Dirt(), new DisarmTrapWand(), new DizzinessTrap(),
                new DoubleHourglass(), new DPlug(), new DPort(),
                new DrunkTrap(), new Empty(), new EmptyVoid(),
                new EnergySphere(), new EnhancementShop(),
                new EnragedBarrierGenerator(), new EPlug(), new EPort(),
                new Exit(), new ExperienceBoots(), new ExplodingWall(),
                new Explore(), new ExploreWall(), new FadingWall(),
                new FaithPowerShop(), new FakeFinish(), new FakeWall(),
                new Finish(), new FinishMakingWand(), new FinishTo(),
                new FireAmulet(), new FireBomb(), new FireBoots(),
                new FireBow(), new ForceField(), new FPlug(), new FPort(),
                new GarnetSquare(), new GarnetWall(), new GhostAmulet(),
                new GhostBow(), new Glove(), new GloveWall(), new GlueBoots(),
                new GoldenSquare(), new GoldenWall(), new GPlug(), new GPort(),
                new Grass(), new GrayCrystal(), new GreenButton(),
                new GreenCarpet(), new GreenCrystal(), new GreenHouse(),
                new GreenKey(), new GreenLock(), new GreenWallOff(),
                new GreenWallOn(), new Hammer(), new HealBoots(),
                new HealShop(), new HealTrap(), new HorizontalBarrier(),
                new HotBoots(), new HotRock(), new Hourglass(), new HPlug(),
                new HPort(), new HurtTrap(), new Ice(), new IceAmulet(),
                new IceBomb(), new IceBow(), new IcedBarrierGenerator(),
                new InvisibleBlockTeleport(), new InvisibleChainTeleport(),
                new InvisibleConditionalChainTeleport(),
                new InvisibleConditionalTeleport(),
                new InvisibleOneShotChainTeleport(),
                new InvisibleOneShotConditionalTeleport(),
                new InvisibleOneShotTeleport(), new InvisiblePit(),
                new InvisibleSpringboard(), new InvisibleTeleport(),
                new InvisibleWall(), new IPlug(), new IPort(), new ItemShop(),
                new JPlug(), new JPort(), new Key(), new KPlug(), new KPort(),
                new Lava(), new LightBlueCrystal(), new LightCyanCrystal(),
                new LightGem(), new LightGrayCrystal(), new LightGreenCrystal(),
                new LightMagentaCrystal(), new LightnessGem(),
                new LightRedCrystal(), new LightWand(),
                new LightYellowCrystal(), new Lock(), new LPlug(), new LPort(),
                new MagentaButton(), new MagentaCarpet(), new MagentaCrystal(),
                new MagentaHouse(), new MagentaKey(), new MagentaLock(),
                new MagentaWallOff(), new MagentaWallOn(),
                new MajorDrainPotion(), new MajorHealPotion(),
                new MajorHurtPotion(), new MajorRandomPotion(),
                new MajorRegenPotion(), new MajorUnknownPotion(),
                new MasterTrappedWall(), new MasterWallTrap(), new MetalBoots(),
                new MetalButton(), new MetalDoor(), new MetalKey(),
                new MinorDrainPotion(), new MinorHealPotion(),
                new MinorHurtPotion(), new MinorRandomPotion(),
                new MinorRegenPotion(), new MinorUnknownPotion(),
                new MoneyBoots(), new Monster(), new MoonDoor(),
                new MoonStone(), new Move(), new MoveWall(), new MovingBlock(),
                new MovingFinish(), new MPlug(), new MPort(), new Necklace(),
                new NecklaceWall(), new NoBlock(), new NoBoots(),
                new NoPlayer(), new NormalAmulet(), new NPlug(), new NPort(),
                new NWayTeleport(), new OneShotChainTeleport(),
                new OneShotConditionalTeleport(),
                new OneShotControllableTeleport(), new OneShotTeleport(),
                new OneWayEastWall(), new OneWayNorthWall(),
                new OneWaySouthWall(), new OneWayWestWall(), new OpenDoor(),
                new OPlug(), new OPort(), new OrangeButton(),
                new OrangeCarpet(), new OrangeCrystal(), new OrangeHouse(),
                new OrangeKey(), new OrangeLock(), new OrangeWallOff(),
                new OrangeWallOn(), new PasswallBoots(), new Pit(),
                new PlantCrystal(), new PoisonBomb(), new PoisonBow(),
                new PoisonedBarrierGenerator(), new PoisonousAmulet(),
                new PPlug(), new PPort(), new PullableBlock(),
                new PullableBlockOnce(), new PullableBlockThrice(),
                new PullableBlockTwice(), new PurpleButton(),
                new PurpleCarpet(), new PurpleCrystal(), new PurpleHouse(),
                new PurpleKey(), new PurpleLock(), new PurpleWallOff(),
                new PurpleWallOn(), new PushableBlock(),
                new PushableBlockOnce(), new PushableBlockThrice(),
                new PushableBlockTwice(), new PushablePullableBlock(),
                new QPlug(), new QPort(), new QuakeBomb(),
                new RandomInvisibleOneShotTeleport(),
                new RandomInvisibleTeleport(), new RandomOneShotTeleport(),
                new RandomTeleport(), new RedButton(), new RedCarpet(),
                new RedCrystal(), new RedHouse(), new RedKey(), new RedLock(),
                new RedWallOff(), new RedWallOn(), new RegenBoots(),
                new Regenerator(), new RemoteActionWand(), new RoseButton(),
                new RoseCarpet(), new RoseCrystal(), new RoseHouse(),
                new RoseKey(), new RoseLock(), new RoseWallOff(),
                new RoseWallOn(), new RotationTrap(), new RotationWand(),
                new RPlug(), new RPort(), new Ruby(), new RubySquare(),
                new RubyWall(), new Sand(), new Sapphire(),
                new SapphireSquare(), new SapphireWall(), new SealedFinish(),
                new SealingWall(), new SeaweedButton(), new SeaweedCarpet(),
                new SeaweedCrystal(), new SeaweedHouse(), new SeaweedKey(),
                new SeaweedLock(), new SeaweedWallOff(), new SeaweedWallOn(),
                new Shield(), new ShieldWall(), new ShockBomb(), new ShockBow(),
                new ShockedBarrierGenerator(), new ShuffleBomb(), new Sign(),
                new SilverSquare(), new SilverWall(), new SkyButton(),
                new SkyCarpet(), new SkyCrystal(), new SkyHouse(), new SkyKey(),
                new SkyLock(), new SkyWallOff(), new SkyWallOn(), new Slime(),
                new SlipperyBoots(), new SmokeBomb(), new Snow(),
                new SocksShop(), new SpellShop(), new SPlug(), new SPort(),
                new Springboard(), new Staff(), new StaffWall(),
                new StairsDown(), new StairsUp(), new Stump(), new Suit(),
                new SuitWall(), new SunDoor(), new SunkenBlock(),
                new SunStone(), new SuperDrainPotion(), new SuperHealPotion(),
                new SuperHurtPotion(), new SuperRandomPotion(),
                new SuperRegenPotion(), new SuperUnknownPotion(), new Sword(),
                new SwordWall(), new Tablet(), new TabletSlot(), new Teleport(),
                new TeleportWand(), new Tile(), new TopazSquare(),
                new TopazWall(), new TPlug(), new TPort(), new TrappedWall0(),
                new TrappedWall1(), new TrappedWall10(), new TrappedWall11(),
                new TrappedWall12(), new TrappedWall13(), new TrappedWall14(),
                new TrappedWall15(), new TrappedWall16(), new TrappedWall17(),
                new TrappedWall18(), new TrappedWall19(), new TrappedWall2(),
                new TrappedWall3(), new TrappedWall4(), new TrappedWall5(),
                new TrappedWall6(), new TrappedWall7(), new TrappedWall8(),
                new TrappedWall9(), new TreasureChest(), new TreasureKey(),
                new Tree(), new TripleHourglass(), new TrueSightAmulet(),
                new Tundra(), new TwoWayTeleport(), new UPlug(), new UPort(),
                new UTurnTrap(), new VariableHealTrap(), new VariableHurtTrap(),
                new VerticalBarrier(), new VPlug(), new VPort(), new Wall(),
                new WallBreakingWand(), new WallMakingTrap(),
                new WallMakingWand(), new WallTrap0(), new WallTrap1(),
                new WallTrap10(), new WallTrap11(), new WallTrap12(),
                new WallTrap13(), new WallTrap14(), new WallTrap15(),
                new WallTrap16(), new WallTrap17(), new WallTrap18(),
                new WallTrap19(), new WallTrap2(), new WallTrap3(),
                new WallTrap4(), new WallTrap5(), new WallTrap6(),
                new WallTrap7(), new WallTrap8(), new WallTrap9(),
                new WarpBomb(), new WarpTrap(), new WarpWand(), new Water(),
                new WeaponsShop(), new WhiteButton(), new WhiteCarpet(),
                new WhiteCrystal(), new WhiteHouse(), new WhiteKey(),
                new WhiteLock(), new WhiteWallOff(), new WhiteWallOn(),
                new WPlug(), new WPort(), new XPlug(), new XPort(),
                new YellowButton(), new YellowCarpet(), new YellowCrystal(),
                new YellowHouse(), new YellowKey(), new YellowLock(),
                new YellowWallOff(), new YellowWallOn(), new YPlug(),
                new YPort(), new ZPlug(), new ZPort() };
        this.allObjectList = new ArrayList<>();
        // Add all predefined objects to the list
        for (final AbstractMazeObject allObject : allObjects) {
            this.allObjectList.add(allObject);
        }
    }

    // Methods
    public AbstractMazeObject[] getAllObjects() {
        return this.allObjectList
                .toArray(new AbstractMazeObject[this.allObjectList.size()]);
    }

    public void addObject(final AbstractMazeObject o) {
        this.allObjectList.add(o);
    }

    public String[] getAllNames() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final String[] allNames = new String[objects.length];
        for (int x = 0; x < objects.length; x++) {
            allNames[x] = objects[x].getName();
        }
        return allNames;
    }

    public String[] getAllDescriptions() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final String[] allDescriptions = new String[objects.length];
        for (int x = 0; x < objects.length; x++) {
            allDescriptions[x] = objects[x].getDescription();
        }
        return allDescriptions;
    }

    public AbstractMazeObject[] getAllObjectsWithRuleSets() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllObjectsWithRuleSets = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].hasRuleSet()) {
                tempAllObjectsWithRuleSets[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllObjectsWithRuleSet : tempAllObjectsWithRuleSets) {
            if (tempAllObjectsWithRuleSet != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allObjectsWithRuleSets = new AbstractMazeObject[objectCount];
        objectCount = 0;
        for (final AbstractMazeObject tempAllObjectsWithRuleSet : tempAllObjectsWithRuleSets) {
            if (tempAllObjectsWithRuleSet != null) {
                allObjectsWithRuleSets[objectCount] = tempAllObjectsWithRuleSet;
                objectCount++;
            }
        }
        return allObjectsWithRuleSets;
    }

    public AbstractMazeObject[] getAllObjectsWithoutRuleSets() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllObjectsWithoutRuleSets = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (!objects[x].hasRuleSet()) {
                tempAllObjectsWithoutRuleSets[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllObjectsWithoutRuleSet : tempAllObjectsWithoutRuleSets) {
            if (tempAllObjectsWithoutRuleSet != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allObjectsWithoutRuleSets = new AbstractMazeObject[objectCount];
        objectCount = 0;
        for (final AbstractMazeObject tempAllObjectsWithoutRuleSet : tempAllObjectsWithoutRuleSets) {
            if (tempAllObjectsWithoutRuleSet != null) {
                allObjectsWithoutRuleSets[objectCount] = tempAllObjectsWithoutRuleSet;
                objectCount++;
            }
        }
        return allObjectsWithoutRuleSets;
    }

    public AbstractMazeObject[] getAllGroundLayerObjects() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllGroundLayerObjects = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == MazeConstants.LAYER_GROUND) {
                tempAllGroundLayerObjects[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllGroundLayerObject : tempAllGroundLayerObjects) {
            if (tempAllGroundLayerObject != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allGroundLayerObjects = new AbstractMazeObject[objectCount];
        objectCount = 0;
        for (final AbstractMazeObject tempAllGroundLayerObject : tempAllGroundLayerObjects) {
            if (tempAllGroundLayerObject != null) {
                allGroundLayerObjects[objectCount] = tempAllGroundLayerObject;
                objectCount++;
            }
        }
        return allGroundLayerObjects;
    }

    public AbstractMazeObject[] getAllGenerationEligibleTypedObjects() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllGenerationEligibleTypedObjects = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_GENERATION_ELIGIBLE)) {
                tempAllGenerationEligibleTypedObjects[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllGenerationEligibleTypedObject : tempAllGenerationEligibleTypedObjects) {
            if (tempAllGenerationEligibleTypedObject != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allGenerationEligibleTypedObjects = new AbstractMazeObject[objectCount];
        objectCount = 0;
        for (final AbstractMazeObject tempAllGenerationEligibleTypedObject : tempAllGenerationEligibleTypedObjects) {
            if (tempAllGenerationEligibleTypedObject != null) {
                allGenerationEligibleTypedObjects[objectCount] = tempAllGenerationEligibleTypedObject;
                objectCount++;
            }
        }
        return allGenerationEligibleTypedObjects;
    }

    public AbstractMazeObject[] getAllGeneratedTypedObjects() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllGeneratedTypedObjects = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_GENERATED)) {
                tempAllGeneratedTypedObjects[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllGeneratedTypedObject : tempAllGeneratedTypedObjects) {
            if (tempAllGeneratedTypedObject != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allGeneratedTypedObjects = new AbstractMazeObject[objectCount];
        objectCount = 0;
        for (final AbstractMazeObject tempAllGeneratedTypedObject : tempAllGeneratedTypedObjects) {
            if (tempAllGeneratedTypedObject != null) {
                allGeneratedTypedObjects[objectCount] = tempAllGeneratedTypedObject;
                objectCount++;
            }
        }
        return allGeneratedTypedObjects;
    }

    public AbstractMazeObject[] getAllObjectLayerObjects() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllObjectLayerObjects = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == MazeConstants.LAYER_OBJECT) {
                tempAllObjectLayerObjects[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllObjectLayerObject : tempAllObjectLayerObjects) {
            if (tempAllObjectLayerObject != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allObjectLayerObjects = new AbstractMazeObject[objectCount];
        objectCount = 0;
        for (final AbstractMazeObject tempAllObjectLayerObject : tempAllObjectLayerObjects) {
            if (tempAllObjectLayerObject != null) {
                allObjectLayerObjects[objectCount] = tempAllObjectLayerObject;
                objectCount++;
            }
        }
        return allObjectLayerObjects;
    }

    public String[] getAllGroundLayerNames() {
        final AbstractMazeObject[] objects = this.getAllObjects();
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
        final AbstractMazeObject[] objects = this.getAllObjects();
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
        final AbstractMazeObject[] objects = this.getAllObjects();
        final BufferedImageIcon[] allEditorAppearances = new BufferedImageIcon[objects.length];
        for (int x = 0; x < allEditorAppearances.length; x++) {
            allEditorAppearances[x] = ImageTransformer.getTransformedImage(
                    ObjectImageManager.getImage(objects[x].getName(),
                            objects[x].getBaseID(),
                            objects[x].getTemplateColor(),
                            objects[x].getAttributeID(),
                            objects[x].getAttributeTemplateColor()));
        }
        return allEditorAppearances;
    }

    public BufferedImageIcon[] getAllGroundLayerEditorAppearances() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final BufferedImageIcon[] tempAllGroundLayerEditorAppearances = new BufferedImageIcon[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == MazeConstants.LAYER_GROUND) {
                tempAllGroundLayerEditorAppearances[x] = ImageTransformer
                        .getTransformedImage(ObjectImageManager.getImage(
                                objects[x].getName(), objects[x].getBaseID(),
                                objects[x].getTemplateColor(),
                                objects[x].getAttributeID(),
                                objects[x].getAttributeTemplateColor()));
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
        final AbstractMazeObject[] objects = this.getAllObjects();
        final BufferedImageIcon[] tempAllObjectLayerEditorAppearances = new BufferedImageIcon[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].getLayer() == MazeConstants.LAYER_OBJECT) {
                tempAllObjectLayerEditorAppearances[x] = ImageTransformer
                        .getTransformedImage(ObjectImageManager.getImage(
                                objects[x].getName(), objects[x].getBaseID(),
                                objects[x].getTemplateColor(),
                                objects[x].getAttributeID(),
                                objects[x].getAttributeTemplateColor()));
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
        final AbstractMazeObject[] objects = this.getAllObjects();
        final BufferedImageIcon[] tempAllContainableObjectEditorAppearances = new BufferedImageIcon[objects.length];
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

    public AbstractMazeObject[] getAllContainableObjects() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllContainableObjects = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_CONTAINABLE)) {
                tempAllContainableObjects[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllContainableObject : tempAllContainableObjects) {
            if (tempAllContainableObject != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allContainableObjects = new AbstractMazeObject[objectCount];
        objectCount = 0;
        for (final AbstractMazeObject tempAllContainableObject : tempAllContainableObjects) {
            if (tempAllContainableObject != null) {
                allContainableObjects[objectCount] = tempAllContainableObject;
                objectCount++;
            }
        }
        return allContainableObjects;
    }

    public String[] getAllContainableNames() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final String[] tempAllContainableNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_CONTAINABLE)) {
                tempAllContainableNames[x] = objects[x].getName();
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

    public AbstractMazeObject[] getAllInventoryableObjectsMinusSpecial() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllInventoryableObjects = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isInventoryable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOW)
                    && !objects[x].isOfType(TypeConstants.TYPE_AMULET)
                    && !objects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllInventoryableObjects[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllInventoryableObject : tempAllInventoryableObjects) {
            if (tempAllInventoryableObject != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allInventoryableObjects = new AbstractMazeObject[objectCount];
        objectCount = 0;
        for (final AbstractMazeObject tempAllInventoryableObject : tempAllInventoryableObjects) {
            if (tempAllInventoryableObject != null) {
                allInventoryableObjects[objectCount] = tempAllInventoryableObject;
                objectCount++;
            }
        }
        return allInventoryableObjects;
    }

    public String[] getAllInventoryableNamesMinusSpecial() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final String[] tempAllInventoryableNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isInventoryable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOW)
                    && !objects[x].isOfType(TypeConstants.TYPE_AMULET)
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

    public AbstractMazeObject[] getAllProgrammableKeys() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllProgrammableKeys = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_PROGRAMMABLE_KEY)) {
                tempAllProgrammableKeys[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllProgrammableKey : tempAllProgrammableKeys) {
            if (tempAllProgrammableKey != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allProgrammableKeys = new AbstractMazeObject[objectCount];
        objectCount = 0;
        for (final AbstractMazeObject tempAllProgrammableKey : tempAllProgrammableKeys) {
            if (tempAllProgrammableKey != null) {
                allProgrammableKeys[objectCount] = tempAllProgrammableKey;
                objectCount++;
            }
        }
        return allProgrammableKeys;
    }

    public String[] getAllProgrammableKeyNames() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final String[] tempAllProgrammableKeyNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_PROGRAMMABLE_KEY)) {
                tempAllProgrammableKeyNames[x] = objects[x].getName();
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

    public AbstractMazeObject[] getAllUsableObjects() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllUsableObjects = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isUsable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOW)
                    && !objects[x].isOfType(TypeConstants.TYPE_AMULET)
                    && !objects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
                tempAllUsableObjects[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllUsableObject : tempAllUsableObjects) {
            if (tempAllUsableObject != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allUsableObjects = new AbstractMazeObject[objectCount];
        objectCount = 0;
        for (final AbstractMazeObject tempAllUsableObject : tempAllUsableObjects) {
            if (tempAllUsableObject != null) {
                allUsableObjects[objectCount] = tempAllUsableObject;
                objectCount++;
            }
        }
        return allUsableObjects;
    }

    public String[] getAllUsableNamesMinusSpecial() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final String[] tempAllUsableNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isUsable()
                    && !objects[x].isOfType(TypeConstants.TYPE_BOW)
                    && !objects[x].isOfType(TypeConstants.TYPE_AMULET)
                    && !objects[x].isOfType(TypeConstants.TYPE_BOOTS)) {
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

    public AbstractMazeObject[] getAllBows() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllUsableObjects = new AbstractMazeObject[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_BOW)) {
                tempAllUsableObjects[x] = objects[x];
            }
        }
        for (final AbstractMazeObject tempAllUsableObject : tempAllUsableObjects) {
            if (tempAllUsableObject != null) {
                objectCount++;
            }
        }
        final AbstractMazeObject[] allUsableObjects = new AbstractMazeObject[objectCount
                + 1];
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
        final AbstractMazeObject[] objects = this.getAllObjects();
        final String[] tempAllUsableNames = new String[objects.length];
        int objectCount = 0;
        for (int x = 0; x < objects.length; x++) {
            if (objects[x].isOfType(TypeConstants.TYPE_BOW)) {
                tempAllUsableNames[x] = objects[x].getName();
            }
        }
        for (final String tempAllUsableName : tempAllUsableNames) {
            if (tempAllUsableName != null) {
                objectCount++;
            }
        }
        final String[] allUsableNames = new String[objectCount + 1];
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

    public final AbstractMazeObject[] getAllRequired(final int layer) {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllRequired = new AbstractMazeObject[objects.length];
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
            final AbstractMazeObject[] allRequired = new AbstractMazeObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final AbstractMazeObject[] getAllWithoutPrerequisiteAndNotRequired(
            final int layer) {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final AbstractMazeObject[] tempAllWithoutPrereq = new AbstractMazeObject[objects.length];
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
            final AbstractMazeObject[] allWithoutPrereq = new AbstractMazeObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final AbstractMazeObject[] getAllRequiredInBattle(final int layer) {
        final AbstractMazeObject[] objs = this.getAllObjects();
        final AbstractMazeObject[] tempAllRequired = new AbstractMazeObject[objs.length];
        int x;
        int count = 0;
        for (x = 0; x < objs.length; x++) {
            if (objs[x].getLayer() == layer && objs[x].isRequiredInBattle()) {
                tempAllRequired[count] = objs[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final AbstractMazeObject[] allRequired = new AbstractMazeObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final AbstractMazeObject[] getAllNotRequiredInBattle(
            final int layer) {
        final AbstractMazeObject[] objs = this.getAllObjects();
        final AbstractMazeObject[] tempAllWithoutPrereq = new AbstractMazeObject[objs.length];
        int x;
        int count = 0;
        for (x = 0; x < objs.length; x++) {
            if (objs[x].getLayer() == layer && !objs[x].isRequiredInBattle()) {
                tempAllWithoutPrereq[count] = objs[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final AbstractMazeObject[] allWithoutPrereq = new AbstractMazeObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public static final AbstractMazeObject[] getAllRequiredSubset(
            final AbstractMazeObject[] objs, final int layer) {
        if (objs == null) {
            return null;
        }
        final AbstractMazeObject[] tempAllRequired = new AbstractMazeObject[objs.length];
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
            final AbstractMazeObject[] allRequired = new AbstractMazeObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public static final AbstractMazeObject[] getAllWithoutPrerequisiteAndNotRequiredSubset(
            final AbstractMazeObject[] objs, final int layer) {
        if (objs == null) {
            return null;
        }
        final AbstractMazeObject[] tempAllWithoutPrereq = new AbstractMazeObject[objs.length];
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
            final AbstractMazeObject[] allWithoutPrereq = new AbstractMazeObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final AbstractMazeObject getNewInstanceByName(final String name) {
        final AbstractMazeObject[] objects = this.getAllObjects();
        AbstractMazeObject instance = null;
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
                    return instance.getClass().getConstructor().newInstance();
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                return null;
            }
        }
    }

    public AbstractMazeObject readMazeObject(final XDataReader reader,
            final int formatVersion) throws IOException {
        final AbstractMazeObject[] objects = this.getAllObjects();
        AbstractMazeObject o = null;
        String UID = "";
        if (formatVersion == FormatConstants.MAZE_FORMAT_1) {
            reader.readOpeningGroup(MazeObjectList.OBJECT_GROUP);
            reader.readOpeningGroup(MazeObjectList.OBJECT_ID_GROUP);
            UID = reader.readString();
            reader.readClosingGroup(MazeObjectList.OBJECT_ID_GROUP);
        }
        for (final AbstractMazeObject object : objects) {
            try {
                AbstractMazeObject instance;
                if (object.isOfType(TypeConstants.TYPE_GENERATED)) {
                    instance = object.clone();
                } else {
                    instance = object.getClass().getConstructor().newInstance();
                }
                if (formatVersion == FormatConstants.MAZE_FORMAT_1) {
                    o = instance.readMazeObjectV1(reader, UID);
                    if (o != null) {
                        reader.readClosingGroup(MazeObjectList.OBJECT_GROUP);
                        return o;
                    }
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                FantastleX.getErrorLogger().logError(e);
            }
        }
        return null;
    }

    public AbstractMazeObject readSavedMazeObject(final XDataReader reader,
            final String UID, final int formatVersion) throws IOException {
        final AbstractMazeObject[] objects = this.getAllObjects();
        AbstractMazeObject o = null;
        for (final AbstractMazeObject object : objects) {
            try {
                AbstractMazeObject instance;
                if (object.isOfType(TypeConstants.TYPE_GENERATED)) {
                    instance = object.clone();
                } else {
                    instance = object.getClass().getConstructor().newInstance();
                }
                if (formatVersion == FormatConstants.MAZE_FORMAT_1) {
                    o = instance.readMazeObjectV1(reader, UID);
                    if (o != null) {
                        return o;
                    }
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                FantastleX.getErrorLogger().logError(e);
            }
        }
        return null;
    }

    public void readRuleSet(final XDataReader reader, final int rsFormat)
            throws IOException {
        final AbstractMazeObject[] objects = this.getAllObjects();
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
        final AbstractMazeObject[] objects = this.getAllObjects();
        final boolean[] map = this.generateMaze();
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

    private boolean[] generateMaze() {
        final AbstractMazeObject[] objects = this.getAllObjects();
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
