package ir.yeksaco.jahesh.models.users;

import ir.yeksaco.jahesh.Messages;
import ir.yeksaco.jahesh.common.enums.*;
public class VerifyCodeRequest {
    public String MobileNumber;
    public int VerifyCode;
    public UserDevice UserDevice;

    public VerifyCodeRequest() {
    }

    public VerifyCodeRequest(String mobileNumber, int verifyCode, ir.yeksaco.jahesh.models.users.UserDevice userDevice) {
        MobileNumber = mobileNumber;
        VerifyCode = verifyCode;
        UserDevice = userDevice;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public int getVerifyCode() {
        return VerifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        VerifyCode = verifyCode;
    }

    public ir.yeksaco.jahesh.models.users.UserDevice getUserDevice() {
        return UserDevice;
    }

    public void setUserDevice(ir.yeksaco.jahesh.models.users.UserDevice userDevice) {
        UserDevice = userDevice;
    }
}

