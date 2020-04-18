/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericCharacter;
import com.puttysoftware.mazer5d.compatibility.maze.MazeModel;
import com.puttysoftware.mazer5d.editor.MazeEditor;

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
        final MazeEditor me = Mazer5D.getBagOStuff().getEditor();
        me.setPlayerLocation();
    }

    @Override
    public void editorGenerateHook(final int x, final int y, final int z) {
        final MazeEditor me = Mazer5D.getBagOStuff().getEditor();
        me.setPlayerLocation(x, y, z);
    }

    @Override
    public String getDescription() {
        return "This is you - the Player.";
    }

    // Random Generation Rules
    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public int getMinimumRequiredQuantity(final MazeModel maze) {
        return 1;
    }

    @Override
    public int getMaximumRequiredQuantity(final MazeModel maze) {
        return 1;
    }
}