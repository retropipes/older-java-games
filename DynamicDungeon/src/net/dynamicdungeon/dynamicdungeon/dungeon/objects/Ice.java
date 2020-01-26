/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractGround;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;
import net.dynamicdungeon.randomrange.RandomRange;

public class Ice extends AbstractGround {
    public Ice() {
        super(false);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_ICE;
    }

    @Override
    public String getName() {
        return "Ice";
    }

    @Override
    public String getPluralName() {
        return "Squares of Ice";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        SoundManager.playSound(SoundConstants.SOUND_WALK_ICE);
    }

    @Override
    public String getDescription() {
        return "Ice is one of the many types of ground - it is frictionless. Anything that crosses it will slide.";
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon maze, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Generate Ice at 40% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 40;
    }
}
