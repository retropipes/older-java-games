package studio.ignitionigloogames.common.experience;

import studio.ignitionigloogames.common.dialogs.CommonDialogs;

public final class ExperienceEditor {
    // Fields
    private final ExperienceEquation page;
    public static final int DEFAULT_MAX_POWER = 6;
    public static final int DEFAULT_PARAMS = 1;
    public static final int DEFAULT_MAX_RANGE = 99;
    private static final String SUFFIX_N = "th";
    private static final String SUFFIX_1 = "st";
    private static final String SUFFIX_2 = "nd";
    private static final String SUFFIX_3 = "rd";
    private static final String ENTRY_PROMPT_PART_1 = " coefficient for ";
    private static final String ENTRY_PROMPT_PART_2 = " parameter:";
    private static final String EDITOR_STRING = "Page Editor";
    private static final String EXPERIENCE_EDITOR_STRING = "Experience Page Editor";
    private static final String DIALOG_PROMPT = "Do you want to save this page?";
    private static final String DIALOG_TITLE = "Save?";

    // Constructors
    public ExperienceEditor() {
        this.page = new ExperienceEquation(ExperienceEditor.DEFAULT_MAX_POWER,
                ExperienceEditor.DEFAULT_PARAMS,
                ExperienceEditor.DEFAULT_MAX_RANGE, false);
    }

    public ExperienceEditor(final int maxPower, final int params,
            final int range, final boolean experience) {
        this.page = new ExperienceEquation(maxPower, params, range, experience);
    }

    public ExperienceEditor(final ExperienceEquation oldPage) {
        this.page = oldPage;
    }

    // Methods
    public ExperienceEquation edit() {
        String editorString;
        if (this.page.isExperience()) {
            editorString = ExperienceEditor.EXPERIENCE_EDITOR_STRING;
        } else {
            editorString = ExperienceEditor.EDITOR_STRING;
        }
        boolean bad = true;
        boolean inputValid;
        int result, x, y;
        double input = 0.0;
        String xSuffix, ySuffix, rawInput;
        while (bad) {
            for (x = this.page.getMaxPower(); x >= 0; x--) {
                for (y = 1; y <= this.page.getParamCount(); y++) {
                    if (x % 100 >= 10 && x % 100 <= 19) {
                        xSuffix = ExperienceEditor.SUFFIX_N;
                    } else if (x % 10 == 1) {
                        xSuffix = ExperienceEditor.SUFFIX_1;
                    } else if (x % 10 == 2) {
                        xSuffix = ExperienceEditor.SUFFIX_2;
                    } else if (x % 10 == 3) {
                        xSuffix = ExperienceEditor.SUFFIX_3;
                    } else {
                        xSuffix = ExperienceEditor.SUFFIX_N;
                    }
                    if (y % 100 >= 10 && y % 100 <= 19) {
                        ySuffix = ExperienceEditor.SUFFIX_N;
                    } else if (y % 10 == 1) {
                        ySuffix = ExperienceEditor.SUFFIX_1;
                    } else if (y % 10 == 2) {
                        ySuffix = ExperienceEditor.SUFFIX_2;
                    } else if (y % 10 == 3) {
                        ySuffix = ExperienceEditor.SUFFIX_3;
                    } else {
                        ySuffix = ExperienceEditor.SUFFIX_N;
                    }
                    inputValid = false;
                    while (!inputValid) {
                        rawInput = CommonDialogs.showTextInputDialogWithDefault(
                                x + xSuffix
                                        + ExperienceEditor.ENTRY_PROMPT_PART_1
                                        + y + ySuffix
                                        + ExperienceEditor.ENTRY_PROMPT_PART_2,
                                editorString,
                                Double.valueOf(this.page.getCoefficient(x, y))
                                        .toString());
                        try {
                            input = Double.parseDouble(rawInput);
                            if (input < 0.0) {
                                // Input can't be negative
                                throw new NumberFormatException();
                            }
                            inputValid = true;
                        } catch (final NumberFormatException nf) {
                            // Ignore exception
                        }
                        if (!inputValid) {
                            CommonDialogs.showErrorDialog(
                                    "The input provided was invalid - please try again.",
                                    editorString);
                        }
                    }
                    this.page.setCoefficient(x, y, input);
                }
            }
            ExperienceViewer.view(this.page);
            result = CommonDialogs.showConfirmDialog(
                    ExperienceEditor.DIALOG_PROMPT,
                    ExperienceEditor.DIALOG_TITLE);
            if (result == CommonDialogs.YES_OPTION) {
                bad = false;
            }
        }
        return this.page;
    }
}
