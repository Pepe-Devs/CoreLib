package com.pepedevs.radium.nms;

public interface NMSPlayer {

    Object getEntityPlayer();

    Object getPlayerConnection();

    Object getNetworkManager();

    void sendPacket(Object packet);

    int getNextContainerCounter();
}
