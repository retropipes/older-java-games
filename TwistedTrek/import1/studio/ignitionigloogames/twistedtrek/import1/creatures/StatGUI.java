/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.creatures;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import studio.ignitionigloogames.images.BufferedImageIcon;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.GraphicsManager;

public class StatGUI {
	// Fields
	private Container statsPane;
	private JLabel hpLabel;
	private JLabel mpLabel;
	private JLabel mlLabel;
	private JLabel goldLabel;
	private JLabel attackLabel;
	private JLabel defenseLabel;
	private JLabel xpLabel;

	// Constructors
	public StatGUI() {
		this.setUpGUI();
	}

	// Methods
	public Container getStatsPane() {
		return this.statsPane;
	}

	public void updateStats() {
		final PlayerCharacter pc = PCManager.getPlayer();
		this.hpLabel.setText(pc.getHPString());
		this.mpLabel.setText(pc.getMPString());
		this.mlLabel.setText(Integer.toString(pc.getMonsterLevel()));
		this.goldLabel.setText(Integer.toString(pc.getGold()));
		this.attackLabel.setText(Integer.toString(pc.getAttack()));
		this.defenseLabel.setText(Integer.toString(pc.getDefense()));
		this.xpLabel.setText(pc.getXPString());
	}

	private void setUpGUI() {
		this.statsPane = new Container();
		this.statsPane.setLayout(new GridLayout(7, 1));
		this.hpLabel = new JLabel("", null, SwingConstants.LEFT);
		this.mpLabel = new JLabel("", null, SwingConstants.LEFT);
		this.mlLabel = new JLabel("", null, SwingConstants.LEFT);
		this.goldLabel = new JLabel("", null, SwingConstants.LEFT);
		this.attackLabel = new JLabel("", null, SwingConstants.LEFT);
		this.defenseLabel = new JLabel("", null, SwingConstants.LEFT);
		this.xpLabel = new JLabel("", null, SwingConstants.LEFT);
		this.statsPane.add(this.hpLabel);
		this.statsPane.add(this.mpLabel);
		this.statsPane.add(this.mlLabel);
		this.statsPane.add(this.goldLabel);
		this.statsPane.add(this.attackLabel);
		this.statsPane.add(this.defenseLabel);
		this.statsPane.add(this.xpLabel);
	}

	public void updateGUI() {
		final BufferedImageIcon hpImage = GraphicsManager.getStatImage("health");
		this.hpLabel.setIcon(hpImage);
		final BufferedImageIcon mpImage = GraphicsManager.getStatImage("magic");
		this.mpLabel.setIcon(mpImage);
		final BufferedImageIcon mlImage = GraphicsManager.getStatImage("ml");
		this.mlLabel.setIcon(mlImage);
		final BufferedImageIcon goldImage = GraphicsManager.getStatImage("gold");
		this.goldLabel.setIcon(goldImage);
		final BufferedImageIcon attackImage = GraphicsManager.getStatImage("attack");
		this.attackLabel.setIcon(attackImage);
		final BufferedImageIcon defenseImage = GraphicsManager.getStatImage("defense");
		this.defenseLabel.setIcon(defenseImage);
		final BufferedImageIcon xpImage = GraphicsManager.getStatImage("xp");
		this.xpLabel.setIcon(xpImage);
	}
}
