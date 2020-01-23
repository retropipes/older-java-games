/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GenericToggleWall;
import net.worldwizard.support.map.generic.TemplateTransform;

public class SkyWallOff extends GenericToggleWall {
    // Constructors
    public SkyWallOff() {
        super(false, new TemplateTransform(0.5, 0.5, 1.0, ""));
    }

    // Scriptability
    @Override
    public String getName() {
        return "Sky Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Sky Walls Off";
    }

    @Override
    public String getDescription() {
        return "Sky Walls Off can be walked through, and will change to Sky Walls On when a Sky Button is pressed.";
    }
}