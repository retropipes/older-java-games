/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.game;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTransientObject;
import com.puttysoftware.dungeondiver4.dungeon.objects.Empty;
import com.puttysoftware.dungeondiver4.dungeon.objects.FireArrow;
import com.puttysoftware.dungeondiver4.dungeon.objects.GhostArrow;
import com.puttysoftware.dungeondiver4.dungeon.objects.IceArrow;
import com.puttysoftware.dungeondiver4.dungeon.objects.PlainArrow;
import com.puttysoftware.dungeondiver4.dungeon.objects.PoisonArrow;
import com.puttysoftware.dungeondiver4.dungeon.objects.ShockArrow;
import com.puttysoftware.dungeondiver4.dungeon.objects.Wall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ArrowTypeConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DirectionResolver;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class GameArrowTask extends Thread {
    // Fields
    private int x, y;
    private final int at;

    // Constructors
    public GameArrowTask(final int newX, final int newY, final int newAT) {
        this.x = newX;
        this.y = newY;
        this.at = newAT;
        this.setName("Arrow Handler");
    }

    @Override
    public void run() {
        try {
            boolean res = true;
            final Application app = DungeonDiver4.getApplication();
            final Dungeon m = app.getDungeonManager().getDungeon();
            final DungeonObjectInventory inv = app.getGameManager()
                    .getObjectInventory();
            final int px = m.getPlayerLocationX();
            final int py = m.getPlayerLocationY();
            final int pz = m.getPlayerLocationZ();
            final int[] mod = app.getGameManager().doEffects(this.x, this.y);
            this.x = mod[0];
            this.y = mod[1];
            int cumX = this.x;
            int cumY = this.y;
            final int incX = this.x;
            final int incY = this.y;
            m.tickTimers(pz);
            AbstractDungeonObject o = null;
            try {
                o = m.getCell(px + cumX, py + cumY, pz,
                        DungeonConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                o = new Wall();
            }
            final AbstractTransientObject a = GameArrowTask
                    .createArrowForType(this.at);
            final int newDir = DirectionResolver.resolveRelativeDirection(incX,
                    incY);
            a.setDirection(newDir);
            SoundManager.playSound(SoundConstants.SOUND_ARROW);
            while (res) {
                res = o.arrowHitAction(px + cumX, py + cumY, pz, incX, incY,
                        this.at, inv);
                if (!res) {
                    break;
                }
                app.getGameManager().redrawOneSquare(py + cumY, px + cumX, true,
                        a);
                app.getGameManager().redrawOneSquare(py + cumY, px + cumX,
                        false, new Empty());
                cumX += incX;
                cumY += incY;
                try {
                    o = m.getCell(px + cumX, py + cumY, pz,
                            DungeonConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    res = false;
                }
            }
            // Fire arrow hit action for final object, if it hasn't already been
            // fired
            if (res) {
                o.arrowHitAction(px + cumX, py + cumY, pz, incX, incY, this.at,
                        inv);
            }
            SoundManager.playSound(SoundConstants.SOUND_ARROW_DIE);
            app.getGameManager().arrowDone();
        } catch (final Throwable t) {
            DungeonDiver4.getErrorLogger().logError(t);
        }
    }

    private static AbstractTransientObject createArrowForType(final int type) {
        switch (type) {
        case ArrowTypeConstants.ARROW_TYPE_PLAIN:
            return new PlainArrow();
        case ArrowTypeConstants.ARROW_TYPE_ICE:
            return new IceArrow();
        case ArrowTypeConstants.ARROW_TYPE_FIRE:
            return new FireArrow();
        case ArrowTypeConstants.ARROW_TYPE_POISON:
            return new PoisonArrow();
        case ArrowTypeConstants.ARROW_TYPE_SHOCK:
            return new ShockArrow();
        case ArrowTypeConstants.ARROW_TYPE_GHOST:
            return new GhostArrow();
        default:
            return null;
        }
    }
}
