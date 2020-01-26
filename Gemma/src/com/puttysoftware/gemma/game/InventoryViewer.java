package com.puttysoftware.gemma.game;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.gemma.support.creatures.PartyMember;

public final class InventoryViewer {
    private InventoryViewer() {
        // Do nothing
    }

    public static void showEquipmentDialog() {
        String title;
        if (Support.inDebugMode()) {
            title = "Equipment (DEBUG)";
        } else {
            title = "Equipment";
        }
        final PartyMember member = PartyManager.getParty().getLeader();
        if (member != null) {
            final String[] equipString = member.getItems()
                    .generateEquipmentStringArray();
            CommonDialogs.showInputDialog("Equipment", title, equipString,
                    equipString[0]);
        }
    }

    public static void showInventoryDialog() {
        String title;
        if (Support.inDebugMode()) {
            title = "Items (DEBUG)";
        } else {
            title = "Items";
        }
        final PartyMember member = PartyManager.getParty().getLeader();
        if (member != null) {
            final String[] invString = member.getItems()
                    .generateInventoryStringArray();
            CommonDialogs.showInputDialog("Items", title, invString,
                    invString[0]);
        }
    }
}
