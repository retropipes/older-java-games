package net.worldwizard.mazerunner;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
    // Fields
    private static File file;
    private static AudioInputStream stream;
    private static AudioFileFormat fileFormat;
    private static AudioFormat format;
    private static Clip clip;
    private static DataLine.Info info;
    private static boolean inited;

    public static void init(final String filename) {
        SoundManager.inited = false;
        try {
            SoundManager.file = new File("./Sound/" + filename + ".wav");
            SoundManager.stream = AudioSystem
                    .getAudioInputStream(SoundManager.file);
            SoundManager.fileFormat = AudioSystem
                    .getAudioFileFormat(SoundManager.file);
            SoundManager.format = SoundManager.fileFormat.getFormat();
            SoundManager.info = new DataLine.Info(Clip.class,
                    SoundManager.format);
            if (!AudioSystem.isLineSupported(SoundManager.info)) {
                // Do nothing
            }
            try {
                SoundManager.clip = (Clip) AudioSystem
                        .getLine(SoundManager.info);
                SoundManager.clip.open(SoundManager.stream);
                SoundManager.inited = true;
            } catch (final LineUnavailableException e) {
                // Do nothing
            }
        } catch (final UnsupportedAudioFileException e) {
            // Do nothing
        } catch (final IOException e) {
            // Do nothing
        }
    }

    public static void play() {
        if (SoundManager.inited) {
            SoundManager.clip.loop(0);
            SoundManager.clip.start();
        }
    }
}
