/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractInventoryModifier;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class NoBoots extends AbstractInventoryModifier {
    // Constructors
    public NoBoots() {
        super();
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_NO);
        this.setAttributeTemplateColor(ColorConstants.COLOR_RED);
    }

    @Override
    public String getName() {
        return "No Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of No Boots";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        FantastleX.getApplication().getGameManager().decay();
        inv.removeAllBoots();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
    }

    @Override
    public String getDescription() {
        return "No Boots remove any boots worn when picked up.";
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BOOTS;
    }
}
