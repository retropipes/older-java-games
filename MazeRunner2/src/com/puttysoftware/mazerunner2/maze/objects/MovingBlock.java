package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMovingObject;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.randomrange.RandomRange;

public class MovingBlock extends AbstractMovingObject {
    // Constructors
    public MovingBlock() {
        super(true, ColorConstants.COLOR_GRASS,
                ObjectImageConstants.OBJECT_IMAGE_PULLABLE,
                ColorConstants.COLOR_WHITE);
        RandomRange t = new RandomRange(1, 2);
        this.activateTimer(t.generate());
    }

    @Override
    public void timerExpiredAction(int dirX, int dirY) {
        // Move the block
        RandomRange r = new RandomRange(0, 7);
        int move = r.generate();
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .updateMovingBlockPosition(move, dirX, dirY, this);
        RandomRange t = new RandomRange(1, 2);
        this.activateTimer(t.generate());
    }

    @Override
    public String getName() {
        return "Moving Block";
    }

    @Override
    public String getPluralName() {
        return "Moving Blocks";
    }

    @Override
    public String getDescription() {
        return "Moving Blocks move on their own. They cannot be pushed or pulled.";
    }
}
