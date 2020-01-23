/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractMovableObject;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class PullableBlock extends AbstractMovableObject {
    // Constructors
    public PullableBlock() {
        super(false, true, ObjectImageConstants.OBJECT_IMAGE_PULLABLE);
        this.setTemplateColor(ColorConstants.COLOR_GRAY);
    }

    @Override
    public String getName() {
        return "Pullable Block";
    }

    @Override
    public String getPluralName() {
        return "Pullable Blocks";
    }

    @Override
    public String getDescription() {
        return "Pullable Blocks can only be pulled, not pushed.";
    }
}