/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ArrowTypeConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class IcedBarrierGenerator extends AbstractWall {
    // Constants
    private static final int TIMER_DELAY = 24;

    // Constructors
    public IcedBarrierGenerator() {
        super(ColorConstants.COLOR_CYAN);
        this.activateTimer(IcedBarrierGenerator.TIMER_DELAY);
    }

    @Override
    public void timerExpiredAction(int dirX, int dirY) {
        // De-ice
        Application app = DungeonDiver4.getApplication();
        int pz = app.getDungeonManager().getDungeon().getPlayerLocationZ();
        BarrierGenerator bg = new BarrierGenerator();
        app.getGameManager().morph(bg, dirX, dirY, pz);
        bg.timerExpiredAction(dirX, dirY);
    }

    @Override
    public boolean arrowHitAction(int locX, int locY, int locZ, int dirX,
            int dirY, int arrowType, DungeonObjectInventory inv) {
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            // Extend iced effect, if arrow was an ice arrow
            this.extendTimer(IcedBarrierGenerator.TIMER_DELAY);
        } else {
            // Else, de-ice
            Application app = DungeonDiver4.getApplication();
            BarrierGenerator bg = new BarrierGenerator();
            app.getGameManager().morph(bg, locX, locY, locZ);
            bg.timerExpiredAction(locX, locY);
        }
        return false;
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BARRIER_GENERATOR;
    }

    @Override
    public String getName() {
        return "Iced Barrier Generator";
    }

    @Override
    public String getPluralName() {
        return "Iced Barrier Generators";
    }

    @Override
    public String getDescription() {
        return "Iced Barrier Generators are Barrier Generators that have been hit by an Ice Arrow or Ice Bomb.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_REACTS_TO_ICE);
        this.type.set(TypeConstants.TYPE_REACTS_TO_FIRE);
        this.type.set(TypeConstants.TYPE_REACTS_TO_POISON);
        this.type.set(TypeConstants.TYPE_GENERATOR);
    }
}
