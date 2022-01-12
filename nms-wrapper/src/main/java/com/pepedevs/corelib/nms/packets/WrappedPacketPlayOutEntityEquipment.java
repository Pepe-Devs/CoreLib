package com.pepedevs.corelib.nms.packets;

import org.bukkit.inventory.ItemStack;

public interface WrappedPacketPlayOutEntityEquipment extends WrappedPacket {


    int getEntityID();

    int getSlot();

    ItemStack getItemStack();

    void setEntityID(int entityID);

    void setItemStack(ItemStack itemStack);

    void setSlot(int slot);
}
