/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008  Iric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.generic.GenericPort;

public class IPort extends GenericPort {
    // Constructors
    public IPort() {
        super(new IPlug(), 'I');
    }
}