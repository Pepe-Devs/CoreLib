package com.pepedevs.corelib.npc;

public class PlayerNPCData {

    private boolean isOnFire;
    private boolean isCrouched;
    private boolean isSprinting;
    private boolean isHoldingRightClick;
    private boolean isInvisible;

    public PlayerNPCData() {
        this.isOnFire = false;
        this.isCrouched = false;
        this.isSprinting = false;
        this.isHoldingRightClick = false;
        this.isInvisible = false;
    }

    public byte buildByte() {
        byte a = (byte) 0x00;
        if (isOnFire) a = (byte) (a | (byte) 0x01);
        if (isCrouched) a = (byte) (a | (byte) 0x02);
        if (isSprinting) a = (byte) (a | (byte) 0x08);
        if (isHoldingRightClick) a = (byte) (a | (byte) 0x10);
        if (isInvisible) a = (byte) (a | (byte) 0x20);
        return a;
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    public boolean isCrouched() {
        return isCrouched;
    }

    public void setCrouched(boolean crouched) {
        isCrouched = crouched;
    }

    public boolean isSprinting() {
        return isSprinting;
    }

    public void setSprinting(boolean sprinting) {
        isSprinting = sprinting;
    }

    public boolean isHoldingRightClick() {
        return isHoldingRightClick;
    }

    public void setHoldingRightClick(boolean holdingRightClick) {
        isHoldingRightClick = holdingRightClick;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public void setInvisible(boolean invisible) {
        isInvisible = invisible;
    }
}
