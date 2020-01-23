/*  Worldz: A World-Exploring Game
Copyright (C) 2008  Iric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericPort;

public class IPort extends GenericPort {
    // Constructors
    public IPort() {
        super(new IPlug(), 'I');
    }
}