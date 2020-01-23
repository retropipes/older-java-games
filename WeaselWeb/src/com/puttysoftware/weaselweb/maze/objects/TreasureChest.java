/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericContainer;
import com.puttysoftware.weaselweb.maze.generic.MazeObject;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class TreasureChest extends GenericContainer {
    // Constructors
    public TreasureChest() {
        super(new Key());
    }

    public TreasureChest(final MazeObject inside) {
        super(new Key(), inside);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            WeaselWeb.getApplication().showMessage("You need a key");
        }
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_FAILED);
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
    public MazeObject editorPropertiesHook() {
        return WeaselWeb.getApplication().getEditor()
                .editTreasureChestContents();
    }

    @Override
    public String getDescription() {
        return "Treasure Chests require Keys to open, and contain 1 other item.";
    }
}