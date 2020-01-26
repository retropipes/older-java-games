package com.puttysoftware.mazerunner2.battle.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.ai.window.AbstractWindowAIRoutine;
import com.puttysoftware.mazerunner2.battle.AbstractBattle;
import com.puttysoftware.mazerunner2.battle.BattleResults;

public class WindowBattleGUI {
    // Fields
    JFrame battleFrame;
    private final Container holderPane, iconPane, messagePane, buttonPane;
    private final JLabel iconLabel;
    private final JTextArea messageArea;
    private final JButton attack, flee, spell, steal, drain, item, done;
    private final BattleEventHandler handler;

    // Constructor
    public WindowBattleGUI() {
        // Initialize GUI
        this.battleFrame = new JFrame("Battle");
        this.holderPane = new Container();
        this.iconPane = new Container();
        this.messagePane = new Container();
        this.buttonPane = new Container();
        this.iconLabel = new JLabel("");
        this.messageArea = new JTextArea();
        this.messageArea.setOpaque(true);
        this.messageArea.setEditable(false);
        this.messageArea.setBackground(this.battleFrame.getBackground());
        this.attack = new JButton("Attack");
        this.flee = new JButton("Flee");
        this.spell = new JButton("Cast Spell");
        this.steal = new JButton("Steal");
        this.drain = new JButton("Drain");
        this.item = new JButton("Use Item");
        this.done = new JButton("Continue");
        this.battleFrame.getRootPane().setDefaultButton(this.done);
        this.iconPane.setLayout(new FlowLayout());
        this.messagePane.setLayout(new FlowLayout());
        this.buttonPane.setLayout(new GridLayout(2, 4));
        this.holderPane.setLayout(new BorderLayout());
        this.iconPane.add(this.iconLabel);
        this.messagePane.add(this.messageArea);
        this.buttonPane.add(this.attack);
        this.buttonPane.add(this.flee);
        this.buttonPane.add(this.spell);
        this.buttonPane.add(this.steal);
        this.buttonPane.add(this.drain);
        this.buttonPane.add(this.item);
        this.buttonPane.add(this.done);
        this.holderPane.add(this.iconPane, BorderLayout.WEST);
        this.holderPane.add(this.messagePane, BorderLayout.CENTER);
        this.holderPane.add(this.buttonPane, BorderLayout.SOUTH);
        this.battleFrame.setContentPane(this.holderPane);
        this.battleFrame.setResizable(false);
        this.battleFrame.pack();
        // Initialize Event Handlers
        this.handler = new BattleEventHandler();
        this.attack.addActionListener(this.handler);
        this.flee.addActionListener(this.handler);
        this.spell.addActionListener(this.handler);
        this.steal.addActionListener(this.handler);
        this.drain.addActionListener(this.handler);
        this.done.addActionListener(this.handler);
        this.attack.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "Attack");
        this.attack.getActionMap().put("Attack", this.handler);
        this.flee.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "Flee");
        this.flee.getActionMap().put("Flee", this.handler);
        this.spell.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "Cast Spell");
        this.spell.getActionMap().put("Cast Spell", this.handler);
        this.steal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "Steal");
        this.steal.getActionMap().put("Steal", this.handler);
        this.drain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "Drain");
        this.drain.getActionMap().put("Drain", this.handler);
        this.item.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "Use Item");
        this.item.getActionMap().put("Use Item", this.handler);
    }

    void initBattle(final BufferedImageIcon enemyIcon) {
        this.iconLabel.setIcon(enemyIcon);
        this.attack.setVisible(true);
        this.flee.setVisible(true);
        this.spell.setVisible(true);
        this.steal.setVisible(true);
        this.drain.setVisible(true);
        this.done.setVisible(false);
        this.attack.setEnabled(true);
        this.flee.setEnabled(true);
        this.spell.setEnabled(true);
        this.steal.setEnabled(true);
        this.drain.setEnabled(true);
        this.done.setEnabled(false);
        this.battleFrame.setVisible(true);
    }

    public JFrame getOutputFrame() {
        return this.battleFrame;
    }

    final void clearMessageArea() {
        this.messageArea.setText("");
    }

    public final void setStatusMessage(final String s) {
        this.messageArea.setText(this.messageArea.getText() + s + "\n");
    }

    final void stripExtraNewLine() {
        final String currText = this.messageArea.getText();
        this.messageArea.setText(currText.substring(0, currText.length() - 1));
    }

    void doResultCleanup() {
        // Cleanup
        this.attack.setVisible(false);
        this.flee.setVisible(false);
        this.spell.setVisible(false);
        this.steal.setVisible(false);
        this.drain.setVisible(false);
        this.done.setVisible(true);
        this.attack.setEnabled(false);
        this.flee.setEnabled(false);
        this.spell.setEnabled(false);
        this.steal.setEnabled(false);
        this.drain.setEnabled(false);
        this.done.setEnabled(true);
    }

    void doResultFinalCleanup() {
        // Final Cleanup
        this.stripExtraNewLine();
        this.battleFrame.pack();
    }

    private class BattleEventHandler extends AbstractAction {
        private static final long serialVersionUID = 20239525230523523L;

        public BattleEventHandler() {
            // Do nothing
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                boolean success = true;
                final String cmd = e.getActionCommand();
                final WindowBattleGUI wbg = WindowBattleGUI.this;
                final AbstractBattle b = MazeRunnerII.getApplication()
                        .getBattle();
                // Clear Message Area
                wbg.clearMessageArea();
                // Display Beginning Stats
                wbg.setStatusMessage("*** Beginning of Round ***");
                b.displayBattleStats();
                wbg.setStatusMessage("*** Beginning of Round ***\n");
                // Do Player Actions
                if (cmd.equals("Attack") || cmd.equals("a")) {
                    // Attack
                    success = b.doPlayerActions(
                            AbstractWindowAIRoutine.ACTION_ATTACK);
                } else if (cmd.equals("Flee") || cmd.equals("f")) {
                    // Try to Flee
                    success = b.doPlayerActions(
                            AbstractWindowAIRoutine.ACTION_FLEE);
                    if (success) {
                        // Strip Extra Newline Character
                        wbg.stripExtraNewLine();
                        // Pack Battle Frame
                        wbg.battleFrame.pack();
                        // Get out of here
                        b.doResult();
                        return;
                    } else {
                        success = b.doPlayerActions(
                                AbstractWindowAIRoutine.ACTION_ATTACK);
                    }
                } else if (cmd.equals("Continue")) {
                    // Battle Done
                    b.battleDone();
                    return;
                } else if (cmd.equals("Cast Spell") || cmd.equals("c")) {
                    // Cast Spell
                    success = b.doPlayerActions(
                            AbstractWindowAIRoutine.ACTION_CAST_SPELL);
                    if (!success) {
                        // Strip Two Extra Newline Characters
                        wbg.stripExtraNewLine();
                        wbg.stripExtraNewLine();
                        // Pack Battle Frame
                        wbg.battleFrame.pack();
                        // Get out of here
                        return;
                    }
                } else if (cmd.equals("Steal") || cmd.equals("s")) {
                    // Steal Money
                    success = b.doPlayerActions(
                            AbstractWindowAIRoutine.ACTION_STEAL);
                } else if (cmd.equals("Drain") || cmd.equals("d")) {
                    // Drain Enemy
                    success = b.doPlayerActions(
                            AbstractWindowAIRoutine.ACTION_DRAIN);
                } else if (cmd.equals("Use Item") || cmd.equals("i")) {
                    // Use Item
                    success = b.doPlayerActions(
                            AbstractWindowAIRoutine.ACTION_USE_ITEM);
                    if (!success) {
                        // Strip Two Extra Newline Characters
                        wbg.stripExtraNewLine();
                        wbg.stripExtraNewLine();
                        // Pack Battle Frame
                        wbg.battleFrame.pack();
                        // Get out of here
                        return;
                    }
                }
                // Do Enemy Actions
                b.executeNextAIAction();
                // Display Active Effects
                b.displayActiveEffects();
                // Maintain Effects
                b.maintainEffects();
                // Display End Stats
                wbg.setStatusMessage("\n*** End of Round ***");
                b.displayBattleStats();
                wbg.setStatusMessage("*** End of Round ***");
                // Check Result
                final int currResult = b.getResult();
                if (currResult != BattleResults.IN_PROGRESS) {
                    b.setResult(currResult);
                    b.doResult();
                } else {
                    // Strip Extra Newline Character
                    wbg.stripExtraNewLine();
                    // Pack Battle Frame
                    wbg.battleFrame.pack();
                }
            } catch (final Throwable t) {
                MazeRunnerII.getErrorLogger().logError(t);
            }
        }
    }
}
