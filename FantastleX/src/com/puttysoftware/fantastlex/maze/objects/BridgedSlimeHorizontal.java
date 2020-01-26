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

public class BridgedSlimeHorizontal extends AbstractGround {
    // Constructors
    public BridgedSlimeHorizontal() {
        super(ColorConstants.COLOR_GREEN);
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
        return "Bridged Slime Horizontal";
    }

    @Override
    public String getPluralName() {
        return "Squares of Bridged Slime Horizontal";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Bridged Slime Horizontal, unlike Slime, can be walked on.";
    }
}
