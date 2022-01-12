package com.pepedevs.corelib.nms.packets;

import com.pepedevs.corelib.nms.InventoryType;
import net.kyori.adventure.text.Component;

public interface WrappedPacketPlayOutOpenWindow extends WrappedPacket {

    int getNextContainerCounter();

    Component getTitle();

    InventoryType getType();

    int getSize();

    void setNextContainerCounter(int nextContainerCounter);

    void setTitle(Component title);

    void setType(InventoryType type);

    void setSize(int size);
}
