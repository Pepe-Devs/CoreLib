package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutAttachEntity;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutAttachEntity;

import java.io.IOException;

public class WrappedPacketPlayOutAttachEntityImpl implements WrappedPacketPlayOutAttachEntity {

    private int riderID;
    private int providerID;

    public WrappedPacketPlayOutAttachEntityImpl(AttachmentType ignored, int riderID, int providerID) {
        this.riderID = riderID;
        this.providerID = providerID;
    }

    public WrappedPacketPlayOutAttachEntityImpl(AttachmentType ignored, int riderID) {
        this(ignored, riderID, -1);
    }

    public WrappedPacketPlayOutAttachEntityImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.riderID = dataSerializer.readInt();
        this.providerID = dataSerializer.readInt();
    }

    @Override
    public AttachmentType getType() {
        return null;
    }

    @Override
    public int getProviderID() {
        return this.providerID;
    }

    @Override
    public int getRiderID() {
        return this.riderID;
    }

    @Override
    public void setType(AttachmentType type) {}

    @Override
    public void setProviderID(int providerID) {
        this.providerID = providerID;
    }

    @Override
    public void setRiderID(int riderID) {
        this.riderID = riderID;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeInt(riderID).serializeInt(providerID);
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
