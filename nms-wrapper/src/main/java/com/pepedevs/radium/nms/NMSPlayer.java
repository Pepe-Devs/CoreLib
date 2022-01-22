package com.pepedevs.radium.nms;

import com.pepedevs.radium.nms.packets.WrappedPacket;

public interface NMSPlayer {

    Object getEntityPlayer();

    Object getPlayerConnection();

    Object getNetworkManager();

    void sendPacket(WrappedPacket packet);

    void sendPacket(Object packet);

    int getNextContainerCounter();
}
