package ir.yeksaco.jahesh.models.users;

import ir.yeksaco.jahesh.common.enums.*;

public class UserDevice {
    public String DeviceID;
    public String FireBaseToken;
    public DeviceType deviceType;
    public int CodeVersion;
    public String Brand;
    public String SdkVersion;
    public String Model;
    public String Manufacture;
    public String AppVersion;

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getFireBaseToken() {
        return FireBaseToken;
    }

    public void setFireBaseToken(String fireBaseToken) {
        FireBaseToken = fireBaseToken;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public int getCodeVersion() {
        return CodeVersion;
    }

    public void setCodeVersion(int codeVersion) {
        CodeVersion = codeVersion;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getSdkVersion() {
        return SdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        SdkVersion = sdkVersion;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getManufacture() {
        return Manufacture;
    }

    public void setManufacture(String manufacture) {
        Manufacture = manufacture;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }
}
