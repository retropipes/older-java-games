package net.worldwizard.worldz.ai;

import net.worldwizard.worldz.generic.TypeConstants;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.objects.BattleCharacter;
import net.worldwizard.worldz.world.World;

public class AIContext {
    private final BattleCharacter aiContext;
    private final int myTeam;
    private final int[][] apCosts;
    private final int[][] creatureLocations;
    public static final int MINIMUM_RADIUS = 1;
    public static final int MAXIMUM_RADIUS = 6;
    public static final int NOTHING_THERE = -1;
    public static final int CANNOT_MOVE_THERE = -1;
    public static final int MOVE_WILL_FLEE = -2;

    // Constructor
    public AIContext(final BattleCharacter context, final World arena) {
        this.aiContext = context;
        this.myTeam = context.getTeamID();
        this.apCosts = new int[arena.getRows()][arena.getColumns()];
        this.creatureLocations = new int[arena.getRows()][arena.getColumns()];
    }

    // Methods
    public void updateContext(final World arena) {
        for (int x = 0; x < this.apCosts.length; x++) {
            for (int y = 0; y < this.apCosts[x].length; y++) {
                final WorldObject obj = arena.getBattleCell(x, y);
                if (obj.isSolid()) {
                    this.apCosts[x][y] = AIContext.CANNOT_MOVE_THERE;
                } else {
                    // For now, every move costs 1 AP
                    this.apCosts[x][y] = 1;
                }
            }
        }
        for (int x = 0; x < this.creatureLocations.length; x++) {
            for (int y = 0; y < this.creatureLocations[x].length; y++) {
                final WorldObject obj = arena.getBattleCell(x, y);
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

    public int[] isEnemyNearby(final int minR, final int maxR) {
        int minRadius = minR;
        int maxRadius = maxR;
        if (maxRadius > AIContext.MAXIMUM_RADIUS) {
            maxRadius = AIContext.MAXIMUM_RADIUS;
        }
        if (maxRadius < AIContext.MINIMUM_RADIUS) {
            maxRadius = AIContext.MINIMUM_RADIUS;
        }
        if (minRadius > AIContext.MAXIMUM_RADIUS) {
            minRadius = AIContext.MAXIMUM_RADIUS;
        }
        if (minRadius < AIContext.MINIMUM_RADIUS) {
            minRadius = AIContext.MINIMUM_RADIUS;
        }
        final int x = this.aiContext.getX();
        final int y = this.aiContext.getY();
        int u, v;
        for (u = x - maxRadius; u <= x + maxRadius; u++) {
            for (v = y - maxRadius; v <= y + maxRadius; v++) {
                if (Math.abs(u - x) < minRadius
                        && Math.abs(v - y) < minRadius) {
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

    public int getMoveAPCost(final int x, final int y) {
        final int u = this.aiContext.getX();
        final int v = this.aiContext.getY();
        try {
            if (this.creatureLocations[u + x][v + y] == -1) {
                return this.apCosts[u + x][v + y];
            } else {
                return AIContext.CANNOT_MOVE_THERE;
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return AIContext.MOVE_WILL_FLEE;
        }
    }
}
