package com.pepedevs.corelib.nms;

import com.pepedevs.corelib.utils.version.Version;
import org.bukkit.entity.Player;

public class NMSProvider {

    private static final NMSBridge SERVER_NMS_ADAPTOR;

    static {
        SERVER_NMS_ADAPTOR = getNmsBridge(Version.SERVER_VERSION);
    }

    public static NMSBridge getNMSImpl() {
        return SERVER_NMS_ADAPTOR;
    }

    public static NMSPlayer getNMSPlayer(Player player) {
        return SERVER_NMS_ADAPTOR.getPlayer(player);
    }

    public static NMSBridge getNMSImpl(Version version) {
        return getNmsBridge(version);
    }

    private static NMSBridge getNmsBridge(Version version) {
        switch (version) {
            case v1_8_R3: {
                return com.pepedevs.corelib.nms.v1_8_R3.NMSImpl.INSTANCE;
            }
            default: {
                return null;
            }
        }
    }

}
