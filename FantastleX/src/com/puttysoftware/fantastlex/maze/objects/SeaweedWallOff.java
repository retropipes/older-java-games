/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractToggleWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class SeaweedWallOff extends AbstractToggleWall {
    // Constructors
    public SeaweedWallOff() {
        super(false, ColorConstants.COLOR_SEAWEED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Walls Off";
    }

    @Override
    public String getDescription() {
        return "Seaweed Walls Off can be walked through, and will change to Seaweed Walls On when a Seaweed Button is pressed.";
    }
}