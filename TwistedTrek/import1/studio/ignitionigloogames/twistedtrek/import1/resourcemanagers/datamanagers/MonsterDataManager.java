/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.datamanagers;

import java.util.Vector;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;

public class MonsterDataManager {
    public static String[] getMonsterData() {
	try (final ResourceStreamReader rsr = new ResourceStreamReader(MonsterDataManager.class
		.getResourceAsStream("/net/worldwizard/fantastle5/resources/data/monster/monsternames.dat"))) {
	    // Fetch data
	    final Vector<String> data = new Vector<>(10, 10);
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
