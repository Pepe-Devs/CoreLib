package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityDestroy;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;

public class WrappedPacketPlayOutEntityDestroyImpl extends PacketPlayOutEntityDestroy implements WrappedPacketPlayOutEntityDestroy {

    public WrappedPacketPlayOutEntityDestroyImpl(int... entityIDs) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(entityIDs.length);
        for (int entityID : entityIDs) {
            serializer.serializeIntToByte(entityID);
        }
    }

}
