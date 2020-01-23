package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMovingObject;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;

public class IcedMonster extends AbstractMovingObject {
    // Fields and Constants
    private static final int ICE_LENGTH = 20;

    // Constructors
    public IcedMonster(AbstractMazeObject saved) {
        super(true);
        this.setTemplateColor(ColorConstants.COLOR_CYAN);
        this.setSavedObject(saved);
        this.activateTimer(IcedMonster.ICE_LENGTH);
    }

    @Override
    public boolean arrowHitAction(int locX, int locY, int locZ, int dirX,
            int dirY, int arrowType, MazeObjectInventory inv) {
        // Extend iced effect, if hit by an ice arrow
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            this.extendTimer(IcedMonster.ICE_LENGTH);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void timerExpiredAction(int dirX, int dirY) {
        // Transform into a normal monster
        int pz = MazeRunnerII.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        MazeRunnerII
                .getApplication()
                .getGameManager()
                .morph(new Monster(this.getSavedObject()), dirX, dirY, pz,
                        MazeConstants.LAYER_OBJECT);
    }

    @Override
    public String getName() {
        return "Iced Monster";
    }

    @Override
    public String getPluralName() {
        return "Iced Monsters";
    }

    @Override
    public String getDescription() {
        return "Iced Monsters cannot move or fight, but the ice coating will eventually wear off.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_REACTS_TO_ICE);
    }
}
