package com.puttysoftware.mastermaze.game;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.creatures.PartyManager;
import com.puttysoftware.mastermaze.creatures.PartyMember;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;

public final class InventoryViewer {
    private InventoryViewer() {
        // Do nothing
    }

    public static void showEquipmentDialog() {
        String title;
        if (MasterMaze.inDebugMode()) {
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
        if (MasterMaze.inDebugMode()) {
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

    public static void showObjectInventoryDialog(final ObjectInventory oi) {
        final String[] invString = oi.generateInventoryStringArray();
        CommonDialogs.showInputDialog("Objects", "Objects", invString,
                invString[0]);
    }
}
