package com.pepedevs.corelib.nms.packets;

public interface WrappedPacketPlayOutScoreboardDisplayObjective extends WrappedPacket {

    int getPosition();

    ScoreboardPosition getPositionEnum();

    void setPosition(int position);

    void setPosition(ScoreboardPosition position);

    String getName();

    void setName(String name);

    enum ScoreboardPosition {
        NONE,
        SIDEBAR,
        BELOW_NAME,
        PLAYER_LIST
    }

}
