/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractPort;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class PPort extends AbstractPort {
    // Constructors
    public PPort() {
        super(new PPlug(), 'P');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_P_PORT;
    }
}