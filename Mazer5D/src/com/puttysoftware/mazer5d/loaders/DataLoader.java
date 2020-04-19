package com.puttysoftware.mazer5d.loaders;

import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.files.FileExtensions;
import com.puttysoftware.mazer5d.objectmodel.MazeObjectActions;

public class DataLoader {
    private DataLoader() {
        // Do nothing
    }

    public static String[] loadMusicData() {
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                DataLoader.class.getResourceAsStream("/assets/data/music/files"
                        + FileExtensions
                                .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final ArrayList<String> data = new ArrayList<>();
            String raw = "0";
            while (raw != null) {
                raw = rsr.readString();
                if (raw != null) {
                    data.add(raw);
                }
            }
            return data.toArray(new String[data.size()]);
        } catch (final IOException e) {
            Mazer5D.logError(e);
            return null;
        }
    }

    public static String[] loadSoundData() {
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                DataLoader.class.getResourceAsStream("/assets/data/sound/files"
                        + FileExtensions
                                .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final ArrayList<String> data = new ArrayList<>();
            String raw = "0";
            while (raw != null) {
                raw = rsr.readString();
                if (raw != null) {
                    data.add(raw);
                }
            }
            return data.toArray(new String[data.size()]);
        } catch (final IOException e) {
            Mazer5D.logError(e);
            return null;
        }
    }

    public static MazeObjectActions[] loadObjectActionData() {
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                DataLoader.class.getResourceAsStream(
                        "/assets/data/object/actions" + FileExtensions
                                .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final ArrayList<MazeObjectActions> data = new ArrayList<>();
            String raw = "0";
            while (raw != null) {
                raw = rsr.readString();
                if (raw != null) {
                    data.add(new MazeObjectActions(Long.parseLong(raw)));
                }
            }
            return data.toArray(new MazeObjectActions[data.size()]);
        } catch (final IOException e) {
            Mazer5D.logError(e);
            return null;
        }
    }

    public static int[] loadObjectActionAddonData(final int actionID) {
        final String name = "action-" + Integer.toString(actionID);
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                DataLoader.class.getResourceAsStream("/assets/data/object/"
                        + name + FileExtensions
                                .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final ArrayList<Integer> rawData = new ArrayList<>();
            String raw = "0";
            while (raw != null) {
                raw = rsr.readString();
                if (raw != null) {
                    rawData.add(Integer.parseInt(raw));
                }
            }
            int index = 0;
            final int[] data = new int[rawData.size()];
            for (final Integer rawItem : rawData) {
                data[index] = rawItem;
                index++;
            }
            return data;
        } catch (final IOException e) {
            Mazer5D.logError(e);
            return null;
        }
    }

    public static String[] loadEffectImageData() {
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                DataLoader.class.getResourceAsStream("/assets/data/image/effect"
                        + FileExtensions
                                .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final ArrayList<String> data = new ArrayList<>();
            String raw = "0";
            while (raw != null) {
                raw = rsr.readString();
                if (raw != null) {
                    data.add(raw);
                }
            }
            return data.toArray(new String[data.size()]);
        } catch (final IOException e) {
            Mazer5D.logError(e);
            return null;
        }
    }

    public static String[] loadObjectImageData() {
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                DataLoader.class.getResourceAsStream("/assets/data/image/object"
                        + FileExtensions
                                .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final ArrayList<String> data = new ArrayList<>();
            String raw = "0";
            while (raw != null) {
                raw = rsr.readString();
                if (raw != null) {
                    data.add(raw);
                }
            }
            return data.toArray(new String[data.size()]);
        } catch (final IOException e) {
            Mazer5D.logError(e);
            return null;
        }
    }

    public static String[] loadLogoImageData() {
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                DataLoader.class.getResourceAsStream("/assets/data/image/logo"
                        + FileExtensions
                                .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final ArrayList<String> data = new ArrayList<>();
            String raw = "0";
            while (raw != null) {
                raw = rsr.readString();
                if (raw != null) {
                    data.add(raw);
                }
            }
            return data.toArray(new String[data.size()]);
        } catch (final IOException e) {
            Mazer5D.logError(e);
            return null;
        }
    }
}
