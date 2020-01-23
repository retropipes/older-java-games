/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.spells;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver3.support.battle.BattleDefinitions;
import com.puttysoftware.dungeondiver3.support.creatures.BattleTarget;
import com.puttysoftware.dungeondiver3.support.creatures.Creature;
import com.puttysoftware.dungeondiver3.support.effects.Effect;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.SoundManager;

public class SpellCaster {
    // Fields
    private static boolean NO_SPELLS_FLAG = false;
    private static final String[] powers = new String[] { "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "X" };

    // Private Constructor
    private SpellCaster() {
        // Do nothing
    }

    public static boolean selectAndCastSpell(final Creature caster,
            final int teamID, final boolean aiEnabled,
            final BattleDefinitions battle) {
        boolean result = false;
        SpellCaster.NO_SPELLS_FLAG = false;
        Spell s = SpellCaster.selectSpell(caster);
        if (s != null) {
            int power = SpellCaster.selectSpellPower();
            if (power != -1) {
                result = SpellCaster.castSpellWithPower(s, power, caster,
                        teamID, aiEnabled, battle);
                if (!result && !SpellCaster.NO_SPELLS_FLAG) {
                    CommonDialogs
                            .showErrorDialog(
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
                SoundManager.playSound(GameSoundConstants.SOUND_SPELL_SELECT);
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
                    return book.getSpellByName(names[index]);
                } else {
                    return null;
                }
            } else {
                SpellCaster.NO_SPELLS_FLAG = true;
                CommonDialogs
                        .showErrorDialog(
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
        String dialogResult = null;
        dialogResult = CommonDialogs
                .showInputDialog("Select a Spell Power Level", "Select Power",
                        powers, powers[0]);
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
            int casterMP = caster.getCurrentMP();
            int cost = cast.getCost();
            if (casterMP >= cost) {
                // Cast Spell
                caster.drain(cost);
                Effect eff = cast.getEffect();
                eff.setSource(caster);
                // Play spell's associated sound effect, if it has one
                SoundManager.playSound(cast.getSound());
                eff.resetEffect();
                Creature[] targets = SpellCaster.resolveTarget(cast, caster,
                        teamID, aiEnabled, battle);
                for (int x = 0; x < targets.length; x++) {
                    Creature target = targets[x];
                    if (target.isEffectActive(eff)) {
                        target.extendEffect(eff, eff.getInitialRounds());
                    } else {
                        eff.restoreEffect();
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

    private static boolean castSpellWithPower(final Spell cast,
            final int power, final Creature caster, final int teamID,
            final boolean aiEnabled, final BattleDefinitions battle) {
        if (cast != null) {
            int casterMP = caster.getCurrentMP();
            int cost = cast.getCostForPower(power);
            if (casterMP >= cost) {
                // Cast Spell
                caster.drain(cost);
                Effect eff = cast.getEffect();
                eff.setSource(caster);
                // Play spell's associated sound effect, if it has one
                SoundManager.playSound(cast.getSound());
                eff.resetEffect();
                eff.modifyEffectForPower(power);
                Creature[] targets = SpellCaster.resolveTarget(cast, caster,
                        teamID, aiEnabled, battle);
                for (int x = 0; x < targets.length; x++) {
                    Creature target = targets[x];
                    if (target.isEffectActive(eff)) {
                        target.extendEffect(eff, eff.getInitialRounds());
                    } else {
                        eff.restoreEffect();
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
            final Creature caster, final int teamID, boolean aiEnabled,
            BattleDefinitions battle) {
        BattleTarget target = cast.getTarget();
        boolean hasAI = caster.hasAI();
        boolean useAI = hasAI && aiEnabled;
        switch (target) {
        case SELF:
            // Self
            return new Creature[] { battle.getSelfTarget() };
        case ONE_ALLY:
            // One Ally
            if (useAI) {
                return new Creature[] { battle
                        .pickOneFriendOfTeamRandomly(teamID) };
            } else {
                SoundManager.playSound(GameSoundConstants.SOUND_ON_WHO);
                return new Creature[] { battle.pickOneFriendOfTeam(teamID) };
            }
        case ONE_ENEMY:
            // One Enemy
            if (useAI) {
                return new Creature[] { battle
                        .pickOneEnemyOfTeamRandomly(teamID) };
            } else {
                SoundManager.playSound(GameSoundConstants.SOUND_ON_WHO);
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
