/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.generic.GenericPort;

public class KPort extends GenericPort {
    // Constructors
    public KPort() {
        super(new KPlug(), 'K');
    }
}