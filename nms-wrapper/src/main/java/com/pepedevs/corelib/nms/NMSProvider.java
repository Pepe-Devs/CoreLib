package com.pepedevs.corelib.nms;

import com.mojang.authlib.GameProfile;
import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.objects.WrappedPlayerInfoData;
import io.netty.buffer.ByteBuf;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface NMSProvider {

    String craftChatMessageFromComponent(Object component);

    Object[] craftChatMessageFromString(String message);

    NMSPlayer getPlayer(Player player);

    WrappedPacketDataSerializer getDataSerializer();

    WrappedPacketDataSerializer getDataSerializer(ByteBuf byteBuf);

    WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, String name);

    WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, Component name);

    WrappedPlayerInfoData getPlayerInfo(GameProfile gameProfile, int latency, EnumGameMode gamemode, Object name);

    void craftEventFactoryHandleInventoryClose(Player player);

    Enum<?> getEnumChatFormat(ChatColor color);

    ChatColor getEnumChatFormat(Enum<?> enumChatFormat);

}
