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

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import com.puttysoftware.diane.gui.CommonDialogs;
import com.puttysoftware.fantastlereboot.assets.SoundGroup;
import com.puttysoftware.fantastlereboot.assets.SoundIndex;
import com.puttysoftware.fantastlereboot.battle.Battle;
import com.puttysoftware.fantastlereboot.battle.map.MapBattleLogic;
import com.puttysoftware.fantastlereboot.game.Game;
import com.puttysoftware.fantastlereboot.gui.AboutDialog;
import com.puttysoftware.fantastlereboot.gui.GUIManager;
import com.puttysoftware.fantastlereboot.gui.MenuManager;
import com.puttysoftware.fantastlereboot.gui.Prefs;
import com.puttysoftware.fantastlereboot.items.Shop;
import com.puttysoftware.fantastlereboot.items.ShopTypes;
import com.puttysoftware.fantastlereboot.items.combat.CombatItemList;
import com.puttysoftware.fantastlereboot.loaders.SoundPlayer;
import com.puttysoftware.updater.BrowserLauncher;
import com.puttysoftware.updater.ProductData;
import com.puttysoftware.updater.UpdateCheckResults;

public class BagOStuff {
  // Fields
  private AboutDialog about;
  private GUIManager guiMgr;
  private final CombatItemList combatItems;
  private Shop weapons, armor, healer, bank, regenerator, spells, items, socks,
      enhancements, faiths;
  private MapBattleLogic mapTurnBattle;
  private static final String UPDATE_SITE = "https://autoupdate.puttysoftware.com/fantastle-reboot/";
  private static final String NEW_VERSION_SITE = "https://puttysoftware.github.com/fantastle-reboot/";
  private static ProductData pd;
  private static final int VERSION_MAJOR = 0;
  private static final int VERSION_MINOR = 5;
  private static final int VERSION_BUGFIX = 0;
  private static final int VERSION_CODE = ProductData.CODE_BETA;
  private static final int VERSION_PRERELEASE = 0;

  // Constructors
  public BagOStuff() {
    this.combatItems = new CombatItemList();
  }

  // Methods
  void postConstruct() {
    try {
      pd = new ProductData(BagOStuff.UPDATE_SITE, BagOStuff.NEW_VERSION_SITE,
          BagOStuff.VERSION_MAJOR, BagOStuff.VERSION_MINOR,
          BagOStuff.VERSION_BUGFIX, BagOStuff.VERSION_CODE,
          BagOStuff.VERSION_PRERELEASE);
    } catch (MalformedURLException e) {
      FantastleReboot.exception(e);
    }
    this.about = new AboutDialog(BagOStuff.getVersionString());
    this.guiMgr = new GUIManager();
    this.mapTurnBattle = new MapBattleLogic();
    this.weapons = new Shop(ShopTypes.WEAPONS);
    this.armor = new Shop(ShopTypes.ARMOR);
    this.healer = new Shop(ShopTypes.HEALER);
    this.bank = new Shop(ShopTypes.BANK);
    this.regenerator = new Shop(ShopTypes.REGENERATOR);
    this.spells = new Shop(ShopTypes.SPELLS);
    this.items = new Shop(ShopTypes.ITEMS);
    this.socks = new Shop(ShopTypes.SOCKS);
    this.enhancements = new Shop(ShopTypes.ENHANCEMENTS);
    this.faiths = new Shop(ShopTypes.FAITH_POWERS);
  }

  public MenuManager getMenuManager() {
    return FantastleReboot.getMenuManager();
  }

  public GUIManager getGUIManager() {
    return this.guiMgr;
  }

  public void resetPreferences() {
    Prefs.resetPrefs();
  }

  public AboutDialog getAboutDialog() {
    return this.about;
  }

  public Shop getShop(final int shopType) {
    switch (shopType) {
    case ShopTypes.ARMOR:
      return this.armor;
    case ShopTypes.BANK:
      return this.bank;
    case ShopTypes.ENHANCEMENTS:
      return this.enhancements;
    case ShopTypes.FAITH_POWERS:
      return this.faiths;
    case ShopTypes.HEALER:
      return this.healer;
    case ShopTypes.ITEMS:
      return this.items;
    case ShopTypes.REGENERATOR:
      return this.regenerator;
    case ShopTypes.SOCKS:
      return this.socks;
    case ShopTypes.SPELLS:
      return this.spells;
    case ShopTypes.WEAPONS:
      return this.weapons;
    default:
      // Invalid shop type
      return null;
    }
  }

  public Shop getArmor() {
    return this.armor;
  }

  public Shop getBank() {
    return this.bank;
  }

  public Shop getHealer() {
    return this.healer;
  }

  public Shop getItems() {
    return this.items;
  }

  public Shop getRegenerator() {
    return this.regenerator;
  }

  public Shop getSpells() {
    return this.spells;
  }

  public Shop getWeapons() {
    return this.weapons;
  }

  public Battle getBattle() {
    return this.mapTurnBattle;
  }

  public void playLogoSound() {
    SoundPlayer.playSound(SoundIndex.LOGO, SoundGroup.USER_INTERFACE);
  }

  public static void checkForUpdates(final boolean manual) {
    try {
      UpdateCheckResults results = Prefs.checkForUpdates(manual, BagOStuff.pd);
      if (results.hasUpdate()) {
        int resp = CommonDialogs.showConfirmDialog(
            "An update is available. Do you want to download it?",
            FantastleReboot.PROGRAM_NAME);
        if (resp == CommonDialogs.YES_OPTION) {
          BrowserLauncher.openURL(BagOStuff.NEW_VERSION_SITE);
          System.exit(0);
        }
      } else {
        if (manual) {
          CommonDialogs.showDialog("No updates are available.");
        }
      }
    } catch (UnknownHostException uhe) {
      if (manual) {
        CommonDialogs
            .showDialog("The check for updates could not be completed,\n"
                + "because you are not connected to the Internet.");
      }
    } catch (Throwable t) {
      FantastleReboot.silentlyLog(t);
      if (manual) {
        CommonDialogs
            .showDialog("An error occurred while checking for updates.\n"
                + "The details have been recorded.");
      }
    }
  }

  private static String getVersionString() {
    return BagOStuff.pd.getVersionString();
  }

  public void showMessage(final String msg) {
    if (Modes.inGame()) {
      Game.setStatusMessage(msg);
    } else if (Modes.inBattle()) {
      this.getBattle().setStatusMessage(msg);
    } else {
      CommonDialogs.showDialog(msg);
    }
  }

  public CombatItemList getCombatItems() {
    return this.combatItems;
  }
}
