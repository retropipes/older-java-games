/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericInfiniteKey;

public class Tablet extends GenericInfiniteKey {
    // Constructors
    public Tablet() {
	super();
    }

    @Override
    public String getName() {
	return "Tablet";
    }

    @Override
    public String getPluralName() {
	return "Tablets";
    }

    @Override
    public byte getObjectID() {
	return (byte) 27;
    }

    @Override
    public String getDescription() {
	return "Tablets are used to fill Tablet Slots, and make them disappear. Tablets can be used infinitely many times.";
    }
}