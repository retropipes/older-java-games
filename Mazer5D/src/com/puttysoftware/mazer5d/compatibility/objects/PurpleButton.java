/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericButton;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class PurpleButton extends GenericButton {
    public PurpleButton() {
        super(new PurpleWallOff(), new PurpleWallOn());
    }

    @Override
    public String getName() {
        return "Purple Button";
    }

    @Override
    public String getPluralName() {
        return "Purple Buttons";
    }

    @Override
    public String getDescription() {
        return "Purple Buttons will cause all Purple Walls Off to become On, and all Purple Walls On to become Off.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.PURPLE_BUTTON;
    }
}
