/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.ai;

import java.awt.Point;

import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;

public class MapAIContext {
    private final BattleCharacter aiContext;
    private final int myTeam;
    private final int[][] apCosts;
    private final int[][] creatureLocations;
    private static final int MINIMUM_RADIUS = 1;
    private static final int MAXIMUM_RADIUS = 16;
    private static final int NOTHING_THERE = -1;
    private static final int CANNOT_MOVE_THERE = -1;
    private static final int AP_COST = 1;

    // Constructor
    public MapAIContext(final BattleCharacter context, final Dungeon arena) {
        this.aiContext = context;
        this.myTeam = context.getTeamID();
        this.apCosts = new int[arena.getRows()][arena.getColumns()];
        this.creatureLocations = new int[arena.getRows()][arena.getColumns()];
    }

    // Static method
    public static int getAPCost() {
        return MapAIContext.AP_COST;
    }

    // Methods
    public void updateContext(final Dungeon arena) {
        for (int x = 0; x < this.apCosts.length; x++) {
            for (int y = 0; y < this.apCosts[x].length; y++) {
                final AbstractGameObject obj = arena.getCell(x, y,
                        DungeonConstants.LAYER_OBJECT);
                if (obj.isSolid()) {
                    this.apCosts[x][y] = MapAIContext.CANNOT_MOVE_THERE;
                } else {
                    this.apCosts[x][y] = MapAIContext.AP_COST;
                }
            }
        }
        for (int x = 0; x < this.creatureLocations.length; x++) {
            for (int y = 0; y < this.creatureLocations[x].length; y++) {
                final AbstractGameObject obj = arena.getCell(x, y,
                        DungeonConstants.LAYER_OBJECT);
                if (obj instanceof BattleCharacter) {
                    final BattleCharacter bc = (BattleCharacter) obj;
                    this.creatureLocations[x][y] = bc.getTeamID();
                } else {
                    this.creatureLocations[x][y] = MapAIContext.NOTHING_THERE;
                }
            }
        }
    }

    public BattleCharacter getCharacter() {
        return this.aiContext;
    }

    Point isEnemyNearby() {
        return this.isEnemyNearby(1, 1);
    }

    Point isEnemyNearby(final int minRadius, final int maxRadius) {
        int fMinR = minRadius;
        int fMaxR = maxRadius;
        if (fMaxR > MapAIContext.MAXIMUM_RADIUS) {
            fMaxR = MapAIContext.MAXIMUM_RADIUS;
        }
        if (fMaxR < MapAIContext.MINIMUM_RADIUS) {
            fMaxR = MapAIContext.MINIMUM_RADIUS;
        }
        if (fMinR > MapAIContext.MAXIMUM_RADIUS) {
            fMinR = MapAIContext.MAXIMUM_RADIUS;
        }
        if (fMinR < MapAIContext.MINIMUM_RADIUS) {
            fMinR = MapAIContext.MINIMUM_RADIUS;
        }
        final int x = this.aiContext.getX();
        final int y = this.aiContext.getY();
        int u, v;
        for (u = x - fMaxR; u <= x + fMaxR; u++) {
            for (v = y - fMaxR; v <= y + fMaxR; v++) {
                if (Math.abs(u - x) < fMinR && Math.abs(v - y) < fMinR) {
                    continue;
                }
                try {
                    if (this.creatureLocations[u][v] != -1
                            && this.creatureLocations[u][v] != this.myTeam) {
                        return new Point(u - x, v - y);
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Ignore
                }
            }
        }
        return null;
    }

    Point runAway() {
        final int fMinR = MapAIContext.MAXIMUM_RADIUS;
        final int fMaxR = MapAIContext.MAXIMUM_RADIUS;
        final int x = this.aiContext.getX();
        final int y = this.aiContext.getY();
        int u, v;
        for (u = x - fMaxR; u <= x + fMaxR; u++) {
            for (v = y - fMaxR; v <= y + fMaxR; v++) {
                if (Math.abs(u - x) < fMinR && Math.abs(v - y) < fMinR) {
                    continue;
                }
                try {
                    if (this.creatureLocations[u][v] != -1
                            && this.creatureLocations[u][v] != this.myTeam) {
                        return new Point(u + x, v + y);
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Ignore
                }
            }
        }
        return null;
    }
}
