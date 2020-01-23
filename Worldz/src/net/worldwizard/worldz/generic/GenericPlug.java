/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

public abstract class GenericPlug extends GenericInfiniteKey {
    // Fields
    private char letter;

    protected GenericPlug(final char newLetter) {
        super();
        this.letter = Character.toUpperCase(newLetter);
    }

    @Override
    public GenericPlug clone() {
        final GenericPlug copy = (GenericPlug) super.clone();
        copy.letter = this.letter;
        return copy;
    }

    @Override
    public String getName() {
        return this.letter + " Plug";
    }

    @Override
    public String getPluralName() {
        return this.letter + " Plugs";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_LETTER_KEY);
        this.type.set(TypeConstants.TYPE_INFINITE_KEY);
        this.type.set(TypeConstants.TYPE_KEY);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public String getDescription() {
        return this.letter + " Plugs open " + this.letter
                + " Ports, and can be used infinitely many times.";
    }
}