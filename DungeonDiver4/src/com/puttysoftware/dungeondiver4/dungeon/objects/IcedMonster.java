package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMovingObject;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ArrowTypeConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;

public class IcedMonster extends AbstractMovingObject {
    // Fields and Constants
    private static final int ICE_LENGTH = 20;

    // Constructors
    public IcedMonster(AbstractDungeonObject saved) {
        super(true);
        this.setTemplateColor(ColorConstants.COLOR_CYAN);
        this.setSavedObject(saved);
        this.activateTimer(IcedMonster.ICE_LENGTH);
    }

    @Override
    public boolean arrowHitAction(int locX, int locY, int locZ, int dirX,
            int dirY, int arrowType, DungeonObjectInventory inv) {
        // Extend iced effect, if hit by an ice arrow
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            this.extendTimer(IcedMonster.ICE_LENGTH);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void timerExpiredAction(int dirX, int dirY) {
        // Transform into a normal monster
        int pz = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon().getPlayerLocationZ();
        DungeonDiver4
                .getApplication()
                .getGameManager()
                .morph(new MonsterObject(this.getSavedObject()), dirX, dirY,
                        pz, DungeonConstants.LAYER_OBJECT);
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
