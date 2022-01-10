package com.pepedevs.corelib.nms;

import com.pepedevs.corelib.utils.version.Version;
import org.bukkit.entity.Player;

public class NMSBridge {

    private static final NMSProvider SERVER_NMS_ADAPTOR;
    private static final PacketProvider SERVER_PACKET_PROVIDER;

    static {
        SERVER_NMS_ADAPTOR = getNMSProvider(Version.SERVER_VERSION);
        SERVER_PACKET_PROVIDER = getPacketProvider(Version.SERVER_VERSION);
    }

    public static NMSProvider getNMSProvider() {
        return SERVER_NMS_ADAPTOR;
    }

    public static NMSProvider getNMSProvider(Version version) {
        switch (version) {
            case v1_8_R3: {
                return com.pepedevs.corelib.nms.v1_8_R3.NMSImpl.INSTANCE;
            }
            default: {
                return null;
            }
        }
    }

    public static PacketProvider getPacketProvider() {
        return SERVER_PACKET_PROVIDER;
    }

    public static PacketProvider getPacketProvider(Version version) {
        switch (version) {
            case v1_8_R3: {
                return com.pepedevs.corelib.nms.v1_8_R3.PacketProviderImpl.INSTANCE;
            }
            default: {
                return null;
            }
        }
    }

    public static NMSPlayer getNMSPlayer(Player player) {
        return SERVER_NMS_ADAPTOR.getPlayer(player);
    }

}
