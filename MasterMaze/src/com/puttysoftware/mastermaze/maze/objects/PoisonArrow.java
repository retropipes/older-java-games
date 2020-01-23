/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericTransientObject;

public class PoisonArrow extends GenericTransientObject {
    // Constructors
    public PoisonArrow() {
        super("Poison Arrow", ColorConstants.COLOR_GREEN);
    }
}
