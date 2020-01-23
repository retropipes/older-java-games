/*  Mapr5D: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver3.support.map.generic;

import java.awt.Color;

import com.puttysoftware.dungeondiver3.support.map.MapConstants;

public abstract class GenericTransientObject extends MapObject {
    // Fields
    private String name;
    private final String baseName;

    // Constructors
    protected GenericTransientObject(String newBaseName, Color arrowColor) {
        super(true);
        this.baseName = newBaseName;
        this.name = newBaseName;
        this.setTemplateTransform(new TemplateTransform(arrowColor));
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

    public final void setNameSuffix(String suffix) {
        this.name = this.baseName + " " + suffix;
    }

    @Override
    public int getLayer() {
        return MapConstants.LAYER_OBJECT;
    }

    @Override
    protected final void setTypes() {
        // Do nothing
    }

    @Override
    public int getCustomProperty(int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }
}
