/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericTextHolder;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class Sign extends GenericTextHolder {
    // Constructors
    public Sign() {
        super();
    }

    @Override
    public String getName() {
        return "Sign";
    }

    @Override
    public String getPluralName() {
        return "Signs";
    }

    @Override
    public String getDescription() {
        return "Signs display their message when walked into.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SIGN;
    }
}