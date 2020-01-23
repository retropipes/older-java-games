/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.battle;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;

import com.puttysoftware.riskyrescue.map.objects.BattleCharacter;

class BattleEffects {
    // Fields
    private Container effectsPane;
    private JLabel effectLabel;

    // Constructors
    BattleEffects() {
        this.setUpGUI();
    }

    // Methods
    Container getEffectsPane() {
        if (this.effectsPane == null) {
            this.effectsPane = new Container();
        }
        return this.effectsPane;
    }

    void updateEffects(final BattleCharacter bc) {
        if (bc != null) {
            final int count = bc.getTemplate().getActiveEffectCount();
            final String[] es = bc.getTemplate().getCompleteEffectString();
            for (int x = 0; x < count; x++) {
                this.effectLabel.setText(es[x]);
            }
        }
    }

    private void setUpGUI() {
        this.effectsPane = this.getEffectsPane();
        this.effectsPane.removeAll();
        this.effectsPane.setLayout(new GridLayout(1, 1));
        this.effectLabel = new JLabel(" ");
    }
}
