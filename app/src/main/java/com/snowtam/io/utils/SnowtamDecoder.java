package com.snowtam.io.data.local.entity;

import java.util.List;

public class Snowtam {

    // coded string
    private String coded;

    // encoded
    List<SnowtamItem> snowtamItems;


    public Snowtam(String coded) {
        this.coded = coded;

        // decode the string an
        List<SnowtamItem> snowtamItems = decode(coded);
        this.snowtamItems = snowtamItems;
    }


    private List<SnowtamItem> decode(String coded) {


        return null;
    }

    public Snowtam(List<SnowtamItem> snowtamItems) {
        this.snowtamItems = snowtamItems;
    }

    public List<SnowtamItem> getSnowtamItems() {
        return snowtamItems;
    }
}
