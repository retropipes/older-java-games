/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericButton;

public class CyanButton extends GenericButton {
    public CyanButton() {
        super(new CyanWallOff(), new CyanWallOn());
    }

    @Override
    public String getName() {
        return "Cyan Button";
    }

    @Override
    public String getPluralName() {
        return "Cyan Buttons";
    }

    @Override
    public String getDescription() {
        return "Cyan Buttons will cause all Cyan Walls Off to become On, and all Cyan Walls On to become Off.";
    }
}
