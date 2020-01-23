package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMovingObject;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.randomrange.RandomRange;

public class MovingBlock extends AbstractMovingObject {
    // Constructors
    public MovingBlock() {
        super(true, ColorConstants.COLOR_GRASS,
                ObjectImageConstants.OBJECT_IMAGE_PULLABLE,
                ColorConstants.COLOR_WHITE);
        RandomRange t = new RandomRange(1, 2);
        this.activateTimer(t.generate());
    }

    @Override
    public void timerExpiredAction(int dirX, int dirY) {
        // Move the block
        RandomRange r = new RandomRange(0, 7);
        int move = r.generate();
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .updateMovingBlockPosition(move, dirX, dirY, this);
        RandomRange t = new RandomRange(1, 2);
        this.activateTimer(t.generate());
    }

    @Override
    public boolean shouldGenerateObject(Dungeon dungeon, int row, int col,
            int floor, int level, int layer) {
        // Blacklist object
        return false;
    }

    @Override
    public String getName() {
        return "Moving Block";
    }

    @Override
    public String getPluralName() {
        return "Moving Blocks";
    }

    @Override
    public String getDescription() {
        return "Moving Blocks move on their own. They cannot be pushed or pulled.";
    }
}
