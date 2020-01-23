/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericWand;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public class DisarmTrapWand extends GenericWand {
    // Constructors
    public DisarmTrapWand() {
        super();
    }

    @Override
    public String getName() {
        return "Disarm Trap Wand";
    }

    @Override
    public String getPluralName() {
        return "Disarm Trap Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(new Empty(), x, y, z);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_DESTROY);
    }

    @Override
    public String getDescription() {
        return "Disarm Trap Wands will make one trap disappear when used, if aimed at a trap.";
    }
}
