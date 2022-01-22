package com.pepedevs.radium.gui.inventory.item.action;

import com.pepedevs.radium.gui.inventory.action.ItemClickAction;

/** Class to classify menu item action. */
public interface ItemAction {

    /**
     * Returns the priority of the action.
     *
     * <p>
     *
     * @return {@link ItemActionPriority}
     */
    public ItemActionPriority getPriority();

    /**
     * Click action on the item.
     *
     * <p>
     *
     * @param action {@link ItemClickAction} on the Item
     */
    public void onClick(ItemClickAction action);
}
