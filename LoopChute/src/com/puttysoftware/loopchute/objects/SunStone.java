/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericCheckKey;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class SunStone extends GenericCheckKey {
    // Constructors
    public SunStone() {
        super(ColorConstants.COLOR_SUN_DOOR);
    }

    @Override
    public String getName() {
        return "Sun Stone";
    }

    @Override
    public String getPluralName() {
        return "Sun Stones";
    }

    @Override
    public String getDescription() {
        return "Sun Stones act as a trigger for other actions when collected.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        inv.addItem(this);
        final Application app = LoopChute.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_SUN_STONE);
    }
}