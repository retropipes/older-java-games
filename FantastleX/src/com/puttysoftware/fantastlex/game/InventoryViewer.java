package com.puttysoftware.fantastlex.game;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.creatures.party.PartyManager;
import com.puttysoftware.fantastlex.creatures.party.PartyMember;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;

public final class InventoryViewer {
    private InventoryViewer() {
        // Do nothing
    }

    public static void showEquipmentDialog() {
        String title;
        if (FantastleX.inDebugMode()) {
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
        if (FantastleX.inDebugMode()) {
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

    public static void showObjectInventoryDialog(final MazeObjectInventory oi) {
        final String[] invString = oi.generateInventoryStringArray();
        CommonDialogs.showInputDialog("Objects", "Objects", invString,
                invString[0]);
    }
}
