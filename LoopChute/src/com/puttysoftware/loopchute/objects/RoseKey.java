/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericSingleKey;

public class RoseKey extends GenericSingleKey {
    // Constructors
    public RoseKey() {
        super(ColorConstants.COLOR_ROSE);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Key";
    }

    @Override
    public String getPluralName() {
        return "Rose Keys";
    }

    @Override
    public String getDescription() {
        return "Rose Keys will unlock Rose Locks, and can only be used once.";
    }
}