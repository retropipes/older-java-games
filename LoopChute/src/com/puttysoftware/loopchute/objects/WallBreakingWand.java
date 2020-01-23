/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericWand;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class WallBreakingWand extends GenericWand {
    // Constructors
    public WallBreakingWand() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_BLUE);
    }

    @Override
    public String getName() {
        return "Wall-Breaking Wand";
    }

    @Override
    public String getPluralName() {
        return "Wall-Breaking Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(new Empty(), x, y, z);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_DESTROY);
    }

    @Override
    public String getDescription() {
        return "Wall-Breaking Wands will destroy one wall when used, if aimed at a wall.";
    }
}
