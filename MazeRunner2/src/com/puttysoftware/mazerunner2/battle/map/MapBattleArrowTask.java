/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.battle.map;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.battle.AbstractBattle;
import com.puttysoftware.mazerunner2.creatures.faiths.Faith;
import com.puttysoftware.mazerunner2.creatures.faiths.FaithConstants;
import com.puttysoftware.mazerunner2.maze.Maze;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.abc.AbstractTransientObject;
import com.puttysoftware.mazerunner2.maze.objects.Arrow;
import com.puttysoftware.mazerunner2.maze.objects.BattleCharacter;
import com.puttysoftware.mazerunner2.maze.objects.Empty;
import com.puttysoftware.mazerunner2.maze.objects.Wall;
import com.puttysoftware.mazerunner2.maze.utilities.DirectionResolver;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

class MapBattleArrowTask extends Thread {
    // Fields
    private final int x, y, at;
    private final MapBattleDefinitions bd;

    // Constructors
    MapBattleArrowTask(final int newX, final int newY, final int newAT,
            final MapBattleDefinitions defs) {
        this.x = newX;
        this.y = newY;
        this.at = newAT;
        this.bd = defs;
        this.setName("Arrow Handler");
    }

    @Override
    public void run() {
        try {
            boolean res = true;
            final Application app = MazeRunnerII.getApplication();
            final Maze m = this.bd.getBattleMaze();
            final int px = this.bd.getActiveCharacter().getX();
            final int py = this.bd.getActiveCharacter().getY();
            int cumX = this.x;
            int cumY = this.y;
            final int incX = this.x;
            final int incY = this.y;
            AbstractMazeObject o = null;
            try {
                o = m.getBattleCell(px + cumX, py + cumY);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                o = new Wall();
            }
            final AbstractTransientObject a = MapBattleArrowTask
                    .createArrowForType(this.at);
            final int newDir = DirectionResolver.resolveRelativeDirection(incX,
                    incY);
            a.setDirection(newDir);
            SoundManager.playSound(SoundConstants.SOUND_ARROW);
            while (res) {
                res = o.arrowHitBattleCheck();
                if (!res) {
                    break;
                }
                // Draw arrow
                app.getBattle().redrawOneBattleSquare(px + cumX, py + cumY, a);
                // Delay, for animation purposes
                Thread.sleep(MapBattleArrowSpeedConstants.getArrowSpeed());
                // Clear arrow
                app.getBattle().redrawOneBattleSquare(px + cumX, py + cumY,
                        new Empty());
                cumX += incX;
                cumY += incY;
                try {
                    o = m.getBattleCell(px + cumX, py + cumY);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    res = false;
                }
            }
            // Check to see if the arrow hit a creature
            BattleCharacter hit = null;
            if (o instanceof BattleCharacter) {
                // Arrow hit a creature, hurt it
                SoundManager.playSound(SoundConstants.SOUND_ARROW_DIE);
                hit = (BattleCharacter) o;
                final Faith shooter = this.bd.getActiveCharacter().getTemplate()
                        .getFaith();
                final Faith target = hit.getTemplate().getFaith();
                final int mult = (int) (shooter
                        .getMultiplierForOtherFaith(target.getFaithID()) * 10);
                final AbstractBattle bl = app.getBattle();
                if (mult == 0) {
                    hit.getTemplate().doDamagePercentage(1);
                    bl.setStatusMessage(
                            "Ow, you got shot! It didn't hurt much.");
                } else if (mult == 5) {
                    hit.getTemplate().doDamagePercentage(3);
                    bl.setStatusMessage(
                            "Ow, you got shot! It hurt a little bit.");
                } else if (mult == 10) {
                    hit.getTemplate().doDamagePercentage(5);
                    bl.setStatusMessage("Ow, you got shot! It hurt somewhat.");
                } else if (mult == 20) {
                    hit.getTemplate().doDamagePercentage(8);
                    bl.setStatusMessage(
                            "Ow, you got shot! It hurt significantly!");
                }
            } else {
                // Arrow has died
                SoundManager.playSound(SoundConstants.SOUND_ARROW_DIE);
            }
            app.getBattle().arrowDone(hit);
        } catch (final Throwable t) {
            MazeRunnerII.getErrorLogger().logError(t);
        }
    }

    private static AbstractTransientObject createArrowForType(final int type) {
        return new Arrow(FaithConstants.getFaithColor(type).getRGB());
    }
}
