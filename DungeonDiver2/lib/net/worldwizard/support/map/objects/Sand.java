/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericGround;
import net.worldwizard.support.map.generic.TemplateTransform;

public class Sand extends GenericGround {
    // Constructors
    public Sand() {
        super();
        this.setTemplateTransform(new TemplateTransform(1.0, 0.8, 0.6, ""));
    }

    @Override
    public String getName() {
        return "Sand";
    }

    @Override
    public String getPluralName() {
        return "Squares of Sand";
    }

    @Override
    public String getDescription() {
        return "Sand is one of the many types of ground.";
    }

    @Override
    public String getGameImageNameHook() {
        return "textured";
    }
}
