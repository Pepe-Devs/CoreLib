package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutAnimation;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;

import java.io.IOException;

public class WrappedPacketPlayOutAnimationImpl implements WrappedPacketPlayOutAnimation {

    private int entityId;
    private AnimationType animationType;

    public WrappedPacketPlayOutAnimationImpl(int entityId, AnimationType animationType) {
        this.entityId = entityId;
        this.animationType = animationType;
    }

    public WrappedPacketPlayOutAnimationImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityId = dataSerializer.e();
        this.animationType = AnimationType.values()[dataSerializer.readByte()];
    }

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Override
    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public AnimationType getAnimationType() {
        return animationType;
    }

    @Override
    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeVarInt(this.entityId)
                .serializeByte(this.animationType.ordinal());
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
