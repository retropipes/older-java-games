/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.ImageColorConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.LogoManager;
import studio.ignitionigloogames.common.random.RandomRange;

public class GenerateTask extends Thread {
    // Fields
    private final JFrame generateFrame;
    private final boolean scratch;

    // Constructors
    public GenerateTask(final boolean startFromScratch) {
        this.scratch = startFromScratch;
        this.setName("Level Generator");
        this.generateFrame = new JFrame("Generating...");
        this.generateFrame.setIconImage(LogoManager.getIconLogo());
        final JProgressBar loadBar = new JProgressBar();
        loadBar.setIndeterminate(true);
        this.generateFrame.getContentPane().add(loadBar);
        this.generateFrame.setResizable(false);
        this.generateFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.generateFrame.pack();
    }

    // Methods
    @Override
    public void run() {
        try {
            this.generateFrame.setVisible(true);
            final Application app = Chrystalz.getApplication();
            final int zoneID = PartyManager.getParty().getZone();
            final int dungeonSize = Chrystalz.getDungeonLevelSize(zoneID);
            Dungeon gameDungeon = app.getDungeonManager().getDungeon();
            if (!this.scratch) {
                app.getGame().disableEvents();
            } else {
                gameDungeon = new Dungeon();
                app.getDungeonManager().setDungeon(gameDungeon);
            }
            gameDungeon.addLevel(dungeonSize, dungeonSize);
            gameDungeon.fillLevelRandomly();
            final RandomRange rR = new RandomRange(0, dungeonSize - 1);
            final RandomRange rC = new RandomRange(0, dungeonSize - 1);
            if (this.scratch) {
                int startR, startC;
                do {
                    startR = rR.generate();
                    startC = rC.generate();
                } while (gameDungeon
                        .getCell(startR, startC, DungeonConstants.LAYER_OBJECT)
                        .isSolid());
                gameDungeon.setStartRow(startR);
                gameDungeon.setStartColumn(startC);
                app.getDungeonManager().setLoaded(true);
                final boolean playerExists = gameDungeon.doesPlayerExist();
                if (playerExists) {
                    gameDungeon.setPlayerToStart();
                    app.getGame().resetViewingWindow();
                }
            } else {
                int startR, startC;
                do {
                    startR = rR.generate();
                    startC = rC.generate();
                } while (gameDungeon
                        .getCell(startR, startC, DungeonConstants.LAYER_OBJECT)
                        .isSolid());
                gameDungeon.setPlayerLocationX(startR);
                gameDungeon.setPlayerLocationY(startC);
                PartyManager.getParty().offsetZone(1);
            }
            gameDungeon.save();
            // Final cleanup
            AbstractGameObject.setTemplateColor(
                    ImageColorConstants.getColorForLevel(zoneID));
            if (this.scratch) {
                app.getGame().stateChanged();
                app.getGame().playDungeon();
            } else {
                app.getGame().resetViewingWindow();
                app.getGame().enableEvents();
                app.getGame().redrawDungeon();
            }
        } catch (final Throwable t) {
            Chrystalz.getErrorLogger().logError(t);
        } finally {
            this.generateFrame.setVisible(false);
        }
    }
}
