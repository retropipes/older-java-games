/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import com.puttysoftware.lasertank.strings.global.GlobalLoader;
import com.puttysoftware.lasertank.strings.global.UntranslatedString;

class ModernAccelerators extends Accelerators {
    ModernAccelerators() {
	int modKey;
	if (System.getProperty(GlobalLoader.loadUntranslated(UntranslatedString.OS_NAME))
		.equalsIgnoreCase(GlobalLoader.loadUntranslated(UntranslatedString.MAC_OS_X))) {
	    modKey = InputEvent.META_DOWN_MASK;
	} else {
	    modKey = InputEvent.CTRL_DOWN_MASK;
	}
	this.fileNewAccel = KeyStroke.getKeyStroke(KeyEvent.VK_N, modKey);
	this.fileOpenAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O, modKey);
	this.fileCloseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_W, modKey);
	this.fileSaveAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S, modKey);
	this.fileSaveAsAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S, modKey | InputEvent.SHIFT_DOWN_MASK);
	this.filePrintAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P, modKey);
	this.fileExitAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Q, modKey);
	this.editorUndoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z, modKey);
	this.editorRedoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z, modKey | InputEvent.SHIFT_DOWN_MASK);
	this.editorCutLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_X, modKey);
	this.editorCopyLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_C, modKey);
	this.editorPasteLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_V, modKey);
	this.editorInsertLevelFromClipboardAccel = KeyStroke.getKeyStroke(KeyEvent.VK_F, modKey);
	this.filePreferencesAccel = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, modKey);
	this.editorClearHistoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Y, modKey);
	this.editorGoToLocationAccel = KeyStroke.getKeyStroke(KeyEvent.VK_G, modKey | InputEvent.SHIFT_DOWN_MASK);
	this.playPlayArenaAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P, modKey | InputEvent.SHIFT_DOWN_MASK);
	this.playEditArenaAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E, modKey);
	this.gameResetAccel = KeyStroke.getKeyStroke(KeyEvent.VK_R, modKey);
	this.gameShowTableAccel = KeyStroke.getKeyStroke(KeyEvent.VK_T, modKey);
	this.editorUpOneLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_UP, modKey);
	this.editorDownOneLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, modKey);
    }
}
