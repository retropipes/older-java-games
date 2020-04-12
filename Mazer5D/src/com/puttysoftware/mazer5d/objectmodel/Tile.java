/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2020 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objectmodel;

import com.puttysoftware.images.BufferedImageIcon;

final class Tile {
    private Appearance editorAppearance;
    private Appearance gameAppearance;
    private Appearance battleAppearance;
    private final Appearance appearance;

    public Tile(final Appearance look) {
        super();
        this.appearance = look;
    }

    public void setGameLook(final Appearance look) {
        this.gameAppearance = look;
    }

    public void setEditorLook(final Appearance look) {
        this.editorAppearance = look;
    }

    public void setBattleLook(final Appearance look) {
        this.battleAppearance = look;
    }

    public BufferedImageIcon getImage() {
        return this.appearance.getImage();
    }

    public BufferedImageIcon getGameImage() {
        return this.gameAppearance != null ? this.gameAppearance.getImage()
                : this.getImage();
    }

    public BufferedImageIcon getEditorImage() {
        return this.editorAppearance != null ? this.editorAppearance.getImage()
                : this.getGameImage();
    }

    public BufferedImageIcon getBattleImage() {
        return this.battleAppearance != null ? this.battleAppearance.getImage()
                : this.getGameImage();
    }
}
