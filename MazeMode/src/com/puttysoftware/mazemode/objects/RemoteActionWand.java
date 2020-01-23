/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.Application;
import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.generic.GenericWand;
import com.puttysoftware.mazemode.generic.MazeObject;

public class RemoteActionWand extends GenericWand {
    // Constructors
    public RemoteActionWand() {
        super();
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
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        final Application app = MazeMode.getApplication();
        app.getGameManager().doRemoteAction(x, y, z);
    }

    @Override
    public String getDescription() {
        return "Remote Action Wands will act on the target object as if you were there, on top of it.";
    }
}
