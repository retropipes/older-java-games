/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.generic.GenericButton;

public class GreenButton extends GenericButton {
    public GreenButton() {
        super(new GreenWallOff(), new GreenWallOn());
    }

    @Override
    public String getName() {
        return "Green Button";
    }

    @Override
    public String getPluralName() {
        return "Green Buttons";
    }

    @Override
    public String getDescription() {
        return "Green Buttons will cause all Green Walls Off to become On, and all Green Walls On to become Off.";
    }
}
