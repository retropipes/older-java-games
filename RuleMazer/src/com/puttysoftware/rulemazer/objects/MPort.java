/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericPort;

public class MPort extends GenericPort {
    // Constructors
    public MPort() {
        super(new MPlug(), 'M');
    }
}