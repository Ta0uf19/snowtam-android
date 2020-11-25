package com.snowtam.io.data.local.entity;

public class SnowtamItem {

    // attribute (A, B, C..)
    private String attr;

    // name of attribute
    private String name_attr;

    // value of this attribute
    private String value;

    // picture
    private String picture;


    public SnowtamItem(String attr, String name_attr, String value, String picture) {
        this.attr = attr;
        this.name_attr = name_attr;
        this.value = value;
        this.picture = picture;
    }
}
