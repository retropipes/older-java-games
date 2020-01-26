/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.battle.map;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;

import com.puttysoftware.dungeondiver4.dungeon.objects.BattleCharacter;

class MapBattleEffects {
    // Fields
    private Container effectsPane;
    private JLabel[] effectLabels;

    // Constructors
    MapBattleEffects() {
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
            final String[] es = bc.getTemplate().getCompleteEffectStringArray();
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
