package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutAttachEntity;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;

import java.io.IOException;

public class WrappedPacketPlayOutAttachEntityImpl implements WrappedPacketPlayOutAttachEntity {

    private AttachmentType type;
    private int riderID;
    private int providerID;

    public WrappedPacketPlayOutAttachEntityImpl(AttachmentType type, int riderID, int providerID) {
        this.type = type;
        this.riderID = riderID;
        this.providerID = providerID;
    }

    public WrappedPacketPlayOutAttachEntityImpl(AttachmentType type, int riderID) {
        this(type, riderID, -1);
    }

    public WrappedPacketPlayOutAttachEntityImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.riderID = dataSerializer.readInt();
        this.providerID = dataSerializer.readInt();
        this.type = AttachmentType.values()[dataSerializer.readUnsignedByte()];
    }

    @Override
    public AttachmentType getType() {
        return type;
    }

    @Override
    public int getProviderID() {
        return providerID;
    }

    @Override
    public int getRiderID() {
        return riderID;
    }

    @Override
    public void setProviderID(int providerID) {
        this.providerID = providerID;
    }

    @Override
    public void setRiderID(int riderID) {
        this.riderID = riderID;
    }

    @Override
    public void setType(AttachmentType type) {
        this.type = type;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeInt(this.riderID)
                .serializeInt(this.providerID)
                .serializeByte(this.type.ordinal());
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutAttachEntity packet = new PacketPlayOutAttachEntity();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }
}
