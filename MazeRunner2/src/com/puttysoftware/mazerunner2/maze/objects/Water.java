/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractField;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class Water extends AbstractField {
    // Constructors
    public Water() {
        super(new AquaBoots(), true, ColorConstants.COLOR_WATER);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK_WATER);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        MazeRunnerII.getApplication().showMessage("You'll drown");
        SoundManager.playSound(SoundConstants.SOUND_WATER);
    }

    @Override
    public void pushIntoAction(final MazeObjectInventory inv,
            final AbstractMazeObject pushed, final int x, final int y,
            final int z) {
        final Application app = MazeRunnerII.getApplication();
        if (pushed.isPushable()) {
            app.getGameManager().morph(new SunkenBlock(), x, y, z,
                    MazeConstants.LAYER_GROUND);
            app.getGameManager().morph(new Empty(), x, y, z,
                    MazeConstants.LAYER_OBJECT);
            SoundManager.playSound(SoundConstants.SOUND_SINK_BLOCK);
        }
    }

    @Override
    public String getName() {
        return "Water";
    }

    @Override
    public String getPluralName() {
        return "Squares of Water";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Water is too unstable to walk on without Aqua Boots.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}
