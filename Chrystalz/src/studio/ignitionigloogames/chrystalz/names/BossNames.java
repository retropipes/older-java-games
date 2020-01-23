/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.names;

import studio.ignitionigloogames.chrystalz.manager.string.LocalizedFile;
import studio.ignitionigloogames.chrystalz.manager.string.StringManager;

public class BossNames {
    // Private constructor
    private BossNames() {
        // Do nothing
    }

    public static final String getName(final int ID) {
        final String tempBossID = Integer.toString(ID);
        String bossID;
        if (tempBossID.length() == 1) {
            bossID = "0" + tempBossID;
        } else {
            bossID = tempBossID;
        }
        return StringManager.getLocalizedString(LocalizedFile.BOSSES, bossID);
    }
}