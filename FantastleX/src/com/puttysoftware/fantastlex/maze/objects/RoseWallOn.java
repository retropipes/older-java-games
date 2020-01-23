/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractToggleWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class RoseWallOn extends AbstractToggleWall {
    // Constructors
    public RoseWallOn() {
        super(true, ColorConstants.COLOR_ROSE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Wall On";
    }

    @Override
    public String getPluralName() {
        return "Rose Walls On";
    }

    @Override
    public String getDescription() {
        return "Rose Walls On can NOT be walked through, and will change to Rose Walls Off when a Rose Button is pressed.";
    }
}