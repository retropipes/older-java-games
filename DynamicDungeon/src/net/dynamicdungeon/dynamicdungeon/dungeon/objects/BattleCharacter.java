/*  DungeonRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.creatures.AbstractCreature;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractBattleCharacter;

public class BattleCharacter extends AbstractBattleCharacter {
    // Constructors
    public BattleCharacter(final AbstractCreature newTemplate) {
	super(newTemplate);
    }
}
