/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.game;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.ImageColorConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.LogoManager;

public class LevelLoadTask extends Thread {
    // Fields
    private final JFrame loadFrame;
    private final int level;

    // Constructors
    public LevelLoadTask(final int offset) {
        this.level = offset;
        this.setName("Level Loader");
        this.loadFrame = new JFrame("Loading...");
        this.loadFrame.setIconImage(LogoManager.getIconLogo());
        final JProgressBar loadBar = new JProgressBar();
        loadBar.setIndeterminate(true);
        this.loadFrame.getContentPane().add(loadBar);
        this.loadFrame.setResizable(false);
        this.loadFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.loadFrame.pack();
    }

    // Methods
    @Override
    public void run() {
        try {
            this.loadFrame.setVisible(true);
            final Application app = Chrystalz.getApplication();
            final Dungeon gameDungeon = app.getDungeonManager().getDungeon();
            app.getGame().disableEvents();
            gameDungeon.switchLevelOffset(this.level);
            gameDungeon.offsetPlayerLocationW(this.level);
            PartyManager.getParty().offsetZone(this.level);
            AbstractGameObject.setTemplateColor(ImageColorConstants
                    .getColorForLevel(PartyManager.getParty().getZone()));
            app.getGame().resetViewingWindow();
            app.getGame().enableEvents();
            app.getGame().redrawDungeon();
        } catch (final Exception ex) {
            Chrystalz.getErrorLogger().logError(ex);
        } finally {
            this.loadFrame.setVisible(false);
        }
    }
}
