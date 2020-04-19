/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericBoots;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class AquaBoots extends GenericBoots {
    // Constructors
    public AquaBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Aqua Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Aqua Boots";
    }

    @Override
    public String getDescription() {
        return "Aqua Boots allow walking on water. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.AQUA_BOOTS;
    }
}
