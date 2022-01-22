package com.pepedevs.radium.nms.packets;

import com.pepedevs.radium.nms.ItemSlot;
import org.bukkit.inventory.ItemStack;

public interface WrappedPacketPlayOutEntityEquipment extends WrappedPacket {

    int getEntityID();

    ItemSlot getSlot();

    ItemStack getItemStack();

    void setEntityID(int entityID);

    void setItemStack(ItemStack itemStack);

    void setSlot(ItemSlot slot);
}
