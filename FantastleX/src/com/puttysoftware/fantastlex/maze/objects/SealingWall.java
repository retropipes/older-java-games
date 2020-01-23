/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class SealingWall extends AbstractWall {
    // Constructors
    public SealingWall() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SEALING_WALL;
    }

    @Override
    public String getName() {
        return "Sealing Wall";
    }

    @Override
    public String getPluralName() {
        return "Sealing Walls";
    }

    @Override
    public String getDescription() {
        return "Sealing Walls are impassable and indestructible - you'll need to go around them.";
    }
}