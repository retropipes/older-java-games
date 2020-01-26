package net.worldwizard.support.creatures.castes;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import net.worldwizard.support.IDGenerator;
import net.worldwizard.support.Identifiable;
import net.worldwizard.support.Support;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class Caste extends Identifiable {
    private final int[] data;
    private final int casteID;

    public Caste(final int cid, final int... cdata) {
        if (cdata.length != CasteConstants.CASTE_ATTRIBUTE_COUNT) {
            throw new IllegalArgumentException(
                    "Exactly " + CasteConstants.CASTE_ATTRIBUTE_COUNT
                            + " attributes must be specified!");
        }
        this.casteID = cid;
        this.data = cdata;
    }

    public int getAttribute(final int aid) {
        return this.data[aid];
    }

    @Override
    public String getName() {
        return CasteConstants.CASTE_NAMES[this.casteID];
    }

    public int getCasteID() {
        return this.casteID;
    }

    @Override
    public BigInteger computeLongHash() {
        BigInteger longHash = BigInteger.ZERO;
        longHash = longHash.add(IDGenerator
                .computeStringLongHash(CasteConstants.CASTE_NAMES[this.casteID])
                .multiply(BigInteger.valueOf(2)));
        return longHash;
    }

    public static String casteIDtoFilename(final int casteID) {
        BigInteger longHash = BigInteger.ZERO;
        longHash = longHash.add(IDGenerator
                .computeStringLongHash(CasteConstants.CASTE_NAMES[casteID])
                .multiply(BigInteger.valueOf(2)));
        return "$" + longHash.toString(36).toUpperCase();
    }

    @Override
    public void dumpContentsToFile() throws IOException {
        final File dir = new File(Support.getSystemVariables().getBasePath()
                + File.separator + "castes");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final XDataWriter writer = new XDataWriter(
                Support.getSystemVariables().getBasePath() + File.separator
                        + "castes" + File.separator + this.getID()
                        + Extension.getCasteExtensionWithPeriod(),
                Extension.getCasteExtension());
        this.write(writer);
        writer.close();
    }

    public static Caste read(final XDataReader casteFile) throws IOException {
        final int cid = casteFile.readInt();
        final int len = casteFile.readInt();
        final int[] cdata = new int[len];
        for (int x = 0; x < len; x++) {
            cdata[x] = casteFile.readInt();
        }
        return new Caste(cid, cdata);
    }

    public void write(final XDataWriter casteFile) throws IOException {
        casteFile.writeInt(this.casteID);
        casteFile.writeInt(this.data.length);
        for (final int element : this.data) {
            casteFile.writeInt(element);
        }
    }

    @Override
    public String getTypeName() {
        return "Caste";
    }

    @Override
    public String getPluralTypeName() {
        return "castes";
    }
}
