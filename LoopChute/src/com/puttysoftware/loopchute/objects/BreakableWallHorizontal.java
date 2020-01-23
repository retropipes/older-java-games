/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericWall;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.generic.TypeConstants;
import com.puttysoftware.loopchute.maze.Maze;
import com.puttysoftware.loopchute.maze.MazeConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class BreakableWallHorizontal extends GenericWall {
    // Constructors
    public BreakableWallHorizontal() {
        super(ColorConstants.COLOR_BROWN);
        this.setAttributeName("breakable_horizontal");
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
            final int dirY, final ObjectInventory inv) {
        final int dirZ = LoopChute.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        this.chainReactionAction(dirX, dirY, dirZ);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CRACK);
    }

    @Override
    public void chainReactionAction(final int dirX, final int dirY,
            final int dirZ) {
        // Break up
        LoopChute
                .getApplication()
                .getGameManager()
                .morph(new Empty(), dirX, dirY, dirZ,
                        MazeConstants.LAYER_OBJECT);
        final Maze m = LoopChute.getApplication().getMazeManager().getMaze();
        try {
            final MazeObject left = m.getCell(dirX - 1, dirY, dirZ,
                    MazeConstants.LAYER_OBJECT);
            if (left.isOfType(TypeConstants.TYPE_BREAKABLE_H)) {
                this.chainReactionAction(dirX - 1, dirY, dirZ);
            }
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            // Ignore
        }
        try {
            final MazeObject right = m.getCell(dirX + 1, dirY, dirZ,
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