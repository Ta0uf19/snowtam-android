package com.snowtam.io.utils;

import com.snowtam.io.data.local.entity.SnowtamItem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SnowtamDecoder {
    // A -> value
    private List<SnowtamItem> list;
    private String codedString;

    private SnowtamDecoder(String coded) {
        this.list = new ArrayList<SnowtamItem>();
        this.codedString = coded;
    }


    /**
     * Decode snowtam
     * @param coded
     * @return List of items with picture, name and value
     */
    public static List<SnowtamItem> decode(String coded) {

        /**
         * ([ABCDEFGHJKLMPS])\)\s*([A-Z0-9\/]*)[\s\t]*
         * ([NRT])\)\s*([A-Z0-9\/\ .,-]*)[\s\t]*
         */

        SnowtamDecoder stream = new SnowtamDecoder(coded);

        List<SnowtamItem> list = stream
                .parseABC()
                .parseNRT()
                .build();

        return list;
    }

    /**
     * Encode snowtam
     * @param snowtamItems
     * @return String of encoded snowtam
     */

    public static String encode(List<SnowtamItem> snowtamItems) {


        return null;
    }


    /**
     * Clean string and remove html tags
     * Return only SNOWTAM code
     * @param sequence
     * @return
     */
    public static String clean(CharSequence sequence) {
        String regex = "&lt;pre&gt;([\\s\\S]*)&lt;\\/pre&gt;";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(sequence);

        if(matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Parse ABCDEFGHJKLMPS
     */
    private SnowtamDecoder parseABC() {

        String regex = "([ABCDEFGHJKLMPS])\\)\\s*([A-Z0-9\\/]*)[\\s\\t]*";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(codedString);

        while(matcher.find()) {
            String attr = matcher.group(1).trim();
            String value = matcher.group(2).trim();

            SnowtamItem snowtamItem = processingSnowtam(attr, value);
            list.add(snowtamItem);
        }

        return this;
    }

    /**
     * Parse NRT
     */
    private SnowtamDecoder parseNRT() {
        return this;
    }

    private List<SnowtamItem> build() {
        return this.list;
    }

    /**
     * Processing snowtam and handling each case separatly
     * @param attr
     * @param value
     * @return
     */
    private SnowtamItem processingSnowtam(String attr, String value) {

        String picture = ("attr_" + attr + ".png").toLowerCase();
        SnowtamItem snowtamItem = new SnowtamItem(attr, value, picture);

        switch (snowtamItem.getAttr()) {
            case "A":
                snowtamItem.setName_attr("Aérodrome");
                snowtamItem.setValue(value);
                break;
            case "B":
                break;
            case "C":
                snowtamItem.setName_attr("Piste");
                snowtamItem.setValue("Runway "  + value);
                break;
            case "D":
                break;
            case "E":
                break;
            case "F":
                snowtamItem.setName_attr("Runway Contamination");
                break;
            case "G":
                break;
            case "H":
                break;
            case "J":
                break;
        }

        return snowtamItem;
    }

}
