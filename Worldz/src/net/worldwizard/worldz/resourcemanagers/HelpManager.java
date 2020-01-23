/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.resourcemanagers;

import java.net.URL;

public class HelpManager {
    public static URL getHelpURL() {
        return HelpManager.class
                .getResource("/net/worldwizard/worldz/resources/help/WorldzHelp.html");
    }
}