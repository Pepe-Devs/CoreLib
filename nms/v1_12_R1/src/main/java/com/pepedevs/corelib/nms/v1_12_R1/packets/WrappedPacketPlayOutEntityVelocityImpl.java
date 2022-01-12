package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityVelocity;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityVelocity;
import org.bukkit.util.Vector;

import java.io.IOException;

public class WrappedPacketPlayOutEntityVelocityImpl extends PacketPlayOutEntityVelocity implements WrappedPacketPlayOutEntityVelocity {

    private int entityID;
    private Vector velocity;

    public WrappedPacketPlayOutEntityVelocityImpl(int entityID, Vector vector) {
        this.entityID = entityID;
        this.velocity = vector;
    }

    public WrappedPacketPlayOutEntityVelocityImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityID = dataSerializer.g();
        this.velocity = new Vector(dataSerializer.readShort()/8000, dataSerializer.readShort()/8000, dataSerializer.readShort()/8000);
    }

    @Override
    public int getEntityID() {
        return entityID;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(this.entityID);
        serializer.serializeShort((int) (velocity.getX() * 8000.0D)).serializeShort((int) (velocity.getY() * 8000.0D)).serializeShort((int) (velocity.getZ() * 8000.0D));
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutEntityVelocity packet = new PacketPlayOutEntityVelocity();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
