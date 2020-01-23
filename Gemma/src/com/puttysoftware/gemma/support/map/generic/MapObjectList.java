/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.generic;

import java.io.IOException;
import java.util.ArrayList;

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
        MapObject[] allDefaultObjects = { new Empty(), new Grass(), new Dirt(),
                new Sand(), new Snow(), new Tundra(), new Tile(), new Water(),
                new HotRock(), new Slime(), new Lava(), new Player(),
                new Wall(), new BlueWallOff(), new BlueWallOn(),
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
                new Teleport(), new StairsInto(), new StairsOut(),
                new EmptyVoid(), new ArmorShop(), new HealShop(),
                new ItemShop(), new Regenerator(), new WeaponsShop(),
                new EnhancementShop(), new FaithPowerShop() };
        this.allObjects = new ArrayList<>();
        for (int x = 0; x < allDefaultObjects.length; x++) {
            this.allObjects.add(allDefaultObjects[x]);
        }
    }

    // Methods
    private MapObject[] getAllObjects() {
        return this.allObjects.toArray(new MapObject[this.allObjects.size()]);
    }

    public String[] getAllDescriptions() {
        MapObject[] objs = this.getAllObjects();
        String[] tempAllDescriptions = new String[objs.length];
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
            String[] allDescriptions = new String[count];
            for (x = 0; x < count; x++) {
                allDescriptions[x] = tempAllDescriptions[x];
            }
            return allDescriptions;
        }
    }

    public MapObject[] getAllGroundLayerObjects() {
        MapObject[] objs = this.getAllObjects();
        MapObject[] tempAllGroundLayerObjects = new MapObject[this
                .getAllObjects().length];
        int objectCount = 0;
        for (int x = 0; x < objs.length; x++) {
            if (objs[x].getLayer() == MapConstants.LAYER_GROUND) {
                tempAllGroundLayerObjects[x] = objs[x];
            }
        }
        for (int x = 0; x < tempAllGroundLayerObjects.length; x++) {
            if (tempAllGroundLayerObjects[x] != null) {
                objectCount++;
            }
        }
        MapObject[] allGroundLayerObjects = new MapObject[objectCount];
        objectCount = 0;
        for (int x = 0; x < tempAllGroundLayerObjects.length; x++) {
            if (tempAllGroundLayerObjects[x] != null) {
                allGroundLayerObjects[objectCount] = tempAllGroundLayerObjects[x];
                objectCount++;
            }
        }
        return allGroundLayerObjects;
    }

    public BufferedImageIcon[] getAllObjectHelpImages() {
        MapObject[] objs = this.getAllObjects();
        BufferedImageIcon[] tempAllObjectHelpImages = new BufferedImageIcon[objs.length];
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
            BufferedImageIcon[] allObjectHelpImages = new BufferedImageIcon[count];
            for (x = 0; x < count; x++) {
                allObjectHelpImages[x] = tempAllObjectHelpImages[x];
            }
            return allObjectHelpImages;
        }
    }

    public final MapObject[] getAllRequired(int layer) {
        MapObject[] objs = this.getAllObjects();
        MapObject[] tempAllRequired = new MapObject[objs.length];
        int x;
        int count = 0;
        for (x = 0; x < objs.length; x++) {
            if ((objs[x].getLayer() == layer) && objs[x].isRequired()) {
                tempAllRequired[count] = objs[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            MapObject[] allRequired = new MapObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final MapObject[] getAllNotRequired(int layer) {
        MapObject[] objs = this.getAllObjects();
        MapObject[] tempAllWithoutPrereq = new MapObject[objs.length];
        int x;
        int count = 0;
        for (x = 0; x < objs.length; x++) {
            if ((objs[x].getLayer() == layer) && !(objs[x].isRequired())) {
                tempAllWithoutPrereq[count] = objs[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            MapObject[] allWithoutPrereq = new MapObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final MapObject[] getAllRequiredInBattle(int layer) {
        MapObject[] objs = this.getAllObjects();
        MapObject[] tempAllRequired = new MapObject[objs.length];
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
            MapObject[] allRequired = new MapObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final MapObject[] getAllNotRequiredInBattle(int layer) {
        MapObject[] objs = this.getAllObjects();
        MapObject[] tempAllWithoutPrereq = new MapObject[objs.length];
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
            MapObject[] allWithoutPrereq = new MapObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final MapObject getNewInstanceByName(String name) {
        MapObject[] objs = this.getAllObjects();
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
            return instance.clone();
        }
    }

    public MapObject readMapObjectX(XDataReader reader, int formatVersion)
            throws IOException {
        MapObject[] objs = this.getAllObjects();
        MapObject o = null;
        String UID = "";
        if (formatVersion == FormatConstants.SCENARIO_FORMAT_1) {
            UID = reader.readString();
        }
        for (int x = 0; x < objs.length; x++) {
            MapObject instance = objs[x].clone();
            if (formatVersion == FormatConstants.SCENARIO_FORMAT_1) {
                o = instance.readMapObject(reader, UID, formatVersion);
            }
            if (o != null) {
                return o;
            }
        }
        // Failed, object not found
        return null;
    }
}
