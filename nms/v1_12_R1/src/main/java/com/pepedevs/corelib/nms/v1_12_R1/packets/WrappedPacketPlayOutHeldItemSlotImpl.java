package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutHeldItemSlot;

import java.io.IOException;

public class WrappedPacketPlayOutHeldItemSlotImpl extends PacketPlayOutHeldItemSlot implements WrappedPacketPlayOutHeldItemSlot {

    public WrappedPacketPlayOutHeldItemSlotImpl(int slot) {
        super(slot);
    }

    public WrappedPacketPlayOutHeldItemSlotImpl(WrappedPacketDataSerializer serializer) {
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
