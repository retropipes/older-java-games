/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericButton;

public class BlueButton extends GenericButton {
    public BlueButton() {
        super(new BlueWallOff(), new BlueWallOn());
    }

    @Override
    public String getName() {
        return "Blue Button";
    }

    @Override
    public String getPluralName() {
        return "Blue Buttons";
    }

    @Override
    public String getDescription() {
        return "Blue Buttons will cause all Blue Walls Off to become On, and all Blue Walls On to become Off.";
    }
}
