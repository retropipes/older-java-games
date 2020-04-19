/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericPassThroughObject;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class SealedFinish extends GenericPassThroughObject {
    // Constructors
    public SealedFinish() {
        super();
    }

    @Override
    public String getName() {
        return "Sealed Finish";
    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SEALED_FINISH;
    }}