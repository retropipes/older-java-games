/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.Maze;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.abc.AbstractWand;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class DarkWand extends AbstractWand {
    // Constructors
    public DarkWand() {
        super(ColorConstants.COLOR_BLACK);
    }

    @Override
    public String getName() {
        return "Dark Wand";
    }

    @Override
    public String getPluralName() {
        return "Dark Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        final Maze m = MazeRunnerII.getApplication().getMazeManager().getMaze();
        final AbstractMazeObject obj = m.getCell(x, y, z,
                MazeConstants.LAYER_OBJECT);
        if (obj.getName().equals("Empty")) {
            // Create a Dark Gem
            this.useAction(new DarkGem(), x, y, z);
            SoundManager.playSound(SoundConstants.SOUND_DARKNESS);
        } else if (obj.getName().equals("Light Gem")) {
            // Destroy the Light Gem
            this.useAction(new Empty(), x, y, z);
            SoundManager.playSound(SoundConstants.SOUND_SHATTER);
        }
    }

    @Override
    public String getDescription() {
        return "Dark Wands have 2 uses. When aimed at an empty space, they create a Dark Gem. When aimed at a Light Gem, it is destroyed.";
    }
}
