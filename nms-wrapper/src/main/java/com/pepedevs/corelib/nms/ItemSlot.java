package com.pepedevs.corelib.nms;

public enum ItemSlot {

    MAINHAND(0),
    OFFHAND(5),
    FEET(1),
    LEGS(2),
    CHEST(3),
    HEAD(4);

    public final int OLD;

    ItemSlot(int oldMap) {
        this.OLD = oldMap;
    }

    public static ItemSlot fromOldSlot(int slot) {
        for (ItemSlot value : values()) {
            if (value.OLD == slot) return value;
        }
        return null;
    }

}
