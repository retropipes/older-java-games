/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.battle;

import javax.swing.JFrame;

import com.puttysoftware.mastermaze.creatures.monsters.BaseMonster;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.objects.BattleCharacter;

public abstract class GenericBattle {
    // Constructors
    protected GenericBattle() {
        // Do nothing
    }

    // Methods
    public abstract JFrame getOutputFrame();

    public abstract void doBattle();

    public abstract void doBattleByProxy();

    public abstract void setStatusMessage(final String msg);

    public abstract boolean getTerminatedEarly();

    public abstract void executeNextAIAction();

    public abstract boolean getLastAIActionResult();

    public abstract boolean updatePosition(int x, int y);

    public abstract void fireArrow(int x, int y);

    public abstract void arrowDone(BattleCharacter hit);

    public abstract boolean castSpell();

    public abstract boolean useItem();

    public abstract boolean steal();

    public abstract boolean drain();

    public abstract void endTurn();

    public abstract void redrawOneBattleSquare(int x, int y, MazeObject obj3);

    public abstract boolean isWaitingForAI();

    public abstract BaseMonster getEnemy();
}
