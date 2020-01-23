/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractToggleWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class SeaweedWallOn extends AbstractToggleWall {
    // Constructors
    public SeaweedWallOn() {
        super(true, ColorConstants.COLOR_SEAWEED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Wall On";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Walls On";
    }

    @Override
    public String getDescription() {
        return "Seaweed Walls On can NOT be walked through, and will change to Seaweed Walls Off when a Seaweed Button is pressed.";
    }
}