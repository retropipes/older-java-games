package studio.ignitionigloogames.audio.wav;

import java.net.URL;

public abstract class WavFactory extends Thread {
    // Constants
    protected static final int EXTERNAL_BUFFER_SIZE = 4096; // 4Kb

    // Constructor
    protected WavFactory() {
        super();
    }

    // Methods
    public abstract void stopLoop();

    // Factories
    public static WavFactory loadFile(final String file) {
        return new WavFile(file);
    }

    public static WavFactory loadResource(final URL resource) {
        return new WavResource(resource);
    }
}
