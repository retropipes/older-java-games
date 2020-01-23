/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class MazePreferencesManager {
    // Fields
    private JFrame prefFrame;
    private Container mainPrefPane, contentPane, buttonPane;
    private JButton prefsOK, prefsCancel;
    private JCheckBox horizontalWrap;
    private JCheckBox verticalWrap;
    private JCheckBox thirdDimensionalWrap;
    private JComboBox<String> startLevelChoices;
    private String[] startLevelChoiceArray;
    private EventHandler handler;

    // Constructors
    public MazePreferencesManager() {
	this.setUpGUI();
    }

    // Methods
    public JFrame getPrefFrame() {
	if (this.prefFrame != null && this.prefFrame.isVisible()) {
	    return this.prefFrame;
	} else {
	    return null;
	}
    }

    public void showPrefs() {
	Import1.getApplication().getEditor().disableOutput();
	this.prefFrame.setVisible(true);
    }

    public void hidePrefs() {
	this.prefFrame.setVisible(false);
	Import1.getApplication().getEditor().enableOutput();
	Import1.getApplication().getMazeManager().setDirty(true);
	Import1.getApplication().getEditor().redrawEditor();
    }

    public void setPrefs() {
	final int level = Import1.getApplication().getEditor().getLocationManager().getEditorLocationW();
	final Maze m = Import1.getApplication().getMazeManager().getMaze();
	if (this.horizontalWrap.isSelected()) {
	    m.enableHorizontalWraparound(level);
	} else {
	    m.disableHorizontalWraparound(level);
	}
	if (this.verticalWrap.isSelected()) {
	    m.enableVerticalWraparound(level);
	} else {
	    m.disableVerticalWraparound(level);
	}
	if (this.thirdDimensionalWrap.isSelected()) {
	    m.enable3rdDimensionWraparound(level);
	} else {
	    m.disable3rdDimensionWraparound(level);
	}
	m.setStartLevel(this.startLevelChoices.getSelectedIndex());
    }

    public void loadPrefs() {
	final int level = Import1.getApplication().getEditor().getLocationManager().getEditorLocationW();
	final Maze m = Import1.getApplication().getMazeManager().getMaze();
	this.startLevelChoiceArray = new String[m.getLevels()];
	for (int x = 0; x < m.getLevels(); x++) {
	    this.startLevelChoiceArray[x] = Integer.toString(x + 1);
	}
	final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(this.startLevelChoiceArray);
	this.startLevelChoices.setModel(model);
	this.horizontalWrap.setSelected(m.isHorizontalWraparoundEnabled(level));
	this.verticalWrap.setSelected(m.isVerticalWraparoundEnabled(level));
	this.thirdDimensionalWrap.setSelected(m.is3rdDimensionWraparoundEnabled(level));
	this.startLevelChoices.setSelectedIndex(m.getStartLevel());
    }

    private void setUpGUI() {
	this.handler = new EventHandler();
	this.prefFrame = new JFrame("Maze Preferences");
	this.mainPrefPane = new Container();
	this.contentPane = new Container();
	this.buttonPane = new Container();
	this.prefsOK = new JButton("OK");
	this.prefsOK.setDefaultCapable(true);
	this.prefFrame.getRootPane().setDefaultButton(this.prefsOK);
	this.prefsCancel = new JButton("Cancel");
	this.prefsCancel.setDefaultCapable(false);
	this.startLevelChoices = new JComboBox<>();
	this.horizontalWrap = new JCheckBox("Enable horizontal wraparound", false);
	this.verticalWrap = new JCheckBox("Enable vertical wraparound", false);
	this.thirdDimensionalWrap = new JCheckBox("Enable 3rd dimension wraparound", false);
	this.prefFrame.setContentPane(this.mainPrefPane);
	this.prefFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	this.prefFrame.addWindowListener(this.handler);
	this.mainPrefPane.setLayout(new BorderLayout());
	this.prefFrame.setResizable(false);
	this.contentPane.setLayout(new GridLayout(5, 1));
	this.contentPane.add(this.horizontalWrap);
	this.contentPane.add(this.verticalWrap);
	this.contentPane.add(this.thirdDimensionalWrap);
	this.contentPane.add(new JLabel("Starting Level:"));
	this.contentPane.add(this.startLevelChoices);
	this.buttonPane.setLayout(new FlowLayout());
	this.buttonPane.add(this.prefsOK);
	this.buttonPane.add(this.prefsCancel);
	this.mainPrefPane.add(this.contentPane, BorderLayout.CENTER);
	this.mainPrefPane.add(this.buttonPane, BorderLayout.SOUTH);
	this.prefsOK.addActionListener(this.handler);
	this.prefsCancel.addActionListener(this.handler);
	this.prefFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
	public EventHandler() {
	    // TODO Auto-generated constructor stub
	}

	// Handle buttons
	@Override
	public void actionPerformed(final ActionEvent e) {
	    try {
		final MazePreferencesManager mpm = MazePreferencesManager.this;
		final String cmd = e.getActionCommand();
		if (cmd.equals("OK")) {
		    mpm.setPrefs();
		    mpm.hidePrefs();
		} else if (cmd.equals("Cancel")) {
		    mpm.hidePrefs();
		}
	    } catch (final Exception ex) {
		Import1.debug(ex);
	    }
	}

	@Override
	public void windowOpened(final WindowEvent e) {
	    // Do nothing
	}

	@Override
	public void windowClosing(final WindowEvent e) {
	    final MazePreferencesManager pm = MazePreferencesManager.this;
	    pm.hidePrefs();
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
