package com.pepedevs.corelib.gui.inventory.item.action.open;

import com.pepedevs.corelib.gui.inventory.ItemMenu;
import com.pepedevs.corelib.gui.inventory.action.ItemClickAction;
import com.pepedevs.corelib.gui.inventory.item.action.ActionItem;
import com.pepedevs.corelib.gui.inventory.item.action.ItemAction;
import com.pepedevs.corelib.gui.inventory.item.action.ItemActionPriority;
import org.bukkit.inventory.ItemStack;

/** Implementation of {@link ActionItem} handling with open menu option. */
public class OpenMenuActionItem extends ActionItem {

    protected ItemMenu menu;

    /**
     * Constructs the Open Menu Action Item.
     *
     * <p>
     *
     * @param name Name of the Item
     * @param icon ItemStack icon of the Item
     * @param lore Lore of the Item
     */
    public OpenMenuActionItem(String name, ItemStack icon, String... lore) {
        super(name, icon, lore);
        addDefaultAction();
    }

    /**
     * Constructs the Open Menu Action Item.
     *
     * <p>
     *
     * @param icon ItemStack icon of the Item
     */
    public OpenMenuActionItem(ItemStack icon) {
        super(icon);
        addDefaultAction();
    }

    /** Adds the default action of opening other menu. */
    protected void addDefaultAction() {
        addAction(
                new ItemAction() {

                    @Override
                    public ItemActionPriority getPriority() {
                        return ItemActionPriority.LOW;
                    }

                    @Override
                    public void onClick(ItemClickAction action) {
                        action.getMenu().getHandler().delayedClose(action.getPlayer(), 1);
                        menu.getHandler().delayedOpen(action.getPlayer(), 3);
                    }
                });
    }

    /**
     * Sets the {@link ItemMenu} that will be opened when clicking this.
     *
     * <p>
     *
     * @param menu Menu to open when this clicked
     * @return This Object, for chaining
     */
    public OpenMenuActionItem setMenu(ItemMenu menu) {
        this.menu = menu;
        return this;
    }
}
