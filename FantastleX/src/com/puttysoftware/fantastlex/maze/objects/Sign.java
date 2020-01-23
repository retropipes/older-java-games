/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractTextHolder;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class Sign extends AbstractTextHolder {
    // Constructors
    public Sign() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_SIGN;
    }

    @Override
    public String getName() {
        return "Sign";
    }

    @Override
    public String getPluralName() {
        return "Signs";
    }

    @Override
    public String getDescription() {
        return "Signs display their message when walked into.";
    }
}