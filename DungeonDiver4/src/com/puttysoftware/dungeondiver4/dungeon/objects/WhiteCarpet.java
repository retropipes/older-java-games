/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractCarpet;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class WhiteCarpet extends AbstractCarpet {
    // Constructors
    public WhiteCarpet() {
        super("White", ColorConstants.COLOR_WHITE);
    }
}