package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityMetadata;
import com.pepedevs.corelib.nms.v1_12_R1.NMSImpl;
import net.minecraft.server.v1_12_R1.DataWatcher;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityMetadata;

import java.io.IOException;
import java.util.List;

public class WrappedPacketPlayOutEntityMetadataImpl extends PacketPlayOutEntityMetadata implements WrappedPacketPlayOutEntityMetadata {

    public WrappedPacketPlayOutEntityMetadataImpl(int entityId, Object watchableObjects) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(entityId);

        List<DataWatcher.Item<?>> watchableObjectList = (List<DataWatcher.Item<?>>) watchableObjects;
        try {
            DataWatcher.a(watchableObjectList, (PacketDataSerializer) serializer);
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutEntityMetadataImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
