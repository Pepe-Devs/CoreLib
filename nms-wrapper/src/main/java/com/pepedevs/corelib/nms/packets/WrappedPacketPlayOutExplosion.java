package com.pepedevs.corelib.nms.packets;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Set;

public interface WrappedPacketPlayOutExplosion extends WrappedPacket {
    Location getLocation();

    float getPower();

    Set<Location> getBlocks();

    Vector getKnockback();

    void setLocation(Location location);

    void setPower(float power);

    void setBlocks(Set<Location> blocks);

    void setKnockback(Vector knockback);
}
