package studio.ignitionigloogames.dungeondiver1;

public class SavedStateIdentifier {
    // Constants
    private static final String IDENTIFIER = "dds2";

    // Private constructor
    private SavedStateIdentifier() {
        // Do nothing
    }

    // Methods
    public static String getIdentifier() {
        return SavedStateIdentifier.IDENTIFIER;
    }

    public static String getIdentifierWithPeriod() {
        return "." + SavedStateIdentifier.IDENTIFIER;
    }
}
