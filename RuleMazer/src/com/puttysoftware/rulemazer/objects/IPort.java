/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008  Iric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericPort;

public class IPort extends GenericPort {
    // Constructors
    public IPort() {
        super(new IPlug(), 'I');
    }
}