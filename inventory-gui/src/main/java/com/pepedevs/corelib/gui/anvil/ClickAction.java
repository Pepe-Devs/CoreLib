package com.pepedevs.corelib.gui.anvil;

import com.pepedevs.corelib.gui.anvil.action.AnvilItemClickAction;

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
