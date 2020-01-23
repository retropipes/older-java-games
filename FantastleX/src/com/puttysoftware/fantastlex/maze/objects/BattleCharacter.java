/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.creatures.AbstractCreature;
import com.puttysoftware.fantastlex.maze.abc.AbstractBattleCharacter;

public class BattleCharacter extends AbstractBattleCharacter {
    // Constructors
    public BattleCharacter(final AbstractCreature newTemplate) {
        super(newTemplate);
    }
}
