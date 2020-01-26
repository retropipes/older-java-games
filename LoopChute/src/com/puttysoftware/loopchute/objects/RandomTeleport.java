/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.generic.GenericRandomTeleport;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class RandomTeleport extends GenericRandomTeleport {
    // Constructors
    public RandomTeleport() {
        super(0, 0, "random");
    }

    public RandomTeleport(final int newRandomRangeY,
            final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX, "random");
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = LoopChute.getApplication();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
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