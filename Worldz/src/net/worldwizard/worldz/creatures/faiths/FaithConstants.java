package net.worldwizard.worldz.creatures.faiths;

import java.awt.Color;

public interface FaithConstants {
    int FAITHS_COUNT = 25;
    String[] FAITH_NAMES = { "Blend", "Flame", "Storm", "Flood", "Shock",
            "Chill", "Sound", "Death", "Gleam", "Quake", "Chaos", "Plant",
            "Order", "Smoke", "Drain", "Boost", "Humid", "Heart", "React",
            "Toxin", "Smart", "Cloud", "Naked", "Swamp", "Ghost" };
    String[] FAITH_DAMAGE_TYPES = { "Physical", "Hot", "Wind", "Water",
            "Electric", "Cold", "Sonic", "Dark", "Light", "Earth", "Chaos",
            "Plant", "Order", "Smoke", "Drain", "Boost", "Sweat", "Love",
            "Chemical", "Poison", "Mental", "Cloud", "Sleaze", "Swamp",
            "Fear" };
    Color[] FAITH_COLORS = { new Color(127, 127, 127), new Color(255, 0, 0),
            new Color(0, 255, 0), new Color(0, 0, 255), new Color(255, 255, 0),
            new Color(0, 255, 255), new Color(255, 0, 255), new Color(0, 0, 0),
            new Color(255, 255, 255), new Color(127, 63, 0),
            new Color(127, 0, 0), new Color(0, 127, 0), new Color(0, 0, 127),
            new Color(127, 127, 0), new Color(0, 127, 127),
            new Color(127, 0, 127), new Color(255, 127, 0),
            new Color(255, 0, 127), new Color(127, 255, 0),
            new Color(0, 255, 127), new Color(127, 0, 255),
            new Color(0, 127, 255), new Color(255, 127, 127),
            new Color(127, 255, 127), new Color(127, 127, 255) };
    boolean[] FAITH_DARK_EYES = { false, false, true, false, true, true, true,
            false, true, false, false, false, false, false, false, false, false,
            false, true, true, false, false, true, true, true };
}