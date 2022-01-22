package com.pepedevs.radium.gui.anvil;

import com.pepedevs.radium.gui.anvil.action.AnvilItemClickAction;

public interface ClickAction {

    /**
     * Action triggered when a gui slot is clicked.
     *
     * <p>
     *
     * @param action {@link AnvilItemClickAction}
     */
    void onClick(AnvilItemClickAction action);
}
