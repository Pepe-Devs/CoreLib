package com.pepedevs.corelib.holograms.object;

import com.pepedevs.corelib.utils.StringUtils;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;

public class HologramSettings {

    private String defaultText = " ";
    private BiFunction<String, Player, String> messageParser = (s, player) -> StringUtils.translateAlternateColorCodes(s);
    private boolean downOrigin = false;

    private double heightText = 0.3;
    private double heightIcon = 0.6;
    private double heightHead = 0.75;
    private double heightSmallHead = 0.6;

    private int displayRange = 48;
    private int updateRange = 48;
    private int updateInterval = 20;

    public String getDefaultText() {
        return defaultText;
    }

    public BiFunction<String, Player, String> getMessageParser() {
        return messageParser;
    }

    public void setMessageParser(BiFunction<String, Player, String> messageParser) {
        this.messageParser = messageParser;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public boolean isDownOrigin() {
        return downOrigin;
    }

    public void setDownOrigin(boolean downOrigin) {
        this.downOrigin = downOrigin;
    }

    public double getHeightText() {
        return heightText;
    }

    public void setHeightText(double heightText) {
        this.heightText = heightText;
    }

    public double getHeightIcon() {
        return heightIcon;
    }

    public void setHeightIcon(double heightIcon) {
        this.heightIcon = heightIcon;
    }

    public double getHeightHead() {
        return heightHead;
    }

    public void setHeightHead(double heightHead) {
        this.heightHead = heightHead;
    }

    public double getHeightSmallHead() {
        return heightSmallHead;
    }

    public void setHeightSmallHead(double heightSmallHead) {
        this.heightSmallHead = heightSmallHead;
    }

    public int getDisplayRange() {
        return displayRange;
    }

    public void setDisplayRange(int displayRange) {
        this.displayRange = displayRange;
    }

    public int getUpdateRange() {
        return updateRange;
    }

    public void setUpdateRange(int updateRange) {
        this.updateRange = updateRange;
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

}
