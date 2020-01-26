/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.GenericTeleport;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class OneShotControllableTeleport extends GenericTeleport {
    // Constructors
    public OneShotControllableTeleport() {
        super(0, 0, 0, true,
                ObjectImageConstants.OBJECT_IMAGE_ONE_SHOT_CONTROLLABLE);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = MasterMaze.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_WALK);
        app.getGameManager().controllableTeleport();
        app.getGameManager().decay();
    }

    @Override
    public String getName() {
        return "One-Shot Controllable Teleport";
    }

    @Override
    public String getPluralName() {
        return "One-Shot Controllable Teleports";
    }

    @Override
    public void editorProbeHook() {
        MasterMaze.getApplication().showMessage(this.getName());
    }

    @Override
    public MazeObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "One-Shot Controllable Teleports let you choose the place you teleport to, then disappear.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }
}