/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericSingleKey;

public class SeaweedKey extends GenericSingleKey {
    // Constructors
    public SeaweedKey() {
        super(ColorConstants.COLOR_SEAWEED);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Key";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Keys";
    }

    @Override
    public String getDescription() {
        return "Seaweed Keys will unlock Seaweed Locks, and can only be used once.";
    }
}