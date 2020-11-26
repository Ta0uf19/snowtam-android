package com.snowtam.io.data.local.entity.decoder;

public enum Friction {
    DUMMY,
    POOR,
    MEDIUMPOOR,
    MEDIUM,
    MEDIUMGOOD,
    GOOD;

    private static Friction[] list = Friction.values();

    public static Friction get(int i) {
        return list[i];
    }
    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
