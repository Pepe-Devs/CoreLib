package com.pepedevs.corelib.utils.reflection.bukkit;

import com.pepedevs.corelib.utils.reflection.accessor.FieldAccessor;
import com.pepedevs.corelib.utils.reflection.resolver.FieldResolver;
import com.pepedevs.corelib.utils.reflection.resolver.MethodResolver;
import com.pepedevs.corelib.utils.reflection.resolver.ResolverQuery;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.CraftClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.NMSClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ClassWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.MethodWrapper;
import io.netty.channel.Channel;
import org.bukkit.entity.Player;

/** Class for reflecting {@link Player} */
public class PlayerReflection {

    public static final ClassWrapper<?> CRAFT_PLAYER_CLASS;
    public static final ClassWrapper<?> ENTITY_PLAYER_CLASS;
    public static final ClassWrapper<?> PLAYER_CONNECTION_CLASS;
    public static final ClassWrapper<?> NETWORK_MANAGER_CLASS;
    public static final FieldAccessor PLAYER_CONNECTION_FIELD;
    public static final FieldAccessor NETWORK_MANAGER_FIELD;
    public static final FieldAccessor CHANNEL_FIELD;
    public static final MethodWrapper CRAFT_PLAYER_GET_HANDLE;

    static {
        NMSClassResolver nmsClassResolver = new NMSClassResolver();
        CraftClassResolver craftClassResolver = new CraftClassResolver();
        CRAFT_PLAYER_CLASS = craftClassResolver.resolveWrapper("entity.CraftPlayer");
        ENTITY_PLAYER_CLASS =
                nmsClassResolver.resolveWrapper(
                        "net.minecraft.server.level.EntityPlayer", "EntityPlayer");
        PLAYER_CONNECTION_CLASS =
                nmsClassResolver.resolveWrapper(
                        "net.minecraft.server.network.PlayerConnection", "PlayerConnection");
        NETWORK_MANAGER_CLASS =
                nmsClassResolver.resolveWrapper(
                        "net.minecraft.network.NetworkManager", "NetworkManager");
        PLAYER_CONNECTION_FIELD =
                new FieldResolver(ENTITY_PLAYER_CLASS.getClazz())
                        .resolveAccessor(
                                ResolverQuery.builder()
                                        .with("b", PLAYER_CONNECTION_CLASS.getClazz())
                                        .with(
                                                "playerConnection",
                                                PLAYER_CONNECTION_CLASS.getClazz())
                                        .build());
        NETWORK_MANAGER_FIELD =
                new FieldResolver(PLAYER_CONNECTION_CLASS.getClazz())
                        .resolveAccessor(
                                ResolverQuery.builder()
                                        .with("a", NETWORK_MANAGER_CLASS.getClazz())
                                        .with("networkManager", NETWORK_MANAGER_CLASS.getClazz())
                                        .build());
        CHANNEL_FIELD =
                new FieldResolver(NETWORK_MANAGER_CLASS.getClazz())
                        .resolveAccessor(
                                ResolverQuery.builder()
                                        .with("i", Channel.class)
                                        .with("k", Channel.class)
                                        .with("channel", Channel.class)
                                        .build());
        CRAFT_PLAYER_GET_HANDLE =
                new MethodResolver(CRAFT_PLAYER_CLASS.getClazz()).resolveWrapper("getHandle");
    }

    /**
     * Gets the handle (the represented nms player by the craftbukkit player) of the provided {@code
     * player}.
     *
     * <p>
     *
     * @param player Player to get
     * @return Handle of the provided craftbukkit player
     */
    public static Object getHandle(Player player) {
        return CRAFT_PLAYER_GET_HANDLE.invokeSilent(CRAFT_PLAYER_CLASS.getClazz().cast(player));
    }

    /**
     * Gets player's connection.
     *
     * <p>
     *
     * @param player Player to get
     * @return Player's connection
     */
    public static Object getPlayerConnection(Player player) {
        return PLAYER_CONNECTION_FIELD.get(PlayerReflection.getHandle(player));
    }

    /**
     * Gets player's network manager.
     *
     * <p>
     *
     * @param player Player to get
     * @return Player's network manager
     */
    public static Object getNetworkManager(Player player) {
        return NETWORK_MANAGER_FIELD.get(PlayerReflection.getPlayerConnection(player));
    }

    /**
     * Gets player's channel.
     *
     * <p>
     *
     * @param player Player to get
     * @return Player's channel
     */
    public static Channel getChannel(Player player) {
        return CHANNEL_FIELD.get(PlayerReflection.getNetworkManager(player));
    }
}
