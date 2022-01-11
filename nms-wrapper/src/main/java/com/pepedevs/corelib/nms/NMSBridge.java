package com.pepedevs.corelib.nms;

import com.pepedevs.corelib.utils.version.Version;
import org.bukkit.entity.Player;

import java.util.EnumMap;

public class NMSBridge {

    private static final EnumMap<Version, NMSProvider> NMS_PROVIDER_MAP = new EnumMap<>(Version.class);
    private static final EnumMap<Version, PacketProvider> PACKET_PROVIDER_MAP = new EnumMap<>(Version.class);

    private static final NMSProvider SERVER_NMS_ADAPTOR;
    private static final PacketProvider SERVER_PACKET_PROVIDER;

    static {
        for (Version value : Version.values()) {
            Class<?> clazz;
            try {
                clazz = Class.forName("com.pepedevs.corelib.nms." + value.name() + ".NMSProviderImpl");
            } catch (ClassNotFoundException e) {
                continue;
            }

            try {
                NMS_PROVIDER_MAP.put(value, (NMSProvider) clazz.getDeclaredField("INSTANCE").get(null));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        for (Version value : Version.values()) {
            Class<?> clazz;
            try {
                clazz = Class.forName("com.pepedevs.corelib.nms." + value.name() + ".PacketProviderImpl");
            } catch (ClassNotFoundException e) {
                continue;
            }

            try {
                PACKET_PROVIDER_MAP.put(value, (PacketProvider) clazz.getDeclaredField("INSTANCE").get(null));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        SERVER_NMS_ADAPTOR = getNMSProvider(Version.SERVER_VERSION);
        SERVER_PACKET_PROVIDER = getPacketProvider(Version.SERVER_VERSION);
    }

    public static NMSProvider getNMSProvider() {
        return SERVER_NMS_ADAPTOR;
    }

    public static NMSProvider getNMSProvider(Version version) {
        return NMS_PROVIDER_MAP.get(version);
    }

    public static PacketProvider getPacketProvider() {
        return SERVER_PACKET_PROVIDER;
    }

    public static PacketProvider getPacketProvider(Version version) {
        return PACKET_PROVIDER_MAP.get(version);
    }

    public static NMSPlayer getNMSPlayer(Player player) {
        return SERVER_NMS_ADAPTOR.getPlayer(player);
    }

}
