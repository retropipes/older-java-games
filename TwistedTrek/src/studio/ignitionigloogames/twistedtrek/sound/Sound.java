/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.sound;

import studio.ignitionigloogames.audio.wav.WavFactory;

public final class Sound {
    public static void play(final String soundName) {
	Sound.playSound(soundName);
    }

    private static void playSound(final String soundName) {
	WavFactory.loadResource(Sound.class.getResource("/assets/sounds/" + soundName + ".wav")).start();
    }

    public static void actionFailed() {
	Sound.playSound("action-failed");
    }

    public static void buff1() {
	Sound.playSound("buff-1");
    }

    public static void crack() {
	Sound.playSound("crack");
    }

    public static void defeat() {
	Sound.playSound("defeat");
    }

    public static void enemyBluntHit() {
	Sound.playSound("enemy-blunt-hit");
    }

    public static void enemyDeath() {
	Sound.playSound("death");
    }

    public static void equip() {
	Sound.playSound("equip");
    }

    public static void fireArrow() {
	Sound.playSound("arrow-shoot");
    }

    public static void fumble() {
	Sound.playSound("fumble");
    }

    public static void grabKey() {
	Sound.playSound("grab-key");
    }

    public static void grabNonKey() {
	Sound.playSound("grab-other");
    }

    public static void playerBladedHit() {
	Sound.playSound("player-bladed-hit");
    }

    public static void castSpell() {
	Sound.playSound("cast-spell");
    }

    public static void stairsDown() {
	Sound.playSound("stairs-down");
    }

    public static void stairsUp() {
	Sound.playSound("stairs-up");
    }

    public static void walk() {
	Sound.playSound("walk");
    }

    public static void winGame() {
	Sound.playSound("win-game");
    }

    public static void levelUp() {
	Sound.playSound("level-up");
    }
}
