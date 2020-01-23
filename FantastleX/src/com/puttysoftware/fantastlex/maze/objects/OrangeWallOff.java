/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractToggleWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class OrangeWallOff extends AbstractToggleWall {
    // Constructors
    public OrangeWallOff() {
        super(false, ColorConstants.COLOR_ORANGE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Orange Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Orange Walls Off";
    }

    @Override
    public String getDescription() {
        return "Orange Walls Off can be walked through, and will change to Orange Walls On when a Orange Button is pressed.";
    }
}