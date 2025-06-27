package studio.ignitionigloogames.dungeondiver1.dungeon.buffs;

import studio.ignitionigloogames.dungeondiver1.utilities.RandomRange;

public class Confusion extends DungeonBuff {
    // Private Constants
    private static final int CONFUSED_STATE_UDRL = 1;
    private static final int CONFUSED_STATE_ULDR = 2;
    private static final int CONFUSED_STATE_ULRD = 3;
    private static final int CONFUSED_STATE_URDL = 4;
    private static final int CONFUSED_STATE_URLD = 5;
    private static final int CONFUSED_STATE_DULR = 6;
    private static final int CONFUSED_STATE_DURL = 7;
    private static final int CONFUSED_STATE_DLUR = 8;
    private static final int CONFUSED_STATE_DLRU = 9;
    private static final int CONFUSED_STATE_DRUL = 10;
    private static final int CONFUSED_STATE_DRLU = 11;
    private static final int CONFUSED_STATE_LDUR = 12;
    private static final int CONFUSED_STATE_LDRU = 13;
    private static final int CONFUSED_STATE_LUDR = 14;
    private static final int CONFUSED_STATE_LURD = 15;
    private static final int CONFUSED_STATE_LRDU = 16;
    private static final int CONFUSED_STATE_LRUD = 17;
    private static final int CONFUSED_STATE_RDLU = 18;
    private static final int CONFUSED_STATE_RDUL = 19;
    private static final int CONFUSED_STATE_RLDU = 20;
    private static final int CONFUSED_STATE_RLUD = 21;
    private static final int CONFUSED_STATE_RUDL = 22;
    private static final int CONFUSED_STATE_RULD = 23;
    private static final int MIN_CONFUSED_STATE = 1;
    private static final int MAX_CONFUSED_STATE = 23;

    // Constructor
    public Confusion(final int newRounds) {
        super("Confused", 0, DungeonBuff.WHAT_MOVEMENT, newRounds);
        final RandomRange r = new RandomRange(Confusion.MIN_CONFUSED_STATE,
                Confusion.MAX_CONFUSED_STATE);
        this.power = r.generate();
    }

    @Override
    public int customUseLogic(final int arg) {
        switch (arg) {
            case DungeonBuff.MOVE_UP:
                switch (this.power) {
                    case CONFUSED_STATE_UDRL:
                    case CONFUSED_STATE_ULDR:
                    case CONFUSED_STATE_ULRD:
                    case CONFUSED_STATE_URDL:
                    case CONFUSED_STATE_URLD:
                        return DungeonBuff.MOVE_UP;
                    case CONFUSED_STATE_DULR:
                    case CONFUSED_STATE_DURL:
                    case CONFUSED_STATE_DLUR:
                    case CONFUSED_STATE_DLRU:
                    case CONFUSED_STATE_DRUL:
                    case CONFUSED_STATE_DRLU:
                        return DungeonBuff.MOVE_DOWN;
                    case CONFUSED_STATE_LDUR:
                    case CONFUSED_STATE_LDRU:
                    case CONFUSED_STATE_LUDR:
                    case CONFUSED_STATE_LURD:
                    case CONFUSED_STATE_LRDU:
                    case CONFUSED_STATE_LRUD:
                        return DungeonBuff.MOVE_LEFT;
                    case CONFUSED_STATE_RDLU:
                    case CONFUSED_STATE_RDUL:
                    case CONFUSED_STATE_RLDU:
                    case CONFUSED_STATE_RLUD:
                    case CONFUSED_STATE_RUDL:
                    case CONFUSED_STATE_RULD:
                        return DungeonBuff.MOVE_RIGHT;
                    default:
                        break;
                }
                break;
            case DungeonBuff.MOVE_DOWN:
                switch (this.power) {
                    case CONFUSED_STATE_DULR:
                    case CONFUSED_STATE_DURL:
                    case CONFUSED_STATE_LUDR:
                    case CONFUSED_STATE_LURD:
                    case CONFUSED_STATE_RUDL:
                    case CONFUSED_STATE_RULD:
                        return DungeonBuff.MOVE_UP;
                    case CONFUSED_STATE_UDRL:
                    case CONFUSED_STATE_LDUR:
                    case CONFUSED_STATE_LDRU:
                    case CONFUSED_STATE_RDLU:
                    case CONFUSED_STATE_RDUL:
                        return DungeonBuff.MOVE_DOWN;
                    case CONFUSED_STATE_ULDR:
                    case CONFUSED_STATE_ULRD:
                    case CONFUSED_STATE_DLUR:
                    case CONFUSED_STATE_DLRU:
                    case CONFUSED_STATE_RLDU:
                    case CONFUSED_STATE_RLUD:
                        return DungeonBuff.MOVE_LEFT;
                    case CONFUSED_STATE_URDL:
                    case CONFUSED_STATE_URLD:
                    case CONFUSED_STATE_DRUL:
                    case CONFUSED_STATE_DRLU:
                    case CONFUSED_STATE_LRDU:
                    case CONFUSED_STATE_LRUD:
                        return DungeonBuff.MOVE_RIGHT;
                    default:
                        break;
                }
                break;
            case DungeonBuff.MOVE_LEFT:
                switch (this.power) {
                    case CONFUSED_STATE_DLUR:
                    case CONFUSED_STATE_DRUL:
                    case CONFUSED_STATE_LDUR:
                    case CONFUSED_STATE_LRUD:
                    case CONFUSED_STATE_RDUL:
                    case CONFUSED_STATE_RLUD:
                        return DungeonBuff.MOVE_UP;
                    case CONFUSED_STATE_ULDR:
                    case CONFUSED_STATE_URDL:
                    case CONFUSED_STATE_LUDR:
                    case CONFUSED_STATE_LRDU:
                    case CONFUSED_STATE_RLDU:
                    case CONFUSED_STATE_RUDL:
                        return DungeonBuff.MOVE_DOWN;
                    case CONFUSED_STATE_URLD:
                    case CONFUSED_STATE_DULR:
                    case CONFUSED_STATE_DRLU:
                    case CONFUSED_STATE_RDLU:
                    case CONFUSED_STATE_RULD:
                        return DungeonBuff.MOVE_LEFT;
                    case CONFUSED_STATE_DURL:
                    case CONFUSED_STATE_DLRU:
                    case CONFUSED_STATE_UDRL:
                    case CONFUSED_STATE_ULRD:
                    case CONFUSED_STATE_LDRU:
                    case CONFUSED_STATE_LURD:
                        return DungeonBuff.MOVE_RIGHT;
                    default:
                        break;
                }
                break;
            case DungeonBuff.MOVE_RIGHT:
                switch (this.power) {
                    case CONFUSED_STATE_DLRU:
                    case CONFUSED_STATE_DRLU:
                    case CONFUSED_STATE_LDRU:
                    case CONFUSED_STATE_LRDU:
                    case CONFUSED_STATE_RDLU:
                    case CONFUSED_STATE_RLDU:
                        return DungeonBuff.MOVE_UP;
                    case CONFUSED_STATE_ULRD:
                    case CONFUSED_STATE_URLD:
                    case CONFUSED_STATE_LURD:
                    case CONFUSED_STATE_LRUD:
                    case CONFUSED_STATE_RLUD:
                    case CONFUSED_STATE_RULD:
                        return DungeonBuff.MOVE_DOWN;
                    case CONFUSED_STATE_UDRL:
                    case CONFUSED_STATE_URDL:
                    case CONFUSED_STATE_DURL:
                    case CONFUSED_STATE_DRUL:
                    case CONFUSED_STATE_RDUL:
                    case CONFUSED_STATE_RUDL:
                        return DungeonBuff.MOVE_LEFT;
                    case CONFUSED_STATE_ULDR:
                    case CONFUSED_STATE_DULR:
                    case CONFUSED_STATE_DLUR:
                    case CONFUSED_STATE_LDUR:
                    case CONFUSED_STATE_LUDR:
                        return DungeonBuff.MOVE_RIGHT;
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