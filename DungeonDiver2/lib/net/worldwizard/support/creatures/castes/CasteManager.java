package net.worldwizard.support.creatures.castes;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.support.spells.SpellBook;
import net.worldwizard.support.spells.SpellBookLoader;
import net.worldwizard.support.spells.SpellBookRegistration;

public class CasteManager implements CasteConstants {
    private static boolean CACHE_CREATED = false;
    private static Caste[] CACHE;

    public static Caste selectCaste() {
        final String[] names = CasteConstants.CASTE_NAMES;
        String dialogResult = null;
        dialogResult = CommonDialogs.showInputDialog("Select a Caste",
                "Create Character", names, names[0]);
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
        if (!CasteManager.CACHE_CREATED) {
            // Create cache
            CasteManager.CACHE = new Caste[CasteConstants.CASTES_COUNT];
            for (int x = 0; x < CasteConstants.CASTES_COUNT; x++) {
                CasteManager.CACHE[x] = CasteLoader
                        .loadCaste(Caste.casteIDtoFilename(casteID));
            }
            CasteManager.CACHE_CREATED = true;
        }
        return CasteManager.CACHE[casteID];
    }

    public static SpellBook getSpellBookByID(final int ID) {
        final String name = CasteManager.getCaste(ID).getName();
        final String sbid = SpellBookRegistration.getIDForName(name);
        return SpellBookLoader.loadSpellBook(sbid);
    }
}
