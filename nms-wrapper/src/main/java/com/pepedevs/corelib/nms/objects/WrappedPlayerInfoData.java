package com.pepedevs.corelib.nms.objects;

import com.mojang.authlib.GameProfile;
import com.pepedevs.corelib.nms.EnumGameMode;
import net.kyori.adventure.text.Component;

public interface WrappedPlayerInfoData {

    GameProfile getGameProfile();

    int getLatency();

    EnumGameMode getGameMode();

    Object getEnumGameMode();

    String getName();

    Object getNameComponent();

    Component getComponent();

}
