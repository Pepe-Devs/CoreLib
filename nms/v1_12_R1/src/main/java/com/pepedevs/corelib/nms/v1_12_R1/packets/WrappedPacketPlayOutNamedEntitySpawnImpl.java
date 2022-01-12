package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutNamedEntitySpawn;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import com.pepedevs.corelib.utils.reflection.accessor.FieldAccessor;
import com.pepedevs.corelib.utils.reflection.resolver.FieldResolver;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.UUID;

public class WrappedPacketPlayOutNamedEntitySpawnImpl implements WrappedPacketPlayOutNamedEntitySpawn {

    private static final FieldAccessor DATA_WATCHER_FIELD = new FieldResolver(PacketPlayOutNamedEntitySpawn.class).resolveAccessor("h");

    private int entityId;
    private UUID uuid;
    private double locX;
    private double locY;
    private double locZ;
    private float yaw;
    private float pitch;
    private ItemStack mainHandItem;
    private DataWatcher dataWatcher;

    public WrappedPacketPlayOutNamedEntitySpawnImpl(int entityId, UUID uuid, double locX, double locY, double locZ, float yaw, float pitch, Object dataWatcher) {
        this.entityId = entityId;
        this.uuid = uuid;
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.dataWatcher = (DataWatcher) dataWatcher;
    }

    public WrappedPacketPlayOutNamedEntitySpawnImpl(int entityId, UUID uuid, Location location, Object dataWatcher) {
        this(entityId, uuid, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), dataWatcher);
    }

    public WrappedPacketPlayOutNamedEntitySpawnImpl(WrappedPacketDataSerializer serializer, Object dataWatcher) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityId = dataSerializer.g();
        this.uuid = dataSerializer.i();
        this.locX = dataSerializer.readInt() / 32.0D;
        this.locY = dataSerializer.readInt() / 32.0D;
        this.locZ = dataSerializer.readInt() / 32.0D;
        this.yaw = dataSerializer.readByte() * 360.0F / 256.0F;
        this.pitch = dataSerializer.readByte() * 360.0F / 256.0F;
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
    public ItemStack getMainHandItem() {
        return mainHandItem;
    }

    @Override
    public void setMainHandItem(ItemStack mainHandItem) {
        this.mainHandItem = mainHandItem;
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
        serializer.serializeIntToByte(entityId)
                .serializeUUID(uuid)
                .serializeInt(MathHelper.floor(this.locX * 32.0D))
                .serializeInt(MathHelper.floor(this.locY * 32.0D))
                .serializeInt(MathHelper.floor(this.locZ * 32.0D))
                .serializeByte(MathHelper.d(this.yaw * 256.0F / 360.0F))
                .serializeByte(MathHelper.d(this.pitch * 256.0F / 360.0F));
        try {
            dataWatcher.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
        try {
            packet.a((PacketDataSerializer) buildData());
            DATA_WATCHER_FIELD.set(packet, dataWatcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
