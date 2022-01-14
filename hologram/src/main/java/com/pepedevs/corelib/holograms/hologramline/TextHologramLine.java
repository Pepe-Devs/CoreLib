package com.pepedevs.corelib.holograms.hologramline;

import com.pepedevs.corelib.holograms.object.HologramLine;
import com.pepedevs.corelib.holograms.object.HologramLineType;
import com.pepedevs.corelib.holograms.utils.PacketUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TextHologramLine extends HologramLine {

    private String content;

    public TextHologramLine(Location location, String content) {
        super(location, HologramLineType.TEXT);
        this.content = content;
        //        this.height = this.getParent().getParent().getSettings().getHeightText();
    }

    @Override
    public void show(Player... players) {
        for (Player player : players) {
            if (this.isVisible(player)) continue;

            PacketUtils.showFakeEntityArmorStand(
                    player, this.getLocation(), this.entityIds[0], true, true, true);
            PacketUtils.updateFakeEntityCustomName(
                    player, this.parse(this.getContent(), player), this.entityIds[0]);
            this.viewers.add(player.getUniqueId());
        }
    }

    @Override
    public void destroy() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
