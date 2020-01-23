/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericCharacter;

public class Player extends GenericCharacter {
    // Constructors
    public Player() {
	super();
    }

    @Override
    public String getName() {
	return "Player";
    }

    @Override
    public String getPluralName() {
	return "Players";
    }

    @Override
    public void editorPlaceHook() {
	final MazeEditor me = Import1.getApplication().getEditor();
	me.setPlayerLocation();
    }

    @Override
    public byte getObjectID() {
	return (byte) 1;
    }

    @Override
    public String getDescription() {
	return "This is you - the Player.";
    }
}