package net.worldwizard.support.creatures.monsters;

public abstract class RandomFixedBaseMonster extends RandomBaseMonster {
    // Constructors
    RandomFixedBaseMonster() {
        super();
    }

    @Override
    public boolean scales() {
        return false;
    }
}
