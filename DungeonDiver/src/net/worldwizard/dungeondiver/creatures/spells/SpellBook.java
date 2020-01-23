package net.worldwizard.dungeondiver.creatures.spells;

public abstract class SpellBook {
    // Fields
    protected Spell[] spells;

    // Constructor
    protected SpellBook(final int numSpells) {
        this.spells = new Spell[numSpells];
        this.defineSpells();
    }

    protected abstract void defineSpells();

    public abstract int getID();

    public final Spell getSpellByID(final int ID) {
        return this.spells[ID];
    }

    public final Spell getSpellByName(final String name) {
        int x;
        for (x = 0; x < this.spells.length; x++) {
            final String currName = this.spells[x].getEffect().getName();
            if (currName.equals(name)) {
                // Found it
                return this.spells[x];
            }
        }
        // Didn't find it
        return null;
    }

    public final String[] getAllSpellNames() {
        int x;
        final String[] names = new String[this.spells.length];
        for (x = 0; x < this.spells.length; x++) {
            names[x] = this.spells[x].getEffect().getName();
        }
        return names;
    }

    public final String[] getAllSpellNamesWithCosts() {
        int x;
        final String[] names = new String[this.spells.length];
        for (x = 0; x < this.spells.length; x++) {
            final String name = this.spells[x].getEffect().getName();
            final int cost = this.spells[x].getCost();
            final String costStr = Integer.toString(cost);
            names[x] = name + " (" + costStr + " MP)";
        }
        return names;
    }
}
