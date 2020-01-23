/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import studio.ignitionigloogames.common.random.RandomRange;

class GameFileManager {
    private GameFileManager() {
        // Do nothing
    }

    public static void save(final File src, final File dst) throws IOException {
        try (FileInputStream in = new FileInputStream(src);
                FileOutputStream out = new FileOutputStream(dst)) {
            final byte[] buf = new byte[1024];
            int len;
            final byte transform = (byte) new RandomRange(1, 250).generate();
            out.write(transform);
            while ((len = in.read(buf)) > 0) {
                for (int x = 0; x < buf.length; x++) {
                    buf[x] += transform;
                }
                out.write(buf, 0, len);
            }
        } catch (final IOException ioe) {
            throw ioe;
        }
    }

    public static void load(final File src, final File dst) throws IOException {
        try (FileInputStream in = new FileInputStream(src);
                FileOutputStream out = new FileOutputStream(dst)) {
            final byte[] buf = new byte[1024];
            int len;
            final byte transform = (byte) in.read();
            while ((len = in.read(buf)) > 0) {
                for (int x = 0; x < buf.length; x++) {
                    buf[x] -= transform;
                }
                out.write(buf, 0, len);
            }
        } catch (final IOException ioe) {
            throw ioe;
        }
    }
}
