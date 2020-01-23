package net.worldwizard.support.creatures.monsters;

public abstract class DefiniteFixedBaseMonster extends DefiniteBaseMonster {
    // Constructors
    DefiniteFixedBaseMonster() {
        super();
    }

    @Override
    public boolean scales() {
        return false;
    }
}
