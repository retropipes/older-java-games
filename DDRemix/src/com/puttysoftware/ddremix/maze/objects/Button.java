/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.Application;
import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.maze.Maze;
import com.puttysoftware.ddremix.maze.abc.AbstractTrigger;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;
import com.puttysoftware.randomrange.RandomRange;

public class Button extends AbstractTrigger {
    // Constructors
    public Button() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BUTTON;
    }

    @Override
    public String getName() {
        return "Button";
    }

    @Override
    public String getPluralName() {
        return "Buttons";
    }

    @Override
    public String getDescription() {
        return "Buttons toggle Walls On and Walls Off.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        SoundManager.playSound(SoundConstants.SOUND_BUTTON);
        final Application app = DDRemix.getApplication();
        app.getMazeManager().getMaze().fullScanButton(this.getLayer());
        app.getGameManager().redrawMaze();
    }

    @Override
    public boolean shouldGenerateObject(final Maze maze, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Generate Buttons at 50% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 50;
    }
}