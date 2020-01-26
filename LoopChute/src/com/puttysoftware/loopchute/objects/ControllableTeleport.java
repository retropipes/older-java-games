/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.generic.GenericTeleport;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class ControllableTeleport extends GenericTeleport {
    // Constructors
    public ControllableTeleport() {
        super(0, 0, 0, false, "controllable");
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = LoopChute.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK);
        app.getGameManager().controllableTeleport();
    }

    @Override
    public String getName() {
        return "Controllable Teleport";
    }

    @Override
    public String getPluralName() {
        return "Controllable Teleports";
    }

    @Override
    public void editorProbeHook() {
        LoopChute.getApplication().showMessage(this.getName());
    }

    @Override
    public MazeObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Controllable Teleports let you choose the place you teleport to.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }
}