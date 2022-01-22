package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.nms.EntityStatus;
import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutEntityStatus;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;

import java.io.IOException;

public class WrappedPacketPlayOutEntityStatusImpl implements WrappedPacketPlayOutEntityStatus {

    private int entityID;
    private byte status;

    public WrappedPacketPlayOutEntityStatusImpl(int entityID, EntityStatus status) {
        this.entityID = entityID;
        this.status = status.STATUS_NUMBER;
    }

    public WrappedPacketPlayOutEntityStatusImpl(int entityID, byte status) {
        this.entityID = entityID;
        this.status = status;
    }

    public WrappedPacketPlayOutEntityStatusImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityID = dataSerializer.readInt();
        this.status = dataSerializer.readByte();
    }

    @Override
    public int getEntityID() {
        return entityID;
    }

    @Override
    public byte getStatus() {
        return status;
    }

    @Override
    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public void setStatus(EntityStatus status) {
        this.status = status.STATUS_NUMBER;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeInt(entityID).serializeByte(this.status);
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }
}
