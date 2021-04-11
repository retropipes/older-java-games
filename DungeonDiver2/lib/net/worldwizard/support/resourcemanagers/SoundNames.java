package net.worldwizard.support.resourcemanagers;

public class SoundNames {
    // Package-Protected Constants
    static final String[] SOUND_CATEGORY_NAMES = { "attacks", "battle",
            "changemode", "deaths", "effects", "game", "interact", "inventory",
            "magic", "moving", "talking" };
    static final int[] SOUND_CATEGORY_OFFSETS = { 8, 10, 15, 16, 52, 79, 119,
            141, 159, 181, 209 };
    static final String[] SOUND_NAMES = { "attackhit", "attackmiss", "breathe",
            "chaclunk", "club", "missiledodge", "slimed", "zap", "dropitem",
            "nextround", "angrymob", "cashregisterding", "entershop",
            "partyslain", "song", "death", "die1", "die2", "die3", "die4",
            "die5", "die6", "killpc", "monsterdead", "slayfemale", "slaymale",
            "slayother1", "slayother2", "slaysqueal", "blindness", "blotto",
            "boing", "bolt", "bonk", "bottles", "bubbles", "bwabble", "claps",
            "confusion", "disease", "drain", "explosion", "fullheal", "heal",
            "hitbumper", "hiteffect1", "hiteffect2", "hiteffect3", "hiteffect4",
            "hpshuffle", "huh", "lightning", "paralyze", "petrify",
            "plasmaexplode", "prout", "shortcough", "shortow", "sleep",
            "smallexplode", "spell", "spelleffectheal", "spellhitobject",
            "vibratingstrike", "weakness", "bigexplode", "bloomp", "bloop",
            "boinkdown", "boinkup", "bombom", "bubbledip", "bwee", "ca-click",
            "coolclick", "earthshake", "endofmagic", "generationerror",
            "generationgood", "glass", "laser", "levelup", "logo",
            "nextprevious", "nuk", "nullevent", "pops", "sensitive", "splash",
            "twinkle", "underwaterlaser", "wingflap", "button", "crack",
            "create", "curtains", "damagetrap", "dang", "darn", "deposit1",
            "deposit2", "deposit3", "destroy", "dig", "doorclose", "dooropen",
            "doorslam", "excuseme", "fail", "gained", "lever", "littleboom",
            "lockopen", "messagenod", "metaldoor", "moongate", "movewall",
            "openchest", "phasing", "pinballbumper", "portcullis", "scribble",
            "seegrowl", "sinkship", "slamshut", "slide", "smash", "teleport",
            "trap", "unlock", "warp", "waterfall", "booty", "bow", "chomp",
            "clotharmor", "coins", "coinsjingling", "drink", "dripitybeep",
            "eating", "findgold", "identify", "join", "light", "metalarmor",
            "mix", "movemoney", "pool", "scan", "split", "swallow", "swap",
            "takeitem", "castspell", "energyblast", "jumping", "magespell",
            "poison", "priestspell", "resistmagic", "spell_launch1",
            "spell_launch2", "spell_launch3", "spell_launch4", "spell_launch5",
            "spell_launch6", "spell_launch7", "spell_launch8", "spell_launch9",
            "spellselect", "summonboing", "collision", "grab", "gravel",
            "hitobstacle", "horsestep", "mounthorse", "splashing", "squish",
            "squishstep", "stairs", "walk", "walk", "walkcarpet", "walkcave",
            "walkcity", "walkinvisible", "walkmud", "walkroad", "walkrocks",
            "walktile", "walkwater", "walkwoods", "argh", "growl1", "growl2",
            "growl3", "growl4", "hallelujah", "hello", "hey", "hi", "mmm",
            "monstershout", "monstertalk", "monsteruh", "oof", "oow", "ouch",
            "oww", "ressurectdeath", "scream", "sneeze", "spit", "talk1",
            "talk2", "talk3", "talk4", "whatisit", "womanscream", "yawn" };

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
