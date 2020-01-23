/*  Fantastle: A World-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Any questions should be directed to the author via email at: fantastle@worldwizard.net
 */
package com.puttysoftware.fantastlereboot.effects;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.puttysoftware.diane.utilties.DirectionResolver;
import com.puttysoftware.fantastlereboot.game.Game;

public class EffectManager {
  // Fields
  private static Effect[] activeEffects;
  private static JPanel activeEffectMessageJPanel;
  private static JLabel[] activeEffectMessages;
  private static int[] activeEffectIndices;
  private static final int NUM_EFFECTS = 9;
  private static final int MAX_ACTIVE_EFFECTS = 3;

  // Constructors
  private EffectManager() {
    // Do nothing
  }

  public static void initialize() {
    EffectManager.activeEffects = new Effect[EffectManager.NUM_EFFECTS];
    EffectManager.activeEffects[EffectConstants.EFFECT_ROTATED_CLOCKWISE] = new RotatedCW(
        0);
    EffectManager.activeEffects[EffectConstants.EFFECT_ROTATED_COUNTERCLOCKWISE] = new RotatedCCW(
        0);
    EffectManager.activeEffects[EffectConstants.EFFECT_U_TURNED] = new UTurned(
        0);
    EffectManager.activeEffects[EffectConstants.EFFECT_CONFUSED] = new Confused(
        0);
    EffectManager.activeEffects[EffectConstants.EFFECT_DIZZY] = new Dizzy(0);
    EffectManager.activeEffects[EffectConstants.EFFECT_DRUNK] = new Drunk(0);
    EffectManager.activeEffects[EffectConstants.EFFECT_STICKY] = new Sticky(0);
    EffectManager.activeEffects[EffectConstants.EFFECT_POWER_GATHER] = new PowerGather(
        0);
    EffectManager.activeEffects[EffectConstants.EFFECT_POWER_WITHER] = new PowerWither(
        0);
    // Create GUI
    EffectManager.activeEffectMessageJPanel = new JPanel();
    EffectManager.activeEffectMessages = new JLabel[EffectManager.MAX_ACTIVE_EFFECTS];
    EffectManager.activeEffectMessageJPanel
        .setLayout(new GridLayout(EffectManager.MAX_ACTIVE_EFFECTS, 1));
    for (int z = 0; z < EffectManager.MAX_ACTIVE_EFFECTS; z++) {
      EffectManager.activeEffectMessages[z] = new JLabel("");
      EffectManager.activeEffectMessageJPanel
          .add(EffectManager.activeEffectMessages[z]);
    }
    // Set up miscellaneous things
    EffectManager.activeEffectIndices = new int[EffectManager.MAX_ACTIVE_EFFECTS];
    for (int z = 0; z < EffectManager.MAX_ACTIVE_EFFECTS; z++) {
      EffectManager.activeEffectIndices[z] = -1;
    }
  }

  // Methods
  public static JPanel getEffectMessageJPanel() {
    return EffectManager.activeEffectMessageJPanel;
  }

  public static void decayEffects() {
    for (int x = 0; x < EffectManager.NUM_EFFECTS; x++) {
      if (EffectManager.activeEffects[x].isActive()) {
        EffectManager.activeEffects[x].useEffect();
        // Update effect grid
        EffectManager.updateGridEntry(x);
        if (!EffectManager.activeEffects[x].isActive()) {
          // Clear effect grid
          EffectManager.clearGridEntry(x);
          Game.keepNextMessage();
          Game.setStatusMessage("You feel normal again.");
        }
      }
    }
  }

  private static void clearGridEntry(final int effectID) {
    final int index = EffectManager.lookupEffect(effectID);
    if (index != -1) {
      EffectManager.clearGridEntryText(index);
      // Compact grid
      for (int z = index; z < EffectManager.MAX_ACTIVE_EFFECTS - 1; z++) {
        EffectManager.activeEffectMessages[z]
            .setText(EffectManager.activeEffectMessages[z + 1].getText());
        EffectManager.activeEffectIndices[z] = EffectManager.activeEffectIndices[z
            + 1];
      }
      // Clear last entry
      EffectManager.clearGridEntryText(EffectManager.MAX_ACTIVE_EFFECTS - 1);
    }
  }

  private static void clearGridEntryText(final int index) {
    EffectManager.activeEffectIndices[index] = -1;
    EffectManager.activeEffectMessages[index].setText("");
  }

  private static void updateGridEntry(final int effectID) {
    final int index = EffectManager.lookupEffect(effectID);
    if (index != -1) {
      final String effectString = EffectManager.activeEffects[effectID]
          .getEffectString();
      EffectManager.activeEffectMessages[index].setText(effectString);
    }
  }

  public static void deactivateAllEffects() {
    for (int x = 0; x < EffectManager.NUM_EFFECTS; x++) {
      EffectManager.activeEffects[x].deactivateEffect();
      EffectManager.clearGridEntry(x);
    }
  }

  public static boolean isEffectActive(final int effectID) {
    return EffectManager.activeEffects[effectID].isActive();
  }

  private static int lookupEffect(final int effectID) {
    for (int z = 0; z < EffectManager.MAX_ACTIVE_EFFECTS; z++) {
      if (EffectManager.activeEffectIndices[z] == effectID) {
        return z;
      }
    }
    return -1;
  }

  public static int[] doEffects(final int x, final int y) {
    int[] res = new int[] { x, y };
    int dir = DirectionResolver.resolve(x, y);
    for (int z = 0; z < EffectManager.NUM_EFFECTS; z++) {
      if (EffectManager.activeEffects[z].isActive()) {
        dir = EffectManager.activeEffects[z].modifyMove1(dir);
        res = DirectionResolver.unresolve(dir);
        res = EffectManager.activeEffects[z].modifyMove2(res);
      }
    }
    return res;
  }
}