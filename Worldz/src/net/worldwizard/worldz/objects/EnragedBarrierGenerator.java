/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.ArrowTypeConstants;
import net.worldwizard.worldz.generic.GenericGenerator;

public class EnragedBarrierGenerator extends GenericGenerator {
    // Fields
    private int RAGE_CYCLES;
    private static final int RAGE_LIMIT = 3;

    // Constructors
    public EnragedBarrierGenerator() {
        super();
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
            final Application app = Worldz.getApplication();
            final BarrierGenerator bg = new BarrierGenerator();
            app.getGameManager().morph(bg, dirX, dirY, dirZ);
            bg.timerExpiredAction(dirX, dirY);
        }
        return true;
    }

    @Override
    protected void arrowHitActionHook(final int locX, final int locY,
            final int locZ, final int arrowType, final ObjectInventory inv) {
        final Application app = Worldz.getApplication();
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
