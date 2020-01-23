/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractRandomInvisibleTeleport;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class RandomInvisibleTeleport extends AbstractRandomInvisibleTeleport {
    // Constructors
    public RandomInvisibleTeleport() {
        super(0, 0, ObjectImageConstants.OBJECT_IMAGE_RANDOM);
    }

    public RandomInvisibleTeleport(final int newRandomRangeY,
            final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX,
                ObjectImageConstants.OBJECT_IMAGE_RANDOM);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Random Invisible Teleport";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Random Invisible Teleports";
    }

    @Override
    public String getDescription() {
        return "Random Invisible Teleports are both random and invisible.";
    }
}