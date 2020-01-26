/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.generic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.puttysoftware.dungeondiver3.support.map.MapConstants;
import com.puttysoftware.dungeondiver3.support.map.objects.ArmorShop;
import com.puttysoftware.dungeondiver3.support.map.objects.Bank;
import com.puttysoftware.dungeondiver3.support.map.objects.BlueButton;
import com.puttysoftware.dungeondiver3.support.map.objects.BlueWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.BlueWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.ClosedDoor;
import com.puttysoftware.dungeondiver3.support.map.objects.CyanButton;
import com.puttysoftware.dungeondiver3.support.map.objects.CyanWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.CyanWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.Dirt;
import com.puttysoftware.dungeondiver3.support.map.objects.Empty;
import com.puttysoftware.dungeondiver3.support.map.objects.EmptyVoid;
import com.puttysoftware.dungeondiver3.support.map.objects.EnhancementShop;
import com.puttysoftware.dungeondiver3.support.map.objects.FaithPowerShop;
import com.puttysoftware.dungeondiver3.support.map.objects.Grass;
import com.puttysoftware.dungeondiver3.support.map.objects.GreenButton;
import com.puttysoftware.dungeondiver3.support.map.objects.GreenWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.GreenWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.HealShop;
import com.puttysoftware.dungeondiver3.support.map.objects.HotRock;
import com.puttysoftware.dungeondiver3.support.map.objects.ItemShop;
import com.puttysoftware.dungeondiver3.support.map.objects.Lava;
import com.puttysoftware.dungeondiver3.support.map.objects.MagentaButton;
import com.puttysoftware.dungeondiver3.support.map.objects.MagentaWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.MagentaWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.OpenDoor;
import com.puttysoftware.dungeondiver3.support.map.objects.OrangeButton;
import com.puttysoftware.dungeondiver3.support.map.objects.OrangeWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.OrangeWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.Pit;
import com.puttysoftware.dungeondiver3.support.map.objects.Player;
import com.puttysoftware.dungeondiver3.support.map.objects.PurpleButton;
import com.puttysoftware.dungeondiver3.support.map.objects.PurpleWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.PurpleWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.RedButton;
import com.puttysoftware.dungeondiver3.support.map.objects.RedWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.RedWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.Regenerator;
import com.puttysoftware.dungeondiver3.support.map.objects.RoseButton;
import com.puttysoftware.dungeondiver3.support.map.objects.RoseWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.RoseWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.Sand;
import com.puttysoftware.dungeondiver3.support.map.objects.SeaweedButton;
import com.puttysoftware.dungeondiver3.support.map.objects.SeaweedWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.SeaweedWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.SkyButton;
import com.puttysoftware.dungeondiver3.support.map.objects.SkyWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.SkyWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.Slime;
import com.puttysoftware.dungeondiver3.support.map.objects.Snow;
import com.puttysoftware.dungeondiver3.support.map.objects.SocksShop;
import com.puttysoftware.dungeondiver3.support.map.objects.SpellShop;
import com.puttysoftware.dungeondiver3.support.map.objects.Springboard;
import com.puttysoftware.dungeondiver3.support.map.objects.StairsDown;
import com.puttysoftware.dungeondiver3.support.map.objects.StairsInto;
import com.puttysoftware.dungeondiver3.support.map.objects.StairsOut;
import com.puttysoftware.dungeondiver3.support.map.objects.StairsUp;
import com.puttysoftware.dungeondiver3.support.map.objects.Teleport;
import com.puttysoftware.dungeondiver3.support.map.objects.Tile;
import com.puttysoftware.dungeondiver3.support.map.objects.Tundra;
import com.puttysoftware.dungeondiver3.support.map.objects.Wall;
import com.puttysoftware.dungeondiver3.support.map.objects.Water;
import com.puttysoftware.dungeondiver3.support.map.objects.WeaponsShop;
import com.puttysoftware.dungeondiver3.support.map.objects.WhiteButton;
import com.puttysoftware.dungeondiver3.support.map.objects.WhiteWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.WhiteWallOn;
import com.puttysoftware.dungeondiver3.support.map.objects.YellowButton;
import com.puttysoftware.dungeondiver3.support.map.objects.YellowWallOff;
import com.puttysoftware.dungeondiver3.support.map.objects.YellowWallOn;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.ImageManager;
import com.puttysoftware.dungeondiver3.support.scenario.FormatConstants;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.xio.XDataReader;

public class MapObjectList {
    // Fields
    private final ArrayList<MapObject> allObjects;

    // Constructor
    public MapObjectList() {
        final MapObject[] allDefaultObjects = { new Empty(), new Grass(),
                new Dirt(), new Sand(), new Snow(), new Tundra(), new Tile(),
                new Water(), new HotRock(), new Slime(), new Lava(),
                new Player(), new Wall(), new BlueWallOff(), new BlueWallOn(),
                new GreenWallOff(), new GreenWallOn(), new MagentaWallOff(),
                new MagentaWallOn(), new OrangeWallOff(), new OrangeWallOn(),
                new PurpleWallOff(), new PurpleWallOn(), new RedWallOff(),
                new RedWallOn(), new RoseWallOff(), new RoseWallOn(),
                new SeaweedWallOff(), new SeaweedWallOn(), new SkyWallOff(),
                new SkyWallOn(), new WhiteWallOff(), new WhiteWallOn(),
                new YellowWallOff(), new YellowWallOn(), new CyanWallOff(),
                new CyanWallOn(), new ClosedDoor(), new OpenDoor(),
                new BlueButton(), new GreenButton(), new MagentaButton(),
                new OrangeButton(), new PurpleButton(), new RedButton(),
                new RoseButton(), new SeaweedButton(), new SkyButton(),
                new WhiteButton(), new YellowButton(), new CyanButton(),
                new Teleport(), new StairsUp(), new StairsDown(),
                new StairsInto(), new StairsOut(), new Pit(), new Springboard(),
                new EmptyVoid(), new ArmorShop(), new Bank(), new HealShop(),
                new ItemShop(), new Regenerator(), new SocksShop(),
                new SpellShop(), new WeaponsShop(), new EnhancementShop(),
                new FaithPowerShop() };
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
        final MapObject[] tempAllGroundLayerObjects = new MapObject[this
                .getAllObjects().length];
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
                tempAllObjectHelpImages[count] = ImageManager.getMapHelpImage(
                        objs[x].getEditorImageName(), objs[x].getName(),
                        objs[x].getTemplateTransform());
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
                return instance.getClass().getConstructor().newInstance();
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                return null;           
            }
        }
    }

    public MapObject readMapObjectX(final XDataReader reader,
            final int formatVersion) throws IOException {
        final MapObject[] objs = this.getAllObjects();
        MapObject o = null;
        String UID = "";
        if (formatVersion == FormatConstants.SCENARIO_FORMAT_1
                || formatVersion == FormatConstants.SCENARIO_FORMAT_2) {
            UID = reader.readString();
        }
        for (final MapObject obj : objs) {
            try {
                final MapObject instance = obj.getClass().getConstructor().newInstance();
                if (formatVersion == FormatConstants.SCENARIO_FORMAT_1
                        || formatVersion == FormatConstants.SCENARIO_FORMAT_2) {
                    o = instance.readMapObject(reader, UID, formatVersion);
                }
                if (o != null) {
                    return o;
                }
            } catch (final InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                // Ignore
            }
        }
        // Failed, object not found
        return null;
    }
}
