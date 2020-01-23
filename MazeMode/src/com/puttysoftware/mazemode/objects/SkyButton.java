/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericButton;

public class SkyButton extends GenericButton {
    public SkyButton() {
        super(new SkyWallOff(), new SkyWallOn());
    }

    @Override
    public String getName() {
        return "Sky Button";
    }

    @Override
    public String getPluralName() {
        return "Sky Buttons";
    }

    @Override
    public String getDescription() {
        return "Sky Buttons will cause all Sky Walls Off to become On, and all Sky Walls On to become Off.";
    }
}
