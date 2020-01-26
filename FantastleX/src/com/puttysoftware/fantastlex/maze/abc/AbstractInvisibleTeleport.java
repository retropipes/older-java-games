/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public abstract class AbstractInvisibleTeleport extends AbstractTeleport {
    // Constructors
    protected AbstractInvisibleTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int attrName) {
        super(destinationRow, destinationColumn, destinationFloor, true,
                attrName);
        this.setTemplateColor(ColorConstants.COLOR_CYAN);
        this.setAttributeTemplateColor(
                ColorConstants.COLOR_INVISIBLE_TELEPORT_ATTRIBUTE);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        app.showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_INVISIBLE_TELEPORT);
        this.type.set(TypeConstants.TYPE_TELEPORT);
    }
}