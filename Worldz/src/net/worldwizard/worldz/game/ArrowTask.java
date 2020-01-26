/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.game;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.ArrowTypeConstants;
import net.worldwizard.worldz.generic.DirectionResolver;
import net.worldwizard.worldz.generic.GenericTransientObject;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.objects.Arrow;
import net.worldwizard.worldz.objects.Empty;
import net.worldwizard.worldz.objects.FireArrow;
import net.worldwizard.worldz.objects.IceArrow;
import net.worldwizard.worldz.objects.PoisonArrow;
import net.worldwizard.worldz.objects.ShockArrow;
import net.worldwizard.worldz.objects.Wall;
import net.worldwizard.worldz.resourcemanagers.SoundManager;
import net.worldwizard.worldz.world.World;
import net.worldwizard.worldz.world.WorldConstants;

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
            final Application app = Worldz.getApplication();
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
            final World m = app.getWorldManager().getWorld();
            m.tickTimers(pz);
            WorldObject o = null;
            try {
                o = m.getCell(px + cumX, py + cumY, pz,
                        WorldConstants.LAYER_OBJECT);
            } catch (final ArrayIndexOutOfBoundsException ae) {
                o = new Wall();
            }
            final GenericTransientObject a = ArrowTask
                    .createArrowForType(this.at);
            final String suffix = DirectionResolver
                    .resolveDirectionConstantToName(DirectionResolver
                            .resolveRelativeDirection(incX, incY));
            a.setNameSuffix(suffix);
            if (app.getPrefsManager()
                    .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
                SoundManager.playSound("arrow");
            }
            while (!o.isConditionallyDirectionallySolid(true, incX, incY,
                    inv)) {
                res = o.arrowHitAction(px + cumX, py + cumY, pz, incX, incY,
                        this.at, inv);
                if (!res) {
                    break;
                }
                if (!o.isConditionallyDirectionallySolid(true, incX, incY,
                        inv)) {
                    app.getGameManager().redrawOneSquare(px + cumX, py + cumY,
                            true, a.getName());
                }
                app.getGameManager().redrawOneSquare(px + cumX, py + cumY,
                        false, new Empty().getName());
                cumX += incX;
                cumY += incY;
                try {
                    o = m.getCell(px + cumX, py + cumY, pz,
                            WorldConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    o = new Wall();
                }
            }
            // Fire arrow hit action for final object, if it hasn't already been
            // fired
            if (res) {
                o.arrowHitAction(px + cumX, py + cumY, pz, incX, incY, this.at,
                        inv);
            }
            if (app.getPrefsManager()
                    .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
                SoundManager.playSound("arrowdie");
            }
            app.getGameManager().arrowDone();
        } catch (final Throwable t) {
            Worldz.getDebug().debug(t);
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
        default:
            return null;
        }
    }
}
