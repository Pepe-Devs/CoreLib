package com.pepedevs.corelib.nms.packets;

import org.bukkit.Location;

public interface WrappedPacketPlayOutOpenSignEditor extends WrappedPacket {
    Location getLocation();

    void setLocation(Location location);
}
