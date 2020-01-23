/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractLightModifier;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class LightGem extends AbstractLightModifier {
    // Constructors
    public LightGem() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public String getName() {
        return "Light Gem";
    }

    @Override
    public String getPluralName() {
        return "Light Gems";
    }

    @Override
    public String getDescription() {
        return "Light Gems bathe the immediately adjacent area in permanent light.";
    }
}
