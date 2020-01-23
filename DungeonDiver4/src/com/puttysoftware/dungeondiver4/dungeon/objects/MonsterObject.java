package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMovingObject;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ArrowTypeConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.randomrange.RandomRange;

public class MonsterObject extends AbstractMovingObject {
    // Constructors
    public MonsterObject() {
        super(false);
        this.setSavedObject(new Empty());
        this.activateTimer(1);
    }

    public MonsterObject(AbstractDungeonObject saved) {
        super(false);
        this.setSavedObject(saved);
        this.activateTimer(1);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        if (DungeonDiver4.getApplication().getMode() != Application.STATUS_BATTLE) {
            DungeonDiver4.getApplication().getBattle().doBattle();
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .postBattle(this, dirX, dirY, true);
        }
    }

    @Override
    public boolean arrowHitAction(int locX, int locY, int locZ, int dirX,
            int dirY, int arrowType, DungeonObjectInventory inv) {
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            // Transform into iced monster, if hit by an ice arrow
            int pz = DungeonDiver4.getApplication().getDungeonManager()
                    .getDungeon().getPlayerLocationZ();
            DungeonDiver4
                    .getApplication()
                    .getGameManager()
                    .morph(new IcedMonster(this.getSavedObject()), locX, locY,
                            pz, DungeonConstants.LAYER_OBJECT);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void timerExpiredAction(int dirX, int dirY) {
        // Move the monster
        RandomRange r = new RandomRange(0, 7);
        int move = r.generate();
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
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

    @Override
    public boolean isRequired() {
        return true;
    }
}
