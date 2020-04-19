/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericButton;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class SeaweedButton extends GenericButton {
    public SeaweedButton() {
        super(new SeaweedWallOff(), new SeaweedWallOn());
    }

    @Override
    public String getName() {
        return "Seaweed Button";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Buttons";
    }

    @Override
    public String getDescription() {
        return "Seaweed Buttons will cause all Seaweed Walls Off to become On, and all Seaweed Walls On to become Off.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SEAWEED_BUTTON;
    }}
