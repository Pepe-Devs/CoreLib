package com.pepedevs.corelib.nms.packets;

import com.mojang.authlib.GameProfile;
import com.pepedevs.corelib.nms.EnumGameMode;
import net.kyori.adventure.text.Component;

public interface WrappedPacketPlayOutPlayerInfo extends WrappedPacket {

    interface WrappedPlayerInfoData {

        GameProfile getGameProfile();

        int getLatency();

        EnumGameMode getGameMode();

        Object getEnumGameMode();

        String getName();

        Object getNameComponent();

        Component getComponent();

    }

    enum PlayerInfoAction {
        ADD_PLAYER,
        UPDATE_GAME_MODE,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME,
        REMOVE_PLAYER;
    }

}
