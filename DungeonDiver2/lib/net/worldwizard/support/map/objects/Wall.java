/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericWall;
import net.worldwizard.support.map.generic.TemplateTransform;
import net.worldwizard.support.map.generic.TypeConstants;

public class Wall extends GenericWall {
    // Constructors
    public Wall() {
        super();
        this.setTemplateTransform(new TemplateTransform(0.75, 0.5, 0.25, ""));
    }

    @Override
    public String getName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Walls";
    }

    @Override
    public String getDescription() {
        return "Walls are impassable - you'll need to go around them.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}