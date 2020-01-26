/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.creatures.PartyMember;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.resourcemanagers.SoundManager;
import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericCharacter extends WorldObject {
    // Fields
    private static final int SHOT_SELF_NORMAL_DAMAGE = 5;
    private static final int SHOT_SELF_SPECIAL_DAMAGE = 10;

    // Constructors
    protected GenericCharacter() {
        super(false);
    }

    // Methods
    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CHARACTER);
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        // Shot self
        Messager.showMessage("Ouch, you shot yourself!");
        final PartyMember leader = PartyManager.getParty().getLeader();
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_PLAIN) {
            leader.doDamage(GenericCharacter.SHOT_SELF_NORMAL_DAMAGE);
        } else {
            leader.doDamage(GenericCharacter.SHOT_SELF_SPECIAL_DAMAGE);
        }
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            SoundManager.playSound("lava");
        }
        return false;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return WorldObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
