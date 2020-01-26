/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.spells;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.riskyrescue.assets.SoundConstants;
import com.puttysoftware.riskyrescue.assets.SoundManager;
import com.puttysoftware.riskyrescue.battle.BattleDefinitions;
import com.puttysoftware.riskyrescue.creatures.BattleTarget;
import com.puttysoftware.riskyrescue.creatures.Creature;
import com.puttysoftware.riskyrescue.effects.Effect;

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
            result = SpellCaster.castSpell(s, caster, teamID, aiEnabled,
                    battle);
            if (!result && !SpellCaster.NO_SPELLS_FLAG) {
                CommonDialogs.showErrorDialog(
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
                SoundManager.playSound(SoundConstants.QUESTION);
                String dialogResult;
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

    public static boolean castSpell(final Spell cast, final Creature caster,
            final int teamID, final boolean aiEnabled,
            final BattleDefinitions battle) {
        if (cast != null && caster != null && battle != null) {
            final int casterMP = caster.getCurrentMP();
            final int cost = cast.getCost();
            if (casterMP >= cost) {
                // Cast Spell
                caster.drain(cost);
                final Effect eff = cast.getEffect();
                eff.resetEffect();
                final Creature[] targets = SpellCaster.resolveTarget(cast,
                        caster, teamID, aiEnabled, battle);
                // Play spell's associated sound effect, if it has one
                SoundManager.playSound(cast.getSound());
                for (final Creature target : targets) {
                    if (target != null) {
                        if (target.isEffectActive(eff)) {
                            target.extendEffect(eff, eff.getInitialRounds());
                        } else {
                            eff.restoreEffect();
                            target.applyEffect(eff);
                        }
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
                SoundManager.playSound(SoundConstants.ON_WHO);
                return new Creature[] { battle.pickOneFriendOfTeam(teamID) };
            }
        case ONE_ENEMY:
            // One Enemy
            if (useAI) {
                return new Creature[] {
                        battle.pickOneEnemyOfTeamRandomly(teamID) };
            } else {
                SoundManager.playSound(SoundConstants.ON_WHO);
                return new Creature[] { battle.pickOneEnemyOfTeam(teamID) };
            }
        default:
            return null;
        }
    }
}
