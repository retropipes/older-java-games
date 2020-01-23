/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.battle.damageengines;

import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;

public abstract class AbstractDamageEngine {
    // Methods
    public abstract int computeDamage(AbstractCreature enemy,
            AbstractCreature acting);

    public abstract boolean enemyDodged();

    public abstract boolean weaponMissed();

    public abstract boolean weaponCrit();

    public abstract boolean weaponPierce();

    public abstract boolean weaponFumble();

    public static AbstractDamageEngine getInstance() {
        return new PercentDamageEngine();
    }
}
