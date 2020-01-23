/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class SealedFinish extends AbstractWall {
    // Constructors
    public SealedFinish() {
        super(ColorConstants.COLOR_WHITE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SEALED_FINISH;
    }

    @Override
    public String getName() {
        return "Sealed Finish";
    }

    @Override
    public String getPluralName() {
        return "Sealed Finishes";
    }

    @Override
    public String getDescription() {
        return "Sealed Finishes are Finishes that are currently closed.";
    }
}