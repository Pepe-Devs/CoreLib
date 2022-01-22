package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutEntityHeadRotation;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;

import java.io.IOException;

public class WrappedPacketPlayOutEntityHeadRotationImpl implements WrappedPacketPlayOutEntityHeadRotation {

    private int entityId;
    private float headRotation;

    public WrappedPacketPlayOutEntityHeadRotationImpl(int entityId, float headRotation) {
        this.entityId = entityId;
        this.headRotation = headRotation;
    }

    public WrappedPacketPlayOutEntityHeadRotationImpl(WrappedPacketDataSerializer dataSerializer) {
        PacketDataSerializer serializer = (PacketDataSerializer) dataSerializer;
        this.entityId = serializer.e();
        this.headRotation = serializer.readByte() * 360.0F / 256.0F;
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
    public float getHeadRotation() {
        return headRotation;
    }

    @Override
    public void setHeadRotation(float headRotation) {
        this.headRotation = headRotation;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer dataSerializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        dataSerializer.serializeVarInt(this.entityId)
                .serializeByte(MathHelper.d(this.headRotation * 256.0F / 360.0F));
        return dataSerializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutEntityHeadRotation packet = new PacketPlayOutEntityHeadRotation();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
