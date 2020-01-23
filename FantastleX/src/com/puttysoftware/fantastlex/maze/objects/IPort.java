/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008  Iric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.maze.abc.AbstractPort;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class IPort extends AbstractPort {
    // Constructors
    public IPort() {
        super(new IPlug(), 'I');
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_I_PORT;
    }
}