/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.objects;

import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGround;
import studio.ignitionigloogames.chrystalz.manager.asset.ObjectImageConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundManager;
import studio.ignitionigloogames.common.random.RandomRange;

public class Ice extends AbstractGround {
    public Ice() {
        super(false);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.ICE;
    }

    @Override
    public String getName() {
        return "Ice";
    }

    @Override
    public String getPluralName() {
        return "Squares of Ice";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        SoundManager.playSound(SoundConstants.WALK_ICE);
    }

    @Override
    public String getDescription() {
        return "Ice is one of the many types of ground - it is frictionless. Anything that crosses it will slide.";
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon maze, final int row,
            final int col, final int level, final int layer) {
        // Generate Ice at 40% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 40;
    }
}
