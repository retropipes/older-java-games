package net.worldwizard.worldz.creatures.castes;

import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.creatures.castes.spellbooks.AssassinSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.BasherSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.CurerSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.DestroyerSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.EclecticSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.FoolSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.GuruSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.HunterSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.JumperSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.KnightSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.LocksmithSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.MonkSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.NinjaSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.OverseerSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.PickpocketSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.RogueSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.SpySpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.TeacherSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.WarlockSpellBook;
import net.worldwizard.worldz.creatures.castes.spellbooks.YellerSpellBook;
import net.worldwizard.worldz.spells.SpellBook;

public class CasteManager implements CasteConstants {
    private static boolean CACHE_CREATED = false;
    private static Caste[] CACHE;

    public static Caste selectCaste() {
        final String[] names = CasteConstants.CASTE_NAMES;
        String dialogResult = null;
        dialogResult = Messager.showInputDialog("Select a Caste",
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
                CasteManager.CACHE[x] = new Caste(x);
            }
            CasteManager.CACHE_CREATED = true;
        }
        return CasteManager.CACHE[casteID];
    }

    public static SpellBook getSpellBookByID(final int ID) {
        switch (ID) {
        case CasteConstants.CASTE_ASSASSIN:
            return new AssassinSpellBook();
        case CasteConstants.CASTE_BASHER:
            return new BasherSpellBook();
        case CasteConstants.CASTE_CURER:
            return new CurerSpellBook();
        case CasteConstants.CASTE_DESTROYER:
            return new DestroyerSpellBook();
        case CasteConstants.CASTE_ECLECTIC:
            return new EclecticSpellBook();
        case CasteConstants.CASTE_FOOL:
            return new FoolSpellBook();
        case CasteConstants.CASTE_GURU:
            return new GuruSpellBook();
        case CasteConstants.CASTE_HUNTER:
            return new HunterSpellBook();
        case CasteConstants.CASTE_JUMPER:
            return new JumperSpellBook();
        case CasteConstants.CASTE_KNIGHT:
            return new KnightSpellBook();
        case CasteConstants.CASTE_LOCKSMITH:
            return new LocksmithSpellBook();
        case CasteConstants.CASTE_MONK:
            return new MonkSpellBook();
        case CasteConstants.CASTE_NINJA:
            return new NinjaSpellBook();
        case CasteConstants.CASTE_OVERSEER:
            return new OverseerSpellBook();
        case CasteConstants.CASTE_PICKPOCKET:
            return new PickpocketSpellBook();
        case CasteConstants.CASTE_ROGUE:
            return new RogueSpellBook();
        case CasteConstants.CASTE_SPY:
            return new SpySpellBook();
        case CasteConstants.CASTE_TEACHER:
            return new TeacherSpellBook();
        case CasteConstants.CASTE_WARLOCK:
            return new WarlockSpellBook();
        case CasteConstants.CASTE_YELLER:
            return new YellerSpellBook();
        default:
            return null;
        }
    }
}
