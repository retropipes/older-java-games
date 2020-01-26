/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.datamanagers;

import java.util.ArrayList;

import net.worldwizard.xio.ResourceStreamReader;

public class MonsterDataManager {
    public static String[] getMonsterData() {
        try {
            // Fetch data
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    MonsterDataManager.class.getResourceAsStream(
                            "/net/worldwizard/support/resources/data/monster/monsternames.dat"));
            final ArrayList<String> data = new ArrayList<>();
            String raw = "0";
            while (raw != null) {
                raw = rsr.readString();
                data.add(raw);
            }
            rsr.close();
            final Object[] arr = data.toArray();
            final String[] tempres = new String[arr.length];
            int count = 0;
            for (int x = 0; x < arr.length; x++) {
                if (arr[x] != null) {
                    tempres[x] = arr[x].toString();
                    count++;
                }
            }
            final String[] res = new String[count];
            count = 0;
            for (final String tempre : tempres) {
                if (tempre != null) {
                    res[count] = tempre;
                    count++;
                }
            }
            return res;
        } catch (final Exception e) {
            return null;
        }
    }
}
