package ir.yeksaco.jahesh.models.users;

public class SendCodeRequest {
    public  String MobileNumber;
    public  String AppCode;

    public SendCodeRequest(String mobileNumber,String appCode) {
        MobileNumber = mobileNumber;
        AppCode = appCode;
    }
    public SendCodeRequest( ) {

    }
}
