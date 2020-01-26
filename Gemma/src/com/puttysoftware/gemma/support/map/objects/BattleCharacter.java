/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.creatures.Creature;
import com.puttysoftware.gemma.support.map.generic.GenericBattleCharacter;

public class BattleCharacter extends GenericBattleCharacter {
    // Constructors
    public BattleCharacter(final Creature newTemplate) {
        super(newTemplate);
    }
}
