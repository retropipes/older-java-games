/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericMovableObject;
import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.maze.MazeConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class PushableBlockThrice extends GenericMovableObject {
    // Constructors
    public PushableBlockThrice() {
        super(true, false);
    }

    @Override
    public String getName() {
        return "Pushable Block Thrice";
    }

    @Override
    public String getPluralName() {
        return "Pushable Blocks Thrice";
    }

    @Override
    public void pushAction(final ObjectInventory inv, final MazeObject mo,
            final int x, final int y, final int pushX, final int pushY) {
        final Application app = WidgetWarren.getApplication();
        app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_PUSH);
        app.getGameManager().morphOther(new PushableBlockTwice(), pushX, pushY,
                MazeConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Pushable Blocks Thrice can only be pushed three times, before turning into a wall.";
    }
}