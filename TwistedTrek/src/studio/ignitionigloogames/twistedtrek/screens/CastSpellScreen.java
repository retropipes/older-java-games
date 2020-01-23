/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.screens;

import studio.ignitionigloogames.twistedtrek.Creature;
import studio.ignitionigloogames.twistedtrek.Spell;

public class CastSpellScreen extends TargetBasedScreen {
    private final Spell spell;

    public CastSpellScreen(final Creature newPlayer, final String newCaption, final int sx, final int sy,
	    final Spell newSpell) {
	super(newPlayer, newCaption, sx, sy);
	this.spell = newSpell;
    }

    @Override
    public void selectWorldCoordinate(final int nx, final int ny, final int screenX, final int screenY) {
	this.player.castSpell(this.spell, nx, ny);
    }
}
