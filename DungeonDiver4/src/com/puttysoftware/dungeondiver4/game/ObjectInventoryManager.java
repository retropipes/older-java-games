/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.game;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBow;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.objects.AnnihilationWand;
import com.puttysoftware.dungeondiver4.dungeon.objects.Bow;
import com.puttysoftware.dungeondiver4.dungeon.objects.DarkGem;
import com.puttysoftware.dungeondiver4.dungeon.objects.DarkWand;
import com.puttysoftware.dungeondiver4.dungeon.objects.DisarmTrapWand;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.objects.LightGem;
import com.puttysoftware.dungeondiver4.dungeon.objects.LightWand;
import com.puttysoftware.dungeondiver4.dungeon.objects.Player;
import com.puttysoftware.dungeondiver4.dungeon.objects.TeleportWand;
import com.puttysoftware.dungeondiver4.dungeon.objects.WallBreakingWand;
import com.puttysoftware.dungeondiver4.dungeon.objects.WallMakingWand;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ArrowTypeConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectList;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class ObjectInventoryManager {
    // Fields
    private DungeonObjectInventory objectInv, savedObjectInv;
    private int lastUsedBowIndex;
    private AbstractBow activeBow;
    private AbstractDungeonObject objectBeingUsed;
    private int lastUsedObjectIndex;
    private boolean using;
    private int activeArrowType;
    private boolean arrowActive;

    // Constructor
    ObjectInventoryManager() {
        this.objectInv = new DungeonObjectInventory();
        this.using = false;
        this.activeBow = new Bow();
        this.activeArrowType = ArrowTypeConstants.ARROW_TYPE_PLAIN;
        this.arrowActive = false;
    }

    // Methods
    boolean usingAnItem() {
        return this.using;
    }

    void setUsingAnItem(final boolean isUsing) {
        this.using = isUsing;
    }

    boolean isArrowActive() {
        return this.arrowActive;
    }

    void resetObjectInventory() {
        this.objectInv = new DungeonObjectInventory();
    }

    void saveObjectInventory() {
        this.savedObjectInv = this.objectInv.clone();
    }

    void restoreObjectInventory() {
        if (this.savedObjectInv != null) {
            this.objectInv = this.savedObjectInv.clone();
        } else {
            this.objectInv = new DungeonObjectInventory();
        }
    }

    void fireStepActions() {
        this.objectInv.fireStepActions();
    }

    DungeonObjectInventory getObjectInventory() {
        return this.objectInv;
    }

    void fireArrow(final int x, final int y) {
        if (this.getObjectInventory().getUses(this.activeBow) == 0) {
            DungeonDiver4.getApplication().showMessage("You're out of arrows!");
        } else {
            final GameArrowTask at = new GameArrowTask(x, y,
                    this.activeArrowType);
            this.arrowActive = true;
            at.start();
        }
    }

    void arrowDone() {
        this.arrowActive = false;
        this.objectInv.useBow(this.activeBow);
    }

    void showUseDialog() {
        int x;
        final DungeonObjectList list = DungeonDiver4.getApplication()
                .getObjects();
        final AbstractDungeonObject[] choices = list.getAllUsableObjects();
        final String[] userChoices = this.objectInv.generateUseStringArray();
        final String result = CommonDialogs.showInputDialog("Use which object?",
                "DungeonDiver4", userChoices,
                userChoices[this.lastUsedObjectIndex]);
        try {
            for (x = 0; x < choices.length; x++) {
                if (result.equals(userChoices[x])) {
                    this.lastUsedObjectIndex = x;
                    this.objectBeingUsed = choices[x];
                    if (this.objectInv.getUses(this.objectBeingUsed) == 0) {
                        DungeonDiver4.getApplication().showMessage(
                                "That item has no more uses left.");
                        this.setUsingAnItem(false);
                    } else {
                        DungeonDiver4.getApplication()
                                .showMessage("Click to set target");
                        this.setUsingAnItem(true);
                    }
                    return;
                }
            }
        } catch (final NullPointerException np) {
            this.setUsingAnItem(false);
        }
    }

    void showSwitchBowDialog() {
        int x;
        final DungeonObjectList list = DungeonDiver4.getApplication()
                .getObjects();
        final AbstractDungeonObject[] choices = list.getAllBows();
        final String[] userChoices = this.objectInv.generateBowStringArray();
        final String result = CommonDialogs.showInputDialog(
                "Switch to which bow?", "DungeonDiver4", userChoices,
                userChoices[this.lastUsedBowIndex]);
        try {
            for (x = 0; x < choices.length; x++) {
                if (result.equals(userChoices[x])) {
                    this.lastUsedBowIndex = x;
                    this.activeBow = (AbstractBow) choices[x];
                    this.activeArrowType = this.activeBow.getArrowType();
                    if (this.objectInv.getUses(this.activeBow) == 0) {
                        DungeonDiver4.getApplication()
                                .showMessage("That bow is out of arrows!");
                    } else {
                        DungeonDiver4.getApplication().showMessage(
                                this.activeBow.getName() + " activated.");
                    }
                    return;
                }
            }
        } catch (final NullPointerException np) {
            // Do nothing
        }
    }

    void useItemHandler(final int x, final int y) {
        final Application app = DungeonDiver4.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        final int z = m.getPlayerLocationZ();
        if (this.usingAnItem() && app.getMode() == Application.STATUS_GAME) {
            final boolean visible = m.isSquareVisible(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), x, y);
            try {
                final AbstractDungeonObject target = m.getCell(x, y, z,
                        DungeonConstants.LAYER_OBJECT);
                final String name = this.objectBeingUsed.getName();
                if ((target.isSolid() || !visible)
                        && name.equals(new TeleportWand().getName())) {
                    this.setUsingAnItem(false);
                    DungeonDiver4.getApplication()
                            .showMessage("Can't teleport there");
                }
                if (target.getName().equals(new Player().getName())) {
                    this.setUsingAnItem(false);
                    DungeonDiver4.getApplication()
                            .showMessage("Don't aim at yourself!");
                }
                if (!target.isDestroyable()
                        && name.equals(new AnnihilationWand().getName())) {
                    this.setUsingAnItem(false);
                    DungeonDiver4.getApplication()
                            .showMessage("Can't destroy that");
                }
                if (!target.isDestroyable()
                        && name.equals(new WallMakingWand().getName())) {
                    this.setUsingAnItem(false);
                    DungeonDiver4.getApplication()
                            .showMessage("Can't create a wall there");
                }
                if ((!target.isDestroyable()
                        || !target.isOfType(TypeConstants.TYPE_WALL))
                        && name.equals(new WallBreakingWand().getName())) {
                    this.setUsingAnItem(false);
                    DungeonDiver4.getApplication().showMessage("Aim at a wall");
                }
                if ((!target.isDestroyable()
                        || !target.isOfType(TypeConstants.TYPE_TRAP))
                        && name.equals(new DisarmTrapWand().getName())) {
                    this.setUsingAnItem(false);
                    DungeonDiver4.getApplication().showMessage("Aim at a trap");
                }
                if (!target.getName().equals(new Empty().getName())
                        && !target.getName().equals(new DarkGem().getName())
                        && name.equals(new LightWand().getName())) {
                    this.setUsingAnItem(false);
                    DungeonDiver4.getApplication().showMessage(
                            "Aim at either an empty space or a Dark Gem");
                }
                if (!target.getName().equals(new Empty().getName())
                        && !target.getName().equals(new LightGem().getName())
                        && name.equals(new DarkWand().getName())) {
                    this.setUsingAnItem(false);
                    DungeonDiver4.getApplication().showMessage(
                            "Aim at either an empty space or a Light Gem");
                }
            } catch (final ArrayIndexOutOfBoundsException ae) {
                this.setUsingAnItem(false);
                DungeonDiver4.getApplication()
                        .showMessage("Aim within the dungeon");
            } catch (final NullPointerException np) {
                this.setUsingAnItem(false);
            }
        }
        if (this.usingAnItem()) {
            this.getObjectInventory().use(this.objectBeingUsed, x, y, z);
        }
    }

    void readObjectInventory(final XDataReader dungeonFile,
            final int formatVersion) throws IOException {
        this.objectInv = DungeonObjectInventory.readInventory(dungeonFile,
                formatVersion);
        this.savedObjectInv = DungeonObjectInventory.readInventory(dungeonFile,
                formatVersion);
    }

    void writeObjectInventory(final XDataWriter dungeonFile)
            throws IOException {
        this.objectInv.writeInventory(dungeonFile);
        this.savedObjectInv.writeInventory(dungeonFile);
    }
}
