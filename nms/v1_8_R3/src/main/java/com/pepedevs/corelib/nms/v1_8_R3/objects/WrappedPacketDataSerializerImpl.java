package com.pepedevs.corelib.nms.v1_8_R3.objects;

import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import io.netty.buffer.ByteBuf;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.UUID;

public class WrappedPacketDataSerializerImpl extends PacketDataSerializer implements WrappedPacketDataSerializer {

    public WrappedPacketDataSerializerImpl(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override
    public WrappedPacketDataSerializer serializeComponent(Component component) {
        this.serializeComponent(AdventureUtils.asVanilla(component));
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeComponent(Object iChatBaseComponent) {
        try {
            super.a((IChatBaseComponent) iChatBaseComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeComponent(String s) {
        return this.serializeComponent(CraftChatMessage.fromString(s)[0]);
    }

    @Override
    public WrappedPacketDataSerializer serializeUUID(UUID uuid) {
        super.a(uuid);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeBlockPosition(Object blockPosition) {
        super.a((BlockPosition) blockPosition);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeBlockPosition(Location location) {
        super.a(new BlockPosition(location.getX(), location.getY(), location.getZ()));
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeItemStack(ItemStack itemStack) {
        super.a(CraftItemStack.asNMSCopy(itemStack));
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeItemStack(Object itemStack) {
        super.a((net.minecraft.server.v1_8_R3.ItemStack) itemStack);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeNBTTagCompound(Object nbtTag) {
        super.a((NBTTagCompound) nbtTag);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeEnum(Enum<?> e) {
        super.a(e);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeVarInt(int size) {
        super.b(size);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeBytes(byte[] array) {
        super.writeBytes(array);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeByte(int b) {
        super.writeByte(b);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeBoolean(boolean b) {
        super.writeBoolean(b);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeShort(int s) {
        super.writeShort(s);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeMedium(int i) {
        super.writeMedium(i);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeInt(int i) {
        super.writeInt(i);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeLong(long l) {
        super.writeLong(l);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeChar(char c) {
        super.writeChar(c);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeFloat(float f) {
        super.writeFloat(f);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeDouble(double d) {
        super.writeDouble(d);
        return this;
    }

    @Override
    public WrappedPacketDataSerializer serializeString(String s) {
        super.a(s);
        return this;
    }

}
