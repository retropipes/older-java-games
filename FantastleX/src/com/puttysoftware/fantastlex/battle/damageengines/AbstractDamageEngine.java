/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.battle.damageengines;

import com.puttysoftware.fantastlex.creatures.AbstractCreature;

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
