package com.pepedevs.corelib.nms.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;

public interface WrappedPacket {

    String toString();

    Object buildPacket();

    WrappedPacketDataSerializer buildData();
}
