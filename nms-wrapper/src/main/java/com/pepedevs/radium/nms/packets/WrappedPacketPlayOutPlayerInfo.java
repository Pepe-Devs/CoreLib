package com.pepedevs.radium.nms.packets;

import com.pepedevs.radium.nms.objects.WrappedPlayerInfoData;

import java.util.List;

public interface WrappedPacketPlayOutPlayerInfo extends WrappedPacket {

    PlayerInfoAction getInfoAction();

    void setInfoAction(PlayerInfoAction infoAction);

    List<WrappedPlayerInfoData> getInfoData();

    void setInfoData(List<WrappedPlayerInfoData> infoData);

    enum PlayerInfoAction {
        ADD_PLAYER,
        UPDATE_GAME_MODE,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        REMOVE_PLAYER;
    }

}
