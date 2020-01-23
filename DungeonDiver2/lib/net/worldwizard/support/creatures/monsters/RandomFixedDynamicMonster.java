package net.worldwizard.support.creatures.monsters;

public class RandomFixedDynamicMonster extends RandomFixedBaseMonster {
    // Constructors
    RandomFixedDynamicMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return true;
    }
}
