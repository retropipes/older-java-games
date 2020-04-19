/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericButton;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class MagentaButton extends GenericButton {
    public MagentaButton() {
        super(new MagentaWallOff(), new MagentaWallOn());
    }

    @Override
    public String getName() {
        return "Magenta Button";
    }

    @Override
    public String getPluralName() {
        return "Magenta Buttons";
    }

    @Override
    public String getDescription() {
        return "Magenta Buttons will cause all Magenta Walls Off to become On, and all Magenta Walls On to become Off.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.MAGENTA_BUTTON;
    }}
