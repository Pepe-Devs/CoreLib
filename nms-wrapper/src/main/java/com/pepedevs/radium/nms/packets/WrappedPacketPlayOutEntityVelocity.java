package com.pepedevs.radium.nms.packets;

import org.bukkit.util.Vector;

public interface WrappedPacketPlayOutEntityVelocity extends WrappedPacket {
    int getEntityID();

    Vector getVelocity();

    void setEntityID(int entityID);

    void setVelocity(Vector velocity);
}
