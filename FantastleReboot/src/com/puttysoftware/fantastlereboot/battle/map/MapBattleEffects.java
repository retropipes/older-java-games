/*  FantastleReboot: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlereboot.battle.map;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.puttysoftware.fantastlereboot.objects.temporary.BattleCharacter;

public class MapBattleEffects {
  // Fields
  private JPanel effectsPane;
  private JLabel[] effectLabels;

  // Constructors
  public MapBattleEffects() {
    // Do nothing
  }

  // Methods
  public JPanel getEffectsPane() {
    if (this.effectsPane == null) {
      this.effectsPane = new JPanel();
    }
    return this.effectsPane;
  }

  public void updateEffects(final BattleCharacter bc) {
    final int count = bc.getCreature().getActiveEffectCount();
    if (count > 0) {
      this.setUpGUI(count);
      final String[] es = bc.getCreature().getCompleteEffectStringArray();
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
