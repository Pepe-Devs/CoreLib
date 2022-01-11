package com.pepedevs.corelib.nms;

import com.pepedevs.corelib.nms.packets.WrappedPacket;

public interface NMSPlayer {

    Object getEntityPlayer();

    Object getPlayerConnection();

    Object getNetworkManager();

    void sendPacket(WrappedPacket packet);

    void sendPacket(Object packet);

    int getNextContainerCounter();
}
