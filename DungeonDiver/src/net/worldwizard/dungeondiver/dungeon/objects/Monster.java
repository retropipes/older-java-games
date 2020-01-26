package net.worldwizard.dungeondiver.dungeon.objects;

import javax.swing.ImageIcon;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.Preferences;
import net.worldwizard.map.NDimensionalLocation;
import net.worldwizard.randomnumbers.RandomRange;

public class Monster extends GenericNSRSBObject {
    // Fields
    private static final long serialVersionUID = 4433251363460882L;
    private DungeonObject savedObject;

    // Constructors
    public Monster() {
        super(false, "Monster", 20);
        this.savedObject = new Tile();
    }

    @Override
    public void moveOntoHook() {
        DungeonDiver.getHoldingBag().getBattle().doBattle();
        final NDimensionalLocation playerLoc = DungeonDiver.getHoldingBag()
                .getDungeonGUI().getPlayerLocation();
        DungeonDiver.getHoldingBag().getDungeonGUI().postBattle(playerLoc, this,
                true);
    }

    @Override
    public boolean shouldCache() {
        return false;
    }

    public DungeonObject getSavedObject() {
        return this.savedObject;
    }

    public void setSavedObject(final DungeonObject newSavedObject) {
        this.savedObject = newSavedObject;
    }

    @Override
    public void invalidateAppearance() {
        this.savedObject.invalidateAppearance();
        super.invalidateAppearance();
    }

    @Override
    public void updateAppearance() {
        this.savedObject.updateAppearance();
        super.updateAppearance();
    }

    @Override
    public void playerMoveHook(final int xLoc, final int yLoc) {
        // Move the monster
        final RandomRange r = new RandomRange(1, 4);
        int move, xMove, yMove;
        move = (int) r.generate();
        if (move == 1) {
            // Move left
            xMove = -1;
            yMove = 0;
        } else if (move == 2) {
            // Move right
            xMove = 1;
            yMove = 0;
        } else if (move == 3) {
            // Move up
            xMove = 0;
            yMove = -1;
        } else {
            // Move down
            xMove = 0;
            yMove = 1;
        }
        DungeonDiver.getHoldingBag().getDungeonGUI()
                .updateMonsterPosition(xMove, yMove, xLoc, yLoc, this);
    }

    @Override
    public ImageIcon getGameAppearance() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(Preferences.MONSTERS_ON_MAP_ENABLED);
        if (enabled) {
            return super.getGameAppearance();
        } else {
            return this.savedObject.getGameAppearance();
        }
    }
}
