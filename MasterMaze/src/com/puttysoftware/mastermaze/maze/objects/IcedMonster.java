package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.generic.ArrowTypeConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMovingObject;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;

public class IcedMonster extends GenericMovingObject {
    // Fields and Constants
    private static final int ICE_LENGTH = 20;

    // Constructors
    public IcedMonster(final MazeObject saved) {
        super(true);
        this.setTemplateColor(ColorConstants.COLOR_CYAN);
        this.setSavedObject(saved);
        this.activateTimer(IcedMonster.ICE_LENGTH);
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        // Extend iced effect, if hit by an ice arrow
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            this.extendTimer(IcedMonster.ICE_LENGTH);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Transform into a normal monster
        final int pz = MasterMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        MasterMaze.getApplication().getGameManager().morph(
                new Monster(this.getSavedObject()), dirX, dirY, pz,
                MazeConstants.LAYER_OBJECT);
    }

    @Override
    public String getName() {
        return "Iced Monster";
    }

    @Override
    public String getPluralName() {
        return "Iced Monsters";
    }

    @Override
    public String getDescription() {
        return "Iced Monsters cannot move or fight, but the ice coating will eventually wear off.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_REACTS_TO_ICE);
    }
}
