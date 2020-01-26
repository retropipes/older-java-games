/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.datamanagers;

import java.io.IOException;
import java.util.ArrayList;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.fileutils.ResourceStreamReader;

public class GraphicsDataManager {
    public static String[] getObjectGraphicsData() {
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                GraphicsDataManager.class.getResourceAsStream(
                        "/net/dynamicdungeon/dynamicdungeon/resources/data/graphics/objects.txt"))) {
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
            DynamicDungeon.getErrorLogger().logError(e);
            return null;
        }
    }

    public static String[] getStatGraphicsData() {
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                GraphicsDataManager.class.getResourceAsStream(
                        "/net/dynamicdungeon/dynamicdungeon/resources/data/graphics/stats.txt"))) {
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
            DynamicDungeon.getErrorLogger().logError(e);
            return null;
        }
    }
}
