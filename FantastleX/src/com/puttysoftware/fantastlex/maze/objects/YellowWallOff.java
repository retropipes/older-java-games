/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractToggleWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class YellowWallOff extends AbstractToggleWall {
    // Constructors
    public YellowWallOff() {
        super(false, ColorConstants.COLOR_YELLOW);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Yellow Walls Off";
    }

    @Override
    public String getDescription() {
        return "Yellow Walls Off can be walked through, and will change to Yellow Walls On when a Yellow Button is pressed.";
    }
}