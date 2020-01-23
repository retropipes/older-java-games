/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

public class SoundConstants {
    // Public Sound Constants
    public static final int ATTACK_HIT = 0;
    public static final int AXE_HIT = 1;
    public static final int BLINDNESS = 2;
    public static final int BOLT_SPELL = 3;
    public static final int BOSS_DIE = 4;
    public static final int BUBBLE_SPELL = 5;
    public static final int BUFF_1 = 6;
    public static final int BUFF_2 = 7;
    public static final int CLICK = 8;
    public static final int COLD_SPELL = 9;
    public static final int CONFUSION_SPELL = 10;
    public static final int COUNTER = 11;
    public static final int CRITICAL_HIT = 12;
    public static final int DAGGER_HIT = 13;
    public static final int DANGER = 14;
    public static final int DEATH = 15;
    public static final int DEBUFF_1 = 16;
    public static final int DEBUFF_2 = 17;
    public static final int DEFEATED = 18;
    public static final int DISPEL_EFFECT = 19;
    // public static final int UNUSED_1 = 20;
    public static final int DRAIN_SPELL = 21;
    public static final int DUMBFOUND_SPELL = 22;
    public static final int ERROR = 23;
    public static final int EXPLODE_SPELL = 24;
    public static final int FAILED = 25;
    public static final int FATAL_ERROR = 26;
    public static final int FIGHT = 27;
    public static final int FIREBALL_SPELL = 28;
    public static final int FOCUS_SPELL = 29;
    public static final int FREEZE_SPELL = 30;
    public static final int FUMBLE = 31;
    public static final int HAMMER_HIT = 32;
    public static final int WALK_ICE = 33;
    public static final int LEVEL_UP = 34;
    public static final int MELT_SPELL = 35;
    public static final int MISSED = 36;
    public static final int MONSTER_HIT = 37;
    public static final int NEXT_ROUND = 38;
    public static final int PLAYER_UP = 39;
    public static final int RUN_AWAY = 40;
    public static final int SHOP = 41;
    public static final int CAST_SPELL = 42;
    public static final int STAFF_HIT = 43;
    public static final int STEAM_SPELL = 44;
    public static final int SWORD_HIT = 45;
    public static final int TRANSACT = 46;
    // public static final int UNUSED_2 = 47;
    public static final int VICTORY = 48;
    public static final int WALK = 49;
    public static final int WAND_HIT = 50;
    public static final int WEAKNESS_SPELL = 51;
    public static final int WIN_GAME = 52;
    public static final int ZAP_SPELL = 53;
    public static final int HEAL_SPELL = 54;
    public static final int DOOR_CLOSES = 55;
    public static final int DOOR_OPENS = 56;
    public static final int EQUIP = 57;
    public static final int MONSTER_SPELL = 58;
    public static final int SPECIAL = 59;
    private static final String[] NAMES = SoundDataManager.getSoundData();

    // Private constructor
    private SoundConstants() {
        // Do nothing
    }

    static String getSoundName(final int ID) {
        return SoundConstants.NAMES[ID];
    }
}