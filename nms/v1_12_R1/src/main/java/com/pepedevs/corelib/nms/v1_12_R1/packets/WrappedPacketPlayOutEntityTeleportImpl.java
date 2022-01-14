package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityTeleport;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.MathHelper;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityTeleport;
import org.bukkit.Location;

import java.io.IOException;

public class WrappedPacketPlayOutEntityTeleportImpl implements WrappedPacketPlayOutEntityTeleport {

    private int entityId;
    private double locX;
    private double locY;
    private double locZ;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public WrappedPacketPlayOutEntityTeleportImpl(int entityId, int locX, int locY, int locZ, float yaw, float pitch, boolean onGround) {
        this.entityId = entityId;
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public WrappedPacketPlayOutEntityTeleportImpl(int entityId, Location location, boolean onGround) {
        this(entityId, location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getYaw(), location.getPitch(), onGround);
    }

    public WrappedPacketPlayOutEntityTeleportImpl(int entityId, int locX, int locY, int locZ, float yaw, float pitch) {
        this(entityId, locX, locY, locZ, yaw, pitch, true);
    }

    public WrappedPacketPlayOutEntityTeleportImpl(int entityId, Location location) {
        this(entityId, location, true);
    }

    public WrappedPacketPlayOutEntityTeleportImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityId = dataSerializer.readInt();
        this.locX = dataSerializer.readDouble();
        this.locY = dataSerializer.readDouble();
        this.locZ = dataSerializer.readDouble();
        this.yaw = dataSerializer.readByte() / 256.0F * 360.0F;
        this.pitch = dataSerializer.readByte() / 256.0F * 360.0F;
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
    public double getLocX() {
        return locX;
    }

    @Override
    public void setLocX(double locX) {
        this.locX = locX;
    }

    @Override
    public double getLocY() {
        return locY;
    }

    @Override
    public void setLocY(double locY) {
        this.locY = locY;
    }

    @Override
    public double getLocZ() {
        return locZ;
    }

    @Override
    public void setLocZ(double locZ) {
        this.locZ = locZ;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public void setPitch(float pitch) {
        this.pitch = pitch;
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
                .serializeDouble(this.locX)
                .serializeDouble(this.locY)
                .serializeDouble(this.locZ)
                .serializeByte(MathHelper.d(this.yaw * 256.0F / 360.0F))
                .serializeByte(MathHelper.d(this.pitch * 256.0F / 360.0F))
                .serializeBoolean(this.onGround);

        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
