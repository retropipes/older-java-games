package studio.ignitionigloogames.audio.wav;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

class WavResource extends WavFactory {
	private final URL soundURL;

	public WavResource(final URL resURL) {
		super();
		this.soundURL = resURL;
	}

	@Override
	public void run() {
		try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.soundURL)) {
			final AudioFormat format = audioInputStream.getFormat();
			final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			try (SourceDataLine auline = (SourceDataLine) AudioSystem.getLine(info)) {
				auline.open(format);
				auline.start();
				int nBytesRead = 0;
				final byte[] abData = new byte[WavFactory.EXTERNAL_BUFFER_SIZE];
				try {
					while (nBytesRead != -1) {
						nBytesRead = audioInputStream.read(abData, 0, abData.length);
						if (nBytesRead >= 0) {
							auline.write(abData, 0, nBytesRead);
						}
					}
				} catch (final IOException e) {
					return;
				} finally {
					auline.drain();
					auline.close();
					try {
						audioInputStream.close();
					} catch (final IOException e2) {
						// Ignore
					}
				}
			} catch (final LineUnavailableException e) {
				try {
					audioInputStream.close();
				} catch (final IOException e2) {
					// Ignore
				}
				return;
			}
		} catch (final UnsupportedAudioFileException e1) {
			return;
		} catch (final IOException e1) {
			return;
		}
	}

	@Override
	public void stopLoop() {
		// Do nothing
	}
}
