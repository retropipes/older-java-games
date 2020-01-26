/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.resourcemanagers;

import java.net.URL;

public class HelpManager {
    public static URL getHelpURL() {
        return HelpManager.class.getResource(
                "/com/puttysoftware/rulemazer/resources/help/RuleMazerHelp.html");
    }
}