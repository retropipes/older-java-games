package net.worldwizard.worldz.spells;

import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.creatures.Creature;
import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.effects.Effect;
import net.worldwizard.worldz.resourcemanagers.SoundManager;

public class SpellBookManager {
    // Fields
    private static boolean NO_SPELLS_FLAG = false;

    // Private Constructor
    private SpellBookManager() {
        // Do nothing
    }

    public static boolean selectAndCastSpell(final Creature caster) {
        boolean result = false;
        SpellBookManager.NO_SPELLS_FLAG = false;
        final Spell s = SpellBookManager.selectSpell(caster);
        if (s != null) {
            result = SpellBookManager.castSpell(s, caster);
            if (!result && !SpellBookManager.NO_SPELLS_FLAG) {
                Messager.showErrorDialog(
                        "You try to cast a spell, but realize you don't have enough MP!",
                        "Select Spell");
            }
        }
        return result;
    }

    private static Spell selectSpell(final Creature caster) {
        final SpellBook book = caster.getSpellBook();
        if (book != null) {
            final String[] names = book.getAllSpellNames();
            final String[] displayNames = book.getAllSpellNamesWithCosts();
            if (names != null && displayNames != null) {
                // Play casting spell sound
                if (Worldz.getApplication().getPrefsManager()
                        .getSoundEnabled(PreferencesManager.SOUNDS_BATTLE)) {
                    SoundManager.playSound("spell");
                }
                String dialogResult = null;
                dialogResult = Messager.showInputDialog(
                        "Select a Spell to Cast", "Select Spell", displayNames,
                        displayNames[0]);
                if (dialogResult != null) {
                    int index;
                    for (index = 0; index < displayNames.length; index++) {
                        if (dialogResult.equals(displayNames[index])) {
                            break;
                        }
                    }
                    final Spell s = book.getSpellByName(names[index]);
                    return s;
                } else {
                    return null;
                }
            } else {
                SpellBookManager.NO_SPELLS_FLAG = true;
                Messager.showErrorDialog(
                        "You try to cast a spell, but realize you don't know any!",
                        "Select Spell");
                return null;
            }
        } else {
            SpellBookManager.NO_SPELLS_FLAG = true;
            Messager.showErrorDialog(
                    "You try to cast a spell, but realize you don't know any!",
                    "Select Spell");
            return null;
        }
    }

    public static boolean castSpell(final Spell cast, final Creature caster) {
        if (cast != null) {
            final int casterMP = caster.getCurrentMP();
            final int cost = cast.getCost();
            if (casterMP >= cost) {
                // Cast Spell
                caster.drain(cost);
                final Effect eff = cast.getEffect();
                eff.setSource(caster);
                // Play spell's associated sound effect, if it has one
                final String snd = cast.getSound();
                if (snd != null) {
                    if (Worldz.getApplication().getPrefsManager()
                            .getSoundEnabled(
                                    PreferencesManager.SOUNDS_BATTLE)) {
                        SoundManager.playSound(snd);
                    }
                }
                eff.resetEffect();
                final Creature[] targets = SpellBookManager.resolveTarget(cast,
                        caster);
                for (final Creature target : targets) {
                    if (target.isEffectActive(eff)) {
                        target.extendEffect(eff, eff.getInitialRounds());
                    } else {
                        eff.restoreEffect(target);
                        target.applyEffect(eff);
                    }
                }
                return true;
            } else {
                // Not enough MP
                return false;
            }
        } else {
            return false;
        }
    }

    private static Creature[] resolveTarget(final Spell cast,
            final Creature caster) {
        final char target = cast.getTarget();
        final boolean hasAI = caster.hasAI();
        final boolean aiEnabled = Worldz.getApplication().getBattle().isAIOn();
        final boolean useAI = hasAI && aiEnabled;
        switch (target) {
        case 'P':
            // One Friend
            if (useAI) {
                return new Creature[] {
                        PartyManager.getParty().pickOnePartyMemberRandomly() };
            } else {
                return new Creature[] {
                        PartyManager.getParty().pickOnePartyMember() };
            }
        case 'E':
            // One Enemy
            if (useAI) {
                return new Creature[] { Worldz.getApplication().getBattle()
                        .pickOneEnemyRandomly() };
            } else {
                return new Creature[] {
                        Worldz.getApplication().getBattle().pickOneEnemy() };
            }
        case 'F':
            // All Friends
            return Worldz.getApplication().getBattle().getAllFriends();
        case 'A':
            // All Enemies
            return Worldz.getApplication().getBattle().getAllEnemies();
        default:
            return null;
        }
    }

    public static SpellBook getEnemySpellBookByID(final int ID) {
        switch (ID) {
        case 1:
            return new LowLevelSpellBook();
        case 2:
            return new MidLevelSpellBook();
        case 3:
            return new HighLevelSpellBook();
        case 4:
            return new ToughLevelSpellBook();
        default:
            return null;
        }
    }
}
