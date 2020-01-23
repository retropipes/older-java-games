package com.puttysoftware.audio.mod;

public class Instrument {
    public int vibratoType = 0, vibratoSweep = 0, vibratoDepth = 0,
            vibratoRate = 0;
    public int volumeFadeOut = 0;
    public Envelope volumeEnvelope = new Envelope();
    public Envelope panningEnvelope = new Envelope();
    public int[] keyToSample = new int[97];
    public Sample[] samples = new Sample[] { new Sample() };
}
