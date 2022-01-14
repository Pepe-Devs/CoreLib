package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutRelEntityMove;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.MathHelper;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity;
import org.bukkit.Location;

import java.io.IOException;

public class WrappedPacketPlayOutRelEntityMoveImpl implements WrappedPacketPlayOutRelEntityMove {

    private int entityId;
    private int deltaX;
    private int deltaY;
    private int deltaZ;
    private boolean onGround;

    public WrappedPacketPlayOutRelEntityMoveImpl(int entityId, int deltaX, int deltaY, int deltaZ, boolean onGround) {
        this.entityId = entityId;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.onGround = onGround;
    }

    public WrappedPacketPlayOutRelEntityMoveImpl(int entityId, Location oldLocation, Location newLocation, boolean onGround) {
        this.entityId = entityId;
        this.deltaX = (int) (MathHelper.d(oldLocation.getX() * 4096.0D) - MathHelper.d(newLocation.getX() * 4096.0D));
        this.deltaY = (int) (MathHelper.d(oldLocation.getY() * 4096.0D) - MathHelper.d(newLocation.getY() * 4096.0D));
        this.deltaZ = (int) (MathHelper.d(oldLocation.getZ() * 4096.0D) - MathHelper.d(newLocation.getZ() * 4096.0D));
        this.onGround = onGround;
    }

    public WrappedPacketPlayOutRelEntityMoveImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityId = dataSerializer.g();
        this.deltaX = dataSerializer.readShort();
        this.deltaY = dataSerializer.readShort();
        this.deltaZ = dataSerializer.readShort();
        this.onGround = dataSerializer.readBoolean();
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
    public int getDeltaX() {
        return deltaX;
    }

    @Override
    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }

    @Override
    public int getDeltaY() {
        return deltaY;
    }

    @Override
    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    @Override
    public int getDeltaZ() {
        return deltaZ;
    }

    @Override
    public void setDeltaZ(int deltaZ) {
        this.deltaZ = deltaZ;
    }

    @Override
    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeVarInt(this.entityId)
                .serializeShort(this.deltaX)
                .serializeShort(this.deltaY)
                .serializeShort(this.deltaZ)
                .serializeBoolean(this.onGround);
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutEntity.PacketPlayOutRelEntityMove packet = new PacketPlayOutEntity.PacketPlayOutRelEntityMove();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
