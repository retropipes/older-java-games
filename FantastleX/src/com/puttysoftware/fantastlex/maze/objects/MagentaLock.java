/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractSingleLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class MagentaLock extends AbstractSingleLock {
    // Constructors
    public MagentaLock() {
        super(new MagentaKey(), ColorConstants.COLOR_MAGENTA);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        if (this.isConditionallySolid(inv)) {
            FantastleX.getApplication().showMessage("You need a magenta key");
        }
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public String getName() {
        return "Magenta Lock";
    }

    @Override
    public String getPluralName() {
        return "Magenta Locks";
    }

    @Override
    public String getDescription() {
        return "Magenta Locks require Magenta Keys to open.";
    }
}