package santorini.model.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *Class Color
 * @author G. Perego
 */

public enum Color implements Serializable {
    BLUE("B"),RED("R"), GREEN("G"),YELLOW("Y"), PURPLE("P"),WHITE("W");
    private String colorAbb;

    /**
     * constructor of class Color
     * @param colorAbb :abbreviation of the color
     */
    Color(String colorAbb) {
        this.colorAbb = colorAbb;
    }

    /**
     * method getAbbreviation
     * @return colorAbb : the abbreviation of the color
     */

    public String getAbbreviation() {
        return colorAbb;
    }

    /**
     * method getAbreviation
     * create an Array of the abbreviations of the colors
     * @return the result of the abbreviation
     */

    public static List<String> getAbbreviations(){
        List<String> result = new ArrayList<String>();
        result.add("W");
        result.add("R");
        result.add("G");
        result.add("B");
        result.add("Y");
        result.add("P");
        return result;
    }

    /**
     *
     * @param color
     * the method associates an integer number for each color
     * @return the integer number if there is the color, else return -10
     */

    public static int fromColor (Color color){
        int i = 0;
        for (Color col: Color.values()){
            if (col == color)return i;
            i = i +1;
        }
        return -10;
    }

    /**
     *
     * @param string the string is the abbreviation of the color
     * @return the color if there is its abbreviation, return null if there is not the abbreviation of the color
     */

    public static Color fromAbbreviation(String string){
        if ("W".equals(string)) {
            return Color.WHITE;
        } else if ("B".equals(string)) {
            return Color.BLUE;
        } else if ("R".equals(string)) {
            return Color.RED;
        } else if ("G".equals(string)) {
            return Color.GREEN;
        } else if ("Y".equals(string)) {
            return Color.YELLOW;
        } else if ("P".equals(string)) {
            return Color.PURPLE;
        }
        return null;
    }
}



