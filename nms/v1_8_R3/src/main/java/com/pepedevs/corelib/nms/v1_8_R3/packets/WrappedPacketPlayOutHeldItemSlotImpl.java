package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutHeldItemSlot;

public class WrappedPacketPlayOutHeldItemSlotImpl extends PacketPlayOutHeldItemSlot implements WrappedPacketPlayOutHeldItemSlot {

    public WrappedPacketPlayOutHeldItemSlotImpl(int slot) {
        super(slot);
    }

}
