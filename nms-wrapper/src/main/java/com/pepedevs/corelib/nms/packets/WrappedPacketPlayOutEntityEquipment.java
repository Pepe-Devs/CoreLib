package com.pepedevs.corelib.nms.packets;

import com.pepedevs.corelib.nms.ItemSlot;
import org.bukkit.inventory.ItemStack;

public interface WrappedPacketPlayOutEntityEquipment extends WrappedPacket {

    int getEntityID();

    ItemSlot getSlot();

    ItemStack getItemStack();

    void setEntityID(int entityID);

    void setItemStack(ItemStack itemStack);

    void setSlot(ItemSlot slot);
}
