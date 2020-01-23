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

public class WhiteLock extends AbstractSingleLock {
    // Constructors
    public WhiteLock() {
        super(new WhiteKey(), ColorConstants.COLOR_WHITE);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        if (this.isConditionallySolid(inv)) {
            FantastleX.getApplication().showMessage("You need a white key");
        }
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public String getName() {
        return "White Lock";
    }

    @Override
    public String getPluralName() {
        return "White Locks";
    }

    @Override
    public String getDescription() {
        return "White Locks require White Keys to open.";
    }
}