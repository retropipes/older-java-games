/*  FantastleX: A Names Editor
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.editor.namer.editor;

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
import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.datamanagers.NamesDataManager;
import com.puttysoftware.fantastlex.editor.namer.abc.AbstractObjectEditor;
import com.puttysoftware.fantastlex.names.NamesConstants;
import com.puttysoftware.fantastlex.names.NamesManager;

public class NameEditor extends AbstractObjectEditor {
    // Declarations
    private final EventHandler handler;
    private String[][] cachedNames;
    private JMenu nameCommandsMenu;

    public NameEditor() {
        super("Name Editor", Application.EDITOR_NAME, 2, 2,
                NamesConstants.NAMES_LENGTH, true);
        this.handler = new EventHandler();
    }

    @Override
    protected void handleButtonClick(final String cmd, final int num) {
        // Do nothing
    }

    @Override
    protected void guiNameLabelProperties(final JLabel nameLbl, final int num) {
        if (nameLbl != null) {
            nameLbl.setAlignmentX(SwingConstants.RIGHT);
            nameLbl.setText(" New " + NamesConstants.EDITOR_SECTION_ARRAY[num]
                    + " Name: ");
        }
    }

    @Override
    protected boolean guiEntryType(final int num) {
        return AbstractObjectEditor.ENTRY_TYPE_TEXT;
    }

    @Override
    protected void guiEntryFieldProperties(final JTextField entry,
            final int num) {
        if (entry != null) {
            entry.setText(this.cachedNames[num][1]);
        }
    }

    @Override
    protected String[] guiEntryListItems(final int num) {
        return null;
    }

    @Override
    protected void guiEntryListProperties(final JComboBox<String> list,
            final int num) {
        // Do nothing
    }

    @Override
    protected void guiActionButtonProperties(final JButton actBtn,
            final int row, final int col) {
        // Do nothing
    }

    @Override
    protected String guiActionButtonActionCommand(final int row,
            final int col) {
        return null;
    }

    @Override
    protected void autoStoreEntryFieldValue(final JTextField entry,
            final int num) {
        if (entry != null) {
            this.cachedNames[num][1] = entry.getText();
        }
    }

    @Override
    protected void autoStoreEntryListValue(final JComboBox<String> list,
            final int num) {
        // Do nothing
    }

    @Override
    public JMenu createEditorCommandsMenu() {
        this.nameCommandsMenu = new JMenu("Names Editor");
        // Create menu commands
        final JMenuItem nameEdit = new JMenuItem("Edit Names");
        final JMenuItem nameReset = new JMenuItem("Reset Names");
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
        final int count = this.nameCommandsMenu.getItemCount();
        for (int x = 0; x < count; x++) {
            this.nameCommandsMenu.getItem(x).setEnabled(false);
        }
    }

    @Override
    public void enableEditorCommands() {
        final int count = this.nameCommandsMenu.getItemCount();
        for (int x = 0; x < count; x++) {
            this.nameCommandsMenu.getItem(x).setEnabled(true);
        }
    }

    @Override
    protected boolean doesObjectExist() {
        return this.cachedNames != null;
    }

    @Override
    protected boolean newObjectOptions() {
        this.cachedNames = NamesManager.getNamesCache();
        return this.cachedNames != null;
    }

    @Override
    protected boolean newObjectCreate() {
        return true;
    }

    @Override
    protected void loadObject() {
        this.cachedNames = FantastleX.getApplication().getScenarioManager()
                .getNamesFileManager().getNames();
    }

    @Override
    protected void saveObject() {
        FantastleX.getApplication().getScenarioManager().getNamesFileManager()
                .setNames(this.cachedNames);
    }

    @Override
    public void handleCloseWindow() {
        FantastleX.getApplication().getScenarioManager().getNamesFileManager()
                .saveNames();
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
                final String cmd = e.getActionCommand();
                final NameEditor me = NameEditor.this;
                if (cmd.equals("Edit Names")) {
                    if (me.didObjectChange()) {
                        me.loadObject();
                    }
                    if (me.doesObjectExist()) {
                        me.edit();
                    } else {
                        me.create();
                        FantastleX.getApplication().getScenarioManager()
                                .getNamesFileManager().loadNames();
                        me.reSetUpGUI();
                        me.edit();
                    }
                } else if (cmd.equals("Reset Names")) {
                    NamesDataManager.resetNames();
                    NamesManager.invalidateNamesCache();
                    CommonDialogs.showDialog("Names Reset.");
                }
            } catch (final Exception ex) {
                FantastleX.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void windowActivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosed(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent we) {
            NameEditor.this.handleCloseWindow();
        }

        @Override
        public void windowDeactivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowOpened(final WindowEvent we) {
            // Do nothing
        }
    }

    @Override
    protected WindowListener guiHookWindow() {
        return this.handler;
    }
}
