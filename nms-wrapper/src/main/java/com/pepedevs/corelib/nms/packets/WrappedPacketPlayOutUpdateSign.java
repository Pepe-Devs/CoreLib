package com.pepedevs.corelib.nms.packets;

import org.bukkit.Location;

public interface WrappedPacketPlayOutUpdateSign extends WrappedPacket {
    Location getLocation();

    String[] getLines();

    void setLocation(Location location);

    void setLines(String[] lines);
}
