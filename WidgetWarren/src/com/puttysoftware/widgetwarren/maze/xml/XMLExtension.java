/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.maze.xml;

public class XMLExtension {
    // Constants
    private static final String XML_MAZE_EXTENSION = "maze";
    private static final String XML_SAVED_GAME_EXTENSION = "game";
    private static final String XML_SCORES_EXTENSION = "scores";
    private static final String XML_RULE_SET_EXTENSION = "ruleset";

    // Methods
    public static String getXMLMazeExtension() {
        return XMLExtension.XML_MAZE_EXTENSION;
    }

    public static String getXMLMazeExtensionWithPeriod() {
        return "." + XMLExtension.XML_MAZE_EXTENSION;
    }

    public static String getXMLGameExtension() {
        return XMLExtension.XML_SAVED_GAME_EXTENSION;
    }

    public static String getXMLGameExtensionWithPeriod() {
        return "." + XMLExtension.XML_SAVED_GAME_EXTENSION;
    }

    public static String getXMLScoresExtensionWithPeriod() {
        return "." + XMLExtension.XML_SCORES_EXTENSION;
    }

    public static String getXMLRuleSetExtension() {
        return XMLExtension.XML_RULE_SET_EXTENSION;
    }

    public static String getXMLRuleSetExtensionWithPeriod() {
        return "." + XMLExtension.XML_RULE_SET_EXTENSION;
    }
}
