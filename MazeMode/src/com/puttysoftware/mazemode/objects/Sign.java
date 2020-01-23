/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericTextHolder;

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
}