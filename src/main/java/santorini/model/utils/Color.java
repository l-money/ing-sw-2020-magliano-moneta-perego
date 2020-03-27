package santorini.model.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum Color implements Serializable {
    BLUE("B"),RED("R"), GREEN("G"),YELLOW("Y"), PURPLE("P"),WHITE("W");
    private String colorAbb;

    Color(String colorAbb) {
        this.colorAbb = colorAbb;
    }

    public String getAbbreviation() {
        return colorAbb;
    }

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

    public static int fromColor (Color color){
        int i = 0;
        for (Color col: Color.values()){
            if (col == color)return i;
            i = i +1;
        }
        return -1;
    }

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



