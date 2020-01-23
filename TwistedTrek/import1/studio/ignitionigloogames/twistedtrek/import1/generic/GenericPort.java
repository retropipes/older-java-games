/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;

public abstract class GenericPort extends GenericInfiniteLock {
    // Fields
    private char letter;

    protected GenericPort(final GenericPlug mgk, final char newLetter) {
	super(mgk);
	this.letter = Character.toUpperCase(newLetter);
    }

    @Override
    public GenericPort clone() {
	final GenericPort copy = (GenericPort) super.clone();
	copy.letter = this.letter;
	return copy;
    }

    @Override
    public byte getGroupID() {
	return (byte) 11;
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	String fill = "";
	if (this.isLetterVowel()) {
	    fill = "an";
	} else {
	    fill = "a";
	}
	Messager.showMessage("You need " + fill + " " + this.letter + " plug");
	// Play move failed sound, if it's enabled
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveFailedSound();
	}
    }

    @Override
    public String getName() {
	return this.letter + " Port";
    }

    @Override
    public String getIdentifier4() {
	return this.letter + " Lock";
    }

    @Override
    public String getPluralName() {
	return this.letter + " Ports";
    }

    private boolean isLetterVowel() {
	if (this.letter == 'A' || this.letter == 'E' || this.letter == 'I' || this.letter == 'O'
		|| this.letter == 'U') {
	    return true;
	} else {
	    return false;
	}
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
	this.type.set(TypeConstants.TYPE_LETTER_LOCK);
	this.type.set(TypeConstants.TYPE_INFINITE_LOCK);
	this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    public String getDescription() {
	String fill;
	if (this.isLetterVowel()) {
	    fill = "an";
	} else {
	    fill = "a";
	}
	return this.letter + " Ports require " + fill + " " + this.letter + " Plug to open.";
    }
}