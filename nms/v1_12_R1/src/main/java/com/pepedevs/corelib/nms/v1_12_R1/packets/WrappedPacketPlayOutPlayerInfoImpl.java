package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.nms.EnumGameMode;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.objects.WrappedPlayerInfoData;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutPlayerInfo;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import com.pepedevs.corelib.nms.v1_12_R1.objects.WrappedPlayerInfoDataImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_12_R1.*;

import java.io.IOException;
import java.util.List;

public class WrappedPacketPlayOutPlayerInfoImpl implements WrappedPacketPlayOutPlayerInfo {

    private PlayerInfoAction infoAction;
    private List<WrappedPlayerInfoData> infoData;

    public WrappedPacketPlayOutPlayerInfoImpl(PlayerInfoAction action, List<WrappedPlayerInfoData> data) {
        this.infoAction = action;
        this.infoData = data;
    }

    public WrappedPacketPlayOutPlayerInfoImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.infoAction = dataSerializer.a(PlayerInfoAction.class);
        int size = dataSerializer.g();

        for(int i = 0; i < size; ++i) {
            GameProfile gameProfile = null;
            int latency = 0;
            EnumGameMode gamemode = null;
            Component displayName = null;
            switch(this.infoAction) {
                case ADD_PLAYER:
                    gameProfile = new GameProfile(dataSerializer.i(), dataSerializer.e(16));
                    int propertySize = dataSerializer.g();

                    for(int j = 0; j < propertySize; ++j) {
                        String propertyName = dataSerializer.e(32767);
                        String propertyValue = dataSerializer.e(32767);
                        if (dataSerializer.readBoolean()) {
                            gameProfile.getProperties().put(propertyName, new Property(propertyName, propertyValue, dataSerializer.e(32767)));
                        } else {
                            gameProfile.getProperties().put(propertyName, new Property(propertyName, propertyValue));
                        }
                    }

                    gamemode = EnumGameMode.valueOf(EnumGamemode.getById(dataSerializer.g()).name());
                    latency = dataSerializer.g();
                    if (dataSerializer.readBoolean()) {
                        displayName = AdventureUtils.asAdventure(dataSerializer.f());
                    }
                    break;
                case UPDATE_GAME_MODE:
                    gameProfile = new GameProfile(dataSerializer.i(), null);
                    gamemode = EnumGameMode.valueOf(EnumGamemode.getById(dataSerializer.g()).name());
                    break;
                case UPDATE_LATENCY:
                    gameProfile = new GameProfile(dataSerializer.i(), null);
                    latency = dataSerializer.g();
                    break;
                case UPDATE_DISPLAY_NAME:
                    gameProfile = new GameProfile(dataSerializer.i(), null);
                    if (dataSerializer.readBoolean()) {
                        displayName = AdventureUtils.asAdventure(dataSerializer.f());
                    }
                    break;
                case REMOVE_PLAYER:
                    gameProfile = new GameProfile(dataSerializer.i(), null);
            }

            this.infoData.add(new WrappedPlayerInfoDataImpl(gameProfile, latency, gamemode == null ? EnumGameMode.NOT_SET : gamemode, displayName));
        }
    }

    @Override
    public PlayerInfoAction getInfoAction() {
        return infoAction;
    }

    @Override
    public void setInfoAction(PlayerInfoAction infoAction) {
        this.infoAction = infoAction;
    }

    @Override
    public List<WrappedPlayerInfoData> getInfoData() {
        return infoData;
    }

    @Override
    public void setInfoData(List<WrappedPlayerInfoData> infoData) {
        this.infoData = infoData;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeEnum(this.infoAction)
                .serializeVarInt(this.infoData.size());
        for (WrappedPlayerInfoData infoData : this.infoData) {
            switch (this.infoAction) {
                case ADD_PLAYER:
                    serializer
                            .serializeUUID(infoData.getGameProfile().getId())
                            .serializeString(infoData.getGameProfile().getName())
                            .serializeVarInt(infoData.getGameProfile().getProperties().size());
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

                    serializer.serializeVarInt(((EnumGamemode) infoData.getEnumGameMode()).getId())
                            .serializeVarInt(infoData.getLatency());

                    if (infoData.getNameComponent() == null) {
                        serializer.serializeBoolean(false);
                    } else {
                        serializer.serializeBoolean(true)
                                .serializeComponent(infoData.getNameComponent());
                    }
                    break;
                case UPDATE_GAME_MODE:
                    serializer.serializeUUID(infoData.getGameProfile().getId())
                            .serializeVarInt(((EnumGamemode) infoData.getEnumGameMode()).getId());
                    break;
                case UPDATE_LATENCY:
                    serializer.serializeUUID(infoData.getGameProfile().getId())
                            .serializeVarInt(infoData.getLatency());
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

        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
