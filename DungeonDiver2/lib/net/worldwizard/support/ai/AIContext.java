package net.worldwizard.support.ai;

import net.worldwizard.support.map.Map;
import net.worldwizard.support.map.generic.MapObject;
import net.worldwizard.support.map.generic.TypeConstants;
import net.worldwizard.support.map.objects.BattleCharacter;

public class AIContext {
    private final BattleCharacter aiContext;
    private final int myTeam;
    private final int[][] apCosts;
    private final int[][] creatureLocations;
    public static final int MINIMUM_RADIUS = 1;
    public static final int MAXIMUM_RADIUS = 9;
    public static final int NOTHING_THERE = -1;
    public static final int CANNOT_MOVE_THERE = -1;

    // Constructor
    public AIContext(final BattleCharacter context, final Map arena) {
        this.aiContext = context;
        this.myTeam = context.getTeamID();
        this.apCosts = new int[arena.getRows()][arena.getColumns()];
        this.creatureLocations = new int[arena.getRows()][arena.getColumns()];
    }

    // Methods
    public void updateContext(final Map arena) {
        for (int x = 0; x < this.apCosts.length; x++) {
            for (int y = 0; y < this.apCosts[x].length; y++) {
                final MapObject obj = arena.getBattleCell(x, y);
                if (obj.isSolid()) {
                    this.apCosts[x][y] = AIContext.CANNOT_MOVE_THERE;
                } else {
                    final MapObject ground = arena.getBattleGround(x, y);
                    this.apCosts[x][y] = ground.getBattleAPCost();
                }
            }
        }
        for (int x = 0; x < this.creatureLocations.length; x++) {
            for (int y = 0; y < this.creatureLocations[x].length; y++) {
                final MapObject obj = arena.getBattleCell(x, y);
                if (obj.isOfType(TypeConstants.TYPE_BATTLE_CHARACTER)) {
                    final BattleCharacter bc = (BattleCharacter) obj;
                    this.creatureLocations[x][y] = bc.getTeamID();
                } else {
                    this.creatureLocations[x][y] = AIContext.NOTHING_THERE;
                }
            }
        }
    }

    public BattleCharacter getCharacter() {
        return this.aiContext;
    }

    public int[] isEnemyNearby() {
        return this.isEnemyNearby(1, 1);
    }

    public int[] isEnemyNearby(final int minRadius, final int maxRadius) {
        int fMaxR = maxRadius;
        int fMinR = minRadius;
        if (fMaxR > AIContext.MAXIMUM_RADIUS) {
            fMaxR = AIContext.MAXIMUM_RADIUS;
        }
        if (fMaxR < AIContext.MINIMUM_RADIUS) {
            fMaxR = AIContext.MINIMUM_RADIUS;
        }
        if (fMinR > AIContext.MAXIMUM_RADIUS) {
            fMinR = AIContext.MAXIMUM_RADIUS;
        }
        if (fMinR < AIContext.MINIMUM_RADIUS) {
            fMinR = AIContext.MINIMUM_RADIUS;
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
                        return new int[] { u - x, v - y };
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Ignore
                }
            }
        }
        return null;
    }
}
