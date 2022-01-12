package com.pepedevs.corelib.nms.v1_8_R3.packets;

import com.pepedevs.corelib.nms.objects.WrappedPacketDataSerializer;
import com.pepedevs.corelib.nms.packets.WrappedPacketPlayOutScoreboardObjective;
import com.pepedevs.corelib.nms.v1_8_R3.NMSProviderImpl;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;

import java.io.IOException;

public class WrappedPacketPlayOutScoreboardObjectiveImpl implements WrappedPacketPlayOutScoreboardObjective {

    private String name;
    private String displayName;
    private HealthDisplay display;
    private ObjectiveMode mode;

    public WrappedPacketPlayOutScoreboardObjectiveImpl(String name, String displayName, HealthDisplay display, ObjectiveMode mode) {
        this.name = name;
        this.displayName = displayName;
        this.display = display;
        this.mode = mode;
    }

    public WrappedPacketPlayOutScoreboardObjectiveImpl(WrappedPacketDataSerializer serializer) {
        PacketDataSerializer dataSerializer = (PacketDataSerializer) serializer;
        this.name = dataSerializer.c(16);
        this.mode = ObjectiveMode.values()[dataSerializer.readByte()];
        if (this.mode == ObjectiveMode.CREATE || this.mode == ObjectiveMode.UPDATE) {
            this.displayName = dataSerializer.c(32);
            this.display = HealthDisplay.valueOf(dataSerializer.c(16));
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public HealthDisplay getDisplay() {
        return display;
    }

    @Override
    public void setDisplay(HealthDisplay display) {
        this.display = display;
    }

    @Override
    public ObjectiveMode getMode() {
        return mode;
    }

    @Override
    public void setMode(ObjectiveMode mode) {
        this.mode = mode;
    }

    @Override
    public WrappedPacketDataSerializer buildData() {
        WrappedPacketDataSerializer serializer = NMSProviderImpl.INSTANCE.getDataSerializer();
        serializer.serializeString(this.name)
                .serializeByte(this.mode.ordinal());
        if (this.mode == ObjectiveMode.CREATE || this.mode == ObjectiveMode.UPDATE) {
            serializer.serializeString(this.displayName)
                    .serializeString(this.display.name().toLowerCase());
        }
        return serializer;
    }

    @Override
    public Object buildPacket() {
        PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective();
        try {
            packet.a((PacketDataSerializer) buildData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

}
