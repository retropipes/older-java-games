/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractTrigger;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;
import net.dynamicdungeon.randomrange.RandomRange;

public class Button extends AbstractTrigger {
    // Constructors
    public Button() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BUTTON;
    }

    @Override
    public String getName() {
        return "Button";
    }

    @Override
    public String getPluralName() {
        return "Buttons";
    }

    @Override
    public String getDescription() {
        return "Buttons toggle Walls On and Walls Off.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        SoundManager.playSound(SoundConstants.SOUND_BUTTON);
        final Application app = DynamicDungeon.getApplication();
        app.getDungeonManager().getDungeon().fullScanButton(this.getLayer());
        app.getGameManager().redrawDungeon();
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon maze, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Generate Buttons at 50% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 50;
    }
}