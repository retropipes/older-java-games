package net.worldwizard.support.creatures.monsters;

public class RandomFixedStaticMonster extends RandomFixedBaseMonster {
    // Constructors
    RandomFixedStaticMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return false;
    }
}
