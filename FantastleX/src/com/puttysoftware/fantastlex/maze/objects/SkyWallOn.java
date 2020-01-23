/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractToggleWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class SkyWallOn extends AbstractToggleWall {
    // Constructors
    public SkyWallOn() {
        super(true, ColorConstants.COLOR_SKY);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Wall On";
    }

    @Override
    public String getPluralName() {
        return "Sky Walls On";
    }

    @Override
    public String getDescription() {
        return "Sky Walls On can NOT be walked through, and will change to Sky Walls Off when a Sky Button is pressed.";
    }
}