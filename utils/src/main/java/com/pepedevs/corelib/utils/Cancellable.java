package com.pepedevs.corelib.utils;

public interface Cancellable {

    void setCancelled(boolean cancelled);

    boolean isCancelled();
}
