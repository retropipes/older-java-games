/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericToggleWall;
import net.worldwizard.support.map.generic.TemplateTransform;

public class RedWallOn extends GenericToggleWall {
    // Constructors
    public RedWallOn() {
        super(true, new TemplateTransform(1.0, 0.0, 0.0, ""));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Red Wall On";
    }

    @Override
    public String getPluralName() {
        return "Red Walls On";
    }

    @Override
    public String getDescription() {
        return "Red Walls On can NOT be walked through, and will change to Red Walls Off when a Red Button is pressed.";
    }
}