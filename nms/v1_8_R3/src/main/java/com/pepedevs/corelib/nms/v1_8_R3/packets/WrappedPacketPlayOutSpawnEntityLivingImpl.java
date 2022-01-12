package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutSpawnEntityLiving;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import com.pepedevs.corelib.utils.reflection.accessor.FieldAccessor;
import com.pepedevs.corelib.utils.reflection.resolver.FieldResolver;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.UUID;

public class WrappedPacketPlayOutSpawnEntityLivingImpl implements WrappedPacketPlayOutSpawnEntityLiving {

    private static final FieldAccessor DATA_WATCHER_FIELD = new FieldResolver(PacketPlayOutSpawnEntityLiving.class).resolveAccessor("l");

    private int entityId;
    private UUID uuid;
    private EntityType entityType;
    private double locX;
    private double locY;
    private double locZ;
    private float yaw;
    private float pitch;
    private float headRotation;
    private Vector velocity;
    private DataWatcher dataWatcher;

    public WrappedPacketPlayOutSpawnEntityLivingImpl(int entityId, UUID uuid, EntityType entityType, double locX, double locY, double locZ, float yaw, float pitch, float headRotation, Vector velocity, Object dataWatcher) {
        this.entityId = entityId;
        this.entityType = entityType;
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.headRotation = headRotation;
        this.velocity = velocity;
        this.dataWatcher = (DataWatcher) dataWatcher;
    }

    public WrappedPacketPlayOutSpawnEntityLivingImpl(int entityId, UUID uuid, EntityType entity, Location location, float headPitch, Vector velocity, Object dataWatcher) {
        this(entityId, uuid, entity, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), headPitch, velocity, (DataWatcher) dataWatcher);
    }

    public WrappedPacketPlayOutSpawnEntityLivingImpl(int entityId, UUID uuid, EntityType entity, double locX, double locY, double locZ, float yaw, float pitch, float headPitch, Object dataWatcher) {
        this(entityId, uuid, entity, locX, locY, locZ, yaw, pitch, headPitch, new Vector(0, 0, 0), dataWatcher);
    }

    public WrappedPacketPlayOutSpawnEntityLivingImpl(int entityId, UUID uuid, EntityType entity, Location location, float headPitch, Object dataWatcher) {
        this(entityId, uuid, entity, location, headPitch, new Vector(0, 0, 0), dataWatcher);
    }

    public WrappedPacketPlayOutSpawnEntityLivingImpl(WrappedPacketDataSerializer serializer, Object dataWatcher) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityId = dataSerializer.e();
        this.entityType = EntityType.fromId(dataSerializer.readByte() & 255);
        this.locX = dataSerializer.readInt() / 32;
        this.locY = dataSerializer.readInt() / 32;
        this.locZ = dataSerializer.readInt() / 32;
        this.yaw = dataSerializer.readByte() * 360 / 256.0F;
        this.pitch = dataSerializer.readByte() * 360 / 256.0F;
        this.headRotation = dataSerializer.readByte() * 360 / 256.0F;
        this.velocity = new Vector(dataSerializer.readShort() / 8000.0D, dataSerializer.readShort() / 8000.0D, dataSerializer.readShort() / 8000.0D);
        this.dataWatcher = (DataWatcher) dataWatcher;
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
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
    public float getHeadRotation() {
        return headRotation;
    }

    @Override
    public void setHeadRotation(float headRotation) {
        this.headRotation = headRotation;
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
    public Object getDataWatcher() {
        return dataWatcher;
    }

    @Override
    public void setDataWatcher(Object dataWatcher) {
        this.dataWatcher = (DataWatcher) dataWatcher;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(this.entityId)
                .serializeByte(((byte) this.entityType.getTypeId()) & 255)
                .serializeInt(MathHelper.floor(this.locX * 32.0D))
                .serializeInt(MathHelper.floor(this.locY * 32.0D))
                .serializeInt(MathHelper.floor(this.locZ * 32.0D))
                .serializeByte((byte) ((int) (this.yaw * 256.0F / 360.0F)))
                .serializeByte((byte) ((int) (this.pitch * 256.0F / 360.0F)))
                .serializeByte((byte) ((int) (this.headRotation * 256.0F / 360.0F)))
                .serializeShort((int) (velocity.getX() * 8000.0D))
                .serializeShort((int) (velocity.getY() * 8000.0D))
                .serializeShort((int) (velocity.getZ() * 8000.0D));
        try {
            this.dataWatcher.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving();
        try {
            packet.a((PacketDataSerializer) buildData());
            DATA_WATCHER_FIELD.set(packet, this.dataWatcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
