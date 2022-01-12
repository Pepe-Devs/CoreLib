package com.pepedevs.corelib.nms.v1_8_R3.objects;

import com.mojang.authlib.GameProfile;
import com.pepedevs.corelib.adventure.AdventureUtils;
import com.pepedevs.corelib.nms.EnumGameMode;
import com.pepedevs.corelib.nms.objects.WrappedPlayerInfoData;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_8_R3.WorldSettings;

public class WrappedPlayerInfoDataImpl implements WrappedPlayerInfoData {

    private final int latency;
    private final EnumGameMode gamemode;
    private final GameProfile gameProfile;
    private final Component name;

    public WrappedPlayerInfoDataImpl(GameProfile gameProfile, int latency, EnumGameMode gamemode, Component nameComponent) {
        this.latency = latency;
        this.name = nameComponent;
        this.gamemode = gamemode;
        this.gameProfile = gameProfile;
    }

    public WrappedPlayerInfoDataImpl(GameProfile gameProfile, int latency, EnumGameMode gamemode, String name) {
        this(gameProfile, latency, gamemode, name == null ? null : AdventureUtils.fromVanillaString(name));
    }

    public WrappedPlayerInfoDataImpl(GameProfile gameProfile, int latency, EnumGameMode gamemode, Object nameComponent) {
        this(gameProfile, latency, gamemode, nameComponent == null ? null : AdventureUtils.asAdventure(nameComponent));
    }

    @Override
    public GameProfile getGameProfile() {
        return this.gameProfile;
    }

    @Override
    public int getLatency() {
        return this.latency;
    }

    @Override
    public EnumGameMode getGameMode() {
        return gamemode;
    }

    @Override
    public Object getEnumGameMode() {
        return WorldSettings.EnumGamemode.valueOf(this.gamemode.name());
    }

    @Override
    public String getName() {
        return this.name == null ? null : AdventureUtils.toVanillaString(this.name);
    }

    @Override
    public Object getNameComponent() {
        return this.name == null ? null : AdventureUtils.asVanilla(this.name);
    }

    @Override
    public Component getComponent() {
        return this.name;
    }

}
