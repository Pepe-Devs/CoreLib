package com.pepedevs.corelib.nms;

public interface NMSPlayer {


    Object getEntityPlayer();

    Object getPlayerConnection();

    Object getNetworkManager();

    void sendPacket(Object packet);

    int getNextContainerCounter();
}
