/*  LTRemix: An Arena-Solving Game
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ltremix.utilities;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.ltremix.LTRemix;
import com.puttysoftware.ltremix.arena.abstractobjects.AbstractArenaObject;
import com.puttysoftware.ltremix.arena.objects.*;
import com.puttysoftware.ltremix.resourcemanagers.ImageManager;
import com.puttysoftware.ltremix.stringmanagers.StringConstants;
import com.puttysoftware.xio.XDataReader;

public class ArenaObjectList {
    // Fields
    private final AbstractArenaObject[] allObjects = { new Empty(),
            new Ground(), new TankMover(), new Ice(), new Water(),
            new ThinIce(), new Bridge(), new Tank(), new Flag(), new Wall(),
            new AntiTank(), new DeadAntiTank(), new CrystalBlock(),
            new Bricks(), new Tunnel(), new Mirror(), new RotaryMirror(),
            new Box(), new AntiTankMover(), new TenMissiles(),
            new MagneticBox(), new MagneticMirror(), new MirrorCrystalBlock(),
            new TenStunners(), new TenBoosts(), new TenMagnets(),
            new MagneticWall(), new FrostField(), new StairsDown(),
            new StairsUp(), new TenBlueLasers(), new IcyBox(), new BlueDoor(),
            new BlueKey(), new GreenDoor(), new GreenKey(), new RedDoor(),
            new RedKey(), new Barrel(), new ExplodingBarrel(), new Ball(),
            new TenDisruptors(), new TenBombs(), new TenHeatBombs(),
            new TenIceBombs(), new WoodenWall(), new IcyWall(), new HotWall(),
            new Lava(), new HotBox(), new MetallicBricks(),
            new MetallicMirror(), new MetallicRotaryMirror(), new DeepWater(),
            new DeeperWater(), new DeepestWater(), new WoodenBox(),
            new IceBridge(), new PlasticBox(), new MetallicBox(),
            new FireAllButton(), new FireAllButtonDoor(),
            new FirePressureButton(), new FirePressureButtonDoor(),
            new FireTriggerButton(), new FireTriggerButtonDoor(),
            new IceAllButton(), new IceAllButtonDoor(), new IcePressureButton(),
            new IcePressureButtonDoor(), new IceTriggerButton(),
            new IceTriggerButtonDoor(), new MagneticAllButton(),
            new MagneticAllButtonDoor(), new MagneticPressureButton(),
            new MagneticPressureButtonDoor(), new MagneticTriggerButton(),
            new MagneticTriggerButtonDoor(), new MetallicAllButton(),
            new MetallicAllButtonDoor(), new MetallicPressureButton(),
            new MetallicPressureButtonDoor(), new MetallicTriggerButton(),
            new MetallicTriggerButtonDoor(), new PlasticAllButton(),
            new PlasticAllButtonDoor(), new PlasticPressureButton(),
            new PlasticPressureButtonDoor(), new PlasticTriggerButton(),
            new PlasticTriggerButtonDoor(), new StoneAllButton(),
            new StoneAllButtonDoor(), new StonePressureButton(),
            new StonePressureButtonDoor(), new StoneTriggerButton(),
            new StoneTriggerButtonDoor(), new UniversalAllButton(),
            new UniversalAllButtonDoor(), new UniversalPressureButton(),
            new UniversalPressureButtonDoor(), new UniversalTriggerButton(),
            new UniversalTriggerButtonDoor(), new WoodenAllButton(),
            new WoodenAllButtonDoor(), new WoodenPressureButton(),
            new WoodenPressureButtonDoor(), new WoodenTriggerButton(),
            new WoodenTriggerButtonDoor(), new BoxMover(), new JumpBox(),
            new ReverseJumpBox(), new MirrorMover(), new HotCrystalBlock(),
            new IcyCrystalBlock(), new Cracked(), new Crumbling(),
            new Damaged(), new Weakened(), new Cloak(), new Darkness(),
            new PowerBolt(), new RollingBarrelHorizontal(),
            new RollingBarrelVertical(), new FreezeSpell(), new KillSpell(),
            new AntiBelt(), new StickyBox(), new Acid(), new StrongAcid(),
            new StrongerAcid(), new StrongestAcid(), new RemoteController(),
            new AnyMover(), new ToughBricks(), new TimeWarper() };

    public String[] getAllDescriptions() {
        final String[] allDescriptions = new String[this.allObjects.length];
        for (int x = 0; x < this.allObjects.length; x++) {
            allDescriptions[x] = this.allObjects[x].getDescription();
        }
        return allDescriptions;
    }

    public BufferedImageIcon[] getAllEditorAppearances() {
        final BufferedImageIcon[] allEditorAppearances = new BufferedImageIcon[this.allObjects.length];
        for (int x = 0; x < allEditorAppearances.length; x++) {
            allEditorAppearances[x] = ImageManager.getImage(this.allObjects[x],
                    false);
        }
        return allEditorAppearances;
    }

    public void enableAllObjects() {
        for (final AbstractArenaObject allObject : this.allObjects) {
            allObject.setEnabled(true);
        }
    }

    public AbstractArenaObject[] getAllObjectsOnLayer(final int layer) {
        for (final AbstractArenaObject allObject : this.allObjects) {
            if (allObject.isOnLayer(layer)) {
                allObject.setEnabled(true);
            } else {
                allObject.setEnabled(false);
            }
        }
        return this.allObjects;
    }

    public boolean[] getObjectEnabledStatuses(final int layer) {
        final boolean[] allObjectEnabledStatuses = new boolean[this.allObjects.length];
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isOnLayer(layer)) {
                allObjectEnabledStatuses[x] = true;
            } else {
                allObjectEnabledStatuses[x] = false;
            }
        }
        return allObjectEnabledStatuses;
    }

    public BufferedImageIcon[] getAllEditorAppearancesOnLayer(final int layer) {
        final BufferedImageIcon[] allEditorAppearancesOnLayer = new BufferedImageIcon[this.allObjects.length];
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isOnLayer(layer)) {
                this.allObjects[x].setEnabled(true);
            } else {
                this.allObjects[x].setEnabled(false);
            }
            allEditorAppearancesOnLayer[x] = ImageManager
                    .getImage(this.allObjects[x], false);
        }
        return allEditorAppearancesOnLayer;
    }

    public AbstractArenaObject readArenaObjectG2(final XDataReader reader,
            final int formatVersion) throws IOException {
        AbstractArenaObject o = null;
        String UID = StringConstants.COMMON_STRING_SPACE;
        if (FormatConstants.isFormatVersionValidGeneration1(formatVersion)
                || FormatConstants
                        .isFormatVersionValidGeneration2(formatVersion)) {
            UID = reader.readString();
        } else {
            return null;
        }
        for (final AbstractArenaObject allObject : this.allObjects) {
            try {
                final AbstractArenaObject instance = allObject.getClass()
                        .getConstructor().newInstance();
                if (FormatConstants
                        .isFormatVersionValidGeneration1(formatVersion)
                        || FormatConstants.isFormatVersionValidGeneration2(
                                formatVersion)) {
                    o = instance.readArenaObjectG2(reader, UID, formatVersion);
                } else {
                    return null;
                }
                if (o != null) {
                    return o;
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                LTRemix.getErrorLogger().logError(e);
            }
        }
        return null;
    }

    public AbstractArenaObject readArenaObjectG3(final XDataReader reader,
            final int formatVersion) throws IOException {
        AbstractArenaObject o = null;
        String UID = StringConstants.COMMON_STRING_SPACE;
        if (FormatConstants.isFormatVersionValidGeneration3(formatVersion)) {
            UID = reader.readString();
        } else {
            return null;
        }
        for (final AbstractArenaObject allObject : this.allObjects) {
            try {
                final AbstractArenaObject instance = allObject.getClass()
                        .getConstructor().newInstance();
                if (FormatConstants
                        .isFormatVersionValidGeneration3(formatVersion)) {
                    o = instance.readArenaObjectG3(reader, UID, formatVersion);
                } else {
                    return null;
                }
                if (o != null) {
                    return o;
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                LTRemix.getErrorLogger().logError(e);
            }
        }
        return null;
    }

    public AbstractArenaObject readArenaObjectG4(final XDataReader reader,
            final int formatVersion) throws IOException {
        AbstractArenaObject o = null;
        String UID = StringConstants.COMMON_STRING_SPACE;
        if (FormatConstants.isFormatVersionValidGeneration4(formatVersion)) {
            UID = reader.readString();
        } else {
            return null;
        }
        for (final AbstractArenaObject allObject : this.allObjects) {
            try {
                final AbstractArenaObject instance = allObject.getClass()
                        .getConstructor().newInstance();
                if (FormatConstants
                        .isFormatVersionValidGeneration4(formatVersion)) {
                    o = instance.readArenaObjectG4(reader, UID, formatVersion);
                } else {
                    return null;
                }
                if (o != null) {
                    return o;
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                LTRemix.getErrorLogger().logError(e);
            }
        }
        return null;
    }

    public AbstractArenaObject readArenaObjectG5(final XDataReader reader,
            final int formatVersion) throws IOException {
        AbstractArenaObject o = null;
        String UID = StringConstants.COMMON_STRING_SPACE;
        if (FormatConstants.isFormatVersionValidGeneration5(formatVersion)) {
            UID = reader.readString();
        } else {
            return null;
        }
        for (final AbstractArenaObject allObject : this.allObjects) {
            try {
                final AbstractArenaObject instance = allObject.getClass()
                        .getConstructor().newInstance();
                if (FormatConstants
                        .isFormatVersionValidGeneration5(formatVersion)) {
                    o = instance.readArenaObjectG5(reader, UID, formatVersion);
                } else {
                    return null;
                }
                if (o != null) {
                    return o;
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                LTRemix.getErrorLogger().logError(e);
            }
        }
        return null;
    }

    public AbstractArenaObject readArenaObjectG6(final XDataReader reader,
            final int formatVersion) throws IOException {
        AbstractArenaObject o = null;
        String UID = StringConstants.COMMON_STRING_SPACE;
        if (FormatConstants.isFormatVersionValidGeneration6(formatVersion)) {
            UID = reader.readString();
        } else {
            return null;
        }
        for (final AbstractArenaObject allObject : this.allObjects) {
            try {
                final AbstractArenaObject instance = allObject.getClass()
                        .getConstructor().newInstance();
                if (FormatConstants
                        .isFormatVersionValidGeneration6(formatVersion)) {
                    o = instance.readArenaObjectG6(reader, UID, formatVersion);
                } else {
                    return null;
                }
                if (o != null) {
                    return o;
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                LTRemix.getErrorLogger().logError(e);
            }
        }
        return null;
    }

    public AbstractArenaObject readArenaObjectG7(final XDataReader reader,
            final int formatVersion) throws IOException {
        AbstractArenaObject o = null;
        String UID = StringConstants.COMMON_STRING_SPACE;
        if (FormatConstants.isFormatVersionValidGeneration7(formatVersion)) {
            UID = reader.readString();
        } else {
            return null;
        }
        for (final AbstractArenaObject allObject : this.allObjects) {
            try {
                final AbstractArenaObject instance = allObject.getClass()
                        .getConstructor().newInstance();
                if (FormatConstants
                        .isFormatVersionValidGeneration7(formatVersion)) {
                    o = instance.readArenaObjectG7(reader, UID, formatVersion);
                } else {
                    return null;
                }
                if (o != null) {
                    return o;
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                LTRemix.getErrorLogger().logError(e);
            }
        }
        return null;
    }
}
