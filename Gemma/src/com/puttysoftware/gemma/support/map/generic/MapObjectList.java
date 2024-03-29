/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.generic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.support.map.MapConstants;
import com.puttysoftware.gemma.support.map.objects.ArmorShop;
import com.puttysoftware.gemma.support.map.objects.BlueButton;
import com.puttysoftware.gemma.support.map.objects.BlueWallOff;
import com.puttysoftware.gemma.support.map.objects.BlueWallOn;
import com.puttysoftware.gemma.support.map.objects.ClosedDoor;
import com.puttysoftware.gemma.support.map.objects.CyanButton;
import com.puttysoftware.gemma.support.map.objects.CyanWallOff;
import com.puttysoftware.gemma.support.map.objects.CyanWallOn;
import com.puttysoftware.gemma.support.map.objects.Dirt;
import com.puttysoftware.gemma.support.map.objects.Empty;
import com.puttysoftware.gemma.support.map.objects.EmptyVoid;
import com.puttysoftware.gemma.support.map.objects.EnhancementShop;
import com.puttysoftware.gemma.support.map.objects.FaithPowerShop;
import com.puttysoftware.gemma.support.map.objects.Grass;
import com.puttysoftware.gemma.support.map.objects.GreenButton;
import com.puttysoftware.gemma.support.map.objects.GreenWallOff;
import com.puttysoftware.gemma.support.map.objects.GreenWallOn;
import com.puttysoftware.gemma.support.map.objects.HealShop;
import com.puttysoftware.gemma.support.map.objects.HotRock;
import com.puttysoftware.gemma.support.map.objects.ItemShop;
import com.puttysoftware.gemma.support.map.objects.Lava;
import com.puttysoftware.gemma.support.map.objects.MagentaButton;
import com.puttysoftware.gemma.support.map.objects.MagentaWallOff;
import com.puttysoftware.gemma.support.map.objects.MagentaWallOn;
import com.puttysoftware.gemma.support.map.objects.OpenDoor;
import com.puttysoftware.gemma.support.map.objects.OrangeButton;
import com.puttysoftware.gemma.support.map.objects.OrangeWallOff;
import com.puttysoftware.gemma.support.map.objects.OrangeWallOn;
import com.puttysoftware.gemma.support.map.objects.Player;
import com.puttysoftware.gemma.support.map.objects.PurpleButton;
import com.puttysoftware.gemma.support.map.objects.PurpleWallOff;
import com.puttysoftware.gemma.support.map.objects.PurpleWallOn;
import com.puttysoftware.gemma.support.map.objects.RedButton;
import com.puttysoftware.gemma.support.map.objects.RedWallOff;
import com.puttysoftware.gemma.support.map.objects.RedWallOn;
import com.puttysoftware.gemma.support.map.objects.Regenerator;
import com.puttysoftware.gemma.support.map.objects.RoseButton;
import com.puttysoftware.gemma.support.map.objects.RoseWallOff;
import com.puttysoftware.gemma.support.map.objects.RoseWallOn;
import com.puttysoftware.gemma.support.map.objects.Sand;
import com.puttysoftware.gemma.support.map.objects.SeaweedButton;
import com.puttysoftware.gemma.support.map.objects.SeaweedWallOff;
import com.puttysoftware.gemma.support.map.objects.SeaweedWallOn;
import com.puttysoftware.gemma.support.map.objects.SkyButton;
import com.puttysoftware.gemma.support.map.objects.SkyWallOff;
import com.puttysoftware.gemma.support.map.objects.SkyWallOn;
import com.puttysoftware.gemma.support.map.objects.Slime;
import com.puttysoftware.gemma.support.map.objects.Snow;
import com.puttysoftware.gemma.support.map.objects.StairsInto;
import com.puttysoftware.gemma.support.map.objects.StairsOut;
import com.puttysoftware.gemma.support.map.objects.Teleport;
import com.puttysoftware.gemma.support.map.objects.Tile;
import com.puttysoftware.gemma.support.map.objects.Tundra;
import com.puttysoftware.gemma.support.map.objects.Wall;
import com.puttysoftware.gemma.support.map.objects.Water;
import com.puttysoftware.gemma.support.map.objects.WeaponsShop;
import com.puttysoftware.gemma.support.map.objects.WhiteButton;
import com.puttysoftware.gemma.support.map.objects.WhiteWallOff;
import com.puttysoftware.gemma.support.map.objects.WhiteWallOn;
import com.puttysoftware.gemma.support.map.objects.YellowButton;
import com.puttysoftware.gemma.support.map.objects.YellowWallOff;
import com.puttysoftware.gemma.support.map.objects.YellowWallOn;
import com.puttysoftware.gemma.support.resourcemanagers.ImageManager;
import com.puttysoftware.gemma.support.scenario.FormatConstants;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.xio.XDataReader;

public class MapObjectList {
	// Fields
	private final ArrayList<MapObject> allObjects;

	// Constructor
	public MapObjectList() {
		final MapObject[] allDefaultObjects = { new Empty(), new Grass(), new Dirt(), new Sand(), new Snow(),
				new Tundra(), new Tile(), new Water(), new HotRock(), new Slime(), new Lava(), new Player(), new Wall(),
				new BlueWallOff(), new BlueWallOn(), new GreenWallOff(), new GreenWallOn(), new MagentaWallOff(),
				new MagentaWallOn(), new OrangeWallOff(), new OrangeWallOn(), new PurpleWallOff(), new PurpleWallOn(),
				new RedWallOff(), new RedWallOn(), new RoseWallOff(), new RoseWallOn(), new SeaweedWallOff(),
				new SeaweedWallOn(), new SkyWallOff(), new SkyWallOn(), new WhiteWallOff(), new WhiteWallOn(),
				new YellowWallOff(), new YellowWallOn(), new CyanWallOff(), new CyanWallOn(), new ClosedDoor(),
				new OpenDoor(), new BlueButton(), new GreenButton(), new MagentaButton(), new OrangeButton(),
				new PurpleButton(), new RedButton(), new RoseButton(), new SeaweedButton(), new SkyButton(),
				new WhiteButton(), new YellowButton(), new CyanButton(), new Teleport(), new StairsInto(),
				new StairsOut(), new EmptyVoid(), new ArmorShop(), new HealShop(), new ItemShop(), new Regenerator(),
				new WeaponsShop(), new EnhancementShop(), new FaithPowerShop() };
		this.allObjects = new ArrayList<>();
		for (final MapObject allDefaultObject : allDefaultObjects) {
			this.allObjects.add(allDefaultObject);
		}
	}

	// Methods
	private MapObject[] getAllObjects() {
		return this.allObjects.toArray(new MapObject[this.allObjects.size()]);
	}

	public String[] getAllDescriptions() {
		final MapObject[] objs = this.getAllObjects();
		final String[] tempAllDescriptions = new String[objs.length];
		int x;
		int count = 0;
		for (x = 0; x < objs.length; x++) {
			if (!objs[x].hideFromHelp()) {
				tempAllDescriptions[count] = objs[x].getDescription();
				count++;
			}
		}
		if (count == 0) {
			return null;
		} else {
			final String[] allDescriptions = new String[count];
			for (x = 0; x < count; x++) {
				allDescriptions[x] = tempAllDescriptions[x];
			}
			return allDescriptions;
		}
	}

	public MapObject[] getAllGroundLayerObjects() {
		final MapObject[] objs = this.getAllObjects();
		final MapObject[] tempAllGroundLayerObjects = new MapObject[this.getAllObjects().length];
		int objectCount = 0;
		for (int x = 0; x < objs.length; x++) {
			if (objs[x].getLayer() == MapConstants.LAYER_GROUND) {
				tempAllGroundLayerObjects[x] = objs[x];
			}
		}
		for (final MapObject tempAllGroundLayerObject : tempAllGroundLayerObjects) {
			if (tempAllGroundLayerObject != null) {
				objectCount++;
			}
		}
		final MapObject[] allGroundLayerObjects = new MapObject[objectCount];
		objectCount = 0;
		for (final MapObject tempAllGroundLayerObject : tempAllGroundLayerObjects) {
			if (tempAllGroundLayerObject != null) {
				allGroundLayerObjects[objectCount] = tempAllGroundLayerObject;
				objectCount++;
			}
		}
		return allGroundLayerObjects;
	}

	public BufferedImageIcon[] getAllObjectHelpImages() {
		final MapObject[] objs = this.getAllObjects();
		final BufferedImageIcon[] tempAllObjectHelpImages = new BufferedImageIcon[objs.length];
		int x;
		int count = 0;
		for (x = 0; x < objs.length; x++) {
			if (!objs[x].hideFromHelp()) {
				tempAllObjectHelpImages[count] = ImageManager.getMapHelpImage(objs[x].getEditorImageName(),
						objs[x].getName(), objs[x].getTemplateTransform());
				count++;
			}
		}
		if (count == 0) {
			return null;
		} else {
			final BufferedImageIcon[] allObjectHelpImages = new BufferedImageIcon[count];
			for (x = 0; x < count; x++) {
				allObjectHelpImages[x] = tempAllObjectHelpImages[x];
			}
			return allObjectHelpImages;
		}
	}

	public final MapObject[] getAllRequired(final int layer) {
		final MapObject[] objs = this.getAllObjects();
		final MapObject[] tempAllRequired = new MapObject[objs.length];
		int x;
		int count = 0;
		for (x = 0; x < objs.length; x++) {
			if (objs[x].getLayer() == layer && objs[x].isRequired()) {
				tempAllRequired[count] = objs[x];
				count++;
			}
		}
		if (count == 0) {
			return null;
		} else {
			final MapObject[] allRequired = new MapObject[count];
			for (x = 0; x < count; x++) {
				allRequired[x] = tempAllRequired[x];
			}
			return allRequired;
		}
	}

	public final MapObject[] getAllNotRequired(final int layer) {
		final MapObject[] objs = this.getAllObjects();
		final MapObject[] tempAllWithoutPrereq = new MapObject[objs.length];
		int x;
		int count = 0;
		for (x = 0; x < objs.length; x++) {
			if (objs[x].getLayer() == layer && !objs[x].isRequired()) {
				tempAllWithoutPrereq[count] = objs[x];
				count++;
			}
		}
		if (count == 0) {
			return null;
		} else {
			final MapObject[] allWithoutPrereq = new MapObject[count];
			for (x = 0; x < count; x++) {
				allWithoutPrereq[x] = tempAllWithoutPrereq[x];
			}
			return allWithoutPrereq;
		}
	}

	public final MapObject[] getAllRequiredInBattle(final int layer) {
		final MapObject[] objs = this.getAllObjects();
		final MapObject[] tempAllRequired = new MapObject[objs.length];
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
			final MapObject[] allRequired = new MapObject[count];
			for (x = 0; x < count; x++) {
				allRequired[x] = tempAllRequired[x];
			}
			return allRequired;
		}
	}

	public final MapObject[] getAllNotRequiredInBattle(final int layer) {
		final MapObject[] objs = this.getAllObjects();
		final MapObject[] tempAllWithoutPrereq = new MapObject[objs.length];
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
			final MapObject[] allWithoutPrereq = new MapObject[count];
			for (x = 0; x < count; x++) {
				allWithoutPrereq[x] = tempAllWithoutPrereq[x];
			}
			return allWithoutPrereq;
		}
	}

	public final MapObject getNewInstanceByName(final String name) {
		final MapObject[] objs = this.getAllObjects();
		MapObject instance = null;
		int x;
		for (x = 0; x < objs.length; x++) {
			if (objs[x].getName().equals(name)) {
				instance = objs[x];
				break;
			}
		}
		if (instance == null) {
			return null;
		} else {
			try {
				return instance.getClass().getConstructor(MapObject.class).newInstance(instance);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				Gemma.getErrorLogger().logError(e);
			}
		}
		// Failed, object not found
		return null;
	}

	public MapObject readMapObjectX(final XDataReader reader, final int formatVersion) throws IOException {
		final MapObject[] objs = this.getAllObjects();
		MapObject o = null;
		String UID = "";
		if (formatVersion == FormatConstants.SCENARIO_FORMAT_1) {
			UID = reader.readString();
		}
		for (int x = 0; x < objs.length; x++) {
			try {
				MapObject instance = objs[x].getClass().getConstructor(MapObject.class).newInstance(objs[x]);
				if (formatVersion == FormatConstants.SCENARIO_FORMAT_1) {
					o = instance.readMapObject(reader, UID, formatVersion);
				}
				if (o != null) {
					return o;
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				Gemma.getErrorLogger().logError(e);
			}
		}
		// Failed, object not found
		return null;
	}
}
