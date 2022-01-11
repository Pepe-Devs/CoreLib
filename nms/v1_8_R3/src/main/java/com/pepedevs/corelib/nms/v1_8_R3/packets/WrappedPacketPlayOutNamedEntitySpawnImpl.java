package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutNamedEntitySpawn;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.UUID;

public class WrappedPacketPlayOutNamedEntitySpawnImpl extends PacketPlayOutNamedEntitySpawn implements WrappedPacketPlayOutNamedEntitySpawn {

    public WrappedPacketPlayOutNamedEntitySpawnImpl(int entityId, UUID uuid, Location location, ItemStack mainHandItem, Object dataWatcher) {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(entityId)
                .serializeUUID(uuid)
                .serializeInt(MathHelper.floor(location.getX() * 32.0D))
                .serializeInt(MathHelper.floor(location.getY() * 32.0D))
                .serializeInt(MathHelper.floor(location.getZ() * 32.0D))
                .serializeByte(MathHelper.d(location.getYaw() * 256.0F / 360.0F))
                .serializeByte(MathHelper.d(location.getPitch() * 256.0F / 360.0F))
                .serializeShort(mainHandItem == null ? 0 : Item.getId(CraftItemStack.asNMSCopy(mainHandItem).getItem()));
        try {
            ((DataWatcher) dataWatcher).a((PacketDataSerializer) serializer);
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WrappedPacketPlayOutNamedEntitySpawnImpl(int entityId, UUID uuid, Location location, Object dataWatcher) {
        this(entityId, uuid, location, null, dataWatcher);
    }

    public WrappedPacketPlayOutNamedEntitySpawnImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
