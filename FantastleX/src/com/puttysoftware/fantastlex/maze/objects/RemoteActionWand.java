/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractWand;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class RemoteActionWand extends AbstractWand {
    // Constructors
    public RemoteActionWand() {
        super(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public String getName() {
        return "Remote Action Wand";
    }

    @Override
    public String getPluralName() {
        return "Remote Action Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }

    @Override
    public void useAction(final AbstractMazeObject mo, final int x, final int y,
            final int z) {
        final Application app = FantastleX.getApplication();
        app.getGameManager().doRemoteAction(x, y, z);
    }

    @Override
    public String getDescription() {
        return "Remote Action Wands will act on the target object as if you were there, on top of it.";
    }
}
