/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericWall;
import com.puttysoftware.weaselweb.maze.generic.TypeConstants;

public class OneWaySouthWall extends GenericWall {
    public OneWaySouthWall() {
        super(true, false, true, true, true, false, true, true);
        this.setType(TypeConstants.TYPE_PLAIN_WALL);
    }

    @Override
    public String getName() {
        return "One-Way South Wall";
    }

    @Override
    public String getPluralName() {
        return "One-Way South Walls";
    }

    @Override
    public String getDescription() {
        return "One-Way South Walls allow movement through them only South.";
    }
}
