package net.worldwizard.support.creatures.races;

import net.worldwizard.commondialogs.CommonDialogs;

public class RaceManager {
    private static boolean CACHE_CREATED = false;
    private static Race[] CACHE;

    public static Race selectRace() {
        final String[] names = RaceConstants.RACE_NAMES;
        String dialogResult = null;
        dialogResult = CommonDialogs.showInputDialog("Select a Race",
                "Create Character", names, names[0]);
        if (dialogResult != null) {
            int index;
            for (index = 0; index < names.length; index++) {
                if (dialogResult.equals(names[index])) {
                    break;
                }
            }
            return RaceManager.getRace(index);
        } else {
            return null;
        }
    }

    public static Race getRace(final int raceID) {
        if (!RaceManager.CACHE_CREATED) {
            // Create cache
            RaceManager.CACHE = new Race[RaceConstants.RACES_COUNT];
            for (int x = 0; x < RaceConstants.RACES_COUNT; x++) {
                RaceManager.CACHE[x] = RaceLoader.loadRace(Race
                        .raceIDtoFilename(raceID));
            }
            RaceManager.CACHE_CREATED = true;
        }
        return RaceManager.CACHE[raceID];
    }
}
