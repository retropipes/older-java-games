/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.battle.map;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.battle.AbstractBattle;
import com.puttysoftware.dungeondiver4.creatures.faiths.Faith;
import com.puttysoftware.dungeondiver4.creatures.faiths.FaithConstants;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTransientObject;
import com.puttysoftware.dungeondiver4.dungeon.objects.Arrow;
import com.puttysoftware.dungeondiver4.dungeon.objects.BattleCharacter;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.objects.Wall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DirectionResolver;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

class MapBattleArrowTask extends Thread {
    // Fields
    private int x, y, at;
    private MapBattleDefinitions bd;

    // Constructors
    MapBattleArrowTask(int newX, int newY, int newAT, MapBattleDefinitions defs) {
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
            Application app = DungeonDiver4.getApplication();
            Dungeon m = this.bd.getBattleDungeon();
            int px = this.bd.getActiveCharacter().getX();
            int py = this.bd.getActiveCharacter().getY();
            int cumX = this.x;
            int cumY = this.y;
            int incX = this.x;
            int incY = this.y;
            AbstractDungeonObject o = null;
            try {
                o = m.getBattleCell(px + cumX, py + cumY);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                o = new Wall();
            }
            AbstractTransientObject a = MapBattleArrowTask
                    .createArrowForType(this.at);
            int newDir = DirectionResolver.resolveRelativeDirection(incX, incY);
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
                SoundManager.playSound(SoundConstants.SOUND_ARROW_HIT);
                hit = (BattleCharacter) o;
                Faith shooter = this.bd.getActiveCharacter().getTemplate()
                        .getFaith();
                Faith target = hit.getTemplate().getFaith();
                int mult = (int) (shooter.getMultiplierForOtherFaith(target
                        .getFaithID()) * 10);
                AbstractBattle bl = app.getBattle();
                if (mult == 0) {
                    hit.getTemplate().doDamagePercentage(1);
                    bl.setStatusMessage("Ow, you got shot! It didn't hurt much.");
                } else if (mult == 5) {
                    hit.getTemplate().doDamagePercentage(3);
                    bl.setStatusMessage("Ow, you got shot! It hurt a little bit.");
                } else if (mult == 10) {
                    hit.getTemplate().doDamagePercentage(5);
                    bl.setStatusMessage("Ow, you got shot! It hurt somewhat.");
                } else if (mult == 20) {
                    hit.getTemplate().doDamagePercentage(8);
                    bl.setStatusMessage("Ow, you got shot! It hurt significantly!");
                }
            } else {
                // Arrow has died
                SoundManager.playSound(SoundConstants.SOUND_ARROW_DIE);
            }
            app.getBattle().arrowDone(hit);
        } catch (Throwable t) {
            DungeonDiver4.getErrorLogger().logError(t);
        }
    }

    private static AbstractTransientObject createArrowForType(int type) {
        return new Arrow(FaithConstants.getFaithColor(type).getRGB());
    }
}
