package com.puttysoftware.mazer5d.mazemodel;

import com.puttysoftware.mazer5d.objectmodel.MazeObjectModel;

public interface MazeModel {
    MazeObjectModel getCell(int... location);

    void setCell(MazeObjectModel o, int... location);
}