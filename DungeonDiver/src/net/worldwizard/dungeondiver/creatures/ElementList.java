package net.worldwizard.dungeondiver.creatures;

import java.awt.Color;

import net.worldwizard.randomnumbers.RandomRange;

public class ElementList {
    // Fields
    private static Element[] list;
    private static String[] elementNames;

    // Pseudo-Constructor
    private static void init() {
        // Set properties of all elements
        Element.setTrans(Color.MAGENTA);
        Element.setEye(Color.BLACK);
        // Create elements
        ElementList.elementNames = new String[61];
        ElementList.list = new Element[61];
        int counter = 0;
        for (int r = 0; r < 5; r++) {
            for (int g = 0; g < 5; g++) {
                for (int b = 0; b < 5; b++) {
                    if (r != 4 && g != 4 && b != 4) {
                        continue;
                    } else {
                        ElementList.elementNames[counter] = ElementList
                                .getElementNameByPosition(counter);
                        final boolean eye = ElementList
                                .getElementEyeByPosition(counter);
                        final double r2 = r / 4.0;
                        final double g2 = g / 4.0;
                        final double b2 = b / 4.0;
                        ElementList.list[counter] = new Element(r2, g2, b2, eye,
                                ElementList.elementNames[counter]);
                        counter++;
                    }
                }
            }
        }
    }

    private static int getElementID(final String element) {
        int x;
        for (x = 0; x < ElementList.elementNames.length; x++) {
            if (element.equals(ElementList.elementNames[x])) {
                return x;
            }
        }
        return -1;
    }

    public static Element getElement(final String elementName) {
        if (ElementList.list == null) {
            ElementList.init();
        }
        final int ID = ElementList.getElementID(elementName);
        if (ID == -1) {
            return null;
        } else {
            return ElementList.list[ID];
        }
    }

    public static Element getRandomElement() {
        if (ElementList.list == null) {
            ElementList.init();
        }
        final RandomRange r = new RandomRange(0, ElementList.list.length - 1);
        final int ID = (int) r.generate();
        return ElementList.list[ID];
    }

    private static String getElementNameByPosition(final int pos) {
        switch (pos) {
        case 0:
            return "Water";
        case 1:
            return "Ocean";
        case 2:
            return "Sea";
        case 3:
            return "Lake";
        case 4:
            return "Air";
        case 5:
            return "Grass";
        case 6:
            return "Swamp";
        case 7:
            return "Bog";
        case 8:
            return "Ice";
        case 9:
            return "Flood";
        case 10:
            return "Tide";
        case 11:
            return "River";
        case 12:
            return "Stream";
        case 13:
            return "Gunk";
        case 14:
            return "Slime";
        case 15:
            return "Sewer";
        case 16:
            return "Stench";
        case 17:
            return "Tundra";
        case 18:
            return "Frozen";
        case 19:
            return "Cold";
        case 20:
            return "Chilly";
        case 21:
            return "Cool";
        case 22:
            return "Gas";
        case 23:
            return "Vapor";
        case 24:
            return "Aether";
        case 25:
            return "Fog";
        case 26:
            return "Frostbitten";
        case 27:
            return "Sleepy";
        case 28:
            return "Tired";
        case 29:
            return "Yawning";
        case 30:
            return "Exhausted";
        case 31:
            return "Sludge";
        case 32:
            return "Chemical";
        case 33:
            return "Toxic";
        case 34:
            return "Poisonous";
        case 35:
            return "Sub-Zero";
        case 36:
            return "Fire";
        case 37:
            return "Magma";
        case 38:
            return "Lava";
        case 39:
            return "Hot";
        case 40:
            return "Psychic";
        case 41:
            return "Warm";
        case 42:
            return "Mild";
        case 43:
            return "Volcanic";
        case 44:
            return "Mental";
        case 45:
            return "Nether";
        case 46:
            return "Daemonic";
        case 47:
            return "Neutral";
        case 48:
            return "Angelic";
        case 49:
            return "Toasty";
        case 50:
            return "Inspired";
        case 51:
            return "Dull";
        case 52:
            return "Dumb";
        case 53:
            return "Sand";
        case 54:
            return "Flesh";
        case 55:
            return "Smart";
        case 56:
            return "Electric";
        case 57:
            return "Shocking";
        case 58:
            return "Volt";
        case 59:
            return "Charged";
        case 60:
            return "Light";
        default:
            return "Element " + Integer.toString(pos + 1);
        }
    }

    private static boolean getElementEyeByPosition(final int pos) {
        switch (pos) {
        case 0:
        case 1:
        case 2:
        case 9:
        case 10:
        case 11:
        case 18:
        case 19:
        case 20:
        case 27:
        case 28:
        case 36:
        case 37:
        case 38:
        case 39:
        case 40:
        case 41:
        case 42:
        case 43:
        case 44:
        case 45:
            return false;
        default:
            return true;
        }
    }
}
