/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.editor.MazeEditor;
import com.puttysoftware.rulemazer.generic.GenericCharacter;
import com.puttysoftware.rulemazer.maze.Maze;

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
        final MazeEditor me = Main.getApplication().getEditor();
        me.setPlayerLocation();
    }

    @Override
    public void editorGenerateHook(final int x, final int y, final int z) {
        final MazeEditor me = Main.getApplication().getEditor();
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
    public int getMinimumRequiredQuantity(final Maze maze) {
        return 1;
    }

    @Override
    public int getMaximumRequiredQuantity(final Maze maze) {
        return 1;
    }
}