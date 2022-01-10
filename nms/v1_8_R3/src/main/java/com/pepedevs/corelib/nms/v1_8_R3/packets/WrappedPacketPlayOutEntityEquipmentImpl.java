package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.packets.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityEquipment;
import com.pepedevs.corelib.nms.v1_8_R3.NMSImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class WrappedPacketPlayOutEntityEquipmentImpl extends PacketPlayOutEntityEquipment implements WrappedPacketPlayOutEntityEquipment {

    public WrappedPacketPlayOutEntityEquipmentImpl(int entityId, int slot, ItemStack itemStack) {
        WrappedPacketDataSerializer serializer = NMSImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(entityId).serializeShort((short) slot).serializeItemStack(itemStack);
        try {
            this.a((PacketDataSerializer) serializer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
