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
package com.puttysoftware.fantastlereboot.gui;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.time.Clock;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;

import com.puttysoftware.diane.gui.CommonDialogs;
import com.puttysoftware.diane.gui.MainWindow;
import com.puttysoftware.fantastlereboot.BagOStuff;
import com.puttysoftware.fantastlereboot.FantastleReboot;
import com.puttysoftware.fantastlereboot.Modes;
import com.puttysoftware.fantastlereboot.assets.MusicGroup;
import com.puttysoftware.fantastlereboot.assets.SoundGroup;
import com.puttysoftware.fantastlereboot.files.CommonPaths;
import com.puttysoftware.fantastlereboot.files.versions.PrefsVersionException;
import com.puttysoftware.fantastlereboot.files.versions.PrefsVersions;
import com.puttysoftware.fantastlereboot.objectmodel.FantastleObjectModel;
import com.puttysoftware.fantastlereboot.objects.Tile;
import com.puttysoftware.randomrange.RandomLongRange;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.updater.ProductData;
import com.puttysoftware.updater.UpdateCheckResults;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class Prefs {
  // Fields
  private static MainWindow prefFrame;
  private static JTabbedPane prefTabPane;
  private static JPanel mainPrefPane, twisterPane;
  private static JButton prefsOK, prefsCancel;
  private static JButton prefsExport, prefsImport;
  private static JCheckBox[] sounds = new JCheckBox[Prefs.SOUNDS_LENGTH];
  private static JCheckBox[] music = new JCheckBox[Prefs.MUSIC_LENGTH];
  private static JCheckBox checkUpdatesStartup;
  private static JCheckBox moveOneAtATime;
  private static JComboBox<String> editorFillChoices;
  private static String[] editorFillChoiceArray;
  private static JComboBox<String> difficultyChoices;
  private static JComboBox<String> updateCheckInterval;
  private static String[] updateCheckIntervalValues;
  private static JComboBox<String> viewingWindowChoices;
  private static JComboBox<String> editorWindowChoices;
  private static JRadioButton generatorConstrainedRandom;
  private static JRadioButton generatorTwister;
  private static JSlider randomRoomSize;
  private static JSlider randomHallSize;
  private static EventHandler handler;
  private static final PrefsFileManager fileMgr = new PrefsFileManager();
  private static final ExportImportManager eiMgr = new ExportImportManager();
  private static int editorFill;
  private static boolean checkUpdatesStartupEnabled;
  private static boolean moveOneAtATimeEnabled;
  private static int difficultySetting = Prefs.DEFAULT_DIFFICULTY;
  private static int viewingWindowIndex;
  private static int editorWindowIndex;
  private static int updateCheckIntervalIndex;
  private static int randomRoomSizeIndex;
  private static int randomHallSizeIndex;
  private static long lastUpdateCheck;
  private static int worldGenerator;
  private static int cachedMajorVersion;
  private static int cachedMinorVersion;
  private static int cachedBugfixVersion;
  private static int cachedPrereleaseVersion;
  private static boolean cachedHasUpdate;
  private static boolean[] soundsEnabled = new boolean[Prefs.SOUNDS_LENGTH];
  private static boolean[] musicEnabled = new boolean[Prefs.MUSIC_LENGTH];
  private static boolean guiSetUp = false;
  private static final int[] VIEWING_WINDOW_SIZES = new int[] { 7, 9, 11, 13,
      15, 17, 19, 21, 23, 25 };
  private static final int MIN_ROOM_SIZE = 3;
  private static final int DEFAULT_ROOM_SIZE = 8;
  private static final int MAX_ROOM_SIZE = 15;
  private static final int MIN_HALL_SIZE = 3;
  private static final int DEFAULT_HALL_SIZE = 8;
  private static final int MAX_HALL_SIZE = 15;
  private static final int DEFAULT_UPDATE_CHECK_INTERVAL_INDEX = 2;
  private static final long[] UPDATE_CHECK_INTERVAL_VALUES = new long[] {
      86400L, 172800L, 604800L, 1209600L, 2592000L };
  private static final int[] MDM_MIN_TOP = new int[] { 5, 4, 3, 2, 1 };
  private static final int[] MDM_MIN_BOT = new int[] { 5, 5, 5, 5, 5 };
  private static final int[] MDM_MAX_TOP = new int[] { 5, 5, 5, 5, 5 };
  private static final int[] MDM_MAX_BOT = new int[] { 5, 4, 3, 2, 1 };
  private static final int[] EXP_MIN_TOP = new int[] { 9, 9, 9, 9, 9 };
  private static final int[] EXP_MIN_BOT = new int[] { 8, 7, 6, 5, 4 };
  private static final int[] EXP_MAX_TOP = new int[] { 9, 9, 9, 9, 9 };
  private static final int[] EXP_MAX_BOT = new int[] { 6, 5, 4, 3, 2 };
  private static final int[] GLD_MIN_TOP = new int[] { 23, 23, 23, 23, 23 };
  private static final int[] GLD_MIN_BOT = new int[] { 22, 20, 18, 16, 14 };
  private static final int[] GLD_MAX_TOP = new int[] { 23, 23, 23, 23, 23 };
  private static final int[] GLD_MAX_BOT = new int[] { 18, 16, 14, 12, 10 };
  private static final int DEFAULT_GAME_VIEW_SIZE_INDEX = 2;
  private static final int DEFAULT_EDITOR_VIEW_SIZE_INDEX = 2;
  private static final String[] VIEWING_WINDOW_SIZE_NAMES = new String[] {
      "Tiny", "Small", "Medium", "Large", "Huge", "Tiny HD", "Small HD",
      "Medium HD", "Large HD", "Huge HD" };
  private static final String[] DIFFICULTY_CHOICE_NAMES = new String[] {
      "Very Easy", "Easy", "Normal", "Hard", "Very Hard" };
  private static final int SOUNDS_ALL = 0;
  private static final int SOUNDS_UI = 1;
  private static final int SOUNDS_GAME = 2;
  private static final int SOUNDS_BATTLE = 3;
  private static final int SOUNDS_SHOP = 4;
  private static final int MUSIC_ALL = 0;
  private static final int MUSIC_UI = 1;
  private static final int MUSIC_GAME = 2;
  private static final int MUSIC_BATTLE = 3;
  private static final int MUSIC_SHOP = 4;
  private static final int GENERATOR_OBSOLETE = 0;
  private static final int GENERATOR_CONSTRAINED_RANDOM = 1;
  private static final int GENERATOR_TWISTER = 2;
  public static final int DIFFICULTY_VERY_EASY = 0;
  public static final int DIFFICULTY_EASY = 1;
  public static final int DIFFICULTY_NORMAL = 2;
  public static final int DIFFICULTY_HARD = 3;
  public static final int DIFFICULTY_VERY_HARD = 4;
  private static final int DEFAULT_DIFFICULTY = Prefs.DIFFICULTY_NORMAL;
  private static final int BATTLE_SPEED = 1000;
  private static final int MUSIC_LENGTH = 5;
  private static final int SOUNDS_LENGTH = 5;
  private static final int GRID_LENGTH = 8;
  private static final long DEFAULT_NEXT_UPDATE = -1L;
  private static final String DOC_TAG = "settings";

  // Constructors
  private Prefs() {
    super();
  }

  // Methods
  public static int getMonsterCount(final int pmCount) {
    final int diff = Prefs.getGameDifficulty();
    int min = pmCount * Prefs.MDM_MIN_TOP[diff] / Prefs.MDM_MIN_BOT[diff];
    if (min < 1) {
      min = 1;
    }
    int max = pmCount * Prefs.MDM_MAX_TOP[diff] / Prefs.MDM_MAX_BOT[diff];
    if (max < 1) {
      max = 1;
    }
    return RandomRange.generate(min, max);
  }

  public static int getPerfectBattleGold(final int battleGold) {
    final int diff = Prefs.getGameDifficulty();
    int min = battleGold * Prefs.GLD_MIN_TOP[diff] / Prefs.GLD_MIN_BOT[diff];
    if (min < 1) {
      min = 1;
    }
    int max = battleGold * Prefs.GLD_MAX_TOP[diff] / Prefs.GLD_MAX_BOT[diff];
    if (max < 1) {
      max = 1;
    }
    return RandomRange.generate(min, max);
  }

  public static long getPerfectBattleExp(final long battleExp) {
    final int diff = Prefs.getGameDifficulty();
    long min = battleExp * Prefs.EXP_MIN_TOP[diff] / Prefs.EXP_MIN_BOT[diff];
    if (min < 1) {
      min = 1;
    }
    long max = battleExp * Prefs.EXP_MAX_TOP[diff] / Prefs.EXP_MAX_BOT[diff];
    if (max < 1) {
      max = 1;
    }
    return new RandomLongRange(min, max).generate();
  }

  public static boolean isWorldGeneratorConstrainedRandom() {
    return Prefs.worldGenerator == Prefs.GENERATOR_OBSOLETE
        || Prefs.worldGenerator == Prefs.GENERATOR_CONSTRAINED_RANDOM;
  }

  public static boolean isWorldGeneratorTwister() {
    return Prefs.worldGenerator == Prefs.GENERATOR_TWISTER;
  }

  public static int getRandomRoomSize() {
    return Prefs.randomRoomSizeIndex;
  }

  public static int getRandomHallSize() {
    return Prefs.randomHallSizeIndex;
  }

  public static int getBattleSpeed() {
    return Prefs.BATTLE_SPEED;
  }

  public static int getGameDifficulty() {
    return Prefs.difficultySetting;
  }

  private static boolean useCache(final boolean manual) {
    if (!manual && !Prefs.checkUpdatesStartupEnabled) {
      return false;
    }
    if (Prefs.lastUpdateCheck == Prefs.DEFAULT_NEXT_UPDATE) {
      return true;
    }
    long nextUpdateCheck = Prefs.lastUpdateCheck
        + Prefs.UPDATE_CHECK_INTERVAL_VALUES[Prefs.updateCheckIntervalIndex];
    long stamp = Clock.systemUTC().instant().getEpochSecond();
    if (stamp >= nextUpdateCheck) {
      Prefs.lastUpdateCheck = stamp;
    }
    return stamp >= nextUpdateCheck;
  }

  public static UpdateCheckResults checkForUpdates(final boolean manual,
      final ProductData pd) throws IOException {
    UpdateCheckResults results;
    if (Prefs.useCache(manual)) {
      results = Prefs.getCachedResults();
    } else {
      results = pd.checkForUpdates();
      Prefs.cacheResults(results);
    }
    return results;
  }

  private static UpdateCheckResults getCachedResults() {
    return new UpdateCheckResults(Prefs.cachedHasUpdate,
        Prefs.cachedMajorVersion, Prefs.cachedMinorVersion,
        Prefs.cachedBugfixVersion, Prefs.cachedPrereleaseVersion);
  }

  private static void cacheResults(final UpdateCheckResults results) {
    Prefs.cachedMajorVersion = results.getMajorVersion();
    Prefs.cachedMinorVersion = results.getMinorVersion();
    Prefs.cachedBugfixVersion = results.getBugfixVersion();
    Prefs.cachedPrereleaseVersion = results.getPrereleaseVersion();
    Prefs.cachedHasUpdate = results.hasUpdate();
  }

  public static boolean oneMove() {
    return Prefs.moveOneAtATimeEnabled;
  }

  public static int getViewingWindowSize() {
    return Prefs.VIEWING_WINDOW_SIZES[Prefs.getViewingWindowSizeIndex()];
  }

  public static int getViewingWindowSizeIndex() {
    return Prefs.viewingWindowIndex;
  }

  public static int getEditorWindowSize() {
    return Prefs.VIEWING_WINDOW_SIZES[Prefs.getEditorWindowSizeIndex()];
  }

  public static int getEditorWindowSizeIndex() {
    return Prefs.editorWindowIndex;
  }

  public static boolean isSoundGroupEnabled(final SoundGroup group) {
    return Prefs.isSoundGroupEnabledImpl(group.ordinal());
  }

  private static boolean isSoundGroupEnabledImpl(final int snd) {
    if (!Prefs.soundsEnabled[Prefs.SOUNDS_ALL]) {
      return false;
    } else {
      return Prefs.soundsEnabled[snd];
    }
  }

  public static boolean isMusicGroupEnabled(final MusicGroup group) {
    return Prefs.isMusicGroupEnabledImpl(group.ordinal());
  }

  private static boolean isMusicGroupEnabledImpl(final int mus) {
    if (!Prefs.musicEnabled[Prefs.MUSIC_ALL]) {
      return false;
    } else {
      return Prefs.musicEnabled[mus];
    }
  }

  public static FantastleObjectModel getEditorDefaultFill() {
    return new Tile();
  }

  private static void defaultEnableSoundGroups() {
    for (int x = 0; x < Prefs.SOUNDS_LENGTH; x++) {
      Prefs.soundsEnabled[x] = true;
    }
  }

  private static void defaultEnableMusicGroups() {
    for (int x = 0; x < Prefs.MUSIC_LENGTH; x++) {
      Prefs.musicEnabled[x] = true;
    }
  }

  private static void setSoundGroupEnabledImpl(final int snd,
      final boolean status) {
    Prefs.soundsEnabled[snd] = status;
  }

  private static void setMusicGroupEnabled(final int mus,
      final boolean status) {
    Prefs.musicEnabled[mus] = status;
  }

  public static void showPrefs() {
    if (!Prefs.guiSetUp) {
      Prefs.setUpGUI();
      Prefs.guiSetUp = true;
    }
    Prefs.prefFrame = MainWindow.getOutputFrame();
    Prefs.prefFrame.setTitle("Preferences");
    Prefs.prefFrame.setDefaultButton(Prefs.prefsOK);
    Prefs.prefFrame.attachContent(Prefs.mainPrefPane);
    Prefs.prefFrame.addWindowListener(Prefs.handler);
    Prefs.prefFrame.pack();
    final BagOStuff app = FantastleReboot.getBagOStuff();
    Modes.setInPrefs();
    app.getMenuManager().setPrefMenus();
  }

  private static void hidePrefs() {
    if (!Prefs.guiSetUp) {
      Prefs.setUpGUI();
      Prefs.guiSetUp = true;
    }
    Prefs.prefFrame.setDefaultButton(null);
    Prefs.prefFrame.removeWindowListener(Prefs.handler);
    Modes.restore();
  }

  public static void writePrefs() {
    Prefs.fileMgr.writePreferencesFile();
  }

  public static void resetPrefs() {
    Prefs.fileMgr.deletePreferencesFile();
    Prefs.resetDefaultPrefs();
  }

  private static void loadPrefs() {
    if (!Prefs.guiSetUp) {
      Prefs.setUpGUI();
      Prefs.guiSetUp = true;
    }
    Prefs.editorFillChoices.setSelectedIndex(Prefs.editorFill);
    for (int x = 0; x < Prefs.SOUNDS_LENGTH; x++) {
      Prefs.sounds[x].setSelected(Prefs.isSoundGroupEnabledImpl(x));
    }
    for (int x = 0; x < Prefs.MUSIC_LENGTH; x++) {
      Prefs.music[x].setSelected(Prefs.isMusicGroupEnabledImpl(x));
    }
    Prefs.updateCheckInterval.setSelectedIndex(Prefs.updateCheckIntervalIndex);
    Prefs.checkUpdatesStartup.setSelected(Prefs.checkUpdatesStartupEnabled);
    Prefs.difficultyChoices.setSelectedIndex(Prefs.difficultySetting);
    Prefs.moveOneAtATime.setSelected(Prefs.moveOneAtATimeEnabled);
    Prefs.viewingWindowChoices.setSelectedIndex(Prefs.viewingWindowIndex);
    Prefs.editorWindowChoices.setSelectedIndex(Prefs.editorWindowIndex);
    if (Prefs.worldGenerator == Prefs.GENERATOR_CONSTRAINED_RANDOM) {
      Prefs.generatorConstrainedRandom.setSelected(true);
    } else if (Prefs.worldGenerator == Prefs.GENERATOR_TWISTER) {
      Prefs.generatorTwister.setSelected(true);
    } else {
      Prefs.generatorConstrainedRandom.setSelected(true);
    }
    Prefs.randomRoomSize.setValue(Prefs.randomRoomSizeIndex);
    Prefs.randomHallSize.setValue(Prefs.randomHallSizeIndex);
  }

  private static void savePrefs() {
    if (!Prefs.guiSetUp) {
      Prefs.setUpGUI();
      Prefs.guiSetUp = true;
    }
    Prefs.editorFill = Prefs.editorFillChoices.getSelectedIndex();
    for (int x = 0; x < Prefs.SOUNDS_LENGTH; x++) {
      Prefs.setSoundGroupEnabledImpl(x, Prefs.sounds[x].isSelected());
    }
    for (int x = 0; x < Prefs.MUSIC_LENGTH; x++) {
      Prefs.setMusicGroupEnabled(x, Prefs.music[x].isSelected());
    }
    Prefs.updateCheckIntervalIndex = Prefs.updateCheckInterval
        .getSelectedIndex();
    Prefs.checkUpdatesStartupEnabled = Prefs.checkUpdatesStartup.isSelected();
    Prefs.difficultySetting = Prefs.difficultyChoices.getSelectedIndex();
    Prefs.moveOneAtATimeEnabled = Prefs.moveOneAtATime.isSelected();
    Prefs.viewingWindowIndex = Prefs.viewingWindowChoices.getSelectedIndex();
    Prefs.editorWindowIndex = Prefs.editorWindowChoices.getSelectedIndex();
    if (Prefs.generatorConstrainedRandom.isSelected()) {
      Prefs.worldGenerator = Prefs.GENERATOR_CONSTRAINED_RANDOM;
    } else if (Prefs.generatorTwister.isSelected()) {
      Prefs.worldGenerator = Prefs.GENERATOR_TWISTER;
    } else {
      Prefs.worldGenerator = Prefs.GENERATOR_CONSTRAINED_RANDOM;
    }
    Prefs.randomRoomSizeIndex = Prefs.randomRoomSize.getValue();
    Prefs.randomHallSizeIndex = Prefs.randomHallSize.getValue();
  }

  public static void setDefaultPrefs() {
    if (!Prefs.fileMgr.readPreferencesFile()) {
      Prefs.resetDefaultPrefs();
    }
  }

  private static void resetDefaultPrefs() {
    if (!Prefs.guiSetUp) {
      Prefs.setUpGUI();
      Prefs.guiSetUp = true;
    }
    Prefs.editorFill = 0;
    Prefs.defaultEnableSoundGroups();
    Prefs.defaultEnableMusicGroups();
    Prefs.checkUpdatesStartup.setSelected(false);
    Prefs.checkUpdatesStartupEnabled = false;
    Prefs.difficultySetting = Prefs.DEFAULT_DIFFICULTY;
    Prefs.difficultyChoices.setSelectedIndex(Prefs.difficultySetting);
    Prefs.moveOneAtATime.setSelected(true);
    Prefs.moveOneAtATimeEnabled = true;
    Prefs.viewingWindowIndex = Prefs.DEFAULT_GAME_VIEW_SIZE_INDEX;
    Prefs.viewingWindowChoices.setSelectedIndex(Prefs.viewingWindowIndex);
    Prefs.editorWindowIndex = Prefs.DEFAULT_EDITOR_VIEW_SIZE_INDEX;
    Prefs.editorWindowChoices.setSelectedIndex(Prefs.editorWindowIndex);
    Prefs.updateCheckIntervalIndex = DEFAULT_UPDATE_CHECK_INTERVAL_INDEX;
    Prefs.updateCheckInterval
        .setSelectedIndex(Prefs.DEFAULT_UPDATE_CHECK_INTERVAL_INDEX);
    Prefs.worldGenerator = Prefs.GENERATOR_CONSTRAINED_RANDOM;
    Prefs.randomRoomSizeIndex = Prefs.DEFAULT_ROOM_SIZE;
    Prefs.randomHallSizeIndex = Prefs.DEFAULT_HALL_SIZE;
    Prefs.lastUpdateCheck = Prefs.DEFAULT_NEXT_UPDATE;
    Prefs.randomRoomSize.setValue(Prefs.DEFAULT_ROOM_SIZE);
    Prefs.randomHallSize.setValue(Prefs.DEFAULT_HALL_SIZE);
    Prefs.loadPrefs();
  }

  static void handleExport() {
    final boolean result = Prefs.eiMgr
        .exportPreferencesFile(Prefs.eiMgr.getExportDestination());
    if (!result) {
      CommonDialogs.showErrorDialog("Export Failed!", "Preferences");
    }
  }

  static void handleImport() {
    final boolean result = Prefs.eiMgr
        .importPreferencesFile(Prefs.eiMgr.getImportSource());
    if (!result) {
      CommonDialogs.showErrorDialog("Import Failed!", "Preferences");
    }
  }

  private static void setUpGUI() {
    Prefs.handler = new EventHandler();
    Prefs.prefTabPane = new JTabbedPane();
    Prefs.mainPrefPane = new JPanel();
    final JPanel editorPane = new JPanel();
    final JPanel generatorPane = new JPanel();
    Prefs.twisterPane = new JPanel();
    final JPanel soundPane = new JPanel();
    final JPanel musicPane = new JPanel();
    final JPanel miscPane = new JPanel();
    final JPanel buttonPane = new JPanel();
    Prefs.prefTabPane.setOpaque(true);
    Prefs.prefsOK = new JButton("OK");
    Prefs.prefsOK.setDefaultCapable(true);
    Prefs.prefsCancel = new JButton("Cancel");
    Prefs.prefsCancel.setDefaultCapable(false);
    Prefs.prefsExport = new JButton("Export...");
    Prefs.prefsExport.setDefaultCapable(false);
    Prefs.prefsImport = new JButton("Import...");
    Prefs.prefsImport.setDefaultCapable(false);
    Prefs.editorFillChoiceArray = new String[] { "Grass", "Dirt", "Sand",
        "Snow", "Tile", "Tundra" };
    Prefs.editorFillChoices = new JComboBox<>(Prefs.editorFillChoiceArray);
    Prefs.sounds[Prefs.SOUNDS_ALL] = new JCheckBox("Enable ALL sounds", true);
    Prefs.sounds[Prefs.SOUNDS_UI] = new JCheckBox(
        "Enable user interface sounds", true);
    Prefs.sounds[Prefs.SOUNDS_GAME] = new JCheckBox("Enable game sounds", true);
    Prefs.sounds[Prefs.SOUNDS_BATTLE] = new JCheckBox("Enable battle sounds",
        true);
    Prefs.sounds[Prefs.SOUNDS_SHOP] = new JCheckBox("Enable shop sounds", true);
    Prefs.music[Prefs.MUSIC_ALL] = new JCheckBox("Enable ALL music", true);
    Prefs.music[Prefs.MUSIC_UI] = new JCheckBox("Enable user interface music",
        true);
    Prefs.music[Prefs.MUSIC_GAME] = new JCheckBox("Enable game music", true);
    Prefs.music[Prefs.MUSIC_BATTLE] = new JCheckBox("Enable battle music",
        true);
    Prefs.music[Prefs.MUSIC_SHOP] = new JCheckBox("Enable shop music", true);
    Prefs.checkUpdatesStartup = new JCheckBox("Check for Updates at Startup",
        true);
    Prefs.moveOneAtATime = new JCheckBox("One Move at a Time", true);
    Prefs.updateCheckIntervalValues = new String[] { "Daily", "Every 2nd Day",
        "Weekly", "Every 2nd Week", "Monthly" };
    Prefs.updateCheckInterval = new JComboBox<>(
        Prefs.updateCheckIntervalValues);
    Prefs.difficultyChoices = new JComboBox<>(Prefs.DIFFICULTY_CHOICE_NAMES);
    Prefs.generatorConstrainedRandom = new JRadioButton(
        "Randomness with limits", true);
    Prefs.generatorTwister = new JRadioButton("Twisted Hallways With Rooms",
        false);
    ButtonGroup generatorGroup = new ButtonGroup();
    generatorGroup.add(Prefs.generatorConstrainedRandom);
    generatorGroup.add(Prefs.generatorTwister);
    Prefs.randomRoomSize = new JSlider(Prefs.MIN_ROOM_SIZE,
        Prefs.MAX_ROOM_SIZE);
    Prefs.randomRoomSize
        .setLabelTable(Prefs.randomRoomSize.createStandardLabels(1));
    Prefs.randomRoomSize.setPaintLabels(true);
    Prefs.randomHallSize = new JSlider(Prefs.MIN_HALL_SIZE,
        Prefs.MAX_HALL_SIZE);
    Prefs.randomHallSize
        .setLabelTable(Prefs.randomHallSize.createStandardLabels(1));
    Prefs.randomHallSize.setPaintLabels(true);
    Prefs.mainPrefPane.setLayout(new BorderLayout());
    editorPane.setLayout(new GridLayout(Prefs.GRID_LENGTH, 1));
    editorPane.add(new JLabel("Default fill for new worlds:"));
    editorPane.add(Prefs.editorFillChoices);
    editorPane.add(new JLabel("Editor Window Size"));
    Prefs.editorWindowChoices = new JComboBox<>(
        Prefs.VIEWING_WINDOW_SIZE_NAMES);
    editorPane.add(Prefs.editorWindowChoices);
    soundPane.setLayout(new GridLayout(Prefs.GRID_LENGTH, 1));
    for (int x = 0; x < Prefs.SOUNDS_LENGTH; x++) {
      soundPane.add(Prefs.sounds[x]);
    }
    musicPane.setLayout(new GridLayout(Prefs.GRID_LENGTH, 1));
    for (int x = 0; x < Prefs.MUSIC_LENGTH; x++) {
      musicPane.add(Prefs.music[x]);
    }
    miscPane.setLayout(new GridLayout(Prefs.GRID_LENGTH, 1));
    miscPane.add(Prefs.checkUpdatesStartup);
    miscPane.add(Prefs.moveOneAtATime);
    miscPane.add(new JLabel("Check How Often For Updates"));
    miscPane.add(Prefs.updateCheckInterval);
    miscPane.add(new JLabel("Game Difficulty"));
    miscPane.add(Prefs.difficultyChoices);
    final JPanel viewPane = new JPanel();
    viewPane.setLayout(new GridLayout(Prefs.GRID_LENGTH, 1));
    viewPane.add(new JLabel("Viewing Window Size"));
    Prefs.viewingWindowChoices = new JComboBox<>(
        Prefs.VIEWING_WINDOW_SIZE_NAMES);
    viewPane.add(Prefs.viewingWindowChoices);
    generatorPane.setLayout(new GridLayout(Prefs.GRID_LENGTH, 1));
    generatorPane.add(new JLabel("World Generation Method"));
    generatorPane.add(Prefs.generatorConstrainedRandom);
    generatorPane.add(Prefs.generatorTwister);
    Prefs.twisterPane.setLayout(new GridLayout(Prefs.GRID_LENGTH, 1));
    Prefs.twisterPane.add(new JLabel("Room Size"));
    Prefs.twisterPane.add(Prefs.randomRoomSize);
    Prefs.twisterPane.add(new JLabel("Hall Size"));
    Prefs.twisterPane.add(Prefs.randomHallSize);
    buttonPane.setLayout(new FlowLayout());
    buttonPane.add(Prefs.prefsOK);
    buttonPane.add(Prefs.prefsCancel);
    buttonPane.add(Prefs.prefsExport);
    buttonPane.add(Prefs.prefsImport);
    Prefs.prefTabPane.addTab("Editor", null, editorPane);
    Prefs.prefTabPane.addTab("Generator", null, generatorPane);
    Prefs.prefTabPane.addTab("Generator Tweaks", null, Prefs.twisterPane);
    Prefs.prefTabPane.addTab("Sounds", null, soundPane);
    Prefs.prefTabPane.addTab("Music", null, musicPane);
    Prefs.prefTabPane.addTab("Misc.", null, miscPane);
    Prefs.prefTabPane.addTab("View", null, viewPane);
    Prefs.mainPrefPane.add(Prefs.prefTabPane, BorderLayout.CENTER);
    Prefs.mainPrefPane.add(buttonPane, BorderLayout.SOUTH);
    Prefs.sounds[Prefs.SOUNDS_ALL].addItemListener(Prefs.handler);
    Prefs.music[Prefs.MUSIC_ALL].addItemListener(Prefs.handler);
    Prefs.prefsOK.addActionListener(Prefs.handler);
    Prefs.prefsCancel.addActionListener(Prefs.handler);
    Prefs.prefsExport.addActionListener(Prefs.handler);
    Prefs.prefsImport.addActionListener(Prefs.handler);
  }

  private static class PrefsFileManager {
    // Constructors
    public PrefsFileManager() {
      // Do nothing
    }

    // Methods
    public void deletePreferencesFile() {
      // Delete preferences file
      CommonPaths.getPrefsFile().delete();
    }

    public boolean readPreferencesFile() {
      if (!CommonPaths.getPrefsFile().exists()) {
        // Abort early if the file does not exist
        return false;
      }
      try (final XDataReader reader = new XDataReader(
          CommonPaths.getPrefsFile().getAbsolutePath(), Prefs.DOC_TAG)) {
        // Read the preferences from the file
        // Read version
        final int version = reader.readInt();
        // Version check
        if (!PrefsVersions.isCompatible(version)) {
          throw new PrefsVersionException(version);
        }
        Prefs.editorFill = reader.readInt();
        Prefs.checkUpdatesStartupEnabled = reader.readBoolean();
        Prefs.moveOneAtATimeEnabled = reader.readBoolean();
        for (int x = 0; x < Prefs.SOUNDS_LENGTH; x++) {
          Prefs.soundsEnabled[x] = reader.readBoolean();
        }
        Prefs.updateCheckIntervalIndex = reader.readInt();
        int cachedBugfix = -1;
        if (version == 1) {
          reader.readString();
          reader.readString();
          reader.readInt();
        } else {
          cachedBugfix = reader.readInt();
        }
        Prefs.difficultySetting = reader.readInt();
        Prefs.viewingWindowIndex = reader.readInt();
        for (int x = 0; x < Prefs.MUSIC_LENGTH; x++) {
          Prefs.musicEnabled[x] = reader.readBoolean();
        }
        Prefs.editorWindowIndex = reader.readInt();
        Prefs.worldGenerator = reader.readInt();
        Prefs.randomRoomSizeIndex = reader.readInt();
        int cachedMajor = reader.readInt();
        Prefs.randomHallSizeIndex = reader.readInt();
        int cachedMinor = reader.readInt();
        if (version == 1) {
          Prefs.lastUpdateCheck = Prefs.DEFAULT_NEXT_UPDATE;
        } else {
          Prefs.cachedMajorVersion = cachedMajor;
          Prefs.cachedMinorVersion = cachedMinor;
          Prefs.cachedBugfixVersion = cachedBugfix;
          Prefs.cachedPrereleaseVersion = reader.readInt();
          Prefs.cachedHasUpdate = reader.readBoolean();
          Prefs.lastUpdateCheck = reader.readLong();
        }
        Prefs.loadPrefs();
        return true;
      } catch (final PrefsVersionException pe) {
        FantastleReboot.exceptionWithMessage(pe,
            "Incompatible preferences version found; using defaults.");
        return false;
      } catch (final Throwable e) {
        FantastleReboot.exceptionWithMessage(e,
            "An error occurred while loading settings. Details have been recorded.");
        return false;
      }
    }

    public void writePreferencesFile() {
      // Create the needed subdirectories, if they don't already exist
      final File prefsFile = CommonPaths.getPrefsFile();
      final File prefsParent = new File(prefsFile.getParent());
      if (!prefsFile.canWrite()) {
        prefsParent.mkdirs();
      }
      try (final XDataWriter writer = new XDataWriter(
          prefsFile.getAbsolutePath(), Prefs.DOC_TAG)) {
        // Write the preferences to the file
        writer.writeInt(PrefsVersions.LATEST);
        writer.writeInt(Prefs.editorFill);
        writer.writeBoolean(Prefs.checkUpdatesStartupEnabled);
        writer.writeBoolean(Prefs.moveOneAtATimeEnabled);
        for (int x = 0; x < Prefs.SOUNDS_LENGTH; x++) {
          writer.writeBoolean(Prefs.soundsEnabled[x]);
        }
        writer.writeInt(Prefs.updateCheckIntervalIndex);
        writer.writeInt(Prefs.cachedBugfixVersion);
        writer.writeInt(Prefs.difficultySetting);
        writer.writeInt(Prefs.viewingWindowIndex);
        for (int x = 0; x < Prefs.MUSIC_LENGTH; x++) {
          writer.writeBoolean(Prefs.musicEnabled[x]);
        }
        writer.writeInt(Prefs.editorWindowIndex);
        writer.writeInt(Prefs.worldGenerator);
        writer.writeInt(Prefs.randomRoomSizeIndex);
        writer.writeInt(Prefs.cachedMajorVersion);
        writer.writeInt(Prefs.randomHallSizeIndex);
        writer.writeInt(Prefs.cachedMinorVersion);
        writer.writeInt(Prefs.cachedPrereleaseVersion);
        writer.writeBoolean(Prefs.cachedHasUpdate);
        writer.writeLong(Prefs.lastUpdateCheck);
      } catch (final Throwable t) {
        FantastleReboot.exceptionWithMessage(t,
            "An error occurred while saving settings. Changes may have been lost. Details have been recorded.");
      }
    }
  }

  private static class ExportImportManager {
    // Constructors
    public ExportImportManager() {
      // Do nothing
    }

    // Methods
    public boolean importPreferencesFile(final File importFile) {
      try (final XDataReader reader = new XDataReader(
          importFile.getAbsolutePath(), Prefs.DOC_TAG)) {
        // Read the preferences from the file
        // Read version
        final int version = reader.readInt();
        // Version check
        if (!PrefsVersions.isCompatible(version)) {
          throw new PrefsVersionException(version);
        }
        Prefs.editorFill = reader.readInt();
        Prefs.checkUpdatesStartupEnabled = reader.readBoolean();
        Prefs.moveOneAtATimeEnabled = reader.readBoolean();
        for (int x = 0; x < Prefs.SOUNDS_LENGTH; x++) {
          Prefs.soundsEnabled[x] = reader.readBoolean();
        }
        Prefs.updateCheckIntervalIndex = reader.readInt();
        int cachedBugfix = -1;
        if (version == 1) {
          reader.readString();
          reader.readString();
          reader.readInt();
        } else {
          cachedBugfix = reader.readInt();
        }
        Prefs.difficultySetting = reader.readInt();
        Prefs.viewingWindowIndex = reader.readInt();
        for (int x = 0; x < Prefs.MUSIC_LENGTH; x++) {
          Prefs.musicEnabled[x] = reader.readBoolean();
        }
        Prefs.editorWindowIndex = reader.readInt();
        Prefs.worldGenerator = reader.readInt();
        Prefs.randomRoomSizeIndex = reader.readInt();
        int cachedMajor = reader.readInt();
        Prefs.randomHallSizeIndex = reader.readInt();
        int cachedMinor = reader.readInt();
        if (version == 1) {
          Prefs.lastUpdateCheck = Prefs.DEFAULT_NEXT_UPDATE;
        } else {
          Prefs.cachedMajorVersion = cachedMajor;
          Prefs.cachedMinorVersion = cachedMinor;
          Prefs.cachedBugfixVersion = cachedBugfix;
          Prefs.cachedPrereleaseVersion = reader.readInt();
          Prefs.cachedHasUpdate = reader.readBoolean();
          Prefs.lastUpdateCheck = reader.readLong();
        }
        Prefs.loadPrefs();
        return true;
      } catch (final PrefsVersionException pe) {
        FantastleReboot.exceptionWithMessage(pe,
            "Incompatible preferences version found; aborting import.");
        return false;
      } catch (final Throwable e) {
        FantastleReboot.exception(e);
        return false;
      }
    }

    public boolean exportPreferencesFile(final File exportFile) {
      try (final XDataWriter writer = new XDataWriter(
          exportFile.getAbsolutePath(), Prefs.DOC_TAG)) {
        // Write the preferences to the file
        writer.writeInt(PrefsVersions.LATEST);
        writer.writeInt(Prefs.editorFill);
        writer.writeBoolean(Prefs.checkUpdatesStartupEnabled);
        writer.writeBoolean(Prefs.moveOneAtATimeEnabled);
        for (int x = 0; x < Prefs.SOUNDS_LENGTH; x++) {
          writer.writeBoolean(Prefs.soundsEnabled[x]);
        }
        writer.writeInt(Prefs.updateCheckIntervalIndex);
        writer.writeInt(Prefs.cachedBugfixVersion);
        writer.writeInt(Prefs.difficultySetting);
        writer.writeInt(Prefs.viewingWindowIndex);
        for (int x = 0; x < Prefs.MUSIC_LENGTH; x++) {
          writer.writeBoolean(Prefs.musicEnabled[x]);
        }
        writer.writeInt(Prefs.editorWindowIndex);
        writer.writeInt(Prefs.worldGenerator);
        writer.writeInt(Prefs.randomRoomSizeIndex);
        writer.writeInt(Prefs.cachedMajorVersion);
        writer.writeInt(Prefs.randomHallSizeIndex);
        writer.writeInt(Prefs.cachedMinorVersion);
        writer.writeInt(Prefs.cachedPrereleaseVersion);
        writer.writeBoolean(Prefs.cachedHasUpdate);
        writer.writeLong(Prefs.lastUpdateCheck);
        return true;
      } catch (final Throwable t) {
        FantastleReboot.exceptionWithMessage(t,
            "An error occurred while exporting settings. Export aborted. Details have been recorded.");
        return false;
      }
    }

    public File getImportSource() {
      final FileDialog chooser = new FileDialog((java.awt.Frame) null, "Import",
          FileDialog.LOAD);
      chooser.setVisible(true);
      return new File(chooser.getDirectory() + chooser.getFile());
    }

    public File getExportDestination() {
      final FileDialog chooser = new FileDialog((java.awt.Frame) null, "Export",
          FileDialog.SAVE);
      chooser.setVisible(true);
      return new File(chooser.getDirectory() + chooser.getFile());
    }
  }

  private static class EventHandler
      implements ActionListener, ItemListener, WindowListener {
    public EventHandler() {
      // Do nothing
    }

    // Handle buttons
    @Override
    public void actionPerformed(final ActionEvent e) {
      try {
        final String cmd = e.getActionCommand();
        if (cmd.equals("OK")) {
          Prefs.savePrefs();
          Prefs.hidePrefs();
        } else if (cmd.equals("Cancel")) {
          Prefs.loadPrefs();
          Prefs.hidePrefs();
        } else if (cmd.equals("Export...")) {
          Prefs.handleExport();
        } else if (cmd.equals("Import...")) {
          Prefs.handleImport();
        }
      } catch (final Exception ex) {
        FantastleReboot.exception(ex);
      }
    }

    @Override
    public void itemStateChanged(final ItemEvent e) {
      try {
        final Object o = e.getItem();
        if (o.getClass().equals(Prefs.sounds[Prefs.SOUNDS_ALL].getClass())) {
          final JCheckBox check = (JCheckBox) o;
          if (check.equals(Prefs.sounds[Prefs.SOUNDS_ALL])) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
              for (int x = 1; x < Prefs.SOUNDS_LENGTH; x++) {
                Prefs.sounds[x].setEnabled(true);
              }
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
              for (int x = 1; x < Prefs.SOUNDS_LENGTH; x++) {
                Prefs.sounds[x].setEnabled(false);
              }
            }
          }
        } else if (o.getClass()
            .equals(Prefs.music[Prefs.MUSIC_ALL].getClass())) {
          final JCheckBox check = (JCheckBox) o;
          if (check.equals(Prefs.music[Prefs.MUSIC_ALL])) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
              for (int x = 1; x < Prefs.MUSIC_LENGTH; x++) {
                Prefs.music[x].setEnabled(true);
              }
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
              for (int x = 1; x < Prefs.MUSIC_LENGTH; x++) {
                Prefs.music[x].setEnabled(false);
              }
            }
          }
        }
      } catch (final Exception ex) {
        FantastleReboot.exception(ex);
      }
    }

    @Override
    public void windowOpened(final WindowEvent e) {
      // Do nothing
    }

    @Override
    public void windowClosing(final WindowEvent e) {
      try {
        Prefs.hidePrefs();
      } catch (final Exception ex) {
        FantastleReboot.exception(ex);
      }
    }

    @Override
    public void windowClosed(final WindowEvent e) {
      // Do nothing
    }

    @Override
    public void windowIconified(final WindowEvent e) {
      // Do nothing
    }

    @Override
    public void windowDeiconified(final WindowEvent e) {
      // Do nothing
    }

    @Override
    public void windowActivated(final WindowEvent e) {
      // Do nothing
    }

    @Override
    public void windowDeactivated(final WindowEvent e) {
      // Do nothing
    }
  }
}
