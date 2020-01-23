/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.monsters;

public class MonsterFactory {
    private MonsterFactory() {
        // Do nothing
    }

    public static BaseMonster getNewMonsterInstance() {
        return new BothRandomScalingStaticMonster();
    }

    public static BaseMonster getBossMonsterInstance() {
        return new BossMonster();
    }
}
