/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.file;

import java.io.File;
import java.io.FilenameFilter;

public class GameFinder implements FilenameFilter {
    @Override
    public boolean accept(final File f, final String s) {
        final String extension = GameFinder.getExtension(s);
        if (extension != null) {
            if (extension.equals(Extension.getGameExtension())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private static String getExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
