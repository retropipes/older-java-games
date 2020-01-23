/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericPass;

public class EnergySphere extends GenericPass {
    // Constructors
    public EnergySphere() {
        super();
    }

    @Override
    public String getName() {
        return "Energy Sphere";
    }

    @Override
    public String getPluralName() {
        return "Energy Spheres";
    }

    @Override
    public String getDescription() {
        return "Energy Spheres permit walking on Force Fields.";
    }
}
