/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.generic.GenericPort;

public class OPort extends GenericPort {
    // Constructors
    public OPort() {
        super(new OPlug(), 'O');
    }
}