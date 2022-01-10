package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityVelocity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import org.bukkit.util.Vector;

public class WrappedPacketPlayOutEntityVelocityImpl extends PacketPlayOutEntityVelocity implements WrappedPacketPlayOutEntityVelocity {

    public WrappedPacketPlayOutEntityVelocityImpl(int entityID, Vector vector) {
        super(entityID, vector.getX(), vector.getY(), vector.getZ());
    }

}
