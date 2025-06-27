/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;
import studio.ignitionigloogames.twistedtrek.import1.generic.TypeConstants;

public class IcedBarrierGenerator extends GenericWall {
    // Constants
    private static final int TIMER_DELAY = 20;

    // Constructors
    public IcedBarrierGenerator() {
        super();
        this.activateTimer(IcedBarrierGenerator.TIMER_DELAY);
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // De-ice
        final Application app = Import1.getApplication();
        final int pz = app.getGameManager().getPlayerManager().getPlayerLocationZ();
        final int pw = app.getGameManager().getPlayerManager().getPlayerLocationW();
        final BarrierGenerator bg = new BarrierGenerator();
        app.getGameManager().morph(bg, dirX, dirY, pz, pw);
        bg.timerExpiredAction(dirX, dirY);
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
            final int dirY, final int arrowType, final ObjectInventory inv) {
        // Extend iced effect
        this.extendTimer(IcedBarrierGenerator.TIMER_DELAY);
        return false;
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
    public byte getObjectID() {
        return (byte) 0;
    }

    @Override
    public String getDescription() {
        return "Iced Barrier Generators are Barrier Generators that have been hit by an Ice Arrow or Ice Bomb.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_REACTS_TO_ICE);
    }
}