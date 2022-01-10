package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutAttachEntity;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;

import java.io.IOException;

public class WrappedPacketPlayOutAttachEntityImpl extends PacketPlayOutAttachEntity implements WrappedPacketPlayOutAttachEntity {

    public WrappedPacketPlayOutAttachEntityImpl(AttachmentType type, int riderID, int providerID) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeInt(riderID).serializeInt(providerID).serializeByte(type.ordinal());
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutAttachEntityImpl(AttachmentType type, int riderID) {
        this(type, riderID, -1);
    }

    public WrappedPacketPlayOutAttachEntityImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
