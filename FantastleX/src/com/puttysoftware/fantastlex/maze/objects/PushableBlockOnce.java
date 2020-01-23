/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractMovableObject;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class PushableBlockOnce extends AbstractMovableObject {
    // Constructors
    public PushableBlockOnce() {
        super(true, false, ObjectImageConstants.OBJECT_IMAGE_ONCE);
        this.setTemplateColor(ColorConstants.COLOR_BLOCK);
    }

    @Override
    public String getName() {
        return "Pushable Block Once";
    }

    @Override
    public String getPluralName() {
        return "Pushable Blocks Once";
    }

    @Override
    public void pushAction(final MazeObjectInventory inv,
            final AbstractMazeObject mo, final int x, final int y,
            final int pushX, final int pushY) {
        final Application app = FantastleX.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        SoundManager.playSound(SoundConstants.SOUND_PUSH_PULL);
        app.getGameManager().morphOther(new Wall(), pushX, pushY,
                MazeConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Pushable Blocks Once can only be pushed once, before turning into a wall.";
    }
}