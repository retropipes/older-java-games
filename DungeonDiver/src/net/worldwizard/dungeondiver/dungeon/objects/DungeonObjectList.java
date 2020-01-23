package net.worldwizard.dungeondiver.dungeon.objects;

import net.worldwizard.map.MapObject;
import net.worldwizard.map.MapObjectList;

public final class DungeonObjectList extends MapObjectList {
    // Fields
    private static final DungeonObject[] objects = { new Armor(), new Bank(),
            new ClockwiseRotationTrap(), new ConfusionTrap(),
            new CounterclockwiseRotationTrap(), new DarkGem(), new Darkness(),
            new Door(), new Healer(), new Ice(), new LightGem(), new Monster(),
            new Player(), new Regenerator(), new StairsUp(), new StairsDown(),
            new Switcher(), new SpinnerTrap(), new Tile(), new Wall(),
            new WarpTrap(), new Weapons(), new ZoomTube() };

    // Constructors
    public DungeonObjectList() {
        super(DungeonObjectList.objects);
    }

    public static MapObject getSpecificObject(final String name) {
        int x;
        for (x = 0; x < DungeonObjectList.objects.length; x++) {
            if (name.equals(DungeonObjectList.objects[x].getName())) {
                if (DungeonObjectList.objects[x].shouldCache()) {
                    return DungeonObjectList.objects[x];
                } else {
                    return MapObjectList
                            .getNewInstance(DungeonObjectList.objects[x]);
                }
            }
        }
        return null;
    }

    public static void updateAllAppearances() {
        int x;
        for (x = 0; x < DungeonObjectList.objects.length; x++) {
            final DungeonObject d = DungeonObjectList.objects[x];
            d.invalidateAppearance();
            d.updateAppearance();
        }
    }
}
