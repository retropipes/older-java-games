/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractGround;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class Tile extends AbstractGround {
    // Constructors
    public Tile() {
        super(true, true, true, true, ColorConstants.COLOR_NONE);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TILE;
    }

    @Override
    public String getName() {
        return "Tile";
    }

    @Override
    public String getPluralName() {
        return "Tiles";
    }

    @Override
    public String getDescription() {
        return "Tile is one of the many types of ground - unlike other types of ground, objects can be pushed and pulled over Tiles.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}