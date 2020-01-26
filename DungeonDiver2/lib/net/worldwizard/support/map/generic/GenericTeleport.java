/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.generic;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.map.Map;
import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.scripts.game.GameActionCode;
import net.worldwizard.support.scripts.game.GameScript;
import net.worldwizard.support.scripts.game.GameScriptEntry;
import net.worldwizard.support.scripts.game.GameScriptEntryArgument;

public abstract class GenericTeleport extends MapObject {
    // Fields
    private GameScript postMove;

    // Constructors
    protected GenericTeleport() {
        super(false);
        this.setTemplateTransform(new TemplateTransform(0.25, 0.5, 1.0, ""));
    }

    // Scriptability
    @Override
    public GameScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ, final Map map) {
        final RandomRange row = new RandomRange(0, map.getRows() - 1);
        final RandomRange col = new RandomRange(0, map.getColumns() - 1);
        // Create post-move script
        this.postMove = new GameScript();
        final GameScriptEntry act0 = new GameScriptEntry();
        act0.setActionCode(GameActionCode.MOVE);
        act0.addActionArg(new GameScriptEntryArgument(true));
        act0.addActionArg(new GameScriptEntryArgument(false));
        act0.addActionArg(new GameScriptEntryArgument(row.generate()));
        act0.addActionArg(new GameScriptEntryArgument(col.generate()));
        act0.addActionArg(new GameScriptEntryArgument(dirZ));
        act0.finalizeActionArgs();
        this.postMove.addAction(act0);
        final GameScriptEntry act1 = new GameScriptEntry();
        act1.setActionCode(GameActionCode.SOUND);
        act1.addActionArg(
                new GameScriptEntryArgument(GameSoundConstants.SOUND_TELEPORT));
        act1.finalizeActionArgs();
        this.postMove.addAction(act1);
        this.postMove.finalizeActions();
        return this.postMove;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MapConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TELEPORT);
    }

    @Override
    public boolean defersSetProperties() {
        return true;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
