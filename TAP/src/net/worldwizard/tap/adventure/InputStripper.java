package net.worldwizard.tap.adventure;

class InputStripper {
    // Fields
    private static final String[] STRIP_MIDDLE_WORDS = { " a ", " an ",
            " the " };

    // Private constructor
    private InputStripper() {
        // Do nothing
    }

    // Methods
    protected static String stripInput(final String in) {
        String out = in;
        for (final String element : InputStripper.STRIP_MIDDLE_WORDS) {
            out = in.replace(element, " ");
        }
        return out;
    }
}
