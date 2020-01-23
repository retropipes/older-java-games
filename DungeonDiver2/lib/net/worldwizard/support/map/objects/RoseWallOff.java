/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericToggleWall;
import net.worldwizard.support.map.generic.TemplateTransform;

public class RoseWallOff extends GenericToggleWall {
    // Constructors
    public RoseWallOff() {
        super(false, new TemplateTransform(1.0, 0.5, 0.5, ""));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Rose Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Rose Walls Off";
    }

    @Override
    public String getDescription() {
        return "Rose Walls Off can be walked through, and will change to Rose Walls On when a Rose Button is pressed.";
    }
}