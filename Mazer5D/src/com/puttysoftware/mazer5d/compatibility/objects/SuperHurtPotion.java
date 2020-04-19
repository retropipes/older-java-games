/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericPotion;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class SuperHurtPotion extends GenericPotion {
    // Constructors
    public SuperHurtPotion() {
        super(false);
    }

    @Override
    public String getName() {
        return "Super Hurt Potion";
    }

    @Override
    public String getPluralName() {
        return "Super Hurt Potions";
    }

    @Override
    public int getEffectValue() {
        return -(Mazer5D.getBagOStuff().getMazeManager().getMaze()
                .getCurrentHP() - 1);
    }

    @Override
    public String getDescription() {
        return "Super Hurt Potions bring you to the brink of death when picked up.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SUPER_HURT_POTION;
    }}