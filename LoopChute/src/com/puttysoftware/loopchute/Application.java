/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute;

import java.awt.Image;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.images.edges.EdgeGenerator;
import com.puttysoftware.loopchute.editor.MazeEditor;
import com.puttysoftware.loopchute.editor.rulesets.RuleSetPicker;
import com.puttysoftware.loopchute.game.GameLogicManager;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.generic.MazeObjectList;
import com.puttysoftware.loopchute.maze.MazeManager;
import com.puttysoftware.loopchute.objects.GeneratedEdge;
import com.puttysoftware.loopchute.prefs.PreferencesManager;
import com.puttysoftware.loopchute.resourcemanagers.GraphicsManager;
import com.puttysoftware.loopchute.resourcemanagers.LogoManager;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class Application {
    // Fields
    private AboutDialog about;
    private GameLogicManager gameMgr;
    private MazeManager mazeMgr;
    private MenuManager menuMgr;
    private ObjectHelpManager oHelpMgr;
    private MazeEditor editor;
    private RuleSetPicker rsPicker;
    private GUIManager guiMgr;
    private final MazeObjectList objects;
    private boolean IN_GUI, IN_PREFS, IN_GAME, IN_EDITOR;
    private static final int VERSION_MAJOR = 0;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_BETA = 1;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_EDITOR = 2;
    public static final int STATUS_PREFS = 3;
    public static final int STATUS_NULL = 4;

    // Constructors
    public Application() {
        this.objects = new MazeObjectList();
    }

    // Methods
    void postConstruct() {
        // Create Edge Images and Objects
        final MazeObject[] groundTypes = this.objects
                .getAllGenerationEligibleTypedObjects();
        final String[] edgeNameSuffixes = EdgeGenerator
                .generateAllEdgedImageNameSuffixes();
        final String[] edgeFriendlyNameSuffixes = EdgeGenerator
                .generateAllEdgedImageFriendlyNameSuffixes();
        for (int x = 0; x < groundTypes.length - 1; x++) {
            for (int y = x + 1; y < groundTypes.length; y++) {
                if (groundTypes[x].equals(groundTypes[y])) {
                    continue;
                }
                final BufferedImageIcon[] edges = EdgeGenerator
                        .generateAllEdgedImages(
                                GraphicsManager.getImage(
                                        groundTypes[x].getName(),
                                        groundTypes[x].getBaseName(),
                                        groundTypes[x].getTemplateColor(),
                                        groundTypes[x].getAttributeName(),
                                        groundTypes[x]
                                                .getAttributeTemplateColor()),
                                GraphicsManager.getImage(
                                        groundTypes[y].getName(),
                                        groundTypes[y].getBaseName(),
                                        groundTypes[y].getTemplateColor(),
                                        groundTypes[y].getAttributeName(),
                                        groundTypes[y]
                                                .getAttributeTemplateColor()),
                                GraphicsManager.generateEdgeColor(
                                        groundTypes[x].getTemplateColor(),
                                        groundTypes[y].getTemplateColor()));
                for (int z = 0; z < edges.length; z++) {
                    GraphicsManager.addImageToCache(groundTypes[x].getName()
                            + "/" + groundTypes[y].getName() + " Transition "
                            + edgeFriendlyNameSuffixes[z], edges[z]);
                    final GeneratedEdge ge = new GeneratedEdge(
                            groundTypes[x].getName() + "/"
                                    + groundTypes[y].getName() + " Transition "
                                    + edgeFriendlyNameSuffixes[z],
                            groundTypes[x].getBaseName() + "_"
                                    + groundTypes[y].getBaseName()
                                    + edgeNameSuffixes[z],
                            groundTypes[x].getName() + "/"
                                    + groundTypes[y].getName() + " Transitions "
                                    + edgeFriendlyNameSuffixes[z],
                            groundTypes[x].getName(), groundTypes[y].getName(),
                            edgeFriendlyNameSuffixes[z]);
                    this.objects.addObject(ge);
                }
            }
        }
        // Create Managers
        this.about = new AboutDialog(this.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.oHelpMgr = new ObjectHelpManager();
        // Cache Logo
        this.guiMgr.updateLogo();
    }

    public void setInGUI(final boolean value) {
        this.IN_GUI = value;
    }

    public void setInPrefs(final boolean value) {
        this.IN_PREFS = value;
    }

    public void setInGame(final boolean value) {
        this.IN_GAME = value;
    }

    public void setInEditor(final boolean value) {
        this.IN_EDITOR = value;
    }

    public int getMode() {
        if (this.IN_PREFS) {
            return Application.STATUS_PREFS;
        } else if (this.IN_GUI) {
            return Application.STATUS_GUI;
        } else if (this.IN_GAME) {
            return Application.STATUS_GAME;
        } else if (this.IN_EDITOR) {
            return Application.STATUS_EDITOR;
        } else {
            return Application.STATUS_NULL;
        }
    }

    public int getFormerMode() {
        if (this.IN_GUI) {
            return Application.STATUS_GUI;
        } else if (this.IN_GAME) {
            return Application.STATUS_GAME;
        } else if (this.IN_EDITOR) {
            return Application.STATUS_EDITOR;
        } else {
            return Application.STATUS_NULL;
        }
    }

    public void showMessage(final String msg) {
        if (this.IN_PREFS) {
            CommonDialogs.showDialog(msg);
        } else if (this.IN_GUI) {
            CommonDialogs.showDialog(msg);
        } else if (this.IN_GAME) {
            this.getGameManager().setStatusMessage(msg);
        } else if (this.IN_EDITOR) {
            this.getEditor().setStatusMessage(msg);
        } else {
            CommonDialogs.showDialog(msg);
        }
    }

    public MenuManager getMenuManager() {
        return this.menuMgr;
    }

    public GUIManager getGUIManager() {
        return this.guiMgr;
    }

    public GameLogicManager getGameManager() {
        if (this.gameMgr == null) {
            this.gameMgr = new GameLogicManager();
        }
        return this.gameMgr;
    }

    public MazeManager getMazeManager() {
        if (this.mazeMgr == null) {
            this.mazeMgr = new MazeManager();
        }
        return this.mazeMgr;
    }

    public ObjectHelpManager getObjectHelpManager() {
        return this.oHelpMgr;
    }

    public MazeEditor getEditor() {
        if (this.editor == null) {
            this.editor = new MazeEditor();
        }
        return this.editor;
    }

    public RuleSetPicker getRuleSetPicker() {
        if (this.rsPicker == null) {
            this.rsPicker = new RuleSetPicker();
        }
        return this.rsPicker;
    }

    public AboutDialog getAboutDialog() {
        return this.about;
    }

    public BufferedImageIcon getMicroLogo() {
        return LogoManager.getMicroLogo();
    }

    public Image getIconLogo() {
        return LogoManager.getIconLogo();
    }

    public void playLogoSound() {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_USER_INTERFACE,
                SoundConstants.SOUND_LOGO);
    }

    private String getVersionString() {
        if (this.isBetaModeEnabled()) {
            return "" + Application.VERSION_MAJOR + "."
                    + Application.VERSION_MINOR + "."
                    + Application.VERSION_BUGFIX + "-beta"
                    + Application.VERSION_BETA;
        } else {
            return "" + Application.VERSION_MAJOR + "."
                    + Application.VERSION_MINOR + "."
                    + Application.VERSION_BUGFIX;
        }
    }

    public JFrame getOutputFrame() {
        try {
            if (this.getMode() == Application.STATUS_PREFS) {
                return PreferencesManager.getPrefFrame();
            } else if (this.getMode() == Application.STATUS_GUI) {
                return this.getGUIManager().getGUIFrame();
            } else if (this.getMode() == Application.STATUS_GAME) {
                return this.getGameManager().getOutputFrame();
            } else if (this.getMode() == Application.STATUS_EDITOR) {
                return this.getEditor().getOutputFrame();
            } else {
                return null;
            }
        } catch (final NullPointerException npe) {
            return null;
        }
    }

    public MazeObjectList getObjects() {
        return this.objects;
    }

    public boolean isBetaModeEnabled() {
        return Application.VERSION_BETA > 0;
    }
}
