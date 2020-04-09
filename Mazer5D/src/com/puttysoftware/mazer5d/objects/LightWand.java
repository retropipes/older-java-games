/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.generic.GenericWand;
import com.puttysoftware.mazer5d.generic.MazeObject;
import com.puttysoftware.mazer5d.maze.Maze;
import com.puttysoftware.mazer5d.maze.MazeConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;

public class LightWand extends GenericWand {
    // Constructors
    public LightWand() {
        super();
    }

    @Override
    public String getName() {
        return "Light Wand";
    }

    @Override
    public String getPluralName() {
        return "Light Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        final Maze m = Mazer5D.getApplication().getMazeManager().getMaze();
        final MazeObject obj = m.getCell(x, y, z, MazeConstants.LAYER_OBJECT);
        if (obj.getName().equals("Empty")) {
            // Create a Light Gem
            this.useAction(new LightGem(), x, y, z);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_LIGHT);
        } else if (obj.getName().equals("Dark Gem")) {
            // Destroy the Dark Gem
            this.useAction(new Empty(), x, y, z);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_SHATTER);
        }
    }

    @Override
    public String getDescription() {
        return "Light Wands have 2 uses. When aimed at an empty space, they create a Light Gem. When aimed at a Dark Gem, it is destroyed.";
    }
}
