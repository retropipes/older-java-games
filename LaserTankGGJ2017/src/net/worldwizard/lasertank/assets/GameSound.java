package net.worldwizard.lasertank.assets;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class GameSound {
    // Fields
    private final URL location;

    // Constructor
    public GameSound(final URL u) {
	super();
	this.location = u;
    }

    public void play() {
	if (this.location != null) {
	    new Player(this.location).start();
	}
    }

    private static class Player extends Thread {
	// Fields
	private final URL u;
	private final int BUFFER_SIZE = 128000;
	private AudioInputStream audioStream;
	private SourceDataLine sourceLine;

	// Constructor
	public Player(final URL input) {
	    this.u = input;
	}

	@Override
	public void run() {
	    try {
		this.audioStream = AudioSystem.getAudioInputStream(this.u);
	    } catch (final Exception e) {
		// Ignore
	    }
	    try {
		this.sourceLine = (SourceDataLine) AudioSystem
			.getLine(new DataLine.Info(SourceDataLine.class, this.audioStream.getFormat()));
		this.sourceLine.open(this.audioStream.getFormat());
	    } catch (final Exception e) {
		// Ignore
	    }
	    this.sourceLine.start();
	    int nBytesRead = 0;
	    final byte[] abData = new byte[this.BUFFER_SIZE];
	    while (nBytesRead != -1) {
		try {
		    nBytesRead = this.audioStream.read(abData, 0, abData.length);
		} catch (final IOException e) {
		    // Ignore
		}
		if (nBytesRead >= 0) {
		    this.sourceLine.write(abData, 0, nBytesRead);
		}
	    }
	    this.sourceLine.drain();
	    this.sourceLine.close();
	}
    }
}
