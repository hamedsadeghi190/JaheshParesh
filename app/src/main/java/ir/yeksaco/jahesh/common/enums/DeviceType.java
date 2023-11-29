package ir.yeksaco.jahesh.common.enums;

public enum DeviceType {
    Android(1),
    Ios(2),
    WebBrowser(3);

    private int value;

    private DeviceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
