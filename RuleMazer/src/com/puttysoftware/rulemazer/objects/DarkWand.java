/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.generic.GenericWand;
import com.puttysoftware.rulemazer.generic.MazeObject;
import com.puttysoftware.rulemazer.maze.Maze;
import com.puttysoftware.rulemazer.maze.MazeConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class DarkWand extends GenericWand {
    // Constructors
    public DarkWand() {
        super();
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
        final Maze m = Main.getApplication().getMazeManager().getMaze();
        final MazeObject obj = m.getCell(x, y, z, MazeConstants.LAYER_OBJECT);
        if (obj.getName().equals("Empty")) {
            // Create a Dark Gem
            this.useAction(new DarkGem(), x, y, z);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_DARKNESS);
        } else if (obj.getName().equals("Light Gem")) {
            // Destroy the Light Gem
            this.useAction(new Empty(), x, y, z);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_SHATTER);
        }
    }

    @Override
    public String getDescription() {
        return "Dark Wands have 2 uses. When aimed at an empty space, they create a Dark Gem. When aimed at a Light Gem, it is destroyed.";
    }
}
