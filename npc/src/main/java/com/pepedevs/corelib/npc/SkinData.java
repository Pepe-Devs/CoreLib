package com.pepedevs.corelib.npc;

public class SkinData {

    private boolean capeEnabled;
    private boolean jacketEnabled;
    private boolean leftSleeveEnabled;
    private boolean rightSleeveEnabled;
    private boolean leftPantsLegEnabled;
    private boolean rightPantsLegEnabled;
    private boolean hatEnabled;

    protected SkinData() {
        this.capeEnabled = true;
        this.jacketEnabled = true;
        this.leftSleeveEnabled = true;
        this.rightSleeveEnabled = true;
        this.leftPantsLegEnabled = true;
        this.rightPantsLegEnabled = true;
        this.hatEnabled = true;
    }

    protected byte buildByte() {
        byte a = (byte) 0x00;
        if (capeEnabled) a = (byte) (a | (byte) 0x01);
        if (jacketEnabled) a = (byte) (a | (byte) 0x02);
        if (leftSleeveEnabled) a = (byte) (a | (byte) 0x04);
        if (rightSleeveEnabled) a = (byte) (a | (byte) 0x08);
        if (leftPantsLegEnabled) a = (byte) (a | (byte) 0x10);
        if (rightPantsLegEnabled) a = (byte) (a | (byte) 0x20);
        if (hatEnabled) a = (byte) (a | (byte) 0x40);
        return a;
    }

    protected boolean isCapeEnabled() {
        return capeEnabled;
    }

    protected void setCapeEnabled(boolean capeEnabled) {
        this.capeEnabled = capeEnabled;
    }

    protected boolean isJacketEnabled() {
        return jacketEnabled;
    }

    protected void setJacketEnabled(boolean jacketEnabled) {
        this.jacketEnabled = jacketEnabled;
    }

    protected boolean isLeftSleeveEnabled() {
        return leftSleeveEnabled;
    }

    protected void setLeftSleeveEnabled(boolean leftSleeveEnabled) {
        this.leftSleeveEnabled = leftSleeveEnabled;
    }

    protected boolean isRightSleeveEnabled() {
        return rightSleeveEnabled;
    }

    protected void setRightSleeveEnabled(boolean rightSleeveEnabled) {
        this.rightSleeveEnabled = rightSleeveEnabled;
    }

    protected boolean isLeftPantsLegEnabled() {
        return leftPantsLegEnabled;
    }

    protected void setLeftPantsLegEnabled(boolean leftPantsLegEnabled) {
        this.leftPantsLegEnabled = leftPantsLegEnabled;
    }

    protected boolean isRightPantsLegEnabled() {
        return rightPantsLegEnabled;
    }

    protected void setRightPantsLegEnabled(boolean rightPantsLegEnabled) {
        this.rightPantsLegEnabled = rightPantsLegEnabled;
    }

    protected boolean isHatEnabled() {
        return hatEnabled;
    }

    protected void setHatEnabled(boolean hatEnabled) {
        this.hatEnabled = hatEnabled;
    }
}
