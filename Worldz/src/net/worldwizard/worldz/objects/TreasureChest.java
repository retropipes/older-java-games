/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericContainer;
import net.worldwizard.worldz.generic.WorldObject;

public class TreasureChest extends GenericContainer {
    // Constructors
    public TreasureChest() {
        super(new Key());
    }

    public TreasureChest(final WorldObject inside) {
        super(new Key(), inside);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Messager.showMessage("You need a key");
        }
        // Play move failed sound, if it's enabled
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
        }
    }

    @Override
    public String getName() {
        return "Treasure Chest";
    }

    @Override
    public String getPluralName() {
        return "Treasure Chests";
    }

    @Override
    public WorldObject editorPropertiesHook() {
        return Worldz.getApplication().getEditor().editTreasureChestContents();
    }

    @Override
    public String getDescription() {
        return "Treasure Chests require Keys to open, and contain 1 other item.";
    }
}