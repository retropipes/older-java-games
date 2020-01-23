package net.worldwizard.support.creatures.monsters;

public class RandomScalingStaticMonster extends RandomScalingBaseMonster {
    // Constructors
    RandomScalingStaticMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return false;
    }
}
