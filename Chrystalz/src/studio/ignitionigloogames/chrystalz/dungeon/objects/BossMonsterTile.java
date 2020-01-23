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

public class BossMonsterTile extends AbstractMovingObject {
    // Constructors
    public BossMonsterTile() {
        super(false);
        this.setSavedObject(new Empty());
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        if (Chrystalz.getApplication().getMode() != Application.STATUS_BATTLE) {
            Chrystalz.getApplication().getBattle().doBossBattle();
        }
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.BOSS;
    }

    @Override
    public String getName() {
        return "Boss Monster";
    }

    @Override
    public String getPluralName() {
        return "Boss Monsters";
    }

    @Override
    public String getDescription() {
        return "Boss Monsters are very dangerous. Encountering one starts a boss battle.";
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
            return 1;
        }
    }

    @Override
    public int getMaximumRequiredQuantity(final Dungeon dungeon) {
        if (dungeon.getActiveLevel() == Dungeon.getMaxLevels() - 1) {
            return RandomGenerationRule.NO_LIMIT;
        } else {
            return 1;
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
