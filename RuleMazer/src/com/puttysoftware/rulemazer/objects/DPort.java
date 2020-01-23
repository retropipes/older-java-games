/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericPort;

public class DPort extends GenericPort {
    // Constructors
    public DPort() {
        super(new DPlug(), 'D');
    }
}