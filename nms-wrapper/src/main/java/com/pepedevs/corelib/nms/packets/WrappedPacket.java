package com.pepedevs.corelib.nms.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;

public interface WrappedPacket {

    String toString();

    WrappedPacketDataSerializer buildData();

    Object buildPacket();
}
