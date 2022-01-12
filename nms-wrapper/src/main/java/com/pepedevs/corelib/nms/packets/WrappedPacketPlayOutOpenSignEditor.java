package com.pepedevs.corelib.nms.packets;

import org.bukkit.util.Vector;

public interface WrappedPacketPlayOutOpenSignEditor extends WrappedPacket {

    Vector getLocation();

    void setLocation(Vector location);

}
