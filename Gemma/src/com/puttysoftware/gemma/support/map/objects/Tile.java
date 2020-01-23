/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericGround;

public class Tile extends GenericGround {
    // Constructors
    public Tile() {
        super();
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
        return "Tile is one of the many types of ground.";
    }
}
