/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.items.combat;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.battle.BattleTarget;
import com.puttysoftware.mastermaze.battle.map.MapBattleDefinitions;
import com.puttysoftware.mastermaze.creatures.Creature;
import com.puttysoftware.mastermaze.creatures.PartyManager;
import com.puttysoftware.mastermaze.effects.Effect;
import com.puttysoftware.mastermaze.items.ItemInventory;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class CombatItemChucker {
    // Fields
    private static final CombatItemList COMBAT_ITEMS = new CombatItemList();
    private static final Creature SOURCE = PartyManager.getNewPCInstance(0, 0,
            0, 0, 0, "");

    // Private Constructor
    private CombatItemChucker() {
        // Do nothing
    }

    public static boolean selectAndUseItem(final Creature user) {
        boolean result = false;
        final CombatItem i = CombatItemChucker.selectItem(user);
        if (i != null) {
            result = CombatItemChucker.useItem(i, user);
        }
        return result;
    }

    public static boolean useItem(final CombatItem used, final Creature user) {
        if (used != null) {
            final Effect e = used.getEffect();
            // Play item's associated sound effect, if it has one
            final int snd = used.getSound();
            SoundManager.playSound(snd);
            e.resetEffect();
            final Creature target = CombatItemChucker.resolveTarget(used,
                    user.getTeamID());
            used.use();
            if (target.isEffectActive(e)) {
                target.extendEffect(e, e.getInitialRounds());
            } else {
                e.restoreEffect();
                target.applyEffect(e);
            }
            return true;
        } else {
            return false;
        }
    }

    private static Creature resolveTarget(final CombatItem cast,
            final int teamID) {
        final BattleTarget target = cast.getTarget();
        switch (target) {
        case ONE_ALLY:
            if (teamID == Creature.TEAM_PARTY) {
                return PartyManager.getParty().getLeader();
            } else {
                return MasterMaze.getApplication().getBattle().getEnemy();
            }
        case ONE_ENEMY:
            if (teamID == Creature.TEAM_PARTY) {
                return MasterMaze.getApplication().getBattle().getEnemy();
            } else {
                return PartyManager.getParty().getLeader();
            }
        default:
            return null;
        }
    }

    public static boolean selectAndUseItem(final Creature user,
            final int teamID, final boolean aiEnabled,
            final MapBattleDefinitions battle) {
        boolean result = false;
        final CombatItem i = CombatItemChucker.selectItem(user);
        if (i != null) {
            result = CombatItemChucker.useItem(i, user, teamID, aiEnabled,
                    battle);
        }
        return result;
    }

    private static CombatItem selectItem(final Creature user) {
        final ItemInventory ii = user.getItems();
        if (ii != null) {
            final String[] names = ii.generateCombatUsableStringArray();
            final String[] displayNames = ii
                    .generateCombatUsableDisplayStringArray();
            if (names != null && displayNames != null) {
                // Play using item sound
                SoundManager.playSound(SoundConstants.SOUND_SPELL);
                String dialogResult = null;
                dialogResult = CommonDialogs.showInputDialog(
                        "Select an Item to Use", "Select Item", displayNames,
                        displayNames[0]);
                if (dialogResult != null) {
                    int index;
                    for (index = 0; index < displayNames.length; index++) {
                        if (dialogResult.equals(displayNames[index])) {
                            break;
                        }
                    }
                    final CombatItem i = CombatItemChucker.COMBAT_ITEMS
                            .getItemByName(names[index]);
                    if (ii.getUses(i) > 0) {
                        return i;
                    } else {
                        CommonDialogs
                                .showErrorDialog(
                                        "You try to use an item, but realize you've run out!",
                                        "Select Item");
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                CommonDialogs
                        .showErrorDialog(
                                "You try to use an item, but realize you don't have any!",
                                "Select Item");
                return null;
            }
        } else {
            CommonDialogs.showErrorDialog(
                    "You try to use an item, but realize you don't have any!",
                    "Select Item");
            return null;
        }
    }

    public static boolean useItem(final CombatItem used, final Creature user,
            final int teamID, final boolean aiEnabled,
            final MapBattleDefinitions battle) {
        if (used != null) {
            final Effect e = used.getEffect();
            e.setSource(CombatItemChucker.SOURCE);
            // Play item's associated sound effect, if it has one
            SoundManager.playSound(used.getSound());
            used.use();
            e.resetEffect();
            final Creature[] targets = CombatItemChucker.resolveTarget(used,
                    user, teamID, aiEnabled, battle);
            for (final Creature target : targets) {
                if (target.isEffectActive(e)) {
                    target.extendEffect(e, e.getInitialRounds());
                } else {
                    e.restoreEffect();
                    target.applyEffect(e);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static Creature[] resolveTarget(final CombatItem used,
            final Creature user, final int teamID, final boolean aiEnabled,
            final MapBattleDefinitions battle) {
        final BattleTarget target = used.getTarget();
        final boolean hasAI = user.hasAI();
        final boolean useAI = hasAI && aiEnabled;
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
                SoundManager.playSound(SoundConstants.SOUND_ON_WHO);
                return new Creature[] { battle.pickOneFriendOfTeam(teamID) };
            }
        case ONE_ENEMY:
            // One Enemy
            if (useAI) {
                return new Creature[] { battle
                        .pickOneEnemyOfTeamRandomly(teamID) };
            } else {
                SoundManager.playSound(SoundConstants.SOUND_ON_WHO);
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
