package studio.ignitionigloogames.dungeondiver1.dungeon.buffs;

public class Rotated extends DungeonBuff {
    // Constants
    public static final int ROTATED_STATE_CLOCKWISE = 1;
    public static final int ROTATED_STATE_COUNTERCLOCKWISE = 2;

    // Constructor
    public Rotated(final int newRounds, final int rotationState) {
        super("Rotated", 0, DungeonBuff.WHAT_MOVEMENT, newRounds);
        this.power = rotationState;
    }

    @Override
    public int customUseLogic(final int arg) {
        switch (arg) {
        case DungeonBuff.MOVE_UP:
            switch (this.power) {
            case ROTATED_STATE_COUNTERCLOCKWISE:
                return DungeonBuff.MOVE_LEFT;
            case ROTATED_STATE_CLOCKWISE:
                return DungeonBuff.MOVE_RIGHT;
            default:
                break;
            }
            break;
        case DungeonBuff.MOVE_DOWN:
            switch (this.power) {
            case ROTATED_STATE_COUNTERCLOCKWISE:
                return DungeonBuff.MOVE_RIGHT;
            case ROTATED_STATE_CLOCKWISE:
                return DungeonBuff.MOVE_LEFT;
            default:
                break;
            }
            break;
        case DungeonBuff.MOVE_LEFT:
            switch (this.power) {
            case ROTATED_STATE_COUNTERCLOCKWISE:
                return DungeonBuff.MOVE_DOWN;
            case ROTATED_STATE_CLOCKWISE:
                return DungeonBuff.MOVE_UP;
            default:
                break;
            }
            break;
        case DungeonBuff.MOVE_RIGHT:
            switch (this.power) {
            case ROTATED_STATE_COUNTERCLOCKWISE:
                return DungeonBuff.MOVE_UP;
            case ROTATED_STATE_CLOCKWISE:
                return DungeonBuff.MOVE_DOWN;
            default:
                break;
            }
            break;
        default:
            break;
        }
        return 0;
    }
}