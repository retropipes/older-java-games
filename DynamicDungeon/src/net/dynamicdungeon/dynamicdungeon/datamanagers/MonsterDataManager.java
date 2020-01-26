/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.datamanagers;

import java.io.IOException;
import java.util.ArrayList;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.fileutils.ResourceStreamReader;

public class MonsterDataManager {
    public static String[] getMonsterData(final int level,
            final boolean display) {
        String extraPath;
        if (display) {
            extraPath = "display/";
        } else {
            extraPath = "file/";
        }
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                MonsterDataManager.class.getResourceAsStream(
                        "/net/dynamicdungeon/dynamicdungeon/resources/data/monsters/"
                                + extraPath + "level" + level + ".txt"))) {
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
