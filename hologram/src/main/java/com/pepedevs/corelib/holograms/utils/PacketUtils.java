package com.pepedevs.corelib.holograms.utils;

import com.pepedevs.corelib.nms.ItemSlot;
import com.pepedevs.corelib.nms.NMSBridge;
import com.pepedevs.corelib.nms.objects.WrappedDataWatcher;
import com.pepedevs.corelib.nms.packets.*;
import com.pepedevs.corelib.utils.reflection.PacketConstant;
import com.pepedevs.corelib.utils.reflection.accessor.FieldAccessor;
import com.pepedevs.corelib.utils.reflection.bukkit.BukkitReflection;
import com.pepedevs.corelib.utils.reflection.bukkit.EntityReflection;
import com.pepedevs.corelib.utils.reflection.general.FieldReflection;
import com.pepedevs.corelib.utils.reflection.resolver.ConstructorResolver;
import com.pepedevs.corelib.utils.reflection.resolver.FieldResolver;
import com.pepedevs.corelib.utils.reflection.resolver.MethodResolver;
import com.pepedevs.corelib.utils.reflection.resolver.ResolverQuery;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.CraftClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.NMSClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ClassWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ConstructorWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.MethodWrapper;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public final class PacketUtils {

    private PacketUtils() {}

    public static int getFreeEntityId() {
        return EntityReflection.getFreeEntityId();
    }

    public static void showFakeEntity(Player player, Location location, EntityType entityType, int entityId) {
        Validate.notNull(player);
        Validate.notNull(location);

        WrappedPacketPlayOutSpawnEntity packet = NMSBridge.getPacketProvider().getNewSpawnEntityPacket(entityId, UUID.randomUUID(), location, entityType);
        NMSBridge.getNMSPlayer(player).sendPacket(packet);
    }

    public static void showFakeEntityLiving(Player player, Location location, EntityType entityType, int entityId) {
        Validate.notNull(player);
        Validate.notNull(location);

        WrappedDataWatcher dataWatcher = NMSBridge.getNMSProvider().getDataWatcher();
        dataWatcher.add(15, (byte) 0);
        PacketUtils.showFakeEntityLiving(player, location, entityType, entityId, dataWatcher);
    }

    public static void showFakeEntityLiving(Player player, Location location, EntityType entityType, int entityId, WrappedDataWatcher dataWatcher) {
        Validate.notNull(player);
        Validate.notNull(location);

        WrappedPacketPlayOutSpawnEntityLiving packet = NMSBridge.getPacketProvider().getNewSpawnEntityLivingPacket(entityId, UUID.randomUUID(), entityType, location, location.getYaw(), dataWatcher);
        NMSBridge.getNMSPlayer(player).sendPacket(packet);
    }

    public static void showFakeEntityArmorStand(Player player, Location location, int entityId, boolean invisible, boolean small, boolean clickable) {
        WrappedDataWatcher dataWatcher = NMSBridge.getNMSProvider().getDataWatcher();
        dataWatcher.add(0, (byte) (invisible ? 0x20 : 0x00));
        byte data = 0x08;
        if (small) data += 0x01;
        if (!clickable) data += 0x10;
        dataWatcher.add(10, data);
        PacketUtils.showFakeEntityLiving(player, location, EntityType.ARMOR_STAND, entityId, dataWatcher);
    }

    public static void showFakeEntityItem(Player player, Location location, ItemStack itemStack, int entityId) {
        Validate.notNull(player);
        Validate.notNull(location);
        Validate.notNull(itemStack);

        PacketUtils.showFakeEntity(player, location, EntityType.DROPPED_ITEM, entityId);

        Object nmsItemStack = NMSBridge.getNMSProvider().craftItemStackAsNmsCopy(itemStack);
        WrappedDataWatcher.WrappedWatchableObject watchableObject = NMSBridge.getNMSProvider().getWatchableObject(5, 10, nmsItemStack);
        WrappedPacketPlayOutEntityMetadata packet = NMSBridge.getPacketProvider().getNewEntityMetadataPacket(entityId, Collections.singletonList(watchableObject));
        NMSBridge.getNMSPlayer(player).sendPacket(packet);

        PacketUtils.teleportFakeEntity(player, location, entityId);
    }

    public static void updateFakeEntityCustomName(Player player, String name, int entityId) {
        Validate.notNull(player);
        Validate.notNull(name);

        WrappedDataWatcher.WrappedWatchableObject customName = NMSBridge.getNMSProvider().getWatchableObject(4, 2, name);
        WrappedDataWatcher.WrappedWatchableObject customNameVisible = NMSBridge.getNMSProvider().getWatchableObject(0, 3, (byte) (ChatColor.stripColor(name).isEmpty() ? 0 : 1));
        WrappedPacketPlayOutEntityMetadata packet = NMSBridge.getPacketProvider().getNewEntityMetadataPacket(entityId, Arrays.asList(customName, customNameVisible));
        NMSBridge.getNMSPlayer(player).sendPacket(packet);
    }

    public static void teleportFakeEntity(Player player, Location location, int entityId) {
        Validate.notNull(player);
        Validate.notNull(location);

        WrappedPacketPlayOutEntityTeleport packet = NMSBridge.getPacketProvider().getNewEntityTeleportPacket(entityId, location);
        NMSBridge.getNMSPlayer(player).sendPacket(packet);
    }

    public static void helmetFakeEntity(Player player, ItemStack itemStack, int entityId) {
        Validate.notNull(player);
        Validate.notNull(itemStack);

        WrappedPacketPlayOutEntityEquipment packet = NMSBridge.getPacketProvider().getNewEntityEquipmentPacket(entityId, ItemSlot.HEAD, itemStack);
        NMSBridge.getNMSPlayer(player).sendPacket(packet);
    }

    public static void attachFakeEntity(Player player, int vehicleId, int entityId) {
        Validate.notNull(player);

        WrappedPacketPlayOutAttachEntity packet = NMSBridge.getPacketProvider().getNewAttachEntityPacket(WrappedPacketPlayOutAttachEntity.AttachmentType.MOUNT, entityId, vehicleId);
        NMSBridge.getNMSPlayer(player).sendPacket(packet);
    }

    public static void hideFakeEntities(Player player, int... entityIds) {
        Validate.notNull(player);

        WrappedPacketPlayOutEntityDestroy packet = NMSBridge.getPacketProvider().getNewEntityDestroyPacket(entityIds);
        NMSBridge.getNMSPlayer(player).sendPacket(packet);
    }

}
