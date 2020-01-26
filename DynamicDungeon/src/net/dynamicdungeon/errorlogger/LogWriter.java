package net.dynamicdungeon.errorlogger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.dynamicdungeon.dynamicdungeon.utilities.DynamicProperties;

class LogWriter {
    // Fields
    private static final String ERROR_EXT = ".crash";
    private final Throwable t;
    private final Calendar c;
    private final String p;

    // Constructors
    LogWriter(final Throwable problem, final String programName) {
        this.t = problem;
        this.c = Calendar.getInstance();
        this.p = programName;
    }

    // Methods
    void writeErrorInfo() {
        try {
            // Make sure the needed directories exist first
            final File df = this.getErrorFile();
            final File parent = new File(df.getParent());
            if (!parent.exists()) {
                final boolean res = parent.mkdirs();
                if (!res) {
                    throw new FileNotFoundException("Cannot make directories!");
                }
            }
            // Print to the file
            try (PrintStream s = new PrintStream(
                    new BufferedOutputStream(new FileOutputStream(df)))) {
                this.t.printStackTrace(s);
                s.close();
            }
        } catch (final FileNotFoundException fnf) {
            // Print to standard error, if something went wrong
            this.t.printStackTrace(System.err);
        }
    }

    private static String getErrorDirectory() {
        return DynamicProperties.getApplicationSupportDirectory()
                + File.separator;
    }

    private static String getErrorFileExtension() {
        return LogWriter.ERROR_EXT;
    }

    private String getStampSuffix() {
        final Date time = this.c.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat(
                "'_'yyyyMMdd'_'HHmmssSSS");
        return sdf.format(time);
    }

    private String getErrorFileName() {
        return this.p;
    }

    private File getErrorFile() {
        final StringBuilder b = new StringBuilder();
        b.append(LogWriter.getErrorDirectory());
        b.append(this.getErrorFileName());
        b.append(this.getStampSuffix());
        b.append(LogWriter.getErrorFileExtension());
        return new File(b.toString());
    }
}
