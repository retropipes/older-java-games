/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.generic;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;

public abstract class GenericWand extends GenericUsableObject {
    // Fields
    private static final long SCORE_USE = 5L;

    // Constructors
    protected GenericWand() {
        super(1);
    }

    @Override
    public abstract String getName();

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        final Application app = WidgetWarren.getApplication();
        app.getGameManager().morph(mo, x, y, z);
        WidgetWarren.getApplication().getGameManager()
                .addToScore(GenericWand.SCORE_USE);
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
