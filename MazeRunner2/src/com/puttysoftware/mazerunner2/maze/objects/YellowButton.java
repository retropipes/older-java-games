/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.maze.abc.AbstractButton;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class YellowButton extends AbstractButton {
    public YellowButton() {
        super(new YellowWallOff(), new YellowWallOn(),
                ColorConstants.COLOR_YELLOW);
    }

    @Override
    public String getName() {
        return "Yellow Button";
    }

    @Override
    public String getPluralName() {
        return "Yellow Buttons";
    }

    @Override
    public String getDescription() {
        return "Yellow Buttons will cause all Yellow Walls Off to become On, and all Yellow Walls On to become Off.";
    }
}
