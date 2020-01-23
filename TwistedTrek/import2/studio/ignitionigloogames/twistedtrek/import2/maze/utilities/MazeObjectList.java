/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.utilities;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import studio.ignitionigloogames.images.BufferedImageIcon;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.FormatConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.abc.AbstractMazeObject;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Amulet;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.ArmorShop;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Bank;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Button;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.ClockwiseRotationTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.ClosedDoor;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.ConfusionTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.CounterclockwiseRotationTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.DarkGem;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.DizzinessTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.DrunkTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Empty;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.EmptyVoid;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.EnhancementShop;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.FaithPowerShop;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.HealShop;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.HealTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.HurtTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Ice;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.ItemShop;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.LightGem;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Monster;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.OpenDoor;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Regenerator;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.SealingWall;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.SocksShop;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.SpellShop;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.StairsDown;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.StairsUp;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Tile;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.UTurnTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.VariableHealTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.VariableHurtTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.Wall;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.WallOff;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.WallOn;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.WarpTrap;
import studio.ignitionigloogames.twistedtrek.import2.maze.objects.WeaponsShop;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ImageTransformer;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.ObjectImageManager;
import studio.ignitionigloogames.xio.XDataReader;

public class MazeObjectList {
    // Fields
    private final ArrayList<AbstractMazeObject> allObjectList;

    // Constructor
    public MazeObjectList() {
	final AbstractMazeObject[] allObjects = { new ArmorShop(), new Bank(), new ClockwiseRotationTrap(),
		new ClosedDoor(), new ConfusionTrap(), new CounterclockwiseRotationTrap(), new DarkGem(),
		new DizzinessTrap(), new DrunkTrap(), new Empty(), new EmptyVoid(), new EnhancementShop(),
		new FaithPowerShop(), new HealShop(), new HealTrap(), new HurtTrap(), new Ice(), new ItemShop(),
		new LightGem(), new Monster(), new OpenDoor(), new Regenerator(), new SealingWall(), new SocksShop(),
		new SpellShop(), new Tile(), new UTurnTrap(), new VariableHealTrap(), new VariableHurtTrap(),
		new Wall(), new WarpTrap(), new WeaponsShop(), new StairsUp(), new StairsDown(), new WallOff(),
		new WallOn(), new Button(), new Amulet() };
	this.allObjectList = new ArrayList<>();
	// Add all predefined objects to the list
	for (final AbstractMazeObject allObject : allObjects) {
	    this.allObjectList.add(allObject);
	}
    }

    // Methods
    public AbstractMazeObject[] getAllObjects() {
	return this.allObjectList.toArray(new AbstractMazeObject[this.allObjectList.size()]);
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
	    allEditorAppearances[x] = ImageTransformer.getTransformedImage(ObjectImageManager
		    .getImage(objects[x].getName(), objects[x].getBaseID(), AbstractMazeObject.getTemplateColor()),
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

    public final AbstractMazeObject[] getAllWithoutPrerequisiteAndNotRequired(final int layer) {
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
		return instance.getClass().getConstructor().newInstance();
	    } catch (final IllegalAccessException | InstantiationException | IllegalArgumentException
		    | InvocationTargetException | NoSuchMethodException | SecurityException iae) {
		return null;
	    }
	}
    }

    public AbstractMazeObject readMazeObject(final XDataReader reader, final int formatVersion) throws IOException {
	final AbstractMazeObject[] objects = this.getAllObjects();
	AbstractMazeObject o = null;
	String UID = "";
	if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
	    UID = reader.readString();
	}
	for (final AbstractMazeObject object : objects) {
	    try {
		AbstractMazeObject instance;
		instance = object.getClass().getConstructor().newInstance();
		if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
		    o = instance.readMazeObjectV1(reader, UID);
		    if (o != null) {
			return o;
		    }
		}
	    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
		    | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
		Import2.getErrorLogger().logError(ex);
	    }
	}
	return null;
    }

    public AbstractMazeObject readSavedMazeObject(final XDataReader reader, final String UID, final int formatVersion)
	    throws IOException {
	final AbstractMazeObject[] objects = this.getAllObjects();
	AbstractMazeObject o = null;
	for (final AbstractMazeObject object : objects) {
	    try {
		AbstractMazeObject instance;
		instance = object.getClass().getConstructor().newInstance();
		if (formatVersion == FormatConstants.MAZE_FORMAT_LATEST) {
		    o = instance.readMazeObjectV1(reader, UID);
		    if (o != null) {
			return o;
		    }
		}
	    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
		    | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
		Import2.getErrorLogger().logError(ex);
	    }
	}
	return null;
    }
}
