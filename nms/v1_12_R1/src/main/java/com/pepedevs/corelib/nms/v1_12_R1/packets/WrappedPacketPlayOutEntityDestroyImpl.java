package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityDestroy;
import com.pepedevs.corelib.nms.v1_12_R1.NMSImpl;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;

import java.io.IOException;

public class WrappedPacketPlayOutEntityDestroyImpl extends PacketPlayOutEntityDestroy implements WrappedPacketPlayOutEntityDestroy {

    public WrappedPacketPlayOutEntityDestroyImpl(int... entityIDs) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(entityIDs.length);
        for (int entityID : entityIDs) {
            serializer.serializeIntToByte(entityID);
        }
    }

    public WrappedPacketPlayOutEntityDestroyImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
