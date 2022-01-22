package com.pepedevs.radium.gui.inventory.handler;

import com.pepedevs.radium.gui.inventory.ItemMenu;
import com.pepedevs.radium.gui.inventory.action.ItemMenuClickAction;
import com.pepedevs.radium.gui.inventory.holder.ItemMenuHolder;
import com.pepedevs.radium.utils.scheduler.SchedulerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

/** Handler for handling Item Menu. */
public class ItemMenuHandler implements Listener {

    protected final ItemMenu menu;
    protected final Plugin plugin;

    /**
     * Constructs the ItemMenu Handler.
     *
     * <p>
     *
     * @param menu ItemMenu
     * @param plugin Plugin to register handler
     */
    public ItemMenuHandler(ItemMenu menu, Plugin plugin) {
        this.menu = menu;
        this.plugin = plugin;
    }

    /**
     * Returns the plugin instance.
     *
     * <p>
     *
     * @return Plugin instance
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Add delay to menu open.
     *
     * <p>
     *
     * @param player Player to open menu
     * @param delay Delay in milliseconds
     */
    public void delayedOpen(Player player, int delay) {
        delayed(
                () -> {
                    menu.open(player);
                },
                delay);
    }

    /**
     * Add delay to menu update.
     *
     * <p>
     *
     * @param player Player to open menu
     * @param delay Delay in milliseconds
     */
    public void delayedUpdate(Player player, int delay) {
        delayed(
                () -> {
                    menu.update(player);
                },
                delay);
    }

    /**
     * Add delay to menu close.
     *
     * <p>
     *
     * @param player Player to open menu
     * @param delay Delay in milliseconds
     */
    public void delayedClose(Player player, int delay) {
        delayed(
                () -> {
                    menu.close(player);
                },
                delay);
    }

    /** Unregister the handler. */
    public void unregisterListener() {
        HandlerList.unregisterAll(this);
    }

    protected void delayed(Runnable runnable, int delay) {
        SchedulerUtils.scheduleSyncDelayedTask(runnable, delay, plugin);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() == null
                || event.getInventory().getType() != InventoryType.CHEST
                || !(event.getWhoClicked() instanceof Player)
                || !(event.getInventory().getHolder() instanceof ItemMenuHolder)) {
            return;
        }

        ItemMenuHolder holder = (ItemMenuHolder) event.getInventory().getHolder();
        if (!menu.equals(holder.getItemMenu())) {
            return;
        }

        final ItemMenuClickAction action = new ItemMenuClickAction(menu, event);
        ((ItemMenuHolder) event.getInventory().getHolder()).getItemMenu().onClick(action);
        event.setCurrentItem(action.getCurrentItem());
        event.setCursor(action.getCursor());
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(plugin)) {
            this.menu.closeOnlinePlayers();
        }
    }
}
