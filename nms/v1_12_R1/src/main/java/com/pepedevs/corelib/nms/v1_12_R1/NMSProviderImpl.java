package com.pepedevs.corelib.nms.v1_12_R1;

import com.mojang.authlib.GameProfile;
import com.pepedevs.corelib.nms.EnumGameMode;
import com.pepedevs.corelib.nms.NMSPlayer;
import com.pepedevs.corelib.nms.NMSProvider;
import com.pepedevs.corelib.nms.objects.WrappedDataWatcher;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.objects.WrappedPlayerInfoData;
import com.pepedevs.corelib.nms.v1_12_R1.objects.WrappedPacketDataSerializerImpl;
import com.pepedevs.corelib.nms.v1_12_R1.objects.WrappedPlayerInfoDataImpl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_12_R1.EnumChatFormat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NMSProviderImpl implements NMSProvider {

    public static final NMSProviderImpl INSTANCE = new NMSProviderImpl();

    private NMSProviderImpl(){}

    @Override
    public String craftChatMessageFromComponent(Object component) {
        IChatBaseComponent chatBaseComponent = (IChatBaseComponent) component;
        return CraftChatMessage.fromComponent(chatBaseComponent);
    }

    @Override
    public Object[] craftChatMessageFromString(String message) {
        return CraftChatMessage.fromString(message);
    }

    @Override
    public NMSPlayer getPlayer(Player player) {
        return new com.pepedevs.corelib.nms.v1_12_R1.NMSPlayer(player);
    }

    @Override
    public WrappedPacketDataSerializer getDataSerializer() {
        return this.getDataSerializer(Unpooled.buffer());
    }

    @Override
    public WrappedPacketDataSerializer getDataSerializer(ByteBuf byteBuf) {
        return new WrappedPacketDataSerializerImpl(byteBuf);
    }

    @Override
    public WrappedDataWatcher getDataWatcher() {
        return null;
    }

    @Override
    public WrappedDataWatcher.WrappedWatchableObject getWatchableObject(Object watchableObject) {
        return null;
    }

    @Override
    public WrappedDataWatcher.WrappedWatchableObject getWatchableObject(int i, int j, Object o) {
        return null;
    }

    @Override
    public WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, String name) {
        return new WrappedPlayerInfoDataImpl(gameProfile, latency, gamemode, name);
    }

    @Override
    public WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, Component name) {
        return new WrappedPlayerInfoDataImpl(gameProfile, latency, gamemode, name);
    }

    @Override
    public WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, Object name) {
        return new WrappedPlayerInfoDataImpl(gameProfile, latency, gamemode, name);
    }

    @Override
    public void craftEventFactoryHandleInventoryClose(Player player) {
        CraftEventFactory.handleInventoryCloseEvent(((CraftPlayer) player).getHandle());
    }

    @Override
    public Enum<?> getEnumChatFormat(ChatColor color) {
        if (color == ChatColor.MAGIC)
            return EnumChatFormat.OBFUSCATED;

        return EnumChatFormat.valueOf(color.name());
    }

    @Override
    public ChatColor getEnumChatFormat(Enum<?> enumChatFormat) {
        if (enumChatFormat == EnumChatFormat.OBFUSCATED)
            return ChatColor.MAGIC;

        return ChatColor.valueOf(enumChatFormat.name());
    }

    @Override
    public Object craftItemStackAsNmsCopy(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack);
    }

    @Override
    public Object craftItemStackAsCraftCopy(ItemStack itemStack) {
        return CraftItemStack.asCraftCopy(itemStack);
    }

    @Override
    public ItemStack craftItemStackAsBukkitCopy(Object nmsItemStack) {
        return CraftItemStack.asBukkitCopy((net.minecraft.server.v1_12_R1.ItemStack) nmsItemStack);
    }

}
