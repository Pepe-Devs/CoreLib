package com.pepedevs.radium.nms.packets;

import org.bukkit.util.Vector;

public interface WrappedPacketPlayOutOpenSignEditor extends WrappedPacket {

    Vector getLocation();

    void setLocation(Vector location);

}
