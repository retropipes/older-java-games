/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz;

import java.io.IOException;

public class VersionException extends IOException {
    private static final long serialVersionUID = 7521249394165201264L;

    public VersionException(final String message) {
        super(message);
    }
}
