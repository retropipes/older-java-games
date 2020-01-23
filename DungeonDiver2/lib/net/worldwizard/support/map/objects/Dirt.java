/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericGround;
import net.worldwizard.support.map.generic.TemplateTransform;

public class Dirt extends GenericGround {
    // Constructors
    public Dirt() {
        super();
        this.setTemplateTransform(new TemplateTransform(0.5, 0.3, 0.1, ""));
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
}
