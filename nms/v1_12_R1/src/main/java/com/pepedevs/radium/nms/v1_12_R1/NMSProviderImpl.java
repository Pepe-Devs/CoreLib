package com.pepedevs.radium.nms.v1_12_R1;

import com.pepedevs.radium.nms.NMSPlayer;
import com.pepedevs.radium.nms.NMSProvider;
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
        return new com.pepedevs.radium.nms.v1_12_R1.NMSPlayer(player);
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
