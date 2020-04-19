/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericBoots;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class HealBoots extends GenericBoots {
    // Constants
    private static final int HEAL_AMOUNT = 1;

    // Constructors
    public HealBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Heal Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Heal Boots";
    }

    @Override
    public String getDescription() {
        return "Heal Boots restore your health as you walk. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public void stepAction() {
        Mazer5D.getBagOStuff().getMazeManager().getMaze().heal(
                HealBoots.HEAL_AMOUNT);
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.HEAL_BOOTS;
    }
}
