package net.worldwizard.support.creatures.races;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import net.worldwizard.support.IDGenerator;
import net.worldwizard.support.Identifiable;
import net.worldwizard.support.Support;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class Race extends Identifiable {
    private final int[] data;
    private final int raceID;

    public Race(final int rid, final int... rdata) {
        if (rdata.length != RaceConstants.RACE_ATTRIBUTE_COUNT) {
            throw new IllegalArgumentException(
                    "Exactly " + RaceConstants.RACE_ATTRIBUTE_COUNT
                            + " attributes must be specified!");
        }
        this.raceID = rid;
        this.data = rdata;
    }

    public int getAttribute(final int aid) {
        return this.data[aid];
    }

    @Override
    public String getName() {
        return RaceConstants.RACE_NAMES[this.raceID];
    }

    public int getRaceID() {
        return this.raceID;
    }

    @Override
    public BigInteger computeLongHash() {
        BigInteger longHash = BigInteger.ZERO;
        longHash = longHash.add(IDGenerator
                .computeStringLongHash(RaceConstants.RACE_NAMES[this.raceID])
                .multiply(BigInteger.valueOf(2)));
        return longHash;
    }

    public static String raceIDtoFilename(final int raceID) {
        BigInteger longHash = BigInteger.ZERO;
        longHash = longHash.add(IDGenerator
                .computeStringLongHash(RaceConstants.RACE_NAMES[raceID])
                .multiply(BigInteger.valueOf(2)));
        return "$" + longHash.toString(36).toUpperCase();
    }

    @Override
    public void dumpContentsToFile() throws IOException {
        final File dir = new File(Support.getSystemVariables().getBasePath()
                + File.separator + "races");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final XDataWriter writer = new XDataWriter(
                Support.getSystemVariables().getBasePath() + File.separator
                        + "races" + File.separator + this.getID()
                        + Extension.getRaceExtensionWithPeriod(),
                Extension.getRaceExtension());
        this.write(writer);
        writer.close();
    }

    public static Race read(final XDataReader raceFile) throws IOException {
        final int rid = raceFile.readInt();
        final int len = raceFile.readInt();
        final int[] rdata = new int[len];
        for (int x = 0; x < len; x++) {
            rdata[x] = raceFile.readInt();
        }
        return new Race(rid, rdata);
    }

    public void write(final XDataWriter raceFile) throws IOException {
        raceFile.writeInt(this.raceID);
        raceFile.writeInt(this.data.length);
        for (final int element : this.data) {
            raceFile.writeInt(element);
        }
    }

    @Override
    public String getTypeName() {
        return "Race";
    }

    @Override
    public String getPluralTypeName() {
        return "races";
    }
}
