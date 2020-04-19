/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericPotion;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class MinorHurtPotion extends GenericPotion {
    // Fields
    private static final int MIN_HURT = -1;
    private static final int MAX_HURT = -5;

    // Constructors
    public MinorHurtPotion() {
        super(true, MinorHurtPotion.MAX_HURT, MinorHurtPotion.MIN_HURT);
    }

    @Override
    public String getName() {
        return "Minor Hurt Potion";
    }

    @Override
    public String getPluralName() {
        return "Minor Hurt Potions";
    }

    @Override
    public String getDescription() {
        return "Minor Hurt Potions hurt you slightly when picked up.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.MINOR_HURT_POTION;
    }
}