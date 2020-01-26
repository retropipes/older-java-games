module com.puttysoftware.brainmaze {
    requires java.desktop;
    requires com.puttysoftware.audio.wav;
    requires com.puttysoftware.desktop;
    requires com.puttysoftware.integration;
    requires com.puttysoftware.randomrange;
    requires com.puttysoftware.storage;

    uses javax.sound.sampled.spi.AudioFileReader;
    uses javax.sound.sampled.spi.FormatConversionProvider;
}