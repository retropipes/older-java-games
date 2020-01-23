/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.editor.WorldEditor;
import net.worldwizard.worldz.generic.GenericCharacter;
import net.worldwizard.worldz.world.World;

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
        final WorldEditor me = Worldz.getApplication().getEditor();
        me.setPlayerLocation();
    }

    @Override
    public void editorGenerateHook(final int x, final int y, final int z) {
        final WorldEditor me = Worldz.getApplication().getEditor();
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
    public int getMinimumRequiredQuantity(final World world) {
        return 1;
    }

    @Override
    public int getMaximumRequiredQuantity(final World world) {
        return 1;
    }
}