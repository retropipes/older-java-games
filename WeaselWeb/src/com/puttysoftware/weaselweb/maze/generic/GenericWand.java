/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;

public abstract class GenericWand extends GenericUsableObject {
    // Fields
    private static final long SCORE_USE = 5L;

    // Constructors
    protected GenericWand() {
        super(1);
        this.setType(TypeConstants.TYPE_WAND);
    }

    @Override
    public abstract String getName();

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        final Application app = WeaselWeb.getApplication();
        app.getGameManager().morph(mo, x, y, z);
        WeaselWeb.getApplication().getGameManager()
                .addToScore(GenericWand.SCORE_USE);
    }

    @Override
    public abstract void useHelper(int x, int y, int z);
}
