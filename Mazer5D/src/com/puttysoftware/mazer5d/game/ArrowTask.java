/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.game;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.ArrowTypeConstants;
import com.puttysoftware.mazer5d.compatibility.abc.DirectionResolver;
import com.puttysoftware.mazer5d.compatibility.abc.GenericTransientObject;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.compatibility.maze.MazeModel;
import com.puttysoftware.mazer5d.compatibility.objects.GameObjects;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.Layers;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

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
        boolean res = true;
        final BagOStuff app = Mazer5D.getBagOStuff();
        final PlayerLocationManager plMgr = app.getGameManager()
                .getPlayerManager();
        final ObjectInventory inv = app.getGameManager().getObjectInventory();
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
        final MazeModel m = app.getMazeManager().getMaze();
        m.tickTimers(pz);
        MazeObjectModel o = null;
        try {
            o = m.getCell(px + cumX, py + cumY, pz, Layers.OBJECT);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            o = GameObjects.createObject(MazeObjects.WALL);
        }
        final GenericTransientObject a = ArrowTask.createArrowForType(this.at);
        final String suffix = DirectionResolver.resolveDirectionConstantToName(
                DirectionResolver.resolveRelativeDirection(incX, incY));
        a.setNameSuffix(suffix);
        SoundPlayer.playSound(SoundIndex.ARROW_FIRED, SoundGroup.GAME);
        while (res) {
            res = o.arrowHitAction(px + cumX, py + cumY, pz, incX, incY,
                    this.at, inv);
            if (!res) {
                break;
            }
            app.getGameManager().redrawOneSquare(px + cumX, py + cumY, true, a);
            app.getGameManager().redrawOneSquare(px + cumX, py + cumY, false,
                    GameObjects.getEmptySpace());
            cumX += incX;
            cumY += incY;
            try {
                o = m.getCell(px + cumX, py + cumY, pz, Layers.OBJECT);
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
        SoundPlayer.playSound(SoundIndex.ARROW_DEAD, SoundGroup.GAME);
        app.getGameManager().arrowDone();
    }

    private static GenericTransientObject createArrowForType(final int type) {
        switch (type) {
        case ArrowTypeConstants.ARROW_TYPE_PLAIN:
            return (GenericTransientObject) GameObjects.createObject(
                    MazeObjects.ARROW);
        case ArrowTypeConstants.ARROW_TYPE_ICE:
            return (GenericTransientObject) GameObjects.createObject(
                    MazeObjects.ICE_ARROW);
        case ArrowTypeConstants.ARROW_TYPE_FIRE:
            return (GenericTransientObject) GameObjects.createObject(
                    MazeObjects.FIRE_ARROW);
        case ArrowTypeConstants.ARROW_TYPE_POISON:
            return (GenericTransientObject) GameObjects.createObject(
                    MazeObjects.POISON_ARROW);
        case ArrowTypeConstants.ARROW_TYPE_SHOCK:
            return (GenericTransientObject) GameObjects.createObject(
                    MazeObjects.SHOCK_ARROW);
        case ArrowTypeConstants.ARROW_TYPE_GHOST:
            return (GenericTransientObject) GameObjects.createObject(
                    MazeObjects.GHOST_ARROW);
        default:
            return null;
        }
    }
}
