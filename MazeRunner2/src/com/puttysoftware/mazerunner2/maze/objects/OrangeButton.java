/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractButton;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class OrangeButton extends AbstractButton {
    public OrangeButton() {
        super(new OrangeWallOff(), new OrangeWallOn(),
                ColorConstants.COLOR_ORANGE);
    }

    @Override
    public String getName() {
        return "Orange Button";
    }

    @Override
    public String getPluralName() {
        return "Orange Buttons";
    }

    @Override
    public String getDescription() {
        return "Orange Buttons will cause all Orange Walls Off to become On, and all Orange Walls On to become Off.";
    }
}
