/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.utilities.DirectionConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public abstract class AbstractWall extends AbstractMazeObject {
    // Constructors
    protected AbstractWall(final int tc) {
        super(true, true);
        this.setTemplateColor(tc);
    }

    protected AbstractWall(final boolean isSolidXN, final boolean isSolidXS,
            final boolean isSolidXW, final boolean isSolidXE,
            final boolean isSolidIN, final boolean isSolidIS,
            final boolean isSolidIW, final boolean isSolidIE, final int tc,
            final int attr, final int attrColor) {
        super(true, true);
        this.setTemplateColor(tc);
        this.setAttributeID(attr);
        this.setAttributeTemplateColor(attrColor);
        this.setDirectionallySolid(true, DirectionConstants.DIRECTION_NORTH,
                isSolidXN);
        this.setDirectionallySolid(true, DirectionConstants.DIRECTION_SOUTH,
                isSolidXS);
        this.setDirectionallySolid(true, DirectionConstants.DIRECTION_WEST,
                isSolidXW);
        this.setDirectionallySolid(true, DirectionConstants.DIRECTION_EAST,
                isSolidXE);
        this.setDirectionallySolid(false, DirectionConstants.DIRECTION_NORTH,
                isSolidIN);
        this.setDirectionallySolid(false, DirectionConstants.DIRECTION_SOUTH,
                isSolidIS);
        this.setDirectionallySolid(false, DirectionConstants.DIRECTION_WEST,
                isSolidIW);
        this.setDirectionallySolid(false, DirectionConstants.DIRECTION_EAST,
                isSolidIE);
    }

    protected AbstractWall(final boolean sightBlock, final int tc) {
        super(true, sightBlock);
        this.setTemplateColor(tc);
    }

    protected AbstractWall(final boolean isDestroyable,
            final boolean doesChainReact, final int tc) {
        super(true, false, false, false, false, false, false, true,
                isDestroyable, doesChainReact, true);
        this.setTemplateColor(tc);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_WALL_ON;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        FantastleX.getApplication().showMessage("Can't go that way");
        // Play move failed sound, if it's enabled
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}