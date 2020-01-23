/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractGround;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;

public class Grass extends AbstractGround {
    // Constructors
    public Grass() {
        super(ColorConstants.COLOR_GRASS);
    }

    @Override
    public String getName() {
        return "Grass";
    }

    @Override
    public String getPluralName() {
        return "Squares of Grass";
    }

    @Override
    public String getDescription() {
        return "Grass is one of the many types of ground.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}