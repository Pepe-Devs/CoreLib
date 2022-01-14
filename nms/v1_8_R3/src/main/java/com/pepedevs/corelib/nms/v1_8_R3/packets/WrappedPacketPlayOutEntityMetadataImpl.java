package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityMetadata;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;

import java.io.IOException;
import java.util.List;

public class WrappedPacketPlayOutEntityMetadataImpl implements WrappedPacketPlayOutEntityMetadata {

    private int entityId;
    private List<DataWatcher.WatchableObject> watchableObjects;

    public WrappedPacketPlayOutEntityMetadataImpl(int entityId, Object watchableObjects) {
        this.entityId = entityId;
        this.watchableObjects = (List<DataWatcher.WatchableObject>) watchableObjects;
    }

    public WrappedPacketPlayOutEntityMetadataImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityId = dataSerializer.e();
        try {
            this.watchableObjects = DataWatcher.b(dataSerializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public Object getWatchableObjects() {
        return watchableObjects;
    }

    @Override
    public void setWatchableObjects(Object watchableObjects) {
        this.watchableObjects = (List<DataWatcher.WatchableObject>) watchableObjects;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeVarInt(this.entityId);
        try {
            DataWatcher.a(this.watchableObjects, (PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
