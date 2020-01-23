/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.generic;

import java.io.IOException;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.map.objects.ArmorShop;
import net.worldwizard.support.map.objects.Bank;
import net.worldwizard.support.map.objects.BlueButton;
import net.worldwizard.support.map.objects.BlueWallOff;
import net.worldwizard.support.map.objects.BlueWallOn;
import net.worldwizard.support.map.objects.ClosedDoor;
import net.worldwizard.support.map.objects.CyanButton;
import net.worldwizard.support.map.objects.CyanWallOff;
import net.worldwizard.support.map.objects.CyanWallOn;
import net.worldwizard.support.map.objects.DarkGem;
import net.worldwizard.support.map.objects.Dirt;
import net.worldwizard.support.map.objects.Empty;
import net.worldwizard.support.map.objects.EmptyVoid;
import net.worldwizard.support.map.objects.Grass;
import net.worldwizard.support.map.objects.GreenButton;
import net.worldwizard.support.map.objects.GreenWallOff;
import net.worldwizard.support.map.objects.GreenWallOn;
import net.worldwizard.support.map.objects.Healer;
import net.worldwizard.support.map.objects.HotRock;
import net.worldwizard.support.map.objects.Ice;
import net.worldwizard.support.map.objects.ItemShop;
import net.worldwizard.support.map.objects.Lava;
import net.worldwizard.support.map.objects.LightGem;
import net.worldwizard.support.map.objects.MagentaButton;
import net.worldwizard.support.map.objects.MagentaWallOff;
import net.worldwizard.support.map.objects.MagentaWallOn;
import net.worldwizard.support.map.objects.OpenDoor;
import net.worldwizard.support.map.objects.OrangeButton;
import net.worldwizard.support.map.objects.OrangeWallOff;
import net.worldwizard.support.map.objects.OrangeWallOn;
import net.worldwizard.support.map.objects.Pit;
import net.worldwizard.support.map.objects.Player;
import net.worldwizard.support.map.objects.PurpleButton;
import net.worldwizard.support.map.objects.PurpleWallOff;
import net.worldwizard.support.map.objects.PurpleWallOn;
import net.worldwizard.support.map.objects.RedButton;
import net.worldwizard.support.map.objects.RedWallOff;
import net.worldwizard.support.map.objects.RedWallOn;
import net.worldwizard.support.map.objects.Regenerator;
import net.worldwizard.support.map.objects.RoseButton;
import net.worldwizard.support.map.objects.RoseWallOff;
import net.worldwizard.support.map.objects.RoseWallOn;
import net.worldwizard.support.map.objects.Sand;
import net.worldwizard.support.map.objects.SeaweedButton;
import net.worldwizard.support.map.objects.SeaweedWallOff;
import net.worldwizard.support.map.objects.SeaweedWallOn;
import net.worldwizard.support.map.objects.SkyButton;
import net.worldwizard.support.map.objects.SkyWallOff;
import net.worldwizard.support.map.objects.SkyWallOn;
import net.worldwizard.support.map.objects.Slime;
import net.worldwizard.support.map.objects.Snow;
import net.worldwizard.support.map.objects.SocksShop;
import net.worldwizard.support.map.objects.SpellShop;
import net.worldwizard.support.map.objects.Springboard;
import net.worldwizard.support.map.objects.StairsInto;
import net.worldwizard.support.map.objects.StairsOut;
import net.worldwizard.support.map.objects.Teleport;
import net.worldwizard.support.map.objects.Tile;
import net.worldwizard.support.map.objects.Tundra;
import net.worldwizard.support.map.objects.Wall;
import net.worldwizard.support.map.objects.Water;
import net.worldwizard.support.map.objects.WeaponsShop;
import net.worldwizard.support.map.objects.WhiteButton;
import net.worldwizard.support.map.objects.WhiteWallOff;
import net.worldwizard.support.map.objects.WhiteWallOn;
import net.worldwizard.support.map.objects.YellowButton;
import net.worldwizard.support.map.objects.YellowWallOff;
import net.worldwizard.support.map.objects.YellowWallOn;
import net.worldwizard.support.resourcemanagers.ImageTransformer;
import net.worldwizard.support.variables.FormatConstants;
import net.worldwizard.xio.XDataReader;

public class MapObjectList {
    // Fields
    private final MapObject[] allObjects = { new Empty(), new Grass(),
            new Dirt(), new Sand(), new Snow(), new Tundra(), new Tile(),
            new Ice(), new Water(), new HotRock(), new Slime(), new Lava(),
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
            new Teleport(), new EmptyVoid(), new DarkGem(), new LightGem(),
            new ArmorShop(), new Bank(), new Healer(), new ItemShop(),
            new Regenerator(), new SocksShop(), new SpellShop(),
            new WeaponsShop(), new Springboard(), new Pit(), new StairsInto(),
            new StairsOut() };

    public MapObject[] getAllObjects() {
        return this.allObjects;
    }

    public String[] getAllNames() {
        final String[] allNames = new String[this.allObjects.length];
        for (int x = 0; x < this.allObjects.length; x++) {
            allNames[x] = this.allObjects[x].getName();
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

    public MapObject[] getAllGroundLayerObjects() {
        final MapObject[] tempAllGroundLayerObjects = new MapObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == MapConstants.LAYER_GROUND) {
                tempAllGroundLayerObjects[x] = this.allObjects[x];
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

    public MapObject[] getAllObjectLayerObjects() {
        final MapObject[] tempAllObjectLayerObjects = new MapObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == MapConstants.LAYER_OBJECT) {
                tempAllObjectLayerObjects[x] = this.allObjects[x];
            }
        }
        for (final MapObject tempAllObjectLayerObject : tempAllObjectLayerObjects) {
            if (tempAllObjectLayerObject != null) {
                objectCount++;
            }
        }
        final MapObject[] allObjectLayerObjects = new MapObject[objectCount];
        objectCount = 0;
        for (final MapObject tempAllObjectLayerObject : tempAllObjectLayerObjects) {
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
            if (this.allObjects[x].getLayer() == MapConstants.LAYER_GROUND) {
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
            if (this.allObjects[x].getLayer() == MapConstants.LAYER_OBJECT) {
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

    public BufferedImageIcon[] getAllAppearances() {
        final BufferedImageIcon[] allAppearances = new BufferedImageIcon[this.allObjects.length];
        for (int x = 0; x < this.allObjects.length; x++) {
            allAppearances[x] = ImageTransformer.getTransformedImage(
                    this.allObjects[x].getGameImageName(),
                    this.allObjects[x].getName(),
                    this.allObjects[x].getTemplateTransform());
        }
        return allAppearances;
    }

    public final MapObject[] getAllRequired(final int layer) {
        final MapObject[] tempAllRequired = new MapObject[this.allObjects.length];
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
            final MapObject[] allRequired = new MapObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final MapObject[] getAllNotRequired(final int layer) {
        final MapObject[] tempAllWithoutPrereq = new MapObject[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getLayer() == layer
                    && !this.allObjects[x].isRequired()) {
                tempAllWithoutPrereq[count] = this.allObjects[x];
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
        MapObject instance = null;
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

    public MapObject readMapObjectX(final XDataReader reader,
            final int formatVersion) throws IOException {
        MapObject o = null;
        String UID = "";
        if (formatVersion == FormatConstants.SCENARIO_FORMAT_1) {
            UID = reader.readString();
        }
        for (final MapObject allObject : this.allObjects) {
            try {
                final MapObject instance = allObject.getClass().newInstance();
                if (formatVersion == FormatConstants.SCENARIO_FORMAT_1) {
                    o = instance.readMapObjectX(reader, UID, formatVersion);
                }
                if (o != null) {
                    return o;
                }
            } catch (final InstantiationException ex) {
                // Ignore
            } catch (final IllegalAccessException ex) {
                // Ignore
            }
        }
        return null;
    }
}
