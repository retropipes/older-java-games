/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.ai;

import java.awt.Point;

public class AutoMapAI extends AbstractMapAIRoutine {
    // Constructor
    public AutoMapAI() {
        super();
    }

    @Override
    public int getNextAction(final MapAIContext ac) {
        final Point there = ac.isEnemyNearby();
        if (there != null) {
            // Something hostile is nearby, so attack it
            this.moveX = there.x;
            this.moveY = there.y;
            return AbstractMapAIRoutine.ACTION_MOVE;
        } else {
            return AbstractMapAIRoutine.ACTION_END_TURN;
        }
    }
}
