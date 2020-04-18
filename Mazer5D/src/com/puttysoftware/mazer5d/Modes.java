package com.puttysoftware.mazer5d;

import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.prefs.Prefs;

public class Modes {
    private Modes() {
        super();
    }

    static {
        Modes.current = Modes.NULL;
        Modes.former = Modes.NULL;
    }
    private static final int NULL = 0;
    private static final int GAME = 1;
    private static final int EDITOR = 2;
    private static final int PREFS = 3;
    private static final int ABOUT = 4;
    private static final int GUI = 5;
    private static int current;
    private static int former;

    public static void setInGUI() {
        Modes.current = Modes.GUI;
    }

    public static void setInPrefs() {
        Modes.save();
        Modes.current = Modes.PREFS;
    }

    public static void setInGame() {
        Modes.current = Modes.GAME;
    }

    public static void setInEditor() {
        Modes.current = Modes.EDITOR;
    }

    public static void setInAbout() {
        Modes.save();
        Modes.current = Modes.ABOUT;
    }

    public static boolean inGUI() {
        return Modes.current == Modes.GUI;
    }

    public static boolean inGame() {
        return Modes.current == Modes.GAME;
    }

    public static boolean inEditor() {
        return Modes.current == Modes.EDITOR;
    }

    private static void save() {
        Modes.former = Modes.current;
    }

    public static void restore() {
        final BagOStuff bag = Mazer5D.getBagOStuff();
        Modes.current = Modes.former;
        switch (Modes.current) {
        case GUI:
            bag.getGUIManager().showGUI();
            break;
        case GAME:
            bag.getGameManager().showOutput();
            break;
        case EDITOR:
            bag.getMenuManager().setEditorMenus();
            bag.getEditor().showOutput();
            break;
        case PREFS:
            Prefs.showPrefs();
            break;
        case ABOUT:
            bag.getAboutDialog().showAboutDialog();
            break;
        default:
            break;
        }
    }
}
