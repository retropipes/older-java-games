/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.ai;

import java.awt.Point;

import com.puttysoftware.riskyrescue.map.Map;
import com.puttysoftware.riskyrescue.map.objects.BattleCharacter;
import com.puttysoftware.riskyrescue.map.objects.MapObject;

public class AIContext {
    private final BattleCharacter aiContext;
    private final int myTeam;
    private final int[][] apCosts;
    private final int[][] creatureLocations;
    private static final int MINIMUM_RADIUS = 1;
    private static final int MAXIMUM_RADIUS = 9;
    private static final int NOTHING_THERE = -1;
    private static final int CANNOT_MOVE_THERE = -1;

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
                    this.apCosts[x][y] = MapObject.getBattleAPCost();
                }
            }
        }
        for (int x = 0; x < this.creatureLocations.length; x++) {
            for (int y = 0; y < this.creatureLocations[x].length; y++) {
                final MapObject obj = arena.getBattleCell(x, y);
                if (obj instanceof BattleCharacter) {
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

    Point isEnemyNearby() {
        return this.isEnemyNearby(1, 1);
    }

    Point isEnemyNearby(final int minRadius, final int maxRadius) {
        int fMinR = minRadius;
        int fMaxR = maxRadius;
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
                        return new Point(u - x, v - y);
                    }
                } catch (final ArrayIndexOutOfBoundsException aioob) {
                    // Ignore
                }
            }
        }
        return null;
    }
}
