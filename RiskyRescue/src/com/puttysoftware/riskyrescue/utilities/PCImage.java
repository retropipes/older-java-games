package com.puttysoftware.riskyrescue.utilities;

import java.io.IOException;

import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class PCImage {
    // Enumerations
    private enum Clothing {
        GREEN, RED, YELLOW, CYAN, MAGENTA, BLUE;
    }

    private enum Skin {
        DARKEST, DARKER, DARK, RED, TAN, LIGHT, LIGHTER, LIGHTEST;
    }

    private enum Hair {
        BLACK, DARK_BROWN, BROWN, LIGHT_BROWN, RED, VERY_DARK_GOLD, DARK_GOLD, GOLD, LIGHT_GOLD, VERY_LIGHT_GOLD;
    }

    // Fields
    private final Clothing clothing;
    private final Skin skin;
    private final Hair hair;

    // Constructor
    public PCImage(final int c, final int s, final int h) {
        super();
        this.clothing = Clothing.values()[c];
        this.skin = Skin.values()[s];
        this.hair = Hair.values()[h];
    }

    public static String getPCImageName(final int c, final int s, final int h) { // NO_UCD
                                                                                 // (actually
                                                                                 // used)
        return Integer.toString(c) + Integer.toString(s) + Integer.toString(h);
    }

    public String getImageName() {
        return Integer.toString(this.clothing.ordinal())
                + Integer.toString(this.skin.ordinal())
                + Integer.toString(this.hair.ordinal());
    }

    public static PCImage read(final XDataReader worldFile) throws IOException {
        final int c = worldFile.readInt();
        final int s = worldFile.readInt();
        final int h = worldFile.readInt();
        return new PCImage(c, s, h);
    }

    public void write(final XDataWriter worldFile) throws IOException {
        worldFile.writeInt(this.clothing.ordinal());
        worldFile.writeInt(this.skin.ordinal());
        worldFile.writeInt(this.hair.ordinal());
    }

    public static String[] getClothingNames() { // NO_UCD (actually used)
        final Clothing[] values = Clothing.values();
        final String[] names = new String[values.length];
        for (int n = 0; n < names.length; n++) {
            final String[] raw = values[n].name().split("_");
            final StringBuilder rawBuilder = new StringBuilder();
            for (int r = 0; r < raw.length; r++) {
                rawBuilder.append(raw[r].substring(0, 1).toUpperCase());
                rawBuilder.append(raw[r].substring(1).toLowerCase());
                if (r < raw.length - 1) {
                    rawBuilder.append(" ");
                }
            }
            names[n] = rawBuilder.toString();
        }
        return names;
    }

    public static String[] getSkinNames() { // NO_UCD (actually used)
        final Skin[] values = Skin.values();
        final String[] names = new String[values.length];
        for (int n = 0; n < names.length; n++) {
            final String[] raw = values[n].name().split("_");
            final StringBuilder rawBuilder = new StringBuilder();
            for (int r = 0; r < raw.length; r++) {
                rawBuilder.append(raw[r].substring(0, 1).toUpperCase());
                rawBuilder.append(raw[r].substring(1).toLowerCase());
                if (r < raw.length - 1) {
                    rawBuilder.append(" ");
                }
            }
            names[n] = rawBuilder.toString();
        }
        return names;
    }

    public static String[] getHairNames() { // NO_UCD (actually used)
        final Hair[] values = Hair.values();
        final String[] names = new String[values.length];
        for (int n = 0; n < names.length; n++) {
            final String[] raw = values[n].name().split("_");
            final StringBuilder rawBuilder = new StringBuilder();
            for (int r = 0; r < raw.length; r++) {
                rawBuilder.append(raw[r].substring(0, 1).toUpperCase());
                rawBuilder.append(raw[r].substring(1).toLowerCase());
                if (r < raw.length - 1) {
                    rawBuilder.append(" ");
                }
            }
            names[n] = rawBuilder.toString();
        }
        return names;
    }
}
