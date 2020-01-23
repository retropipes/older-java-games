package net.worldwizard.support.creatures.monsters;

public class MonsterManager {
    private MonsterManager() {
        // Do nothing
    }

    public static BaseMonster getNewMonsterInstance(final boolean random,
            final boolean scales, final boolean dynamic) {
        if (random) {
            if (scales) {
                if (dynamic) {
                    return new RandomScalingDynamicMonster();
                } else {
                    return new RandomScalingStaticMonster();
                }
            } else {
                if (dynamic) {
                    return new RandomFixedDynamicMonster();
                } else {
                    return new RandomFixedStaticMonster();
                }
            }
        } else {
            if (scales) {
                if (dynamic) {
                    return new DefiniteScalingDynamicMonster();
                } else {
                    return new DefiniteScalingStaticMonster();
                }
            } else {
                if (dynamic) {
                    return new DefiniteFixedDynamicMonster();
                } else {
                    return new DefiniteFixedStaticMonster();
                }
            }
        }
    }
}
