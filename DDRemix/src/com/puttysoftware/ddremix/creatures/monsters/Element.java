/*  DDRemix: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.creatures.monsters;

import java.awt.Color;

import com.puttysoftware.ddremix.creatures.faiths.Faith;

public class Element {
    // Fields
    private final Color[] transforms;
    private final String name;
    private final Faith faith;
    private static final int TFORMS = 7;
    private static final double[] TFORM_RULES = new double[] { 1.0, 0.75, 0.5,
            0.82, 0.88, 0.94 };
    private static final Color EYE_RULES = new Color(127, 63, 191);

    // Constructor
    public Element(final Faith f) {
        this.faith = f;
        this.transforms = new Color[Element.TFORMS];
        for (int z = 0; z < Element.TFORMS; z++) {
            final Color c = f.getColor();
            final int r = c.getRed();
            final int g = c.getGreen();
            final int b = c.getBlue();
            int nr, ng, nb;
            if (z == Element.TFORMS - 1) {
                // Eye
                final int er = Element.EYE_RULES.getRed();
                final int eg = Element.EYE_RULES.getGreen();
                final int eb = Element.EYE_RULES.getBlue();
                if (r < er && g < eg && b < eb) {
                    // White
                    nr = 255;
                    ng = 255;
                    nb = 255;
                } else {
                    // Black
                    nr = 0;
                    ng = 0;
                    nb = 0;
                }
            } else {
                // Other colors
                nr = (int) (r * Element.TFORM_RULES[z]);
                ng = (int) (g * Element.TFORM_RULES[z]);
                nb = (int) (b * Element.TFORM_RULES[z]);
            }
            this.transforms[z] = new Color(nr, ng, nb);
        }
        this.name = f.getName();
    }

    // Methods
    public String getName() {
        return this.name;
    }

    public Faith getFaith() {
        return this.faith;
    }

    public Color[] getTransformColors() {
        return this.transforms;
    }
}
