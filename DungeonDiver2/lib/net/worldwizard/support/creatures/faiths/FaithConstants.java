package net.worldwizard.support.creatures.faiths;

import java.awt.Color;

public class FaithConstants {
    // Fields
    private static final int FAITHS_COUNT = 25;
    private static final String[] FAITH_NAMES = { "Blend", "Flame", "Storm",
            "Flood", "Shock", "Chill", "Sound", "Death", "Gleam", "Quake",
            "Chaos", "Plant", "Order", "Smoke", "Drain", "Boost", "Humid",
            "Heart", "React", "Toxin", "Smart", "Cloud", "Naked", "Swamp",
            "Ghost" };
    private static final String[] FAITH_DAMAGE_TYPES = { "Physical", "Hot",
            "Wind", "Water", "Electric", "Cold", "Sonic", "Dark", "Light",
            "Earth", "Chaos", "Plant", "Order", "Smoke", "Drain", "Boost",
            "Sweat", "Love", "Chemical", "Poison", "Mental", "Cloud", "Sleaze",
            "Swamp", "Fear" };
    private static final Color[] FAITH_COLORS = { new Color(127, 127, 127),
            new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255),
            new Color(255, 255, 0), new Color(0, 255, 255),
            new Color(255, 0, 255), new Color(0, 0, 0),
            new Color(255, 255, 255), new Color(127, 63, 0),
            new Color(127, 0, 0), new Color(0, 127, 0), new Color(0, 0, 127),
            new Color(127, 127, 0), new Color(0, 127, 127),
            new Color(127, 0, 127), new Color(255, 127, 0),
            new Color(255, 0, 127), new Color(127, 255, 0),
            new Color(0, 255, 127), new Color(127, 0, 255),
            new Color(0, 127, 255), new Color(255, 127, 127),
            new Color(127, 255, 127), new Color(127, 127, 255) };
    private static final boolean[] FAITH_DARK_EYES = { false, false, true,
            false, true, true, true, false, true, false, false, false, false,
            false, false, false, false, false, true, true, false, false, true,
            true, true };

    // Private constructor
    private FaithConstants() {
        // Do nothing
    }

    // Methods
    public static int getFaithsCount() {
        return FaithConstants.FAITHS_COUNT;
    }

    public static String[] getFaithNames() {
        return FaithConstants.FAITH_NAMES;
    }

    public static String getFaithName(final int f) {
        return FaithConstants.FAITH_NAMES[f];
    }

    public static String getFaithDamageType(final int f) {
        return FaithConstants.FAITH_DAMAGE_TYPES[f];
    }

    public static Color getFaithColor(final int f) {
        return FaithConstants.FAITH_COLORS[f];
    }

    public static boolean getFaithDarkEye(final int f) {
        return FaithConstants.FAITH_DARK_EYES[f];
    }
}
