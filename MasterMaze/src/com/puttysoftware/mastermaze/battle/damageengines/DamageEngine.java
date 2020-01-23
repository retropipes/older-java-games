/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.battle.damageengines;

import com.puttysoftware.mastermaze.creatures.Creature;

public abstract class DamageEngine {
    // Methods
    public abstract int computeDamage(Creature enemy, Creature acting);

    public abstract boolean enemyDodged();

    public abstract boolean weaponMissed();

    public abstract boolean weaponCrit();

    public abstract boolean weaponPierce();

    public abstract boolean weaponFumble();

    public static DamageEngine getInstance() {
        return new PercentDamageEngine();
    }
}
