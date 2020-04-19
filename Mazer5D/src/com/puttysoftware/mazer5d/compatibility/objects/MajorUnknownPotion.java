/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericPotion;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class MajorUnknownPotion extends GenericPotion {
    // Fields
    private static final int MIN_EFFECT = -25;
    private static final int MAX_EFFECT = 25;

    // Constructors
    public MajorUnknownPotion() {
        super(true, MajorUnknownPotion.MIN_EFFECT,
                MajorUnknownPotion.MAX_EFFECT);
    }

    @Override
    public String getName() {
        return "Major Unknown Potion";
    }

    @Override
    public String getPluralName() {
        return "Major Unknown Potions";
    }

    @Override
    public String getDescription() {
        return "Major Unknown Potions might heal you or hurt you significantly when picked up.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.MAJOR_UNKNOWN_POTION;
    }
}