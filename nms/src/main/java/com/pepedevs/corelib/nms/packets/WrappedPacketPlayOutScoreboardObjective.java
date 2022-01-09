package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutScoreboardObjective extends WrappedPacket {

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
