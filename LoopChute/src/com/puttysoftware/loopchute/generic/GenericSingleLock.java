/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.generic;

public abstract class GenericSingleLock extends GenericLock {
    protected GenericSingleLock(final GenericSingleKey mgk, final int tc) {
        super(mgk);
        this.setTemplateColor(tc);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_SINGLE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    public final String getBaseName() {
        return "lock";
    }
}
