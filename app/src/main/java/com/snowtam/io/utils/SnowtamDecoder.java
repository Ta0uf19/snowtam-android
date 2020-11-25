package com.snowtam.io.utils;

import com.snowtam.io.data.local.entity.SnowtamItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SnowtamDecoder {
    private SnowtamDecoder(String coded) { }

    // A -> value
    static Map<String, String> map = new HashMap<String, String>();

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
        return null;
    }

    /**
     * Encode snowtam
     * @param snowtamItems
     * @return String of encoded snowtam
     */

    public static String encode(List<SnowtamItem> snowtamItems) {


        return null;
    }

}
