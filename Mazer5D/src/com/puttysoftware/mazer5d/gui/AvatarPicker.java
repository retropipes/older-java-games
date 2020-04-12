package com.puttysoftware.mazer5d.gui;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.assets.AvatarImageModel;
import com.puttysoftware.mazer5d.loaders.AvatarImageLoader;
import com.puttysoftware.randomrange.RandomRange;

public class AvatarPicker {
    private AvatarPicker() {
        super();
    }

    public static AvatarImageModel pickAvatar() {
        final int randomSkin = RandomRange.generate(0, 9);
        final int randomHair = RandomRange.generate(0, 9);
        final int avatarFamily = AvatarPicker.pickAvatarFamily(randomSkin,
                randomHair);
        if (avatarFamily != ImageListWithDescDialog.CANCEL) {
            final int avatarSkin = AvatarPicker
                    .pickAvatarSkinColor(avatarFamily, randomHair);
            if (avatarSkin != ImageListWithDescDialog.CANCEL) {
                final int avatarHair = AvatarPicker
                        .pickAvatarHairColor(avatarFamily, avatarSkin);
                if (avatarHair != ImageListWithDescDialog.CANCEL) {
                    return new AvatarImageModel(avatarFamily, avatarSkin,
                            avatarHair);
                }
            }
        }
        return null;
    }

    private static int pickAvatarFamily(final int randomSkinID,
            final int randomHairID) {
        final String labelText = "Avatar Families";
        final String title = "Pick Avatar Family";
        final BufferedImageIcon[] input = new BufferedImageIcon[] {
                AvatarImageLoader.load(0,
                        new AvatarImageModel(0, randomSkinID, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(1, randomSkinID, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(2, randomSkinID, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(3, randomSkinID, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(4, randomSkinID, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(5, randomSkinID, randomHairID)
                                .getRules()) };
        final String[] descriptions = new String[] { "Green", "Red", "Yellow",
                "Cyan", "Pink", "Blue" };
        return ImageListWithDescDialog.showDialog(null, labelText, title, input,
                0, descriptions[0], descriptions);
    }

    private static int pickAvatarSkinColor(final int familyID,
            final int randomHairID) {
        final String labelText = "Avatar Skin Colors";
        final String title = "Pick Avatar Skin Color";
        final BufferedImageIcon[] input = new BufferedImageIcon[] {
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, 0, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, 1, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, 2, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, 3, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, 4, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, 5, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, 6, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, 7, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, 8, randomHairID)
                                .getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, 9, randomHairID)
                                .getRules()) };
        final String[] descriptions = new String[] { "Darkest", "Darker",
                "Dark", "Mildly Dark", "Slightly Dark", "Slightly Light",
                "Mildly Light", "Light", "Lighter", "Lightest" };
        return ImageListWithDescDialog.showDialog(null, labelText, title, input,
                0, descriptions[0], descriptions);
    }

    private static int pickAvatarHairColor(final int familyID,
            final int skinID) {
        final String labelText = "Avatar Hair Colors";
        final String title = "Pick Avatar Hair Color";
        final BufferedImageIcon[] input = new BufferedImageIcon[] {
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, skinID, 0).getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, skinID, 1).getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, skinID, 2).getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, skinID, 3).getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, skinID, 4).getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, skinID, 5).getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, skinID, 6).getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, skinID, 7).getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, skinID, 8).getRules()),
                AvatarImageLoader.load(0,
                        new AvatarImageModel(familyID, skinID, 9).getRules()) };
        final String[] descriptions = new String[] { "Black", "Dark Gray",
                "Dark Brown", "Light Brown", "Red", "Light Gray", "Dark Yellow",
                "Yellow", "Tan", "Bright Yellow" };
        return ImageListWithDescDialog.showDialog(null, labelText, title, input,
                0, descriptions[0], descriptions);
    }
}
