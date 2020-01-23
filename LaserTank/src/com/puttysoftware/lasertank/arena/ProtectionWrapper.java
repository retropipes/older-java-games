package com.puttysoftware.lasertank.arena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.lasertank.stringmanagers.StringConstants;
import com.puttysoftware.lasertank.stringmanagers.StringLoader;

public class ProtectionWrapper {
    // Constants
    private static final int BLOCK_MULTIPLIER = 16;

    private ProtectionWrapper() {
        // Do nothing
    }

    public static void protect(final File src, final File dst)
            throws IOException {
        try (FileInputStream in = new FileInputStream(src);
                FileOutputStream out = new FileOutputStream(dst)) {
            final char[] transform = ProtectionWrapper.getTransform();
            if (transform == null) {
                throw new ProtectionCancelException();
            }
            final byte[] buf = new byte[transform.length
                    * ProtectionWrapper.BLOCK_MULTIPLIER];
            int len;
            while ((len = in.read(buf)) > 0) {
                for (int x = 0; x < buf.length; x++) {
                    buf[x] += transform[x % transform.length];
                }
                out.write(buf, 0, len);
            }
        } catch (final IOException ioe) {
            throw ioe;
        }
    }

    public static void unprotect(final File src, final File dst)
            throws IOException {
        try (FileInputStream in = new FileInputStream(src);
                FileOutputStream out = new FileOutputStream(dst)) {
            final char[] transform = ProtectionWrapper.getTransform();
            if (transform == null) {
                throw new ProtectionCancelException();
            }
            final byte[] buf = new byte[transform.length
                    * ProtectionWrapper.BLOCK_MULTIPLIER];
            int len;
            while ((len = in.read(buf)) > 0) {
                for (int x = 0; x < buf.length; x++) {
                    buf[x] -= transform[x % transform.length];
                }
                out.write(buf, 0, len);
            }
        } catch (final IOException ioe) {
            throw ioe;
        }
    }

    private static char[] getTransform() {
        return CommonDialogs.showPasswordInputDialog(
                StringLoader.loadString(StringConstants.DIALOG_STRINGS_FILE,
                        StringConstants.DIALOG_STRING_PROTECTION_PROMPT),
                StringLoader.loadString(StringConstants.DIALOG_STRINGS_FILE,
                        StringConstants.DIALOG_STRING_PROTECTION_TITLE),
                15);
    }
}
