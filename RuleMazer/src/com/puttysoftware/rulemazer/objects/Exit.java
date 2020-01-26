/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class Exit extends FinishTo {
    // Constructors
    public Exit() {
        super();
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = Main.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_DOWN);
        app.getGameManager().goToLevel(this.getDestinationLevel());
    }

    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public String getPluralName() {
        return "Exits";
    }

    @Override
    public String getDescription() {
        return "Exits send you outside when walked on.";
    }
}