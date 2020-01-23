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

public abstract class AbstractInvisibleBlockTeleport extends
        AbstractBlockTeleport {
    // Constructors
    protected AbstractInvisibleBlockTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int attrName) {
        super(destinationRow, destinationColumn, destinationFloor, attrName);
        this.setTemplateColor(ColorConstants.COLOR_SAND);
        this.setAttributeTemplateColor(ColorConstants.COLOR_INVISIBLE_BLOCK_TELEPORT_ATTRIBUTE);
    }

    @Override
    public void pushIntoAction(final MazeObjectInventory inv,
            final AbstractMazeObject mo, final int x, final int y, final int z) {
        final Application app = FantastleX.getApplication();
        final AbstractMovableObject pushedInto = (AbstractMovableObject) mo;
        app.getGameManager().updatePushedIntoPositionAbsolute(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), x, y, z, pushedInto, this);
        app.getGameManager().keepNextMessage();
        app.showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public void pullIntoAction(final MazeObjectInventory inv,
            final AbstractMazeObject mo, final int x, final int y, final int z) {
        final Application app = FantastleX.getApplication();
        final AbstractMovableObject pushedInto = (AbstractMovableObject) mo;
        app.getGameManager().updatePushedIntoPositionAbsolute(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), x, y, z, pushedInto, this);
        app.getGameManager().keepNextMessage();
        app.showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_INVISIBLE_TELEPORT);
        this.type.set(TypeConstants.TYPE_TELEPORT);
    }
}
