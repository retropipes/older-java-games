package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.generic.ArrowTypeConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericMovingObject;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.maze.generic.TypeConstants;
import com.puttysoftware.mastermaze.prefs.PreferencesManager;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;
import com.puttysoftware.randomrange.RandomRange;

public class Monster extends GenericMovingObject {
    // Constructors
    public Monster() {
        super(false);
        this.setSavedObject(new Empty());
        this.activateTimer(1);
    }

    public Monster(final MazeObject saved) {
        super(false);
        this.setSavedObject(saved);
        this.activateTimer(1);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        if (PreferencesManager.getRPGEnabled()) {
            if (MasterMaze.getApplication()
                    .getMode() != Application.STATUS_BATTLE) {
                MasterMaze.getApplication().getBattle().doBattle();
                MasterMaze.getApplication().getMazeManager().getMaze()
                        .postBattle(this, dirX, dirY, true);
            }
        } else {
            SoundManager.playSound(SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        if (PreferencesManager.getRPGEnabled()) {
            if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
                // Transform into iced monster, if hit by an ice arrow
                final int pz = MasterMaze.getApplication().getMazeManager()
                        .getMaze().getPlayerLocationZ();
                MasterMaze.getApplication().getGameManager().morph(
                        new IcedMonster(this.getSavedObject()), locX, locY, pz,
                        MazeConstants.LAYER_OBJECT);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        if (PreferencesManager.getRPGEnabled()) {
            // Move the monster
            final RandomRange r = new RandomRange(0, 7);
            final int move = r.generate();
            MasterMaze.getApplication().getMazeManager().getMaze()
                    .updateMonsterPosition(move, dirX, dirY, this);
        }
        this.activateTimer(1);
    }

    @Override
    public MazeObject gameRenderHook(final int x, final int y, final int z) {
        if (PreferencesManager.getRPGEnabled()) {
            return this;
        } else {
            return new Empty();
        }
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_MONSTER;
    }

    @Override
    public String getName() {
        return "Monster";
    }

    @Override
    public String getPluralName() {
        return "Monsters";
    }

    @Override
    public String getDescription() {
        return "Monsters are dangerous. Encountering one starts a battle.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_REACTS_TO_ICE);
    }
}
