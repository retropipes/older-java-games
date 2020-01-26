package net.worldwizard.worldz.items.combat;

import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.creatures.Creature;
import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.effects.Effect;
import net.worldwizard.worldz.items.ItemInventory;
import net.worldwizard.worldz.resourcemanagers.SoundManager;

public class CombatItemManager {
    // Fields
    private static final CombatItemList COMBAT_ITEMS = new CombatItemList();
    private static final Creature SOURCE = PartyManager.getNewPCInstance(0, 0,
            0, 0, 0, "");

    // Private Constructor
    private CombatItemManager() {
        // Do nothing
    }

    public static boolean selectAndUseItem(final Creature user) {
        boolean result = false;
        final CombatUsableItem i = CombatItemManager.selectItem(user);
        if (i != null) {
            result = CombatItemManager.useItem(i, user);
        }
        return result;
    }

    private static CombatUsableItem selectItem(final Creature caster) {
        final ItemInventory ii = caster.getItems();
        if (ii != null) {
            final String[] names = ii.generateCombatUsableStringArray();
            final String[] displayNames = ii
                    .generateCombatUsableDisplayStringArray();
            if (names != null && displayNames != null) {
                // Play using item sound
                if (Worldz.getApplication().getPrefsManager()
                        .getSoundEnabled(PreferencesManager.SOUNDS_BATTLE)) {
                    SoundManager.playSound("spell");
                }
                String dialogResult = null;
                dialogResult = Messager.showInputDialog("Select an Item to Use",
                        "Select Item", displayNames, displayNames[0]);
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
                        Messager.showErrorDialog(
                                "You try to use an item, but realize you've run out!",
                                "Select Item");
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                Messager.showErrorDialog(
                        "You try to use an item, but realize you don't have any!",
                        "Select Item");
                return null;
            }
        } else {
            Messager.showErrorDialog(
                    "You try to use an item, but realize you don't have any!",
                    "Select Item");
            return null;
        }
    }

    public static boolean useItem(final CombatUsableItem used,
            final Creature user) {
        if (used != null) {
            final Effect e = used.getEffect();
            e.setSource(CombatItemManager.SOURCE);
            // Play item's associated sound effect, if it has one
            final String snd = used.getSound();
            if (snd != null) {
                if (Worldz.getApplication().getPrefsManager()
                        .getSoundEnabled(PreferencesManager.SOUNDS_BATTLE)) {
                    SoundManager.playSound(snd);
                }
            }
            e.resetEffect();
            final Creature[] targets = CombatItemManager.resolveTarget(used,
                    user);
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
            final Creature user) {
        final char target = used.getTarget();
        final boolean hasAI = user.hasAI();
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
}
