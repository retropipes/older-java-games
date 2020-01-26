package com.puttysoftware.ltremix;

import javax.swing.JMenu;

public interface MenuSection {
    void enableModeCommands();

    void disableModeCommands();

    void setInitialState();

    JMenu createCommandsMenu();

    void attachAccelerators(final Accelerators accel);

    void enableLoadedCommands();

    void disableLoadedCommands();

    void enableDirtyCommands();

    void disableDirtyCommands();
}
