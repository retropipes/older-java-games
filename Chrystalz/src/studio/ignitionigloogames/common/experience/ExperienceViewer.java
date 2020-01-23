package studio.ignitionigloogames.common.experience;

import studio.ignitionigloogames.common.dialogs.CommonDialogs;

public final class ExperienceViewer {
    // Private constants
    private static final String ENTRY_PROMPT_PART_1 = "Enter value for ";
    private static final String ENTRY_PROMPT_PART_2 = "parameter:";
    private static final String SUFFIX_N = "th";
    private static final String SUFFIX_1 = "st";
    private static final String SUFFIX_2 = "nd";
    private static final String SUFFIX_3 = "rd";
    private static final String VIEWER_STRING = "Page Viewer";
    private static final String EXPERIENCE_VIEWER_STRING = "Experience Page Viewer";

    // Private constructor
    private ExperienceViewer() {
        // Do nothing
    }

    // Methods
    public static void view(final ExperienceEquation page) {
        String viewerString;
        if (page.isExperience()) {
            viewerString = ExperienceViewer.EXPERIENCE_VIEWER_STRING;
        } else {
            viewerString = ExperienceViewer.VIEWER_STRING;
        }
        if (page.getParamCount() > 1) {
            ExperienceViewer.mpValueView(page, viewerString);
        } else {
            boolean viewAsArray = true;
            final int returnCode = CommonDialogs.showConfirmDialog(
                    "View as a list, or as single values?", viewerString);
            if (returnCode == CommonDialogs.YES_OPTION) {
                viewAsArray = true;
            } else {
                viewAsArray = false;
            }
            if (viewAsArray) {
                ExperienceViewer.listView(page, viewerString);
            } else {
                ExperienceViewer.spValueView(page, viewerString);
            }
        }
    }

    private static void listView(final ExperienceEquation page,
            final String viewerString) {
        final long[] lData = page.evaluateToArray();
        final String[] sData = new String[lData.length];
        for (int x = 0; x < lData.length; x++) {
            sData[x] = Integer.valueOf(x + 1).toString() + ": "
                    + Long.valueOf(lData[x]).toString();
        }
        CommonDialogs.showInputDialog("List of values:", viewerString, sData,
                sData[0]);
    }

    private static void mpValueView(final ExperienceEquation page,
            final String viewerString) {
        boolean viewMore = true;
        while (viewMore) {
            final int[] paramValues = new int[page.getParamCount()];
            for (int x = 0; x < paramValues.length; x++) {
                boolean valid = false;
                String rawInput;
                int input = 0;
                String suffix;
                if ((x + 1) % 100 >= 10 && (x + 1) % 100 <= 19) {
                    suffix = ExperienceViewer.SUFFIX_N;
                } else if ((x + 1) % 10 == 1) {
                    suffix = ExperienceViewer.SUFFIX_1;
                } else if ((x + 1) % 10 == 2) {
                    suffix = ExperienceViewer.SUFFIX_2;
                } else if ((x + 1) % 10 == 3) {
                    suffix = ExperienceViewer.SUFFIX_3;
                } else {
                    suffix = ExperienceViewer.SUFFIX_N;
                }
                while (!valid) {
                    rawInput = CommonDialogs.showTextInputDialog(
                            ExperienceViewer.ENTRY_PROMPT_PART_1 + (x + 1)
                                    + suffix + " "
                                    + ExperienceViewer.ENTRY_PROMPT_PART_2,
                            viewerString);
                    try {
                        input = Integer.parseInt(rawInput);
                        if (input < 0) {
                            // Input can't be negative
                            throw new NumberFormatException();
                        }
                        valid = true;
                    } catch (final NumberFormatException nf) {
                        // Ignore exception
                    }
                    if (!valid) {
                        CommonDialogs.showErrorDialog(
                                "The input provided was invalid - please try again.",
                                viewerString);
                    }
                }
                paramValues[x] = input;
            }
            final long value = page.evaluate(paramValues);
            CommonDialogs.showTitledDialog("Value for the given parameters: "
                    + Long.valueOf(value).toString(), viewerString);
            final int returnCode = CommonDialogs
                    .showConfirmDialog("View more values?", viewerString);
            if (returnCode == CommonDialogs.YES_OPTION) {
                viewMore = true;
            } else {
                viewMore = false;
            }
        }
    }

    private static void spValueView(final ExperienceEquation page,
            final String viewerString) {
        boolean viewMore = true;
        while (viewMore) {
            int paramValue = 0;
            boolean valid = false;
            String rawInput;
            int input = 0;
            while (!valid) {
                rawInput = CommonDialogs.showTextInputDialog(
                        ExperienceViewer.ENTRY_PROMPT_PART_1
                                + ExperienceViewer.ENTRY_PROMPT_PART_2,
                        viewerString);
                try {
                    input = Integer.parseInt(rawInput);
                    if (input < 0) {
                        // Input can't be negative
                        throw new NumberFormatException();
                    }
                    valid = true;
                } catch (final NumberFormatException nf) {
                    // Ignore exception
                }
                if (!valid) {
                    CommonDialogs.showErrorDialog(
                            "The input provided was invalid - please try again.",
                            viewerString);
                }
            }
            paramValue = input;
            final long value = page.evaluate(paramValue);
            CommonDialogs.showTitledDialog(
                    "Value: " + Long.valueOf(value).toString(), viewerString);
            final int returnCode = CommonDialogs
                    .showConfirmDialog("View more values?", viewerString);
            if (returnCode == CommonDialogs.YES_OPTION) {
                viewMore = true;
            } else {
                viewMore = false;
            }
        }
    }
}
