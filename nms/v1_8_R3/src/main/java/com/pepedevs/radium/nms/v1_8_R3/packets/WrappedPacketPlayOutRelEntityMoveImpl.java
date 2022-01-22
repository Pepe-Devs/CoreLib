package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutRelEntityMove;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
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
        this.deltaX = MathHelper.floor(oldLocation.getX() * 32.0D) - MathHelper.floor(newLocation.getX() * 32.0D);
        this.deltaY = MathHelper.floor(oldLocation.getY() * 32.0D) - MathHelper.floor(newLocation.getY() * 32.0D);
        this.deltaZ = MathHelper.floor(oldLocation.getZ() * 32.0D) - MathHelper.floor(newLocation.getZ() * 32.0D);
        this.onGround = onGround;
    }

    public WrappedPacketPlayOutRelEntityMoveImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityId = dataSerializer.e();
        this.deltaX = dataSerializer.readByte();
        this.deltaY = dataSerializer.readByte();
        this.deltaZ = dataSerializer.readByte();
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
                .serializeByte(this.deltaX)
                .serializeByte(this.deltaY)
                .serializeByte(this.deltaZ)
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
