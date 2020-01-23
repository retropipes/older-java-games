package com.puttysoftware.audio.mod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class MODFactory {
    private static final int SAMPLE_RATE = 41000;
    private Module module;
    IBXM ibxm;
    volatile boolean playing;
    private int interpolation;
    private Thread playThread;
    private final String tempDir;

    public MODFactory(final String tempLoc) {
        super();
        this.tempDir = tempLoc;
    }

    public boolean isPlayThreadAlive() {
        return this.playThread != null && this.playThread.isAlive();
    }

    public synchronized MODFactory loadResource(final String modRes)
            throws IOException {
        final File tmpMod = new File(this.tempDir + File.pathSeparator
                + MODFactory.getFileNameOnly(modRes));
        try (final InputStream is = MODFactory.class
                .getResourceAsStream(modRes)) {
            try (final FileOutputStream os = new FileOutputStream(tmpMod)) {
                // Copy the bits
                final byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) > 0) {
                    os.write(buf, 0, len);
                }
            }
        }
        return this.loadFile(tmpMod);
    }

    public synchronized MODFactory loadFile(final File modFile)
            throws IOException {
        final byte[] moduleData = new byte[(int) modFile.length()];
        try (FileInputStream inputStream = new FileInputStream(modFile)) {
            int offset = 0;
            while (offset < moduleData.length) {
                final int len = inputStream.read(moduleData, offset,
                        moduleData.length - offset);
                if (len < 0) {
                    inputStream.close();
                    throw new IOException("Unexpected end of file."); //$NON-NLS-1$
                }
                offset += len;
            }
            inputStream.close();
            this.module = new Module(moduleData);
            this.ibxm = new IBXM(this.module, MODFactory.SAMPLE_RATE);
            this.ibxm.setInterpolation(this.interpolation);
        }
        return this;
    }

    public synchronized void play() {
        if (this.ibxm != null) {
            this.playing = true;
            this.playThread = new Thread(() -> {
                final int[] mixBuf = new int[MODFactory.this.ibxm
                        .getMixBufferLength()];
                final byte[] outBuf = new byte[mixBuf.length * 4];
                AudioFormat audioFormat = null;
                audioFormat = new AudioFormat(MODFactory.SAMPLE_RATE, 16, 2,
                        true, true);
                try (SourceDataLine audioLine = AudioSystem
                        .getSourceDataLine(audioFormat)) {
                    audioLine.open();
                    audioLine.start();
                    while (MODFactory.this.playing) {
                        final int count = MODFactory.this.getAudio(mixBuf);
                        int outIdx = 0;
                        for (int mixIdx = 0, mixEnd = count
                                * 2; mixIdx < mixEnd; mixIdx++) {
                            int ampl = mixBuf[mixIdx];
                            if (ampl > 32767) {
                                ampl = 32767;
                            }
                            if (ampl < -32768) {
                                ampl = -32768;
                            }
                            outBuf[outIdx++] = (byte) (ampl >> 8);
                            outBuf[outIdx++] = (byte) ampl;
                        }
                        audioLine.write(outBuf, 0, outIdx);
                    }
                    audioLine.drain();
                } catch (final Exception e) {
                    // Ignore
                }
            });
            this.playThread.start();
        }
    }

    public synchronized boolean isPlaying() {
        return this.playing;
    }

    public synchronized void stopLoop() {
        this.playing = false;
        try {
            if (this.playThread != null) {
                this.playThread.join();
            }
        } catch (final InterruptedException e) {
        }
    }

    synchronized int getAudio(final int[] mixBuf) {
        final int count = this.ibxm.getAudio(mixBuf);
        return count;
    }

    private static String getFileNameOnly(final String s) {
        String fno = null;
        final int i = s.lastIndexOf(File.separatorChar);
        if (i > 0 && i < s.length() - 1) {
            fno = s.substring(i + 1);
        } else {
            fno = s;
        }
        return fno;
    }
}
