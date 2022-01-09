package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutPlayerInfo;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.WorldSettings;

import java.io.IOException;
import java.util.List;

public class WrappedPacketPlayOutPlayerInfoImpl extends PacketPlayOutPlayerInfo implements WrappedPacketPlayOutPlayerInfo {

    public WrappedPacketPlayOutPlayerInfoImpl(PlayerInfoAction action, List<WrappedPlayerInfoData> data) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeEnum(EnumPlayerInfoAction.valueOf(action.name()))
                .serializeIntToByte(data.size());
        for (WrappedPlayerInfoData infoData : data) {
            switch (action) {
                case ADD_PLAYER:
                    serializer.serializeUUID(infoData.getGameProfile().getId())
                            .serializeString(infoData.getGameProfile().getName())
                            .serializeIntToByte(infoData.getGameProfile().getProperties().size());
                    for (Property value : infoData.getGameProfile().getProperties().values()) {
                        serializer.serializeString(value.getName())
                                .serializeString(value.getValue());
                        if (value.hasSignature()) {
                            serializer.serializeBoolean(true)
                                    .serializeString(value.getSignature());
                        } else {
                            serializer.serializeBoolean(false);
                        }
                    }

                    serializer.serializeIntToByte(((WorldSettings.EnumGamemode) infoData.getEnumGameMode()).getId())
                            .serializeIntToByte(infoData.getLatency());

                    if (infoData.getNameComponent() == null) {
                        serializer.serializeBoolean(false);
                    } else {
                        serializer.serializeBoolean(true)
                                .serializeComponent(infoData.getNameComponent());
                    }
                    break;
                case UPDATE_GAME_MODE:
                    serializer.serializeUUID(infoData.getGameProfile().getId())
                            .serializeIntToByte(((WorldSettings.EnumGamemode) infoData.getEnumGameMode()).getId());
                    break;
                case UPDATE_LATENCY:
                    serializer.serializeUUID(infoData.getGameProfile().getId())
                            .serializeIntToByte(infoData.getLatency());
                    break;
                case UPDATE_DISPLAY_NAME:
                    serializer.serializeUUID(infoData.getGameProfile().getId());
                    if (infoData.getNameComponent() == null) {
                        serializer.serializeBoolean(false);
                    } else {
                        serializer.serializeBoolean(true)
                                .serializeComponent(infoData.getNameComponent());
                    }
                    break;
                case REMOVE_PLAYER:
                    serializer.serializeUUID(infoData.getGameProfile().getId());
                    break;
            }
        }

        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class WrappedPlayerInfoDataImpl extends PlayerInfoData implements WrappedPlayerInfoData {

        public WrappedPlayerInfoDataImpl(GameProfile gameProfile, int latency, EnumGameMode gamemode, String name) {
            super(gameProfile, latency, WorldSettings.EnumGamemode.valueOf(gamemode.name()), name == null ? null : (IChatBaseComponent) NMSImpl.INSTANCE.craftChatMessageFromString(name)[0]);
        }

        public WrappedPlayerInfoDataImpl(GameProfile gameProfile, int latency, EnumGameMode gamemode, Object nameComponent) {
            super(gameProfile, latency, WorldSettings.EnumGamemode.valueOf(gamemode.name()), (IChatBaseComponent) nameComponent);
        }

        public WrappedPlayerInfoDataImpl(GameProfile gameProfile, int latency, EnumGameMode gamemode, Component nameComponent) {
            super(gameProfile, latency, WorldSettings.EnumGamemode.valueOf(gamemode.name()), nameComponent == null ? null : (IChatBaseComponent) AdventureUtils.asVanilla(nameComponent));
        }

        @Override
        public GameProfile getGameProfile() {
            return this.a();
        }

        @Override
        public int getLatency() {
            return this.b();
        }

        @Override
        public EnumGameMode getGameMode() {
            return EnumGameMode.valueOf(this.c().name());
        }

        @Override
        public Object getEnumGameMode() {
            return this.c();
        }

        @Override
        public String getName() {
            return this.d() == null ? null : NMSImpl.INSTANCE.craftChatMessageFromComponent(this.d());
        }

        @Override
        public Object getNameComponent() {
            return this.d();
        }

        @Override
        public Component getComponent() {
            return this.d() == null ? null : AdventureUtils.asAdventure(this.d());
        }

    }

}
