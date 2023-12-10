package ir.yeksaco.jahesh.webService.iterfaces;


import java.util.ArrayList;

import ir.yeksaco.jahesh.models.general.ResponseBase;
import ir.yeksaco.jahesh.models.users.ProfileData;
import ir.yeksaco.jahesh.models.users.SendCodeRequest;
import ir.yeksaco.jahesh.models.users.SendCodeResponse;
import ir.yeksaco.jahesh.models.users.UserProfile;
import ir.yeksaco.jahesh.models.users.VerifyCodeRequest;
import ir.yeksaco.jahesh.models.users.VerifyCodeResponse;
import ir.yeksaco.jahesh.models.users.buys.BuyHistoryModel;
import ir.yeksaco.jahesh.models.users.token.RefreshTokenResponse;
import ir.yeksaco.jahesh.models.users.token.ResetPasswordRequest;
import ir.yeksaco.jahesh.models.users.token.UpdateFcmTokenRequest;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
public interface IUser {

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @POST("Account/RefreshToken")
    Call<ResponseBase<RefreshTokenResponse>> RefreshToken(@Header("Authorization") String AuthKey,@Header("RefreshToken") String RefreshToken);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @POST("Account/SendCode")
    Call<SendCodeResponse> SendCode(@Body SendCodeRequest model);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @POST("Account/VerifyCode")
    Call<ResponseBase<VerifyCodeResponse>> VerifyCode(@Body VerifyCodeRequest model);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @POST("Account/CheckUserAuthenticated")
    Call<ResponseBase<RefreshTokenResponse>> CheckUserAuthenticated(@Header("Authorization") String AuthKey);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @POST("Account/ResetPassword")
    Call<ResponseBase<String>> ResetPassword(@Header("Authorization") String AuthKey,@Body ResetPasswordRequest model);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @POST("Account/UpdateFCM")
    Call<ResponseBase<String>> UpdateFCM(@Header("Authorization") String AuthKey,@Body UpdateFcmTokenRequest model);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @GET("Account/BuyHistory")
    Call<ResponseBase<ArrayList<BuyHistoryModel>>> BuyHistory(@Header("Authorization") String AuthKey);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @GET("UserProfile/GetUserProfile")
    Call<ResponseBase<ProfileData>> GetProfile(@Header("Authorization") String AuthKey);

    @Headers({
            "content-type: application/json",
            "Accept:application/json",
    })
    @POST("UserProfile/Update")
    Call<ResponseBase<String>> UpdateProfile(@Header("Authorization") String AuthKey,@Body UserProfile UserProfile);
}