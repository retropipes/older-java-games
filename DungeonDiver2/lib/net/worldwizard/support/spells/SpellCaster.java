package net.worldwizard.support.spells;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.support.battle.BattleDefinitions;
import net.worldwizard.support.creatures.BattleTarget;
import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.effects.Effect;
import net.worldwizard.support.map.generic.GameSounds;
import net.worldwizard.support.resourcemanagers.SoundManager;

public class SpellCaster {
    // Fields
    private static boolean NO_SPELLS_FLAG = false;

    // Private Constructor
    private SpellCaster() {
        // Do nothing
    }

    public static boolean selectAndCastSpell(final Creature caster,
            final int teamID, final boolean aiEnabled,
            final BattleDefinitions battle) {
        boolean result = false;
        SpellCaster.NO_SPELLS_FLAG = false;
        final Spell s = SpellCaster.selectSpell(caster);
        if (s != null) {
            final int power = SpellCaster.selectSpellPower();
            if (power != -1) {
                result = SpellCaster.castSpellWithPower(s, power, caster,
                        teamID, aiEnabled, battle);
                if (!result && !SpellCaster.NO_SPELLS_FLAG) {
                    CommonDialogs.showErrorDialog(
                            "You try to cast a spell, but realize you don't have enough MP!",
                            "Select Spell");
                }
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
                SoundManager.playSound(GameSounds.SPELL_SELECT);
                String dialogResult = null;
                dialogResult = CommonDialogs.showInputDialog(
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
                SpellCaster.NO_SPELLS_FLAG = true;
                CommonDialogs.showErrorDialog(
                        "You try to cast a spell, but realize you don't know any!",
                        "Select Spell");
                return null;
            }
        } else {
            SpellCaster.NO_SPELLS_FLAG = true;
            CommonDialogs.showErrorDialog(
                    "You try to cast a spell, but realize you don't know any!",
                    "Select Spell");
            return null;
        }
    }

    private static int selectSpellPower() {
        final String[] powers = new String[] { "1", "2", "3", "4", "5", "6",
                "7", "8", "9", "X" };
        String dialogResult = null;
        dialogResult = CommonDialogs.showInputDialog(
                "Select a Spell Power Level", "Select Power", powers,
                powers[0]);
        if (dialogResult != null) {
            int index;
            for (index = 0; index < powers.length; index++) {
                if (dialogResult.equals(powers[index])) {
                    break;
                }
            }
            return index + 1;
        } else {
            return -1;
        }
    }

    public static boolean castSpell(final Spell cast, final Creature caster,
            final int teamID, final boolean aiEnabled,
            final BattleDefinitions battle) {
        if (cast != null) {
            final int casterMP = caster.getCurrentMP();
            final int cost = cast.getCost();
            if (casterMP >= cost) {
                // Cast Spell
                caster.drain(cost);
                final Effect eff = cast.getEffect();
                eff.setSource(caster);
                eff.resetEffect();
                final Creature[] targets = SpellCaster.resolveTarget(cast,
                        caster, teamID, aiEnabled, battle);
                // Play spell's associated sound effect, if it has one
                SoundManager.playSound(cast.getSound());
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

    public static boolean castSpellWithPower(final Spell cast, final int power,
            final Creature caster, final int teamID, final boolean aiEnabled,
            final BattleDefinitions battle) {
        if (cast != null) {
            final int casterMP = caster.getCurrentMP();
            final int cost = cast.getCostForPower(power);
            if (casterMP >= cost) {
                // Cast Spell
                caster.drain(cost);
                final Effect eff = cast.getEffect();
                eff.setSource(caster);
                eff.resetEffect();
                eff.modifyEffectForPower(power);
                final Creature[] targets = SpellCaster.resolveTarget(cast,
                        caster, teamID, aiEnabled, battle);
                // Play spell's associated sound effect, if it has one
                SoundManager.playSound(cast.getSound());
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
            final Creature caster, final int teamID, final boolean aiEnabled,
            final BattleDefinitions battle) {
        final BattleTarget target = cast.getTarget();
        final boolean hasAI = caster.hasAI();
        final boolean useAI = hasAI && aiEnabled;
        switch (target) {
        case SELF:
            // Self
            return new Creature[] { battle.getSelfTarget() };
        case ONE_ALLY:
            // One Ally
            if (useAI) {
                return new Creature[] {
                        battle.pickOneFriendOfTeamRandomly(teamID) };
            } else {
                return new Creature[] { battle.pickOneFriendOfTeam(teamID) };
            }
        case ONE_ENEMY:
            // One Enemy
            if (useAI) {
                return new Creature[] {
                        battle.pickOneEnemyOfTeamRandomly(teamID) };
            } else {
                return new Creature[] { battle.pickOneEnemyOfTeam(teamID) };
            }
        case ALL_ALLIES:
            // All Allies
            return battle.getAllFriendsOfTeam(teamID);
        case ALL_ENEMIES:
            // All Enemies
            return battle.getAllEnemiesOfTeam(teamID);
        default:
            return null;
        }
    }
}
