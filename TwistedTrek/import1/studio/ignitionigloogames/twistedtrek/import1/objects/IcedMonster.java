/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import java.io.IOException;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.ArrowTypeConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericDungeonObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObjectList;
import studio.ignitionigloogames.twistedtrek.import1.generic.TypeConstants;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public class IcedMonster extends GenericDungeonObject {
    // Fields and Constants
    private MazeObject savedObject;
    private static final int ICE_LENGTH = 20;

    // Constructors
    public IcedMonster(final MazeObject saved) {
        super(true);
        this.savedObject = saved;
        this.activateTimer(IcedMonster.ICE_LENGTH);
    }

    @Override
    public IcedMonster clone() {
        final IcedMonster copy = new IcedMonster(this.savedObject.clone());
        return copy;
    }

    @Override
    public boolean hasAdditionalProperties() {
        return true;
    }

    public MazeObject getSavedObject() {
        return this.savedObject;
    }

    public void setSavedObject(final MazeObject newSavedObject) {
        this.savedObject = newSavedObject;
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
            final int dirY, final int arrowType, final ObjectInventory inv) {
        // Extend iced effect, if hit by an ice arrow
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            this.extendTimer(IcedMonster.ICE_LENGTH);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Transform into a normal monster
        final int pz = Import1.getApplication().getGameManager().getPlayerManager().getPlayerLocationZ();
        final int pw = Import1.getApplication().getGameManager().getPlayerManager().getPlayerLocationW();
        Import1.getApplication().getGameManager().morph(new Monster(this.savedObject), dirX, dirY, pz, pw);
    }

    @Override
    public String getName() {
        return "Iced Monster";
    }

    @Override
    public String getPluralName() {
        return "Iced Monsters";
    }

    @Override
    public String getDescription() {
        return "Iced Monsters cannot move or fight, but the ice coating will eventually wear off.";
    }

    @Override
    public byte getObjectID() {
        return (byte) 0;
    }

    @Override
    public int getCustomFormat() {
        return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    protected void writeMazeObjectHook(final XDataWriter writer) throws IOException {
        this.savedObject.writeMazeObject(writer);
    }

    @Override
    protected MazeObject readMazeObjectHook(final XDataReader reader, final int formatVersion) throws IOException {
        final MazeObjectList objectList = Import1.getApplication().getObjects();
        this.savedObject = objectList.readMazeObject(reader, formatVersion);
        return this;
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_REACTS_TO_ICE);
    }
}
