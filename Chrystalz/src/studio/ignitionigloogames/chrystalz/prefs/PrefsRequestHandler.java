/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.
All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.prefs;

import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;

public class PrefsRequestHandler implements PreferencesHandler {
    public PrefsRequestHandler() {
        // Do nothing
    }

    @Override
    public void handlePreferences(final PreferencesEvent pe) {
        PreferencesManager.showPrefs();
    }
}