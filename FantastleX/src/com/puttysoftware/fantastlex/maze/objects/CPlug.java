/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractPlug;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class CPlug extends AbstractPlug {
    // Constructors
    public CPlug() {
        super('C');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_C_PLUG;
    }
}