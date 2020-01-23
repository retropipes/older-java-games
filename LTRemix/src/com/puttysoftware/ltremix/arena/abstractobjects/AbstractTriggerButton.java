/*  LTRemix: An Arena-Solving Game
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ltremix.arena.abstractobjects;

import com.puttysoftware.ltremix.Application;
import com.puttysoftware.ltremix.LTRemix;
import com.puttysoftware.ltremix.arena.objects.Empty;
import com.puttysoftware.ltremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ltremix.resourcemanagers.SoundManager;
import com.puttysoftware.ltremix.utilities.TypeConstants;

public abstract class AbstractTriggerButton extends AbstractButton {
    // Constructors
    protected AbstractTriggerButton(final AbstractTriggerButtonDoor tbd,
            final boolean isUniversal) {
        super(tbd, isUniversal);
        this.type.set(TypeConstants.TYPE_TRIGGER_BUTTON);
    }

    @Override
    public boolean pushIntoAction(final AbstractMovableObject pushed,
            final int x, final int y, final int z) {
        final Application app = LTRemix.getApplication();
        if (this.isUniversal() || pushed.getMaterial() == this.getMaterial()) {
            SoundManager.playSound(SoundConstants.SOUND_BUTTON);
            if (this.isTriggered()) {
                // Close door at location
                app.getGameManager().morph(this.getButtonDoor(),
                        this.getDoorX(), this.getDoorY(), z,
                        this.getPrimaryLayer());
                SoundManager.playSound(SoundConstants.SOUND_DOOR_CLOSES);
                this.setTriggered(false);
            } else {
                // Open door at location
                app.getGameManager().morph(new Empty(), this.getDoorX(),
                        this.getDoorY(), z, this.getPrimaryLayer());
                SoundManager.playSound(SoundConstants.SOUND_DOOR_OPENS);
                this.setTriggered(true);
            }
        }
        return true;
    }

    @Override
    public void pushOutAction(final AbstractMovableObject pushed, final int x,
            final int y, final int z) {
        // Do nothing
    }
}
