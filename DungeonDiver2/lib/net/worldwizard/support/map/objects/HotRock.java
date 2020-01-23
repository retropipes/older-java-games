/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericGround;
import net.worldwizard.support.map.generic.TemplateTransform;

public class HotRock extends GenericGround {
    // Constructors
    public HotRock() {
        super();
        this.setTemplateTransform(new TemplateTransform(1.0, 0.0, 0.0, ""));
    }

    @Override
    public String getName() {
        return "Hot Rock";
    }

    @Override
    public String getPluralName() {
        return "Squares of Hot Rock";
    }

    @Override
    public String getDescription() {
        return "Hot Rock is one of the many types of ground.";
    }

    @Override
    public String getGameImageNameHook() {
        return "textured";
    }
}