/*  Gemma: A Names Editor
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.scenario.names;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.support.names.NamesConstants;
import com.puttysoftware.gemma.support.names.NamesManager;

class NamesSaveTask extends Thread {
    // Fields
    private String filename;
    private String[] namesData;

    // Constructors
    NamesSaveTask(String file, String[] data) {
        this.filename = file;
        this.namesData = data;
        this.setName("Names File Writer");
    }

    @Override
    public void run() {
        final String sg = "Names";
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(this.filename))) {
            // Write version
            bw.write(Integer.toString(NamesConstants.NAMES_VERSION) + "\n");
            for (int x = 0; x < this.namesData.length; x++) {
                bw.write(this.namesData[x]);
                if (x < this.namesData.length - 1) {
                    bw.write("\n");
                }
            }
            bw.close();
            NamesManager.invalidateNamesCache();
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Writing the " + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
        } catch (final Exception ex) {
            Gemma.getErrorLogger().logError(ex);
        }
    }
}
