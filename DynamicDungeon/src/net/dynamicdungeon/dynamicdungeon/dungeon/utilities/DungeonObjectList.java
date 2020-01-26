/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.utilities;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.FormatConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractDungeonObject;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Amulet;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.ArmorShop;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Bank;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Bomb;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Button;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.ClockwiseRotationTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.ClosedDoor;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.ConfusionTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.CounterclockwiseRotationTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.DarkGem;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.DizzinessTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.DrunkTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Empty;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.EmptyVoid;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Exit;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Hammer;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.HealShop;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.HealTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.HurtTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Ice;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.ItemShop;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Key;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.LightGem;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Lock;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Monster;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.OpenDoor;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Regenerator;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.SealingWall;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.SpellShop;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.StairsDown;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.StairsUp;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Stone;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Tablet;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.TabletSlot;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Tile;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.UTurnTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.VariableHealTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.VariableHurtTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Wall;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.WallOff;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.WallOn;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.WarpTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.WeaponsShop;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ImageTransformer;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageManager;
import net.dynamicdungeon.images.BufferedImageIcon;

public class DungeonObjectList {
    // Fields
    private final ArrayList<AbstractDungeonObject> allObjectList;

    // Constructor
    public DungeonObjectList() {
        final AbstractDungeonObject[] allObjects = { new ArmorShop(),
                new Bank(), new ClockwiseRotationTrap(), new ClosedDoor(),
                new ConfusionTrap(), new CounterclockwiseRotationTrap(),
                new DarkGem(), new DizzinessTrap(), new DrunkTrap(),
                new Empty(), new EmptyVoid(), new HealShop(), new HealTrap(),
                new HurtTrap(), new Ice(), new ItemShop(), new LightGem(),
                new Monster(), new OpenDoor(), new Regenerator(),
                new SealingWall(), new SpellShop(), new Tile(), new UTurnTrap(),
                new VariableHealTrap(), new VariableHurtTrap(), new Wall(),
                new WarpTrap(), new WeaponsShop(), new StairsUp(),
                new StairsDown(), new WallOff(), new WallOn(), new Button(),
                new Amulet(), new Stone(), new Bomb(), new Hammer(), new Exit(),
                new Key(), new Tablet(), new Lock(), new TabletSlot() };
        this.allObjectList = new ArrayList<>();
        // Add all predefined objects to the list
        for (final AbstractDungeonObject allObject : allObjects) {
            this.allObjectList.add(allObject);
        }
    }

    // Methods
    public AbstractDungeonObject[] getAllObjects() {
        return this.allObjectList
                .toArray(new AbstractDungeonObject[this.allObjectList.size()]);
    }

    public String[] getAllDescriptions() {
        final AbstractDungeonObject[] objects = this.getAllObjects();
        final String[] allDescriptions = new String[objects.length];
        for (int x = 0; x < objects.length; x++) {
            allDescriptions[x] = objects[x].getDescription();
        }
        return allDescriptions;
    }

    public BufferedImageIcon[] getAllEditorAppearances() {
        final AbstractDungeonObject[] objects = this.getAllObjects();
        final BufferedImageIcon[] allEditorAppearances = new BufferedImageIcon[objects.length];
        for (int x = 0; x < allEditorAppearances.length; x++) {
            allEditorAppearances[x] = ImageTransformer.getTransformedImage(
                    ObjectImageManager.getImage(objects[x].getName(),
                            objects[x].getBaseID(),
                            AbstractDungeonObject.getTemplateColor()),
                    ImageTransformer.getGraphicSize());
        }
        return allEditorAppearances;
    }

    public final AbstractDungeonObject[] getAllRequired(final int layer) {
        final AbstractDungeonObject[] objects = this.getAllObjects();
        final AbstractDungeonObject[] tempAllRequired = new AbstractDungeonObject[objects.length];
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
            final AbstractDungeonObject[] allRequired = new AbstractDungeonObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final AbstractDungeonObject[] getAllWithoutPrerequisiteAndNotRequired(
            final int layer) {
        final AbstractDungeonObject[] objects = this.getAllObjects();
        final AbstractDungeonObject[] tempAllWithoutPrereq = new AbstractDungeonObject[objects.length];
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
            final AbstractDungeonObject[] allWithoutPrereq = new AbstractDungeonObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final AbstractDungeonObject getNewInstanceByName(final String name) {
        final AbstractDungeonObject[] objects = this.getAllObjects();
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
                return instance.getClass().getConstructor().newInstance();
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                return null;
            }
        }
    }

    public AbstractDungeonObject readDungeonObject(final DatabaseReader reader,
            final int formatVersion) throws IOException {
        final AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject o = null;
        String UID = "";
        if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
            UID = reader.readString();
        }
        for (final AbstractDungeonObject object : objects) {
            try {
                AbstractDungeonObject instance;
                instance = object.getClass().getConstructor().newInstance();
                if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
                    o = instance.readDungeonObjectV1(reader, UID);
                    if (o != null) {
                        return o;
                    }
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                DynamicDungeon.getErrorLogger().logError(e);
            }
        }
        return null;
    }

    public AbstractDungeonObject readSavedDungeonObject(
            final DatabaseReader reader, final String UID,
            final int formatVersion) throws IOException {
        final AbstractDungeonObject[] objects = this.getAllObjects();
        AbstractDungeonObject o = null;
        for (final AbstractDungeonObject object : objects) {
            try {
                AbstractDungeonObject instance;
                instance = object.getClass().getConstructor().newInstance();
                if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
                    o = instance.readDungeonObjectV1(reader, UID);
                    if (o != null) {
                        return o;
                    }
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                DynamicDungeon.getErrorLogger().logError(e);
            }
        }
        return null;
    }
}
