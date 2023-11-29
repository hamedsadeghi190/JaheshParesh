package ir.yeksaco.jahesh.models.users.token;

public class UpdateFcmTokenRequest {
    public String FireBaseToken;
    public String DeviceId;

    public UpdateFcmTokenRequest( ) {

    }

    public UpdateFcmTokenRequest(String fireBaseToken, String deviceId) {
        FireBaseToken = fireBaseToken;
        DeviceId = deviceId;
    }

    public String getFireBaseToken() {
        return FireBaseToken;
    }

    public void setFireBaseToken(String fireBaseToken) {
        FireBaseToken = fireBaseToken;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }
}
