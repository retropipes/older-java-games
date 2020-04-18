package com.puttysoftware.mazer5d.mazemodel;

import com.puttysoftware.mazer5d.objectmodel.ObjectModel;

public interface MapModel {
    ObjectModel getCell(int... location);

    int getSize(int dimension);

    void setCell(ObjectModel o, int... location);

    void fill(ObjectModel with);
}