/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.battle;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;

import com.puttysoftware.dungeondiver3.support.map.objects.BattleCharacter;

class BattleEffects {
    // Fields
    private Container effectsPane;
    private JLabel[] effectLabels;

    // Constructors
    BattleEffects() {
        // Do nothing
    }

    // Methods
    Container getEffectsPane() {
        if (this.effectsPane == null) {
            this.effectsPane = new Container();
        }
        return this.effectsPane;
    }

    void updateEffects(final BattleCharacter bc) {
        final int count = bc.getTemplate().getActiveEffectCount();
        if (count > 0) {
            this.setUpGUI(count);
            final String[] es = bc.getTemplate().getCompleteEffectString();
            for (int x = 0; x < count; x++) {
                this.effectLabels[x].setText(es[x]);
            }
        }
    }

    private void setUpGUI(final int count) {
        this.effectsPane = this.getEffectsPane();
        this.effectsPane.removeAll();
        this.effectsPane.setLayout(new GridLayout(count, 1));
        this.effectLabels = new JLabel[count];
        for (int x = 0; x < count; x++) {
            this.effectLabels[x] = new JLabel(" ");
        }
    }
}
