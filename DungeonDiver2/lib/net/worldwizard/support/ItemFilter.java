package net.worldwizard.support;

import java.io.File;
import java.io.FilenameFilter;

import net.worldwizard.support.variables.Extension;

public class ItemFilter implements FilenameFilter {
    @Override
    public boolean accept(final File dir, final String name) {
        if (name.endsWith(Extension.getItemExtensionWithPeriod())) {
            return true;
        } else {
            return false;
        }
    }
}
