/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractGround;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class BridgedLavaHorizontal extends AbstractGround {
    // Constructors
    public BridgedLavaHorizontal() {
        super(ColorConstants.COLOR_ORANGE);
        this.setAttributeID(
                ObjectImageConstants.OBJECT_IMAGE_BRIDGE_HORIZONTAL);
        this.setAttributeTemplateColor(ColorConstants.COLOR_BRIDGE);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public String getName() {
        return "Bridged Lava Horizontal";
    }

    @Override
    public String getPluralName() {
        return "Squares of Bridged Lava Horizontal";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Bridged Lava Horizontal, unlike Lava, can be walked on.";
    }
}
