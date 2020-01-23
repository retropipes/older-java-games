/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.dungeon;

import java.io.IOException;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.game.FileHooks;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final FileIOReader reader, final int formatVersion)
            throws IOException {
        Chrystalz.getApplication().getGame();
        FileHooks.loadGameHook(reader);
    }

    @Override
    public void writeSuffix(final FileIOWriter writer) throws IOException {
        Chrystalz.getApplication().getGame();
        FileHooks.saveGameHook(writer);
    }
}
