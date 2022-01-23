package com.pepedevs.radium.nms;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NMSProvider {

    String craftChatMessageFromComponent(Object component);

    Object[] craftChatMessageFromString(String message);

    NMSPlayer getPlayer(Player player);

    void craftEventFactoryHandleInventoryClose(Player player);

    Enum<?> getEnumChatFormat(ChatColor color);

    ChatColor getEnumChatFormat(Enum<?> enumChatFormat);

    Object craftItemStackAsNmsCopy(ItemStack itemStack);

    Object craftItemStackAsCraftCopy(ItemStack itemStack);

    ItemStack craftItemStackAsBukkitCopy(Object nmsItemStack);

}
