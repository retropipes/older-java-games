/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.genders;

public class Gender {
    private final int genderID;

    Gender(final int gid) {
        this.genderID = gid;
    }

    public int getGenderID() {
        return this.genderID;
    }
}
