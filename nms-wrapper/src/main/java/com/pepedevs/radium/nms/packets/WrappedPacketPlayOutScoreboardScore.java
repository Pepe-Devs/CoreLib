package com.pepedevs.radium.nms.packets;

public interface WrappedPacketPlayOutScoreboardScore extends WrappedPacket{

    String getPlayerName();

    void setPlayerName(String playerName);

    String getObjectiveName();

    void setObjectiveName(String objectiveName);

    int getValue();

    void setValue(int value);

    ScoreboardAction getAction();

    void setAction(ScoreboardAction action);

    enum ScoreboardAction {
        CHANGE,
        REMOVE
    }

}
