package net.worldwizard.support.resourcemanagers;

public class SoundNames {
    // Package-Protected Constants
    static final String[] SOUND_CATEGORY_NAMES = { "attacks", "battle",
            "changemode", "deaths", "effects", "game", "interact", "inventory",
            "magic", "moving", "talking" };
    static final int[] SOUND_CATEGORY_OFFSETS = { 0, 8, 10, 15, 16, 19, 22, 26,
            29, 31, 36 };
    static final String[] SOUND_NAMES = { "attackhit", "attackmiss", "breathe",
            "chaclunk", "club", "missiledodge", "slimed", "zap", "dropitem",
            "nextround", "angrymob", "cashregisterding", "entershop",
            "partyslain", "song", "death", "bolt", "heal", "shortow", "levelup",
            "logo", "nullevent", "button", "doorslam", "teleport", "warp",
            "booty", "identify", "jumping", "spellselect", "hitobstacle",
            "splashing", "stairs", "walk1", "walk2", "oof" };

    // Private constructor
    private SoundNames() {
        // Do nothing
    }

    // Static methods
    static int getCategoryIndexFromSoundIndex(final int si) {
        for (int x = 0; x < SoundNames.SOUND_CATEGORY_OFFSETS.length - 1; x++) {
            if (si >= SoundNames.SOUND_CATEGORY_OFFSETS[x]
                    && si < SoundNames.SOUND_CATEGORY_OFFSETS[x + 1]) {
                return x;
            }
        }
        return -1;
    }
}
