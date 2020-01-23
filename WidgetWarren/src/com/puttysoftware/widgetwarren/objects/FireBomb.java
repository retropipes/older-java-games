/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericUsableObject;
import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class FireBomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 2;

    // Constructors
    public FireBomb() {
        super(1);
    }

    @Override
    public String getName() {
        return "Fire Bomb";
    }

    @Override
    public String getPluralName() {
        return "Fire Bombs";
    }

    @Override
    public String getDescription() {
        return "Fire Bombs burn anything in an area of radius 2 centered on the target point.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY,
            final int arrowType, final ObjectInventory inv) {
        // Act as if bomb was used
        this.useAction(null, locX, locY, locZ);
        // Destroy bomb
        WidgetWarren.getApplication().getGameManager()
                .morph(new Empty(), locX, locY, locZ);
        // Stop arrow
        return false;
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y,
            final int z) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_EXPLODE);
        // Enrage objects that react to fire
        WidgetWarren.getApplication().getMazeManager().getMaze()
                .radialScanEnrageObjects(x, y, z, FireBomb.EFFECT_RADIUS);
        // Burn the ground, too
        WidgetWarren.getApplication().getMazeManager().getMaze()
                .radialScanBurnGround(x, y, z, FireBomb.EFFECT_RADIUS);
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(null, x, y, z);
    }
}
