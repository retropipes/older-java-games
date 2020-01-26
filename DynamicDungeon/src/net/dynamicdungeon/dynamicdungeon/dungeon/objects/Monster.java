package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractMovingObject;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.randomrange.RandomRange;

public class Monster extends AbstractMovingObject {
    // Constructors
    public Monster() {
        super(false);
        this.setSavedObject(new Empty());
        this.activateTimer(1);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        if (DynamicDungeon.getApplication()
                .getMode() != Application.STATUS_BATTLE) {
            DynamicDungeon.getApplication().getBattle().doBattle();
            DynamicDungeon.getApplication().getDungeonManager().getDungeon()
                    .postBattle(this, dirX, dirY, true);
        }
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Move the monster
        final RandomRange r = new RandomRange(0, 7);
        final int move = r.generate();
        DynamicDungeon.getApplication().getDungeonManager().getDungeon()
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
