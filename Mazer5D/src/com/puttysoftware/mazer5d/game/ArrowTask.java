/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.game;

import com.puttysoftware.mazer5d.Application;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assetmanagers.SoundConstants;
import com.puttysoftware.mazer5d.assetmanagers.SoundManager;
import com.puttysoftware.mazer5d.generic.ArrowTypeConstants;
import com.puttysoftware.mazer5d.generic.DirectionResolver;
import com.puttysoftware.mazer5d.generic.GenericTransientObject;
import com.puttysoftware.mazer5d.generic.MazeObject;
import com.puttysoftware.mazer5d.maze.Maze;
import com.puttysoftware.mazer5d.maze.MazeConstants;
import com.puttysoftware.mazer5d.objects.Arrow;
import com.puttysoftware.mazer5d.objects.Empty;
import com.puttysoftware.mazer5d.objects.FireArrow;
import com.puttysoftware.mazer5d.objects.GhostArrow;
import com.puttysoftware.mazer5d.objects.IceArrow;
import com.puttysoftware.mazer5d.objects.PoisonArrow;
import com.puttysoftware.mazer5d.objects.ShockArrow;
import com.puttysoftware.mazer5d.objects.Wall;

public class ArrowTask extends Thread {
    // Fields
    private int x, y;
    private final int at;

    // Constructors
    public ArrowTask(final int newX, final int newY, final int newAT) {
        this.x = newX;
        this.y = newY;
        this.at = newAT;
        this.setName("Arrow Handler");
    }

    @Override
    public void run() {
        try {
            boolean res = true;
            final Application app = Mazer5D.getApplication();
            final PlayerLocationManager plMgr = app.getGameManager()
                    .getPlayerManager();
            final ObjectInventory inv = app.getGameManager()
                    .getObjectInventory();
            final int px = plMgr.getPlayerLocationX();
            final int py = plMgr.getPlayerLocationY();
            final int pz = plMgr.getPlayerLocationZ();
            final int[] mod = app.getGameManager().doEffects(this.x, this.y);
            this.x = mod[0];
            this.y = mod[1];
            int cumX = this.x;
            int cumY = this.y;
            final int incX = this.x;
            final int incY = this.y;
            final Maze m = app.getMazeManager().getMaze();
            m.tickTimers(pz);
            MazeObject o = null;
            try {
                o = m.getCell(px + cumX, py + cumY, pz,
                        MazeConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                o = new Wall();
            }
            final GenericTransientObject a = ArrowTask
                    .createArrowForType(this.at);
            final String suffix = DirectionResolver
                    .resolveDirectionConstantToName(DirectionResolver
                            .resolveRelativeDirection(incX, incY));
            a.setNameSuffix(suffix);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_ARROW_FIRED);
            while (res) {
                res = o.arrowHitAction(px + cumX, py + cumY, pz, incX, incY,
                        this.at, inv);
                if (!res) {
                    break;
                }
                app.getGameManager().redrawOneSquare(px + cumX, py + cumY, true,
                        a);
                app.getGameManager().redrawOneSquare(px + cumX, py + cumY,
                        false, new Empty());
                cumX += incX;
                cumY += incY;
                try {
                    o = m.getCell(px + cumX, py + cumY, pz,
                            MazeConstants.LAYER_OBJECT);
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
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_ARROW_DEAD);
            app.getGameManager().arrowDone();
        } catch (final Throwable t) {
            Mazer5D.logError(t);
        }
    }

    private static GenericTransientObject createArrowForType(final int type) {
        switch (type) {
        case ArrowTypeConstants.ARROW_TYPE_PLAIN:
            return new Arrow();
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
