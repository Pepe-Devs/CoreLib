package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.nms.EnumGameMode;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutPlayerInfo;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_12_R1.*;

import java.io.IOException;
import java.util.List;

public class WrappedPacketPlayOutPlayerInfoImpl extends PacketPlayOutPlayerInfo implements WrappedPacketPlayOutPlayerInfo {

    public WrappedPacketPlayOutPlayerInfoImpl(PlayerInfoAction action, List<WrappedPlayerInfoData> data) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
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

                    serializer.serializeIntToByte(((EnumGamemode) infoData.getEnumGameMode()).getId())
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
                            .serializeIntToByte(((EnumGamemode) infoData.getEnumGameMode()).getId());
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

    public WrappedPacketPlayOutPlayerInfoImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class WrappedPlayerInfoDataImpl implements WrappedPlayerInfoData {

        private final int latency;
        private final EnumGamemode gamemode;
        private final GameProfile gameProfile;
        private final IChatBaseComponent name;

        public WrappedPlayerInfoDataImpl(GameProfile gameProfile, int latency, EnumGameMode gamemode, String name) {
            this.latency = latency;
            this.name = name == null ? null : (IChatBaseComponent) NMSProviderImpl.INSTANCE.craftChatMessageFromString(name)[0];
            this.gamemode = EnumGamemode.valueOf(gamemode.name());
            this.gameProfile = gameProfile;
        }

        public WrappedPlayerInfoDataImpl(GameProfile gameProfile, int latency, EnumGameMode gamemode, Object nameComponent) {
            this.latency = latency;
            this.name = (IChatBaseComponent) nameComponent;
            this.gamemode = EnumGamemode.valueOf(gamemode.name());
            this.gameProfile = gameProfile;
        }

        public WrappedPlayerInfoDataImpl(GameProfile gameProfile, int latency, EnumGameMode gamemode, Component nameComponent) {
            this.latency = latency;
            this.name = (IChatBaseComponent) AdventureUtils.asVanilla(nameComponent);
            this.gamemode = EnumGamemode.valueOf(gamemode.name());
            this.gameProfile = gameProfile;
        }

        @Override
        public GameProfile getGameProfile() {
            return this.gameProfile;
        }

        @Override
        public int getLatency() {
            return this.latency;
        }

        @Override
        public EnumGameMode getGameMode() {
            return EnumGameMode.valueOf(this.gamemode.name());
        }

        @Override
        public Object getEnumGameMode() {
            return this.gamemode;
        }

        @Override
        public String getName() {
            return this.name == null ? null : NMSProviderImpl.INSTANCE.craftChatMessageFromComponent(this.name);
        }

        @Override
        public Object getNameComponent() {
            return this.name;
        }

        @Override
        public Component getComponent() {
            return this.name == null ? null : AdventureUtils.asAdventure(this.name);
        }

    }

}
