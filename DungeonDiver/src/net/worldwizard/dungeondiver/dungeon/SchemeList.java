package net.worldwizard.dungeondiver.dungeon;

import net.worldwizard.randomnumbers.RandomRange;

public class SchemeList {
    // Fields
    private static Scheme[] list;
    private static Scheme active;

    // Pseudo-Constructor
    private static void init() {
        SchemeList.list = new Scheme[61];
        int counter = 0;
        for (int r = 0; r < 5; r++) {
            for (int g = 0; g < 5; g++) {
                for (int b = 0; b < 5; b++) {
                    if (r != 4 && g != 4 && b != 4) {
                        continue;
                    } else {
                        final double r2 = r / 4.0;
                        final double g2 = g / 4.0;
                        final double b2 = b / 4.0;
                        SchemeList.list[counter] = new Scheme(r2, g2, b2);
                        counter++;
                    }
                }
            }
        }
        SchemeList.active = SchemeList.getRandomScheme();
    }

    private static Scheme getRandomScheme() {
        final RandomRange r = new RandomRange(0, SchemeList.list.length - 1);
        final int ID = (int) r.generate();
        return SchemeList.list[ID];
    }

    public static Scheme getActiveScheme() {
        if (SchemeList.list == null) {
            SchemeList.init();
        }
        return SchemeList.active;
    }

    public static void setActiveScheme() {
        if (SchemeList.list == null) {
            SchemeList.init();
        }
        SchemeList.active = SchemeList.getRandomScheme();
    }
}
