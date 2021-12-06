package com.pepedevs.corelib.holograms.listener;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pepedevs.corelib.holograms.HologramManager;
import com.pepedevs.corelib.holograms.action.ClickType;
import com.pepedevs.corelib.holograms.object.Hologram;
import com.pepedevs.corelib.packets.PacketAdapter;
import com.pepedevs.corelib.packets.PacketEvent;
import com.pepedevs.corelib.utils.reflection.accessor.FieldAccessor;
import com.pepedevs.corelib.utils.reflection.resolver.ConstructorResolver;
import com.pepedevs.corelib.utils.reflection.resolver.FieldResolver;
import com.pepedevs.corelib.utils.reflection.resolver.MethodResolver;
import com.pepedevs.corelib.utils.reflection.resolver.ResolverQuery;
import com.pepedevs.corelib.utils.reflection.resolver.minecraft.NMSClassResolver;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ClassWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.ConstructorWrapper;
import com.pepedevs.corelib.utils.reflection.resolver.wrapper.MethodWrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HologramPacketListener extends PacketAdapter {

    protected static final ClassWrapper<?> ENTITY_USE_PACKET_CLASS;
    protected static final FieldAccessor ENTITY_USE_PACKET_ID_FIELD;
    private static final ClassWrapper<?> PACKET_DATA_SERIALIZER_CLASS;
    private static final ConstructorWrapper<?> PACKET_DATA_SERIALIZER_CONSTRUCTOR;
    private static final MethodWrapper ENTITY_USE_PACKET_A_METHOD;
    private static final MethodWrapper PACKET_DATA_SERIALIZER_READ_INT_METHOD;

    static {
        NMSClassResolver nmsClassResolver = new NMSClassResolver();
        ENTITY_USE_PACKET_CLASS =
                nmsClassResolver.resolveWrapper(
                        "PacketPlayInUseEntity",
                        "net.minecraft.network.protocol.game.PacketPlayInUseEntity");
        PACKET_DATA_SERIALIZER_CLASS =
                nmsClassResolver.resolveWrapper(
                        "PacketDataSerializer", "net.minecraft.network.PacketDataSerializer");
        ENTITY_USE_PACKET_ID_FIELD =
                new FieldResolver(ENTITY_USE_PACKET_CLASS.getClazz()).resolveAccessor("a");
        PACKET_DATA_SERIALIZER_CONSTRUCTOR =
                new ConstructorResolver(PACKET_DATA_SERIALIZER_CLASS.getClazz())
                        .resolveWrapper(new Class[] {ByteBuf.class});
        PACKET_DATA_SERIALIZER_READ_INT_METHOD =
                new MethodResolver(PACKET_DATA_SERIALIZER_CLASS.getClazz()).resolveWrapper("e");
        ENTITY_USE_PACKET_A_METHOD =
                new MethodResolver(ENTITY_USE_PACKET_CLASS.getClazz())
                        .resolveWrapper(
                                ResolverQuery.builder()
                                        .with("b", PACKET_DATA_SERIALIZER_CLASS.getClazz())
                                        .build());
    }

    private static final Cache<UUID, Integer> COOLDOWN =
            CacheBuilder.newBuilder().expireAfterWrite(20, TimeUnit.MILLISECONDS).build();

    @Override
    public void onReceiving(PacketEvent event) {
        if (event.getPacket() != null
                && event.getPacket()
                        .getClass()
                        .isAssignableFrom(ENTITY_USE_PACKET_CLASS.getClazz())) {
            int entityId = ENTITY_USE_PACKET_ID_FIELD.get(event.getPacket());
            if (COOLDOWN.asMap().containsKey(event.getPlayer().getUniqueId())
                    && COOLDOWN.asMap().getOrDefault(event.getPlayer().getUniqueId(), -1)
                            == entityId) return;

            ClickType clickType = this.getClickType(event.getPacket(), event.getPlayer());
            for (Hologram hologram : HologramManager.get().getHolograms().values()) {
                if (!hologram.getLocation()
                        .getWorld()
                        .getName()
                        .equals(event.getPlayer().getLocation().getWorld().getName())) continue;
                if (hologram.handleClick(event.getPlayer(), entityId, clickType)) {
                    COOLDOWN.put(event.getPlayer().getUniqueId(), entityId);
                    break;
                }
            }
        }
    }

    protected int getEntityUseActionOrdinal(Object packet) {
        if (packet != null
                && packet.getClass().isAssignableFrom(ENTITY_USE_PACKET_CLASS.getClazz())) {
            Object packetDataSerializer =
                    PACKET_DATA_SERIALIZER_CONSTRUCTOR.newInstance(Unpooled.buffer());
            ENTITY_USE_PACKET_A_METHOD.invoke(packet, packetDataSerializer);
            //            PACKET_DATA_SERIALIZER_READ_INT_METHOD.invoke(packetDataSerializer);
            return (int) PACKET_DATA_SERIALIZER_READ_INT_METHOD.invoke(packetDataSerializer);
        }
        return -1;
    }

    private ClickType getClickType(Object packet, Player player) {
        int ordinal = this.getEntityUseActionOrdinal(packet);
        if (ordinal == 1) {
            return player.isSneaking() ? ClickType.SHIFT_LEFT_CLICK : ClickType.LEFT_CLICK;
        } else {
            return player.isSneaking() ? ClickType.SHIFT_RIGHT_CLICK : ClickType.RIGHT_CLICK;
        }
    }
}
