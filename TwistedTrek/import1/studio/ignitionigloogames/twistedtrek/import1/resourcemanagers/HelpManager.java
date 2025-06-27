/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers;

import java.net.URL;

public class HelpManager {
    public static URL getHelpURL() {
        return HelpManager.class.getResource("/net/worldwizard/fantastle5/resources/help/Import1Help.html");
    }
}