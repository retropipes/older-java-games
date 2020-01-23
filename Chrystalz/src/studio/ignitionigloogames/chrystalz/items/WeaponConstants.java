/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.items;

import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.manager.string.LocalizedFile;
import studio.ignitionigloogames.chrystalz.manager.string.StringManager;
import studio.ignitionigloogames.chrystalz.names.ZoneNames;

public class WeaponConstants {
    // Private Constructor
    private WeaponConstants() {
        // Do nothing
    }

    public static final int TYPE_COUNT = 6;
    private static final int[] HIT_SOUND_LOOKUP = {
            SoundConstants.AXE_HIT, SoundConstants.DAGGER_HIT,
            SoundConstants.HAMMER_HIT, SoundConstants.STAFF_HIT,
            SoundConstants.SWORD_HIT, SoundConstants.WAND_HIT };

    public static synchronized String[] getWeaponChoices() {
        return StringManager.getAllLocalizedStrings(LocalizedFile.WEAPON_TYPES,
                6);
    }

    public static synchronized String getWeapon(final int index) {
        return StringManager.getLocalizedString(LocalizedFile.WEAPON_TYPES,
                ZoneNames.getZoneNumber(index));
    }

    public static int getWeaponTypeHitSound(final int index) {
        return WeaponConstants.HIT_SOUND_LOOKUP[index];
    }
}
