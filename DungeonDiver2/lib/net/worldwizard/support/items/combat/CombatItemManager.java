package net.worldwizard.support.items.combat;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.support.battle.BattleDefinitions;
import net.worldwizard.support.creatures.BattleTarget;
import net.worldwizard.support.creatures.Creature;
import net.worldwizard.support.creatures.PartyManager;
import net.worldwizard.support.effects.Effect;
import net.worldwizard.support.items.ItemInventory;
import net.worldwizard.support.map.generic.GameSoundConstants;
import net.worldwizard.support.resourcemanagers.SoundManager;

public class CombatItemManager {
    // Fields
    private static final CombatItemList COMBAT_ITEMS = new CombatItemList();
    private static final Creature SOURCE = PartyManager.getNewPCInstance(0, 0,
            0, 0, 0, "");

    // Private Constructor
    private CombatItemManager() {
        // Do nothing
    }

    public static boolean selectAndUseItem(final Creature user,
            final int teamID, final boolean aiEnabled,
            final BattleDefinitions battle) {
        boolean result = false;
        final CombatUsableItem i = CombatItemManager.selectItem(user);
        if (i != null) {
            result = CombatItemManager.useItem(i, user, teamID, aiEnabled,
                    battle);
        }
        return result;
    }

    private static CombatUsableItem selectItem(final Creature user) {
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
                    final CombatUsableItem i = CombatItemManager.COMBAT_ITEMS
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

    public static boolean useItem(final CombatUsableItem used,
            final Creature user, final int teamID, final boolean aiEnabled,
            final BattleDefinitions battle) {
        if (used != null) {
            final Effect e = used.getEffect();
            e.setSource(CombatItemManager.SOURCE);
            used.use();
            e.resetEffect();
            final Creature[] targets = CombatItemManager.resolveTarget(used,
                    user, teamID, aiEnabled, battle);
            // Play item's associated sound effect, if it has one
            SoundManager.playSound(used.getSound());
            for (final Creature target : targets) {
                if (target.isEffectActive(e)) {
                    target.extendEffect(e, e.getInitialRounds());
                } else {
                    e.restoreEffect(target);
                    target.applyEffect(e);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static Creature[] resolveTarget(final CombatUsableItem used,
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
