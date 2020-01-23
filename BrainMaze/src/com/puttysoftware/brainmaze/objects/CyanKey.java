/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericSingleKey;

public class CyanKey extends GenericSingleKey {
    // Constructors
    public CyanKey() {
        super(ColorConstants.COLOR_CYAN);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Cyan Key";
    }

    @Override
    public String getPluralName() {
        return "Cyan Keys";
    }

    @Override
    public String getDescription() {
        return "Cyan Keys will unlock Cyan Locks, and can only be used once.";
    }
}