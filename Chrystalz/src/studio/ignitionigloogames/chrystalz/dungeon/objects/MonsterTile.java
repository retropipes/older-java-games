/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.objects;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractMovingObject;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.RandomGenerationRule;
import studio.ignitionigloogames.chrystalz.manager.asset.ObjectImageConstants;
import studio.ignitionigloogames.common.random.RandomRange;

public class MonsterTile extends AbstractMovingObject {
    // Constructors
    public MonsterTile() {
        super(false);
        this.setSavedObject(new Empty());
        this.activateTimer(1);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        if (Chrystalz.getApplication().getMode() != Application.STATUS_BATTLE) {
            Chrystalz.getApplication().getBattle().doBattle();
            Chrystalz.getApplication().getDungeonManager().getDungeon()
                    .postBattle(this, dirX, dirY, true);
        }
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Move the monster
        final RandomRange r = new RandomRange(0, 7);
        final int move = r.generate();
        Chrystalz.getApplication().getDungeonManager().getDungeon()
                .updateMonsterPosition(move, dirX, dirY, this);
        this.activateTimer(1);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.NONE;
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
    public boolean shouldGenerateObject(final Dungeon dungeon, final int row,
            final int col, final int level, final int layer) {
        if (dungeon.getActiveLevel() == Dungeon.getMaxLevels() - 1) {
            return false;
        } else {
            return super.shouldGenerateObject(dungeon, row, col, level, layer);
        }
    }

    @Override
    public int getMinimumRequiredQuantity(final Dungeon dungeon) {
        if (dungeon.getActiveLevel() == Dungeon.getMaxLevels() - 1) {
            return RandomGenerationRule.NO_LIMIT;
        } else {
            return (int) Math.pow(dungeon.getRows() * dungeon.getColumns(),
                    1.0 / 2.2);
        }
    }

    @Override
    public int getMaximumRequiredQuantity(final Dungeon dungeon) {
        if (dungeon.getActiveLevel() == Dungeon.getMaxLevels() - 1) {
            return RandomGenerationRule.NO_LIMIT;
        } else {
            return (int) Math.pow(dungeon.getRows() * dungeon.getColumns(),
                    1.0 / 1.8);
        }
    }

    @Override
    public boolean isRequired(final Dungeon dungeon) {
        if (dungeon.getActiveLevel() == Dungeon.getMaxLevels() - 1) {
            return false;
        } else {
            return true;
        }
    }
}
