/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.creatures.faiths;

import java.awt.Color;

public final class Faith {
    private final int faithID;

    Faith(final int fid) {
	this.faithID = fid;
    }

    public int getFaithID() {
	return this.faithID;
    }

    public String getName() {
	return FaithConstants.getFaithName(this.faithID);
    }

    public Color getColor() {
	return FaithConstants.getFaithColor(this.faithID);
    }
}
