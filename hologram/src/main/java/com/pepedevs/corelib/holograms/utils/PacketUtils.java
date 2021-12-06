package com.pepedevs.corelib.holograms.utils;

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

public final class PacketUtils {

    // UTILITY
    private static final ClassWrapper<?> ITEM_STACK_CLASS;
    private static final MethodWrapper CRAFT_ITEM_NMS_COPY_METHOD;
    // DATA WATCHER
    private static final ClassWrapper<?> DATA_WATCHER_CLASS;
    private static final ConstructorWrapper<?> DATA_WATCHER_CONSTRUCTOR;
    private static final MethodWrapper DATA_WATCHER_A_METHOD;
    // MATH HELPER
    private static final ClassWrapper<?> MATH_HELPER_CLASS;
    private static final MethodWrapper MATH_HELPER_FLOOR_METHOD;
    private static final MethodWrapper MATH_HELPER_D_METHOD;
    // PACKETS
    private static final ConstructorWrapper<?> PACKET_SPAWN_ENTITY_CONSTRUCTOR;
    private static final ConstructorWrapper<?> PACKET_SPAWN_ENTITY_LIVING_CONSTRUCTOR;
    private static final ConstructorWrapper<?> PACKET_ENTITY_METADATA_CONSTRUCTOR;
    private static final ConstructorWrapper<?> PACKET_ENTITY_TELEPORT_CONSTRUCTOR;
    private static final ConstructorWrapper<?> PACKET_ATTACH_ENTITY_CONSTRUCTOR;
    private static final ConstructorWrapper<?> PACKET_ENTITY_EQUIPMENT_CONSTRUCTOR;
    private static final ConstructorWrapper<?> PACKET_ENTITY_DESTROY_CONSTRUCTOR;

    private static final FieldAccessor ENTITY_COUNTER_FIELD;

    private PacketUtils() {}

    public static int getFreeEntityId() {
        int entityCount = ENTITY_COUNTER_FIELD.get(null);
        ENTITY_COUNTER_FIELD.set(null, entityCount + 1);
        return entityCount;
    }

    public static void showFakeEntity(
            Player player, Location location, EntityType entityType, int entityId) {
        Validate.notNull(player);
        Validate.notNull(location);

        PacketUtils.showFakeEntity(player, location, entityType.getTypeId(), entityId);
    }

    public static void showFakeEntity(
            Player player, Location location, int entityTypeId, int entityId) {
        Validate.notNull(player);
        Validate.notNull(location);

        Object spawn = PACKET_SPAWN_ENTITY_CONSTRUCTOR.newInstance();
        if (spawn == null) return;
        try {
            FieldReflection.setValue(spawn, "a", entityId);
            FieldReflection.setValue(
                    spawn, "b", MATH_HELPER_FLOOR_METHOD.invoke(null, location.getX() * 32.0D));
            FieldReflection.setValue(
                    spawn, "c", MATH_HELPER_FLOOR_METHOD.invoke(null, location.getY() * 32.0D));
            FieldReflection.setValue(
                    spawn, "d", MATH_HELPER_FLOOR_METHOD.invoke(null, location.getZ() * 32.0D));
            FieldReflection.setValue(
                    spawn,
                    "h",
                    MATH_HELPER_D_METHOD.invoke(null, location.getPitch() * 256.0F / 360.0F));
            FieldReflection.setValue(
                    spawn,
                    "i",
                    MATH_HELPER_D_METHOD.invoke(null, location.getYaw() * 256.0F / 360.0F));
            FieldReflection.setValue(spawn, "j", entityTypeId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BukkitReflection.sendPacket(player, spawn);
    }

    public static void showFakeEntityLiving(
            Player player, Location location, EntityType entityType, int entityId) {
        Validate.notNull(player);
        Validate.notNull(location);

        Object dataWatcher =
                DATA_WATCHER_CONSTRUCTOR.newInstance(
                        EntityReflection.NMS_ENTITY_CLASS.getClazz().cast(null));
        DATA_WATCHER_A_METHOD.invoke(dataWatcher, 15, (byte) 0);
        PacketUtils.showFakeEntityLiving(
                player, location, entityType.getTypeId(), entityId, dataWatcher);
    }

    public static void showFakeEntityLiving(
            Player player, Location location, int entityTypeId, int entityId, Object dataWatcher) {
        Validate.notNull(player);
        Validate.notNull(location);
        if (dataWatcher == null
                || !DATA_WATCHER_CLASS.getClazz().isAssignableFrom(dataWatcher.getClass())) return;

        Object spawn = PACKET_SPAWN_ENTITY_LIVING_CONSTRUCTOR.newInstance();
        if (spawn == null) return;
        try {
            FieldReflection.setValue(spawn, "a", entityId);
            FieldReflection.setValue(spawn, "b", entityTypeId);
            FieldReflection.setValue(
                    spawn, "c", MATH_HELPER_FLOOR_METHOD.invoke(null, location.getX() * 32.0D));
            FieldReflection.setValue(
                    spawn, "d", MATH_HELPER_FLOOR_METHOD.invoke(null, location.getY() * 32.0D));
            FieldReflection.setValue(
                    spawn, "e", MATH_HELPER_FLOOR_METHOD.invoke(null, location.getZ() * 32.0D));
            FieldReflection.setValue(
                    spawn, "i", (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));
            FieldReflection.setValue(
                    spawn, "j", (byte) ((int) (location.getPitch() * 256.0F / 360.0F)));
            FieldReflection.setValue(
                    spawn, "k", (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));
            FieldReflection.setValue(spawn, "l", dataWatcher);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BukkitReflection.sendPacket(player, spawn);
    }

    public static void showFakeEntityArmorStand(
            Player player,
            Location location,
            int entityId,
            boolean invisible,
            boolean small,
            boolean clickable) {
        Object dataWatcher =
                DATA_WATCHER_CONSTRUCTOR.newInstance(
                        EntityReflection.NMS_ENTITY_CLASS.getClazz().cast(null));
        DATA_WATCHER_A_METHOD.invoke(dataWatcher, 0, (byte) (invisible ? 0x20 : 0x00)); // Invisible
        byte data = 0x08;
        if (small) data += 0x01;
        if (!clickable) data += 0x10;
        DATA_WATCHER_A_METHOD.invoke(dataWatcher, 10, data);
        PacketUtils.showFakeEntityLiving(player, location, 30, entityId, dataWatcher);
    }

    public static void showFakeEntityItem(
            Player player, Location location, ItemStack itemStack, int entityId) {
        Validate.notNull(player);
        Validate.notNull(location);
        Validate.notNull(itemStack);

        Object nmsItemStack = CRAFT_ITEM_NMS_COPY_METHOD.invoke(null, itemStack);
        Object dataWatcher =
                DATA_WATCHER_CONSTRUCTOR.newInstance(
                        EntityReflection.NMS_ENTITY_CLASS.getClazz().cast(null));
        if (nmsItemStack == null || dataWatcher == null) return;
        DATA_WATCHER_A_METHOD.invoke(dataWatcher, 10, nmsItemStack);
        PacketUtils.showFakeEntity(player, location, 2, entityId);
        BukkitReflection.sendPacket(
                player,
                PACKET_ENTITY_METADATA_CONSTRUCTOR.newInstance(entityId, dataWatcher, true));
        PacketUtils.teleportFakeEntity(player, location, entityId);
    }

    public static void updateFakeEntityCustomName(Player player, String name, int entityId) {
        Validate.notNull(player);
        Validate.notNull(name);

        Object dataWatcher =
                DATA_WATCHER_CONSTRUCTOR.newInstance(
                        EntityReflection.NMS_ENTITY_CLASS.getClazz().cast(null));
        DATA_WATCHER_A_METHOD.invoke(dataWatcher, 2, name); // Custom Name
        DATA_WATCHER_A_METHOD.invoke(
                dataWatcher,
                3,
                (byte) (ChatColor.stripColor(name).isEmpty() ? 0 : 1)); // Custom Name Visible
        BukkitReflection.sendPacket(
                player,
                PACKET_ENTITY_METADATA_CONSTRUCTOR.newInstance(entityId, dataWatcher, true));
    }

    public static void teleportFakeEntity(Player player, Location location, int entityId) {
        Validate.notNull(player);
        Validate.notNull(location);

        Object teleport = PACKET_ENTITY_TELEPORT_CONSTRUCTOR.newInstance();
        if (teleport == null) return;
        try {
            FieldReflection.setValue(teleport, "a", entityId);
            FieldReflection.setValue(
                    teleport, "b", MATH_HELPER_FLOOR_METHOD.invoke(null, location.getX() * 32.0D));
            FieldReflection.setValue(
                    teleport, "c", MATH_HELPER_FLOOR_METHOD.invoke(null, location.getY() * 32.0D));
            FieldReflection.setValue(
                    teleport, "d", MATH_HELPER_FLOOR_METHOD.invoke(null, location.getZ() * 32.0D));
            FieldReflection.setValue(
                    teleport, "e", (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));
            FieldReflection.setValue(
                    teleport, "f", (byte) ((int) (location.getPitch() * 256.0F / 360.0F)));
            FieldReflection.setValue(teleport, "g", true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BukkitReflection.sendPacket(player, teleport);
    }

    public static void helmetFakeEntity(Player player, ItemStack itemStack, int entityId) {
        Validate.notNull(player);
        Validate.notNull(itemStack);

        Object nmsItemStack = CRAFT_ITEM_NMS_COPY_METHOD.invoke(null, itemStack);
        if (nmsItemStack == null) return;
        Object packet = PACKET_ENTITY_EQUIPMENT_CONSTRUCTOR.newInstance(entityId, 4, nmsItemStack);
        if (packet == null) return;
        BukkitReflection.sendPacket(player, packet);
    }

    public static void attachFakeEntity(Player player, int vehicleId, int entityId) {
        Validate.notNull(player);

        Object packet = PACKET_ATTACH_ENTITY_CONSTRUCTOR.newInstance();
        if (packet == null) return;
        try {
            FieldReflection.setValue(packet, "a", 0);
            FieldReflection.setValue(packet, "b", entityId);
            FieldReflection.setValue(packet, "c", vehicleId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        BukkitReflection.sendPacket(player, packet);
    }

    public static void hideFakeEntities(Player player, int... entityIds) {
        Validate.notNull(player);
        BukkitReflection.sendPacket(
                player, PACKET_ENTITY_DESTROY_CONSTRUCTOR.newInstance((Object) entityIds));
    }

    static {
        NMSClassResolver nmsClassResolver = new NMSClassResolver();
        CraftClassResolver craftClassResolver = new CraftClassResolver();
        ClassWrapper<?> craftItemStack =
                craftClassResolver.resolveWrapper("inventory.CraftItemStack");
        ITEM_STACK_CLASS =
                nmsClassResolver.resolveWrapper("ItemStack", "net.minecraft.world.item.ItemStack");
        CRAFT_ITEM_NMS_COPY_METHOD =
                new MethodResolver(craftItemStack.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder().with("asNMSCopy", ItemStack.class).build());
        // DATA WATCHER
        DATA_WATCHER_CLASS = nmsClassResolver.resolveWrapper("DataWatcher");
        DATA_WATCHER_CONSTRUCTOR =
                new ConstructorResolver(DATA_WATCHER_CLASS.getClazz())
                        .resolveWrapper(new Class[] {EntityReflection.NMS_ENTITY_CLASS.getClazz()});
        DATA_WATCHER_A_METHOD =
                new MethodResolver(DATA_WATCHER_CLASS.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder().with("a", int.class, Object.class).build());
        // MATH HELPER
        MATH_HELPER_CLASS = nmsClassResolver.resolveWrapper("MathHelper");
        MATH_HELPER_FLOOR_METHOD =
                new MethodResolver(MATH_HELPER_CLASS.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder().with("floor", double.class).build());
        MATH_HELPER_D_METHOD =
                new MethodResolver(MATH_HELPER_CLASS.getClazz())
                        .resolveWrapper(ResolverQuery.builder().with("d", float.class).build());
        // PACKETS
        PACKET_SPAWN_ENTITY_CONSTRUCTOR =
                new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_SPAWN_ENTITY.getClazz())
                        .resolveWrapper(new Class[0]);
        PACKET_SPAWN_ENTITY_LIVING_CONSTRUCTOR =
                new ConstructorResolver(
                                PacketConstant.PACKET_PLAY_OUT_SPAWN_ENTITY_LIVING.getClazz())
                        .resolveWrapper(new Class[0]);
        PACKET_ENTITY_METADATA_CONSTRUCTOR =
                new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_ENTITY_METADATA.getClazz())
                        .resolveWrapper(
                                new Class[] {
                                    int.class, DATA_WATCHER_CLASS.getClazz(), boolean.class
                                });
        PACKET_ENTITY_TELEPORT_CONSTRUCTOR =
                new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_ENTITY_TELEPORT.getClazz())
                        .resolveWrapper(new Class[0]);
        PACKET_ATTACH_ENTITY_CONSTRUCTOR =
                new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_ATTACH_ENTITY.getClazz())
                        .resolveWrapper(new Class[0]);
        PACKET_ENTITY_EQUIPMENT_CONSTRUCTOR =
                new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_ENTITY_EQUIPMENT.getClazz())
                        .resolveWrapper(
                                new Class[] {int.class, int.class, ITEM_STACK_CLASS.getClazz()});
        PACKET_ENTITY_DESTROY_CONSTRUCTOR =
                new ConstructorResolver(PacketConstant.PACKET_PLAY_OUT_ENTITY_DESTROY.getClazz())
                        .resolveWrapper(new Class[] {int[].class});

        ENTITY_COUNTER_FIELD =
                new FieldResolver(EntityReflection.NMS_ENTITY_CLASS.getClazz())
                        .resolveAccessor("entityCount");
    }
}
