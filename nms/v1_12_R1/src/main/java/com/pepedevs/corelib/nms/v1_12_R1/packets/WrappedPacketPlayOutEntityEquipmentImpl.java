package com.pepedevs.corelib.nms.v1_12_R1.packets;

import com.pepedevs.corelib.nms.ItemSlot;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutEntityEquipment;
import com.pepedevs.corelib.nms.v1_12_R1.NMSProviderImpl;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class WrappedPacketPlayOutEntityEquipmentImpl implements WrappedPacketPlayOutEntityEquipment {

    private int entityID;
    private ItemSlot slot;
    private ItemStack itemStack;

    public WrappedPacketPlayOutEntityEquipmentImpl(int entityID, ItemSlot slot, ItemStack itemStack) {
        this.entityID = entityID;
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public WrappedPacketPlayOutEntityEquipmentImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.entityID = dataSerializer.g();
        this.slot = ItemSlot.valueOf(dataSerializer.a(EnumItemSlot.class).name());
        this.itemStack = CraftItemStack.asBukkitCopy(dataSerializer.k()).clone();
    }

    @Override
    public int getEntityID() {
        return entityID;
    }

    @Override
    public ItemSlot getSlot() {
        return slot;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void setSlot(ItemSlot slot) {
        this.slot = slot;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeIntToByte(entityID).serializeEnum(slot).serializeItemStack(itemStack);
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
