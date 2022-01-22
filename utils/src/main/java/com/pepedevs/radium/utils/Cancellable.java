package com.pepedevs.radium.utils;

public interface Cancellable {

    void setCancelled(boolean cancelled);

    boolean isCancelled();
}
