package com.pepedevs.radium.nms.v1_8_R3.packets;

import com.pepedevs.radium.nms.ItemSlot;
import com.pepedevs.radium.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.radium.nms.packets.WrappedPacketPlayOutEntityEquipment;
import com.pepedevs.radium.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
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
        this.entityID = dataSerializer.e();
        this.slot = ItemSlot.fromOldSlot(dataSerializer.readShort());
        try {
            this.itemStack = CraftItemStack.asBukkitCopy(dataSerializer.i());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        serializer.serializeVarInt(entityID).serializeShort((short) slot.OLD).serializeItemStack(itemStack);
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
