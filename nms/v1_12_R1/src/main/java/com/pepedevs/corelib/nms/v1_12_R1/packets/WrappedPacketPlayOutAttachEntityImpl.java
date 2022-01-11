package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutAttachEntity;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutAttachEntity;

import java.io.IOException;

public class WrappedPacketPlayOutAttachEntityImpl extends PacketPlayOutAttachEntity implements WrappedPacketPlayOutAttachEntity {

    public WrappedPacketPlayOutAttachEntityImpl(AttachmentType ignored, int riderID, int providerID) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeInt(riderID).serializeInt(providerID);
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutAttachEntityImpl(AttachmentType ignored, int riderID) {
        this(ignored, riderID, -1);
    }

    public WrappedPacketPlayOutAttachEntityImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
