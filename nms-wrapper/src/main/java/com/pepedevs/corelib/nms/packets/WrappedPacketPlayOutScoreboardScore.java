package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutScoreboardScore extends WrappedPacket{

    enum ScoreboardAction {
        CHANGE,
        REMOVE
    }

}
