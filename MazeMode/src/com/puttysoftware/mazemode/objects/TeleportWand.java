/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.Application;
import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.generic.GenericWand;
import com.puttysoftware.mazemode.generic.MazeObject;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public class TeleportWand extends GenericWand {
    public TeleportWand() {
        super();
    }

    @Override
    public String getName() {
        return "Teleport Wand";
    }

    @Override
    public String getPluralName() {
        return "Teleport Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        final Application app = MazeMode.getApplication();
        app.getGameManager().updatePositionAbsolute(x, y, z);
    }

    @Override
    public String getDescription() {
        return "Teleport Wands will teleport you to the target square when used. You cannot teleport to areas you cannot see.";
    }
}
