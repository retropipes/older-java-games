/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractContainer;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class TreasureChest extends AbstractContainer {
    // Constructors
    public TreasureChest() {
        super(new TreasureKey());
        this.setTemplateColor(ColorConstants.COLOR_BRIDGE);
    }

    public TreasureChest(AbstractMazeObject inside) {
        super(new TreasureKey(), inside);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TREASURE_CHEST;
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            MazeRunnerII.getApplication()
                    .showMessage("You need a treasure key");
        }
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
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
    public AbstractMazeObject editorPropertiesHook() {
        return MazeRunnerII.getApplication().getEditor()
                .editTreasureChestContents();
    }

    @Override
    public String getDescription() {
        return "Treasure Chests require Treasure Keys to open, and contain 1 other item.";
    }
}