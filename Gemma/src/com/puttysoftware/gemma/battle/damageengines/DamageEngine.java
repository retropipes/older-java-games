/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.battle.damageengines;

import com.puttysoftware.gemma.support.creatures.Creature;

public abstract class DamageEngine {
    // Methods
    public abstract int computeDamage(Creature enemy, Creature acting);

    public abstract boolean enemyDodged();

    public abstract boolean weaponMissed();

    public abstract boolean weaponCrit();

    public abstract boolean weaponPierce();

    public static DamageEngine getInstance() {
        return new PercentDamageEngine();
    }
}
