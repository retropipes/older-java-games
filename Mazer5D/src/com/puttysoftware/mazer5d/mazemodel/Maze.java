/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2020 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.mazemodel;

import com.puttysoftware.mazer5d.objectmodel.MazeObjectModel;

class Maze extends Map implements MazeModel {
    // Constructors
    public Maze(final int... dimensions) {
        super(dimensions);
    }

    @Override
    public MazeObjectModel getCell(final int... location) {
        return (MazeObjectModel) super.getCell(location);
    }

    @Override
    public void setCell(final MazeObjectModel o, final int... location) {
        super.setCell(o, location);
    }
}
