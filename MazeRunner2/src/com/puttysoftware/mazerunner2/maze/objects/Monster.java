package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMovingObject;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.prefs.PreferencesManager;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;
import com.puttysoftware.randomrange.RandomRange;

public class Monster extends AbstractMovingObject {
    // Constructors
    public Monster() {
        super(false);
        this.setSavedObject(new Empty());
        this.activateTimer(1);
    }

    public Monster(AbstractMazeObject saved) {
        super(false);
        this.setSavedObject(saved);
        this.activateTimer(1);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        if (PreferencesManager.getRPGEnabled()) {
            if (MazeRunnerII.getApplication().getMode() != Application.STATUS_BATTLE) {
                MazeRunnerII.getApplication().getBattle().doBattle();
                MazeRunnerII.getApplication().getMazeManager().getMaze()
                        .postBattle(this, dirX, dirY, true);
            }
        } else {
            SoundManager.playSound(SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public boolean arrowHitAction(int locX, int locY, int locZ, int dirX,
            int dirY, int arrowType, MazeObjectInventory inv) {
        if (PreferencesManager.getRPGEnabled()) {
            if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
                // Transform into iced monster, if hit by an ice arrow
                int pz = MazeRunnerII.getApplication().getMazeManager()
                        .getMaze().getPlayerLocationZ();
                MazeRunnerII
                        .getApplication()
                        .getGameManager()
                        .morph(new IcedMonster(this.getSavedObject()), locX,
                                locY, pz, MazeConstants.LAYER_OBJECT);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void timerExpiredAction(int dirX, int dirY) {
        if (PreferencesManager.getRPGEnabled()) {
            // Move the monster
            RandomRange r = new RandomRange(0, 7);
            int move = r.generate();
            MazeRunnerII.getApplication().getMazeManager().getMaze()
                    .updateMonsterPosition(move, dirX, dirY, this);
        }
        this.activateTimer(1);
    }

    @Override
    public AbstractMazeObject gameRenderHook(int x, int y, int z) {
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
