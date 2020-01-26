/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractRandomTeleport;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class RandomTeleport extends AbstractRandomTeleport {
    // Constructors
    public RandomTeleport() {
        super(0, 0, ObjectImageConstants.OBJECT_IMAGE_RANDOM);
    }

    public RandomTeleport(final int newRandomRangeY,
            final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX,
                ObjectImageConstants.OBJECT_IMAGE_RANDOM);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc, 0);
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getName() {
        return "Random Teleport";
    }

    @Override
    public String getPluralName() {
        return "Random Teleports";
    }

    @Override
    public String getDescription() {
        return "Random Teleports, unlike regular Teleports, send you to a randomly chosen destination.";
    }
}