/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items.combat;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.support.battle.BattleDefinitions;
import com.puttysoftware.gemma.support.creatures.BattleTarget;
import com.puttysoftware.gemma.support.creatures.Creature;
import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.gemma.support.effects.Effect;
import com.puttysoftware.gemma.support.items.ItemInventory;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.resourcemanagers.SoundManager;

public class CombatItemChucker {
    // Fields
    private static final CombatItemList COMBAT_ITEMS = new CombatItemList();
    private static final Creature SOURCE = PartyManager.getNewPCInstance(0, 0,
            0, 0, "");

    // Private Constructor
    private CombatItemChucker() {
        // Do nothing
    }

    public static boolean selectAndUseItem(final Creature user,
            final int teamID, final boolean aiEnabled,
            final BattleDefinitions battle) {
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
                SoundManager.playSound(GameSoundConstants.SOUND_SPELL_SELECT);
                String dialogResult;
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
                        CommonDialogs.showErrorDialog(
                                "You try to use an item, but realize you've run out!",
                                "Select Item");
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                CommonDialogs.showErrorDialog(
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
            final BattleDefinitions battle) {
        if (used != null) {
            final Effect e = used.getEffect();
            e.setSource(CombatItemChucker.SOURCE);
            used.use();
            e.resetEffect();
            final Creature[] targets = CombatItemChucker.resolveTarget(used,
                    user, teamID, aiEnabled, battle);
            // Play item's associated sound effect, if it has one
            SoundManager.playSound(used.getSound());
            for (int x = 0; x < targets.length; x++) {
                final Creature target = targets[x];
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
            final BattleDefinitions battle) {
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
                    return new Creature[] {
                            battle.pickOneFriendOfTeamRandomly(teamID) };
                } else {
                    SoundManager.playSound(GameSoundConstants.SOUND_ON_WHO);
                    return new Creature[] { battle.pickOneFriendOfTeam(teamID) };
                }
            case ONE_ENEMY:
                // One Enemy
                if (useAI) {
                    return new Creature[] {
                            battle.pickOneEnemyOfTeamRandomly(teamID) };
                } else {
                    SoundManager.playSound(GameSoundConstants.SOUND_ON_WHO);
                    return new Creature[] { battle.pickOneEnemyOfTeam(teamID) };
                }
            default:
                return null;
        }
    }
}
