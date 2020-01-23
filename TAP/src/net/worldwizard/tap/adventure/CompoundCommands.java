package net.worldwizard.tap.adventure;

class CompoundCommands {
    // Fields
    private static final String[] COMPOUND_COMMAND_WORDS = { " and ", " then " };
    private static final String COMPOUND_COMMAND_SEPARATOR = " && ";

    // Private constructor
    private CompoundCommands() {
        // Do nothing
    }

    // Methods
    protected static String[] splitCompoundInput(final String in) {
        String fixed = in;
        for (final String element : CompoundCommands.COMPOUND_COMMAND_WORDS) {
            fixed = fixed.replace(element,
                    CompoundCommands.COMPOUND_COMMAND_SEPARATOR);
        }
        final String[] split = fixed
                .split(CompoundCommands.COMPOUND_COMMAND_SEPARATOR);
        return split;
    }
}
