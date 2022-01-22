package com.pepedevs.radium.nms.packets;

public interface WrappedPacketPlayOutAttachEntity extends WrappedPacket {

    AttachmentType getType();

    int getProviderID();

    int getRiderID();

    void setProviderID(int providerID);

    void setRiderID(int riderID);

    void setType(AttachmentType type);

    enum AttachmentType {
        MOUNT,
        LEASH
    }

}
