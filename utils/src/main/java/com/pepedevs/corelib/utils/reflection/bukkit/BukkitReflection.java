package com.pepedevs.corelib.utils.reflection.bukkit;

import com.pepedevs.corelib.utils.reflection.PacketConstant;
import com.pepedevs.corelib.utils.reflection.accessor.FieldAccessor;
import com.pepedevs.corelib.utils.reflection.general.MethodReflection;
import com.pepedevs.corelib.utils.reflection.resolver.FieldResolver;
import com.pepedevs.corelib.utils.reflection.resolver.MethodResolver;
import com.pepedevs.corelib.utils.reflection.resolver.ResolverQuery;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.CraftClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.NMSClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ClassWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.MethodWrapper;
import com.pepedevs.corelib.utils.version.Version;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/** Class for reflecting servers based on Bukkit. */
public class BukkitReflection {

    public static final ClassWrapper<?> MINECRAFT_SERVER;
    public static final ClassWrapper<?> PLAYER_LIST;
    public static final ClassWrapper<?> DIMENSION_MANAGER;
    public static final ClassWrapper<?> CRAFT_WORLD;
    public static final ClassWrapper<?> NMS_WORLD;
    public static final ClassWrapper<?> RESOURCE_KEY;
    public static final MethodWrapper<?> SEND_PACKET;
    public static final MethodWrapper MINECRAFT_SERVER_GET_SERVER;
    public static final MethodWrapper<?> MINECRAFT_SERVER_SET_MOTD;
    public static final MethodWrapper MINECRAFT_SERVER_GET_PLAYER_LIST;
    public static final MethodWrapper<?> SEND_PACKET_NEARBY;
    public static final FieldAccessor WORLD_BORDER_FIELD;
    public static final FieldAccessor WORLD_WORLD_SERVER;

    static {
        NMSClassResolver nmsClassResolver = new NMSClassResolver();
        MINECRAFT_SERVER =
                nmsClassResolver.resolveWrapper(
                        "MinecraftServer", "net.minecraft.server.MinecraftServer");
        PLAYER_LIST =
                nmsClassResolver.resolveWrapper(
                        "PlayerList", "net.minecraft.server.players.PlayerList");
        DIMENSION_MANAGER =
                nmsClassResolver.resolveWrapper(
                        "DimensionManager", "net.minecraft.world.level.dimension.DimensionManager");
        CRAFT_WORLD = new CraftClassResolver().resolveWrapper("CraftWorld");
        NMS_WORLD = nmsClassResolver.resolveWrapper("World", "net.minecraft.world.level.World");
        RESOURCE_KEY =
                nmsClassResolver.resolveWrapper(
                        "ResourceKey", "net.minecraft.resources.ResourceKey");
        SEND_PACKET =
                new MethodResolver(PlayerReflection.PLAYER_CONNECTION_CLASS.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with("sendPacket", PacketConstant.PACKET_CLASS.getClazz())
                                        .build());
        MINECRAFT_SERVER_GET_SERVER =
                new MethodResolver(MINECRAFT_SERVER.getClazz()).resolveWrapper("getServer");
        MINECRAFT_SERVER_SET_MOTD =
                new MethodResolver(MINECRAFT_SERVER.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder().with("setMotd", String.class).build());
        MINECRAFT_SERVER_GET_PLAYER_LIST =
                new MethodResolver(MINECRAFT_SERVER.getClazz()).resolveWrapper("getPlayerList");
        SEND_PACKET_NEARBY =
                new MethodResolver(PLAYER_LIST.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with(
                                                "sendPacketNearby",
                                                EntityReflection.ENTITY_HUMAN.getClazz(),
                                                double.class,
                                                double.class,
                                                double.class,
                                                double.class,
                                                int.class,
                                                PacketConstant.PACKET_CLASS.getClazz())
                                        .with(
                                                "sendPacketNearby",
                                                EntityReflection.ENTITY_HUMAN.getClazz(),
                                                double.class,
                                                double.class,
                                                double.class,
                                                double.class,
                                                DIMENSION_MANAGER.getClazz(),
                                                PacketConstant.PACKET_CLASS.getClazz())
                                        .with(
                                                "sendPacketNearby",
                                                EntityReflection.ENTITY_HUMAN.getClazz(),
                                                double.class,
                                                double.class,
                                                double.class,
                                                double.class,
                                                RESOURCE_KEY.getClazz(),
                                                PacketConstant.PACKET_CLASS.getClazz())
                                        .build());
        WORLD_BORDER_FIELD =
                new FieldResolver(CRAFT_WORLD.getClazz()).resolveAccessor("worldBorder");
        WORLD_WORLD_SERVER =
                new FieldResolver(BukkitReflection.CRAFT_WORLD.getClazz()).resolveAccessor("world");
    }

    /**
     * Gets the handle (the represented nms object by a craftbukkit object) of the provided
     * craftbukkit object.
     *
     * <p>
     *
     * @param object Object to get
     * @return Handle of the provided craftbukkit object
     */
    public static Object getHandle(Object object) {
        try {
            return object.getClass().getMethod("getHandle").invoke(object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new UnsupportedOperationException(
                    "cannot get the handle of the provided object!");
        }
    }

    /**
     * Sends the provided packet to the desired player.
     *
     * <p>
     *
     * @param player Player that will receive the packet
     * @param packet Packet instance to send
     */
    public static void sendPacket(Player player, Object packet) {
        Object connection = PlayerReflection.getPlayerConnection(player);
        SEND_PACKET.invokeSilent(connection, packet);
    }

    public static void sendPacketNearby(
            Player player, double x, double y, double z, double range, World world, Object packet) {
        Object handle = null;
        if (player != null) {
            handle = PlayerReflection.getHandle(player);
        }
        Object minecraft_server = MINECRAFT_SERVER_GET_SERVER.invoke(null);
        Object player_list = MINECRAFT_SERVER_GET_PLAYER_LIST.invoke(minecraft_server);

        Object world_server = WORLD_WORLD_SERVER.get(world);

        Object dimension;
        try {
            if (Version.SERVER_VERSION.isNewerEquals(Version.v1_16_R1)) {
                dimension =
                        MethodReflection.get(NMS_WORLD.getClazz(), "getDimensionKey")
                                .invoke(world_server);
            } else if (Version.SERVER_VERSION.isNewerEquals(Version.v1_13_R2)) {
                Object world_provider =
                        MethodReflection.get(NMS_WORLD.getClazz(), "getWorldProvider")
                                .invoke(world_server);
                dimension =
                        MethodReflection.get(world_provider.getClass(), "getDimensionManager")
                                .invoke(world_provider);
            } else {
                dimension = world_server.getClass().getField("dimension").getInt(world_server);
            }

            SEND_PACKET_NEARBY.invoke(player_list, handle, x, y, z, range, dimension, packet);
        } catch (NoSuchFieldException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the MOTD of the current running server.
     *
     * <p>
     *
     * @param motd New MOTD for this server
     */
    public static void setMotd(String motd) {
        Object server = MINECRAFT_SERVER_GET_SERVER.invokeSilent(null);
        MINECRAFT_SERVER_SET_MOTD.invokeSilent(server, motd);
    }

    /**
     * Clears the world border of the desired {@link World}.
     *
     * <p>
     *
     * @param world World with the desired border to clear
     */
    public static void clearBorder(World world) {
        world.getWorldBorder().reset();
        WORLD_BORDER_FIELD.set(world, null);
    }
}
