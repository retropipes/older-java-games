package net.worldwizard.support.creatures.monsters;

public class DefiniteFixedDynamicMonster extends DefiniteFixedBaseMonster {
    // Constructors
    DefiniteFixedDynamicMonster() {
        super();
    }

    @Override
    public boolean dynamic() {
        return true;
    }
}
