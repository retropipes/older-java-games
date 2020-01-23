/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractTeleport;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class OneShotControllableTeleport extends AbstractTeleport {
    // Constructors
    public OneShotControllableTeleport() {
        super(0, 0, 0, true,
                ObjectImageConstants.OBJECT_IMAGE_ONE_SHOT_CONTROLLABLE);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
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
        FantastleX.getApplication().showMessage(this.getName());
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
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