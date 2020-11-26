package com.snowtam.io.data.local.entity;

import com.snowtam.io.utils.SnowtamDecoder;

public class SnowtamItem {

    // attribute (A, B, C..)
    private String attr;

    // name of attribute
    private String name_attr;

    private String original_value;

    // value of this attribute
    private String value;

    // picture
    private String picture;


    public SnowtamItem(String attr, String original_value, String picture) {
        this.attr = attr;
        this.original_value = original_value;
        this.picture = picture;
    }

    public void setName_attr(String name_attr) {
        this.name_attr = name_attr;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAttr() {
        return attr;
    }

    public String getOriginalV() {
        return original_value;
    }

    @Override
    public String toString() {
        return "SnowtamItem{" +
                "attr='" + attr + '\'' +
                ", name_attr='" + name_attr + '\'' +
                ", original_value='" + original_value + '\'' +
                ", value='" + value + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
