/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2020 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objectmodel;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.assets.ColorReplaceRules;
import com.puttysoftware.mazer5d.assets.ColorShader;
import com.puttysoftware.mazer5d.assets.ImageIndex;

public abstract class Appearance {
    private final String cacheName;
    private final ImageIndex whichImage;
    private final ColorShader shading;
    private final ColorReplaceRules replacements;

    public Appearance(final String name, final ImageIndex imageIndex) {
        this.cacheName = name;
        this.whichImage = imageIndex;
        this.shading = null;
        this.replacements = null;
    }

    public Appearance(final String name, final ImageIndex imageIndex,
            final ColorShader shader) {
        this.cacheName = name;
        this.whichImage = imageIndex;
        this.shading = shader;
        this.replacements = null;
    }

    public Appearance(final String name, final ImageIndex imageIndex,
            final ColorReplaceRules replaceRules) {
        this.cacheName = name;
        this.whichImage = imageIndex;
        this.shading = null;
        this.replacements = replaceRules;
    }

    public final String getCacheName() {
        return this.cacheName;
    }

    protected final ImageIndex getWhichImage() {
        return this.whichImage;
    }

    public final boolean hasShading() {
        return this.shading != null;
    }

    public final ColorShader getShading() {
        return this.shading;
    }

    public final boolean hasReplacementRules() {
        return this.replacements != null;
    }

    public final ColorReplaceRules getReplacementRules() {
        return this.replacements;
    }

    public abstract BufferedImageIcon getImage();
}
