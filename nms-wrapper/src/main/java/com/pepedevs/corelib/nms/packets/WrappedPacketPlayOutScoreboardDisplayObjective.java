package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutScoreboardDisplayObjective extends WrappedPacket{

    enum ScoreboardPosition {
        NONE,
        SIDEBAR,
        BELOW_NAME,
        PLAYER_LIST
    }

}
