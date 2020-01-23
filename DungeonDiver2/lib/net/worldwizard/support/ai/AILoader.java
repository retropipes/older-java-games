package net.worldwizard.support.ai;

import java.io.File;

import net.worldwizard.support.variables.Extension;

public class AILoader {
    static AIRoutine loadAI(final String name) {
        final String basePath = AIRegistration.getBasePath();
        File loader = null;
        try {
            final String loadPath = basePath + File.separator + name
                    + Extension.getAIScriptExtensionWithPeriod();
            loader = new File(loadPath);
            return new ScriptedAI(loader);
        } catch (final Exception e) {
            return null;
        }
    }

    public static AIRoutine[] loadAllRegisteredAIs() {
        final String[] registeredNames = AIRegistration.getAIList();
        if (registeredNames != null) {
            final AIRoutine[] res = new AIRoutine[registeredNames.length];
            // Load AIs
            for (int x = 0; x < registeredNames.length; x++) {
                final String name = registeredNames[x];
                final AIRoutine aiWithName = AILoader.loadAI(name);
                if (aiWithName != null) {
                    res[x] = aiWithName;
                } else {
                    // Bad data
                    return null;
                }
            }
            return res;
        }
        return null;
    }
}
