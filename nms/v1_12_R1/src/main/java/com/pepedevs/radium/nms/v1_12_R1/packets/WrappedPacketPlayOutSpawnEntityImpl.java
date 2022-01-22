package com.pepedevs.radium.nms.v1_12_R1.packets;

import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutSpawnEntity;
import com.pepedevs.radium.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.MathHelper;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.UUID;

public class WrappedPacketPlayOutSpawnEntityImpl implements WrappedPacketPlayOutSpawnEntity {

    private int entityId;
    private UUID uuid;
    private double locX;
    private double locY;
    private double locZ;
    private float yaw;
    private float pitch;
    private Vector velocity;
    private EntityType entityType;
    private int data;

    public WrappedPacketPlayOutSpawnEntityImpl(int entityId, UUID uuid, double locX, double locY, double locZ, float yaw, float pitch, Vector velocity, EntityType entityType, int data) {
        this.entityId = entityId;
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.velocity = velocity;
        this.entityType = entityType;
        this.data = data;
    }

    public WrappedPacketPlayOutSpawnEntityImpl(int entityId, UUID uuid, Location location, Vector velocity, EntityType entityType, int data) {
        this(entityId, uuid, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), velocity, entityType, data);
    }

    public WrappedPacketPlayOutSpawnEntityImpl(int entityId, UUID uuid, double locX, double locY, double locZ, float yaw, float pitch, EntityType entityType) {
        this(entityId, uuid, locX, locY, locZ, yaw, pitch, new Vector(0, 0, 0), entityType, 0);
    }

    public WrappedPacketPlayOutSpawnEntityImpl(int entityId, UUID uuid, Location location, EntityType entityType) {
        this(entityId, uuid, location, new Vector(0, 0, 0), entityType, 0);
    }

    public WrappedPacketPlayOutSpawnEntityImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityId = dataSerializer.g();
        this.uuid = dataSerializer.i();
        this.entityType = EntityType.fromId(dataSerializer.readByte());
        this.locX = dataSerializer.readDouble();
        this.locY = dataSerializer.readDouble();
        this.locZ = dataSerializer.readDouble();
        this.yaw = dataSerializer.readByte() / 256.0F * 360.0F;
        this.pitch = dataSerializer.readByte() / 256.0F * 360.0F;
        this.data = dataSerializer.readInt();
        this.velocity = new Vector(0, 0, 0);
        this.velocity.setX(dataSerializer.readShort() / 8000.0D);
        this.velocity.setY(dataSerializer.readShort() / 8000.0D);
        this.velocity.setZ(dataSerializer.readShort() / 8000.0D);
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
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    @Override
    public int getData() {
        return data;
    }

    @Override
    public void setData(int data) {
        this.data = data;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeVarInt(this.entityId)
                .serializeUUID(this.uuid)
                .serializeByte((byte) this.entityType.getTypeId())
                .serializeDouble(this.locX)
                .serializeDouble(this.locY)
                .serializeDouble(this.locZ)
                .serializeByte(MathHelper.d(this.pitch * 256.0F / 360.0F))
                .serializeByte(MathHelper.d(this.yaw * 256.0F / 360.0F))
                .serializeInt(this.data)
                .serializeShort((int) this.velocity.getX() * 8000)
                .serializeShort((int) this.velocity.getY() * 8000)
                .serializeShort((int) this.velocity.getZ() * 8000);
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
