/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek;

public abstract class LevelUpOption {
    private final String name;

    public String name() {
        return this.name;
    }

    public LevelUpOption(final String newName) {
        this.name = newName;
    }

    public abstract void invoke(Creature creature);
}
