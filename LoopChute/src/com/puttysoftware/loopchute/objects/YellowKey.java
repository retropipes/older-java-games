/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericSingleKey;

public class YellowKey extends GenericSingleKey {
    // Constructors
    public YellowKey() {
        super(ColorConstants.COLOR_YELLOW);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Yellow Key";
    }

    @Override
    public String getPluralName() {
        return "Yellow Keys";
    }

    @Override
    public String getDescription() {
        return "Yellow Keys will unlock Yellow Locks, and can only be used once.";
    }
}