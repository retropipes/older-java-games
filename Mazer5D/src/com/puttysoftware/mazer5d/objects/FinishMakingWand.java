/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.generic.GenericWand;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;

public class FinishMakingWand extends GenericWand {
    public FinishMakingWand() {
        super();
    }

    @Override
    public String getName() {
        return "Finish-Making Wand";
    }

    @Override
    public String getPluralName() {
        return "Finish-Making Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(new Finish(), x, y, z);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CREATE);
    }

    @Override
    public String getDescription() {
        return "Finish-Making Wands will create a finish when used.";
    }
}
