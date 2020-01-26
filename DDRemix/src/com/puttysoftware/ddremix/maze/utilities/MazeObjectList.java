/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.maze.utilities;

import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.maze.FormatConstants;
import com.puttysoftware.ddremix.maze.abc.AbstractMazeObject;
import com.puttysoftware.ddremix.maze.objects.Amulet;
import com.puttysoftware.ddremix.maze.objects.ArmorShop;
import com.puttysoftware.ddremix.maze.objects.Bank;
import com.puttysoftware.ddremix.maze.objects.Bomb;
import com.puttysoftware.ddremix.maze.objects.Button;
import com.puttysoftware.ddremix.maze.objects.ClockwiseRotationTrap;
import com.puttysoftware.ddremix.maze.objects.ClosedDoor;
import com.puttysoftware.ddremix.maze.objects.ConfusionTrap;
import com.puttysoftware.ddremix.maze.objects.CounterclockwiseRotationTrap;
import com.puttysoftware.ddremix.maze.objects.DarkGem;
import com.puttysoftware.ddremix.maze.objects.DizzinessTrap;
import com.puttysoftware.ddremix.maze.objects.DrunkTrap;
import com.puttysoftware.ddremix.maze.objects.Empty;
import com.puttysoftware.ddremix.maze.objects.EmptyVoid;
import com.puttysoftware.ddremix.maze.objects.Exit;
import com.puttysoftware.ddremix.maze.objects.Hammer;
import com.puttysoftware.ddremix.maze.objects.HealShop;
import com.puttysoftware.ddremix.maze.objects.HealTrap;
import com.puttysoftware.ddremix.maze.objects.HurtTrap;
import com.puttysoftware.ddremix.maze.objects.Ice;
import com.puttysoftware.ddremix.maze.objects.ItemShop;
import com.puttysoftware.ddremix.maze.objects.Key;
import com.puttysoftware.ddremix.maze.objects.LightGem;
import com.puttysoftware.ddremix.maze.objects.Lock;
import com.puttysoftware.ddremix.maze.objects.Monster;
import com.puttysoftware.ddremix.maze.objects.OpenDoor;
import com.puttysoftware.ddremix.maze.objects.Regenerator;
import com.puttysoftware.ddremix.maze.objects.SealingWall;
import com.puttysoftware.ddremix.maze.objects.SpellShop;
import com.puttysoftware.ddremix.maze.objects.StairsDown;
import com.puttysoftware.ddremix.maze.objects.StairsUp;
import com.puttysoftware.ddremix.maze.objects.Stone;
import com.puttysoftware.ddremix.maze.objects.Tablet;
import com.puttysoftware.ddremix.maze.objects.TabletSlot;
import com.puttysoftware.ddremix.maze.objects.Tile;
import com.puttysoftware.ddremix.maze.objects.UTurnTrap;
import com.puttysoftware.ddremix.maze.objects.VariableHealTrap;
import com.puttysoftware.ddremix.maze.objects.VariableHurtTrap;
import com.puttysoftware.ddremix.maze.objects.Wall;
import com.puttysoftware.ddremix.maze.objects.WallOff;
import com.puttysoftware.ddremix.maze.objects.WallOn;
import com.puttysoftware.ddremix.maze.objects.WarpTrap;
import com.puttysoftware.ddremix.maze.objects.WeaponsShop;
import com.puttysoftware.ddremix.resourcemanagers.ImageTransformer;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageManager;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.xio.XDataReader;

public class MazeObjectList {
    // Fields
    private final ArrayList<AbstractMazeObject> allObjectList;

    // Constructor
    public MazeObjectList() {
        final AbstractMazeObject[] allObjects = { new ArmorShop(), new Bank(),
                new ClockwiseRotationTrap(), new ClosedDoor(),
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
        for (final AbstractMazeObject allObject : allObjects) {
            this.allObjectList.add(allObject);
        }
    }

    // Methods
    public AbstractMazeObject[] getAllObjects() {
        return this.allObjectList
                .toArray(new AbstractMazeObject[this.allObjectList.size()]);
    }

    public String[] getAllDescriptions() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final String[] allDescriptions = new String[objects.length];
        for (int x = 0; x < objects.length; x++) {
            allDescriptions[x] = objects[x].getDescription();
        }
        return allDescriptions;
    }

    public BufferedImageIcon[] getAllEditorAppearances() {
        final AbstractMazeObject[] objects = this.getAllObjects();
        final BufferedImageIcon[] allEditorAppearances = new BufferedImageIcon[objects.length];
        for (int x = 0; x < allEditorAppearances.length; x++) {
            allEditorAppearances[x] = ImageTransformer.getTransformedImage(
                    ObjectImageManager.getImage(objects[x].getName(),
                            objects[x].getBaseID(),
                            AbstractMazeObject.getTemplateColor()),
                    ImageTransformer.getGraphicSize());
        }
        return allEditorAppearances;
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
                return instance.getClass().newInstance();
            } catch (final IllegalAccessException iae) {
                return null;
            } catch (final InstantiationException ie) {
                return null;
            }
        }
    }

    public AbstractMazeObject readMazeObject(final XDataReader reader,
            final int formatVersion) throws IOException {
        final AbstractMazeObject[] objects = this.getAllObjects();
        AbstractMazeObject o = null;
        String UID = "";
        if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
            UID = reader.readString();
        }
        for (final AbstractMazeObject object : objects) {
            try {
                AbstractMazeObject instance;
                instance = object.getClass().newInstance();
                if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
                    o = instance.readMazeObjectV1(reader, UID);
                    if (o != null) {
                        return o;
                    }
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                DDRemix.getErrorLogger().logError(ex);
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
                instance = object.getClass().newInstance();
                if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
                    o = instance.readMazeObjectV1(reader, UID);
                    if (o != null) {
                        return o;
                    }
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                DDRemix.getErrorLogger().logError(ex);
            }
        }
        return null;
    }
}
