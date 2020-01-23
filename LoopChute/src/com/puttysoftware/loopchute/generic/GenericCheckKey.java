/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.generic;

public abstract class GenericCheckKey extends GenericInventoryableObject {
    // Constructors
    protected GenericCheckKey() {
        super(0);
    }

    protected GenericCheckKey(final int templateColor) {
        super(0);
        this.setTemplateColor(templateColor);
    }

    @Override
    public String getBaseName() {
        return "stone";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_CHECK_KEY);
    }
}
