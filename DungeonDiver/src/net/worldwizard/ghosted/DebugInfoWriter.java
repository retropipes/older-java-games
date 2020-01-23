package net.worldwizard.ghosted;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class DebugInfoWriter {
    // Constants
    private static final String MAC_DIR = "/Library/Logs/CrashReporter/";
    private static final String WIN_DIR = "\\Crash\\";
    private static final String UNIX_DIR = "/Crash/";
    private static final String MAC_EXT = ".crash";
    private static final String WIN_EXT = ".log";
    private static final String UNIX_EXT = ""; // Fields
    private final Throwable t;
    private final Calendar c;
    private final String p;

    // Constructors
    public DebugInfoWriter(final Throwable problem, final String programName) {
        this.t = problem;
        this.c = Calendar.getInstance();
        this.p = programName;
    }

    // Methods
    public void writeDebugInfo() {
        try (PrintStream s = new PrintStream(new BufferedOutputStream(
                new FileOutputStream(this.getDebugFile())))) {
            // Print to the file
            this.t.printStackTrace(s);
        } catch (final FileNotFoundException fnf) {
            // Print to standard error, if something went wrong
            this.t.printStackTrace(System.err);
        }
    }

    private static String getDebugDirPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv("HOME");
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv("APPDATA");
        } else {
            // Other - assume UNIX-like
            return System.getenv("HOME");
        }
    }

    private String getDebugDirectory() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return DebugInfoWriter.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return "\\" + this.p + DebugInfoWriter.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return "/" + this.p + DebugInfoWriter.UNIX_DIR;
        }
    }

    private static String getDebugFileExtension() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return DebugInfoWriter.MAC_EXT;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return DebugInfoWriter.WIN_EXT;
        } else {
            // Other - assume UNIX-like
            return DebugInfoWriter.UNIX_EXT;
        }
    }

    private String getStampSuffix() {
        final Date time = this.c.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat(
                "'_'yyyyMMdd'_'HHmmssSSS");
        return sdf.format(time);
    }

    private String getDebugFileName() {
        return this.p;
    }

    private File getDebugFile() {
        final StringBuilder b = new StringBuilder();
        b.append(DebugInfoWriter.getDebugDirPrefix());
        b.append(this.getDebugDirectory());
        b.append(this.getDebugFileName());
        b.append(this.getStampSuffix());
        b.append(DebugInfoWriter.getDebugFileExtension());
        return new File(b.toString());
    }

    public String getFullDebugPath() {
        final StringBuilder b = new StringBuilder();
        b.append(DebugInfoWriter.getDebugDirPrefix());
        b.append(this.getDebugDirectory());
        b.append(this.getDebugFileName());
        b.append(this.getStampSuffix());
        b.append(DebugInfoWriter.getDebugFileExtension());
        return b.toString();
    }
}
