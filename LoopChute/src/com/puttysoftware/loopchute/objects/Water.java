/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericField;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.generic.TypeConstants;
import com.puttysoftware.loopchute.maze.MazeConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class Water extends GenericField {
    // Constructors
    public Water() {
        super(new AquaBoots(), true, ColorConstants.COLOR_WATER);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_ON_WATER);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        LoopChute.getApplication().showMessage("You'll drown");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WATER);
    }

    @Override
    public void pushIntoAction(final ObjectInventory inv,
            final MazeObject pushed, final int x, final int y, final int z) {
        final Application app = LoopChute.getApplication();
        if (pushed.isPushable()) {
            app.getGameManager().morph(new SunkenBlock(), x, y, z,
                    MazeConstants.LAYER_GROUND);
            app.getGameManager().morph(new Empty(), x, y, z,
                    MazeConstants.LAYER_OBJECT);
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_SINK_BLOCK);
        }
    }

    @Override
    public String getName() {
        return "Water";
    }

    @Override
    public String getPluralName() {
        return "Squares of Water";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Water is too unstable to walk on without Aqua Boots.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}
