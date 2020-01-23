/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractTeleportTo;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class FinishTo extends AbstractTeleportTo {
    // Constructors
    public FinishTo() {
        super(ColorConstants.COLOR_NONE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_FINISH_TO;
    }

    @Override
    public String getName() {
        return "Finish To";
    }

    @Override
    public String getPluralName() {
        return "Finishes To";
    }

    @Override
    public String getDescription() {
        return "Finishes To behave like regular Finishes, except that the level they send you to might not be the next one.";
    }
}