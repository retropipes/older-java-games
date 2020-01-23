/*  MazeRunnerII: A Names Editor
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.editor.namer.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.datamanagers.NamesDataManager;
import com.puttysoftware.mazerunner2.editor.namer.abc.AbstractObjectEditor;
import com.puttysoftware.mazerunner2.names.NamesConstants;
import com.puttysoftware.mazerunner2.names.NamesManager;

public class NameEditor extends AbstractObjectEditor {
    // Declarations
    private EventHandler handler;
    private String[][] cachedNames;
    private JMenu nameCommandsMenu;

    public NameEditor() {
        super("Name Editor", Application.EDITOR_NAME, 2, 2,
                NamesConstants.NAMES_LENGTH, true);
        this.handler = new EventHandler();
    }

    @Override
    protected void handleButtonClick(String cmd, int num) {
        // Do nothing
    }

    @Override
    protected void guiNameLabelProperties(JLabel nameLbl, int num) {
        if (nameLbl != null) {
            nameLbl.setAlignmentX(SwingConstants.RIGHT);
            nameLbl.setText(" New " + NamesConstants.EDITOR_SECTION_ARRAY[num]
                    + " Name: ");
        }
    }

    @Override
    protected boolean guiEntryType(int num) {
        return AbstractObjectEditor.ENTRY_TYPE_TEXT;
    }

    @Override
    protected void guiEntryFieldProperties(JTextField entry, int num) {
        if (entry != null) {
            entry.setText(this.cachedNames[num][1]);
        }
    }

    @Override
    protected String[] guiEntryListItems(int num) {
        return null;
    }

    @Override
    protected void guiEntryListProperties(JComboBox<String> list, int num) {
        // Do nothing
    }

    @Override
    protected void guiActionButtonProperties(JButton actBtn, int row, int col) {
        // Do nothing
    }

    @Override
    protected String guiActionButtonActionCommand(int row, int col) {
        return null;
    }

    @Override
    protected void autoStoreEntryFieldValue(JTextField entry, int num) {
        if (entry != null) {
            this.cachedNames[num][1] = entry.getText();
        }
    }

    @Override
    protected void autoStoreEntryListValue(JComboBox<String> list, int num) {
        // Do nothing
    }

    @Override
    public JMenu createEditorCommandsMenu() {
        this.nameCommandsMenu = new JMenu("Names Editor");
        // Create menu commands
        JMenuItem nameEdit = new JMenuItem("Edit Names");
        JMenuItem nameReset = new JMenuItem("Reset Names");
        // Add event handlers
        nameEdit.addActionListener(this.handler);
        nameReset.addActionListener(this.handler);
        // Add commands to menu
        this.nameCommandsMenu.add(nameEdit);
        this.nameCommandsMenu.add(nameReset);
        return this.nameCommandsMenu;
    }

    @Override
    public void disableEditorCommands() {
        int count = this.nameCommandsMenu.getItemCount();
        for (int x = 0; x < count; x++) {
            this.nameCommandsMenu.getItem(x).setEnabled(false);
        }
    }

    @Override
    public void enableEditorCommands() {
        int count = this.nameCommandsMenu.getItemCount();
        for (int x = 0; x < count; x++) {
            this.nameCommandsMenu.getItem(x).setEnabled(true);
        }
    }

    @Override
    protected boolean doesObjectExist() {
        return (this.cachedNames != null);
    }

    @Override
    protected boolean newObjectOptions() {
        this.cachedNames = NamesManager.getNamesCache();
        return (this.cachedNames != null);
    }

    @Override
    protected boolean newObjectCreate() {
        return true;
    }

    @Override
    protected void loadObject() {
        this.cachedNames = MazeRunnerII.getApplication().getScenarioManager()
                .getNamesFileManager().getNames();
    }

    @Override
    protected void saveObject() {
        MazeRunnerII.getApplication().getScenarioManager()
                .getNamesFileManager().setNames(this.cachedNames);
    }

    @Override
    public void handleCloseWindow() {
        MazeRunnerII.getApplication().getScenarioManager()
                .getNamesFileManager().saveNames();
        this.exitEditor();
    }

    private class EventHandler implements ActionListener, WindowListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        // Handle menus
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                String cmd = e.getActionCommand();
                NameEditor me = NameEditor.this;
                if (cmd.equals("Edit Names")) {
                    if (me.didObjectChange()) {
                        me.loadObject();
                    }
                    if (me.doesObjectExist()) {
                        me.edit();
                    } else {
                        me.create();
                        MazeRunnerII.getApplication().getScenarioManager()
                                .getNamesFileManager().loadNames();
                        me.reSetUpGUI();
                        me.edit();
                    }
                } else if (cmd.equals("Reset Names")) {
                    NamesDataManager.resetNames();
                    NamesManager.invalidateNamesCache();
                    CommonDialogs.showDialog("Names Reset.");
                }
            } catch (Exception ex) {
                MazeRunnerII.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void windowActivated(WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosed(WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosing(WindowEvent we) {
            NameEditor.this.handleCloseWindow();
        }

        @Override
        public void windowDeactivated(WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowIconified(WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowOpened(WindowEvent we) {
            // Do nothing
        }
    }

    @Override
    protected WindowListener guiHookWindow() {
        return this.handler;
    }
}
