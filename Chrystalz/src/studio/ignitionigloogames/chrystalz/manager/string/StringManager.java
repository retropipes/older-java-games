/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.string;

import java.io.IOException;
import java.util.Properties;

import studio.ignitionigloogames.chrystalz.manager.file.Extension;
import studio.ignitionigloogames.chrystalz.names.ZoneNames;

public class StringManager {
    private StringManager() {
        // Do nothing
    }

    private static final Properties LOCAL = new Properties();
    private static final Properties GLOBAL = new Properties();
    private static final String ERROR = "Error!";

    public static String getLocalizedString(final LocalizedFile file,
            final int key) {
        return StringManager.getLocalizedString(file, Integer.toString(key));
    }

    public static String[] getAllLocalizedStrings(final LocalizedFile file,
            final int max) {
        final String[] retVal = new String[max];
        try {
            StringManager.LOCAL.load(StringManager.class
                    .getResourceAsStream(FilePaths.BASE + FilePaths.LANG_DEFAULT
                            + LocalizedFileList.LIST[file.ordinal()]
                            + Extension.getStringExtensionWithPeriod()));
            for (int k = 0; k < max; k++) {
                final String key = ZoneNames.getZoneNumber(k);
                retVal[k] = StringManager.LOCAL.getProperty(key);
            }
        } catch (final IOException ioe) {
            for (int k = 0; k < max; k++) {
                retVal[k] = StringManager.ERROR;
            }
        }
        return retVal;
    }

    public static String getLocalizedString(final LocalizedFile file,
            final String key) {
        try {
            StringManager.LOCAL.load(StringManager.class
                    .getResourceAsStream(FilePaths.BASE + FilePaths.LANG_DEFAULT
                            + LocalizedFileList.LIST[file.ordinal()]
                            + Extension.getStringExtensionWithPeriod()));
            return StringManager.LOCAL.getProperty(key);
        } catch (final IOException ioe) {
            return StringManager.ERROR;
        }
    }

    public static String getGlobalString(final GlobalFile file, final int key) {
        return StringManager.getGlobalString(file, Integer.toString(key));
    }

    public static String getGlobalString(final GlobalFile file,
            final String key) {
        try {
            StringManager.GLOBAL.load(StringManager.class.getResourceAsStream(
                    FilePaths.BASE + GlobalFileList.LIST[file.ordinal()]
                            + Extension.getStringExtensionWithPeriod()));
            return StringManager.GLOBAL.getProperty(key);
        } catch (final IOException ioe) {
            return StringManager.ERROR;
        }
    }
}
