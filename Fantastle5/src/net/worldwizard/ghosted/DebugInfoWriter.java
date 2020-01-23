/*  Fantastle: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Any questions should be directed to the author via email at: fantastle@worldwizard.net
 */
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
    // Fields
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "HOME";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Logs/CrashReporter/";
    private static final String WIN_DIR = "\\Crash\\";
    private static final String UNIX_DIR = "/Crash/";
    private static final String MAC_EXT = ".crash";
    private static final String WIN_EXT = ".log";
    private static final String UNIX_EXT = "";
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
        try {
            // Make sure the needed directories exist first
            final File df = this.getDebugFile();
            final File parent = new File(df.getParent());
            if (!parent.exists()) {
                parent.mkdirs();
            }
            // Print to the file
            try (final PrintStream s = new PrintStream(
                    new BufferedOutputStream(new FileOutputStream(df)))) {
                this.t.printStackTrace(s);
            }
        } catch (final FileNotFoundException fnf) {
            // Print to standard error, if something went wrong
            this.t.printStackTrace(System.err);
        }
    }

    private static String getDebugDirPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(DebugInfoWriter.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(DebugInfoWriter.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(DebugInfoWriter.UNIX_PREFIX);
        }
    }

    private static String getDebugDirectory() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return DebugInfoWriter.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return DebugInfoWriter.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return DebugInfoWriter.UNIX_DIR;
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
        b.append(DebugInfoWriter.getDebugDirectory());
        b.append(this.getDebugFileName());
        b.append(this.getStampSuffix());
        b.append(DebugInfoWriter.getDebugFileExtension());
        return new File(b.toString());
    }

    public String getFullDebugPath() {
        final StringBuilder b = new StringBuilder();
        b.append(DebugInfoWriter.getDebugDirPrefix());
        b.append(DebugInfoWriter.getDebugDirectory());
        b.append(this.getDebugFileName());
        b.append(this.getStampSuffix());
        b.append(DebugInfoWriter.getDebugFileExtension());
        return b.toString();
    }
}
