/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractGenerator;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ArrowTypeConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;

public class EnragedBarrierGenerator extends AbstractGenerator {
    // Fields
    private int RAGE_CYCLES;
    private static final int RAGE_LIMIT = 3;

    // Constructors
    public EnragedBarrierGenerator() {
        super(ColorConstants.COLOR_RED);
        this.TIMER_DELAY = 6;
        this.RAGE_CYCLES = 0;
    }

    @Override
    public String getName() {
        return "Enraged Barrier Generator";
    }

    @Override
    public String getPluralName() {
        return "Enraged Barrier Generators";
    }

    @Override
    public String getDescription() {
        return "Enraged Barrier Generators create Barriers. When hit or shot, they stop generating for a while, then resume generating faster than normal.";
    }

    @Override
    protected boolean preMoveActionHook(final int dirX, final int dirY,
            final int dirZ, final int dirW) {
        this.RAGE_CYCLES++;
        if (this.RAGE_CYCLES == EnragedBarrierGenerator.RAGE_LIMIT) {
            final Application app = DungeonDiver4.getApplication();
            final BarrierGenerator bg = new BarrierGenerator();
            app.getGameManager().morph(bg, dirX, dirY, dirZ);
            bg.timerExpiredAction(dirX, dirY);
        }
        return true;
    }

    @Override
    protected void arrowHitActionHook(final int locX, final int locY,
            final int locZ, final int arrowType,
            final DungeonObjectInventory inv) {
        final Application app = DungeonDiver4.getApplication();
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            app.getGameManager().morph(new IcedBarrierGenerator(), locX, locY,
                    locZ);
        } else if (arrowType == ArrowTypeConstants.ARROW_TYPE_POISON) {
            app.getGameManager().morph(new PoisonedBarrierGenerator(), locX,
                    locY, locZ);
        } else if (arrowType == ArrowTypeConstants.ARROW_TYPE_SHOCK) {
            app.getGameManager().morph(new ShockedBarrierGenerator(), locX,
                    locY, locZ);
        } else {
            this.preMoveAction(false, locX, locY, inv);
        }
    }
}
