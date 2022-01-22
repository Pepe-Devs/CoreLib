package com.pepedevs.radium.nms;

import com.pepedevs.radium.utils.version.Version;
import org.bukkit.entity.Player;

public class NMSBridge {

    private static final NMSProvider SERVER_NMS_ADAPTOR;
    private static final PacketProvider SERVER_PACKET_PROVIDER;

    static {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.pepedevs.radium.nms." + Version.SERVER_VERSION.name() + ".NMSProviderImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        NMSProvider nmsProvider = null;
        try {
            nmsProvider = (NMSProvider) clazz.getDeclaredField("INSTANCE").get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        Class<?> clazz2 = null;
        try {
            clazz2 = Class.forName("com.pepedevs.radium.nms." + Version.SERVER_VERSION.name() + ".PacketProviderImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        PacketProvider packetProvider = null;
        try {
            packetProvider = (PacketProvider) clazz2.getDeclaredField("INSTANCE").get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        SERVER_NMS_ADAPTOR = nmsProvider;
        SERVER_PACKET_PROVIDER = packetProvider;
    }

    public static NMSProvider getNMSProvider() {
        return SERVER_NMS_ADAPTOR;
    }

    public static PacketProvider getPacketProvider() {
        return SERVER_PACKET_PROVIDER;
    }

    public static NMSPlayer getNMSPlayer(Player player) {
        return SERVER_NMS_ADAPTOR.getPlayer(player);
    }

}
