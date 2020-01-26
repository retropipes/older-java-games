package com.puttysoftware.dungeondiver4.game;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.creatures.party.PartyMember;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;

public final class InventoryViewer {
    private InventoryViewer() {
        // Do nothing
    }

    public static void showEquipmentDialog() {
        String title;
        if (DungeonDiver4.inDebugMode()) {
            title = "Equipment (DEBUG)";
        } else {
            title = "Equipment";
        }
        final PartyMember member = PartyManager.getParty().pickOnePartyMember();
        if (member != null) {
            final String[] equipString = member.getItems()
                    .generateEquipmentStringArray();
            CommonDialogs.showInputDialog("Equipment", title, equipString,
                    equipString[0]);
        }
    }

    public static void showItemInventoryDialog() {
        String title;
        if (DungeonDiver4.inDebugMode()) {
            title = "Items (DEBUG)";
        } else {
            title = "Items";
        }
        final PartyMember member = PartyManager.getParty().pickOnePartyMember();
        if (member != null) {
            final String[] invString = member.getItems()
                    .generateInventoryStringArray();
            CommonDialogs.showInputDialog("Items", title, invString,
                    invString[0]);
        }
    }

    public static void showObjectInventoryDialog(
            final DungeonObjectInventory oi) {
        final String[] invString = oi.generateInventoryStringArray();
        CommonDialogs.showInputDialog("Objects", "Objects", invString,
                invString[0]);
    }
}
