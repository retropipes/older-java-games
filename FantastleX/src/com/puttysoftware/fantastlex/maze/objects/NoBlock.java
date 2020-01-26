/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractAntiObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class NoBlock extends AbstractAntiObject {
    // Constructors
    public NoBlock() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_GRAY);
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_NO);
        this.setAttributeTemplateColor(ColorConstants.COLOR_RED);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BLOCK_BASE;
    }

    @Override
    public void pushIntoAction(final MazeObjectInventory inv,
            final AbstractMazeObject mo, final int x, final int y,
            final int z) {
        // Destroy incoming block
        final Application app = FantastleX.getApplication();
        app.getGameManager().morph(this, x, y, z, MazeConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_DESTROY);
    }

    @Override
    public void pullIntoAction(final MazeObjectInventory inv,
            final AbstractMazeObject mo, final int x, final int y,
            final int z) {
        // Destroy incoming block
        final Application app = FantastleX.getApplication();
        app.getGameManager().morph(this, x, y, z, MazeConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_DESTROY);
    }

    @Override
    public String getName() {
        return "No Block";
    }

    @Override
    public String getPluralName() {
        return "No Blocks";
    }

    @Override
    public String getDescription() {
        return "No Blocks destroy any blocks that attempt to pass through.";
    }
}