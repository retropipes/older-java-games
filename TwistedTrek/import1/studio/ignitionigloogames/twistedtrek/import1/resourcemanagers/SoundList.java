/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers;

public class SoundList {
    // Fields
    private static final String[] allSounds = { "actfail", "barrier", "bomb", "button", "change", "confused", "create",
	    "darkness", "destroy", "dizzy", "down", "drunk", "explode", "finish", "forcefld", "gameover", "generate",
	    "grab", "heal", "highscor", "hurt", "identify", "intopit", "lava", "light", "logo", "pushpull", "sinkblck",
	    "slime", "start", "teleport", "unlock", "up", "walk", "walkfail", "walkice", "walklava", "walkslim",
	    "walkwatr", "walltrap", "water", "battle", "counter", "hit", "levelup", "missed", "victory", "easier",
	    "harder", "arrow", "arrowdie", "shatter", "shop", "transact", "spell", "attack", "bind", "bolt", "defense",
	    "drain", "focus", "ghostaxe", "iceshard", "tornado", "windswrd", "weakness", "potion", "fumble", "crack" };

    public static String[] getAllSoundNames() {
	return SoundList.allSounds;
    }
}
