package net.worldwizard.support.creatures.monsters;

public class DefiniteFixedStaticMonster extends DefiniteFixedBaseMonster {
    // Constructors
    DefiniteFixedStaticMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return false;
    }
}
