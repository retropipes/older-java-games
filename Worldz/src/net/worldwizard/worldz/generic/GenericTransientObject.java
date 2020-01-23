/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericTransientObject extends WorldObject {
    // Fields
    private String name;
    private final String baseName;

    // Constructors
    protected GenericTransientObject(final String newBaseName) {
        super(true);
        this.baseName = newBaseName;
        this.name = newBaseName;
    }

    // Methods
    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public String getPluralName() {
        return this.name + "s";
    }

    @Override
    public String getDescription() {
        return null;
    }

    public final void setNameSuffix(final String suffix) {
        this.name = this.baseName + " " + suffix;
    }

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    protected final void setTypes() {
        // Do nothing
    }

    @Override
    public int getCustomProperty(final int propID) {
        return WorldObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
