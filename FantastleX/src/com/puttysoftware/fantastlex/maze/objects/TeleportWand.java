/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractWand;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class TeleportWand extends AbstractWand {
    public TeleportWand() {
        super(ColorConstants.COLOR_PURPLE);
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
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public void useAction(final AbstractMazeObject mo, final int x, final int y,
            final int z) {
        final Application app = FantastleX.getApplication();
        app.getGameManager().updatePositionAbsolute(x, y, z);
    }

    @Override
    public String getDescription() {
        return "Teleport Wands will teleport you to the target square when used. You cannot teleport to areas you cannot see.";
    }
}
