package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.EntityStatus;
import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityStatus;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;

import java.io.IOException;

public class WrappedPacketPlayOutEntityStatusImpl extends PacketPlayOutEntityStatus implements WrappedPacketPlayOutEntityStatus {

    public WrappedPacketPlayOutEntityStatusImpl(int entityID, EntityStatus status) {
        this(entityID, status.STATUS_NUMBER);
    }

    public WrappedPacketPlayOutEntityStatusImpl(int entityID, byte status) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer().serializeInt(entityID).serializeByte(status);
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
