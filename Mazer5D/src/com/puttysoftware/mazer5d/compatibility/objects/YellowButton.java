/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericButton;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class YellowButton extends GenericButton {
    public YellowButton() {
        super(new YellowWallOff(), new YellowWallOn());
    }

    @Override
    public String getName() {
        return "Yellow Button";
    }

    @Override
    public String getPluralName() {
        return "Yellow Buttons";
    }

    @Override
    public String getDescription() {
        return "Yellow Buttons will cause all Yellow Walls Off to become On, and all Yellow Walls On to become Off.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.YELLOW_BUTTON;
    }}
