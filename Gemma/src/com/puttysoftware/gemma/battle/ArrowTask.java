/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@putttysoftware.com
 */
package com.puttysoftware.gemma.battle;

import com.puttysoftware.gemma.Application;
import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.support.battle.BattleDefinitions;
import com.puttysoftware.gemma.support.creatures.faiths.Faith;
import com.puttysoftware.gemma.support.creatures.faiths.FaithConstants;
import com.puttysoftware.gemma.support.map.Map;
import com.puttysoftware.gemma.support.map.generic.GenericTransientObject;
import com.puttysoftware.gemma.support.map.generic.MapObject;
import com.puttysoftware.gemma.support.map.objects.Arrow;
import com.puttysoftware.gemma.support.map.objects.BattleCharacter;
import com.puttysoftware.gemma.support.map.objects.Empty;
import com.puttysoftware.gemma.support.map.objects.Wall;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.resourcemanagers.SoundManager;

class ArrowTask extends Thread {
    // Fields
    private final int x, y, at;
    private final BattleDefinitions bd;

    // Constructors
    ArrowTask(final int newX, final int newY, final int newAT,
            final BattleDefinitions defs) {
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
            final Application app = Gemma.getApplication();
            final Map m = this.bd.getBattleMap();
            final int px = this.bd.getActiveCharacter().getX();
            final int py = this.bd.getActiveCharacter().getY();
            int cumX = this.x;
            int cumY = this.y;
            final int incX = this.x;
            final int incY = this.y;
            MapObject o = null;
            try {
                o = m.getBattleCell(px + cumX, py + cumY);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                o = new Wall();
            }
            final GenericTransientObject a = ArrowTask
                    .createArrowForType(this.at);
            final String suffix = DirectionResolver
                    .resolveDirectionConstantToName(DirectionResolver
                            .resolveRelativeDirection(incX, incY));
            a.setNameSuffix(suffix);
            SoundManager.playSound(GameSoundConstants.SOUND_ARROW);
            while (res) {
                res = o.arrowHitCheck();
                if (!res) {
                    break;
                }
                // Draw arrow
                app.getBattle().redrawOneBattleSquare(px + cumX, py + cumY, a);
                // Delay, for animation purposes
                Thread.sleep(ArrowSpeedConstants.getArrowSpeed());
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
                SoundManager.playSound(GameSoundConstants.SOUND_ARROW_HIT);
                hit = (BattleCharacter) o;
                final Faith shooter = this.bd.getActiveCharacter().getTemplate()
                        .getFaith();
                final Faith target = hit.getTemplate().getFaith();
                final int mult = (int) (shooter
                        .getMultiplierForOtherFaith(target.getFaithID()) * 10);
                final BattleLogic bl = app.getBattle();
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
                SoundManager.playSound(GameSoundConstants.SOUND_ARROW_DIE);
            }
            app.getBattle().arrowDone(hit);
        } catch (final Throwable t) {
            Gemma.getErrorLogger().logError(t);
        }
    }

    private static GenericTransientObject createArrowForType(final int type) {
        return new Arrow(FaithConstants.getFaithColor(type));
    }
}
