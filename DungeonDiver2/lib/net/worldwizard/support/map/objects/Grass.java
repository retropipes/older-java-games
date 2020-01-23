/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericGround;
import net.worldwizard.support.map.generic.TemplateTransform;

public class Grass extends GenericGround {
    // Constructors
    public Grass() {
        super();
        this.setTemplateTransform(new TemplateTransform(0.0, 0.75, 0.0, ""));
    }

    @Override
    public String getName() {
        return "Grass";
    }

    @Override
    public String getPluralName() {
        return "Squares of Grass";
    }

    @Override
    public String getDescription() {
        return "Grass is one of the many types of ground.";
    }

    @Override
    public String getGameImageNameHook() {
        return "textured";
    }
}
