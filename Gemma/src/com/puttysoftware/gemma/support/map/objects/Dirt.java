/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericGround;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;

public class Dirt extends GenericGround {
    // Constructors
    public Dirt() {
        super();
        this.setTemplateTransform(new TemplateTransform(0.5, 0.3, 0.1));
    }

    @Override
    public String getName() {
        return "Dirt";
    }

    @Override
    public String getPluralName() {
        return "Squares of Dirt";
    }

    @Override
    public String getDescription() {
        return "Dirt is one of the many types of ground.";
    }

    @Override
    public String getGameImageNameHook() {
        return "textured";
    }

    @Override
    public String getEditorImageNameHook() {
        return "textured";
    }
}
