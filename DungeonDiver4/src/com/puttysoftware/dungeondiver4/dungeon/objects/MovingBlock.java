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
        final RandomRange t = new RandomRange(1, 2);
        this.activateTimer(t.generate());
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Move the block
        final RandomRange r = new RandomRange(0, 7);
        final int move = r.generate();
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .updateMovingBlockPosition(move, dirX, dirY, this);
        final RandomRange t = new RandomRange(1, 2);
        this.activateTimer(t.generate());
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon dungeon, final int row,
            final int col, final int floor, final int level, final int layer) {
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
