/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractGround;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;

public class Sand extends AbstractGround {
    // Constructors
    public Sand() {
        super(ColorConstants.COLOR_SAND);
    }

    @Override
    public String getName() {
        return "Sand";
    }

    @Override
    public String getPluralName() {
        return "Squares of Sand";
    }

    @Override
    public String getDescription() {
        return "Sand is one of the many types of ground.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}