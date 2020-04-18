/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericContainer;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class TreasureChest extends GenericContainer {
    // Constructors
    public TreasureChest() {
        super(new Key());
    }

    public TreasureChest(final MazeObjectModel inside) {
        super(new Key(), inside);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            Mazer5D.getBagOStuff().showMessage("You need a key");
        }
        SoundPlayer.playSound(SoundIndex.WALK_FAILED, SoundGroup.GAME);
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
    public MazeObjectModel editorPropertiesHook() {
        return Mazer5D.getBagOStuff().getEditor().editTreasureChestContents();
    }

    @Override
    public String getDescription() {
        return "Treasure Chests require Keys to open, and contain 1 other item.";
    }
}