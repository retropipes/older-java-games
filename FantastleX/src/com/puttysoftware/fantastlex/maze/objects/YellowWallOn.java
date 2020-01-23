/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractToggleWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class YellowWallOn extends AbstractToggleWall {
    // Constructors
    public YellowWallOn() {
        super(true, ColorConstants.COLOR_YELLOW);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Wall On";
    }

    @Override
    public String getPluralName() {
        return "Yellow Walls On";
    }

    @Override
    public String getDescription() {
        return "Yellow Walls On can NOT be walked through, and will change to Yellow Walls Off when a Yellow Button is pressed.";
    }
}