/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractCheckKey;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class SunStone extends AbstractCheckKey {
    // Constructors
    public SunStone() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_SUN_DOOR);
    }

    @Override
    public String getName() {
        return "Sun Stone";
    }

    @Override
    public String getPluralName() {
        return "Sun Stones";
    }

    @Override
    public String getDescription() {
        return "Sun Stones act as a trigger for other actions when collected.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        inv.addItem(this);
        final Application app = FantastleX.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_SUN_STONE);
    }
}