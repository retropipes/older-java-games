/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.


All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import java.io.IOException;
import java.util.ArrayList;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.common.fileio.ResourceStreamReader;

public class ImageDataManager {
    public static String[] getObjectGraphicsData() {
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                ImageDataManager.class.getResourceAsStream(
                        "/assets/data/images/objects.txt"))) {
            // Fetch data
            final ArrayList<String> rawData = new ArrayList<>();
            String line = "";
            while (line != null) {
                line = rsr.readString();
                if (line != null) {
                    rawData.add(line);
                }
            }
            return rawData.toArray(new String[rawData.size()]);
        } catch (final IOException e) {
            Chrystalz.getErrorLogger().logError(e);
            return null;
        }
    }

    public static String[] getStatGraphicsData() {
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                ImageDataManager.class.getResourceAsStream(
                        "/assets/data/images/stats.txt"))) {
            // Fetch data
            final ArrayList<String> rawData = new ArrayList<>();
            String line = "";
            while (line != null) {
                line = rsr.readString();
                if (line != null) {
                    rawData.add(line);
                }
            }
            return rawData.toArray(new String[rawData.size()]);
        } catch (final IOException e) {
            Chrystalz.getErrorLogger().logError(e);
            return null;
        }
    }
}
