package com.pepedevs.corelib.nms;

import com.mojang.authlib.GameProfile;
import com.pepedevs.corelib.nms.objects.WrappedDataWatcher;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.objects.WrappedPlayerInfoData;
import io.netty.buffer.ByteBuf;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NMSProvider {

    String craftChatMessageFromComponent(Object component);

    Object[] craftChatMessageFromString(String message);

    NMSPlayer getPlayer(Player player);

    WrappedPacketDataSerializer getDataSerializer();

    WrappedPacketDataSerializer getDataSerializer(ByteBuf byteBuf);

    WrappedDataWatcher getDataWatcher();

    WrappedDataWatcher.WrappedWatchableObject getWatchableObject(Object watchableObject);

    WrappedDataWatcher.WrappedWatchableObject getWatchableObject(int i, int j, Object o);

    WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, String name);

    WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, Component name);

    WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, Object name);

    void craftEventFactoryHandleInventoryClose(Player player);

    Enum<?> getEnumChatFormat(ChatColor color);

    ChatColor getEnumChatFormat(Enum<?> enumChatFormat);

    Object craftItemStackAsNmsCopy(ItemStack itemStack);

    Object craftItemStackAsCraftCopy(ItemStack itemStack);

    ItemStack craftItemStackAsBukkitCopy(Object nmsItemStack);

}
