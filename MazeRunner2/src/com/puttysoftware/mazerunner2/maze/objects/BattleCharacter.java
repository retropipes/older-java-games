/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.creatures.AbstractCreature;
import com.puttysoftware.mazerunner2.maze.abc.AbstractBattleCharacter;

public class BattleCharacter extends AbstractBattleCharacter {
    // Constructors
    public BattleCharacter(final AbstractCreature newTemplate) {
        super(newTemplate);
    }
}
