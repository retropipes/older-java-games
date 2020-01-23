/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.items.combat;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver3.support.battle.BattleDefinitions;
import com.puttysoftware.dungeondiver3.support.creatures.BattleTarget;
import com.puttysoftware.dungeondiver3.support.creatures.Creature;
import com.puttysoftware.dungeondiver3.support.creatures.PartyManager;
import com.puttysoftware.dungeondiver3.support.effects.Effect;
import com.puttysoftware.dungeondiver3.support.items.ItemInventory;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.SoundManager;

public class CombatItemChucker {
    // Fields
    private static final CombatItemList COMBAT_ITEMS = new CombatItemList();
    private static final Creature SOURCE = PartyManager.getNewPCInstance(0, 0,
            0, 0, 0, "");

    // Private Constructor
    private CombatItemChucker() {
        // Do nothing
    }

    public static boolean selectAndUseItem(final Creature user, int teamID,
            boolean aiEnabled, BattleDefinitions battle) {
        boolean result = false;
        CombatItem i = CombatItemChucker.selectItem(user);
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
                SoundManager.playSound(GameSoundConstants.SOUND_SPELL_SELECT);
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
                    CombatItem i = CombatItemChucker.COMBAT_ITEMS
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
            int teamID, boolean aiEnabled, BattleDefinitions battle) {
        if (used != null) {
            Effect e = used.getEffect();
            e.setSource(CombatItemChucker.SOURCE);
            // Play item's associated sound effect, if it has one
            SoundManager.playSound(used.getSound());
            used.use();
            e.resetEffect();
            Creature[] targets = CombatItemChucker.resolveTarget(used, user,
                    teamID, aiEnabled, battle);
            for (int x = 0; x < targets.length; x++) {
                Creature target = targets[x];
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
            final Creature user, int teamID, boolean aiEnabled,
            BattleDefinitions battle) {
        BattleTarget target = used.getTarget();
        boolean hasAI = user.hasAI();
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
