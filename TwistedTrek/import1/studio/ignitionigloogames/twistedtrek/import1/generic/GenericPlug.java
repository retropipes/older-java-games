/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

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
	public String getIdentifier4() {
		return this.letter + " Key";
	}

	@Override
	public String getPluralName() {
		return this.letter + " Plugs";
	}

	@Override
	public byte getGroupID() {
		return (byte) 10;
	}

	@Override
	public byte getObjectID() {
		switch (this.letter) {
			case 'A':
				return (byte) 1;
			case 'B':
				return (byte) 2;
			case 'C':
				return (byte) 3;
			case 'D':
				return (byte) 4;
			case 'E':
				return (byte) 5;
			case 'F':
				return (byte) 6;
			case 'G':
				return (byte) 7;
			case 'H':
				return (byte) 8;
			case 'I':
				return (byte) 9;
			case 'J':
				return (byte) 10;
			case 'K':
				return (byte) 11;
			case 'L':
				return (byte) 12;
			case 'M':
				return (byte) 13;
			case 'N':
				return (byte) 14;
			case 'O':
				return (byte) 15;
			case 'P':
				return (byte) 16;
			case 'Q':
				return (byte) 17;
			case 'R':
				return (byte) 18;
			case 'S':
				return (byte) 19;
			case 'T':
				return (byte) 20;
			case 'U':
				return (byte) 21;
			case 'V':
				return (byte) 22;
			case 'W':
				return (byte) 23;
			case 'X':
				return (byte) 24;
			case 'Y':
				return (byte) 25;
			case 'Z':
				return (byte) 26;
			default:
				return (byte) 0;
		}
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
		return this.letter + " Plugs open " + this.letter + " Ports, and can be used infinitely many times.";
	}
}