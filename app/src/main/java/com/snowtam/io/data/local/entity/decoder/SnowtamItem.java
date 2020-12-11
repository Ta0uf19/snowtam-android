package com.snowtam.io.data.local.entity.decoder;

import com.snowtam.io.utils.SnowtamDecoder;

public class SnowtamItem {

    // attribute (A, B, C..)
    private String attr;

    // name of attribute
    private String name;

    private String originalValue;

    // value of this attribute
    private String value;

    // picture
    private String picture;


    public SnowtamItem(String attr, String original_value, String picture) {
        this.attr = attr;
        this.originalValue = original_value;
        this.picture = picture;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "SnowtamItem{" +
                "attr='" + attr + '\'' +
                ", name='" + name + '\'' +
                ", originalValue='" + originalValue + '\'' +
                ", value='" + value + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
