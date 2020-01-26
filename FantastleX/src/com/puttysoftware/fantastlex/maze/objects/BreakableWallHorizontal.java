/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.Maze;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class BreakableWallHorizontal extends AbstractWall {
    // Constructors
    public BreakableWallHorizontal() {
        super(ColorConstants.COLOR_BROWN);
        this.setAttributeID(
                ObjectImageConstants.OBJECT_IMAGE_BREAKABLE_HORIZONTAL);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public String getName() {
        return "Breakable Wall Horizontal";
    }

    @Override
    public String getPluralName() {
        return "Breakable Walls Horizontal";
    }

    @Override
    public String getDescription() {
        return "Breakable Walls Horizontal break up into nothing if walked into, and propagate the effect to other like walls.";
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        final int dirZ = FantastleX.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        this.chainReactionAction(dirX, dirY, dirZ);
        SoundManager.playSound(SoundConstants.SOUND_CRACK);
    }

    @Override
    public void chainReactionAction(final int dirX, final int dirY,
            final int dirZ) {
        // Break up
        FantastleX.getApplication().getGameManager().morph(new Empty(), dirX,
                dirY, dirZ, MazeConstants.LAYER_OBJECT);
        final Maze m = FantastleX.getApplication().getMazeManager().getMaze();
        try {
            final AbstractMazeObject left = m.getCell(dirX - 1, dirY, dirZ,
                    MazeConstants.LAYER_OBJECT);
            if (left.isOfType(TypeConstants.TYPE_BREAKABLE_H)) {
                this.chainReactionAction(dirX - 1, dirY, dirZ);
            }
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            // Ignore
        }
        try {
            final AbstractMazeObject right = m.getCell(dirX + 1, dirY, dirZ,
                    MazeConstants.LAYER_OBJECT);
            if (right.isOfType(TypeConstants.TYPE_BREAKABLE_H)) {
                this.chainReactionAction(dirX + 1, dirY, dirZ);
            }
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            // Ignore
        }
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
        this.type.set(TypeConstants.TYPE_BREAKABLE_H);
    }
}