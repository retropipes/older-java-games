/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.creatures.castes;

import javax.swing.JFrame;

import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.spells.SpellBook;
import net.dynamicdungeon.dynamicdungeon.spells.SpellBookLoader;

public class CasteManager {
    private static boolean CACHE_CREATED = false;
    private static Caste[] CACHE;
    private static String[] DESC_CACHE;

    public static Caste selectCaste(final JFrame owner) {
        CasteManager.createCache();
        final String[] names = CasteConstants.CASTE_NAMES;
        String dialogResult = null;
        dialogResult = PartyManager.showCreationDialog(owner, "Select a Caste",
                "Create Character", names, CasteManager.DESC_CACHE);
        if (dialogResult != null) {
            int index;
            for (index = 0; index < names.length; index++) {
                if (dialogResult.equals(names[index])) {
                    break;
                }
            }
            return CasteManager.getCaste(index);
        } else {
            return null;
        }
    }

    public static Caste getCaste(final int casteID) {
        CasteManager.createCache();
        return CasteManager.CACHE[casteID];
    }

    public static SpellBook getSpellBookByID(final int ID) {
        return SpellBookLoader.loadSpellBook(ID);
    }

    private static void createCache() {
        if (!CasteManager.CACHE_CREATED) {
            // Create cache
            CasteManager.CACHE = new Caste[CasteConstants.CASTES_COUNT];
            CasteManager.DESC_CACHE = new String[CasteConstants.CASTES_COUNT];
            for (int x = 0; x < CasteConstants.CASTES_COUNT; x++) {
                CasteManager.CACHE[x] = CasteLoader
                        .loadCaste(Caste.casteIDtoName(x));
                CasteManager.DESC_CACHE[x] = CasteManager.CACHE[x]
                        .getDescription();
            }
            CasteManager.CACHE_CREATED = true;
        }
    }
}
