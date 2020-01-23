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
package com.puttysoftware.fantastlereboot;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.puttysoftware.diane.gui.MainWindow;
import com.puttysoftware.fantastlereboot.assets.MusicGroup;
import com.puttysoftware.fantastlereboot.assets.MusicIndex;
import com.puttysoftware.fantastlereboot.assets.UserInterfaceImageIndex;
import com.puttysoftware.fantastlereboot.loaders.AvatarImageLoader;
import com.puttysoftware.fantastlereboot.loaders.BossImageLoader;
import com.puttysoftware.fantastlereboot.loaders.EffectImageLoader;
import com.puttysoftware.fantastlereboot.loaders.MusicPlayer;
import com.puttysoftware.fantastlereboot.loaders.ObjectImageLoader;
import com.puttysoftware.fantastlereboot.loaders.UserInterfaceImageLoader;
import com.puttysoftware.fantastlereboot.objectmodel.GameObjects;

class Loader extends Thread {
  private final MainWindow waitFrame;
  private final JLabel waitLabel;
  private final JProgressBar waitProgress;

  // Constructors
  public Loader() {
    // Set up wait frame
    this.waitFrame = MainWindow.getOutputFrame();
    this.waitFrame.setTitle("Loading...");
    this.waitLabel = new JLabel();
    this.waitProgress = new JProgressBar();
    this.waitProgress.setMinimum(0);
    this.waitProgress.setMaximum(100);
    this.waitProgress.setValue(0);
    final JPanel content = new JPanel();
    content.setLayout(new GridBagLayout());
    final GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    content.add(this.waitLabel, c);
    c.gridy = 1;
    content.add(this.waitProgress, c);
    this.waitFrame.attachContent(content);
  }

  @Override
  public void run() {
    try {
      // Enter Wait Mode
      this.enterWaitMode();
      // Get music going
      MusicPlayer.playMusic(MusicIndex.TITLE, MusicGroup.USER_INTERFACE);
      // Cache UI images
      UserInterfaceImageLoader.cacheAll();
      this.updateWaitProgress(17);
      // Cache Boss images
      BossImageLoader.cacheAll();
      this.updateWaitProgress(33);
      // Cache Effect images
      EffectImageLoader.cacheAll();
      this.updateWaitProgress(50);
      // Cache Avatar images
      AvatarImageLoader.cacheAll();
      this.updateWaitProgress(67);
      // Cache Object images
      ObjectImageLoader.cacheAll();
      this.updateWaitProgress(83);
      // Final tasks
      final BagOStuff bag = FantastleReboot.getBagOStuff();
      GameObjects.initializeObjects();
      bag.postConstruct();
      FantastleReboot.doLateOSIntegration();
      this.updateWaitProgress(100);
      // Exit Wait Mode
      bag.playLogoSound();
      bag.getGUIManager().showGUI();
    } catch (final Throwable t) {
      FantastleReboot.exception(t);
    }
  }

  private void enterWaitMode() {
    UserInterfaceImageLoader.preInit();
    this.waitLabel.setIcon(
        UserInterfaceImageLoader.load(UserInterfaceImageIndex.LOADING));
    this.waitFrame.pack();
  }

  private void updateWaitProgress(final int value) {
    this.waitProgress.setValue(value);
  }
}
