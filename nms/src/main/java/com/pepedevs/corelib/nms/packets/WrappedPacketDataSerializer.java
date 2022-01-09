package com.pepedevs.corelib.nms.packets;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface WrappedPacketDataSerializer {

    WrappedPacketDataSerializer serializeComponent(Component component);

    WrappedPacketDataSerializer serializeComponent(Object iChatBaseComponent);

    WrappedPacketDataSerializer serializeComponent(String s);

    WrappedPacketDataSerializer serializeUUID(UUID uuid);

    WrappedPacketDataSerializer serializeBlockPosition(Object blockPosition);

    WrappedPacketDataSerializer serializeBlockPosition(Location location);

    WrappedPacketDataSerializer serializeItemStack(ItemStack itemStack);

    WrappedPacketDataSerializer serializeItemStack(Object itemStack);

    WrappedPacketDataSerializer serializeNBTTagCompound(Object nbtTag);

    WrappedPacketDataSerializer serializeEnum(Enum<?> e);

    WrappedPacketDataSerializer serializeSize(int size);

    WrappedPacketDataSerializer serializeBytes(byte[] array);

    WrappedPacketDataSerializer serializeByte(int b);

    WrappedPacketDataSerializer serializeBoolean(boolean b);

    WrappedPacketDataSerializer serializeShort(short s);

    WrappedPacketDataSerializer serializeMedium(int i);

    WrappedPacketDataSerializer serializeInt(int i);

    WrappedPacketDataSerializer serializeLong(long l);

    WrappedPacketDataSerializer serializeChar(char c);

    WrappedPacketDataSerializer serializeFloat(float f);

    WrappedPacketDataSerializer serializeDouble(double d);

    WrappedPacketDataSerializer serializeString(String s);
}
