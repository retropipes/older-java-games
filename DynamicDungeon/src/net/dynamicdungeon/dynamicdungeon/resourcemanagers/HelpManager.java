package net.dynamicdungeon.dynamicdungeon.resourcemanagers;

import java.net.URL;

public class HelpManager {
    public static URL getHelpURL() {
        return HelpManager.class.getResource(
                "/net/dynamicdungeon/dynamicdungeon/resources/help/manual.html");
    }
}