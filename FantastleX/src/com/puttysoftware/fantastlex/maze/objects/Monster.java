package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractMovingObject;
import com.puttysoftware.fantastlex.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.randomrange.RandomRange;

public class Monster extends AbstractMovingObject {
    // Constructors
    public Monster() {
        super(false);
        this.setSavedObject(new Empty());
        this.activateTimer(1);
    }

    public Monster(final AbstractMazeObject saved) {
        super(false);
        this.setSavedObject(saved);
        this.activateTimer(1);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        if (FantastleX.getApplication()
                .getMode() != Application.STATUS_BATTLE) {
            FantastleX.getApplication().getBattle().doBattle();
            FantastleX.getApplication().getMazeManager().getMaze()
                    .postBattle(this, dirX, dirY, true);
        }
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final MazeObjectInventory inv) {
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            // Transform into iced monster, if hit by an ice arrow
            final int pz = FantastleX.getApplication().getMazeManager()
                    .getMaze().getPlayerLocationZ();
            FantastleX.getApplication().getGameManager().morph(
                    new IcedMonster(this.getSavedObject()), locX, locY, pz,
                    MazeConstants.LAYER_OBJECT);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Move the monster
        final RandomRange r = new RandomRange(0, 7);
        final int move = r.generate();
        FantastleX.getApplication().getMazeManager().getMaze()
                .updateMonsterPosition(move, dirX, dirY, this);
        this.activateTimer(1);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MONSTER;
    }

    @Override
    public String getName() {
        return "Monster";
    }

    @Override
    public String getPluralName() {
        return "Monsters";
    }

    @Override
    public String getDescription() {
        return "Monsters are dangerous. Encountering one starts a battle.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_REACTS_TO_ICE);
    }
}
