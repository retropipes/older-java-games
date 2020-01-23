/*  LTRemix: An Arena-Solving Game
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ltremix.arena.abstractobjects;

import com.puttysoftware.ltremix.utilities.TypeConstants;

public abstract class AbstractPressureButtonDoor extends AbstractButtonDoor {
    // Constructors
    protected AbstractPressureButtonDoor() {
        super();
        this.type.set(TypeConstants.TYPE_PRESSURE_BUTTON_DOOR);
    }
}