/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericGround;

public class Snow extends GenericGround {
    // Constructors
    public Snow() {
        super();
    }

    @Override
    public String getName() {
        return "Snow";
    }

    @Override
    public String getPluralName() {
        return "Squares of Snow";
    }

    @Override
    public String getDescription() {
        return "Snow is one of the many types of ground.";
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
