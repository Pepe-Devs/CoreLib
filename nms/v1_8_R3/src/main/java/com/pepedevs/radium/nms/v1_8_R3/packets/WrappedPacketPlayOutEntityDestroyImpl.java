package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutEntityDestroy;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;

import java.io.IOException;

public class WrappedPacketPlayOutEntityDestroyImpl implements WrappedPacketPlayOutEntityDestroy {

    private int[] entityIDs;

    public WrappedPacketPlayOutEntityDestroyImpl(int... entityIDs) {
        this.entityIDs = entityIDs;
    }

    public WrappedPacketPlayOutEntityDestroyImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityIDs = new int[dataSerializer.e()];
        for (int i = 0; i < this.entityIDs.length; i++) {
            this.entityIDs[i] = dataSerializer.e();
        }
    }

    @Override
    public int[] getEntityIDs() {
        return entityIDs;
    }

    @Override
    public void setEntityIDs(int[] entityIDs) {
        this.entityIDs = entityIDs;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeVarInt(this.entityIDs.length);
        for (int entityID : this.entityIDs) {
            serializer.serializeVarInt(entityID);
        }
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }
}
