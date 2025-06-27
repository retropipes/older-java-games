/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: loopchute@puttysoftware.com
 */
package com.puttysoftware.loopchute.game;

import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.generic.ArrowTypeConstants;
import com.puttysoftware.loopchute.generic.DirectionResolver;
import com.puttysoftware.loopchute.generic.GenericTransientObject;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.maze.Maze;
import com.puttysoftware.loopchute.maze.MazeConstants;
import com.puttysoftware.loopchute.objects.Arrow;
import com.puttysoftware.loopchute.objects.Empty;
import com.puttysoftware.loopchute.objects.FireArrow;
import com.puttysoftware.loopchute.objects.GhostArrow;
import com.puttysoftware.loopchute.objects.IceArrow;
import com.puttysoftware.loopchute.objects.PoisonArrow;
import com.puttysoftware.loopchute.objects.ShockArrow;
import com.puttysoftware.loopchute.objects.Wall;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class ArrowTask extends Thread {
    // Fields
    private final int x, y, at;

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
            final Application app = LoopChute.getApplication();
            final Maze m = app.getMazeManager().getMaze();
            final ObjectInventory inv = app.getGameManager()
                    .getObjectInventory();
            final int px = m.getPlayerLocationX();
            final int py = m.getPlayerLocationY();
            final int pz = m.getPlayerLocationZ();
            int cumX = this.x;
            int cumY = this.y;
            final int incX = this.x;
            final int incY = this.y;
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
                    SoundConstants.SOUND_ARROW);
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
                    SoundConstants.SOUND_ARROW_DIE);
            app.getGameManager().arrowDone();
        } catch (final Throwable t) {
            LoopChute.getErrorLogger().logError(t);
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
