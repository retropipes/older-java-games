/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericPort;

public class HPort extends GenericPort {
    // Constructors
    public HPort() {
        super(new HPlug(), 'H');
    }
}