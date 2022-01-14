public class SkinData {

    private boolean capeEnabled;
    private boolean jacketEnabled;
    private boolean leftSleeveEnabled;
    private boolean rightSleeveEnabled;
    private boolean leftPantsLegEnabled;
    private boolean rightPantsLegEnabled;
    private boolean hatEnabled;

    public SkinData() {
        this.capeEnabled = true;
        this.jacketEnabled = true;
        this.leftSleeveEnabled = true;
        this.rightSleeveEnabled = true;
        this.leftPantsLegEnabled = true;
        this.rightPantsLegEnabled = true;
        this.hatEnabled = true;
    }

    public byte buildByte() {
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

    public boolean isCapeEnabled() {
        return capeEnabled;
    }

    public void setCapeEnabled(boolean capeEnabled) {
        this.capeEnabled = capeEnabled;
    }

    public boolean isJacketEnabled() {
        return jacketEnabled;
    }

    public void setJacketEnabled(boolean jacketEnabled) {
        this.jacketEnabled = jacketEnabled;
    }

    public boolean isLeftSleeveEnabled() {
        return leftSleeveEnabled;
    }

    public void setLeftSleeveEnabled(boolean leftSleeveEnabled) {
        this.leftSleeveEnabled = leftSleeveEnabled;
    }

    public boolean isRightSleeveEnabled() {
        return rightSleeveEnabled;
    }

    public void setRightSleeveEnabled(boolean rightSleeveEnabled) {
        this.rightSleeveEnabled = rightSleeveEnabled;
    }

    public boolean isLeftPantsLegEnabled() {
        return leftPantsLegEnabled;
    }

    public void setLeftPantsLegEnabled(boolean leftPantsLegEnabled) {
        this.leftPantsLegEnabled = leftPantsLegEnabled;
    }

    public boolean isRightPantsLegEnabled() {
        return rightPantsLegEnabled;
    }

    public void setRightPantsLegEnabled(boolean rightPantsLegEnabled) {
        this.rightPantsLegEnabled = rightPantsLegEnabled;
    }

    public boolean isHatEnabled() {
        return hatEnabled;
    }

    public void setHatEnabled(boolean hatEnabled) {
        this.hatEnabled = hatEnabled;
    }
}
