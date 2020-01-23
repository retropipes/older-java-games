package net.worldwizard.support.creatures.faiths;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.randomnumbers.RandomRange;

public class FaithManager {
    private static boolean CACHE_CREATED = false;
    private static Faith[] CACHE;

    public static Faith selectFaith() {
        final String[] names = FaithConstants.getFaithNames();
        String dialogResult = null;
        dialogResult = CommonDialogs.showInputDialog("Select a Faith",
                "Create Character", names, names[0]);
        if (dialogResult != null) {
            int index;
            for (index = 0; index < names.length; index++) {
                if (dialogResult.equals(names[index])) {
                    break;
                }
            }
            return FaithManager.getFaith(index);
        } else {
            return null;
        }
    }

    public static Faith getFaith(final int faithID) {
        if (!FaithManager.CACHE_CREATED) {
            // Create cache
            final int fc = FaithConstants.getFaithsCount();
            FaithManager.CACHE = new Faith[fc];
            for (int x = 0; x < fc; x++) {
                FaithManager.CACHE[x] = new Faith(x);
            }
            FaithManager.CACHE_CREATED = true;
        }
        return FaithManager.CACHE[faithID];
    }

    public static Faith getRandomFaith() {
        if (!FaithManager.CACHE_CREATED) {
            // Create cache
            final int fc = FaithConstants.getFaithsCount();
            FaithManager.CACHE = new Faith[fc];
            for (int x = 0; x < fc; x++) {
                FaithManager.CACHE[x] = new Faith(x);
            }
            FaithManager.CACHE_CREATED = true;
        }
        final int faithID = new RandomRange(0, FaithManager.CACHE.length - 1)
                .generate();
        return FaithManager.CACHE[faithID];
    }
}
