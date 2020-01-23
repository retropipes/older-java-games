/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.editor.rulesets;

public class InvalidRuleSetException extends Exception {
    // Serialization
    private static final long serialVersionUID = 999L;

    // Constructors
    public InvalidRuleSetException() {
        super();
    }

    public InvalidRuleSetException(final String msg) {
        super(msg);
    }
}
