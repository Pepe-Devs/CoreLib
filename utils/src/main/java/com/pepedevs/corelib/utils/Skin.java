package com.pepedevs.corelib.utils;

public class Skin {

    public static final Skin NULL = new Skin(null, null);

    private String value;
    private String signature;

    protected Skin(String value, String signature) {
        this.value = value;
        this.signature = signature;
    }

    public static Skin from(String value, String signature) {
        return new Skin(value, signature);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Skin{" +
                "value='" + value + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
