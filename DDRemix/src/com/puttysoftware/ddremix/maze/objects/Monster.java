package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.Application;
import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.maze.abc.AbstractMovingObject;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.randomrange.RandomRange;

public class Monster extends AbstractMovingObject {
    // Constructors
    public Monster() {
        super(false);
        this.setSavedObject(new Empty());
        this.activateTimer(1);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        if (DDRemix.getApplication().getMode() != Application.STATUS_BATTLE) {
            DDRemix.getApplication().getBattle().doBattle();
            DDRemix.getApplication().getMazeManager().getMaze()
                    .postBattle(this, dirX, dirY, true);
        }
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Move the monster
        final RandomRange r = new RandomRange(0, 7);
        final int move = r.generate();
        DDRemix.getApplication().getMazeManager().getMaze()
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
}
