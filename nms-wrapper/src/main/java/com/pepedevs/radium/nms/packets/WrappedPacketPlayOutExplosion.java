package com.pepedevs.radium.nms.packets;

import org.bukkit.util.Vector;

import java.util.Set;

public interface WrappedPacketPlayOutExplosion extends WrappedPacket {

    Vector getLocation();

    float getPower();

    Set<Vector> getBlocks();

    Vector getKnockback();

    void setLocation(Vector location);

    void setPower(float power);

    void setBlocks(Set<Vector> blocks);

    void setKnockback(Vector knockback);

}
