package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutScoreboardObjective extends WrappedPacket {

    String getName();

    void setName(String name);

    String getDisplayName();

    void setDisplayName(String displayName);

    HealthDisplay getDisplay();

    void setDisplay(HealthDisplay display);

    ObjectiveMode getMode();

    void setMode(ObjectiveMode mode);

    enum HealthDisplay {
        INTEGER,
        HEARTS;
    }

    enum ObjectiveMode {
        CREATE,
        REMOVE,
        UPDATE,;
    }

}
