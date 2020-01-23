package net.worldwizard.dungeondiver2.battle.damageengines;

import net.worldwizard.support.creatures.Creature;

public abstract class DamageEngine {
    // Methods
    public abstract int computeDamage(Creature enemy, Creature acting);

    protected abstract boolean fumble(Creature fumbler);

    public abstract boolean enemyDodged();

    public abstract boolean weaponMissed();

    public static DamageEngine getInstance() {
        return new PercentDamageEngine();
    }
}
