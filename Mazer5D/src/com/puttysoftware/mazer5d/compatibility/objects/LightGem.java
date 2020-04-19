/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericLightModifier;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class LightGem extends GenericLightModifier {
    // Constructors
    public LightGem() {
        super();
    }

    @Override
    public String getName() {
        return "Light Gem";
    }

    @Override
    public String getPluralName() {
        return "Light Gems";
    }

    @Override
    public String getDescription() {
        return "Light Gems bathe the immediately adjacent area in permanent light.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.LIGHT_GEM;
    }
}
