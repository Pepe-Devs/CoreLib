package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutScoreboardTeam {

    enum TeamMode {
        CREATE,
        REMOVE,
        UPDATE,
        ADD_PLAYERS,
        REMOVE_PLAYERS;
    }

}
