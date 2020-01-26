/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericCheckKey;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class MoonStone extends GenericCheckKey {
    // Constructors
    public MoonStone() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_MOON_DOOR);
    }

    @Override
    public String getName() {
        return "Moon Stone";
    }

    @Override
    public String getPluralName() {
        return "Moon Stones";
    }

    @Override
    public String getDescription() {
        return "Moon Stones act as a trigger for other actions when collected.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        inv.addItem(this);
        final Application app = MasterMaze.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_SUN_STONE);
    }
}