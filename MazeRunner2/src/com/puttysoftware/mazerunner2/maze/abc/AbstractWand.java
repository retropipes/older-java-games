/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public abstract class AbstractWand extends AbstractUsableObject {
    // Fields
    private static final long SCORE_USE = 5L;

    // Constructors
    protected AbstractWand(int tc) {
        super(1);
        this.setTemplateColor(tc);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_WAND;
    }

    @Override
    public abstract String getName();

    @Override
    public void useAction(final AbstractMazeObject mo, final int x,
            final int y, final int z) {
        Application app = MazeRunnerII.getApplication();
        app.getGameManager().morph(mo, x, y, z);
        MazeRunnerII.getApplication().getGameManager()
                .addToScore(AbstractWand.SCORE_USE);
    }

    @Override
    public abstract void useHelper(int x, int y, int z);

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WAND);
        this.type.set(TypeConstants.TYPE_USABLE);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }
}
