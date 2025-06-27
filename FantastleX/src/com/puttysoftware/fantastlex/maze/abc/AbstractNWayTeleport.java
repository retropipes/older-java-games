/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.abc;

import java.util.Arrays;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.editor.MazeEditorLogic;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public abstract class AbstractNWayTeleport extends AbstractTeleport {
    // Fields
    private int[] destRowN;
    private int[] destColN;
    private int[] destFloorN;
    private int ways;
    private int memory;
    private String[] destinationNames;
    private static String[] nWayChoices = new String[] { "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "10" };

    // Constructors
    protected AbstractNWayTeleport(final int N, final int attrName) {
        super(true, attrName);
        this.memory = 0;
        this.ways = N - 1;
        this.destRowN = new int[9];
        this.destColN = new int[9];
        this.destFloorN = new int[9];
        this.destinationNames = new String[N];
        for (int d = 0; d < N; d++) {
            this.destinationNames[d] = "Destination " + (d + 1);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(this.destColN);
        result = prime * result + Arrays.hashCode(this.destFloorN);
        result = prime * result + Arrays.hashCode(this.destRowN);
        result = prime * result + Arrays.hashCode(this.destinationNames);
        result = prime * result + this.ways;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof AbstractNWayTeleport)) {
            return false;
        }
        final AbstractNWayTeleport other = (AbstractNWayTeleport) obj;
        if (!Arrays.equals(this.destColN, other.destColN)) {
            return false;
        }
        if (!Arrays.equals(this.destFloorN, other.destFloorN)) {
            return false;
        }
        if (!Arrays.equals(this.destRowN, other.destRowN)) {
            return false;
        }
        if (!Arrays.equals(this.destinationNames, other.destinationNames)) {
            return false;
        }
        if (this.ways != other.ways) {
            return false;
        }
        return true;
    }

    @Override
    public AbstractNWayTeleport clone() {
        final AbstractNWayTeleport copy = (AbstractNWayTeleport) super.clone();
        copy.destColN = this.destColN.clone();
        copy.destFloorN = this.destFloorN.clone();
        copy.destRowN = this.destRowN.clone();
        return copy;
    }

    // Accessor methods
    public int getDestinationRowN(final int n) {
        return this.destRowN[n - 1];
    }

    public int getDestinationColumnN(final int n) {
        return this.destColN[n - 1];
    }

    public int getDestinationFloorN(final int n) {
        return this.destFloorN[n - 1];
    }

    public int getDestinationCount() {
        return this.ways + 1;
    }

    // Transformer methods
    public void setDestinationRowN(final int n, final int destinationRow) {
        this.destRowN[n - 1] = destinationRow;
    }

    public void setDestinationColumnN(final int n,
            final int destinationColumn) {
        this.destColN[n - 1] = destinationColumn;
    }

    public void setDestinationFloorN(final int n, final int destinationFloor) {
        this.destFloorN[n - 1] = destinationFloor;
    }

    public void setDestinationCount(final int n) {
        this.ways = n - 1;
        this.destinationNames = new String[this.ways + 1];
        for (int d = 0; d < this.ways + 1; d++) {
            this.destinationNames[d] = "Destination " + (d + 1);
        }
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        String input = null;
        int n = -1;
        while (input == null) {
            input = CommonDialogs.showInputDialog("Which Destination?",
                    "FantastleX", this.destinationNames,
                    this.destinationNames[this.memory]);
            if (input != null) {
                for (int z = 0; z < this.destinationNames.length; z++) {
                    if (input.equals(this.destinationNames[z])) {
                        n = z;
                        this.memory = z;
                        break;
                    }
                }
            }
        }
        if (n != -1) {
            if (n > 0) {
                app.getGameManager().updatePositionAbsolute(
                        this.getDestinationRowN(n),
                        this.getDestinationColumnN(n),
                        this.getDestinationFloorN(n));
            } else {
                app.getGameManager().updatePositionAbsolute(
                        this.getDestinationRow(), this.getDestinationColumn(),
                        this.getDestinationFloor());
            }
            SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
        }
    }

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TELEPORT);
    }

    @Override
    public void editorPlaceHook() {
        final String respN = CommonDialogs.showInputDialog(
                "How Many Destinations?", "Editor",
                AbstractNWayTeleport.nWayChoices,
                AbstractNWayTeleport.nWayChoices[0]);
        if (respN == null) {
            return;
        }
        this.ways = Integer.parseInt(respN) - 1;
        final MazeEditorLogic me = FantastleX.getApplication().getEditor();
        me.setNWayDestCount(this.ways + 1);
    }

    @Override
    public boolean defersSetProperties() {
        return true;
    }

    @Override
    public int getCustomProperty(final int propID) {
        switch (propID) {
            case 1:
                return this.getDestinationRow();
            case 2:
                return this.getDestinationColumn();
            case 3:
                return this.getDestinationFloor();
            case 4:
                return this.getDestinationRowN(1);
            case 5:
                return this.getDestinationColumnN(1);
            case 6:
                return this.getDestinationFloorN(1);
            case 7:
                return this.getDestinationRowN(2);
            case 8:
                return this.getDestinationColumnN(2);
            case 9:
                return this.getDestinationFloorN(2);
            case 10:
                return this.getDestinationRowN(3);
            case 11:
                return this.getDestinationColumnN(3);
            case 12:
                return this.getDestinationFloorN(3);
            case 13:
                return this.getDestinationRowN(4);
            case 14:
                return this.getDestinationColumnN(4);
            case 15:
                return this.getDestinationFloorN(4);
            case 16:
                return this.getDestinationRowN(5);
            case 17:
                return this.getDestinationColumnN(5);
            case 18:
                return this.getDestinationFloorN(5);
            case 19:
                return this.getDestinationRowN(6);
            case 20:
                return this.getDestinationColumnN(6);
            case 21:
                return this.getDestinationFloorN(6);
            case 22:
                return this.getDestinationRowN(7);
            case 23:
                return this.getDestinationColumnN(7);
            case 24:
                return this.getDestinationFloorN(7);
            case 25:
                return this.getDestinationRowN(8);
            case 26:
                return this.getDestinationColumnN(8);
            case 27:
                return this.getDestinationFloorN(8);
            case 28:
                return this.getDestinationRowN(9);
            case 29:
                return this.getDestinationColumnN(9);
            case 30:
                return this.getDestinationFloorN(9);
            case 31:
                return this.getDestinationCount();
            default:
                return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
        }
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        switch (propID) {
            case 1:
                this.setDestinationRow(value);
                break;
            case 2:
                this.setDestinationColumn(value);
                break;
            case 3:
                this.setDestinationFloor(value);
                break;
            case 4:
                this.setDestinationRowN(1, value);
                break;
            case 5:
                this.setDestinationColumnN(1, value);
                break;
            case 6:
                this.setDestinationFloorN(1, value);
                break;
            case 7:
                this.setDestinationRowN(2, value);
                break;
            case 8:
                this.setDestinationColumnN(2, value);
                break;
            case 9:
                this.setDestinationFloorN(2, value);
                break;
            case 10:
                this.setDestinationRowN(3, value);
                break;
            case 11:
                this.setDestinationColumnN(3, value);
                break;
            case 12:
                this.setDestinationFloorN(3, value);
                break;
            case 13:
                this.setDestinationRowN(4, value);
                break;
            case 14:
                this.setDestinationColumnN(4, value);
                break;
            case 15:
                this.setDestinationFloorN(4, value);
                break;
            case 16:
                this.setDestinationRowN(5, value);
                break;
            case 17:
                this.setDestinationColumnN(5, value);
                break;
            case 18:
                this.setDestinationFloorN(5, value);
                break;
            case 19:
                this.setDestinationRowN(6, value);
                break;
            case 20:
                this.setDestinationColumnN(6, value);
                break;
            case 21:
                this.setDestinationFloorN(6, value);
                break;
            case 22:
                this.setDestinationRowN(7, value);
                break;
            case 23:
                this.setDestinationColumnN(7, value);
                break;
            case 24:
                this.setDestinationFloorN(7, value);
                break;
            case 25:
                this.setDestinationRowN(8, value);
                break;
            case 26:
                this.setDestinationColumnN(8, value);
                break;
            case 27:
                this.setDestinationFloorN(8, value);
                break;
            case 28:
                this.setDestinationRowN(9, value);
                break;
            case 29:
                this.setDestinationColumnN(9, value);
                break;
            case 30:
                this.setDestinationFloorN(9, value);
                break;
            case 31:
                this.setDestinationCount(value);
                break;
            default:
                break;
        }
    }

    @Override
    public int getCustomFormat() {
        return 31;
    }
}
